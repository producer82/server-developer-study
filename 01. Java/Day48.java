import java.util.Comparator;
import java.util.List;

class Order {
    private List<String> products;

    public Order(List<String> products) {
        this.products = products;
    }

    public List<String> getProducts() {
        return products;
    }
}

class Order2 {
    private List<Integer> prices;

    public Order2(List<Integer> prices) {
        this.prices = prices;
    }

    public List<Integer> getPrices() {
        return prices;
    }
}

class User {
    private String name;
    private List<Order> orders;

    public User(String name, List<Order> orders) {
        this.name = name;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }
}

class User2 {
    private String name;
    private List<String> hobbies;

    public User2(String name, List<String> hobbies) {
        this.name = name;
        this.hobbies = hobbies;
    }

    public String getName() {
        return name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }
}

public class Day48 {
    public static void main(String[] args) {
        // flatMap()과 중첩 데이터 처리
        // map()의 한계를 이해하고, 중첩 데이터를 flatMap()으로 처리하자.

        // 1. map()의 한계
        // List가 들어 있는 List를 중첩 데이터라고 한다.
        List<List<String>> names = List.of(
            List.of("철수", "영희"),
            List.of("민수", "지수"),
            List.of("준호", "수진")
        );
        // 각 내부 List를 다 합쳐서 하나의 Stream으로 바꾸고 싶다고 해보자.
        // 이렇게 하면 될까?
        names.stream()
            .map(list -> list.stream())
            .toList();
        // 이렇게 하면 Stream<String>이 담긴 Stream이 만들어져버린다.

        // 2. flatMap()이란?
        // 각 요소를 Stream으로 변환한 다음, 그 Stream을 하나로 합친다.
        List<String> result = names.stream()
            .flatMap(list -> list.stream())
            .toList();
        // 내부 요소가 하나의 Stream으로 합쳐진다.
        System.out.println(result);
        
        // 정리
        // map(): 요소를 1:1로 변환한다.
        // flatMap(): 요소를 1:N으로 변환하고 평탄화(n차원 -> n-1차원)한다.

        // 실무 예제: 한 사용자가 여러 주문을 하고, 하나의 주문에 여러 상품이 들어간다.
        List<User> users = List.of(
            new User("철수", List.of(
                new Order(List.of("노트북", "마우스")),
                new Order(List.of("키보드"))
            )),
            new User("영희", List.of(
                new Order(List.of("모니터", "스피커"))
            )),
            new User("민수", List.of(
                new Order(List.of("휴대폰", "케이스")),
                new Order(List.of("충전기"))
            ))
        );
        // 모든 사용자가 주문한 상품을 하나의 List로 만들어보자.
        List<String> allProducts = users.stream()
            // 주문 목록을 하나로 합친다.
            .flatMap(user -> user.getOrders().stream())
            // 제품 목록을 하나로 합친다.
            .flatMap(order -> order.getProducts().stream())
            .toList();
        System.out.println(allProducts);

        // 메서드 참조를 활용하여 중첩 List를 평탄화해보자
        List<List<Integer>> numbers = List.of(
            List.of(1, 2, 3),
            List.of(4, 5),
            List.of(6, 7, 8)
        );
        List<Integer> result2 = numbers.stream()
            // list -> list.stream()과 같은 의미다.
            .flatMap(List::stream)
            .toList();
        System.out.println(result2);

        // 사용자별 취미 목록을 합쳐보자
        List<User2> users2 = List.of(
            new User2("철수", List.of("축구", "게임")),
            new User2("영희", List.of("독서", "영화")),
            new User2("민수", List.of("여행", "게임"))
        );
        List<String> result3 = users2.stream()
            .flatMap(user -> user.getHobbies().stream())
            .toList();
        System.out.println(result3);

        // 주문 상품의 가격을 합산해보자
        List<Order2> orders = List.of(
            new Order2(List.of(10000, 20000)),
            new Order2(List.of(30000, 40000, 50000)),
            new Order2(List.of(60000))
        );
        int result4 = orders.stream()
            .flatMap(order -> order.getPrices().stream())
            .mapToInt(price -> price)
            .sum();
        System.out.println("전체 주문 금액: " + result4);

        // 다음 조건을 만족하는 코드를 작성해보자.
        // flatMap()으로 모든 숫자를 하나로 합친다.
        // 30 이상인 숫자만 필터링한다.
        // 오름차순으로 정렬한다.
        // 상위 3개 숫자만 추출한다.
        // 최종 결과를 List<Integer>로 만든다.
        List<List<Integer>> numbers2 = List.of(
            List.of(10, 20, 30),
            List.of(40, 50),
            List.of(60, 70, 80)
        );
        List<Integer> result5 = numbers2.stream()
            .flatMap(List::stream)
            .filter(n -> n >= 30)
            .sorted()
            .limit(3)
            .toList();
        System.out.println(result5);    
    
        // Q1. 다음 코드의 결과 자료형은 무엇일까?
        // numbers.stream()
        //      .map(list -> list.stream());
        // A1. Stream<Stream<Integer>>
        // Q2. 다음 두 코드는 어떤 차이가 있을까?
        // .flatMap(list -> list.stream())
        // .flatMap(List::stream)
        // A2. 아래 코드가 메서드 참조를 사용하여 더 간결하다.
    }
}
