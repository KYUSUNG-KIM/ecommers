# ecommers

## 📋 개요
최근 몇 년 간의 이커머스 붐은 쇼핑의 편리함을 다시 한번 각인시켰습니다. 쿠팡, 지마켓, 마켓컬리 같은 대형 이커머스 사이트들이 이 분야에서 선두를 달리고 있죠. 이런 플랫폼들이 제공하는 다양하고 편리한 서비스를 '그럼, 나도 만들 수 있지 않을까?' 하는 가벼운 호기심에서 시작했습니다. 해당 프로젝트는 바로 그런 생각에서 출발한, Spring Boot를 기반의 소규모 백엔드 이커머스 프로젝트입니다. 주된 목적은 새로운 기술을 탐색하고, 이커머스 시스템의 백엔드 구조를 이해하는 것입니다. 또한, 개인적인 코딩 스킬을 향상 시키고, 현대적인 웹 애플리케이션 개발의 전반적인 흐름을 경험하는 것도 중요한 목표 중 하나 입니다.


## 적용 기술
- JAVA 17
- Spring Boot, Spring Security, Spring Cloud(진행중)
- JPA, Querydsl
- MariaDB
- Redis(예정)
- Kafka(예정)
- ...


## 아키텍처
TO-DO...


## 프로젝트 진행 계획
- [x] 1. 모놀로식 아키텍처 구성
- [x] 2. MSA
- [ ] 3. CI/CD 구축 (github action 적용)
- [ ] 4. ...


## 기능 정의서

### ✔️ 기본 기능
<details>
<summary>기본 기능 펼치기</summary>
<div markdown="1">
</br>

***회원***
- [x] 회원 가입
- [x] 로그인
- [x] 내 정보 조회

***상품(공통)***
- [x] 상품 조회
- [x] 상품 목록 검색
- [x] 전체 카테고리 조회

***상품(리셀러)***
- [x] 상품 등록
- [x] 상품 수정
- [x] 상품 삭제

***상품(관리자)***
- [ ] 카테고리 등록

***주문***
- [x] 주문하기
- [x] 주문 목록 조회
- [x] 주문 상세 조회

</br>

</div>
</details>

### 🔍 추가 기능
<details>
<summary>추가 기능 펼치기</summary>
<div markdown="1">
</br>

- [ ] 할인 쿠폰
- [ ] 상품 조회 고도화 > 고객에 따른 최고 할인 금액으로 표시(쿠폰 적용가 또는 프로모션 가격 등)
- [ ] 제품 검색(Elastic Search 적용)
- [ ] 제품 통계 및 분석
- [ ] 이벤트 및 경품 추첨(대량 트래픽 처리)
- [ ] ...

</br>
</div>
</details>


## API 정의서
TO-DO...


## DB 구성도

### 1차 DB구성도

<details>
<summary>1차 DB구성도 펼치기</summary>
<div markdown="1">
</br>
<p align="center">
  <img src="https://github.com/KYUSUNG-KIM/ecommers/assets/37990443/8abc5773-2e94-4b8a-9366-75705519e3de">
</p>

</br>
</div>
</details>
