interface Payment {
    void pay(int amount);
}

class PaymentService {
    void pay(Payment payment, int amount) {
        payment.pay(amount);
    }
}

class CardPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("카드 결제: " + amount);
    }
}

class TransferPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("계좌이체: " + amount);
    }
}

class EasyPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("간편결제: " + amount);
    }
}

class PointPayment implements Payment {
    @Override 
    public void pay(int amount) {
        System.out.println("포인트결제: " + amount);
    }
}

class DiscountService {
    int discount(String type, int price) {
        if (type.equals("student")) {
            return price * 10 / 100;
        } else if (type.equals("vip")) {
            return price * 20 / 100;
        } else {
            return 0;
        }
    }
}

interface DiscountPolicy {
    int getDiscountAmount(int price);
}

class NewDiscountService {
    int discount(DiscountPolicy type, int price) {
        return type.getDiscountAmount(price);
    }
}

class StudentDiscount implements DiscountPolicy {
    @Override 
    public int getDiscountAmount(int price) {
        return price * 10 / 100;
    }
}

class VipDiscount implements DiscountPolicy {
    @Override 
    public int getDiscountAmount(int price) {
        return price * 20 / 100;
    }
}

class MilitaryDiscount implements DiscountPolicy {
    @Override 
    public int getDiscountAmount(int price) {
        return price * 30 / 100;
    }
}

public class Day17 {
    public static void main(String[] args) {
        // OCP (Open-Closed Principle, 개방-폐쇄 원칙)
        // 소프트웨어 구성 요소는 확장에는 열려있고 수정에는 닫혀있어야 한다.
        // 확장에는 열려있다: 새로운 기능이나 구현을 추가할 수 있다.
        // 수정에는 닫혀있다: 기존에 검증된 코드를 매번 수정하지 않아도 된다.
        // 즉, 새로운 기능을 추가할 때 기존 코드를 전부 뜯어고치지 않도록 설계해야 한다는 뜻이다.

        // 문제 상황 제시:
        // 쇼핑몰의 결제 시스템을 PaymentService라는 클래스로 구현했다.
        // 처음에는 카드 결제만 지원하다가, 결제 수단 추가에 대한 요구사항이 발생했다.
        // 이미 잘 동작하는 PaymentService 클래스를 계속 수정해야 할까?
        // A: 인터페이스와 다형성을 활용해보자
        
        PaymentService paymentService = new PaymentService();
        // 생성자로 PaymentService(new CardPayment()) 하는 것이 항상 좋을까?
        // 이번에는 pay()에 Payment 객체를 직접 넘겨주는게 훨씬 활용성이 좋다.
        // Why?: 생성자로 넣어버리면 나중에 결제수단을 바꿀수는 있지만 힘들어짐.
        CardPayment cardPayment = new CardPayment();
        EasyPayment easyPayment = new EasyPayment();
        TransferPayment transferPayment = new TransferPayment();
        // 이제 PaymentService는 무슨 결제인지 알 필요가 없다.
        paymentService.pay(cardPayment, 3000);
        paymentService.pay(easyPayment, 5000);
        paymentService.pay(transferPayment, 7000);
        // 갑자기 포인트 결제 기능을 추가해야하는 상황이라면?
        PointPayment pointPayment = new PointPayment();
        paymentService.pay(pointPayment, 10000);
        // PaymentService는 수정할 필요가 없으며, PointPayment만 만들어주면 된다.

        // 그럼 OCP는 무조건 조건문을 없애는 건가?
        // 단순한 조건 분기에는 조건문이 가장 적절한 방법일 수도 있다.
        // 문제: 새로운 구현이 추가될 때마다 핵심 로직을 계속 수정해야 하는 상황
        // 만약 조건문을 계속 수정해야 하는 상황이라면? -> 다형성을 활용하는게 낫다
        
        // 결국 OCP의 핵심:
        // 1. 변경 가능성이 높은 부분을 파악한다.
        // 2. 그 부분을 추상화 한다.
        // 3. 새로운 구현을 추가해도 검증된 로직의 수정이 최소화되게 만든다.
        // -> 구현을 추가했는데 검증된 로직이 변경되어 오류가 날 가능성이 줄어든다.

        // SRP와의 비교
        // SRP: 이 클래스는 너무 많은 변경 이유를 가지고 있지 않은가?
        // ex) PaymentService가 결제도 하는데 결제 완료 이메일도 발송한다
        // OCP: 새로운 기능을 추가할 때 멀쩡한 코드를 계속 수정해야 하는가?
        // ex) 새로운 결제 수단이 추가될 때마다 검증된 로직이 계속 변경된다.

        // 할인 시스템 OCP로 만들어보기
        DiscountService discountService = new DiscountService();
        System.out.println(discountService.discount("student", 10000));
        // 요구 사항 발생: 군인 할인과 신규 회원 할인을 추가해주세요
        // 문제 상황: DiscountService의 검증된 로직을 계속 수정해야만 한다.
        // 해결: 인터페이스와 다형성을 활용해 OCP로 만든다.
        NewDiscountService newDiscountService = new NewDiscountService();
        System.out.println(newDiscountService.discount(new StudentDiscount(), 10000));
        System.out.println(newDiscountService.discount(new VipDiscount(), 10000));
        System.out.println(newDiscountService.discount(new MilitaryDiscount(), 10000));
    }   
}

