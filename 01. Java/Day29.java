// Comparable<T>를 implements하여 기본 정렬 기준을 정의할 수 있다.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Student implements Comparable<Student> {
    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // 현재 객체와 other를 비교한다.
    @Override
    public int compareTo(Student other) {
        // compareTo는 정수 하나를 반환한다.
        // 음수: 현재 객체가 비교 대상보다 앞에 온다.
        // 0: 두 객체의 정렬 기준상 순서가 같다.
        // 양수: 현재 객체가 비교 대상보다 뒤에 온다.

        // 예제: 점수 오름차순 정렬
        // 직접 점수를 연산해서 비교할 수도 있지만, 범위가 매우 클 때 오버플로가 발생할 수 있다.
        // 따라서, Integer.compare()를 사용하는 것이 안전하다.
        return Integer.compare(this.score, other.score);
    }   

    @Override
    public String toString() {
        return name + ": " + score;
    }
}

public class Day29 {
    public static void main(String[] args) {
        // Comparable과 Comparator
        // 객체를 원하는 기준으로 비교하고 정렬해보자
        

        // 왜 객체 비교가 필요할까?
        // 숫자나 문자열은 Java가 기본적인 비교 방법을 제공한다.
        List<Integer> numbers = new ArrayList<>();
        numbers.add(30);
        numbers.add(10);
        numbers.add(20);
        // Collections.sort()를 사용해 컬렉션의 요소를 정렬할 수 있다.
        Collections.sort(numbers);
        System.out.println(numbers);
        
        // 그럼 객체는 어떤 기준으로 어떻게 정렬해야할까?
        Student student1 = new Student("철수", 85);
        // Java는 내가 만든 클래스에서 어떤 기준으로 객체를 비교해야 하는지
        // 자동으로 알 수 없다.
        // 따라서, 비교 기준을 직접 정의해야 한다.
        
        // Comparable
        // Comparable은 클래스 자체에 기본 정렬 기준을 정의할 때 사용하는 인터페이스다.
        // 예를 들어, 학생을 점수가 낮은 순서부터 정렬하고 싶다고 해보자.
        Student student2 = new Student("영희", 90);
        Student student3 = new Student("미영",70);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        Collections.sort(students);
        System.out.println(students);

        // Comparator
        // 학생을 점수순으로도 정렬하고 싶고, 이름순으로도 정렬하고 싶다면 어떻게 해야할까?
        // Comparable은 클래스의 기본 정렬 기준을 정의하기 때문에, 
        // 여러 기준을 번갈아 적용할 때 불편할 수 있다.
        // 이것을 해결하는 Comparator는 정렬 기준을 별도로 정의할 수 있다.
        
        // 아래 코드는 학생 두 명을 이름으로 사전식 정렬한다.
        students.sort((a, b) -> a.name.compareTo(b.name));
        System.out.println(students);
        // sort에게 비교 메서드를 넘긴다.
        // a.name.compareTo(b.name)이 음수면 a가 앞에 오고, 양수면 b가 앞에 온다.
        // 아래 코드는 학생을 점수 내림차순으로 정렬한다.
        students.sort((a, b) -> Integer.compare(b.score, a.score));
        System.out.println(students);  

        // Q1. Comparable과 Comparator의 차이는?
        // A1. Comparable과 Comparator 비교
        // Comparable: 기본 정렬 기준을 정의하고, 클래스 내부에 메서드로 선언한다.
        // Comparator: 별도의 정렬 기준을 정의하고, 외부에서 sort(비교식 메서드)로 호출한다.
        // Q2. compareTo()가 음수를 반환한다는 것은 무엇을 의미할까?
        // A2. 현재 객체가 비교 대상보다 앞으로 온다.
        // Q3. 학생의 기본 정렬은 점수순으로 유지하면서, 필요할때만 이름순으로 정렬하려면
        // 어떤 설계가 좋을까?
        // A3. 클래스의 Comparable을 점수순으로 두고, 이름순 정렬은 Comparator를 사용한다.
    }
}
