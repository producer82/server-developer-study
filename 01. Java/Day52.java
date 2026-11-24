import java.io.IOException;
import java.util.List;

// 예외를 만들때는 특정 Exception을 상속해야 한다.
class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        // 부모 클래스의 생성자에 메시지를 전달한다.
        // -> 나중에 getMessage()로 예외 메시지를 확인할 수 있다.
        super(message);
    }
}

class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}

class Product {
    private int id;
    private String name;
    private int price;
    private int stock;
    
    public Product(int id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void order(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("주문 수량은 1개 이상이어야 합니다.");
        }
        if (quantity > stock) {
            throw new InsufficientStockException("재고가 부족합니다.");
        }
        // 예외가 발생하면 이 다음으로 넘어가지 않는다
        // -> 재고가 변경되지 않는다.

        stock -= quantity;

        System.out.println(name + " 주문 완료!");
        System.out.println("남은 재고: " + stock);
    }
}

class ProductService {
    private List<Product> products;
    
    public ProductService(List<Product> products) {
        this.products = products;
    }

    public Product findProductById(int id) {
        Product findProduct = products.stream()
            .filter(product -> product.getId() == id)
            .findAny()
            .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다."));
        return findProduct;
    }
}

class OrderService {
    private ProductService productService;

    public OrderService(ProductService productService) {
        this.productService = productService;
    }

    public void createOrder(int productId, int quantity) {
        Product product = productService.findProductById(productId);
        product.order(quantity);
    }
}

public class Day52 {
    public static void main(String[] args) {
        // 예외 처리 심화
        // 실제 서버에서는 잘못된 요청, 존재하지 않는 데이터 등 많은 예외 상황을 다뤄야 한다.

        // 예와: 프로그램 실행 중 발생하는 비정상적인 상황
        // 예외는 try-catch로 처리할 수 있다.
        try {
            int result = 10 / 0; // ArithmeticException
            System.out.println(result);
        } 
        catch (ArithmeticException e) {
            System.out.println("0으로 나눌 수 없습니다.");
        }
        
        // throw
        // 개발자가 직접 예외를 발생시키기
        int age = 17;
        try {
            checkAge(age);
            System.out.println("이용 가능합니다.");
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    
        // thorws
        // 호출자에게 예외를 전달하기
        try {
            // readFile에서 발생한 예외가 이곳으로 넘어온다.
            readFile();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Checked Exception과 Unchecked Exception
        // Checked Exception: 컴파일러가 예외 처리를 강제한다.
        // - IOException, SQLException, ClassNotFoundException 등
        // readFile(); // 컴파일 오류가 발생한다.
        // readFile()이 Checked Exception인 IOException을 던질 수 있는데 처리하지 않았기 때문이다.
        
        // Unchecked Exception: 컴파일러가 처리를 강제하지 않는다.
        // - NullPointerException, ArithmeticException 등
        checkAge(20); // 그냥 실행된다.
        
        // 사용자 정의 예외
        // 예를 들어, 쇼핑몰에서 다음 상황이 발생했다면?
        // - 상품을 찾을 수 없음, 재고가 부족함, 주문 수량이 잘못됨, 상품 가격이 잘못됨
        // 이런 상황을 구체적으로 표현하기 위해 직접 예외 클래스를 만들 수 있다.
        Product product = new Product(1, "Laptop", 1000000, 5);
        try {
            // 재고 5개짜리 상품에 10개를 주문한다 -> 예외 발생
            product.order(10);
        }
        catch (InsufficientStockException e) {
            System.out.println(e.getMessage());
        }

        // 예외 전파
        // 꼬리를 물고 메서드가 메서드를 호출하는 경우를 생각해보자
        // 만약 어떤 메서드가 예외를 발생시켰다면 어떻게 될까?
        // -> 메서드를 타고 올라가서 위쪽으로 전달된다.
        List<Product> products = List.of(
            new Product(1, "Laptop", 1000000, 3),
            new Product(2, "Monitor", 200000, 5)
        );
        ProductService productService = new ProductService(products);
        OrderService orderService = new OrderService(productService);
        // 정상적인 주문
        orderService.createOrder(1, 2);
        // 존재하지 않는 상품 주문
        try {
            orderService.createOrder(3, 2);
        }
        catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // 재고보다 많은 수량 주문
        try {
            orderService.createOrder(2, 10);
        }
        catch (InsufficientStockException e) {
            System.out.println(e.getMessage());
        }
        // 타고 올라오는 예외를 어디서 잡아야 할까?
        // -> 백엔드 개발에 중요한 질문, 추후 다시 자세히 다루게 됨.
    }

    // 예외가 발생할 수 있음을 미리 선언한다.
    public static void readFile() throws IOException {
        // 예외를 발생시킨다.
        throw new IOException("파일을 읽을 수 없습니다.");
    }

    public static void checkAge(int age) {
        if (age < 19) {
            throw new IllegalArgumentException("미성년자는 이용 불가합니다");
        }
    }
}
