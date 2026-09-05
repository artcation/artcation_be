# Artcation BE

## 프로젝트 설정

### 1. 클론

```bash
git clone https://github.com/artcation/artcation_be.git
cd artcation_be
```

### 2. Git Hooks 활성화

```bash
git config core.hooksPath .githooks
```

### 3. 빌드

```bash
./gradlew build
```

## 기술 스택

| 항목 | 버전 |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.0 |

## 의존성

| 라이브러리 | 설명 |
|---|---|
| spring-boot-starter-data-jpa | JPA / Hibernate |
| spring-boot-starter-webmvc | Spring MVC |
| spring-boot-starter-validation | Jakarta Validation (`@NotNull`, `@Size` 등) |
| springdoc-openapi-starter-webmvc-ui | Swagger UI / OpenAPI 3 문서 |
| Lombok | 보일러플레이트 코드 제거 (`@Getter`, `@Builder` 등) |
| MySQL Connector/J | MySQL 드라이버 |
| spring-boot-devtools | 개발 시 자동 재시작 |

## Git Hooks

`git config core.hooksPath .githooks` 으로 활성화합니다.

| 훅 | 동작 |
|---|---|
| pre-commit | `spotlessCheck` — 코드 포맷 검사 |
| commit-msg | 커밋 메시지 컨벤션 검증 |
| pre-push | `checkstyleMain checkstyleTest` — 정적 분석 |

커밋 메시지 형식: `type(scope): 제목`
타입: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`, `ci`

포맷 오류 시: `./gradlew spotlessApply`
