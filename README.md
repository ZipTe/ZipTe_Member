# 1. Project Overview (프로젝트 개요)
- 프로젝트 이름: ZIPTE 유저 서비스
- 프로젝트 설명: 헥사곤 아키텍쳐 기반 MSA 프로젝트 중 유저 서비스 담당 
- 프로젝트 기간 : 2025.04.01 ~ (1인 개발)

# 2. Team Member (팀원 소개)
| 이도연 |
|:------:|
| <img src="https://github.com/user-attachments/assets/653c94e3-5837-4e40-8ee9-b0ff135b59e7" alt="이도연" width="150"> | 
| BE |
| [GitHub](https://github.com/doup2001) | 


# 3. Key Features (주요 기능)
- **회원가입**:
   - Oauth2(네이버, 카카오, 애플, 구글) 기반 회원가입
   - 최초 회원가입시 리액트에서 추가적으로 정보기입 후 회원가입 진행.

- **인가**:
   - JWT 토큰을 통해 유저의 정보가 맞는지 체크.
   - 역할별 접근가능한 페이지 존재

- **로그인**:
  - 기존 회원가입한 멤버는 소셜 로그인 정보를 통해 바로 로그인합니다.
  - 회원정보 수정 가능

# 4. Technology Stack (기술 스택)
## 5. Backend
|  |  |  |
|-----------------|-----------------|-----------------|
| SpringBoot    |  <img src="https://github.com/user-attachments/assets/43d80a85-2060-4475-95a8-fc402f837aa0" alt="SpringBoot" width="100">    | 3.2.4  |
| SpringSecurity    |  <img src="https://github.com/user-attachments/assets/7eafc435-a1b9-47ab-bbc2-6ac02d21a4f0" alt="SpringSecurity" width="100">    |  |
| JWT | <img src= "https://github.com/user-attachments/assets/659ebd31-4021-4ef9-9e04-3340343903f8" alt="SpringBoot" width="100"> | 11.5 |
| OAuth2 | <img src ="https://github.com/user-attachments/assets/9b3cf7a3-628f-415b-a96f-eacdcfb53078" alt="SpringBoot" width="100"> | |
| Kafka | <img src = "https://github.com/user-attachments/assets/841067aa-94e3-4766-b9f0-d7e721a64835" alt="SpringBoot" width="100"> | |
| Docker | <img src = "https://github.com/user-attachments/assets/f4646cfa-43f7-4e3d-8d07-a35bb89ccdad" alt="SpringBoot" width="100"> | |


## 6. Cooperation
|  |  |
|-----------------|-----------------|
| Git    |  <img src="https://github.com/user-attachments/assets/483abc38-ed4d-487c-b43a-3963b33430e6" alt="git" width="100">    |
| Notion    |  <img src="https://github.com/user-attachments/assets/34141eb9-deca-416a-a83f-ff9543cc2f9a" alt="Notion" width="100">    |


# 7. Project Structure (프로젝트 구조)
```
  └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── zipte
        │   │           └── member
        │   │               ├── MemberApplication.java
        │   │               ├── core
        │   │               │   ├── config
        │   │               │   ├── exception
        │   │               │   ├── response
        │   │               │   │   └── pageable
        │   │               │   └── util
        │   │               ├── security
        │   │               │   ├── jwt
        │   │               │   │   ├── domain
        │   │               │   │   ├── exception
        │   │               │   │   ├── handler
        │   │               │   │   ├── provider
        │   │               │   │   ├── service
        │   │               │   │   └── util
        │   │               │   └── oauth2
        │   │               │       ├── domain
        │   │               │       ├── handler
        │   │               │       └── service
        │   │               └── server
        │   │                   ├── adapter
        │   │                   │   ├── in
        │   │                   │   │   └── web
        │   │                   │   │       └── dto
        │   │                   │   └── out
        │   │                   │       └── jpa
        │   │                   ├── application
        │   │                   │   ├── in
        │   │                   │   │   ├── auth
        │   │                   │   │   └── user
        │   │                   │   ├── out
        │   │                   │   │   └── user
        │   │                   │   └── service
        │   │                   └── domain
        │   │                       ├── BaseDomain.java
        │   │                       └── user
        │   └── resources
        │       ├── application-dev.yml
        │       ├── application-oauth2.yml
        │       ├── application-test.yml
        │       ├── application.yml
```

# 8. Development Workflow (개발 워크플로우)
## 브랜치 전략 (Branch Strategy)
우리의 브랜치 전략은 Git Flow를 기반으로 하며, 다음과 같은 브랜치를 사용합니다.

- Main Branch
  - 배포 가능한 상태의 코드를 유지합니다.
  - 모든 배포는 이 브랜치에서 이루어집니다.
 
- Devlop Branch
  - 만든 기능들이 작동하는지 코드를 합병합니다.
  
- {feat} Branch
  - 모든 기능 개발은 feat 브랜치에서 이루어집니다.

# 9. 커밋 컨벤션

## type 종류
```
feat : 새로운 기능 추가
fix : 버그 수정
docs : 문서 수정
style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
refactor : 코드 리펙토링
test : 테스트 코드, 리펙토링 테스트 코드 추가
chore : 설정 추가
```

# 10. ERD
