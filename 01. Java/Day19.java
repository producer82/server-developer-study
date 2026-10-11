interface Machine {
    void print();
    void scan();
    void fax();
}

interface Printable {
    void print();
}

interface Scannable {
    void scan();
}

interface Faxable {
    void fax();
}

class MultiFunctionPrinter implements Machine {
    @Override
    public void print() {
        System.out.println("출력합니다.");
    }

    @Override
    public void scan() {
        System.out.println("스캔합니다.");
    }

    @Override
    public void fax() {
        System.out.println("팩스를 보냅니다.");
    }
}

class ScannerFaxMachine implements Scannable, Faxable {
    @Override
    public void scan() {
        System.out.println("스캔합니다.");
    }

    @Override
    public void fax() {
        System.out.println("팩스를 보냅니다.");
    }
}

class Printer implements Printable {
    @Override
    public void print() {
        System.out.println("출력합니다.");
    }
}

class ScannerMachine implements Scannable {
    @Override
    public void scan() {
        System.out.println("스캔합니다.");
    }
}

interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {
    @Override
    public void work() {
        System.out.println("일합니다.");
    }

    @Override
    public void eat() {
        System.out.println("먹습니다.");
    }
}

class Robot implements Workable {
    @Override
    public void work() {
        System.out.println("일합니다.");
    }
}

public class Day19 {
    public static void main(String[] args) {
        // ISP (Interface Segregation Principle, 인터페이스 분리 원칙)
        // 클라이언트는 자신이 사용하지 않는 메서드에 의존하도록 강요받아서는 안된다.
        // 즉, 필요도 없는 기능을 클래스에게 강제로 구현하도록 하지 말라는 뜻이다.
        
        // 예를 들어, 우리가 만들 기계에 출력, 스캔, 팩스 기능이 있다고 가정해보자.
        // 복합기는 세 가지 기능을 모두 지원할 수 있다.
        // 그래서 지금은 Machine이 무척 잘 어울리는 것처럼 보일 수도 있다.
        MultiFunctionPrinter multiFunctionPrinter = new MultiFunctionPrinter();
        multiFunctionPrinter.print();
        multiFunctionPrinter.scan();
        multiFunctionPrinter.fax();
        
        // 근데 우리는 프린터도 만들어야 한다.
        // 이 상황에서 Machine을 implements 받게되면 쓰지 못하는 기능도 구현해야 한다.
        // API 설계 의도에 따라서는 쓰지 않는 기능만 예외처리 하는 것이 적절할 수도 있겠지만,
        // 지금 상황에서 이 설계는 ISP 관점에서 문제가 생긴다.
        
        // ISP를 적용하여 기능별로 인터페이스를 분리하면 문제가 해결된다.
        // 이제 클래스는 자신이 필요한 기능만 구현해도 된다.
        Printer printer = new Printer();
        ScannerMachine scanner = new ScannerMachine();
        ScannerFaxMachine extendedScanner = new ScannerFaxMachine();
        printer.print();
        scanner.scan();
        extendedScanner.scan();
        extendedScanner.fax();
        // 핵심은 인터페이스를 많이 만드는 것이 아니라, 각 인터페이스가 명확한 목적을 갖게 만드는 것이다.

        // LSP와 ISP의 차이
        // 두 원칙은 서로 관련이 깊지만, 같은 원칙은 아니다.
        // LSP: 하위 타입을 상위 타입 대신 사용해도 의도대로 동작하는가?
        // ISP: 클래스가 사용하지 않는 기능까지 구현하도록 강요받고 있는가?
        // 즉, ISP는 구현을 강요하는지 보는 규칙이고, LSP는 구현이 의도대로 동작하는지 보는 규칙이다.
        // ISP를 적용하면 LSP를 지키기 쉬워질 수 있지만, 이것이 보장되는 것은 아니다.
        

        // SRP와 ISP의 차이
        // 두 원칙 또한 서로 깊은 연관을 가지고 있지만, 같은 원칙은 아니다.
        // SRP: 클래스가 너무 많은 변경 이유를 가지고 있지는 않은가?
        // "인터페이스"에 여러 기능을 넣었다면 구현이 강제되어 ISP가 발생할 수 있다.
        // 반면, 클래스에 관련 없는 여러 기능을 넣었다면 SRP 문제를 의심해볼 수 있다.

        // 인터페이스 분리해보기
        //
        // interface Worker {
        //     void work();
        //     void eat();
        // }

        // class Robot implements Worker {
        //     @Override
        //     public void work() {
        //         System.out.println("로봇이 작업합니다.");
        //     }

        //     @Override
        //     public void eat() {
        //         throw new UnsupportedOperationException();
        //     }
        // }
        //
        // Q. 지금 설계가 왜 ISP 관점에서 문제가 되는가?
        // A. 로봇은 먹을 필요가 없는데, 이것의 구현을 인터페이스에 의해 강요받고 있다.
        // Q. Robot이 eat()을 구현하지 않아도 되게 하려면?
        // A. Worker 인터페이스가 가지고 있는 기능을 분리시켜야 한다.
        Robot robot = new Robot();
        Human human = new Human();
        robot.work();
        human.eat();
        human.work();
    }
}
