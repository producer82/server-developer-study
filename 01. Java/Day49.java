import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Product {
    private String name;
    private String category;
    private int price;
    private int stock;

    public Product(String name, String category, int price, int stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}

class Order {
    private List<Product> products;

    public Order(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}

public class Day49 {
    public static void main(String[] args) {
        // Stream API 심화 활용과 실전 문제
        // 실제 백엔드 데이터 처리에 가까운 문제를 해결해보자.
        
        // 숫자 자료형에 맞는 기본형 Stream을 알아보자
        // mapToInt()
        List<Integer> prices = List.of(10000, 20000, 30000);
        int total = prices.stream()
            .mapToInt(price -> price)
            .sum();
        System.out.println(total);
        // mapToLong()
        List<Long> longPrices = List.of(10000L, 20000L, 30000L);
        long longTotal = longPrices.stream()
            .mapToLong(price -> price)
            .sum();
        System.out.println(longTotal);
        // 동일하게 mapToDouble()도 있다.
        // 기본형 Stream은 sum, average, max, min, count 같은 편리한 메서드를 제공한다.

        // max()와 min() 활용하기
        // 값이 없을수도 있으므로 Optional<T>을 반환한다.
        List<Product> products = List.of(
            new Product("Laptop", "Electronics", 1500000, 5),
            new Product("Mouse", "Electronics", 30000, 20),
            new Product("Keyboard", "Electronics", 80000, 0),
            new Product("Monitor", "Electronics", 500000, 8),
            new Product("Shoes", "Clothing", 120000, 10),
            new Product("Jacket", "Clothing", 200000, 3),
            new Product("T-Shirt", "Clothing", 30000, 15),
            new Product("Pants", "Clothing", 90000, 0)
        );
        // 최댓값과 최솟값을 찾아보자
        int maxPrice = prices.stream()
            .mapToInt(price -> price)
            .max()
            .orElse(0);
        System.out.println(maxPrice);
        // 1. 가장 비싼 상품 찾기
        Product expensive = products.stream()
            .max(Comparator.comparingInt(Product::getPrice))
            .orElseThrow(); 
        System.out.println(expensive.getName() + "/" + expensive.getPrice());
        // 2. 가장 저렴한 상품 찾기
        Product cheapest = products.stream()
            .min(Comparator.comparingInt(Product::getPrice))
            .orElseThrow();
        System.out.println(cheapest.getName() + "/" + cheapest.getPrice());

        // Collectors의 집계 두 가지 이상을 결합하기
        // 2-1. 카테고리별 상품수 구하기
        Map<String, Long> countByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                // Collectors 메서드 Collectors를 전달하는 것을 다운스트림 Collector라고 한다.
                Collectors.counting()
            ));
        System.out.println(countByCategory);
        // 2-2. 카테고리별 가격 합계를 구하기
        Map<String, Integer> totalPriceByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.summingInt(Product::getPrice)
            ));
        System.out.println(totalPriceByCategory);
        // 2-3. 카테고리별 가격 평균을 구하기
        Map<String, Double> averagePriceByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.averagingInt(Product::getPrice)
            ));
        System.out.println(averagePriceByCategory);
        // 카테고리별 상품 이름 목록 만들기
        Map<String, List<String>> namesByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.mapping(
                    Product::getName, 
                    Collectors.toList()
                )
            ));
        System.out.println(namesByCategory);

        // 3. 전체 재고 가치 계산하기
        long stockValue = products.stream()
            // long으로 먼저 형변환하고 연산해야 오버플로를 막을 수 있다.
            .mapToLong(product -> (long)product.getPrice() * product.getStock())
            .sum();
        System.out.println("전체 재고 가치: " + stockValue);

        // flatMap()과 groupingBy() 조합
        List<Order> orders = List.of(
            new Order(List.of(
                new Product("Laptop", "Electronics", 1500000, 5),
                new Product("Mouse", "Electronics", 30000, 20)
            )),
            new Order(List.of(
                new Product("Shoes", "Clothing", 120000, 10),
                new Product("Jacket", "Clothing", 200000, 3)
            )),
            new Order(List.of(
                new Product("Monitor", "Electronics", 500000, 8),
                new Product("T-Shirt", "Clothing", 30000, 15)
            ))
        );
        // 4. 주문별 상품을 하나로 합친 뒤 카테고리별로 집계하기
        Map<String, Long> totalOrders = orders.stream()
            .flatMap(order -> order.getProducts().stream())
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.counting()
            ));
        System.out.println(totalOrders);
        // 5. 다음 조건을 모두 만족하는 코드를 작성해보자
        // 재고가 0인 상품은 제외한다.
        // 가격이 50,000원 이상인 상품만 남긴다.
        // 카테고리별로 그룹화한다.
        // 각 카테고리의 재고 가치를 계산한다.
        // 결과는 Map<String, Long>으로 만든다.
        Map<String, Long> result = products.stream()
            .filter(product -> product.getStock() > 0)
            .filter(product -> product.getPrice() >= 50000)
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.summingLong(product -> (long)product.getPrice() * product.getStock())
            ));
        System.out.println(result);

        // Q1. 두 코드의 차이는?
        // products.stream()
        //     .max(Comparator.comparingInt(Product::getPrice));
        // products.stream()
        //     .mapToInt(Product::getPrice)
        //     .max();
        // A1. 후자는 Stream에서 가격만 추출하는 과정을 거친다.
        // Q2. Collectors.counting()의 반환형은?
        // Map<String, Long> result = products.stream()
        //     .collect(Collectors.groupingBy(
        //         Product::getCategory,
        //         Collectors.counting()
        //     ));
        // A2. Long
        // Q3. 다음 두 연산의 차이는?
        // .map(product -> product.getPrice() * product.getStock())
        // .mapToLong(product -> (long) product.getPrice() * product.getStock())
        // A3. 후자는 무조건 값을 Long으로 반환하며, 기본형 Stream 메서드를 사용할 수 있다.

    }
}
