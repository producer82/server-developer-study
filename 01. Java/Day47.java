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

    @Override 
    public String toString() {
        return name + "(" + price + "원, 재고: " + stock + ")";
    }
}

public class Day47 {
    public static void main(String[] args) {
        // Stream API 실전 종합
        // 온라인 쇼핑몰을 가정하고 상품 목록을 관리해보자
        List<Product> products = List.of(
            new Product("Laptop", "Electronics", 1500000, 5),
            new Product("Mouse", "Electronics", 30000, 20),
            new Product("Keyboard", "Electronics", 80000, 0),
            new Product("Monitor", "Electronics", 350000, 8),
            new Product("Shirt", "Clothing", 50000, 15),
            new Product("Pants", "Clothing", 70000, 0),
            new Product("Shoes", "Clothing", 120000, 10),
            new Product("Jacket", "Clothing", 200000, 3)
        );

        // 1. 가격이 100,000원 이상인 상품을 찾아보자
        List<Product> expensiveProducts = products.stream()
            .filter(product -> product.getPrice() >= 100000)
            .toList();
        System.out.println(expensiveProducts);
        // 동시에 재고가 있는 상품을 찾아보자.
        System.out.println(expensiveProducts.stream()
            .filter(product -> product.getStock() > 0)
            .toList());

        // 2. 가격이 높은 상품부터 3개를 가져와보자
        List<Product> top3Products = products.stream()
            .sorted(Comparator.comparingInt(Product::getPrice).reversed())
            .limit(3)
            .toList();
        System.out.println(top3Products);

        // 3. 전체 상품의 재고 가치를 계산해보자
        int totalStockValue = products.stream()
            // mapToInt()는 요소를 int 값으로 변환한다.
            // 반환 결과는 Stream<Integer>가 아니라 IntStream이다.
            .mapToInt(product -> product.getPrice() * product.getStock())
            // 따라서, 숫자 전용 연산을 사용할 수 있게된다.
            .sum();
        System.out.println(totalStockValue);
        // 평균 상품 가격도 계산해보자
        double averagePrice = products.stream()
            .mapToInt(Product::getPrice)
            .average() // Optional<Double>을 반환한다.
            .orElse(0.0); // 비어있으면 0을 넣는다.
        System.out.println(averagePrice);

        // 4. 카테고리별 상품 가격의 평균을 구해보자.
        Map<String, Double> averagePriceByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.averagingInt(Product::getPrice)
            ));
        System.out.println(averagePriceByCategory);
        
        // 5. 카테고리별 상품 수를 구해보자.
        Map<String, Long> countByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.counting()
            ));
        System.out.println(countByCategory);

        // 6. 카테고리별 재고 가치를 구해보자
        Map<String, Integer> stockValueByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.summingInt(
                    product -> product.getPrice() * product.getStock()
                )
            ));
        System.out.println(stockValueByCategory);

        // Stream API를 사용해도 원본 데이터는 변경되지 않는다.
        System.out.println(products);
        System.out.println("============================");

        // 1. 가격이 100,000원 이상이고 재고가 5개 이상인 상품의 이름을 추출해보자
        List<String> filteredProducts1 = products.stream()
            .filter(product -> product.getPrice() >= 100000)
            .filter(product -> product.getStock() >= 5)
            .map(Product::getName)
            .toList();
        System.out.println(filteredProducts1);

        // 2. 가격이 높은 순서대로 상품을 정렬하고 상위 3개의 이름를 추출해보자
        List<String> filteredProducts2 = products.stream()
            .sorted(Comparator.comparingInt(Product::getPrice).reversed())
            .limit(3)
            .map(Product::getName)
            .toList();
        System.out.println(filteredProducts2);

        // 3. 전체 상품의 재고 가치를 계산해보자
        int totalValue = products.stream()
            .mapToInt(product -> product.getPrice() * product.getStock())
            .sum();
        System.out.println(totalValue);

        // 4. 카테고리별 통계
        // 카테고리별 상품 개수 구하기
        Map<String, Long> categoryStock = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.counting()
            ));
        Map<String, Double> categoryAveragePrice = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.averagingInt(Product::getPrice)
            ));
        Map<String, Integer> categoryStockValue = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.summingInt(product -> product.getPrice() * product.getStock())
            ));
        System.out.println(categoryStock);
        System.out.println(categoryAveragePrice);
        System.out.println(categoryStockValue);

        // 5. 재고가 0개인 상품들의 이름만 추출해보자
        List<String> filteredProducts3 = products.stream()
            .filter(product -> product.getStock() == 0)
            .map(Product::getName)
            .toList();
        System.out.println(filteredProducts3);

        // Q1. map()과 mapToInt()의 차이는?
        // A1. map()은 반환형이 Stream<T>지만, mapToInt()는 반환형이 IntStream이다.
        // Q2. filter()를 여러번 사용하는 것과 하나의 filter() 안에서 &&를 사용하는 것은 어떤 차이가 있을까?
        // A2. 여러반 사용하는게 가독성이 좋다.
        // Q3. average()가 double이 아니라 OptionalDobuel을 반환하는 이유는?
        // A3. 평균을 계산할 요소가 하나도 없을 수 있기 때문이다.
        // Q4. Stream을 사용해 데이터를 가공하면 원본 List도 변경될까?
        // A4. 원본 객체의 상태를 변경하지 않는 한 변경되지 않는다.
        // Q5. sorted() 다음에 limit()을 사용하는 이유는?
        // A5. 정렬한 후 위에서부터 원하는 만큼의 요소를 자르기 위해 사용한다.

    }
}
