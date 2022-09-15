<h1>✈ T'way-air 클론코딩 ✈</h1><br>
Innovation-Camp Week7 _ 7조<br>
Frontend GitHub : https://github.com/lee123so/Clone-T-way-Front_end<br>
Backend GitHub : https://github.com/sparta-team7/t-way-clone<br><br>

<h2>✌️ 프로젝트 소개</h2>
T-way 항공 클론코딩<br>

<h2>📅 제작 기간<br></h2>
2022.09. 09 ~ 2022. 09. 15<br>
👪 구성원 & 담당<br>
김민식<br>
      1. ticket 조회 기능(소요시간 계산 및 파라미터로 입력 적용)<br>
            - 소요시간 계산  완료<br>
            - 파라미터 입력 완료<br>
            - 맴버쉽 기능 미완료(등급 설정 미완성)<br>
이주현<br>
      2. 탑승자 정보 입력 후 예약(완료)<br>
      3.  나의 예약(완료)<br<br>

서나연<br>
      4.OAuth2를 이용한 카카오톡 소셜 로그인 (완료)<br>
  
    
📗 와이어프레임<br>
![190314437-c1807b23-be87-4ce2-8296-24462a87803b](https://user-images.githubusercontent.com/110470208/190331379-252bae28-c832-438f-9c81-a636f29d122c.png)


📘 ERD<br>
<img width="835" alt="Untitled (1)" src="https://user-images.githubusercontent.com/110470208/190321522-2f341bf6-52d9-4b93-b6a1-fb93e3f362be.png">


📙 API<br>
![7조 api](https://user-images.githubusercontent.com/110470208/190321495-94bdaf44-4f72-4335-a58f-ba20df9bb632.JPG)
<h2>✨ 주요 기능</h2><br>
- 회원가입<br>
    - 기존 Tway와는 달리 회원가입 과정에서 userid,, password 정보를 직접 받아 회원등록을 처리합니다.<br>
- 메인페이지<br>
    - 예약은 안되지만 예쁘다.<br>
- 예약<br>
    - 현장결제만 지원하며 수화물이 고정되어있다. (선택항목을 줄여 더욱 간편하고 빠르다)<br>
    - 편도(도착지 제주도로 한정) 항공편만 예약이 가능하다<br>
- 예약 조회<br>
    - 구매 일자는 입력하지 않고 예약 번호로만 조회한다.<br><br>

<h2>👷 사용한 기술</h2><br>
Back-end : SPring;<br>
Front-end : React;<br><br>
<h2>😡 Trouble Shooting</h2><br>
Parameter 0 of constructor in com.example.intermediate.service.TicketService required a bean of type<br>
'com.example.intermediate.discount.RateDiscountPolicy' that could not be found.<br>
라는 오류가 생겼는데 RateDiscountPolicy에 @Component가 들어가지 않아서 생기는 오류였다. <br>
스프링은 컴포넌트 스캔을 통해 의존 관계를 주입해준다.<br>
의존관계가 주입된 Bean도 다른 Bean을 주입받을 수 있는데,<br>
컴포넌트 스캔은 @Component가 달려있는 Bean만 의존관계를 주입시켜줄 수 있다.<br>
