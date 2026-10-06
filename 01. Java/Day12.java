class BankAccount {
    private String owner;
    private int balance;

    BankAccount(String owner, int balance) {
        this.owner = owner;
        this.balance = balance;
    }

    private boolean isValidAmount(int amount) {
        return amount > 0;
    }

    public void deposit(int amount) {
        if (!isValidAmount(amount)) {
            throw new IllegalArgumentException("올바르지 않은 입력값입니다.");
        }

        balance += amount;
    }

    public void withdraw(int amount) {
        // 먼저 값이 올바른지 검증
        if (!isValidAmount(amount)) {
            throw new IllegalArgumentException("올바르지 않은 입력 값입니다.");
        }
        // 계좌에 돈이 있는지 검증
        if (balance < amount) {
            throw new IllegalStateException("돈이 없습니다.");
        }

        balance -= amount;
    }

    public void showBalance() {
        System.out.println(owner + "님의 잔액: " + balance);
    }

    public String getOwner() {
        return owner;
    }

    public int getBalance() {
        return balance;
    }
}

public class Day12 {
    static void test(int a, int b) throws Exception {
        if(a == b){
            throw new Exception("문제 발생");
        }
    }

    public static void main(String[] args) {
        // 예외 
        // 프로그램이 정상적인 흐름을 계속 진행할 수 없는 상황이 발생하는 것
        int a = 10;
        int b = 0;
        int[] numbers = {10, 20, 30};
        String name = null;
        // System.out.println(a / b); // ArithimeticException이 발생한다.
        // System.out.println(numbers[5]); // ArrayIndexOutOfBoundsException이 발생한다.
        // System.out.println(name.length()); // NullPointerException이 발생한다.
        // 예외가 발생하면 이 다음으로 프로그램이 넘어가지 않는다.
        
        // try-catch
        // 예외가 발생한 경우 지정한 동작을 처리할 수 있다.
        System.out.println("시작");
        try {
            // try: 예외가 발생할 수 있는 코드
            System.out.println(a / b); 
        } 
        catch (ArithmeticException e) {
            // catch: 지정한 예외가 발생할 경우 처리할 코드
            // ArithmeticException -> 예외 객체의 한 종류
            // 예외도 객체이기 때문에 e.getMessage()와 같이 메서드를 사용할 수 있다.
            System.out.println("ArithmeticException 발생!"); 
        }
        // 예외가 발생해도 try-catch로 처리되어 코드가 여기까지 무사히 실행된다.
        System.out.println("종료");
        // 다형성
        // ArithmeticException을 비롯한 예외를 업캐스팅하여 사용할 수 있다.
        try {
            System.out.println(a / b);
        }
        catch (RuntimeException e) {
            System.out.println("RuntimeException->ArithmeticException 발생!");
        }
        // 멀티 캐치
        // 여러가지 예외를 처리할 수도 있다.
        try {
            System.out.println(a / b);
            System.out.println(numbers[5]);
        }
        catch (ArithmeticException e) {
            System.out.println("ArithmeticException 발생");
        }
        catch (NullPointerException | IndexOutOfBoundsException e) {
            System.out.println("NullPointerException 또는 IndexOutOfBoundsException 발생");
        }
        finally {
            // finally는 예외 발생과 상관 없이 마지막에 무조건 실행된다.
            System.out.println("Finally 실행"); 
        }
        
        // throw
        // 예외를 발생시키는 것도 가능하다. 
        // 예외 객체를 만들어서 던진다.
        // throw new IllegalArgumentException("나이는 음수가 될 수 없어요.");
        // new Illegal... -> 예외 객체를 만든다
        // throw -> 객체를 ㅅ용해 실제로 예외를 발생시키는 부분
        
        // throws
        // 메서드에서 예외가 "발생할 수도 있다"고 선언하고 다른 메서드에 처리를 떠넘긴다
        try {
            // throws가 붙은 함수는 무조건 try-catch로 처리하거나 다시 throws로 넘겨야 한다.
            test(5,5); 
        }
        catch (Exception e) {
            System.out.println("예외 처리");
        }

        // Exception의 구분
        // 예외는 크게 Exception -> CheckedException, (RuntimeException -> Unchecked Exception)
        // 으로 구분할 수 있다.
        // RuntimeException
        // 예시: NullPointer, Arithmetic, ArrayIndexOutof, IllegalArgument
        // 이런 것들은 컴파일러가 반드시 try-catch를 강제하지 않는다.
        // CheckedException
        // 예시 : IO, SQL
        // 이런 것들은 보통 Exception의 원인이 코드와 상관 없이 프로그램의 실행 범위 
        // 밖에서 일어날 수 있기에, 개발자에게 예외 처리를 강제한다.
        // 이런 경우 처리 메서드를 throws로 선언하거나, 직접 try-catch로 잡아야한다.
        
        // BankAccount 업그레이드 해보기
        BankAccount account = new BankAccount("철수", 10000);
        
        try {
            account.deposit(-5000);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    // A.
    // void withdraw(int amount) {
    //      if (amount <= 0) {
    //      System.out.println("잘못된 금액");
    //      return;
    // }
    // B.
    // void withdraw(int amount) {
    //      if (amount <= 0) {
    //      throw new IllegalArgumentException("잘못된 금액");
    // }
    // 무엇이 더 명확한 설계인가?
    // A는 예외가 발생해도 프로그램의 흐름이 메서드의 정상 return으로 계속 이어지지만
    // B는 예외가 발생한 시점에서 프로그램의 흐름을 전환시키고 어떻게 처리할지
    // 설계할 수 있기 때문에 더욱 안전하고 명확하다.

    // 객체지향적인 시사점
    // 예외를 throw 하는 것은 단순히 오류를 만드는 것이 아니라, 
    // 객체 자체가 "난 이런 것을 허용하지 않겠다"라고 규칙을 정의하고 외부에 알릴 수 있는 수단이다.
    // 즉, 캡슐화에서 더 나아가 잘못된 상태 변경을 거부하고 처리할 수 있는 수단을 제공한다.
    // 객체가 자신의 규칙을 정의 -> 잘못된 요청은 예외로 표현 -> 누가 예외를 처리할지 정함
}
