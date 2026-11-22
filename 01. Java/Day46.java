import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Product {
    private String name;
    public String getName() {
        return name;
    }

    private String category;
    public String getCategory() {
        return category;
    }

    private int price;
    public int getPrice() {
        return price;
    }

    public Product(String name, String category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }
}

public class Day46 {
    public static void main(String[] args) {
        // Stream API 심화
        // Collectors의 주요 기능

        // joining()
        // Stream에 있는 문자열을 하나로 합친다
        List<String> names = List.of(
            // List.of()는 Arrays.asList()와 어떤 차이?
            // asList는 값 변경 가능, of는 불가능
            // asList는 null 가능, of는 불가능
            // asList는 래퍼 객체, of는 불변 객체
            "Laptop",
            "Mouse",
            "Keyboard"
        );
        String result = names.stream()
            // 구분자, 시작 문자, 끝 문자 지정 가능
            .collect(Collectors.joining(", ", "[", "]"));
        System.out.println(result);
        // 데이터를 특정 형식으로 변환해야할 때 유용하다.

        List<Product> products = List.of(
            new Product("Laptop", "Electronics", 1500000),
            new Product("Mouse", "Electronics", 30000),
            new Product("Keyboard", "Electronics", 80000),
            new Product("Shirt", "Clothing", 50000),
            new Product("Pants", "Clothing", 70000),
            new Product("Shoes", "Clothing", 120000)
        );
        // 상품 이름만 출력하기
        String productNames = products.stream()
            .map(Product::getName)
            .collect(Collectors.joining(" | ", "[", "]"));
        System.out.println(productNames);

        // mapping()
        // 각 요소를 다른 형태로 변환하고, 그 결과를 다시 수집한다.
        Map<String, List<String>> namesByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory, // 그룹을 나누는 기준
                Collectors.mapping(
                    Product::getName, // 각 Product의 이름을 가져온다
                    Collectors.toList()) // 이름을 List로 수집
            ));
        // groupingBy()의 두 번째 인자로 사용하면 유용하다.
        for (Map.Entry<String, List<String>> entry : namesByCategory.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // collectingAndThen(수집방법, 수집후변환)
        // 수집이 끝난 결과에 추가 작업을 수행한다.
        List<String> names2 = products.stream()
            .map(Product::getName)
            .collect(Collectors.collectingAndThen(
                // 리스트로 수집하는데
                Collectors.toList(),
                // 수정할 수 없도록 만들어라
                Collections::unmodifiableList
            ));
        // Java 16부터는 아래처럼 toList()를 사용할수도 있다.
        List<String> names3 = products.stream()
            .map(Product::getName)
            // 마찬가지로 수정할 수 없다.
            .toList();
        // 수집 결과를 다른 타입으로 변환하거나, 후처리 할때 유용하다.
        
        // names2.add("Monitor"); // Exception이 발생한다.
        System.out.println(names2);
        System.out.println(names3);

        // Q1. joining(", ")과 joining(", ", "[", "]")의 차이는?
        // A1. joining(", ")는 구분자로 ,를 사용하고, 
        // joining(", ", "[", "]")는 구분자로 ,를, 첫 문자로 [를, 끝 문자로 ]를 사용한다.
        // Q2. mapping()은 map()과 어떤 차이가 있을까?
        // A2. apping()은 결과를 어떤 형태로 수집할지도 정할 수 있다.
        // map()은 Stream 연산이고, mapping()은 Collector 연산이다.
        // Q3. collectingAndThen()은 왜 필요할까?
        // A3. 수집한 결과를 후처리 해야할 때 필요하다.
        // Q4. Stream.toList()와 Collectors.toList()의 차이는?
        // A4. Collectors.toList()는 수정 가능할 수도 있는 객체를 반환한다.
    }
}
