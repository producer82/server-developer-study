class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class PersonOverride {
    String name;
    int age;

    PersonOverride(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { // 같은 객체면 애초에 true
            return true;
        }
        if (obj == null) { // null이면 애초에 false
            return false;
        }
        if (getClass() != obj.getClass()) { // 객체 타입이 다르면 애초에 false
            return false;
        }
        PersonOverride other = (PersonOverride) obj;
        return name.equals(other.name);
    }

    @Override 
    public int hashCode() {
        // equals에서는 이름이 같으면 동일 객체라고 정의했다
        // 여기서도 이름이 같으면 동일 객체라고 정의한다.
        return name.hashCode();
    }
}

class Student {
    String name;
    int studentId;

    Student(String name, int studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    @Override 
    public String toString() {
        return "Student{name='" + name + "', studnetId=" + studentId + "}";
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        Student s = (Student) obj;
        return studentId == s.studentId;
    }

    @Override 
    public int hashCode() {
        return studentId;
    }
}

public class Day13 {
    public static void main(String[] args) {
        // Ojbect와 객체

        // 모든 Java 클래스의 부모는 누구인가?
        // Java에서 클래스를 만들 때, 명시적으로 다른 클래스를 상속하지 않으면 자동으로
        // "Object"를 상속한다.

        // 즉, class Animal { } 은 사실 class Animal extends Object { } 와 같다.
        // 그래서 Animal 클래스로 예시를 들어보면, 사실 진짜 상속 관계는 이렇게 된다.
        // Object -> Animal -> Dog, Cat

        // 그래서 모든 일반적인 Java 객체는 Object에 정의된 메서드를 사용할 수 있다.
        // toString(), equals(), hashCode(), getClass() 등이다.

        // toString(): 객체를 사람이 이해하기 좋은 문자열로 표현하는 메서드
        Person p = new Person("철수", 20);
        // 이렇게 출력하면 "철수, 20"이 나올까?
        System.out.println(p.toString());
        // Object는 toString()의 기본형을 제공하지만, 지금의 출력은 내가 원하는 것과 다르다.
        // 그래서 사람이 이해하기 좋은 문자열을 반환하도록 오버라이딩한다.
        PersonOverride po = new PersonOverride("영희", 18);
        System.out.println(po.toString());
        // System.out.println은 객체를 출력할 때 내부적으로 객체의 toString() 결과를 사용한다.
        // 즉, 아래 코드도 동일한 출력을 만들어낸다.
        System.out.println(po);
        
        // ==와 equals()
        // 둘 다 같은가를 비교하는 역할인데, 왜 굳이 나누어져 있는가?: 역할이 다르다.
        // == : 두 참조가 같은 객체를 가리키는가?
        // equals() : 두 객체는 논리적으로 같은 것인가?
        // 아래 두 객체의 내용은 똑같다.
        Person p1 = new Person("철수", 20);
        Person p2 = new Person("철수", 20);
        // 하지만 객체 자체는 서로 다르다.
        System.out.println(p1 == p2);
        // ==는 두 참조가 같은 객체를 가리키는지 비교하기 때문에 false가 출력된다.
        // (메모리 상의 동일 위치를 가리키는지 비교)
        // equals()를 사용하면 결과가 다를까?
        System.out.println(p1.equals(p2));
        // Object에서 물려받은 기본 equals()는 this == obj와 같은 객체의 동일성을 비교한다.
        // 즉, 지금 상태에서는 p1 == p2와 다름이 없다.
        
        // 왜 equals()를 오버라이딩 해야하는가?
        // equals()의 역할은 논리적인 비교로, "내용이 같다면 같은 사람으로 취급하자"라는 
        // 논리적인 의도에 맞게 eqauls()를 오버라이딩한다.
        PersonOverride po1 = new PersonOverride("영희", 18);
        PersonOverride po2 = new PersonOverride("영희", 18);
        // 이제 equals()는 true를 출력한다.
        System.out.println(po1 == po2);
        System.out.println(po1.equals(po2));
        // 결국 equals()는 단순한 기능을 넘어, 객체의 동일성에 대한 기준을 클래스가 정의하는 것이다.
        // 그렇기 때문에, 클래스마다 "같다"의 기준이 다를 수 있다.

        // hashCode()를 equals()와 함께 오버라이딩해야 하는 이유
        // hashCode(): 해시 기반 자료구조에서 객체를 구분하기 위한 값 -> 이게 왜 지금?
        // Java에는 equals()에 관련한 아주 중요한 규칙이 하나 나온다.
        // equals()가 true인 두 객체는 반드시 같은 hashCode()를 가져야 한다.
        // equals()는 true인데 hashCode()가 서로 다르면, 해시 자료구조가 hashCode()와 equals()로 
        // 객체가 이미 있는지를 판단하는 과정에서 내부적으로 모순이 생긴다.
        // 따라서, hashCode 또한 equals와 같은 비교 기준을 고려하여 오버라이딩 한다.
        System.out.println(p1.equals(p2));
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
        System.out.println(p1.hashCode() == p2.hashCode());
        // 하지만 역은 성립하지 않는데, hashCode()가 같다고 equals()가 true인 것은 아니다.
        // 서로 다른 객체가 우연히 같은 hashCode를 같는 해시 충돌이 발생할 수 있기 때문이다.
        System.out.println("================================");

        // 학생 클래스 만들어보기
        Student s1 = new Student("철수", 1001);
        Student s2 = new Student("영희", 1001);
        Student s3 = new Student("민수", 1002);

        System.out.println(s1);
        System.out.println(s1 == s2); // 다른 메모리를 가리키고 있기에 false
        System.out.println(s1.equals(s2)); // 클래스가 정한 논리적으로 같은 객체 가리키고 있기에 true
        System.out.println(s1.equals(s3));
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
    }
}
