import java.util.HashSet;
import java.util.Set;

public class Day24 {
    public static void main(String[] args) {
        // Set과 HashSet

        // List와 Set의 차이
        // Set은 중복을 허용하지 않기에, 이미 존재하는 값은 추가해도 저장되지 않는다.
        // 예를 들면, 특정 이메일이 이미 등록되어 있는지 확인하는 등에 사용할 수 있다.
        Set<String> names = new HashSet<>(); // 다형성 사용
        names.add("철수");
        names.add("영희");
        names.add("민수");
        names.add("철수");
        // 철수를 두 번 추가했지만, 한 번만 저장됐다. 크기 또한 3이다.
        // HashSet은 순서를 보장하지 않기 때문에, 반드시 삽입 순서와 출력 순서가 일치하지
        // 는 않는다.
        System.out.println(names);
        System.out.println(names.size());
        // contains(value) : 값이 존재하는지 boolean으로 반환한다.
        System.out.println(names.contains("철수"));
        names.remove("민수");
        System.out.println(names.contains(("민수")));
        System.out.println(names.size());
        // HashSet은 내부적으로 해시를 활용해 데이터를 관리한다.
        // 기본 자료형이나, equals() 및 hashCode()가 적절히 구현된 객체는 중복 여부를 올바르게
        // 판단할 수 있다.
        // 따라서, 객체를 HashSet에 넣을때는 equals()와 hashCode()를 제대로 구현해야 한다.

        // 과제 (코드 생략)
        // Q1. 같은 이름을 두 번 추가했는데 데이터 개수가 늘어나지 않는 이유는?
        // A1. Set이 중복을 허용하지 않기 때문에, 같은 값은 추가해도 들어가지 않는다.
        // Q2. HashSet에 "이영희"가 존재한다면, contains("이영희")의 반환 값은?
        // A2. Set에 이영희가 있으니 true를 반환한다.
        // Q3. remove("박민수") 실행 후 데이터 개수는 몇 개일까?
        // A3. 4개에서 하나 지워졌으니 3개가 된다.
        // Q4. 출력은 삽입 순서와 같다는 것을 보장할 수 있는가?
        // A4. HashSet은 데이터의 순서를 보장하지 않는다.
    }
}
