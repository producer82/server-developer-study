final class Person {
    static final int MAX_USERS = 100; // final이라 더이상 대입 불가

    String name;
    Person(String name) {
        this.name = name;
    }

    void introduce() {
        System.out.println("저는 " + name + "입니다.");
    }
}

class Student /*extends Person*/ {

}

class Animal {
    private final String name;
    private final int age;
    private final Person person = new Person("영희");

    // 생성자가 끝난 이후 값을 다시 넣을 수 없다.
    Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 세터도 없다.
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Person getOwner() {
        return person;
    }
}

class Parent {
    int num = 0;
    final void hello() {
        System.out.println("하이염");
    }
}

class Child extends Parent {
    // 컴파일 오류
    // @Override
    // void hello() { 
    //     System.out.println("바이염");
    // }
}

class Money {
    // 외부에서 직접 접근 불가 & 생성 후 변경 불가
    private final int amount; 
    private final String currency;

    // 생성할 때 값을 받는다.
    Money (int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "종류: " + currency + ", 금액: " + amount; 
    }
}

public class Day14 {
    static final double PI = 3.14159;
    static final int MAX_SIZE = 100;
    static final double TAX_RATE = 0.1;

    public static void main(String[] args) {
        // final과 불변 객체(Immutable Object)

        // final
        // final의 핵심은 "더 이상 변경하지 못하게 한다"라는 것에 있다.
        // 하지만, 변수, 메서드, 클래스 중 무엇을 변경하지 못하게 하는지에 따라 의미가 달라진다.
        
        // final 변수
        // final 변수는 값을 한 번만 대입할 수 있고, 이후로는 수정할 수 없다.
        final int age = 20;
        final int num; 
        // age = 30; // 이미 값이 할당되어 불가능하다.
        num = 20; // 첫 번째 할당이라 가능하다.
        // num = 30; // 마찬가지로 이미 할당되면 불가능하다.
        System.out.println(age);
        System.out.println(num);

        // final과 상수
        // final 변수여도 인스턴스마다 다른 값이 들어있을 수 있기 때문에,
        // 보통 이것을 전통적인 의미의 "상수"라고 부르지 않는다.
        System.out.println(PI);
        System.out.println(MAX_SIZE);
        System.out.println(TAX_RATE);
        // 자바에서 완벽한 의미의 상수를 만들 때는 보통 static final을 사용하고, 관례적으로
        // 변수 이름으로 대문자를 사용한다.
        // 이렇게 하면 클래스 전체에서 하나의 값을 공유받을 수 있고, 인스턴스에 따라 값이
        // 변하지 않기 때문이다.
        
        // final 메서드
        // final 메서드는 자식 클래스가 오버라이딩 할 수 없다.
        // 보통 메서드의 동작을 자식이 함부로 변경하면 안되는 경우 사용한다.
        Parent parent = new Parent();
        Child child = new Child();
        parent.hello();
        child.hello();

        // final 클래스
        // final 클래스는 상속할 수 없다.
        // 대표적으로 String은 final 클래스이기 때문에 String을 상속하는 것은 불가능하다.
        Person person = new Person("철수");
        Student student = new Student();
        person.introduce();
        // student.introduce();

        // final 참조 변수의 함정
        // final 참조 변수와 객체의 final 필드를 햇갈려서는 안된다.
        final Parent parent2 = new Parent();
        // 이걸 보고 Person 객체의 필드 변경이 불가능하다고 생각하면 안된다.
        // parent2 = new Parent(); 
        // 메모리 주소를 들고있는 parent2가 final이기 때문에 다른 객체를 가리킬 수없다.
        parent2.num = 5; // Parent의 num 자체는 final이 아니기에 변경 가능하다.

        // 불변 객체란 무엇인가?
        // 객체가 생성된 이후 객체의 상태를 변경할 수 없는 객체이다.
        // 지금까지 만들었던 모든 객체들은 상태를 바꿀 수 있는 가변 객체(Mutable Object)였다.
        // Animal 객체의 name과 age 필드는 final이기 때문에 생성자로 값을 넣은 이후 다시 바꿀 수 없다.
        Animal animal = new Animal("고양이", 12);
        // 하지만 모든 필드가 final이라고 모두 "불변 객체"인 것은 아니다.
        // 대표적으로 필드에 참조 변수가 있을 때 이런 일이 발생한다.
        animal.getOwner().introduce();
        animal.getOwner().name = "철수"; // Animal 객체의 상태가 변경됐다.
        animal.getOwner().introduce();
        // final은 참조 변수가 가리키는 객체를 바꿀 수 없게 할 뿐이지, 변경 가능한 객체를 참조하는 경우
        // 그 객체의 상태까지 보호하지는 않기에 이런 일이 발생할 수 있다.
        // 즉, 불변 객체를 만들때는 객체의 상태 자체가 외부에서 변경되지 않도록 final 자체와 구분하여
        // 생각해야 한다.

        // String: 대표적인 불변 객체
        // 예를 들어 다음과 같은 코드를 보면, String 객체의 내용이 변경된 것처럼 보인다.
        String str = "Hello";
        str = str + " World";
        // 하지만 이것은 개념적으로 str의 내용이 변경된 것이 아니라, str이 "Hello World"를 담은 새로운 
        // 객체를 참조하게 되었다고 봐야한다.
        String a = "Hello";
        String b = a;   // b는 a와 같은 객체를 가리킨다.
        a = "World";
        // a의 내용 자체가 변경되어 아직도 둘이 같은 객체를 가리킨다면, b와 a의 출력이 똑같아야 한다.
        System.out.println(a);
        System.out.println(b);
        // 하지만 a는 이제 새로운 객체를 가리키고 있기 때문에 결과가 다르게 나온다.
        // 그래서 String은 다른 객체와 달리, 일반 타입(int, double 등...)의 변수를 쓰는 것처럼 
        // 직관적으로 다룰 수 있다.
        // 이러한 이유로 참조 변수의 변경과 객체 상태의 변경은 확실하게 구분해야 한다.

        // 불변 객체가 서버 개발에서 왜 중요한가?
        // 1. 객체 상태를 믿을 수 있다.
        // - 바뀌어서는 안되는 객체인 경우 걱정을 하지 않아도 된다.
        // 2. 여러 곳에서 공유하기 안전하다.
        // - A와 B가 한 객체를 공유할 때, A가 객체를 변경하여 B가 곤란해질 일이 없다.
        // 3. 멀티스레드 환경에서 유리하다.
        // - 여러 스레드가 같은 객체를 사용해도 상태가 변경되지 않아 동기화 문제를 줄일 수 있다.

        // final 변수 -> 변수에 다시 대입 불가
        // final 메서드 -> 오버라이딩 불가
        // final 클래스 -> 상속 불가
        // 불변 객체 -> 생성 후 객체의 상태 변경 불가
        // 가장 중요한 구분: final 필드 != 불변 객체

        // Money 만들어보기
        Money money = new Money(10000, "KRW");
        System.out.println(money.getAmount());
        System.out.println(money.getCurrency());
        System.out.println(money);
        // 금액을 20000으로 바꾸고 싶다 -> 방법은 두 가지.
        // 1. 그냥 애초에 만들때부터 값을 그렇게 넣는다.
        // 2. 20000원짜리 객체를 새로 만들고 money가 새로운 객체를 참조하게 한다.
    }
}
