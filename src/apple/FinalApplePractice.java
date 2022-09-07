package apple;

import apple.model.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.awt.Color.*;
import static java.awt.Color.GREEN;

public class FinalApplePractice {

    public static double integrate(DoubleFunction<Double> f, double a, double b) {
        return (f.apply(a) + f.apply(b)) * (b - a) / 2.0;
    }

    public static void main(String[] args) {
        Apple apple  = new Apple(150, GREEN);
        Apple apple1 = new Apple(200, RED);
        Apple apple2 = new Apple(50, GREEN);
        Apple apple3 = new Apple(50, RED);
        Apple apple4 = new Apple(30, YELLOW);
        Apple apple5 = new Apple(250,GREEN);
        Apple apple6 = new Apple(550,RED);
        Apple apple7 = new Apple(10, PINK);
        Apple apple8 = new Apple(130,RED);
        Apple apple9 = new Apple(120,GREEN);

        List<Apple> appleList = Arrays.asList(apple, apple1, apple2, apple3, apple4, apple5, apple6, apple7, apple8, apple9);

        /**
         * Comparator<Apple> 을 한번 더 정의하는 대신 익명 클래스를 사용하는 방식
         */
        appleList.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        /**
         * 람다로 전달하는 대신 메소드 참조를 이용하여 Comparator<Apple> 을 직접 전달
         * Comparator<Apple> comparing = Comparator.comparing((Apple a) -> a.getWeight(); 이 가능하기 때문에
         * sort() 메스드의 파라미터 Comparator<T> 를 직접 전달할 수 있음
         */
        appleList.sort(Comparator.comparing(Apple::getWeight));

        /**
         * Comparator 연결
         */
//        appleList.sort(Comparator.comparing(Apple::getWeight)
//                .reversed()
//                .thenComparing(Apple::getCountry));


        /**
         * Predicate 연결
         * A or B and C -> ( A || B ) && C
         */
        Predicate<Apple> redApple = a -> a.getColor().equals(RED);
        Predicate<Apple> redOrHeavyAndKorea = redApple.or(a -> a.getWeight() > 150).and(a -> a.getCountry().equals("Korea"));

        /**
         * function 의 조합
         * 예시 중 h ( 파라미터로 주어진 함수를 나중에 실행하는 andThen() 메소드) 의 결과 -> y = 2(x+1)
         * 예시 중 i ( 파라미터로 주어진 함수를 먼저 실행하는 compose() 메소드) 의 결과 -> y = 2x+1
         */
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> 2 * x;
        Function<Integer, Integer> h = f.andThen(g);
        Function<Integer, Integer> i = f.compose(g);
        int x = 3;
        System.out.println("h.apply(x) = " + h.apply(x));

        /**
         * 선형 적분 integrate 만들기
         * f(x) a<=x<=b -> 1/2 * (f(a) + f(b) * (b-a) )
         */
        System.out.println("integrate = " + integrate(dx -> dx + 1, 0.5, 1.5));

    }
}

