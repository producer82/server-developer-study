import java.util.HashMap;
import java.util.Map;

public class Day43 {
    public static void main(String[] args) {
        // Map의 고급 활용
        
        // entrySet(): Map의 모든 키와 값 다루기
        // 각 키와 값을 하나의 Entry로 묶어서 반환한다.
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Kim", 90);
        scores.put("Lee", 85);
        scores.put("Park", 95);
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        // forEach()로도 같은 작업을 할 수 있다.
        scores.forEach((name, score) -> {
            System.out.println(name + ":" + score);
        });

        // merge(): 기존 값에 새로운 값 합치기
        // 특정 키에 값이 없으면 새 값을 넣고, 이미 값이 있으면 기존 값과 합친다.
        // Kim의 기존 값에 10을 더한다.
        scores.merge("Kim", 10, (oldValue, newValue) -> oldValue + newValue);
        // Choi라는 Key가 없기에 새로 80을 저장한다.
        scores.merge("Choi", 80, (oldValue, newValue) -> oldValue + newValue);
        System.out.println(scores);
        // 값을 누적해야 하는 상황에 좋다.

        // compute(): 기존 값이 있든 없든 새 값 계산하기
        // 특정 키의 값을 계산해서 새로 저장한다.
        scores.compute("Kim", (key, value) -> value + 10);
        scores.compute("Lee", (key, value) -> 80);
        System.out.println(scores);

        // 상품 판매량 관리 프로그램 작성해보기
        Map<String, Integer> product = new HashMap<>();
        product.put("Laptop", 3);
        product.put("Mouse", 5);
        product.put("Keyboard", 2);
        product.merge("Laptop", 2, (oldValue, newValue) -> (oldValue + newValue));
        product.merge("Monitor", 4, (oldValue, newValue) -> (oldValue + newValue));
        for (Map.Entry<String, Integer> entry : product.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        // 단어 등장 횟수 세기
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = {
            "Java", "Spring", "Java", "Python", "Spring", "Java"
        };
        for (String word : words) {
            wordCount.merge(word, 1, (oldValue, newValue) -> oldValue + newValue);
        }
        wordCount.forEach((key, value) -> System.out.println(key + ": " + value));
        
        // Q1. entrySet()은 어떤 상황에서 사용하면 좋을까?
        // A1. 맵의 모든 Key와 Value를 반복문으로 꺼내야하는 경우
        // Q2. computeIfAbsent()와 compute()의 가장 큰 차이는?
        // A2. computeIfAbsent()는 Key가 없거나 null일때만 동작하고, compute()는 무조건 동작한다.
        // Q3. merge()는 기존 키가 존재할 때와 존재하지 않을때 어떻게 동작할까?
        // Q3. 기존 Key가 존재하면 지정한 식대로 값을 합치고, 존재하지 않으면 새로 값을 넣는다.
        // Q4. 단어 등장 횟수를 계산할 때 merge()가 유용한 이유는?
        // A4. 처음 등장하는 단어를 처리할 수 있고, 값을 누적할 수도 있다.
    }
}
