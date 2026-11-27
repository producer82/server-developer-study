class BankAccount {
    private int balance = 100000;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized void withdraw(int amount) {
        balance -= amount;
    }

    public synchronized int getBalance() {
        return balance;
    }
}

class Example {
    public synchronized void methodA() {
        System.out.println("methodA 시작");
        methodB();
        System.out.println("methodA 종료");
    }

    public synchronized void methodB() {
        System.out.println("methodB 실행");
    }
}

class SharedCounter {
    private static int count = 0;

    public static synchronized void increment() {
        count++;
    }
}

public class Day58 {
    public static void main(String[] args) {
        // 스레드 안전성(Thread Safety)과 동기화 설계
        
        // 스레드 안전성 (Thread Safety)
        // - 여러 스레드가 동시에 사용해도 프로그램의 데이터나 동작이 의도한대로
        // 올바르게 유지되는 특성
        // - 예를 들면, 이전의 synchronized 하지 않았던 Counter는 Thread-Safe 하지 않다.

        // 스레드 안전성의 핵심
        // - 단순히 프로그램이 오류 없이 실행되는 것을 의미하지 않는다.
        // - 1. 공유 데이터가 깨지지 않는다.
        // - 2. Race Condition으로 인해 잘못된 값이 발생하지 않는다.
        // - 3. 동시 실행에서도 데이터의 일관성을 유지한다.
        // - 4. 메서드 실행 결과가 의도한 규칙을 만족한다.
        // - 즉, 올바른 동작을 보장하는지가 핵심이다.

        // 여러 메서드 간의 동기화 설계
        // - Counter 클래스에서는 increment() 하나만 동기화했다.
        // 그러나, 실제 클래스에서는? 
        // -> 여러 메서드가 존재하는 경우가 대부분이다.
        BankAccount bankAccount = new BankAccount(100000);
        // 이 클래스에서는 모든 메서드가 synchronized로 선언되어있다.
        // 이 세 메서드는 모두 동일한 BankAccount 객체의 Lock을 사용한다.
        bankAccount.deposit(50000);
        bankAccount.withdraw(20000);
        System.out.println(bankAccount.getBalance());
        // 즉, 이 객체에서는
        // - Thread 1이 deposit()을 실행중이라면
        // - Thread 2는 withdraw()를 실행하기 위해 대기한다.
        // - Thread 3도 getBalance()에 진입하기 위해 대기한다.
        // 이러한 원리가 여러 메서드 간 동기화 설계의 기초다.

        // 만약 세 메서드를 모두 동기화하지 않았다면?
        // - Thread 1이 synchronized withdraw()을 실행중이다.
        // - Thread 2가 getBalance()를 실행했다.
        // - Thread 2의 작업이 Thread 1보다 먼저 끝났다.
        // -> withdraw 이전의 잔액이 조회된다.
        // 즉, 변경 작업과 조회 작업은 일반적으로 일관된 동기화 규칙을 가져야 할 필요가 있다.

        // 여러 작업을 하나의 원자적인 작업으로 보호하기
        // - 실제로 이루어지는 작업은 여러 단계여도, 이들이 모두 논리적인 하나의 작업이라면?
        // -> 보통은 전체 과정이 하나의 Lock 안에서 실행될 수 있도록 설계해야 한다.
        // 만약 잔액 확인 메서드와 출금 메서드가 각각 분리되었다면?
        // - Thread A와 B가 계좌에서 전액을 인출하는 과정을 생각해보자.
        // - 1. Thread A가 잔액을 확인하고 Lock을 반환했다.
        // - 2. Thread A의 출금 메서드가 가져가야할 Lock을 Thread B가 먼저 채갔다.
        // - 3. Thread B가 잔액을 확인했더니 잔액이 충분하다.
        // -> 두 스레드가 모두 잔액을 인출해버려 잔액이 음수가 된다.
        // 즉, 개별 메서드가 Thread-Safe 하다고 전체 작업까지 Thread-Safe 하다는 보장은 없다.
        // - 중요한 작업은 가능한 하나의 동기화 메서드 안에서 처리하도록 설계하는 것이 좋다
        // - 외부에서 여러 메서드를 조합해야 하는 경우 전체 작업을 보호할 방법이 필요하다.

        // syncronized의 재진입성(Reentrancy)
        // - 이미 Lock을 확보한 스레드가 같은 Lock을 다시 확보할 수 있다
        Example example = new Example();
        example.methodA();
        // 내부에서 다른 메서드를 호출해도 왜 멈추지 않을까?
        // - 만약 Lock이 재진입 불가능하다면 현재 스레드가 자신이 가진 Lock을 기다리게 된다
        // -> 프로그램이 멈춘다
        // 하지만, synchronized는 Lock을 들고서 다른 메서드에 재진입이 가능하다.
        // 따라서, 자신이 가진 Lock을 들고 Method B를 호출할 수 있기 때문에 프로그램이 멈추지 않는다.
        
        // static synchronized
        // - this가 없는 static 메서드에 synchronized를 붙이면 어떻게 될까?
        SharedCounter counter = new SharedCounter();
        // static synchronized는 해당 클래스에 대한 Lock을 사용한다
        // - 코드로 표현하면 synchronized (SharedCounter.class)와 같다.
        // - ounter 객체를 100개 생성해도 static synchronized 메서드는 모두 동일한 Lock을 사용하게 된다.
        // 즉, 서로 다른 객체를 생성해도 static synchronized 메서드끼리는 동일한 Lock을 사용한다.

        // Lock을 직접 지정하기
        // - 별도의 Lock 객체를 만들어 사용할 수도 있다.
        final Object lock = new Object();
        // 이 객체는 오직 Lock 역할만 수행한다.
        // 왜 별도의 Lock을 사용할까?
        // - 외부에서 해당 Lock 객체에 접근하기 어렵다.
        // - 동기화 규칙을 클래스 내부에서 관리할 수 있다.
        // - 보호해야 하는 공유 데이터와 Lock의 연결이 명확해진다.

        // Thread-Safe 클래스를 설게하는 체크리스트
        // - 여러 스레드가 공유하는 데이터 있는가?
        // - 그 데이터는 변경 가능한가?
        // - 공유 데이터에 접근하는 메서드들의 동기화 규칙이 일관적인가?
        // - 하나의 논리적 작업이 여러 단계로 이루어져 있지 않은가?
        // - 필요한 범위만 동기화하고 있는가?
        // - Lock 대상이 명확하며, 같은 자원에 접근하는 코드가 동일한 Lock을
        // 사용하는가?
    }
}
