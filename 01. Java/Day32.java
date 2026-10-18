import java.util.List;
import java.util.stream.Collectors;

public class Day32 {
    public static void main(String[] args) {
        // Stream API 심화
        // 처리한 결과를 collect()와 Collector로 다시 컬렉션으로 모아보자

        // 왜 collect()가 필요할까?
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        // 아래의 stream은 출력만 하고 결과는 저장하지 않는다.
        numbers.stream()
            .filter(number -> number % 2 == 0)
            .forEach(number -> System.out.println(number));
        // 결과를 새로운 리스트로 만들기 위해서 collecct()를 사용한다.
        List<Integer> evenNumbers = numbers.stream()
            .filter(number -> number % 2 == 0)
            .collect(Collectors.toList());
        System.out.println(evenNumbers);
        // 코드의 각 역할을 구분해보면
        // - stream(): 스트림 생성
        // - filter(): 짝수만 남김
        // - collect(): 처리 결과를 모음
        // - Collectors.toList(): 결과를 List로 수집하도록 지정
        // 핵심: forEach()는 요소별로 작업을 수행하는데 사용하고,
        // collect()는 처리한 결과를 모아 새로운 자료구조를 만드는데 사용한다.

        // filter()와 collect() 연결하기
        // 점수가 70점 이상인 학생의 점수만 따로 저장해보자.
        List<Integer> scores = List.of(50, 80, 65, 90, 75);
        List<Integer> passedScores = scores.stream()
            .filter(score -> score >= 70)
            .collect(Collectors.toList());
        System.out.println(passedScores);
        // 원본은 그대로 있고, 조건에 맞는 값만 담은 새로운 리스트가 만들어진다.
        // 주의할 점: 수집 결과의 순서는 일반적으로 원본 요소의 순서를 유지한다.

        // map()과 collect() 연결하기
        // 숫자를 두 배로 바꾼 결과를 리스트로 만들어보자.
        List<Integer> doubledNumbers = numbers.stream()
            .map(number -> number * 2)
            .collect(Collectors.toList());
        System.out.println(doubledNumbers);

        // 모두 연결하기
        // 짝수만 두 배로 바꾼 결과를 리스트로 만들어보자
        List<Integer> numbers2 = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> result = numbers2.stream()
            .filter(number -> number % 2 == 0)
            .map(number -> number * 10)
            .collect(Collectors.toCollection(ArrayList::new));
            // List 중에서도 특별히 ArrayList로 만들 수 있다.
            // 이런 경우, 새로운 List는 확실하게 수정할 수 있다.
        System.out.println(result);


        // Collectors.toList()는 무엇일까?
        // Collectors는 스트림 결과를 수집하는 데 필요한 여러 기능을 제공하는 Java 표준 클래스다.
        // 그중에서도 .toList()는 요소들을 List로 모으기 위한 수집기를 반환한다.
        // Java 16 이상에서는 다음과 같이 간결하게 작성할 수도 있다.
        List<String> names = List.of("kim", "park", "lee");
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .toList();
        System.out.println(upperNames);
        // 다만, 반환된 리스트는 수정할 수 없는 리스트다.

        // count()
        // 결과를 리스트로 저장하지 않고, 개수만 알고 싶을 때도 있다.
        // 반환 타입은 long이다.
        long count = numbers2.stream()
            .filter(number -> number % 2 == 0)
            .count();
        System.out.println(count);

        // reduce()
        // 여러 요소를 하나의 결과로 누적할 때 사용한다.
        // 예를 들면, 숫자의 합계를 구할 수 있다.
        int sum = numbers.stream()
            // 0은 누적의 초기값이다.
            // 람다식은 현재까지의 누적값과 다음 요소를 합치는 규칙이다.
            .reduce(0, (a,b ) -> a + b);
        System.out.println(sum);
        
        // 조건에 맞는 값 수집해보기
        // 50 이상인 숫자만 골라 새로운 List<Integer>에 저장하고 출력해보자
        List<Integer> numbers3 = List.of(12, 45, 67, 23, 89, 54, 91);
        List<Integer> bigNumbers = numbers3.stream()
            .filter(number -> number >= 50)
            .toList();
        System.out.println(bigNumbers);

        // 필터링과 변환 결합하기
        // 홀수만 남긴 뒤 각 숫자에 3을 곱해 새로운 리스트로 저장하고 출력해보자
        List<Integer> numbers4 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> odds = numbers4.stream()
            .filter(number -> number % 2 == 1)
            .map(number -> number * 3)
            .collect(Collectors.toList());
        System.out.println(odds);

        // 개수와 합계 구하기
        List<Integer> numbers5 = List.of(10, 20, 30, 40, 50);
        long count2 = numbers5.stream()
            .filter(number -> number >= 30)
            .count();
        int sum2 = numbers5.stream()
            .filter(number -> number >= 30)
            .reduce(0, (a, b) -> a + b);
        System.out.println(count2);
        System.out.println(sum2);

        // Q1. forEach()와 collect()의 차이는?
        // A1. forEach()는 각 요소에 특정한 동작을 실행하게 하고, 
        // collect()는 요소들을 모아 새로운 자료구조로 만든다.
        // Q2. reduce()는 어던 상황에 쓰이는가?
        // A2. 여러 요소를 원하는 누적 방법을 사용해 하나로 만들고 싶을 때 사용한다.
    }   
}
