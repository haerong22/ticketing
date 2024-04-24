### git-flow 전략

`main`
- 운영 환경에 배포 중인 버전의 branch

`release/{version}`
- 새로운 버전 개발 완료 후 배포 전 QA 환경에 배포되는 branch
- `develop` branch 에서 분기 되어 생성 된다.
- QA 중 발생한 버그 수정은 해당 branch 에서 이어져 수행 한다.
- 테스트 완료 후 배포 시 `main` branch 로 merge 되고 변경 사항이 있다면 `develop` branch 로 merge

`develop`
- 개발 환경에 배포되는 branch
- `main` branch 에서 분기 되어 생성 된다.

`feature/`
- 각 기능 개발을 위한 작업을 수행하는 branch
- 기능 개발 완료 후 `develop` branch 로 merge 된다.

`hotfix`
- 운영 중 크리티컬 이슈가 발생하여 신속한 수정이 필요한 경우 사용하는 branch
- `main` branch 에서 분기되어 생성 된다.
- 수정 완료 후 `main`, `develop` branch 로 merge 된다.
