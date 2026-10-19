import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Day34 {
    public static void main(String[] args) {
        // 날짜와 시간 API
        // 서버에서는 시간을 활용한 데이터를 자주 처리한다. 
        // 이것에 활용할 수 있는 자료형을 알아보자.

        // 날짜 API에는 기본적으로 세 가지 객체가 있다.
        // - LocalDate: 날짜만 표현한다. YYYY-MM-DD
        // - LocalTime: 시간만 표현한다. HH:MM:SS
        // - LocalDateTime: 날짜와 시간을 함께 표현한다. 
        // 시간대 정보는 포함하지 않는다. YYYY-MM-DDTHH:MM:SS
        // 현재 날짜를 가져온다.
        LocalDate today = LocalDate.now();
        // 지정한 날짜를 만든다.
        LocalDate birthday = LocalDate.of(2000, 5, 20);
        System.out.println(today);
        System.out.println(birthday);
        // 연도를 조회한다.
        System.out.println(birthday.getYear());
        // 월을 조회한다.
        System.out.println(birthday.getMonthValue());
        // 해당 월의 일을 조회한다.
        System.out.println(birthday.getDayOfMonth());

        // 날짜 더하기와 빼기
        LocalDate date = LocalDate.of(2026, 10, 18);
        // 7일을 더한다.
        System.out.println(date.plusDays(7));
        // 3일을 뺀다.
        System.out.println(date.minusDays(3));
        // 1달을 더한다.
        System.out.println(date.plusMonths(1));
        // 1년을 더한다.
        System.out.println(date.plusYears(1));
        // LocalDate는 불변 객체이므로, 연산 메서드를 호출해도 원래 날짜는 변하지 않고,
        // 계산된 새 날짜가 반환된다.
        System.out.println(date);
        // 따라서 계산 결과를 저장하기 위해서는 다음과 같이 작성해야 한다.
        date = date.plusDays(7);
        System.out.println(date);

        // LocalTime
        // 현재 시간을 가져온다.
        LocalTime time = LocalTime.now();
        // 지정된 시간을 만든다.
        LocalTime openingTime = LocalTime.of(9, 30);
        LocalTime closingTime = LocalTime.of(18, 0);
        System.out.println(openingTime);
        System.out.println(closingTime);
        System.out.println(time);
        // 2시간을 더한다.
        System.out.println(openingTime.plusHours(2));
        // 30분을 뺀다.
        System.out.println(closingTime.minusMinutes(30));

        // LocalDateTime
        // 현재 날짜와 시간을 가져온다.
        LocalDateTime dateAndTime = LocalDateTime.now();
        // 지정된 날짜와 시간을 만든다.
        LocalDateTime orderTime = LocalDateTime.of(2026, 10, 18, 14, 30);
        System.out.println(dateAndTime);
        System.out.println(orderTime);
        // 1일을 더한다.
        System.out.println(orderTime.plusDays(1));
        // 3시간을 더한다.
        System.out.println(orderTime.plusHours(3));
        // 주의할 점: LocalDateTime에는 시간대 정보가 없다.
        // 따라서, 여러 국가에서 사용하는 서비스에서는 Instant나 ZonedDateTime 같은 타입을 사용해야 한다.

        // 날짜 비교하기
        LocalDate date1 = LocalDate.of(2026, 10, 18);
        LocalDate date2 = LocalDate.of(2026, 10, 25);
        // date1이 date2보다 이전인가?
        System.out.println(date1.isBefore(date2));
        // date1이 date2보다 이후인가?
        System.out.println(date1.isAfter(date2));
        // date1과 date2이 같은 날짜인가?
        System.out.println(date1.isEqual(date2));

        // 날짜 만들기: 2020/3/15을 LocalDate로 만들고 연도, 월, 일을 각각 출력하기
        LocalDate date3 = LocalDate.of(2025, 3, 15);
        System.out.println(date3.getYear());
        System.out.println(date3.getMonthValue());
        System.out.println(date3.getDayOfMonth());

        // 날짜 계산: 2025/3/15에서 10일 뒤와 2개월 뒤 날짜를 각각 출력하기
        System.out.println(date3.plusDays(10));
        System.out.println(date3.plusMonths(2));

        // 시간 계산: 09:30을 LocalTime으로 만들고 2시간 45분 뒤를 출력하기
        LocalTime time2 = LocalTime.of(9, 30);
        System.out.println(time2.plusHours(2).plusMinutes(45));

        // 날짜 비교: 2025/3/15과 2025/3/25를 만들고, 첫 번째 날짜가 두 번째 날짜보다 이전인지 출력하기
        LocalDate date4 = LocalDate.of(2025, 3, 25);
        System.out.println(date3.isBefore(date4));

        // 불변 객체 확인: 날짜에 plusDays(5)를 호출한 뒤 반환값을 저장하지 않았을 때,
        // 원래 날짜가 유지되는지 출력으로 확인하기
        date4.plusDays(5);
        System.out.println(date4);

        // Q1. LocalDate와 LocalDateTime의 차이는?
        // A1. LocalDate는 날짜를 표현하고, LocalDateTime은 날짜와 시간을 표현한다.
        // Q2. date.plusDays(5)를 실행했는데 원래 날짜가 바뀌지 않는 이유는?
        // A2. LocalDate는 불변 객체이기 때문에 메서드를 호출해도 값이 변하지 않는다.
        // Q3. 날짜를 비교할 때 == 대신 isBefore()나 isEqual() 같은 메서드를 사용하는 이유는?
        // A3. ==로 비교하면 객체 자체의 동일성을 비교하게 된다.        
    }
}
