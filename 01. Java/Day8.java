class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void introduce1() {
        System.out.println("나는 " + name + "이고 " + age + "살 입니다.");
    }

    void introduce2() {
        System.out.println("나는 사람입니다.");
    }
}

class Student extends Person {
    int studentId;

    Student(String name, int age, int studentId) {
        // java24부터는 맨 첫줄에 super()가 오지 않아도 된다.
        // System.out.println("자식 생성자"); 
        super(name, age);
        this.studentId = studentId;
    }

    // @Override 태그를 통해 자바에게 오버라이드된 메서드라고 알릴 수 있다.
    // 이게 있으면 오타나 실수가 발생할 경우 컴파일러가 에러로 알려준다.
    // 없어도 오버라이드 자체는 된다.
    @Override 
    void introduce2() {
        System.out.println("나는 학생입니다.");
    }

    void introduce3() {
        super.introduce2(); // 부모의 introduce2()를 호출한다.
        System.out.println("그리고 학생입니다.");
    }

    void study() {
        System.out.println(name + "이 공부합니다.");
    }
}

class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    void eat() {
        System.out.println(name + "가 먹습니다.");
    }

    void sound() {
        System.out.println("울어요");
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
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
        System.out.println("야옹!");
    }
}

public class Day8 {
    public static void main(String[] args) {
        // 상속이란?
        // 하위 클래스가 상위 클래스로부터 필드와 메서드를 
        // 물려받아 새로 작성하거나 재사용하는 것

        // 상속이 필요한 이유는 무엇일까?
        // 여기 사람이 있다.
        Person p1 = new Person("철수", 20);
        p1.introduce1();
        p1.introduce2();
        // 이제 학생을 만들려 한다.
        // 학생은 이미 사람인데 똑같이 이름, 나이를 다시 만들어줘야 할까?

        // extends
        // extends를 사용하여 상위 클래스를 상속할 수 있다.
        Student s1 = new Student("두한", 19, 2022);
        // 하위 클래스는 상위 클래스의 기능과 자신만의 기능을 둘 다 가진다.
        s1.introduce1(); 
        s1.study(); 
        // 여기서 사람을 "부모 클래스 / Super Class"
        // 학생을 "자식 클래스 / Sub Class"라고 한다.
        // 특히, 이런 관계가 A는 B다라고 설명되어 is-a 관계라고 한다.
        // Dog is a Animal -> O
        // Student is a Person -> O// Car is a Engine -> X / 상속하기 부적합
        // Smartphone is a Phone -> O
        // BankAccount is a Bank -> X / 상속하기 부적합
        
        // 메서드 오버라이딩
        // 부모가 가진 메서드의 기능을 바꾸고 싶다면 오버라이딩을 할 수 있다.
        s1.introduce2();
        
        // super
        // super는 부모 클래스를 가리키는 키워드다.
        // this는 객체 자신을 가리키는 키워드였던 것과 비슷하다.
        s1.introduce3();
        // 다만 부모의 필드가 private라면 super로 접근할 수 없다.        

        // 생성자의 상속
        // 자식 생성자는 super()로 부모 생성자를 호출하고 필요한 값을 넘겨준다.
        // 부모에게 기본 생성자가 있는 경우 명시하지 않아도 자동으로 호출되기도 한다.
        // 이 경우에는 super()가 생성자의 맨 첫줄에 들어간다.
        Student s2 = new Student("영희", 17, 2022);
        s2.introduce1();
        s2.introduce2();
        s2.introduce3();

        // 왜 생성자의 상속 과정이 꼭 필요한가?
        // 1. 자식이 만들어지려면 부모가 있어야 하는데 그 과정에서 우선 부모 생성자가
        // 먼저 호출되어야 함
        // 2. 자식은 부모의 필드를 상속받는데, 부모 생성자를 호출하지 않으면
        // 그 필드를 올바르게 초기화 할 수 없음
        System.out.println("==============================");

        // 동물 클래스 만들기
        Dog dog = new Dog("초코");
        Cat cat = new Cat("나비");

        dog.eat();
        dog.sound();

        cat.eat();
        cat.sound();
    }
}
