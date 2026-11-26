class Counter {
    private int count = 0;

    public void increment() {
        count++;
    }
    
    public int getCount() {
        return count;
    }
}

public class Day56 {
    public static void main(String[] args) throws InterruptedException {
        // 멀티스레드와 동시성
        // 여러 스레드가 동일한 데이터를 동시에 수정하면 어떤 문제가 생기는가?
        // -> 백엔드 개발에서 매우 중요

        // 공유 자원 (Shared Resource)
        // - 공유 자원은 여러 스레드가 동시에 접근할 수 있는 자원이다.
        // - 스레드 A와 B가 같은 Counter 객체를 사용하면, 두 스레드 모두
        // 같은 count를 읽고 수정할 수 있다.
        Counter counter = new Counter();

        // 경쟁 상태 (Race Condition)
        // - 여러 스레드가 공유 자원에 동시에 접근하고 변경하면서, 실행 순서나
        // 타이밍에 따라 결과가 달라지는 문제        
        Runnable task = () -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        // 예로, count++은 겉으로 보면 한 줄이지만 실제로는 대략 세 과정으로 이루어진다.
        // - 1. count 값을 읽는다.
        // - 2. 읽은 값에 1을 더한다.
        // - 3. 새로운 값을 count에 저장한다.
        // Thread A와 Thread B가 공유 자원에 이 작업을 동시 수행한다고 생각해보자.
        // - 정상인 경우: 
        // Thread A 작업 완료(1) -> Thread B 작업 완료(2) -> count = 2
        // - 문제가 발생하는 경우:
        // Thread A 읽기(0) -> Thread B 읽기(0) -> ... Thread A 저장(1) -> Thead B 저장(1)
        // -> count = 1
        // 두 스레드 모두 0을 읽었기 때문에 각각 1을 게산하고, 둘 다 1을 저장한다.
        // 이런 문제를 갱신 손실(Lost Update)라고 한다.

        // 갱신 손실(Lost Update)
        // - 두 개 이상의 트랜잭션이 동시에 동일한 데이터를 읽고 갱신할 때,
        // 한 트랜잭션의 수정 사항이 다른 트랙잭션의 수정에 의해 덮어씌워져 
        // 연산 결과가 사리지는 것
        thread1.start();
        thread2.start();

        // 스레드 종료를 기다리기
        // - join()은 호출한 스레드가 대상 스레드의 종료를 기다리게 만든다.
        // - 이 과정을 거치지 않고 값을 출력하면 아직 작업이 끝나지 않았을수도 있다.
        thread1.join();
        thread2.join();
        // main 스레드가 thread1과 thread2의 종료를 기다리게 만든다.
        System.out.println(counter.getCount());
        // Race Condition에 의해 계속 결과가 바뀐다.
        
        // 공유 자원 여부 비교
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
        // 공유 자원이 아닌 경우에는 이러한 문제가 발생하지 않는다.
        thread1 = new Thread(task1);
        thread2 = new Thread(task2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter1.getCount() + " " + counter2.getCount());

        // 왜 백엔드에서 이 문제가 위험한가?
        // 금융 서버의 예시를 생각해보자.
        // - 요청 2개가 동시에 10,000원씩 출금하려고 한다.
        // - 갱신 손실로 두 요청이 동시에 출금했는데 한 번만 줄어들 수도 있다.
        // -> 사용자 자산, 서비스 데이터의 신뢰성 문제로 직결
        // 실제 금융 서버에서는 스레드 동기화만이 아니라, 여러 계층에서 이 문제를
        // 해결해야 한다.

        // Q1. Race Condition이란 무엇인가?
        // A1. 하나의 공유 자원을 여러 스레드가 조작하며 결과가 의도와 달라지는 것
        // Q2. count++가 원자적 연산이 아닌 이유는?
        // A2. 값을 읽기, 값을 증가, 값을 저장하는 등 여러가지 연산이 포함되어 있기 때문
        // Q3. Lost Update란 어떤 문제인가?
        // A3. 여러 스레드가 동시에 작업하다가 한 스레드의 작업 결과를 
        // 다른 스레드가 덮어쓰는 것
        // Q4. join()은 어떤 역할을 하는가?
        // A4. 호출한 스레드가 대상 스레드의 작업 완료를 기다리게 한다.
        // Q5. Thread 두 개가 같은 Counter를 사용하는 것과 서로 다른 Counter를 사용하는 것은
        // 어떤 차이가 있는가?
        // A5. 같은 Counter를 사용하면 공유 자원으로 Race Condition이 생길 수 있지만,
        // 다른 Counter를 사용하면 Race Condition이 생길 일이 없다.
        // Q6. Race Condition이 발생하는 코드가 실행했을 때 항상 잘못된 결과를 내는가?
        // A6. 가끔 우연히 결과가 들어맞을 수도 있다.
    }
}
