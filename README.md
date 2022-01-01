# 스프링 핵심 원리
인프런 김영한님의 '스프링 핵심 원리' 강의를 학습한 프로젝트

## 관심사의 분리를 하지 않을 경우의 문제점

### OCP(개방-폐쇄 원칙) 위반
#### 예시) 주문 클라이언트 코드에서 할인 정책 변경시 발생하는 문제
순수 JAVA 코드로 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다! <br>
아래의 그림처럼 기대했던 관계가 아닌 구현체 클래스가 바뀔 때마다 클라이언트 코드에서 변경이 일어난다. 따라서 OCP를 위반한다.

#### 기대했던 의존 관계
![expect](https://user-images.githubusercontent.com/66157892/147846268-7ac12d0f-7bc8-4a83-aad2-31d73614ad2e.PNG)

#### 실제 의존 관계
![real](https://user-images.githubusercontent.com/66157892/147846269-244552fc-3ff5-4991-a0ad-b33960a6e510.PNG)

#### 정책 변경
![정책변경](https://user-images.githubusercontent.com/66157892/147846468-75cc6784-f173-49c4-8fd2-9d19add73068.PNG)<br>
FixDiscountPolicy 를 RateDiscountPolicy 로 변경하는 순간 OrderServiceImpl 의 소스 코드도 함께 변경해야 한다! OCP 위반

### DIP(의존관계 역전 원칙) 위반
아래의 코드를 보면 추상에 의존하여 DIP를 지키는 것 같지만 사실 추상 뿐만이 아니라 구현체에도 의존하고 있다(new RateDiscountPolicy) <b>따라서 DIP 위반</b>

  <b>private final DiscountPolicy discountPolicy = new RateDiscountPolicy();</b>


DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존관계를 변경하면 된다.

### 해결법
이 문제를 해결하려면 누군가가 클라이언트인 OrderServiceImpl 에 DiscountPolicy 의 구현 객체를 대신 생성하고 주입해주어야 한다.

## 관심사의 분리
![메ㅔ채](https://user-images.githubusercontent.com/66157892/147846611-de1573ab-99fd-4742-a0b9-df6a3148bb01.PNG)<br>
위와 같이 외부에 설정 클래스를 두어 생성자로 주입을 해주면  DIP와 OCP를 지킬 수 있다.
### 클래스 다이어 그램
![qwe](https://user-images.githubusercontent.com/66157892/147846625-d429a516-5430-4c79-bdb5-c6e53f5cd606.PNG)

## 제어의 역전 IoC(Inversion of Control)
- AppConfig가 등장한 이후에 구현 객체는 자신의 로직을 실행하는 역할만 담당한다. 프로그램의제어 흐름은 이제 AppConfig가 가져간다.<br>
- 예를 들어서 OrderServiceImpl은 필요한 인터페이스들을 호출하지만 어떤 구현 객체들이 실행될지 모른다.프로그램에 대한 제어 흐름에 대한 권한은 모두 AppConfig가 가지고 있다. 심지어 OrderServiceImpl
도 AppConfig가 생성한다. 그리고 AppConfig는 OrderServiceImpl 이 아닌 OrderService 인터페이스의 다른 구현 객체를 생성하고 실행할 수 도 있다. 그런 사실도 모른체 OrderServiceImpl 은 묵묵히 자신의 로직을 실행할 뿐이다.<br>
- 이렇듯 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 <b>역전(IoC)</b>이라한다.<br>
- AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 <b>IoC 컨테이너 또는 DI 컨테이너</b>라 한다

## 스프링 컨테이너
- ApplicationContext 를 스프링 컨테이너라 한다.
- 스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용한다. 여기서 @Bean이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다. 이렇게 스프링 컨테이너에
등록된 객체를 <b>스프링 빈</b>이라 한다.
- 스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다. ( memberService ,
orderService )
- 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있다.

## 싱글톤 컨테이너
싱글톤 패턴: 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴이다
- 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤
레지스트리라 한다.
- 스프링 컨테이너의 이런 기능 덕분에 싱글턴 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수
있다

## @Configuration, @Bean과 싱글톤
- 설정 클래스에 @Bean이 붙은 메서드는 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다!
- 클래스 레벨의 @Configuration 없이 @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다.

## 컴포넌트 스캔과 의존관계 자동 주입 시작하기
- @ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록한다.
- 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.<br>
     빈 이름 기본 전략: MemberServiceImpl 클래스 memberServiceImpl.<br>
     이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면 @Component("memberService2") 이런식으로 이름을 부여하면 된다
     
## 의존관계 자동 주입
![wd](https://user-images.githubusercontent.com/66157892/147847141-8ccfd93e-1863-41d8-83d7-8361e30515f1.PNG)<br>
클라이언트 코드의 생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.

## 다양한 의존관계 주입
- <b>생성자 주입</b>
  생성자가 딱 1개만 있으면 @Autowired를 생략해도 자동 주입 된다. 물론 스프링 빈에만 해당한다.
- 수정자 주입(setter 주입)
- 필드 주입
- 일반 메서드 주입
### 롬복 @RequiredArgsConstructor를 사용해 생성자 주입을 선택하자! 

## 스프링 빈의 이벤트 라이프사이클
스프링 컨테이너 생성 --> 스프링 빈 생성 --> 의존관계 주입 --> 초기화 콜백 --> 사용 --> 소멸전 콜백 --> 스프링 종료

## @PostConstruct, @PreDestroy 애노테이션 특징
- @PostConstructor: 초기화 콜백, 의존관계 주입 후 해당 빈을 초기화
- @PreDestroy: 소멸전 콜백, 스프링 종료 직전 호출

## 빈 스코프란
### 웹 관련 스코프
- request: 웹 요청이 들어오고 나갈때 까지 유지되는 스코프이다.
- session: 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프이다.
- application: 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프이다.
### 프로토타입 스코프
싱글톤 스코프의 빈을 조회하면 스프링 컨테이너는 항상 같은 인스턴스의 스프링 빈을 반환한다. 반면에
프로토타입 스코프를 스프링 컨테이너에 조회하면 스프링 컨테이너는 항상 새로운 인스턴스를 생성해서
반환한다.
![trx](https://user-images.githubusercontent.com/66157892/147847571-fb6edb3b-a7a5-4f25-8d56-463d0e4d2412.PNG)<br>
1. 싱글톤 스코프의 빈을 스프링 컨테이너에 요청한다.
2. 스프링 컨테이너는 본인이 관리하는 스프링 빈을 반환한다.
3. 이후에 스프링 컨테이너에 같은 요청이 와도 같은 객체 인스턴스의 스프링 빈을 반환한다.

![vxd](https://user-images.githubusercontent.com/66157892/147847572-5d639bc7-9155-4512-af7a-a01777974a4a.PNG)<br>
1. 프로토타입 스코프의 빈을 스프링 컨테이너에 요청한다.
2. 스프링 컨테이너는 이 시점에 프로토타입 빈을 생성하고, 필요한 의존관계를 주입한다.

