# 🚗 BestEV

전기차 충전소 위치 및 상태를 한눈에 확인할 수 있는 안드로이드 애플리케이션입니다.

---

## 📌 프로젝트 소개

**BestEV**는 공공데이터 포털의 전기차 충전소 Open API를 기반으로  
사용자 주변 및 전국의 전기차 충전소 위치와 충전기 상태를  
지도에서 직관적으로 확인할 수 있도록 제작된 안드로이드 앱입니다.

네이버 지도 SDK를 활용하여 실제 지도 환경에서 충전소 마커를 표시하며,  
충전기 타입, 운영 상태, 주소, 최근 상태 변경 시간 등을 한 화면에서 확인할 수 있습니다.

---

## ✨ 주요 기능

- 전기차 충전소 위치 지도 표시 (네이버 지도)
- 충전소 상태별 마커 아이콘 표시
    - 대기중
    - 충전중
    - 통신이상
    - 시스템 점검
- 마커 클릭 시 충전소 상세 정보 표시
    - 충전소 이름
    - 주소
    - 충전기 타입 (DC차데모, DC콤보, AC3상, 완속 등)
    - 최근 상태 변경 시간
- 하단 슬라이딩 패널(SlidingUpPanelLayout)을 이용한 상세 정보 UI
- 주소 복사 기능
- 로그인 / 회원가입 기능

---

## 🖼️ 화면 구성

### 1️⃣ 로그인 페이지
![로그인 페이지](images/login.png)

### 2️⃣ 회원가입 페이지
![회원가입 페이지](images/register.png)

### 3️⃣ 메인 페이지 (지도 화면)
![메인 페이지](images/main.png)

### 4️⃣ 마커 클릭 시 상세 정보 화면
![마커 클릭](images/marker_detail.png)

### 5️⃣ 스크롤링 시 상세 정보 화면
![스크롤 화면](images/scroll.png)

---

## 🔐 API Key 관리

보안을 위해 API Key는 GitHub 저장소에 직접 포함하지 않습니다.

- `local.properties` 파일에 API Key를 저장합니다.
- 해당 파일은 `.gitignore`에 포함되어 GitHub에 업로드되지 않습니다.

예시:

EV_API_KEY=YOUR_API_KEY

Gradle에서 해당 값을 참조하여 BuildConfig로 사용합니다.

---

## 📂 프로젝트 구조

com.ysc.bestev  
├── Data  
│   ├── DatabaseOpenHelper  
│   ├── LoginActivity  
│   ├── LoginRequest  
│   ├── RegisterActivity  
│   ├── RegisterRequest  
│   ├── UserInformation  
│   └── ValidateRequest  
│  
├── ui  
│   ├── gallery  
│   │   └── GalleryFragment  
│   ├── home  
│   │   └── HomeFragment  
│   └── slideshow  
│       └── SlideshowFragment  
│  
├── ev_api  
├── ev_item  
└── MainActivity

---

## 🛠️ 사용 기술

- Language: Java
- UI: Fragment, SlidingUpPanelLayout
- Map: Naver Map SDK
- Network: XML Pull Parser, Open API
- Data Source: 공공데이터포털 전기차 충전소 Open API
- Version Control: Git / GitHub

---

## 🚀 향후 개선 사항

- 네트워크 통신 비동기 처리 구조 개선 (ANR 방지)
- API 호출 구조 Retrofit 기반으로 리팩토링
- 즐겨찾기 충전소 기능 추가
- 충전소 필터링 기능 (타입 / 상태별)
- UI/UX 개선 및 애니메이션 추가

---

## 🎯 개발 목적

본 프로젝트는 안드로이드 앱 개발 학습을
목표로 제작되었습니다.

지도 SDK 활용, 공공 API 연동,  
Fragment 기반 UI 구성 및 상태 관리에 중점을 두었습니다.