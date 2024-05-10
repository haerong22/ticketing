# 콘서트 예약 서비스

---

## 프로젝트 산출물

#### [Flow chart](docs/FLOW.md)

#### [Sequence diagram](docs/SEQUENCE.md)

#### [ERD](docs/ERD.md)

#### [API 명세](docs/API.md)

#### [Git Branch 전략](docs/git-branch.md)

---

## 대기열
- 대기열은 Redis를 활용하여 구현
- 일정 시간마다 대기 토큰을 활성 토큰으로 전환
- 서비스에 접근 가능한 유저 수를 제한(N개의 활성 토큰)
  - DB처리량 : 약 1,000 TPS => 60,000 TPM
  - 유저가 예약완료 까지 걸리는 시간: 평균 1분
  - 유저가 예약완료 까지 호출 API
    - 콘서트 날짜 조회, 콘서트 좌석 조회, 예약(3) * 재시도 및 중복 호출 계수(2) = 6
  - 처리가능한 동시 접속자 수: 10,000/분, 167/초
  - 최대 활성 토큰 개수 설정: 전환 주기(초) * 167

### 대기 토큰
- Sorted Sets 자료구조로 저장
    - key : `queue:wait`, value : `{token}`, score: 요청시간
- 대기열 입장 시 대기 토큰을 생성 후 저장(ZADD)
- 대기 순번 조회(ZRANK)

### 활성 토큰
- 만료시간 설정을 위한 Strings 자료구조로 저장
- Key는 `queue:proceed:{token}`으로 하고 만료시간 5분

### 토큰 활성화
1. 활성화 가능한 토큰 개수 확인(최대 활성화 토큰 개수 - 현재 활성화 된 토큰)
2. 활성 가능 개수 만큼 대기 토큰 제거(ZPOPMIN)
3. 활성화 토큰 저장(SET), TTL: 5m

### 활성 토큰 만료
- 예약 완료 시 활성 토큰 제거(DEL)
- 만료시간 지나면 자동 제거

---

## 성능 개선

### 인덱스 설정
- 25만 건 더미데이터 활용

#### 예약 가능한 좌석 리스트 조회
```sql
select *
from seat
where performance_schedule_id = {id} and status = 'OPEN';
```

#### 카디널리티 수치 확인
```sql
SELECT
    CONCAT(ROUND(COUNT(DISTINCT id) / COUNT(*) * 100, 2), '%') AS id_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT performance_schedule_id) / COUNT(*) * 100, 2), '%') AS performance_schedule_id_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT price) / COUNT(*) * 100, 2), '%') AS price_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT seat_no) / COUNT(*) * 100, 2), '%') AS seat_no_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT status) / COUNT(*) * 100, 2), '%') AS status_cardinality
FROM seat;
```
![cardinality](docs/images/cardinality.png)

#### 인덱스 생성
```sql
create index idx_performance_schedule_id_status on seat(performance_schedule_id, status);

show index from seat;
```
![index](docs/images/index.png)

#### 실행 계획
```sql
explain
select *
from seat
where performance_schedule_id = {id} and status = 'OPEN';
```

[ 인덱스 생성 전 ]
![explain](docs/images/explain_before.png)

[ 인덱스 생성 후 ]
![explain](docs/images/explain_after_1.png)
![explain](docs/images/explain_after_2.png)

#### Profiling
```sql
show profile cpu for query {query_id};
```

![profiling](docs/images/profiling.png)
- 성능 개선 : `0.084201` -> `0.002176`

---

## 동시성 제어

### 포인트 충전/사용

- 포인트 충전/사용의 경우 유저 별로 일어나기 때문에 충돌은 적을 것으로 예상된다.
- 하지만 동시 요청이 일어날 경우 실패 보다는 순차 처리가 되는 것이 좋다.

-> 비관적 락을 이용하여 포인트를 변경한다.

### 좌석 예약

- 좌석은 예약 가능/불가능 두가지 상태가 있다.
- 동시에 예약을 시도해서 한 명이 성공 하면 나머지 작업은 실패한다.
- 실패 후 재시도 또는 후처리가 필요하지 않다.
- 좌석의 예약 충돌이 많이 일어날 것으로 예상된다.

-> 비관적 락을 이용하여 좌석 상태를 변경한다.

### 예약 좌석 결제

- 좌석에 대한 예약은 5분 동안 한 사람이 점유하고 있다.
- 동시 결제 요청이 일어날 일이 매우 적어 충돌이 일어날 확률이 적다.
- 만약 동시 결제 요청이 일어나면 한 번만 성공하고 나머지는 실패한다.
- 실패 후 재시도 또는 후처리가 필요하지 않다.
- 백 그라운드에서 만료된 예약들을 만료 상태로 변경 처리하는 배치 작업이 진행 중이다.

-> 낙관적 락을 이용하여 예약 상태를 변경한다.
