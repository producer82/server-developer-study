import java.util.Optional;

public class Day33 {
    public static void main(String[] args) {
        // Optional과 null 처리
        
        // null이란 무엇일까?
        // 객체를 참조하는 변수가 현재 어떤 객체도 가리키고 있지 않다는 의미다.
        String name = null;
        // 객체가 없는데 길이를 구하려고 하면 NullPointerException이 발생한다.
        // System.out.println(name.length);
        name = "Java";
        // 객체가 있으면 정상적으로 실행된다.
        System.out.println(name.length());
        // 즉, 객체가 존재하는지 확인하지 않고 메서드를 호출하면 문제가 생길 수 있다.
        // 이 문제를 어떻게 해결할까?

        // null을 직접 확인하는 방법
        // if문으로 객체가 null이 아닌지 확인하고 메서드를 호출한다.
        if (name != null) {
            System.out.println(name.length());
        }
        else {
            System.out.println("이름이 없습니다.");
        }
        // 이 방법은 간단하고 유효하며, 실제로도 많이 사용한다.
        // 하지만 데이터가 없을 가능성이 있는 곳마다 이런 검사를 반복하면 
        // 코드가 길어질 수 있다.
        // 이런 상황에서 사용할 수 있는 도구 중 하나가 Optional이다.

        // Optional이란?
        // Optional<T>는 값이 존재할 수도 있고, 
        // 존재하지 않을 수도 있다는 상황을 표현하는 클래스다.
        Optional<String> empty = Optional.empty();
        Optional<String> notnull = Optional.of("Java");
        Optional<String> maybenull = Optional.ofNullable("Java");
        // 생성 메서드에는 주로 세 가지를 사용한다.
        // - Optional.of(value): 반드시 null이 아닌 값이 있을 때
        // - Optional.ofNullable(value): 값이 null일 수도 있을 때
        // - Optional.empty(): 값이 없는 Optional을 만들 때
        // 주의할 점: Optional.of(null)은 예외를 발생시킨다.
        
        // isPresent(): 값이 존재하는지 확인하기
        // boolean 값을 반환한다.
        System.out.println(empty.isPresent());
        System.out.println(notnull.isPresent());
        
        // orElse(): 값이 없을 때 기본값 사용하기
        // 값이 있으면 그 값을 반환하고, 비어있으면 지정한 기본값을 반환한다.
        System.out.println(empty.orElse("값이 없습니다"));
        // 주의할 점: orElse()의 기본값 인자는 값이 있어도 평가될 수 있다.
        // 기본값을 만드는 작업에 비용이 많이 든다면 orElseGet()을 고려할 수 있다.
        System.out.println(empty.orElseGet(() -> "값이 없습니다."));

        // ifPresent(): 값이 있을 때만 작업하기
        // 값이 있으면 람다식 안의 작업을 실행하고, 값이 없으면 실행하지 않는다.
        notnull.ifPresent(value -> 
            System.out.println(value + "를 공부합니다.")
        );

        // "Toss"를 담은 Optional을 만들고 값이 존재하는지 확인해서 출력하자
        Optional<String> toss = Optional.of("Toss");
        toss.ifPresent(value -> System.out.println(value));

        // 비어있는 Optional<String>을 만들고 orElse()로 기본 이름 출력하기
        Optional<String> username = Optional.empty();
        System.out.println(username.orElse("기본 이름"));

        // Optional.ofNullable()에 null을 전달하고 orElse()로 "값이 없습니다" 출력하기.
        Optional<String> nullable = Optional.ofNullable(null);
        System.out.println(nullable.orElse("값이 없습니다."));

        // Optional.of("Java")를 만들고 ifPresent()로 "Java를 공부합니다" 출력하기
        notnull.ifPresent(value -> 
            System.out.println(value + "를 공부합니다.")
        );

        // Q1. null을 직접 검사하는 것과 Optional을 사용하는 방식은 어떤 차이가 있을까?
        // A1. null을 직접 검사하는 것보다 Optional을 사용하는 것이 코드가 간결해질 수 있다.
        // 추가로, 값이 없을 가능성을 "명시적으로" 코드에 표현할 수 있다.
        // 데이터 조회 결과처럼 값이 없을 수 있는 상황에서 사용하면 좋다.
        // Q2. Optional.of(null)과 Optional.ofNullable(null)은 어떤 차이가 있을까?
        // A2. Optional.of(null)은 "무조건 null이 아님"이므로, null로 비워두려는 시도는 예외를 발생시킨다.
        // Optional.ofNullable(null)은 "null일 수도 있다"를 알려주고 Optional.empty()를 반환한다.
    }
}
