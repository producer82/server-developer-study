class Pair<K, V> {
    // K와 V는 Java가 특별하게 정해놓은 키워드가 아니다.
    // 개발자가 다른 이름을 사용해도 되지만, 관례적으로 작성한다.
    private K key;
    private V value;
    
    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

public class Day28 {
    public static void main(String[] args) {
        // 제네릭 심화

        // 제네릭 타입 매개변수 여러 개 사용하기
        // 실제 프로그램에서는 서로 다른 타입의 데이터를 한 쌍으로
        // 관리해야 할 때가 생긴다.
        // 예를 들면... 
        // 사용자 이름: String, 사용자 나이: Integer
        // 상품 이름: String, 상품 가격: Integer
        // Pair는 타입을 2개 받을 수 있다.        
        Pair<String, Integer> user = new Pair<>("철수", 20);
        System.out.println(user.getKey());
        System.out.println(user.getValue());

        Pair<String, String> product = new Pair<>("노트북", "삼성");
        System.out.println(product.getKey());
        System.out.println(product.getValue());

        // 제네릭 타입 제한하기
        // 숫자를 받아서 실수로 만드는 메서드를 만들고 싶다고 해보자.
        // 기존에 배운 제네릭 메서드는 다양한 타입을 받을 수 있엇다.
        // 하지만, 숫자(Integer, Long, Double 등...)만 허용하고 싶다면 어떻게 해야할까?
        System.out.println(toDouble(10));
        System.out.println(toDouble(3.14));

        // Q1. Pair<K, V>에서 타입 매개변수를 두 개 사용하는 이유는?
        // A1. 두 개의 서로 다른 타입을 관리해야 할 필요가 생겼기 때문
        // Q2. <T extends Number>에서 extends의 의미는?
        // A2. 타입의 범위를 제한한다.
        // Q3. String은 왜 <T extends Number>를 사용하는 메서드에 전달할 수 없을까?
        // A3. String은 Number에 속하지 않으니까
    }
    
    // T는 Number이거나 그 하위 타입이어야 한다.
    // 클래스의 extends: 실제 상속 관계를 선언하는 것
    // 제네릭의 extends: T를 허용할 타입의 범위를 제한하는 것
    // 제네릭은 인터페이스를 기준으로도 타입을 제한할 수 있다.
    // 예를 들면: <T extends Comparable<T>> -> Comparable<T>를 구현한 타입만 들어올 수 있다.
    public static <T extends Number> double toDouble(T value) {
        // T는 Number의 공통 기능인 doubleValue()를 사용할 수 있다.
        return value.doubleValue();
    }
}
