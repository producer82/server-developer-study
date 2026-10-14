import java.util.HashMap;
import java.util.Map;

public class Day25 {
    public static void main(String[] args) {
        // Map과 HashMap
        
        // Map이란?
        // Map은 키와 값을 쌍으로 저장한다.
        // Key: 데이터를 찾기 위한 식별자
        // Value: 키에 연결해서 저장하는 데이터
        // 같은 키는 중복해서 존재할 수 없으며, 서로 다른 키는 같은 값을 가질 수 있다.
        
        // Set과 Map의 차이
        // Set은 값 자체의 중복을 관리하지만, Map은 키의 중복을 관리한다.

        // 제네릭 타입으로 키의 타입과 값의 타입을 각각 지정한다.           
        // 왜 int가 아니라 Integer인가?: 제네릭에는 기본 자료형을 넣을 수 없기에, 
        // 래퍼 클래스를 사용한다.
        Map<String, Integer> scores = new HashMap<>();
        // 값을 추가, 수정할 때는 put(key, value)을 사용한다.
        scores.put("철수", 90);
        scores.put("영희", 95);
        scores.put("민수", 85);
        System.out.println(scores);
        // 값을 조회할때는 get(key)을 사용한다
        System.out.println(scores.get("철수"));
        scores.remove("영희");
        // size()로 크기를 조회하면 키-값 쌍의 개수를 반환한다.
        System.out.println(scores.size());

        // 같은 키를 다시 추가하면 어떤 일이 벌어질까?
        // 같은 키에 새 값을 넣으면 기존 값이 교체된다.
        scores.put("철수", 100);
        System.out.println(scores.get("철수"));

        // Map에 저장된 모든 데이터를 확인하고 싶으면 entrySet()을 활용할 수 있다.
        // HashMap 또한 순서를 보장하지 않으므로, 출력 순서는 달라질 수 있다.
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(
                // getKey(): 키를 반환한다.
                // getValue(): 해당 키에 연결된 값을 반환한다.
                entry.getKey() + ": " + entry.getValue()
            );
        }

        // containsKey()와 containsValue()의 차이
        // containsKey() : 키가 존재하는지 확인한다.
        // containsValue() : 값이 존재하는지 확인한다.
        System.out.println(scores.containsKey("영희"));
        System.out.println(scores.containsValue(100));
        System.out.println("==============================");

        // 학생 점수 관리하기
        Map<String, Integer> newScores = new HashMap<>();
        newScores.put("김철수", 90);
        newScores.put("이영희", 95);
        newScores.put("박민수", 85);
        System.out.println(newScores.get("김철수"));
        newScores.put("김철수", 100);
        System.out.println(newScores.containsKey("이영희"));
        System.out.println(newScores.containsValue(85));
        newScores.remove("박민수");
        System.out.println(newScores.size());

        for (Map.Entry<String, Integer> entry : newScores.entrySet()) {
            System.out.println(entry);
        }

        // Q1. 같은 키로 put()을 두 번 실행하면?
        // A1. 기존 값이 새 값으로 교체된다
        // Q2. Map<String, Integer>에서 String고 Integer는?
        // A2. 키와 값의 타입
        // Q3. HashMap은 삽입 순서를 보장할까?
        // A3. 순서를 보장하지 않는다.
        // Q4. containsKey("김철수")는 무엇을 확인할까?
        // A4. 김철수라는 키가 존재하는지
    }
}
