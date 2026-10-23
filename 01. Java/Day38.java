interface Hello {
    void sayHello();
}

interface Printable {
    void print();
}

public class Day38 {
    public static void main(String[] args) {
        // 지역 클래스와 익명 클래스
        // 클래스를 더 유연하게 활용하는 방법을 배워보자
        // 메서드 안에서 클래스를 선언하거나 이름 없이 클래스를 만들 수 있다.
        
        // 지역 클래스
        // 메서드 내부에 선언하는 클래스다.
        class Greeting {
            // 클래스이므로 필드와 메서드를 가질 수 있다.
            void sayHello() {
                System.out.println("안녕하세요!");
            }
        }
        // 선언한 메서드 내부에서만 사용할 수 있다.
        // 메서드가 끝난 뒤에는 그 이름으로 지역 클래스에 접근할 수 없다.
        Greeting greeting = new Greeting();
        greeting.sayHello();

        // 익명 클래스
        // 클래스 이름을 별도로 선언하지 않고, 
        // 객체를 생성하면서 클래스의 내용을 정의한다.
        Hello hello = new Hello() {
            // 일반적인 인터페이스는 new로 객체를 만들 수 없다.
            // 하지만, 그 인터페이스를 구현하는 익명 클래스를 정의하고 
            // 인스턴스를 생성할 수는 있다.
            @Override 
            public void sayHello() {
                System.out.println("Hello!");
            }
        };
        // 인터페이스를 구현하거나 클래스를 상속하며, 특정 구현을 한 번만 사용해야 할 때 유용하다.
        // 또한 Hello 타입으로 선언되었기 때문에 Hello에 선언된 메서드를 모두 호출할 수 있다.
        hello.sayHello();

        // 지역 클래스 Message 만들기
        class Message {
            void print() {
                System.out.println("메시지입니다.");
            }
        }
        Message message = new Message();
        message.print();

        Printable printable = new Printable() {
            @Override
            public void print() {
                System.out.println("익명 클래스입니다.");
            }
        };
        printable.print();

        // Q1. 지역 클래스는 어디에서 선언하고 사용할 수 있을까?
        // A1. 지역 클래스는 메서드 내부에서 선언하고, 그 메서드 안에서만 사용할 수 있다.
        // Q2. 익명 클래스에는 왜 별도의 클래스 이름이 없을까?
        // A2. 익명 클래스는 객체 생성과 동시에 선언되기 때문에 이름이 필요 없다.
        // Q3. 익명 클래스는 어떤 상황에서 유용할까?
        // A3. 인터페이스나 클래스의 상속 등으로 일회성 구현을 만들어야 할 때 유용하다.
    }
}
