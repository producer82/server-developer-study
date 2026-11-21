import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

public class Day44 {
    public static void main(String[] args) {
        // Stream API 실전 활용: 데이터 필터링과 가공
        // 여러 단게의 데이터 처리 과정을 Stream으로 연결하자.

        // Stream은 크게 세 단게를 거친다.
        // 1. 컬렉션에서 stream() 생성
        // 2. 중간 연산, filter(), map(), sorted() 등
        // 3. 최종 연산, collect, forEach(), count() 등

        // sorted(): 데이터 정렬하기
        List<Integer> numbers = Arrays.asList(5, 2, 8 , 1, 3);
        numbers.stream()
            .sorted()
            .forEach(System.out::println);
        // 내림차순으로 정렬하려면 다음과 같이 해볼 수 있다
        // Comparator.reverseOrder()를 사용한다.
        numbers.stream()
            .sorted(Comparator.reverseOrder())
            .forEach(System.out::println);
        
        // distinct(): 중복 제거하기
        List<Integer> numbers2 = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
        numbers2.stream()
            .distinct()
            .forEach(System.out::println);

        // limit(), skip(): 데이터 일부만 가져오기
        // limit(n)은 앞에서부터 n개를 가져온다.
        List<Integer> numbers3 = Arrays.asList(10, 20, 30, 40, 50);
        numbers3.stream()
            .limit(3)
            .forEach(System.out::println);
        // skip(n)은 앞에서부터 n개를 건너 뛴다.
        numbers3.stream()
            .skip(2)
            .forEach(System.out::println);
        // 두 기능을 연결하면 페이지 단위로 데이터를 가져오는 것 같은 처리가 가능하다.
        numbers3.stream()
            .skip(2)
            .limit(2)
            .forEach(System.out::println);

        // findFirst()와 anyMatch(): 조건에 맞는 데이터 찾기
        // findFirst(): 조건에 맞는 첫 번째 요소를 찾아 Optional로 반환한다.
        Optional<Integer> result = numbers3.stream()
            .filter(n -> n >= 25)
            .findFirst();
        result.ifPresent(System.out::println);
        // anyMatch(): 조건에 맞는 요소가 하나라도 있는지 확인하고, boolean으로 반환한다.
        boolean result2 = numbers.stream()
            .anyMatch(n -> n > 35);
        System.out.println(result2);

        // 숫자 데이터를 처리하기
        List<Integer> numbers4 = Arrays.asList(
            5, 2, 8, 2, 10, 3, 8, 1, 6, 4
        );
        List<Integer> result3 = numbers4.stream()
            .distinct()
            .sorted()
            .filter(n -> n > 5)
            .collect(Collectors.toList());
        System.out.println(result3);

        // 학생 목록에서 80점 이상인 학생의 이름을 점수 내림차순으로 출력해보기
        List<Student> students = Arrays.asList(
            new Student("Kim", 90),
            new Student("Lee", 75),
            new Student("Park", 85),
            new Student("Choi", 95),
            new Student("Jung", 60)
        );
        List<String> result4 = students.stream()
            // 80점 이상인 학생만 남긴다
            .filter(student -> student.getScore() >= 80)
            // 점수 기준 내림차순으로 정렬한다.
            .sorted((s1, s2) -> s2.getScore() - s1.getScore())
            // Student 객체를 이름 문자열로 변환한다.
            .map(Student::getName)
            // 저장한다.
            .collect(Collectors.toList());
        System.out.println(result4);
        
        // 조건 검색하기
        boolean result5 = students.stream()
            .allMatch(student -> student.getScore() >= 60);
        boolean result6 = students.stream()
            .anyMatch(student -> student.getScore() >= 90);
        boolean result7 = students.stream()
            .noneMatch(student -> student.getScore() < 50);
        Optional<Student> result8 = students.stream()
            .filter(student -> student.getScore() >= 80)
            .findFirst();
        System.out.println(result5);
        System.out.println(result6);
        System.out.println(result7);
        result8.ifPresent(student -> System.out.println(student.getName()));

        // Q1. sorted()와 distinct()는 어떤 역할인가?
        // A1. sorted()는 정렬하고 distinct()는 중복을 제거한다.
        // Q2. limit(3)과 skip(3)의 차이는?
        // A2. limit(3)은 앞에서 3개를 가져오고, skip(3)은 앞에서 3개를 건너뛴다.
        // Q3. findFirst()의 반환 타입이 Optional인 이유는?
        // A3. 찾았는데 값이 없을 가능성을 안전하게 표현하기 위해서
        // Q4. anyMatch()와 allMatch()의 차이는?
        // A4. anyMatch()는 하나라도 만족하는지 검사하고, allMatch()는 모두 만족하는지 검사한다.
        // Q5. Stream에서 filter(), map(), sorted()를 연결해서 사용하는 장점은?
        // A5. 여러 연산을 연결하여 원하는 데이터를 만들어낼 수 있다.
    }
}
