// @FunctionalInterface는 함수형 인터페이스의 조건을 만족하는지 검사하도록 하는 어노테이션이다.
// 가급적 붙이는 것이 좋다.

import java.util.function.Consumer;

@FunctionalInterface 
interface Calculator {
    int calculate(int a, int b);
}

public class Day30 {
    public static void main(String[] args) {
        // 람다식과 함수형 인터페이스
        // 람다식과 함수형 인터페이스의 관계를 알아보자

        // 람다식이란?: 쉽게 말하면 메서드를 간결하게 표현하는 방법이다.
        // 자세히는 추상 메서드에 대응하는 동작을 간결하게 구현하는 문법이다.
        // 기존에는 다음과 같이 메서드를 만들고 사용했다.
        System.out.println(add(3,4));
        // 람다식으로는 다음과 같이 메서드를 만들 수 있다.
        // (a, b) -> a + b;
        // 람다식의 기본 형태는 다음과 같다.
        // (매개변수) -> { 실행할 코드 }
        // 다만, 이 자체로 일반적인 메서드의 선언이 아니기 때문에 사용하기 위해서는 어떤 종류의 작업인지 알려주는 
        // 함수형 인터페이스와 연결하는 경우가 많다.

        // 함수형 인터페이스란?: 추상 메서드가 하나인 인터페이스
        // 본래 인터페이스의 추상 메서드를 구현하기 위해서는 다음과 같이 해야했다.
        Calculator calculator1 = new Calculator() {
            // 익명 메서드 기법
            // 객체 생성과 동시에 클래스를 선언할 수 있다.
            @Override
            public int calculate(int a, int b) {
                return a + b;
            }
        };
        System.out.println(calculator1.calculate(10, 20));
        // 이 코드를 람다식으로 바꿔보자.
        Calculator calculator2 = (a, b) -> a + b;
        System.out.println(calculator2.calculate(10, 20));
        Calculator calculator3 = (a, b) -> a * b;
        System.out.println(calculator3.calculate(10, 20));
        // 중요한 점은 람다식이 추상 메서드인 calculate()를 구현하는 역할을 한다는 것이다.
        // 동시에, 매개변수 타입을 생략가능한 이유는 Java가 함수형 인터페이스의 메서드 선언을
        // 바탕으로 타입을 추론하기 때문이다.

        // 람다식의 문법
        // 매개변수가 두 개일 때: (a, b) -> a + b
        // - 매개변수가 두 개이므로 괄호를 사용한다.
        // 매개변수가 하나일 때: x -> x * 2
        // - 매개변수가 하나이고 타입을 선언하지 않는다면 괄호를 생략할 수 있다.
        // 실행문이 여러 개일 때: (a, b) -> { int result = a + b; return result; }
        // - 실행문이 여러 개라면 중괄호를 사용할 수 있다.
        // - 추가로, 값을 반환해야 한다면 return을 사용해야 한다.
        // 실행문이 한 개일 때: (a, b) -> a + b
        // - 중괄호를 생략할 수 있다.
        // - 동시에 return도 생략할 수 있다.
        
        // 람다식을 메서드에 전달하기
        // 이전 Comparator의 경우를 떠올려보자
        // students.sort((a, b) -> a.name.compareTo(b.name));
        // sort()는 인터페이스 Comparator를 매개변수로 받기 때문에, 위 람다식은
        // 함수형 인터페이스 Comparator의 compare() 메서드를 구현하는 표현으로 해석할 수 있다.

        // 즉, 다시 풀어쓰면:
        // Comparator<Student> comparator = (a, b) -> a.name.compareTo(b.name);
        // students.sort(comparator);
        // Comparator를 람다식으로 구현하고, Comparator를 sort()에게 넘겨준다.

        // 람다식을 사용하지 않는다면 아래처럼 작성할 수 있다.
        // students.sort(new Comparator<Student>() {
        //      @Override
        //      public int compare(Student a, Student b) {
        //          return a.name.compareTo(b.name);
        //      }
        // });
        // 람다식은 익명 클래스의 반복적인 사용을 줄일 수 있다.
        // 즉, 람다식은 함수형 인터페이스가 요구한 동작을 간결하게 전달하는 방법이다.

        // 람다식 정리:
        // 람다식은 함수형 인터페이스가 요구하는 동작을 간결하게 표현하는 문법
        // 이를 위해 일반 메서드에서 매개변수와 구현부를 떼온 형식을 하고 있다.
        // 지정된 추상 메서드를 구현하는 형식이기 때문에, 필요한 반환 타입 정보와 
        // 매개변수의 타입 정보는 외부 타깃을 통해 추론할 수 있다.

        // Consumer 사용해보기
        // Consumer<T>는 값을 받아 작업을 수행하지만 결과값을 반환하지 않는 함수형 인터페이스다.
        // 즉, 데이터를 받아서 처리하되 반환값은 없는 공통적인 행동 패턴을 저장하기 위해 존재한다.
        Consumer<String> printer = value -> System.out::println;
        // 메서드 참조 문법
        // 클래스::메서드 형식으로 사용하여 특정 메서드의 구현을 참조할 수 있다.
        printer.accept("Hello");
        printer.accept("World");

        // Q1. 함수형 인터페이스란?
        // A1. 추상 메서드를 한 개 가지고 있는 인터페이스
        // static이나 default같은 다른 인터페이스가 있더라도 추상 메서드가 한개만 있으면 된다.
        // Q2. @FunctionalInterface는 무슨 역할인가?
        // A2. 함수형 인터페이스라는 것을 컴파일러에게 알려주고 조건을 만족하는지 검증할 수 있게 하는
        // 어노테이션이다.
        // Q3. 람다식은 익명 클래스와 어떤 관계가 있을까?
        // A3. 본래 함수형 인터페이스는 익명 클래스를 통해 구현해야 했지만, 이제 람다식을 통해
        // 더 짧고 간결하게 구현할 수 있게 되었다.
    }
    
    public static int add(int a, int b) {
        return a + b;
    }
}
