class Car {
    private final Engine engine;
    private final String name;

    Car(String name, Engine engine) {
        this.name = name;
        this.engine = engine;
    }

    void start() {
        System.out.println(name + engine.start());
    }

    void speed() {
        System.out.println("최고속력: " + engine.getMaxSpeed());
    }
}

class Engine {
    private int maxSpeed;

    Engine(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    String start() {
        return "시동";
    }

    int getMaxSpeed() {
        return maxSpeed;
    }
}

class PaymentService {
    private final Payment payment;

    PaymentService(Payment payment) {
        this.payment = payment;
    }

    void pay(int amount) {
        payment.pay(amount);
    }
}

interface Payment {
    void pay(int amount);
}

class CardPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("카드 결제 " + amount);
    }
}

class KakaoPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("카카오 결제 " + amount);
    }
}

public class Day15_1 {
    public static void main(String[] args) {
        // 의존성 주입(Dependency Injection)
        // 다음 A와 B중 무엇이 유연한 설계일까?
        // A. Car 생성자를 통해 엔진 전달
        // B. Car 클래스 내부에서 엔진 생성
        // 답은 A다. A처럼 설계하면 생성하는 쪽에서 무슨 엔진을 넣을지 선택할 수 있다.
        // 즉, 같은 Car 클래스라도 여러가지 자동차를 만들 수 있다.
        // B처럼 설계하면 모든 Car가 같은 엔진을 가지게 되고, 엔진을 바꾸려면
        // Car 클래스를 수정해야만 한다.
        Engine suv = new Engine(150);
        Engine sports = new Engine(300);
        Car suvCar = new Car("소나타", suv);
        Car sportsCar = new Car("람보르기니", sports);
        suvCar.speed();
        sportsCar.speed();
        // 이걸 의존성 주입(DI)라고 한다.
        // Car는 자동차 기능을 수행하기 위해 Engine에 의존한다.
        // 이 Engine을 유연함을 위해 외부에서 주입받는 형태로 설계한다.
        // 다양한 엔진 전달, 테스트 용이, 엔진과 자동차의 책임 분리가 장점으로 작용한다.
        
        // 의존성 역전 원칙(Dependence Inversion Principle)
        // 구체적인 클래스 대신, 변하지 않는 추상체에 의존하라는 설계 원칙이다.
        // Q. Car는 현재 구체적인 하나의 Engine에 의존하고 있다.
        // 나중에 여러 종류의 Engine을 사용하고 싶다면 어떻게 설계하는게 좋을까?
        // A. Engine을 Interface로 만들고, 서로 다른 엔진이 Interface를 구현하게 한다.
        // 이제 다형성을 이용해 여러가지 Engine 구현체를 Car에 장착할 수 있다.
        // 핵심은?: Car가 엔진의 구체적인 구현 방식은 몰라도, 제공 받아야할 기능은 알고 있다.

        // 실제 백엔드 개발에서는?
        // 결제 서비스를 개발한다고 가정할 때, PaymentService라는 객체를 생각해보자.
        PaymentService payment1 = new PaymentService(new KakaoPayment());
        PaymentService payment2 = new PaymentService(new CardPayment());
        payment1.pay(2000);
        payment2.pay(3000);
        // PaymentService가 직접 결제수단을 생성하는 대신, Payment 인터페이스를 통해
        // 외부에서 결제수단을 주입받도록 만들면 여러 결제 수단을 지원하기 쉬워진다.
        // interface, 다형성, composition이 모두 함께 쓰여서 이런 구현을 만들어냈다.

        // 이러한 확장의 간편성이 OCP 원칙으로 확장된다.
        // OCP (개방-폐쇄 원칙)
        // 확장에는 열려 있고, 변경에는 닫혀 있다.
        // 확장하기는 쉽지만, 이 과정에서 기존 소스 코드나 클래스는 최대한 수정하지 않아야 한다.
    }
}

