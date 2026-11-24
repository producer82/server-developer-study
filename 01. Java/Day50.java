import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private int orderId;
    private List<Product> products;

    public Order(int orderId, List<Product> products) {
        this.orderId = orderId;
        this.products = products;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Product> getProducts() {
        return products;
    }
}

public class Day50 {
    public static void main(String[] args) {
        // Stream API 종합 미니 프로젝트
        
        // 비즈니스 요구 사항
        // 1. 관리자는 상품 목록과 주문 목록을 바탕으로 다음 정보를 확인할 수 있어야 한다.
        // - 전체 상품 목록, 판매 가능한 상품 목록
        // - 카테고리별 상품 통계, 재고 가치
        // - 주문별 판매 금액
        // - 가장 많이 팔린 상품
        // - 전체 매출액

        // 다음과 같은 상품 데이터가 주어진다.
        List<Product> products = List.of(
            new Product("Laptop", "Electronics", 1500000, 5),
            new Product("Mouse", "Electronics", 30000, 20),
            new Product("Keyboard", "Electronics", 80000, 0),
            new Product("Monitor", "Electronics", 500000, 8),
            new Product("Shoes", "Clothing", 120000, 10),
            new Product("Jacket", "Clothing", 200000, 3),
            new Product("T-Shirt", "Clothing", 30000, 15),
            new Product("Pants", "Clothing", 90000, 0),
            new Product("Book", "Education", 25000, 30),
            new Product("Desk", "Furniture", 350000, 4)
        );

        // 다음과 같은 주문 데이터가 주어진다.
        List<Order> orders = List.of(
            new Order(1001, List.of(
                new Product("Laptop", "Electronics", 1500000, 1),
                new Product("Mouse", "Electronics", 30000, 2)
            )),
            new Order(1002, List.of(
                new Product("Shoes", "Clothing", 120000, 1),
                new Product("Jacket", "Clothing", 200000, 1)
            )),
            new Order(1003, List.of(
                new Product("Laptop", "Electronics", 1500000, 1),
                new Product("Monitor", "Electronics", 500000, 2),
                new Product("Desk", "Furniture", 350000, 1)
            )),
            new Order(1004, List.of(
                new Product("Book", "Education", 25000, 3),
                new Product("T-Shirt", "Clothing", 30000, 2)
            )),
            new Order(1005, List.of(
                new Product("Mouse", "Electronics", 30000, 1),
                new Product("Shoes", "Clothing", 120000, 2)
            ))
        );

        // 1. 판매 가능한 상품 조회하기
        List<String> sellableProduct = products.stream()
            .filter(product -> product.getStock() > 0)
            .filter(product -> product.getPrice() >= 50000)
            .sorted(Comparator.comparingInt(Product::getPrice).reversed())
            .map(Product::getName)
            .toList();
        System.out.println(sellableProduct);

        // 2. 카테고리별 상품 통계
        // 상품 개수 구하기
        Map<String, Long> countByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.counting()
            ));
        System.out.println(countByCategory);
        // 평균 가격 구하기
        Map<String, Double> averageByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.averagingInt(Product::getPrice)
            ));
        System.out.println(averageByCategory);
        // 전체 재고 수량
        Map<String, Integer> stockByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.summingInt(Product::getStock)
            ));
        System.out.println(stockByCategory);

        // 3. 카테고리별 재고 가치 구하기
        Map<String, Long> stockValueByCategory = products.stream()
            .filter(product -> product.getStock() > 0)
            .filter(product -> product.getPrice() >= 30000)
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.summingLong(
                    product -> (long)product.getPrice() * product.getStock()
                )
            ));
        System.out.println(stockValueByCategory);

        // 4. 각 주문에 포함된 상품들의 총 주문금액 계산하기
        List<Long> orderPrice = orders.stream()
            .map(order -> order.getProducts().stream())
            .map(productList -> productList
                .mapToLong(
                    product -> (long)product.getPrice() * product.getStock()
                )
                .sum()
            )
            .toList();
        System.out.println(orderPrice);

        // 5. 상품별 총 판매 수량 구하기
        Map<String, Integer> totalSellingCount = orders.stream()
            .flatMap(order -> order.getProducts().stream())
            .collect(Collectors.groupingBy(
                Product::getName,
                Collectors.summingInt(Product::getStock)
            ));
        System.out.println(totalSellingCount);

        // 6. 가장 많이 팔린 상품 찾기
        Map.Entry<String, Integer> bestSellingItem = totalSellingCount.entrySet().stream()
            .max(Comparator.comparingInt(Map.Entry::getValue))
            .orElseThrow();
        System.out.println(bestSellingItem.getKey() + " " + bestSellingItem.getValue());
        
        // 7. 전체 매출액 계산하기
        long totalRevenue = orders.stream()
            .flatMap(order -> order.getProducts().stream())
            .mapToLong(product -> (long)product.getPrice() * product.getStock())
            .sum();
        System.out.println(totalRevenue);

        // Q1. map과 flatMap의 가장 중요한 차이는?
        // A1. map은 1:1 변환을 하고, flatMap은 1:N 변환을 한다.
        // 동시에, flatMap은 평탄화를 수행한다.

        // Q2. groupingBy()에 Collectors.summingInt()를 전달하는 것은 어떤 의미일까?
        // A2. 그룹에 속해있는 요소가 가지고 있는 어떤 필드의 합을 구해라

        // Q3. mapToLong()을 사용하는 것이 map()보다 유리한 상황은 언제일까?
        // A3. 기본형 Stream의 함수(sum, average 등)를 사용해야 할때 유리하다.

        // Q4. Optioanl.orElse(0)과 orElseThrow()는 각각 언제 사용해야 할까?
        // A4. orElse(0)은 값이 없을 때 기본값을 주고싶을 때 사용하고,
        // orElseThrow는 값이 없을 때 예외를 발생시키고 싶을 때 사용한다.

        // Q5. 주문별 매출액을 계산할 때 flatMap()을 무조건 먼저 사용하면 안되는 이유는?
        // A5. flatMap()을 사용해버리면 평탄화되어 주문 별 매출액을 계산할 수
        // 없게 된다.
        
    }
}
