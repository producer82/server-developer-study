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

class Engine {
    private int maxSpeed;

    Engine(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    String start() {
        return "시동";
    }

    int getMaxSpeed() {
        return maxSpeed;
    }
}

class Address {
    String city;

    Address(String city) {
        this.city = city;
    }
}

class Person {
    String name;
    Address address;

    Person(String name) {
        this.name = name;
    }

    void introduce() {
        System.out.println(name);
    }
}

public class Day15 {
    public static void main(String[] args) {
        // 참고와 객체 복사, Composition
        // 이제는 "객체를 어떻게 설계해야 하는가?"를 봐야한다.

        // "객체를 복사한다"는게 정확히 무슨 뜻일까?
        // 일단 참조 변수는 객체 자체가 아니다.
        Person p1 = new Person("철수");
        // 이건 객체를 복사한게 아니라 참조값을 복사한 것이다.
        Person p2 = p1;
        // p1과 p2는 같은 객체를 바라보고 있으니, 다음과 같은 결과가 나온다.
        p2.name = "영희";
        System.out.println(p1.name);
        // 그래서 이건 복사가 아니다.
        // 객체를 복사하고 싶으면 이렇게 해야한다.
        p2 = new Person(p1.name);
        p2.name = "철수";
        // 이제 p1과 p2은 다른 객체다.
        System.out.println(p1.name);

        // 얕은 복사
        // 만약 객체 안에 객체가 있다면 어떻게 될까?
        Address address = new Address("서울");
        p1.address = address;
        // "p1 -> [Person] name = 철수, address -> [Address] city = 서울"의 구조다.
        // 여기서 만약 내가 p1을 p2로 복사하고 싶어서 다음과 같이 하면...
        p2 = new Person(p1.name);
        p2.address = p1.address;
        // Person은 다른 객체지만, address가 참조 변수이기에 여전히 같은 객체를 가리키고 있다.
        // 그래서 다음과 같은 문제가 발생한다.
        p2.address.city = "부산";
        System.out.println(p1.address.city);
        // 이게 얕은 복사의 문제다.

        // 깊은 복사
        // 깊은 복사는 원본과 복사본 간에 완벽한 독립성을 주는 것이다.
        // 따라서 모든 참조를 끊어야한다.
        Address newAddress = new Address(p1.address.city);
        p2 = new Person(p1.name);
        p2.address = newAddress;
        // 그러면 이제 다음과 같이 된다.
        p2.address.city = "서울";
        System.out.println(p1.address.city);

        // 그러면 항상 깊은 복사를 해야할까?
        // 아니지... 다른 객체를 공유하는게 의도된 설계일 수도 있음
        // 여러 자동차가 엔진을 갖고있다고 해서, 전부 다른 종류의 엔진일 필요는 없잖아?

        // Composition
        // 객체 안에 다른 객체를 필드로 가지고 있는 구조를 말한다.
        // has-a 관계로 나타낼 수 있다.
        Engine engine = new Engine(150);
        Car car1 = new Car("소나타", engine);
        Car car2 = new Car("아반떼", engine);
        // 이건 Composition(has-a) 설계인 동시에 객체 공유를 하고 있다.
        // 여러 자동차는 모두 150km를 내는 엔진을 가질 수도 있으니 객체 공유를 해도 되는거지
        car1.start();
        car1.speed();
        car2.start();
        car2.speed();

        // 관계의 비교
        // 상속은 강한 결합을 만든다... Car extends Engine은 의미적으로도 이상하다.
        // A는 B의 일종이다 -> extends
        // A는 B를 할 수 있다 -> implements
        // A가 B를 가지고 있다 -> composition
        // A와 B의 관게가 단순히 기능 공유 목적 -> composition을 고려
        // Car has a Engine이 제일 자연스럽고 -> 그래서 Composition으로 구성된 것...
        
    
    }
}
