import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Day40 {
    public static void main(String[] args) {
        // 메서드 참조
        // 이미 존재하는 메서드를 람다 표현식 대신 간결하게 지정하는 문법
        Consumer<String> printer = text -> System.out.println(text);
        // 이 구현은 text를 받아 텍스트를 출력한다.
        printer.accept("Hello Java");
        // 근데 우리가 구현하고 싶은 기능의 메서드는 이미 존재한다.
        // 메서드 참조를 사용하면 다음과 같이 바꿀 수 있다.
        Consumer<String> printer2 = System.out::println;
        printer2.accept("Hello Java");

        // 두 정수 중 큰 값 구하기
        // BiFunction<T, U, R>은 T 타입 변수와 U 타입 변수를 입력받아 R 타입 변수를 반환한다.
        BiFunction<Integer, Integer, Integer> max1 = (x, y) -> Math.max(x, y);
        System.out.println(max1.apply(10, 7));
        BiFunction<Integer, Integer, Integer> max2 = Math::max;
        System.out.println(max2.apply(10, 7));

        // 문자열을 대문자로 변환
        // Function<T, R>은 T 타입의 값을 받아 R 타입의 결과를 반환한다.
        Function<String, String> upper1 = text -> text.toUpperCase();
        Function<String, String> upper2 = String::toUpperCase;
        System.out.println(upper1.apply("java"));
        System.out.println(upper2.apply("java"));
        
        // 메서드 참조는 무조건 람다보다 좋은 것이 아니다.
        // 메서드 참조가 자연스럽게 쓸 수 있는 상황에서만 사용하는 것이 좋다.

        // Q1. 메서드 참조에서 사용하는 기호는 무엇일까?
        // A1. ::
        // Q2. 메서드 참조를 사용하면 어떤 장점이 있을까?
        // A2. 이미 있는 메서드를 참조하여 코드가 짧아지고 가독성이 좋아질 수 있다.
        // Q3. 모든 람다 표현식을 메서드 참조로 바꿀 수 있을까?
        // A3. 이미 있는 메서드로 대체할 수 없는 경우에는 바꿀 수 없다.
        // Q4. Math::max는 어떤 메서드를 참조할까?
        // A4. Math 클래스의 max 메서드를 참조한다
    }
}
