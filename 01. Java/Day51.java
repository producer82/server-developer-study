import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

public class Day51 {
    public static void main(String[] args) throws IOException {
        // 파일 입출력
        // 실제 백엔드에서는 데이터를 DB나 파일 등에 저장하고 다시 불러와야 한다.

        // 파일 입출력이란?: 프로그램이 파일을 읽거나 쓰는 작업
        // File 클래스 -> 파일이나 디렉터리를 표현
        File file = new File("products.txt");
        // 파일이 존재하지 않더라도 우선 객체 생성 자체는 가능하다.
        System.out.println(file.exists()); // 존재 여부
        System.out.println(file.getName()); // 파일 이름
        System.out.println(file.getAbsolutePath()); // 절대 경로
        System.out.println(file.isFile()); // 파일인지 여부
        System.out.println(file.isDirectory()); // 디렉터리인지 여부

        // Path와 Paths
        // Path 인터페이스 -> 기존 File 보다 경로를 다루기 편리하다.
        // 일반적으로 Path와 Files 조합을 많이 이용
        Path path = Path.of("./data/products.txt");
        Path path2 = Path.of("./data");
        System.out.println(path);
        System.out.println(path.getFileName());
        System.out.println(path.toAbsolutePath());

        // 1. 파일 생성, 읽기, 쓰기
        // 디렉터리가 존재하지 않으면 생성
        if (Files.notExists(path2)) {
            // Files.createDirectory(path2); // 마지막 디렉터리 하나만 생성한다.
            Files.createDirectories(path2); // 필요한 디렉터리를 모두 생성한다.            
        }
        // 파일이 존재하지 않으면 생성
        if (Files.notExists(path)) {
            Files.createFile(path); 
        }
        // 파일이 존재하는지 확인해보기
        System.out.println(Files.exists(path));
        // 파일 이름과 절대 경로 확인해보기
        System.out.println(path.getFileName());
        System.out.println(path.toAbsolutePath());
        // 내용 쓰기, 이어쓰고 싶으면 StandardOpenOption.APPEND를 사용할 수 있다.
        Files.writeString(path, "Laptop, Electronics, 1500000, 5");
        // 전체 내용 읽기
        String content = Files.readString(path);
        System.out.println(content);

        // BufferedReader와 BufferedWriter
        // 파일 크기가 매우 크다면? -> 한 줄씩 읽어보자
        // 2. 상품 정보 파일에 저장하기
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            // try-with-resources:
            // 만약 읽다가 프로그램이 터지면 영원히 파일이 닫히지 않기 때문에 무조건 try로 감싸야한다.
            // 이 방법을 사용하면 작업이 끝나거나 예외가 터져도 자동으로 자원을 해제한다.
            writer.write("Laptop,Electronics,1500000,5");
            writer.newLine();
            writer.write("Mouse,Electronics,30000,20");
            writer.newLine();
            writer.write("Keyboard,Electronics,80000,10");
            writer.newLine();
            writer.write("Monitor,Electronics,500000,8");
        }

        // 3. 파일에서 상품 정보 읽기
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            // readLine은 파일을 한 줄씩 읽어서 문자열을 반환한다.
            // 따라서 끝에 도달하면 null을 반환한다.
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // 4. 파일에서 Product 객체 만들기
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            // readLine은 파일을 한 줄씩 읽어서 문자열을 반환한다.
            // 따라서 끝에 도달하면 null을 반환한다.
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                products.add(
                    new Product(data[0].trim(), data[1].trim(), Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()))
                );
            }
        }
        products.forEach(product -> System.out.println(product.getName() + " " + product.getPrice()));
    }
}
