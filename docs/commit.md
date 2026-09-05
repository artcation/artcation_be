**규칙**

```
<type>(<scope>): <subject>
```

예시:

```
feat(auth): 카카오 소셜 로그인 추가
```

`<type>`

- feat (feature)
- fix (bug fix)
- docs (documentation)
- style (formatting, missing semi colons, …)
- refactor
- test (when adding missing tests)
- chore (maintain)
- ci (CI/CD 설정 변경)

`<scope>`

(생략 가능)

- 패키지 기준

`주요 변경 강조`

- `!`를 사용해 브레이킹 변경 또는 중요한 변경을 강조한다.
- 예시:
    - `feat!: 회원가입 인증 메일에 고양이 사진을 미포함`
    - `feat(auth)!: 회원가입 인증 메일에 고양이 사진을 미포함`