class Counter {
    private int count = 0;

    // 이 메서드는 해당 객체의 Lock을 확보한 상태에서 실행된다.
    public synchronized void increment() {
        count++;
    }

    // 동기화된 최신 값을 확보하기 위해 synchronized를 사용
    public synchronized int getCount() {
        // synchronized (this) {
            return count;
        // }
    }

    public void setCount(int count) {
        this.count = count;
    }
}

public class Day57 {
    public static void main(String[] args) throws InterruptedException {
        // Race Condition과 Lost Update를 해결하는 방법
        // 한 번에 한 스레드만 접근하게 제한하자

        // 왜 이런 문제가 발생했을까?
        // -> 내부적으로 여러 작업을 수행하며 서로의 변경 결과를 덮어쓰기 때문이다.

        // 임계 영역 (Critical Section)
        // - 여러 스레드가 접근할 수 있는 공유 자원 중, 동시 접근 시 문제가 발생할
        // 수 있어 보호해야 하는 코드
        // - 이전 예제의 increment()는 공유 자원인 count를 수정하므로 임계 영역이
        // 될 수 있다.
        
        // 임계 영역에서 필요한 것
        // - 여러 스레드가 동시에 실행하지 못하도록 제한
        // - 공유 데이터의 일관성 유지
        // - Race Condition 방지
        // 어떻게 구현할까? -> synchronized

        // synchronized: 동기화 키워드
        // - 동기화된 코드에 동일한 Lock(잠금)을 사용하는 스레드가 접근할 때, 
        // 한 스레드가 Lock을 확보하고 실행 중이면 다른 스레드는 Lock 이 풀릴 때까지 기다려야 함.
        // - 쉽게 비유하면?: 화장실 열쇠
        // - 1. 열쇠는 하나뿐이다.
        // - 2. A 스레드가 열쇠를 가지고 화장실에 들어갔다
        // - 3. B 스레드는 A 스레드가 열쇠를 내놓을 때까지 기다려야 한다.
        // - 4. A 스레드가 열쇠를 주면 비로소 B 스레드가 사용할 수 있다.
        
        // synchronized 메서드
        Counter counter = new Counter();
        // 이 메서드는 synchronized 키워드로 만들어졌다.
        counter.increment();
        // 이제 두 스레드가 동시에 count++을 수행할 수 없다.
        // -> Lost Update 문제 해결
        
        // Lock은 정확히 무엇을 잠그는가?
        // - synchronized 메서드는 내부적으로 다음과 같은 의미를 갖는다
        // 메서드 { synchronized (this) { 작업 } }
        // - this는 객체 자신을 의미하므로, 인스턴스 메서드에 synchronized를 붙이면
        // 현재 객체의 Lock을 사용하게 된다.
        // - 따라서 같은 객체를 공유하는지, 아니면 서로 다른 객체인지가 중요해진다.

        // 같은 객체를 공유할 때
        Runnable task = () -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        // 두 스레드는 동일한 Lock을 사용하게 된다.
        // -> 한 번에 한 스레드만 increment()에 진입할 수 있다.

        // 서로 다른 객체라면?
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Runnable task1 = () -> {
            for (int i = 0; i < 100000; i++) {
                counter1.increment();
            }
        };
        Runnable task2 = () -> {
            for (int i = 0; i < 100000; i++) {
                counter2.increment();
            }
        };
        Thread thread3 = new Thread(task1);
        Thread thread4 = new Thread(task2);
        thread3.start(); thread4.start();
        thread3.join(); thread4.join();
        System.out.println(counter1.getCount());
        System.out.println(counter2.getCount());
        // 두 스레드는 다른 Lock을 사용하게 된다.
        // -> 두 스레드는 동시에 실행될 수 있다.
        // 즉, 같은 Lock을 사용하는 코드끼리만 서로 기다리게 된다.

        // synchronized 메서드와 synchronized 블록
        // - 동기화를 적용하는 방법에는 크게 두 가지가 있다.
        // 방법 1. 메서드 전체 동기화
        // - synchronized 키워드를 사용한다.
        // - 메서드 전체에 현재 객체의 Lock을 적용한다
        // 방법 2. 필요한 부분만 동기화
        // - synchronized (this) { }를 사용한다.
        // - 필요한 특정 코드 블록에만 Lock을 적용한다.
        
        // 실제 코드로 Race Condition 해결해보기
        counter.setCount(0);
        thread1 = new Thread(task);
        thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter.getCount());

        // synchronized의 한계
        // 1. 같은 객체 내에서만 Lock이 공유됨
        // -> 서로 다른 객체는 다른 Lock을 가지고 있다.
        // 2. 실행 속도가 느려질 수 있음
        // -> 동시에 실행 가능한 작업까지 무조건 기다리게 하면 효율이 떨어진다.
        // 3. 교착 상태(Deadlock)이 발생할 수 있음.
        // -> 두 스레드가 서로 가진 Lockd을 기다리는 상황이 발생하면 프로그램이 멈춤
        // 예를 들어: 
        // - Thread A는 Lock 1을 가지고 Lock 2를 기다림.
        // - Thread B는 Lock 2를 가지고 Lock 1을 기다림.

        // Q1. 임계 영역이란 무엇인가? 왜 보호해야하는가?
        // A1. 여러 스레드가 동시 접근 시 문제가 발생할 수 있는 코드
        // Lost Update 등이 발생할 수 있으므로 보호해야 한다.
        // Q2. synchronized를 붙인 인스턴스 메서드는 어떤 객체의 Lock을 사용하는가?
        // A2. 현재 객체 자신의 Lock을 사용한다.
        // Q3. 두 스레드가 동일한 Counter 객체의 synchronized increment()를 호출한다면,
        // 두 스레드가 동시에 메서드 내부에서 실행될 수 있는가?
        // A3. 실행될 수 없다. 한 스레드는 다른 스레드가 Counter 객체의 Lock을 내놓기 전까지
        // 코드를 실행할 수 없다.
        // Q4. 두 코드의 동기화 범위 차이는?
        // public synchronized void increment() {
        //     count++;
        // }
        // public void increment() {
        //     synchronized (this) {
        //         count++;
        //     }
        // }
        // A4. 첫번째는 메서드 전체를 동기화하고, 두번째는 count++만 동기화한다.
        // Q5. 아래 코드에서 두 스레드가 동시에 increment()를 실행할 수 있는가?
        // Counter counter1 = new Counter();
        // Counter counter2 = new Counter();
        // Thread thread1 = new Thread(() -> counter1.increment());
        // Thread thread2 = new Thread(() -> counter2.increment());
        // A5. 실행할 수 있다. 서로 다른 객체이기 때문에 다른 Lock을 사용하기다.
        // Q6. synchronized의 한계나 주의점 한 가지는?
        // A6. 교착 상태에 빠지거나, 실행 속도가 느려질 수 있다.
    }
}
