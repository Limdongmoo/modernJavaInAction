package menu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DishPractice {
    public static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Type.MEAT),
            new Dish("beef", false, 700, Type.MEAT),
            new Dish("chicken", false, 400, Type.MEAT),
            new Dish("french fries", true, 530, Type.OTHER),
            new Dish("rice", true, 350, Type.OTHER),
            new Dish("season fruit", true, 120, Type.OTHER),
            new Dish("pizza", true, 550, Type.OTHER),
            new Dish("prawns", false, 300, Type.FISH),
            new Dish("salmon", false, 450, Type.FISH));

    public static void main(String[] args) {
        /**
         * 메뉴 stream 에서 칼로리 300 이상인 음식의 이름을 3개까지 추출하는 예제
         * 데이터 소스 : menu
         * 연속된 요소 : Dish
         * 데이터 처리 연산 : filter -> map -> limit -> collect 의 파이프 라인 형성
         */

        List<String> collect = menu.stream()
                .filter(a -> a.getCalories() >= 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("collect = " + collect);

        /**
         * 프로그램 실행 순서를 알기 위한 예제 - 위의 과정을 출력하면서 진행
         */
        menu.stream()
                .filter(a ->
                {
                    System.out.println("a = " + a.getName());
                    return a.getCalories() > 300;
                })
                .map(a -> {
                    System.out.println("a.getName() = " + a.getName());
                    return a.getName();
                })
                .limit(3)
                .collect(Collectors.toList());

        /**
         * 메뉴 stream 에서 칼로리 300 이상인 음식의 이름을 3개까지 추출, 중복은 제외하며 추출된 요소의 총 개수를 반환하는 예제
         * 데이터 소스 : menu
         * 연속된 요소 : Dish
         * 데이터 처리 연산 (중간연산) : filter -> distinct -> limit
         * 최종연산 : 총 개수를 long type 으로 반환하는 count()
         */
        long count = menu.stream()
                .filter(a -> a.getCalories() > 300)
                .distinct()
                .limit(3)
                .count();

        System.out.println("count = " + count);

    }
}
