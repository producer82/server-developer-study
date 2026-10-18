import java.util.List;

public class Day31 {
    public static void main(String[] args) {
        // Stream API 기초
        // 백엔드에서 데이터베이스 조회 결과를 가공하거나, 특정 조건에 맞는 데이터를
        // 필터링할 때 자주 사용한다.

        // Stream 이란
        // 숫자 5개가 들어있는 리스트가 있다.
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        // 짝수만 골라 출력하기 위해서는 다음과 같이 할 수 있었다.
        for (int number : numbers) {
            if (number % 2 == 0) {
                System.out.println(number);
            }
        }
        // 이 코드를 Stream API로 바꾸면 다음과 같이 작성할 수 있다.
        numbers.stream()
            .filter(number -> number % 2 == 0)
            .forEach(number -> System.out.println(number));

        // stream(), filter(), forEach()는 각각 무슨 역할일까?
        // - stream(): 컬렉션의 데이터를 스트림으로 처리할 수 있게 해준다.
        // - filter(): 조건에 맞는 요소만 통과시킨다.
        // - forEach(): 통과한 각 요소에 지정한 동작을 실행한다.
        // 핵심은 데이터를 어떻게 처리할지 단계별로 연결하여 표현한다는 점이다.

        // filter()
        // 데이터를 필터링하는 연산이다.
        // filter()는 Predicate<T> 인터페이스를 구현하는 람다식을 받게 되어있으며,
        // Predicate<T> 인터페이스는 값을 받아 boolean 결과를 반환하는 명세을 갖는다.
        List<Integer> numbers2 = List.of(1, 2, 3, 4, 5);
        numbers2.stream()
            // 아래 람다식은 숫자 하나를 받아 20 이상인지를 판단하고,
            // 결과가 true인 요소만 남게된다
            .filter(number -> number >= 20)
            .forEach(number -> System.out.println(number));

        // map()
        // 각 요소를 다른 값으로 변환하는 연산이다.
        // 각 요소에 변환 함수를 적용하고 그 결과를 다음 단계로 전달한다.
        numbers.stream()
            // 숫자마다 2를 곱한다.
            .map(number -> number * 2)
            .forEach(number -> System.out.println(number));

        List<String> names = List.of("kim", "park", "lee");
        names.stream()
            // 문자를 대문자로 변환한다.
            .map(name -> name.toUpperCase())
            .forEach(name -> System.out.println(name));

        // 여러 연산을 연결하기
        // Stream API의 장점은 여러 작업을 연결할 수 있다는 점이다.
        List<Integer> numbers3 = List.of(1, 2, 3, 4, 5, 6);
        numbers3.stream()
            // 짝수인 요소만 골라내고
            .filter(number -> number % 2 == 0)
            // 10을 곱하고
            .map(number -> number * 10)
            // 각 요소를 출력한다
            .forEach(number -> System.out.println(number));
        
        // filter()와 map()은 데이터를 처리하여 스트림을 반환하는 중간 연산이고,
        // forEach()는 스트림을 마무리하는 최종 연산이다.
        // 중간 연산만 연결해 놓으면 실제 데이터 처리는 최종 연산이 호출될 때 수행된다.
        // 이를 지연 실행(lazy evaluation)이라고 한다.

        // Stream을 사용할 때 주의할 점
        // 스트림은 원본 컬렉션을 직접 수정하지 않는다.
        System.out.println(numbers);
        System.out.println(numbers2);
        System.out.println(numbers3);
        // 또한, 한 번 최종 연산을 수행한 스트림은 재사용할 수 없다.

        // 짝수를 필터링 해보자
        List<Integer> numbers4 = List.of(3, 8, 11, 14, 17, 20);
        numbers4.stream()
            .filter(number -> number % 2 == 0)
            .forEach(number -> System.out.println(number));

        // 각 요소에 3을 곱해보자
        numbers.stream()
            .map(number -> number * 3)
            .forEach(number -> System.out.println(number));

        // 짝수만 남긴 뒤, 5를 곱해보자
        List<Integer> numbers5 = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        numbers5.stream()
            .filter(number -> number % 2 == 0)
            .map(number -> number * 5)
            .forEach(number -> System.out.println(number));
        
        // Q1. filter()와 map()의 차이는?
        // A1. filter()는 요소를 걸러내고, map()은 요소를 다른 값으로 변환한다.
        // Q2. 중간 연산과 최종 연산의 차이는?
        // A2. 중간 연산은 스트림을 반환해 다음으로 이어지고, 최종 연산은 스트림을 끝낸다.
    }   
}   
