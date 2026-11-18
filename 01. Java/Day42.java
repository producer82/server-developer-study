import java.util.HashMap;
import java.util.Map;

public class Day42 {
    public static void main(String[] args) {
        // Map과 람다 활용하기
        // 백엔드 개발에서는 데이터를 키와 값 형태로 관리하는 일이 많다.

        // Map 복습
        // Map은 데이터를 Key와 Value 쌍으로 저장하는 자료구조다.
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Kim", 90);
        scores.put("Lee", 85);
        scores.put("Park", 95);

        // put(key, value): 데이터 저장
        // get(key): 데이터 조회
        // remove(key): 데이터 삭제
        // containsKey(key): key 존재 여부 확인
        // containsValue(value): value 존재 여부 확인
        System.out.println(scores);
        System.out.println(scores.get("Kim"));

        // forEach()로 Map 순회하기
        // Map은 Key와 Value를 함께 저장하기 때문에, forEach()로 두 값을 전달받을 수 있다.
        scores.forEach((name, score) -> {
            // 두 값이 한꺼번에 튀어나오기에 람다식이 자연스럽다.
            System.out.println(name + ":" + score);
        });

        // getOrDefault()로 기본값 지정하기
        // 존재하지 않는 키를 조회하면 Null이 출력된다.
        System.out.println(scores.get("Choi"));
        // getOrDefault()를 사용하면 기본값을 지정할 수 있다.
        System.out.println(scores.getOrDefault("Choi", 0));

        // putIfAbsent()로 중복 저장 방지하기
        // 이미 존재하는 Key에 값을 넣으면 기존 Value가 변경된다.
        // 이미 Key가 존재하기 때문에 저장되지 않는다.
        scores.putIfAbsent("Kim", 100);
        // Key가 존재하지 않기 때문에 값이 저장된다.
        scores.putIfAbsent("Choi", 80);
        System.out.println(scores);

        // computeIfAbsent()로 값 생성하기
        // Key가 없으면 람다로 계산된 값을 저장한다.
        scores.computeIfAbsent("Kim", key -> 90);
        scores.computeIfAbsent("Jung", key -> 70);
        System.out.println(scores);

        // Q1. Map은 어떤 형태로 데이터를 저장할까?
        // A1. Key와 Value 형태로 데이터를 저장한다.
        // Q2. getOrDefault()와 putIfAbsent()의 차이는?
        // A2. getOrDefault()는 값을 출력하고, putIfAbsent()는 값을 저장한다.
        // Q3. computeIfAbsent()는 어떤 상황에서 유리할까?
        // A3. 람다로 값을 계산하여 넣고싶을 때 유리하다.
        // Q4. put()과 putIfAbsent()의 차이는?
        // A4. put()은 Key가 있으면 값을 덮어쓰고, 
        // putIfAbsent()는 Key가 없을때만 값을 생성한다.
    }
}
