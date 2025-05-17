# SnapEvent - 세일 알림 웹 서비스

## 🛒 프로젝트 개요
SnapEvent는 사용자가 원하는 브랜드의 세일 정보를 실시간으로 받아볼 수 있는 서비스입니다.

📅 **개발 기간:** 2023.09 ~ 2024.05  
👥 **팀원:** 백엔드 2명, 프론트엔드 1명  
🔗 **GitHub Repository:** [SnapEvent](https://github.com/Team-3-SnapEvent)  
🔗 **Notion 문서:** [API 명세서 및 기획](https://www.notion.so/Team-3-23F-67780bfed1bb41c09b89e51a0190e516?pvs=21)  

---

### 🔧 프로젝트에서 맡은 작업

- **백엔드 기능 구현**
    - 회원정보 관련 기능 (JWT, OAuth2, Spring Security)
    - 게시판 관련 기능
    - 구독 관련 기능
- **프론트엔드 CSS 및 UI 컴포넌트 구성**

---

### 🛠 사용한 기술 스택

- `Spring Boot`, `JPA`, `Java`
- `Styled-Components`, `React`
- `AWS`, `Nginx`, `Linux`
- `MySQL`
- `Git / GitHub`

---

## 📌 초기 기획 및 설계

- **Miro를 활용한 마인드맵 작성** → 서비스 구조 기획
- **초기 Flow Chart, Wireframe 작성** → UI/UX 레이아웃 설계
- **ERD(Entity-Relationship Diagram) 작성** → 데이터베이스 구조 설계
- **Figma를 활용한 UI 구성** → 프론트엔드 개발 가이드라인 제공

## 🔍 데이터베이스 설계 (ERD)
![ERD Diagram](https://github.com/user-attachments/assets/cb75c3c6-82fc-4d73-bbb9-1c49bf6913a9)

### **Wireframe 및 UI 설계**
![Wireframe](https://github.com/user-attachments/assets/be34afc5-f558-4141-b241-837590acd8f8)

![UI](https://github.com/user-attachments/assets/f1b1fe90-d3f7-4a40-8e43-f8fae5b27e6f)

![UI](https://github.com/user-attachments/assets/00d0c905-edbd-4920-bd94-f6a1a069be1c)

![UI](https://github.com/user-attachments/assets/8cc4ea39-97b5-42ba-b259-626e7d9cc0cb)

### **초기 서비스 Flow Chart**
![Flow Chart](https://github.com/user-attachments/assets/21aca9a3-823a-400c-af84-abdb08b5c3af)

---

## 🔧 개발 단계

### 🏗 백엔드 기능 구현

🔗 [SnapEvent 백엔드 저장소](https://github.com/dahoon7151/SnapEvent-backend)

### **회원정보 관련 기능**

- **JWT 인증 방식 로그인 구현** (CSR 방식 적용)
- **Spring Security 필터 적용** → 토큰 인증 방식의 보안 취약점 보완
- Security Config 파일을 통한 Security 필터 관리
- **OAuth2 소셜 로그인** (Google, Naver, Kakao)
    - Spring Security OAuth2 라이브러리를 활용해 구현
- **회원 관련 기능 구현**: 토큰 재발급, 회원가입, 로그아웃, 회원탈퇴, 회원정보 수정

### **게시판 관련 기능**

- **페이징 처리**를 적용한 게시물 목록 반환 API 구현
- **AuthenticatedPrincipal 어노테이션**을 이용해 현재 사용자가 작성자인지 여부 확인
- 게시글 엔티티의 Like 칼럼(boolean 값)을 확인하여 좋아요 등록/취소 구현
- 게시글 및 댓글의 **작성, 수정, 조회, 삭제 기능** 구현

### **구독 관련 기능**

- 사용자가 원하는 브랜드를 구독할 수 있도록 구독 기능 구현
- 비회원 사용자의 구독 정보를 **로컬스토리지에 저장** 후 로그인 유도
    - 로그인 시 회원가입/로그인 API와 구독 API가 **동기식 처리**되도록 구현
- 구독 취소, 조회 및 팔로워의 구독 리스트 조회 기능 구현

---

### 🔀 Git Flow 전략

- `main / develop / feature` 브랜치 전략 적용, feature 브랜치를 develop으로 병합

---

### 📄 API 명세서 작성

🔗 [API 명세서 (Notion)](https://www.notion.so/API-596be2293efd489387810d9e81c4c4aa?pvs=21)

---

### 🎨 프론트엔드 개발 기여

🔗 [SnapEvent 프론트엔드 저장소](https://github.com/Team-3-SnapEvent/SnapEvent-frontend)

초기 계획에는 프론트엔드 개발이 포함되지 않았지만, 프로젝트 일정 문제로 프론트엔드 작업에도 참여함.

- **Yarn**을 사용하여 팀원과 동일한 개발 환경 구축 및 빌드, 배포 진행
- **Styled-Components**를 활용한 CSS 작성
- **UX 최적화**: 모달창, 버튼, 셀렉트 박스 등의 UI 컴포넌트 구현

---

### ☁️ 배포 및 서버 인프라 구축

### **도메인 및 서버 배포**

- **가비아에서 도메인 구입 후 AWS Route 53을 사용하여 EC2 인스턴스와 연결**
- **AWS RDS (MySQL) 생성 후 EC2와 연동**

### **Nginx 리버스 프록시 설정**

- `/api`로 시작하는 엔드포인트를 `localhost:8080`으로 연결
- SSL 인증서를 적용하여 **HTTPS 프로토콜 적용**

### **배포 구조**

- 백엔드와 프론트엔드를 각각 배포하여 유지보수성 향상
- 환경 변수 및 보안 설정을 통해 **안정적인 서비스 운영 가능**

---

![Image](https://github.com/user-attachments/assets/7fc26497-7c01-49d2-9887-db8eeab9c8c9)

![Image](https://github.com/user-attachments/assets/e603b74a-ef48-43e4-9628-e94ffba98c42)

---

**🎯 후기**

이 프로젝트를 1년간 진행하면서 백엔드 개발에 대해 자신감도 얻게 되었고 새로 공부하게 된것도 너무 많았지만, 무엇보다 백엔드 개발자라는 진로를 스스로 확고하게 할 수 있게 된것이 가장 뜻깊었습니다. 

 처음 웹개발을 배울때는 로그 작성의 중요성을 몰랐고,  학교에서 소프트웨어 공학 수업을 들을때는 왜 ERD나 UML을 작성하는지 그 필요성을 느끼지 못했습니다. 프로젝트를 끝내고 나니 비로소 이론적으로만 알고 있었던 것들의 필요성을 느낄 수 있었습니다.

 원래 프론트엔드 개발은 경험이 없었고 할 계획도 없었지만 시간을 더 투자해 프론트엔드 개발에도 참여했습니다. 다른 팀원들보다 비교적 더 많은 분량의 작업을 했지만 그 덕에 풀스택 개발 경험을 할 수 있어 뜻깊었습니다. 

누구보다도 적극적으로 프로젝트에 임했다는것이 스스로 칭찬하고 싶은 부분이고 익숙치 않은 기술이더라도 쉽게 받아들이고 일단 공부해서 해보려는 자세가 제 강점임을 확인할 수 있었습니다.
