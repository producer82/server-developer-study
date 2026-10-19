import java.time.LocalDate;
import java.time.Period;
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
    }
}
