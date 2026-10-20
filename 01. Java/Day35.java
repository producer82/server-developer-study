import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Day35 {
    public static void main(String[] args) {
        // 날짜와 시간 API 심화
        // 날짜, 시간 차이 계산과 포맷 변환을 알아보자
        
        // Period
        // 두 날짜 사이의 차이를 년, 월, 일 단위로 나타내는 클래스다.
        // 회원 가입일부터 현재 날짜까지 얼마나 지났는지 계산 등에 활용할 수 있다.
        LocalDate startDate = LocalDate.of(2025, 3, 15);
        LocalDate endDate = LocalDate.of(2026, 10, 18);
        // between(startDate, endDate)는 시작 날짜부터 종료 날짜까지의 날짜 차이를 계산한다.
        Period period = Period.between(startDate, endDate);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        // 전체 기간을 일수로 반환하지 않고, 연과 월을 제외하고 남은 일수를 반환한다.
        System.out.println(period.getDays());

        // ChronoUnit
        // 날짜 차이를 특정 단위를 계산할 수 있다.
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        long years = ChronoUnit.YEARS.between(startDate, endDate);
        System.out.println(days);
        System.out.println(months);
        System.out.println(years);
        // 전체 기간을 한 단위로 계산하기 때문에 Period와는 결과가 다르다.
        // 연,월,일의 차이가 필요하면 Period, 특정 단위의 차이가 필요하다면 ChronoUnit을 활용할 수 있다.

        // Duration
        // 두 시간 또는 날짜, 시간 사이의 간격을 초나 나노초 같은 시간 단위로 계산한다.
        LocalTime startTime = LocalTime.of(9, 30);
        LocalTime endTime = LocalTime.of(12, 0);
        Duration duration = Duration.between(startTime, endTime);
        // 차이를 시간으로 출력한다.
        System.out.println(duration.toHours());
        // 차이를 분으로 출력한다.
        System.out.println(duration.toMinutes());

        // DateTimeFormatter
        // 날짜를 원하는 문자열로 바꾼다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        System.out.println(startDate.format(formatter));
        // 자주 쓰이는 패턴은 다음과 같다.
        // yyyy: 연도, MM: 두 자리 월, dd: 두 자리 일
        // HH: 24시간 기준 시, mm: 분, ss: 초

        // parse()
        // 문자열을 날짜로 변환하기
        String text = "2025-03-15";
        // 기본 날짜 형식인 yyyy-MM-dd는 별도의 포맷터 없이도 파싱할 수 있다.
        LocalDate textToDate = LocalDate.parse(text);
        // 다른 형식은 포맷터를 지정해야 한다.
        String text2 = "2025년 03월 15일";
        LocalDate textToDate2 = LocalDate.parse(text2, formatter);
        System.out.println(textToDate);
        System.out.println(textToDate2);

        // 2024/1/10 ~ 2025/4/15까지의 년, 월, 일 차이를 각각 출력
        LocalDate date2 = LocalDate.of(2024, 1 ,10);
        LocalDate date3 = LocalDate.of(2025, 4, 15);
        Period period3 = Period.between(date2, date3);
        System.out.println(period3.getYears());
        System.out.println(period3.getMonths());
        System.out.println(period3.getDays());

        // 같은 두 날짜 사이의 전체 일수를 계산해 출력하기
        System.out.println(ChronoUnit.DAYS.between(date2, date3));

        // 09:20부터 13:50까지의 총 경과 시간(분)을 출력하기
        LocalTime time1 = LocalTime.of(9, 20);
        LocalTime time2 = LocalTime.of(13, 50);
        Duration duration2 = Duration.between(time1, time2);
        System.out.println(duration2.toMinutes());

        // 2025/3/15을 "2025년 03월 15일" 형식으로 출력하기
        System.out.println(startDate.format(formatter));

        
        // "2025/03/15" 문자열을 LocalDate로 변환하기
        String text3 = "2025/03/15";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        System.out.println(LocalDate.parse(text3, formatter2));

        // Q1. Period와 Duration의 차이는?
        // Period는 날짜 간격을 계산하고, Duration은 시간 간격을 계산한다.
        // Q2. Period의 getDays()와 ChronoUnit.DAYS.between()의 차이는?
        // Period는 연,월,일 차이를 각각 달력 기준으로 출력하고, 
        // ChronoUnit.DAYS.between()은 차이를 일 단위로 계산하여 출력한다.
        // Q3. format()과 parse()의 역할은 각각 무엇인가?
        // format은 LocalDate를 String으로 바꾸고,
        // parse는 String을 LocalDate로 바꾼다
    }
}
