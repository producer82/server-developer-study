import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Product {
    private String name;
    private String category;
    private int price;

    public Product(String name, String category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
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

    @Override
    public String toString() {
        return name;
    }
}

public class Day45 {
    public static void main(String[] args) {
        // Stream API 심화 2
        // 데이터를 그룹화하고 분류해보자

        // groupingBy(): 데이터 그룹화하기
        // 데이터를 그룹별로 나눠서 Map으로 만들어준다.
        // 이름의 첫 글자를 기준으로 그룹화하기
        List<String> names = Arrays.asList(
            "Kim", "Lee", "Park", "Kang", "Choi"
        );
        // Character: 그룹을 구분하는 기준
        // List<String>: 해당 그룹에 속한 데이터
        Map<Character, List<String>> grouped = names.stream()
            .collect(Collectors.groupingBy(name -> name.charAt(0)));
        System.out.println(grouped);
        // 객체를 기준으로 그룹화하기
        List<Product> products = Arrays.asList(
            new Product("Laptop", "Electronics", 1500000),
            new Product("Mouse", "Electronics", 30000),
            new Product("Keyboard", "Electronics", 80000),
            new Product("Shirt", "Clothing", 50000),
            new Product("Pants", "Clothing", 70000),
            new Product("Shoes", "Clothing", 120000)
        );
        Map<String, List<Product>> groupedProducts = products.stream()
            .collect(Collectors.groupingBy(Product::getCategory));
        System.out.println(groupedProducts);

        // 집계 기능 함께 사용하기
        // 그룹별 개수: counting()
        Map<String, Long> categoryCount = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                // 반환형이 Long이다
                Collectors.counting()
            ));
        System.out.println("상품 개수:");
        System.out.println(categoryCount);
        // 그룹별 합계: summingInt()
        Map<String, Integer> categoryTotalPrice = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                // 반환형이 Integer다
                Collectors.summingInt(Product::getPrice)
            ));
        System.out.println("가격 합계:");
        System.out.println(categoryTotalPrice);
        // 그룹별 평균: averagingInt()
        Map<String, Double> categoryAveragePrice = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                // 반환형이 Double이다
                Collectors.averagingInt(Product::getPrice)
            ));
        System.out.println("가격 평균:");
        System.out.println(categoryAveragePrice);
        
        // 두 그룹으로 나누기
        // groupingBy()는 여러 그룹으로 분류하고, partitioningBy()는 두 그룹으로 나눈다.
        // 조건을 기준으로 두 그룹으로 나누기 때문에 Boolean을 Key로 갖는다.
        Map<Boolean, List<Product>> partitioned = products.stream()
            .collect(Collectors.partitioningBy(
                product -> product.getPrice() >= 100000
            ));
        System.out.println("고가 상품:");
        System.out.println(partitioned.get(true));
        System.out.println("일반 상품:");
        System.out.println(partitioned.get(false));

        // Q1. groupingBy()의 반환 결과가 Map 형태인 이유는?
        // A1. 요소를 그룹화하기 때문에 
        // 그룹에 해당하는 Key와 그곳에 해당하는 요소인 Value가 있어야 한다.
        // Q2. Collectors.counting()은 어떤 역할을 할까?
        // A2. 그룹에 해당하는 요소의 갯수를 센다.
        // Q3. summingInt()와 averagingInt()의 차이는?
        // A3. summingInt()는 요소의 합계를 구하고, averagingInt()는 요소의 평균을 구한다.
        // Q4. groupingBy()와 partitioningBy()의 가장 큰 차이는?
        // A4. groupingBy()는 여러 개의 그룹으로 요소를 나누고, partitioningBy()는 하나의 조건으로 요소를 양분한다.
        // Q5. 상품 카테고리별 매출을 계산한다면 어떤 메서드 조합을 사용해야할까?
        // A5. groupingBy()를 사용한 후 카테고리 별로 summingInt()를 사용해야한다.
    }
}
