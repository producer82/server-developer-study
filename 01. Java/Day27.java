import java.util.ArrayList;
import java.util.List;

class Box {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

class GenericBox<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }    
}

public class Day27 {
    public static void main(String[] args) {
        // 제네릭(Generic)
        // 제네릭은 클래스 내부에서 사용할 타입을 외부에서 지정하는 기법이다.

        // 왜 필요할까?
        ArrayList list = new ArrayList();
        list.add("철수");
        list.add("영희");
        list.add(100);
        // 지금은 잘 돌아가지만, 만약 (String)list.get(2)가 실행 되어버린다면?
        // ClassCastException으로 프로그램이 터진다.
        String name = (String)list.get(0);
        System.out.println(name);
        // 들어갈때는 어떤 타입이든 들어가지만, 꺼낼때는 타입을 직접 확인하고 꺼내야한다.
        // 이런 문제를 줄이기 위해 제네릭을 사용한다.

        // 제네릭을 통해 이 리스트가 문자열만 다룬다는 것을 컴파일러에게 알려준다.
        List<String> names = new ArrayList<>();
        names.add("철수");
        names.add("영희");
        // names.add(100); // 컴파일 오류가 발생한다.
        name = names.get(0);
        System.out.println(name);

        // 제네릭의 핵심 장점
        // 타입 안정성: 잘못된 타입의 데이터가 들어가는 것을 방지
        // 형변환 감소: 데이터를 꺼낼 때 직접 형변환할 필요가 줄어듦
        // 재사용성: 다양한 타입을 처리하는 클래스를 만들 수 있음
        // 아래의 세 리스트는 모두 ArrayList를 사용하지만, 각자 다른 타입의 데이터를
        // 관리한다.
        List<String> tags = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        tags.add("tags");
        scores.add(100);
        prices.add(29.99);

        // 제네릭 클래스 만들기
        // 일반적인 클래스는 클래스가 지정한 타입의 데이터만 저장할 수 있다.
        Box box = new Box();
        box.setValue("string");
        System.out.println(box.getValue());
        // 제네릭 클래스는 외부에서 타입을 지정해 여러 타입에 재사용 할 수 있다.
        // 제네릭에서 자주 사용하는 매개변수 이름은 다음과 같다.
        // <T>: 일반적인 타입, <E>: 컬렉션의 원소, <K>: 키, <V>: 값
        GenericBox<Integer> integerBox = new GenericBox<>();
        integerBox.setValue(100);
        GenericBox<String> stringBox = new GenericBox<>();
        stringBox.setValue("hello");
        // stringBox.setValue(100); // 컴파일되지 않는다.
        System.out.println(integerBox.getValue());
        System.out.println(stringBox.getValue());

        // 제네릭 메서드 맛보기
        // 클래스 전체가 아니라, 특정 메서드에서만 제네릭을 사용할 수도 있다.
        // 제네릭을 사용한 메서드도 마찬가지로 여러가지 타입을 사용할 수 있다.
        printValue("철수");
        printValue(100);
        printValue(3.14);
        
        // Q1. 제네릭을 사용하는 가장 중요한 이유는 무엇일까?
        // A1. 클래스에 아무 타입의 데이터나 들어가는 것을 막기 위해
        // 부수적으로는 한 클래스에서 여러 타입을 지원해 재사용률을 높이기 위해
        // Q2. Box<T>에서 T는 무엇을 의미할까?
        // A2. Type
        // Q3. Box<String>과 Box<Integer>는 같은 클래스를 사용하는데도 어떤 차이가 있을까?
        // A3. Box<String>에는 String만, Box<Integer>에는 Integer만 들어갈 수 있다.
        // Q4. List<int>가 아니라 List<Integer>를 사용하는 이유는?
        // A4. 제네릭에는 기본 타입을 사용하지 못하기 때문에 래퍼 클래스를 사용한다.
    }
    
    // <T>는 메서드의 반환 타입 앞에 선언되어 있다.
    public static <T> void printValue(T value) {
        System.out.println(value);
    }
}
