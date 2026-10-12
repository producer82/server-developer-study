import java.util.ArrayList;
import java.util.List;

public class Day22 {
    public static void main(String[] args) {
        // 컬렉션 프레임워크 입문
        
        // 배열의 한계
        // 배열은 데이터를 저장하기 편하지만, 크기가 고정되어 있다는
        // 단점이 있다.
        String[] names = new String[3];
        names[0] = "김철수";
        names[1] = "이영희";
        names[2] = "박민수";
        // 만약 회원 한 명이 더 추가된다면?
        // 새 배열을 만들고 기존 배열의 데이터를 옮겨야 한다.
        // 즉, 이런 구조는 데이터 수가 얼마나 될지 모르는 시스템에 부적합하다.

        // 컬렉션 프레임워크
        // 여러 데이터를 효율적으로 저장하고 관리할 수 있도록 Java가 제공하는
        // 자료구조와 관련 인터페이스, 클래스의 모음
        // 대표적으로 세 가지 인터페이스와 그에 따른 클래스 구현체들이 있다.
        // 1. List - 순서가 있는 데이터, 대표 구현체: ArrayList
        // 중복 허용, 순서 유지, 인덱스로 조회
        // 2. Set - 중복을 허용하지 않는 데이터, 대표 구현체: HashSet
        // 중복 불가, 인덱스로 조회 불가
        // 3. Map - 키와 값으로 데이터를 관리, 대표 구현체: HashMap
        // 키는 중복 불가, 키를 통해 값을 조회
        // 인터페이스와 실제 구현체를 구분하는 것이 중요하다.

        // ArrayList
        // <String>: Generic 문법, 리스트에 문자열을 저장하겠다는 의미다.
        ArrayList<String> nameList = new ArrayList<>();
        // List 인터페이스로 다형성을 사용할 수도 있다.
        List<String> nameList2 =  nameList;
        // add(value): 데이터 추가
        nameList.add("김철수");
        nameList.add("이영희");
        nameList.add("박민수");

        System.out.println(nameList);
        // get(index): 해당 인덱스의 데이터 조회
        System.out.println(nameList.get(0));

        // set(index, value): 해당 인덱스의 데이터 변경
        nameList.set(1, "최영희");
        // remove(value): 값에 해당하는 원소 제거
        nameList.remove("박민수");

        System.out.println(nameList);
        // size(): 현재 원소 개수 반환
        System.out.println("회원 수: " + nameList.size());

        // 간단한 회원 관리 프로그램
        // 오른쪽의 제네릭 타입은 생략할 수 있다.
        List<String> member = new ArrayList<>();
        member.add("김회원");
        member.add("최회원");
        member.add("이회원");
        member.add("박회원");
        System.out.println(member);
        System.out.println(member.get(1));
        member.set(1, "유회원");
        member.remove("박회원");
        System.out.println(member);
        System.out.println(member.size());

        for (String person : member) {
            System.out.println(person);
        }

        // Q. 배열과 ArrayList의 가장 큰 차이는?
        // A. 배열은 크기가 고정되어 있지만, ArrayList는 크기가 동적이다.
        // 다만, 내부적으로는 배열을 사용하기에, 공간이 부족하면 비용이 발생할 수 있다.
        // Q. List<String>으로 선언하고, new ArrayList<>()로 생성하는 이유는?
        // A. 인터페이스를 통한 다형성을 사용하기 위하여
        // Q. 회원 목록에 같은 이름을 두 번 추가하면 어떻게 될까?
        // A. ArrayList는 중복을 허용하기에 문제없이 추가될 것이다.
    }
}
