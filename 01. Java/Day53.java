import java.util.ArrayList;
import java.util.List;

class Product {
    private String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class Food extends Product {
    public Food(String name, int price) {
        super(name, price);
    }
}

class Electronic extends Product {
    public Electronic(String name, int price) {
        super(name, price);
    }
}

public class Day53 {
    public static void main(String[] args) {
        // 제네릭과 컬렉션 심화: 제네릭 와일드카드와 PECS 원칙
        // Spring과 실무 라이브러리의 API를 이해하기 위해 중요한 기능

        // 제네릭의 불공변성(Invariance)
        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4));
        // List<Number> numbers = integers; // 컴파일 에러
        // Integer는 Number를 상속한다. 그런데 왜 이개 안될까?
        // 이것을 제네릭의 불공변성(Invariance)이라고 한다.

        // 왜 허용하지 않을까?
        // 대입이 허용된다고 생각해보자.
        // numbers.add(3.14);
        // Integer 리스트에 Double이 들어가는 문제가 발생한다.
        // Integer value = integers.get(0);

        // 그렇다면, 상위 타입과 하위 타입의 관계를 활용하고 싶으면 어떻게 해야할까?
        // 와일드카드 ?를 사용한다.
        List<String> strings = new ArrayList<>(List.of("hello", "world"));
        List<?> list = new ArrayList<>();
        // List<?>는 어떤 타입이든 담긴 List를 의미한다.
        // 함수에도 어떤 타입의 list인지 상관없이 던질 수 있다.
        printAnything(integers);
        printAnything(strings);
        // list.add("Hello"); // 컴파일 에러
        // 대신, 타입을 알 수 없으니 null을 제외한 임의의 값은 추가할 수 없다.

        // 상한 경계 와일드카드 ? extends T
        List<? extends Number> numbers = new ArrayList<>();
        List<Double> doubles = new ArrayList<>(List.of(3.14, 1.52));
        // ? extends Number: Number 또는 Number의 하위 타입을 의미한다.
        // 함수에도 Number 또는 그 하위타입의 list를 무엇이든 던질 수 있다.
        printNumber(integers);
        printNumber(doubles);
        // numbers2.add(3.14); // 컴파일 에러
        // 마찬가지로 타입을 알 수 없으니 null을 제외한 임의의 값을 추가할 수 없다.

        // 하한 경계 와일드카드 ? super T
        List<? super Integer> numbers2 = new ArrayList<>();
        // ? super Integer: Integer 또는 Integer의 상위 타입을 의미한다.
        numbers2.add(10);
        numbers2.add(20);
        // 상위 타입은 Integer를 포함할 수 있기 때문에, 값을 안전하게 추가할 수 있다.
        // Integer value = numbers2.get(0); // 컴파일 에러
        // 다만 Integer로 바로 읽어올 수는 없다. Number나 Object일 수도 있기 때문이다.
        Object value = numbers2.get(0);
        // 가장 안전한 방법은 가장 위에 있는 Object를 사용하는 것이다.

        // PECS 원칙
        // 지금까지 배운 개념을 정리하는 원칙이다.
        // Producer: Extends, Consumer: Super
        // 데이터 제공자는 extends (읽어오는 역할)을 사용하고, 데이터 소비자(Consumer)는 super (추가하는 역할)을 사용한다.        
        
        // 상품 관리 시스템에 제네릭 와일드 카드를 적용해보자
        List<Food> foods = List.of(
            new Food("피자", 10000),
            new Food("스시", 20000)
        );
        printProducts(foods);
        List<Electronic> electronics = List.of(
            new Electronic("모니터", 150000),
            new Electronic("노트북", 1000000)
        );
        printProducts(electronics);
        List<Product> products = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        copyProducts(foods, products);
        products.forEach(
            product -> System.out.println(product.getName() + ":" + product.getPrice())
        );
        copyProducts(products, objects);
        objects.forEach(
            object -> System.out.println(((Product)object).getName() + ":" + ((Product)object).getPrice())
        );
        List<Product> varyProducts = List.of(
            new Food("햄버거", 9000),
            new Electronic("마우스", 30000)
        );
        System.out.println(calculateTotalPrice(varyProducts));

        // Q1. List<Integer>를 List<Number>에 바로 대입할 수 없는 이유는?
        // A1. 제네릭은 기본적으로 불공변이기 때문
        // Q2. ? extends Number의 주된 용도는?
        // A2. Number 계열 데이터를 안전하게 읽기
        // Q3. ? super Integer에 안전하게 추가할 수 있는 타입은?
        // A3. Integer, Number, Object
        // Q4. PECS에서 데이터를 소비하는 쪽에 사용하는 것은?
        // A4. super
    }   
    
    // PECS의 예제
    public static void copy(
        List<? extends Number> source, // 데이터를 제공하므로 extends
        List<? super Number> destination // 데이터를 받으므로 super
    ) {
        for (Number number : source) {
            destination.add(number);
        }
    }

    public static void printAnything(List<?> list) {
        for (Object value : list) {
            System.out.println(value);
        }
    }

    public static void printNumber(List<? extends Number> list) {
        for (Number value : list) {
            System.out.println(value);
        }
    }

    public static void printProducts(List<? extends Product> products) {
        products.forEach(
            product -> System.out.println(product.getName() + ":" + product.getPrice())
        );
    }

    public static void copyProducts(
        List<? extends Product> source,
        List<? super Product> destination
    ) {
        for (Product product : source) {
            destination.add(product);
        }
    }

    public static int calculateTotalPrice(List<? extends Product> products) {
        int totalPrice = products.stream()
            .mapToInt(Product::getPrice)
            .sum();
        return totalPrice;
    }
}
