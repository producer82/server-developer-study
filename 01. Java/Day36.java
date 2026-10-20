public class Day36 {
    // 열거형 타입을 정의한다.
    enum OrderStatus {
        PENDING,
        PAID,
        CANCELLED
    }

    // 필드, 생성자, 메서드도 정의할 수 있다.
    enum PaymentMethod {
        // Enum 상수를 선언하며 생성자에 값을 전달한다.
        // 상수는 정의된 순서대로 생성되며, 생성자는 상수 선언에 전달된 값으로 호출된다.
        // 코드와 설명을 함께 관리해야 할 때 편리하다.
        CARD("카드"),
        BANK_TRANSFER("계좌이체"),
        POINT("포인트");
        
        // 각 상수가 가지는 설명을 저장한다.
        private final String description;

        // Enum 생성자는 new로 직접 호출할 수 없다.
        PaymentMethod(String description) {
            this.description = description;
        }

        // 상수에 저장된 설명을 반환한다.
        public String getDescription() {
            return description;
        }
    }

    public static void main(String[] args) {
        // Enum(열거형)
        // 주문 시스템에는 주문 상태가 있고, 결제 시스템에는 결제 수단이 있다.
        // 이런 값들은 정해진 값 중 하나만 선택하도록 제한하는 것이 안전하다.
        
        // Enum이란?
        // 미리 정해 놓은 상수들의 집합을 정의하는 Java의 타입이다.
        OrderStatus status = OrderStatus.PENDING;
        System.out.println(status);
        // OrderStatus.PAID처럼 타입명.상수명 형식으로 값을 사용한다.
        status = OrderStatus.PAID;
        System.out.println(status);
        // enum을 사용하면 오타와 같은 실수를 방지할 수 있고,
        // 예상하지 못한 값의 입력 또한 막을 수 있다.

        // Enum 비교하기
        // Enum의 각 상수는 해당 열거형에서 고유한 값이기 때문에 == 로 비교해도 된다.
        if (status == OrderStatus.PAID){
            System.out.println("결제가 완료되었습니다.");
        }
        // status가 null 일 수 있다면, status == OrderStatus.PAID는 안전한 반면
        // status.equals(OrderStatus.PAID)는 예외가 발생할 수도 있다.

        // Enum과 switch
        // Enum은 switch와 함께 사용하면 상태별 로직을 깔끔하게 작성할 수 있다.
        switch (status) {
            // switch 내부에서는 상수 이름만 적어도 된다.
            case PENDING:
                System.out.println("결제를 기다리는 중입니다.");
                break;
            case PAID:
                System.out.println("결제가 완료되었습니다.");
                break;
            case CANCELLED:
                System.out.println("주문이 취소되었습니다.");
                break;
        }

        // Enum은 단순한 상수 모음이 아니다.
        // Enum에는 필드, 생성자, 메서드도 정의할 수 있다.
        PaymentMethod method = PaymentMethod.CARD;
        System.out.println(method);
        System.out.println(method.getDescription());
        method = PaymentMethod.POINT;
        System.out.println(method);
        System.out.println(method.getDescription());

        // values()와 valueOf()
        // Enum에 정의된 모든 상수를 순회하거나, 문자열을 Enum 값으로 변환 가능하다.
        for (OrderStatus i : OrderStatus.values()) {
            System.out.println(i);
        }
        // 문자열로 Enum값 찾기
        // 단, valueOf()에 존재하지 않는 이름을 전달하면 IllegalArgumentException이 발생한다.
        // 외부에서 전달된 문자열을 변환할 때는 예외 처리를 고려해야 한다.
        status = OrderStatus.valueOf("PAID");
        System.out.println(status);

        // Q1. String 대신 Enum을 사용하면 어떤 장점이 있을까?
        // A1. 오타와 같은 실수를 방지할 수 있고, 원하지 않은 값의 입력 또한 막을 수 있다.
        // Q2. Enum 생성자를 직접 new로 호출할 수 없는 이유는 무엇일까?
        // A2. Enum 상수는 고정된 상수 집합을 정의하기 위해 미리 단 하나의 인스턴스만 생성되며,
        // 개발자가 new로 임의의 Enum 인스턴스를 생성할 수 없도록 제한되어 있다.
        // Q3. values()와 valueOf()의 차이는 무엇일까?
        // A3. values()는 enum에 있는 모든 상수를 순회하고, valueOf()는 문자열로 상수를 찾는다.
    }
}
