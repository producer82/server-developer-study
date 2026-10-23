@FunctionalInterface 
interface Calculator {
    int calculate(int a, int b);
}

class AddCalculator implements Calculator {
    @Override 
    public int calculate(int a, int b) {
        return a + b;
    }
}

public class Day39 {
    public static void main(String[] args) {
        // 함수형 인터페이스와 람다 표현식 복습 및 심화
        // 람다와 함수형 인터페이스, 익명 클래스는 어떻게 연결될까?
        // 같은 동작을 익명 클래스와 람다로 각각 표현하고 차이를 이해해보자

        // 함수형 인터페이스
        // 추상 메서드가 하나만 있는 인터페이스다
        // 함수형 인터페이스를 구현하는 방법에는 여러가지가 있다.

        // 1. 일반 구현 클래스
        // 구현을 재사용하기 좋지만, 단순한 동작 하나에 코드가 길어질 수 있다.
        AddCalculator classCalculator = new AddCalculator();
        System.out.println(classCalculator.calculate(3, 5));

        // 2. 익명 클래스
        // 별도의 구현 클래스 이름 없이 객체를 만들 수 있다.
        Calculator anonCalculator = new Calculator() {
            @Override
            public int calculate(int a, int b) {
                return a * b;
            }
        };
        System.out.println(anonCalculator.calculate(3, 5));
        
        // 3. 람다 표현식
        // 함수형 인터페이스의 구현을 간단하게 작성할 수 있다.
        Calculator lambdaCalculator = (a, b) -> { if (a > b) { return a; } else { return b; }};
        // 표현식이 하나면 중괄호와 return을 생략할 수 있다.
        System.out.println(lambdaCalculator.calculate(10, 7));
        
        // Q1. 함수형 인터페이스의 조건은?
        // A1. 추상 메서드가 한 개여야 한다.
        // Q2. 람다 표현식은 익명 클래스에 비교해 어떤 장점이 있을까?
        // A2. 익명 클래스에 비하여 구현이 간결해진다.
        // Q3. 모든 인터페이스를 람다 표현식으로 구현할 수 있을까?
        // A3. 함수형 인터페이스만 람다 식으로 구현할 수 있다.
        // Q4. (a, b) -> a + b 와 (a, b) -> { return a + b; }의 차이는 무엇일까?
        // A4. 생략의 차이이지 결과에는 차이가 없다.
    }
}
