import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day23 {
    public static void main(String[] args) {
        // ArrayList vs LinkedList
        // List 구현체를 선택하는 기준은?

        // ArrayList의 내부
        // ArrayList는 내부적으로 배열을 이용해 데이터를 저장한다.
        List<String> arrayList = new ArrayList<>();
        arrayList.add("김철수");
        arrayList.add("이영희");
        arrayList.add("박민수");
        // 따라서, 인덱스를 통해 원소를 조회할 수 있다.
        System.out.println(arrayList.get(2));
        // 이 방법은 일반적으로 O(1)의 시간이 걸린다.
        // 원소 수가 늘어나도 해당 위치에 직접 접근할 수 있기 때문이다.
        // 그렇다면 중간에 데이터를 추가하면?
        arrayList.add(1, "최영희");
        System.out.println(arrayList);
        // 이런 경우에는 기존의 데이터를 한 칸씩 뒤로 이동시켜야만 한다.
        // 따라서 중간 삽입이나 삭제는 원소가 많을 수록 비용이 커질 수 있다.

        // LinkedList의 내부
        // LinkedList는 배열처럼 원소들을 연속적인 인덱스로 관리하는 대신, 각 원소를 노드(Node)로 관리한다.
        // 그리고 각 노드는 다른 노드로 연결되는 참조를 가지고 있다.
        List<String> linkedList = new LinkedList<>();
        // Java의 LinkedList는 이중 연결 리스트로, 각 노드가 이전 노드와 다음 노드로 연결된다.
        // 따라서, 원하는 원소를 찾으려면 연결을 따라가야 하므로 일반적으로 O(n)의 시간이 걸린다.
        // 다만, 이미 노드를 찾은 상태라면 O(1)의 시간이 걸린다.
        // 중간 삽입과 삭제 또한 그 위치를 찾는데에 시간이 걸려 오래걸릴 수 있다.

        // 실무에서는 무엇을 더 많이 사용할까?
        // 일반적인 목록을 관리할 때는 ArrayList를 기본 선택으로 삼는 경우가 많다.
        // 1. 인덱스 조회가 빠르다
        // 2. 메모리 구조가 비교적 단순하다.
        // 3. 순차적으로 데이터를 읽을 때 유리한 경우가 많다.
        // LinkedList는 특정한 삽입, 삭제 패턴이나 Deque 사용 등 적합한 목적이 있을 때
        // 고려하면 좋다.

        // 두 구현체의 사용법은 거의 같다.
        linkedList.add("김철수");
        linkedList.add("이영희");
        linkedList.add("박민수");
        linkedList.add(1, "최영희");
        System.out.println(linkedList);
        System.out.println(arrayList);
        // 같은 인터페이스를 사용한다 -> 공통된 메서드를 그대로 사용할 수 있다.
        // 다만, 내부 동작이나 성능까지 같다는 것을 의미하지는 않는다.

        // 회원 목록을 두 가지 방식으로 관리하기
        List<String> members1 = new ArrayList<>();
        List<String> members2 = new LinkedList<>();
        
        members1.add("김철수");
        members1.add("이영희");
        members1.add("박민수");
        members1.add("배대지");
        members1.add("부닥지");

        members2.add("김철수");
        members2.add("이영희");
        members2.add("박민수");
        members2.add("배대지");
        members2.add("부닥지");
        
        members1.add(1, "김지영");
        members2.add(1, "김지영");

        System.out.println(members1.get(2));
        System.out.println(members2.get(2));
        
        members1.remove("부닥지");
        members2.remove("부닥지");

        System.out.println(members1);
        System.out.println(members2);
    }
}

