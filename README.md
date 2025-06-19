# 금융 상품 추천 애플리케이션

## 주제
사용자의 나이, 성별, 직업 정보를 바탕으로 적합한 예금/적금 상품을 추천해주는 데스크톱 프로그램입니다.

## 개요
이 프로그램은 Java Swing과 H2 데이터베이스를 이용해 간단한 금융 추천 시스템을 구현한 프로젝트입니다.  
회원가입과 로그인을 통해 사용자 정보를 등록하고, 입력된 정보를 기반으로 적합한 금융 상품을 추천해줍니다.

적금/예금 상품 데이터는 실제 시중은행 상품을 기반으로 하며, 초기 실행 시 자동으로 DB에 삽입됩니다.  
로컬 환경에서 실행되며, 여러 사용자 간 데이터 공유가 가능하도록 파일 기반 데이터베이스를 사용합니다.

## 주요 기능

- 회원가입 및 로그인 기능
    - 사용자 정보(나이, 성별, 직업) 입력
    - 비밀번호는 해싱(SHA-256 + salt) 처리
- 사용자 조건에 따른 금융 상품 추천
- 상품 상세 정보 카드 형태로 제공
- 상품 금리와 기간에 따른 예상 수익 확인
- H2 파일 기반 DB 사용 (프로그램 종료 후에도 데이터 유지)


## ERD 구조

### users 테이블
- id: INT (PK)
- username: VARCHAR (unique)
- password_hash: VARCHAR
- salt: VARCHAR
- age: INT
- gender: VARCHAR
- job: VARCHAR

### products 테이블
- id: INT (PK)
- name: VARCHAR
- type: VARCHAR (예: saving / deposit)
- interest_rate: DOUBLE
- period_months: INT
- target_age_min: INT
- target_age_max: INT
- gender: VARCHAR
- job: VARCHAR

## 디렉토리 구조 예시

```
src/
├── bank/io/MainApp.java
├── db/H2Database.java
├── view/LoginView.java, RegisterView.java, MainView.java ...
├── controller/
├── model/
├── service/
└── util/
```

[//]: # (## 향후 개선 예정)

[//]: # (- 추천 결과 정렬/필터 기능)

[//]: # (- 사용자 가입 상품 기록)

[//]: # (- 수익 계산 결과 시각화)
