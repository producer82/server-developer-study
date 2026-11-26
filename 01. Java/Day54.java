import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

class Product {
    private int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class HashProduct {
    private int id;
    private String name;

    public HashProduct(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof HashProduct other)) {
            return false;
        }

        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class Student {
    private String id;
    private String name;
    private int score;

    public Student(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    
    @Override
    public String toString() {
        return id + "," + name + "," + score;
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (!(obj instanceof Student other)) {
            return false;
        }
        
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

public class Day54 {
    public static void main(String[] args) {
        // Collections 선택 기준과 성능
        // 특정 자료구조를 왜 선택해야 하는지, 어떤 상황에서 성능 차이가 발생하는지 알아보자.
        // -> 데이터가 수만 ~ 수백만개로 늘어나면 처리 성능이 달라질 수 있다.

        // 시간 복잡도: 자료구조 성능 비교의 기준
        // 데이터의 개수가 증가할 때 연산량이 얼마나 증가하는가?
        // - O(1): 상수 시간
        // 예: 배열 인덱스 접근
        // - O(log n): 로그 시간
        // 예: 이진 탐색
        // - O(n): 선형 시간
        // 예: 순차 탐색
        // - O(n long n): 선형 로그
        // 예: 효율적인 정렬
        // - O(n^2): 제곱시간
        // 예: 이중 반복문 비교

        // 만약 데이터가 10만개라면...
        // - O(1) -> 1번의 접근으로 처리할 수 있다.
        // - O(log n) -> 약 17번
        // - O(n) -> 100,000번
        // - O(n^2) -> 100억번
        // 다만, 실제 실행시간과 성능에는 메모리 접근, 캐시, 객체 생성, JVM 등의 
        // 영향을 고려해야 한다.

        // ArrayList
        // ArrayList는 내부적으로 배열을 사용한다.
        List<String> names = new ArrayList<>();
        names.add("Kim");
        names.add("Lee");
        names.add("Park");
        names.add("Choi");
        // 주요 연산에 따른 시간 복잡도
        // - get(index): O(1)
        // 인덱스로 바로 접근한다.
        // - set(index, value): O(1)
        // 해당 위치의 값만 변경한다.
        // - 마지막 add(index, value): 평균 O(1)
        // 대부분 바로 추가된다.
        // - 중간 add(index, value): O(n)
        // 뒤쪽 요소를 이동시켜야 한다.
        // - remove(index): O(n)
        // 뒤쪽 요소를 이동시켜야 한다.
        // - contains(): O(n)
        // 처음부터 순차 검색한다.
        // ArrayList는 중간에 끼어있는 요소를 수정하게될 경우 느려진다.

        // ArrayList의 내부 확장
        // ArrayList는 배열을 사용하는데, 배열은 처음 만들어진 크기를 마음대로 늘릴 수 없다.
        // 근데 어떻게 ArrayList는 데이터 추가가 가능할까?
        // -> ArrayList 내부에 실제 데이터 개수인 size와 배열 용량 capacity가 
        // 따로 존재하기 때문
        // size = 3, capacity = 5인 ArrayList를 가정해보자.
        // [10, 20, 30, 빈공간, 빈공간] 
        // 데이터가 더 들어와 size가 capacity를 넘게되면, 기존 데이터를 복사하여
        // 더 큰 배열을 만들어 사용한다.
        
        // 그렇다면 시간 복잡도가 O(n)인 경우가 생기지 않나?
        // -> 그러나 capacity가 점점 늘어나 결국 평균적으로는 O(1)이 된다.
        // 이것을 Armotized O(1)이라고 한다.

        // LinkedList
        // LinkedList는 배열이 아니라 노드들이 서로 연결된 구조다.
        // 각 노드는 저장할 데이터와 다음, 이전 노드의 주소를 가지고 있다.
        // Java는 이중 LinkedList를 사용한다.
        // 주요 연산에 따른 시간 복잡도
        // - 앞/뒤 추가: O(1)
        // 연결만 변경
        // - 앞/뒤 제거: O(1)
        // 연결만 변경
        // - get(index): O(n)
        // 노드를 찾아 이동해야함
        // - 중간 삽입: O(n)
        // 인덱스만 주어진 경우 해당 위치까지 이동해야함
        // - 중간 제거: O(n)
        // 인덱스만 주어진 경우 해당 위치까지 이동해야함
        // - contains(): O(n)
        // 순차 탐색
        
        // ArrayList와 LinkedList 비교
        // 대부분의 일반적인 경우 ArrayList를 먼저 선택하는 것이 합리적이다.
        // - 인덱스 접근이 빠름
        // - 메모리 효율이 좋음
        // - CPU 캐시 활용에 유리함
        // - 일반적인 조회 및 순회에 성능이 우수
        // - 다양한 실무 API에서 기본적인 리스트로 활용하기 적합

        // HashSet 
        // 내부적으로 HashMap을 활용하는 구조를 가지고 있다.
        Set<String> names2 = new HashSet<>();
        names2.add("Kim");
        names2.add("Lee");
        names2.add("Park");
        System.out.println(names2.contains("Lee"));
        // 주요 연산에 따른 시간 복잡도
        // - add(): O(1)
        // 최악의 경우 O(n) 또는 O(log n)
        // - remove(): O(1)
        // 최악의 경우 O(n) 또는 O(log n)
        // - contains()
        // 최악의 경우 O(n) 또는 O(log n)
        // 데이터 검색은 평균 O(1)의 시간 복잡도를 가진다.

        // 왜? -> Hashing
        // 데이터의 hashCode()를 이용해 저장할 위치를 결정하는 과정
        // 1. "Lee" 검색
        // 2. hashCode() 계산
        // 3. Hash Bucket 찾기
        // 4. 데이터 접근
        // 전체 데이터를 검색하지 않고 버킷을 찾아 접근하기 때문에 평균 O(1)이다.
        // 다만, 최악의 경우 여러 데이터가 동일한 버킷에 몰릴 수도 있기 때문에
        // 검색 시간이 늘어날 수도 있다.
        
        // HashMap
        // Key와 Value를 저장하는 자료구조다.
        // - Key는 중복될 수 없다.
        // - Value는 중복될 수 있다.
        // - Key가 중복되면 기존 Value를 덮어쓴다.
        // - 기본 HashMap은 순서를 보장하지 않는다.
        // - null Key 하나와 null Value 여러개를 허용함
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Kim", 90);
        scores.put("Lee", 85);
        scores.put("Park", 100);
        scores.put("Kim", 40);
        System.out.println(scores.get("Lee"));
        // 주요 연산에 따른 시간 복잡도
        // put(key, value): O(1)
        // get(key): O(1)
        // containsKey(): O(1)
        // remove(key): O(1)
        // 마찬가지로 Hashing을 활용한다.

        // equals()와 hashCode()의 중요성
        // 상품 ID가 1인 객체 두 개를 생각해보자
        Product p1 = new Product(1, "키보드");
        Product p2 = new Product(1, "키보드");
        // 두 객체를 같은 상품으로 보고싶다.
        // 하지만, equals()와 hashCode()를 구현하지 않으면 기본적으로 
        // 서로 다른 객체로 취급될 수 있다.
        Set<Product> products = new HashSet<>();
        products.add(p1);
        products.add(p2);
        System.out.println(products.size()); // 상품이 2개라고 나온다.
        // equals()와 hashCode()를 오버라이드하면 같은 상품으로 취급할 수 있다.
        Set<HashProduct> hashProducts = new HashSet<>();
        hashProducts.add(new HashProduct(1, "키보드"));
        hashProducts.add(new HashProduct(1, "키보드"));
        System.out.println(hashProducts.size()); // 상품이 1개라고 나온다.
        // equals()로 같은 객체라고 판단한다면 반드시 hashCode()도 같아야 한다.
        // 이 규칙을 어기면 중복 판단이나 검색 결과가 깨질 수 있다.

        // Queue
        // 먼저 들어온 데이터가 먼저 나가는(FIFO) 자료구조다
        // 주문 처리, 작업 처리, 메시지 처리 등에 활용할 수 있다.
        Queue<String> queue = new ArrayDeque<>();
        queue.offer("주문1"); // 데이터 넣기
        queue.offer("주문2");
        queue.offer("주문3");
        System.out.println(queue.poll()); // 데이터 꺼내기
        System.out.println(queue.poll());

        // Deque(Double Ended Queue)
        // 앞과 뒤 양쪽에서 삽입과 삭제가 가능한 자료구조다.
        Deque<String> deque = new ArrayDeque<>();
        deque.addFirst("A"); // 데이터 넣기
        deque.addLast("B");
        deque.addLast("C");
        System.out.println(deque.removeFirst()); // 앞에서 빼기
        System.out.println(deque.removeLast()); // 뒤에서 빼기

        // 어떤 자료구조를 선택해야 할까?
        // ArrayList
        // - 순서가 필요하고, 조회와 순회가 많을 때
        // HashSet
        // - 중복 제거 또는 존재 여부 확인이 중요할 때
        // HashMap
        // - Key로 빠르게 데이터를 조회할 때
        // LinkedList
        // - Deque 기능 등 특정한 삽입/삭제 패턴이 필요할 때
        // 각 상황에서는 어떤 자료구조를 써야할까?
        // 상품 전체 목록 조회: ArrayList
        // 중복된 회원 ID 검사: HashSet
        // 회원 ID -> 회원 정보: HashMap
        // 상품 ID -> 상품 객체: HashMap
        // 순서대로 처리할 대기 작업: Queue
        // 가장 최근 요청부터 처리할때: Deque, Stack
        
        // ArrayList 학생 관리 프로그램을 작성해보자
        // 잦은 추가와 순회, 인덱스 조회가 필요하므로 ArrayList 사용
        List<Student> students = new ArrayList<>();
        // 1. 학생 5명 등록
        students.add(new Student("S001", "Kim", 95));
        students.add(new Student("S002", "Park", 65));
        students.add(new Student("S003", "Lee", 75));
        students.add(new Student("S004", "Choi", 90));
        students.add(new Student("S005", "Jegal", 85));
        // 2. 전체 학생 출력
        students.forEach(System.out::println);
        // 3. 인덱스로 학생 조회
        System.out.println(students.get(2));
        // 4. 평균 점수 계산
        double averageScore = students.stream()
            .mapToInt(Student::getScore)
            .average()
            .orElseThrow();
        System.out.println(averageScore);
        // 5. 점수 80점 이상 학생 출력
        List<Student> highScores = students.stream()
            .filter(student -> student.getScore() >= 80)
            .toList();
        highScores.forEach(System.out::println);

        // HashSet으로 학생 ID를 관리해보자
        // 중복을 허용하지 않고 빠른 검색이 필요하므로 HashSet 사용
        Set<String> studentIds = new HashSet<>();
        // 이미 존재하는 ID라면 등록 거부
        setStudentIds(studentIds, "S001");
        setStudentIds(studentIds, "S002");
        setStudentIds(studentIds, "S001");

        // HashMap으로 학생 조회를 해보자
        // Key를 통한 값의 조회가 필요하므로 HashMap 사용
        Map<String, Student> studentMap = new HashMap<>();
        studentMap = students.stream()
            .collect(Collectors.toMap(
                Student::getId, student -> student)
            );
        System.out.println(studentMap.get("S001"));

        // Queue를 통해 대기 학생을 처리해보자
        // 먼저 온 학생이 먼저 처리되야하므로 Queue
        Queue<String> waitingQueue = new ArrayDeque<>();
        waitingQueue.offer("S001");
        waitingQueue.offer("S002");
        waitingQueue.offer("S003");
        System.out.println("대기열: " + waitingQueue);
        System.out.println("처리 학생: " + waitingQueue.poll());
        System.out.println("처리 학생: " + waitingQueue.poll());
        System.out.println("처리 학생: " + waitingQueue.poll());
        
        // 다음 문제를 풀어보자
        // Q1. 회원 ID의 중복 여부를 빠르게 확인하고 싶다
        // A1. HashSet
        // Q2. 학생 ID를 Key로 사용하여 학생 정보를 조회하고 싶다
        // A2. HashMap
        // Q3. 상품 목록을 저장하고 순서대로 출력하며 인덱스로 접근한다.
        // A3. ArrayList
        // Q4. 먼저 들어온 주문부터 처리해야 한다
        // A4. Queue
        // Q5. 중복 상품 코드를 제거하고 고유한 코드만 관리하고싶다
        // A5. HashSet
    }

    public static void setStudentIds(Set<String> studentIds, String registerId) {
        if (studentIds.add(registerId)) {
            System.out.println(registerId + " 등록 성공");
        }
        else {
            System.out.println(registerId + " 등록 실패: 이미 존재하는 ID");
        }
    }
}
