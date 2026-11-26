import java.util.List;

class MyThread extends Thread {
    // 스레드가 실행할 작업은 run()을 오버라이드하여 정의한다.
    @Override
    public void run() {
        System.out.println("Thread 작업 시작");
        System.out.println("Thread 작업 종료");
    }
}

class MyTask implements Runnable {
    // 스레드가 실행할 작업은 run()을 구현하여 정의한다.
    @Override
    public void run() {
        System.out.println("Runnable 작업 실행");
        List<Integer> nums = List.of(1, 2, 3, 4, 5);
        nums.forEach(System.out::println);
    }
}

public class Day55 {
    public static void main(String[] args) {
        // 멀티스레드
        // 실제 서버는 여러 사용자의 요청을 동시에 처리해야한다.

        // 프로그램, 프로세스, 스레드
        // - 프로그램: 디스크에 저장되어 있는 실행 가능한 코드
        // - 프로세스: 메모리에 올라가 실행중인 프로그램
        // - 스레드: 프로세스 내부에서 실제 명령어를 실행하는 흐름
        // 하나의 프로세스는 여러 스레드를 가질 수도 있다.

        // 싱글스레드, 멀티스레드: 스레드가 하나인가?
        // 싱글 스레드
        // - 실행 흐름이 하나다.
        // - 한 작업이 끝나야 다음 작업을 진행할 수 있다.
        // 멀티 스레드
        // - 여러 개의 스레드가 하나의 프로세스 내에서 실행된다.
        // - 여러 작업이 동시에 진행되는 것처럼 실행할 수 있다.
        
        // 동시성, 병렬성: 실제로 여러 작업을 하고 있는가?
        // 동시성
        // - 여러 작업을 같은 시간대에 진행할 수 있도록 실행을 조율하는 것
        // - 마치 동시에 작업을 실행하는 것처럼 보이지만, 실제로는 하나의 작업을
        // 빠르게 전환하며 진행한다.
        // 병렬성
        // - 여러 작업을 실제로 같은 순간에 실행하는 것
        // - CPU 코어가 여러 개라면 서로 다른 코어에서 동시에 실행될 수 있다.

        // Java 프로그램은 기본적으로 어떻게 시작할까?
        System.out.println("Hello");
        System.out.println("Java");
        System.out.println("Backend");
        // 이 프로그램은 main 메서드를 실행하는 main thread에서 시작한다.

        // 스레드 만들기
        // 기초적으로 Thread 클래스를 사용하면 스레드를 만들 수 있다.
        // 방법 1. Thread 클래스 상속받기: 작업 정의는 run() 메서드를 Override 한다.
        MyThread thread = new MyThread();
        // run()과 start()의 차이
        // - run(): 작업 내용을 일반 메서드처럼 직접 실행한다.
        // - start(): 새로운 스레드를 시작하고, 새 스레드가 run()을 실행한다.
        thread.start();
        System.out.println("Main 작업 실행");
        // 두 출력 결과의 순서는 고정되어 있지 않다
        // -> 두 스레드의 실행 순서가 확정되어있지 않기 때문이다.

        // 방법 2. Runnable을 구현해 작업을 정의하고 Thread에 전달하기
        Runnable task = new MyTask();
        Thread runnableThread = new Thread(task);
        runnableThread.start();
        System.out.println("Main 작업 실행");
        // Runnable을 사용하는 장점
        // - Thread를 상속하면 다른 클래스를 상속받을 수 없지만, 
        // Runnable은 인터페이스라 다른 클래스의 상속을 유지할 수 있다.
        // - 작업(Task)와 실행(Thread)을 분리할수 있다.

        // Runnable은 람다로도 표현할 수 있다.
        // -> 추상 메서드가 하나뿐인 함수형 인터페이스라 가능하다.
        Thread runnableThread2 = new Thread(() -> {
            System.out.println("Lambda Runnable 작업 실행");
        });
        runnableThread2.start();

        // Thread 2개 동시에 실행해보기
        Thread thread1 = new Thread(() -> {
            System.out.println("A-1");
            System.out.println("A-2");
            System.out.println("A-3");
            System.out.println("A-4");
            System.out.println("A-5");
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("B-1");
            System.out.println("B-2");
            System.out.println("B-3");
            System.out.println("B-4");
            System.out.println("B-5");
        });
        thread1.start();
        thread2.start();
        System.out.println("모든 스레드 실행 요청 완료");

        // Q1. thread.run()과 thread.start()의 가장 큰 차이는?
        // A1. run은 작업을 일반 메서드처럼 실행하고, start()는 새로운 스레드에서 실행한다.
        // Q2. 멀티 스레드를 사용하면 출력 순서가 달라질 수 있는 이유는?
        // A2. 스레드의 실행 순서가 확정되어 있지 않기 때문이다.
        // Q3. Runnable과 Thread를 분리하는 이유는?
        // A3. Task와 Thread를 분리하기 위해서
        // Q4. 동시성과 병렬성 차이는?
        // A4. 동시성은 여러 작업을 동시에 실행하는 것처럼 보이지만 한 번에 하나의 작업만을 실행하고,
        // 병렬성은 실제로 여러 작업을 동시에 실행한다.
        // Q5. Thread 두 개를 만들었는데 CPU 코어가 하나라면 멀티 스레드는 불가능할까?
        // A5. 동시성을 사용하면 가능하다. OS가 스레드마다 CPU 타임을 배분하고 전환하는 방식으로
        // 구현할 수 있다. 
    }
}
