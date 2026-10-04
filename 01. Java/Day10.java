// 인터페이스 메서드는 제공해야할 기능만 정한다.
interface Payment {
    void pay(int amount);

    // default를 붙이면 공통적으로 사용되는 메서드를 구현까지 가능하다.
    default void test() {
        System.out.println("결제 시험 중");
    }
}

interface Fly {
    void fly();
}

interface Swim {
    void swim();
}

interface Shape {
    double area();
}

class Circle implements Shape {
    int r;
    
    Circle(int r) {
        this. r = r;
    }

    @Override
    public double area() {
        return 3.14 * r * r;
    }
}

class Rectangle implements Shape {
    int width;
    int height;
    
    Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return (double)width * height;
    }

}

class Bird implements Fly, Swim {
    @Override
    public void fly() {
        System.out.println("날아갑니다.");
    }

    @Override
    public void swim() {
        System.out.println("수영도 합니다.");
    }
}

class Airplane implements Fly {
    @Override
    public void fly() {
        System.out.println("비행기가 날아갑니다.");
    }
    
}

class PaymentService {
    void process(Payment payment, int amount) {
        payment.pay(amount);
    }
}

class CardPayment implements Payment {
    @Override 
    // 왜 public이 붙지 않으면 오류가 발생할까?
    // 인터페이스의 접근 제어자는 public 아니면 privated 밖에 존재하지 않는다.
    // 아무것도 붙이지 않는 경우에는 기본적으로 public으로 취급된다.
    // 따라서 구현 클래스에서는 접근 수준이 같거나 더 넓어야 한다.

    // 그럼 왜 public이어야만 할까?
    // 인터페이스의 본질이 "이 객체는 외부에 이런 기능들을 제공한다"라는 공공 규격이기 때문.
    // 이걸 private로 막아버리면 패키지 외부에서 볼 수가 없으니 구현이 불가능하다.
    // package-privated의 경우에는 인터페이스의 목적에 대한 직관성을 높이기 위해 막아버렸다.
    public void pay(int amount) {
        System.out.println("카드로 " + amount + "원 결제");
    }
}

class TossPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("토스로 " + amount + "원 결제");
    }
}

public class Day10 {
    public static void main(String[] args) {
        // 지금까지는 상속을 통해 클래스들이 같은 기능을 공유할 수 있도록 만들었다.
        // 근데 상속은 필요 없는데, 다른 클래스가 같은 기능을 반드시 제공하도록 하고 싶다.
        // 어떻게 할까?

        // 한 시스템의 예를 들면,...
        // 결제 방법은 카드, 계좌, 카카오페이, 토스페이 등 여러가지가 있을 수 있다.
        // 하지만 공통적으로 "결제한다"라는 기능은 꼭 필요하다.
        // 이것들을 상속으로 해결하려고 하면 문제가 커진다.

        // 이것들은 결제의 수단(Method)이지 Payment 그 자체 되야하는 것은 아니다.
        // Payment가 Pay를 할 수 있어야 하는 것이지, Payment is a Pay는 아닌 것이다.

        // 인터페이스 (Interface)
        // 클래스가 반드시 지켜야하는 규칙을 정의하는 것이다.
        // 이것은 흔히 can do 관계로 나타내며, A can do B의 형태로 사용한다.
        // 인터페이스 또한 부모-자식 타입처럼 다형성을 사용할 수 있다.
        Payment cardPayment = new CardPayment();
        Payment tossPayment = new TossPayment();
        cardPayment.pay(2000);
        tossPayment.pay(3000);
        // Payment can do TossPayment
        
        Payment[] payments = {
            new CardPayment(),
            new TossPayment(),
            new CardPayment()
        };
        for (Payment payment : payments) {
            payment.pay(10000);
        }

        // 공통 구현 메서드는 default로 정의 가능하다.
        cardPayment.test();
        tossPayment.test();

        // 또한, 보통 무엇을 할 수 있는지를 표현한다고도 볼 수 있다.
        // 비행기와 새는 둘 다 날 수 있다는 공통점이 있다.
        Fly airplane = new Airplane();
        Fly bird = new Bird();
        airplane.fly();
        bird.fly();

        // 상속과 다르게 인터페이스는 하나의 클래스가 여러개를 구현할 수 있다.
        // 다만 다형성을 사용할 경우 interface에서 찾을 수 없는 메서드는 사용할 수 없다.
        Bird bird2 = new Bird();
        bird2.swim();
        bird2.fly();
        // bird.swim() // Fly에는 swim이 없기 때문에 호출할 수 없다.

        // 인터페이스를 사용하는 실질적인 이유?
        // 결제의 예시로 돌아가보면... 결제를 처리하는 코드는 결제를 처리하기만 하면 된다. 즉,
        // 1. 기능별 구현체를 서비스가 전부 가지고 있을 필요가 없다 = 서비스와 로직의 분리.
        // 2. 다형성으로 인해 어떤 서비스인지 신경을 안써도 된다. 어차피 다 Payment거든.
        PaymentService paymentService = new PaymentService();
        paymentService.process(cardPayment, 3000);
        paymentService.process(tossPayment, 5000);
        // 이게 의존성을 낮추고 코드를 유연하게 만든다.
        // 이제 객체 하나씩만 만들어놓으면 PaymentService 하나로 다 해결되잖아.
        // 이거 아니면 종류별로 if문 갈기고 가독성 떨어지고 의존성 때문에 코드 터진다

        // 구체적인 구현이 아니라 추상화된 계약에 의존하는 것...
        // 추후 이것이 Spring의 DI(Dependency Injection)과 깊이 연관된다.

        // Shape interface 만들기
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(10, 20);

        System.out.println(circle.area());
        System.out.println(rectangle.area());

        Shape[] shapes = {
            new Circle(5),
            new Rectangle(10, 20),
            new Circle(3)
        };

        for (Shape shape : shapes) {
            System.out.println(shape.area());
        }
    }
}
