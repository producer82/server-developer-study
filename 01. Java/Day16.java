interface Engine {
    String start();
    int getMaxSpeed();
}

class Car {
    private final Engine engine;
    private final String name;

    Car(String name, Engine engine) {
        this.name = name;
        this.engine = engine;
    }

    void start() {
        System.out.println(name + engine.start());
    }

    void speed() {
        System.out.println("최고속력: " + engine.getMaxSpeed());
    }
}

class GasolineEngine implements Engine {
    private int maxSpeed;

    GasolineEngine(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String start() {
        return "가솔린 엔진 시동";
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }
}

class BankAccount {
    void deposit() {
        System.out.println("입금");
    }

    void withdraw() {
        System.out.println("출금");
    }
    
    void sendEmail() { 
        System.out.println("이메일 보내기");
    }

    void connect() {
        System.out.println("DB 연결");
    }
}

class UserService {
    void registerUser() {
        System.out.println("가입되었습니다");
    }

    void sendWelcomeEmail() {
        System.out.println("환영합니다");
    }

    void generateReport() {
        System.out.println("보고서를 작성합니다");
    }
}

class ShoppingService {
    void calculatePrice() {
        System.out.println("가격 계산");
    }

    void printReceipt() {
        System.out.println("영수증 출력");
    }
}

class ShoppingDatabase {
    void saveOrderToDatabase() {
        System.out.println("주문 DB 저장");
    }
}

class ShoppingEmail {
    void sendEmail() {
        System.out.println("이메일 발송");
    }
}

public class Day16 {
    public static void main(String[] args) {
        // 결합도, 응집도, 단일 책임 원칙 (SRP)

        // 결합도란
        // 한 클래스가 다른 클래스에 얼마나 강하게 의존하는가?
        // 결합도가 높을수록: 구체적인 구현에 의존하며, 구현 변경이 다른 클래스에 영향을 주기 쉽다.
        // 결합도가 낮을수록: 추상화에 의존할 수 있으며, 구현 교체가 상대적으로 쉽다.
        // 무조건 결합도를 0으로 만든다 -> X / 불필요한 결합도를 줄인다 -> O
        // Car 객체는 구체적인 구현이 아닌 Interface에 의존해 여러가지 Engine을 받을 수 있다 -> 결합도가 낮다.
        Car car = new Car("소나타", new GasolineEngine(140));
        car.start();

        // 응집도란
        // 한 클래스 내부의 데이터와 메서드가 얼마나 하나의 목적에 집중되어 있는가?
        // 응집도가 낮을수록: 모듈이 여러가지 이질적인 기능을 동시에 가지고 있다.
        // 응집도가 높을수록: 모듈이 하나의 명확한 목적에 집중하고 있다.
        // 서로 관련 없는 모든 기능이 BankAccount에 몰려있다 -> 응집도가 낮다
        
        BankAccount account = new BankAccount();
        account.connect(); // DB도 연결하고
        account.deposit(); // 입금도 처리하고
        account.sendEmail(); // 이메일도 보내고
        // BankAccount 내부의 기능들이 서로 연관성이 없다.

        // 결합도: 클래스와 클래스 사이의 관계
        // 응집도: 클래스 내부 구성 요소들의 관계

        // SRP (Single Responsibility Principle, 단일 책임 원칙)
        // 하나의 클래스는 하나의 책임을 가져야하며, 하나의 이유로 변경되어야 한다.
        // 책임: 기능이 변경되어야 할 이유, 기능 변경을 요청하는 주체
        // 아래 UserService 객체는 세 가지의 다른 이유, 다른 주체에 의해 변경될 수 있다.
        UserService service = new UserService();
        service.sendWelcomeEmail(); // 환영메일 정책이 바뀔 수 있다. 요청 주체: 운영팀
        service.registerUser(); // 유저 등록 정책이 바뀔 수 있다. 요청 주체: 인사팀
        service.generateReport(); // 보고서 작성 정책이 바뀔 수 있다. 요청 주체: 애널팀
        // 따라서 UserService는 각각의 책임을 개별 클래스로 찢을 수 있다.
        
        // 직접 SRP를 구현해보기
        // ShoppingService { calculatePrice() saveOrderToDatabase() sendEmail() printReceipt() }
        // 1. 각 클래스가 하나의 목적에 집중하도록 분리한다.
        // 2. 메서드 이름과 출력 내용은 자유롭게 바꿔도 된다.
        // 3. main()에서 각 기능을 실행해 본다.
        // 4. 어떤 클래스가 왜 분리되었는지 주석으로 설명한다.

        // ShoppingService 클래스는 실제 계산과 관련된 책임만 전담한다. (계산, 거스름돈, 영수증 출력 등등...)
        ShoppingService sService = new ShoppingService();
        // ShoppingEmail 클래스는 이메일과 관련된 책임만 전담한다. (이메일 발송, 수신, 정리 등등...)
        ShoppingEmail sEmail = new ShoppingEmail();
        // ShoppingDatabase 클래스는 데이터베이스와 관련된 책 임만 전담한다. (DB 연결, DB 입출력 등등...)
        ShoppingDatabase sDatabase = new ShoppingDatabase();

        sService.calculatePrice();
        sService.printReceipt();
        // 영수증과 가격 계산의 분리에 대해서는 한번 더 생각해볼만 하다.
        // 만약 영수증이 계산의 부가적인 기능이 아니라, 어떠한 책임의 주체가 있다면 어떠할까?
        // (ex. 영수증의 디자인, 출력 형식 등등...)
        sEmail.sendEmail();
        sDatabase.saveOrderToDatabase();
        

        // Q. 결합도와 응집도의 차이는 무엇인가?
        // 결합도는 클래스 간의 문제, 응집도는 클래스 내부 구성 요소에 대한 문제다.
        // Q. 메서드가 10개인 클래스는 무조건 SRP 위반인가?
        // 메서드 10개가 모두 같은 책임을 가지고 있다면 SRP에 위반하지 아니한다.
        // Q. 클래스를 너무 많이 분리하면 오히려 어떤 문제가 생길 수 있을까?
        // 같은 책임을 가지고 있는 클래스가 여러 개 생겨서 프로젝트가 쓸데없이 복잡해질 수 있다.
    }
}
