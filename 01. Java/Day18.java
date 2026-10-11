class Animal {
    void sound() {
        System.out.println("동물 소리");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("멍멍");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("야옹");
    }
}

class Rectangle {
    int width;
    int height;

    void setHeight(int height) {
        this.height = height;
    }

    void setWidth(int width) {
        this.width = width;
    }

    int getArea() {
        return width * height;
    }
}

class Square extends Rectangle {
    @Override 
    void setWidth(int width) {
        this.width = width;
        this.height = width;
    }

    @Override
    void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}

interface Shape {
    int getArea();
}

class NewRectangle implements Shape {
    int width;
    int height;
    
    NewRectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getArea() {
        return width * height;
    }
}

class NewSquare implements Shape {
    int side;

    NewSquare(int side) {
        this.side = side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}

class Bird {
    void introduce() {
        System.out.println("새입니다.");
    }
}

interface Flyable {
    void fly();
}

class Sparrow extends Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("날아갑니다.");
    }    

    @Override
    void introduce() {
        System.out.println("참새입니다.");
    }
}

class Penguin extends Bird {
    @Override
    void introduce() {
        System.out.println("펭귄입니다.");
    }
}



public class Day18 {
    public static void main(String[] args) {
        // LSP (Liskov Substitution Principle, 리스코프 치환 원칙)
        // 자식 클래스는 부모 클래스를 대신해서 사용해도 프로그램이 의도대로 동작해야 한다.
        // 이전에 배운 다형성을 떠올려 보자.
        Animal animal1 = new Dog();
        Animal animal2 = new Cat();
        // Animal을 기대하는 코드에 Dog나 Cat을 넣어도 정상적으로 작동한다.
        animal1.sound();
        animal2.sound();

        // 그런데, 상속을 사용했는데도 문제가 생길 수 있다.
        // 수학적으로 정사각형은 직사각형의 한 종류다.
        // 그렇다고 해서 직사각형의 자식 클래스로 정사각형을 만들어도 될까?
        Rectangle rectangle1 = new Rectangle();
        Rectangle rectangle2 = new Square();
        // 메서드 설계 의도: 사각형의 너비를 10, 높이를 5로 설정하고 넓이를 구한다.
        // 예상한 동작: 50이 나와야 한다.
        resizeRectangle(rectangle1);
        resizeRectangle(rectangle2); // 25가 나온다.
        // 핵심은?: 부모 객체 자리에 자식 객체를 넣었더니 프로그램이 의도대로 
        // 동작하지 않았다.

        // 그럼 LSP를 지키려면?
        // 정사각형과 직사각형을 다른 객체로 만들고, 공통 기능을 추상화 해본다.
        Shape shape1 = new NewRectangle(10, 5);
        Shape shape2 = new NewSquare(5);
        // 여전히 두 도형은 다형성을 사용할 수 있다.
        System.out.println(shape1.getArea());
        System.out.println(shape2.getArea());

        // 모든 상속을 인터페이스로 바꿔야 한다?: 절대 아니다.
        // 다만, 부모가 하던 일을 자식이 대신 해도 문제가 발생하지 않는지가 중요하다.

        // LSP는 상속 관계 뿐만 아니라, 상위 타입과 하위 타입 관계가 존재하는
        // 인터페이스 관계에서도 적용될 수 있다.
        // 예시) Fly 인터페이스를 Lion이 implements -> 근데 Lion은 애초에 날지 못한다 ->
        // Fly 타입을 사용하는 메서드에 Lion을 넣었다고 해서 의도대로 동작하지 않을 것
        // 결국... LSP 원칙은 어떻게 보면: "다형성을 무지성으로 발사하지 말자"
        
        // SRP, OCP와 연결
        // SRP: 이 클래스는 너무 많은 변경 이유를 가지고 있지 않은가?
        // ex) PaymentService가 결제도 하는데 결제 완료 이메일도 발송한다
        // OCP: 새로운 기능을 추가할 때 멀쩡한 코드를 계속 수정해야 하는가?
        // ex) 새로운 결제 수단이 추가될 때마다 검증된 로직이 계속 변경된다.
        // LSP: 상위 타입 자리에 하위 타입을 넣어도 의도대로 동작하는가?
        // ex) 정사각형이 직사각형을 상속받아서 크기를 지정할 때 문제가 생긴다.

        // Q. 다음 코드를 살펴보고 LSP를 위반할 수 있는 이유를 찾아보자
        //
        // class Bird {
        //     void fly() {
        //         System.out.println("날아갑니다.");
        //     }
        // }
        // class Sparrow extends Bird {
        //     @Override
        //     void fly() {
        //         System.out.println("참새가 날아갑니다.");
        //     }
        // }
        // class Penguin extends Bird {
        //     @Override
        //     void fly() {
        //         throw new UnsupportedOperationException("펭귄은 날 수 없습니다.");
        //     }
        // }
        //
        // A. Bird로 Sparrow와 Penguin을 업캐스팅해서 사용하다가 fly가 나오면
        // 프로그램이 터질수도 있다.
        //
        // Q. 그러면 모든 새가 fly()를 반드시 구현하도록 하는 대신, 어떻게 해야할까?
        // A. 모든 새가 날 수 있는건 아니다. 그러니 Bird Class와 Fly Interface를 구분해서 
        // 구현하는게 좋다. 그렇게 하고서 Sparrow와 Penguin이 Bird를 상속받게 하되, 
        // Fly는 Sparrow에게만 준다.
        //
        // 부모와 자식 간에는 모두가 가진 공통적인 요소만 물려받을 수 있도록 설계하고,
        // 차이가 발생할 수 있는 요소는 interface로 뺄 수 없을지 고려해봐야 한다.
        // 
        // Q. Bird, Sparrow, Penguin, 그리고 필요하다면 Flyable 인터페이스를 구현해보자.
        // 1. 참새는 날 수 있어야 한다.
        // 2. 펭귄은 새이지만 날 수 없어야 한다.
        // 3. 날 수 있는 객체만 fly()를 호출할 수 있도록 해야한다.
        Bird sparrow = new Sparrow();
        Bird penguine = new Penguin();
        // 이제 펭귄은 날 수 없다.
        sparrow.introduce();
        ((Sparrow)sparrow).fly();
        penguine.introduce();
        //
        // Q. 인터페이스를 구현하기만 하면 LSP를 무조건 만족할까?
        // 핵심은 "상위 타입 자리에 하위 타입을 넣어도 의도대로 동작하는가?"이다.
        // 인터페이스를 implements 받았어도 이 핵심을 어겨버리면 소용이 없다.
    }

    static void resizeRectangle(Rectangle rectangle) {
        rectangle.setWidth(10);
        rectangle.setHeight(5);

        System.out.println(rectangle.getArea());
    }
}

