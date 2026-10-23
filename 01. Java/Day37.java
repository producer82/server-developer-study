public class Day37 {
    public static void main(String[] args) {
        // 중첩 클래스와 내부 클래스
        // 클래스 안에 또 다른 클래스를 정의하는 방법

        // 중첩 클래스란?
        // 클래스 내부에 선언된 클래스를 중첩 클래스라고 한다.
        // 중첩 클래스는 크게 두 종류로 나눌 수 있다.

        // 1. static 중첩 클래스
        // 외부 클래스 이름을 통해 중첩 클래스를 지정할 수 있다.
        Outer.Nested nested = new Outer.Nested();
        // static 중첩 클래스는 외부 클래스의 객체 없이 생성할 수 있다.
        nested.print();
        // 단, 외부 클래스의 인스턴스 필드에는 접근할 수 없다.
        // 외부 클래스의 인스턴스를 생성하지 않았기 때문이다.
        // System.out.println(nested.name);

        // 2. 내부 클래스
        // 내부 클래스 객체를 만드려면 외부 클래스의 객체를 먼저 만들어야 한다.
        Outer outer = new Outer();
        // 생성된 외부 클래스의 객체를 통해 객체를 생성한다.
        Outer.Inner inner = outer.new Inner();
        // 내부 클래스는 외부 클래스의 인스턴스와 연결되어 있기에
        // 인스턴스 멤버에 접근할 수 있다.
        inner.printName();
        
        // 경우에 따라 두 클래스 모두 외부 클래스의 private에 접근할 수 있다.

        // Q1. static 중첩 클래스는 왜 Outer 객체 없이 생성할 수 있을까?
        // A1. static 중첩 클래스는 특정 Outer 인스턴스와 연결되지 않기 때문에
        // Outer 객체 없이도 생성할 수 있다.
        // 단, static 클래스의 객체 자체는 new로 생성한다.
        // Q2. 일반 내부 클래스가 Outer 객체를 필요로 하는 이유는 무엇일까?
        // A2. 일반 내부 클래스는 Outer 인스턴스와 연결되기 떄문에,
        // Outer 인스턴스가 생성되어야 Inner를 생성할 수 있다.
        // Q3. static 중첩 클래스와 일반 내부 클래스의 가장 중요한 차이는?
        // A3. static 중첩 클래스는 외부 클래스의 인스턴스 필드에 접근할 수 없다.
        // 또한, static 중첩 클래스는 Outer 인스턴스가 없어도 생성할 수 있다.
    }
}

class Outer {
    private String name = "Java";

    class Inner {
        void printName() {
            System.out.println(name);
        }
    }

    static class Nested {
        void print() {
            System.out.println("static 중첩 클래스");
        }
    }
}
