import java.util.ArrayList;
import java.util.List;

public class Day41 {
    public static void main(String[] args) {
        // 컬렉션과 람다 활용하기
        List<String> names = new ArrayList<>();
        names.add("Kim");
        names.add("Lee");
        names.add("Park");
        // 지금까지는 for문으로 요소를 출력했다.
        for (String name : names) {
            System.out.println(name);
        }
        // 같은 작업을 forEach()로도 할 수 있다.
        names.forEach(name -> System.out.println(name));
        // 람다식을 메서드 참조로 표현하면 더 간단하게 작성할 수 있다.
        names.forEach(System.out::println);

        // 조건에 맞는 요소 삭제하기
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        // removeIf()를 사용하면 조건을 만족하는 요소를 삭제할 수 있다.
        numbers.removeIf(number -> number % 2 == 0);
        System.out.println(numbers);

        // 요소 변환하기
        // 각 요소에 연산을 적용한다.
        List<String> techs = new ArrayList<>(List.of("java", "spring", "backend"));
        techs.replaceAll(tech -> tech.toUpperCase());
        System.out.println(techs);

        // Q1. forEach()는 어떤 역할을 할까?
        // A1. 각 요소에 지정한 동작을 하나씩 실행한다.
        // Q2. removeIf()와 replaceAll()의 차이는 무엇일까?
        // A2. removeIf()는 요소를 삭제할 수 있지만, replaceAll()는 변환만 할 수 있다.
        // Q3. List.of()로 만든 리스트에 removeIf()를 실행하면 어떤 문제가 발생할 수 있을까?
        // A3. List.of()로 만든 리스트는 수정할 수 없기 때문에 오류가 발생한다.
    }
}
