// 결제 수단 클래스는 모두 Payment를 implements 해야한다.(OCP)
// 딱 Payment가 공통적으로 필요한 기능만 규칙으로 만듬. (ISP)
interface Payment {
    // 모든 Payment 구현체는 pay 설계 의도에 맞는 행동을 구현해야한다. (LSP)
    void pay(int amount);
}

// 결제만 수행한다.(SRP) = OrderService의 하위 모듈 (구체적인 구현)
// 하위 모듈이 모두 Payment 추상화에 의존하여 구현된다.(DIP)
class CardPayment implements Payment {
    @Override
    public void pay(int amount) {
       System.out.println("카드 결제:" + amount);
    }
}

class TossPayment implements Payment {
    @Override
    public void pay(int amount) {
       System.out.println("토스 결제:" + amount);
    }
}

class PointPayment implements Payment {
    @Override
    public void pay(int amount) {
       System.out.println("포인트 결제:" + amount);
    }
}

// 주문 서비스의 핵심 기능만 처리한다.(SRP) = 상위 모듈 (핵심적인 기능)
class OrderService {
    private final OrderResultPrinter printer;

    OrderService(OrderResultPrinter printer) {
        this.printer = printer;
    }

    // 상위 모듈이 Payment 인터페이스에 의존한다. 
    // -> 확장성 확보(OCP), 코드 수정 줄이기(DIP)
    void order(Payment payment, int amount) {
        payment.pay(amount);
        printer.printResult();
    }
}

// 주문 결과 출력만 담당한다.(SRP) = OrderService의 하위 모듈 (구체적인 구현)
class OrderResultPrinter {
    void printResult() {
        System.out.println("주문이 성공적으로 완료되었습니다.");
    }
}

public class Day21 {
    public static void main(String[] args) {
        // SOLID 설계 종합 실습
        // 간단한 주문, 결제 시스템 설계하기
        // 1. 인터페이스와 다형성으로 결제 수단을 교체할 수 있게 만들기
        // 2. 클래스마다 명확한 책임 부여하기
        // 3. 새로운 기능을 추가할 때 기존 코드를 최소한으로 수정하기
        // 4. 생성자 주입으로 클래스 사이의 의존성 관리하기
        // 5. SOLID 원칙이 실제 코드에서 어떻게 적용되는지 설명하기
        //
        // 비즈니스 요구사항
        // 사용자가 상품을 주문하면 다음 작업이 이루어져야 한다.
        // 1. 주문 금액을 계산한다
        // 2. 결제 수단으로 결제한다
        // 3. 주문 결과를 출력한다.
        // 우선 카드 결제만 지원하지만, 나중에 결제 수단을 추가할 수 있어야 한다.
        
        OrderService orderService = new OrderService(new OrderResultPrinter());
        // 결제 수단을 생성자로 주입하는 것 보다, pay()에 직접 넘겨주는게 효율이 좋울 수도 있다.
        // 생성자로 주입하면 결제 수단마다 OrderService를 만들어야 한다.
        // 다만, 모든 호출에서 객체를 생성해 전달해야 한다.

        // 생성자 주입도 장점이 될 수도 있다는 점을 인지해야 한다.
        // 이 방식은 한번 결제수단마다 서비스를 만들어놓으면 계속 사용할 수 있다.
        // 상황에 따라 선택할 것
        orderService.order(new CardPayment(), 10000);
        orderService.order(new TossPayment(), 20000);
        orderService.order(new PointPayment(), 5000);
    }
}
