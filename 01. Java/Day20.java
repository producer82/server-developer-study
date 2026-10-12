interface Engine {
    void start();
}

class GasolineEngine implements Engine{
    @Override
    public void start() {
        System.out.println("휘발유 엔진 시동");
    }
}

class ElectricEngine implements Engine {
    @Override
    public void start() {
        System.out.println("전기 엔진 시동");
    }
}

class Car {
    private final Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void start() {
        engine.start();
    }
}

interface Payment {
    void pay(int amount);
}

class KakaoPay implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("카카오페이 결제: " + amount);
    }
}

class TossPay implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("토스페이 결제: " + amount);
    }
}

class OrderService {
    private Payment payment;

    OrderService(Payment payment) {
        this.payment = payment;
    }

    void order(int amount) {
        payment.pay(amount);
    }
}

public class Day20 {
    public static void main(String[] args) {
        // DIP (Dependency Inversion Principle, 의존성 역전 원칙)
        // 상위 수준의 모듈은 하위 수준의 모듈에 직접 의존하지 말고, 둘 다 추상화에 의존해야 한다.
        // 상위 수준 모듈: 핵심적인 기능을 담당하는 코드
        // 하위 수준 모듈: 구체적인 구현을 담당하는 코드
        // 추상화: 인터페이스나 추상 클래스처럼 공통 규칙을 표현하는 것
        Car car1 = new Car(new ElectricEngine());
        Car car2 = new Car(new GasolineEngine());
        // 자동차가 움직이는 것 -> 핵심 기능
        // 자동차를 이루고 있는 것 -> 구체적인 구현
        car1.start();
        car2.start();
        // 상위 수준, 하위 수준 모듈 모두 인터페이스에 의존하고 있다.
        // DIP를 준수한 자동차는 핵심 기능이 구체적인 구현에 제약을 받지 아니하기에
        // 엔진을 쉽게 갈아낄 수 있다.

        // DIP와 의존성 주입(DI)
        // 두 용어는 함께 등장하는 경우가 많지만, 같은 용어가 아니다.
        // DIP: 모듈의 의존성에 관한 설계 원칙
        // DI: 필요한 의존성을 외부에서 주입하는 방식
        // 즉, DIP 원칙을 구현하기 위한 방법 중 하나 -> 의존성 주입(DI)

        // DIP와 OCP
        // OCP: 확장성은 확보하되 핵심 코드의 수정을 최소화 한다.
        // DIP: 핵심 코드가 추상화에 의존하도록 한다.
        // DIP를 잘 사용하면 OCP를 지키는 데에 도움이 될 수 있다.
        // 위의 예제에서도 본래 Engine을 교체하기 위해 Car를 수정해야만 했지만,
        // 추상화를 통해 핵심 코드를 수정할 필요가 없어졌다.
        
        // 구현이 다양해질 가능성, 변경 가능성, 추상화가 제공하는 이점을 잘 고려하여
        // 적재적소로 설계하는 것이 중요하다.

        // 1. 다음 코드를 살펴보자
        // class KakaoPay {
        //     void pay(int amount) {
        //         System.out.println("카카오페이 결제: " + amount);
        //     }
        // }
        //
        // class OrderService {
        //     private KakaoPay payment = new KakaoPay();
        //
        //     void order(int amount) {
        //         payment.pay(amount);
        //     }
        // }
        //
        // Q. 이 설계에서 OrderService는 어떤 클래스에 직접 의존하고 있는가?
        // A. KakaoPay 클래스에 의존하고 있다.
        // Q. 새로운 결제 수단인 TossPay를 추가하면 어떤 문제가 발생하는가?
        // A. OrderService의 핵심 코드를 변경해야만 한다.
        //
        // 2. 추상화 설계를 적용하여 코드를 수정해보자
        OrderService order1 = new OrderService(new KakaoPay());
        order1.order(10000);

        OrderService order2 = new OrderService(new TossPay());
        order2.order(20000);
    }   
}
