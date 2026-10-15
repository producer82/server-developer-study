import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Map;

public class Day26 {
    public static void main(String[] args) {
        // 컬렉션 종합 실습

        // 간단한 회원 관리 프로그램을 만들어보자
        // 비즈니스 요구사항:
        // 1. 회원 목록이 필요
        // - List<String>과 ArrayList를 사용
        // - 회원: "지민", "서준", "하윤", "지민"
        // - 전체 목록과 크기 출력
        // 2. 중복을 제거한 목록이 필요
        // - Set<String>을 사용
        // - 중복 제거 후의 크기를 출력
        // - "지민"이 존재하는지 확인해 출력한다.
        // 3. 점수 관리 기능이 필요
        // - Map<String, Integer>을 사용
        // - "지민"은 90, "서준"은 85, "하윤"은 95점이다.
        // - "지민"의 점수를 조회한다
        // - "서준"의 점수를 100으로 수정한다
        // - "하윤"의 점수를 삭제한다
        // - Map의 크기를 출력한다
        // - 전체 점수를 출력한다.
        List<String> members = new ArrayList<>();
        members.add("지민");
        members.add("서준");
        members.add("하윤");
        members.add("지민");
        System.out.println(members);
        System.out.println(members.size());

        Set<String> members2 = new HashSet<>(members);
        System.out.println(members2);
        System.out.println(members2.size());
        System.out.println(members2.contains("지민"));

        Map<String, Integer> scores = new HashMap<>();
        scores.put("지민", 90);
        scores.put("서준", 85);
        scores.put("하윤", 95);
        System.out.println(scores.get("지민"));
        scores.put("서준", 100);
        scores.remove("하윤");
        System.out.println(scores.size());
        System.out.println(scores.entrySet());

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Q1. 같은 값을 여러 번 저장할 수 있고, 입력 순서를 유지해야 한다면?
    // A1. List
    // Q2. List의 중복을 제거한 새 컬렉션을 만들 때 적절한 것은?
    // A2. new HashSet<>(members)
    // Q3. Map에서 같은 키로 put()을 다시 실행하면?
    // A3. 기존 값이 교체된다
    // Q4. HashSet과 HashMap의 공통적인 특징은?
    // A4. 해시 기반이며 순서를 보장하지 않는다.
}
