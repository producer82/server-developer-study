interface Fly {
    void fly();
}

abstract class Animal {
    String name;

    Animal(String name) {
        this.name = name;
        System.out.println("부모 생성자");
    }

    void eat() {
        System.out.println(name + "가 먹습니다.");
    }

    // 추상 클래스에서는 추상 메서드를 사용할 수 있다.
    // 대신 자식이 반드시 구현해야 한다.
    abstract void sound();
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
        System.out.println("자식 생성자");
    }

    @Override
    void sound() {
        System.out.println("멍멍!");
    }
}

class Cat extends Animal {
    Cat(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println("야옹");
    }   
}

class Bird extends Animal implements Fly {
    Bird(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println("짹짹");
    }

    @Override
    public void fly() {
        System.out.println("새가 날아요");
    }
}

class Airplane implements Fly {
    @Override
    public void fly() {
        System.out.println("비행기가 날아요");
    }
}

public class Day11 {
    public static void main(String[] args) {
        // 인터페이스는 일종의 규칙을 부여하는 역할을 했다.
        // 그럼 규칙뿐만 아니라 공통적인 상태와 구현까지 물려주고 싶으면 어떻게 해야할까?
        // 추가로, 인터페이스는 무조건 public과 private 상태의 규칙만 만들 수 있었다.
        // 그렇다면 package-privated 상태의 규칙 부여는 할 수 없는건가?
        
        // 추상 클래스
        // 공통된 상태와 구현, 규칙을 물려줄 수 있으며, 
        // 이를 통해 공통적인 "부모"라는 개념을 정의 할 수 있다.
        // 동시에 package-private 상태의 규칙 부여를 가능하게 한다.
        // Animal animal = new Animal("동물"); // 추상 클래스는 객체로 만들 수 없다.
        Animal dog = new Dog("초코"); // 이건 가능하다.

        // 인터페이스와의 차이
        // 추상 클래스는 상태 + 구현 + 추상 메서드를 동시에 가질 수 있다.
        // 즉, 공통적으로 가지고 있는 것과 하는 것, 그리고 자식마다 달라지는 것을 
        // 하나로 표현할 수 있다.
        dog.eat();
        dog.sound();
        // 추상 클래스는 extends를 사용한다.
        // 즉, 이렇게 보면 서로 간의 관계가 나름 정돈된다.
        // extends = is-a, implements = can-do
        // 인터페이스와 추상 클래스만 따지고 보면, 상황별로 둘 중 무엇을 사용해야 할지는 구분하기 쉽다.

        // 추상 클래스의 생성자
        // 추상 클래스도 생성자를 가질 수 있으며, 객체는 만들 수 없지만 자식 객체가 
        // 생성되는 과정에서 추상 클래스의 생성자는 실행될 수 있다.
        Animal cat = new Cat("야옹이");
        
        // 다형성
        // 추상 클래스도 참조 변수 타입으로 지정할 수 있기 때문에 업캐스팅 할 수 있다.
        Animal[] animals = {
            cat,
            dog
        };
        for (Animal animal : animals) {
            animal.eat();
            animal.sound();
        }

        // 인터페이스와 상속 둘 다 사용하기
        // bird는 extends Animal implements Fly이기 때문에 무려 세 가지 참조 타입을 쓸 수 있다.
        Animal bird1 = new Bird("짹짹이");
        Bird bird2 = new Bird("짹짹삼");
        Fly bird3 = new Bird("짹짹사");
        // airplane은 상속을 안받아서 두 가지 참조 변수만 가능하다.
        Fly airplane1 = new Airplane();
        Airplane airplane2 = new Airplane();
        // bird1에서는 상속으로 받은 것만 쓸 수 있다.
        bird1.sound();
        bird1.eat();
        // bird2에서는 상속으로 받은 것, 인터페이스로 받은 것 전부 쓸 수 있다.
        bird2.sound();
        bird2.eat();
        bird2.fly();
        // bird3에서는 인터페이스로 받은 것만 쓸 수 있다.
        bird3.fly();
        // airplane은 어차피 fly밖에 못쓴다.
        airplane1.fly();
        airplane2.fly();
        // 중요한 것은 아래 관계를 이해하는 것:
        // Bird는 Animal이면서 Fly 할 수 있다. (Bird is an Animal and can do Fly)
        // Airplane은 Fly 할 수 있다. 하지만 Animal은 아니다. (Airplane can do Fly,)
        
        // 상속과 인터페이스 중 어느 것이 적합한지 분류해보자
        // Animal → Dog, Cat / 상속 -> 개는 동물이다
        // Flyable → Bird, Airplane, Drone / 인터페이스 -> 새는 날 수있다
        // Payment → CardPayment, TossPayment / 인터페이스 -> 카드는 지불 할 수 있다
        // Car → Engine / 둘 다 X -> 자동차는 엔진도 아니고, 자동차는 엔진을 하는 것도 아님.
        // 만들자면 Car가 Engine을 부품으로 가지고(has-a) 있어야 함.
        // Employee → Developer, Designer / 상속
        // Runnable → 여러 종류의 작업 객체 / 인터페이스

        // 실제로 클래스랑 인터페이스로 필요한 거의 모든 기능이 커버된다.
        // 이것은 아주 중요한 질문이다.

        // 사례 1. 상속과의 차이
        // - 만약 위의 Animal을 클래스로 구현한다고 하면, void sound() { }의 형태로 빈 메서드를 만들어야 한다.
        // - 이제 class Animal과 abstarct class Animal은 같은 결과를 만들어낸다.
        // - 여기서 중요한 것은 내가 Animal을 만든 의도다.
        // 첫번째, 상속을 사용하는 의도
        // - 나는 자식 객체도 원하지만, Animal이라는 "실체"도 원하는 것이다.
        // - 이런 맥락에서는 Animal의 void sound()가 자신만의 기능을 가져야한다. 
        // - 그리고 자식은 이것을 필요에 따라 Override 할 수도 있고, 안할 수도 있다.
        // 두번째, 추상 클래스를 사용하는 의도
        // - 나는 Animal이라는 "실체"가 아니라 "개념"을 원하는 것이다.
        // - 이런 경우 void sound()는 굳이 자신만의 기능을 가질 필요가 없다.
        // - 즉, "동물이 성대로 발성하여 공기를 진동시킨다" 같은 기능이 필요한게 아니라
        // 그저 "동물은 운다"라는 개념만으로 충분한 것이다.
        // - 하지만 여기서 동물은 반드시 울어야 하기 때문에 추상 클래스로 자식이 이것을 반드시
        // Override 하도록 강제할 수 있다.
        // - 다만 sound()가 아무것도 안한다면 Animal 객체가 존재할 이유가 없기 때문에 이것을
        // 코드 수준에서 "구현 불가"라는 시스템으로 강제하는 것이다.
        
        // 사례 2. 인터페이스와의 차이
        // - 그럼 이것은 인터페이스로도 해결할 수 있는 문제 아닌가?
        // - 설계적 의도로 이 질문을 해결 할 수 있다.
        // 인터페이스를 사용하는 의도
        // - "너는 이런 기능/역할을 가지고 있어야 한다"가 핵심이다.
        // - 그래서 얘는 원래 공통적인 구현체를 가질 수 없었다.
        // - 요즘은 default 문법으로 구현체를 만들 수 있지만, 본래의 핵심은 변하지 않는다.
        // 추상 클래스를 사용하는 의도
        // - "너는 이런 공통된 기반(상태/구현)을 공유하고 있어야 한다"가 핵심이다.
        // - 그래서 얘는 공통적인 구현체를 가질 수 있다.

        // 두 사례에서 알 수 있는 것:
        // 객체지향 프로그래밍은 결과보다 설계 의도 및 거기서 비롯되는 객체간의 역할, 관계를 중요시한다.
        // 두 방법이 같은 결과를 만들어도, 개발자의 설계 의도가 훨씬 중요하다는 것이다.
        // 즉, 지금 객체지향 프로그래밍은 단순히 내가 원하는 기능을 구현하는 맥락으로 접근하면 안되고
        // 내가 원하는 "의도"를 구현하는 맥락에서 해석해야 한다.   
        // 이게 바로 Bird를 Fly 시킬 수 있는 방법은 많지만, 이것을 인터페이스로 구현하고
        // "Bird는 Fly라는 기능을 수행할 수 있다"라고 표현하는 이유다.

        // 그럼 결국 어디로 발전해야 하는가?
        // - 내가 구현해야 하는 것이 무엇인지 파악하고, 이것을 객체의 어떤 기능과 
        // 관계로 풀어낼 것인가하는 분석 능력
        // - 결국 객체의 관계 및 기능을 적절히 설정하여 큰 프로그램으로 만들어내는 구현 능력
    }
}
