package apple;

import apple.model.Apple;
import apple.model.appleformatter.AppleFancyFormatter;
import apple.model.appleformatter.AppleFormatter;
import apple.model.appleformatter.AppleSimpleFormatter;
import apple.model.applepredicate.AppleColorPredicate;
import apple.model.applepredicate.ApplePredicate;
import apple.model.applepredicate.AppleWeightPredicate;
import apple.model.applepredicate.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.Color.*;

public class AppleFilterApplication {

    public static List<Apple> filteringApple(List<Apple> appleList, ApplePredicate p) {

        List<Apple> result = new ArrayList<>();

        for (Apple a : appleList) {
            if (p.test(a)) {
                result.add(a);
            }
        }
        return result;
    }

    public static void formattingApple(List<Apple> appleList, AppleFormatter f) {

        for (Apple a : appleList) {
            System.out.println(f.accept(a));
        }
    }

    /**
     *추상화 메소드
     */
    public static <T> List<T> filtering(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();

        for (T t : list) {
            if(p.test(t)){
                result.add(t);
            }
        }
        return result;
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
         *  메소드의 파라미터화  -> 동작 파라미터화
         */
        List<Apple> greenAppleList = filteringApple(appleList, new AppleColorPredicate());

        formattingApple(appleList, new AppleFancyFormatter());
        System.out.println("");
        formattingApple(appleList, new AppleSimpleFormatter());


        /**
         * 익명 클래스를 이용한 필터링
         */
        List<Apple> filterGreenAppleWithAnonymous = filteringApple(appleList, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return apple.getColor().equals(GREEN);
            }
        });
        formattingApple(filterGreenAppleWithAnonymous, new AppleFancyFormatter());

        /**
         * 람다식을 이용한 필터
         */
        List<Apple> filterGreenAppleWithRamda =
                appleList.stream()
                        .filter(a -> a.getColor().equals(GREEN))
                        .collect(Collectors.toList());
        formattingApple(filterGreenAppleWithRamda, new AppleFancyFormatter());


        /**
         * List 형식으로 추상화
         */
        List<Apple> filtering = filtering(appleList, a -> a.getColor().equals(GREEN));
        System.out.println("filter with abstracted");
        formattingApple(filtering, new AppleFancyFormatter());

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 22, 33, 44, 55);
        System.out.println("filter Int with abstracted");
        List<Integer> filteringNums = filtering(nums, a -> a > 5);
        for (int e : filteringNums) {
            System.out.println("e = " + e);

        }

        /**
         * 익명 클래스를 이용해 사과의 무게 순으로 정리
         */
        appleList.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        /**
         * 람다를 이용해 사과의 무게순으로 정리
         */

        appleList.sort(
                (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
        );

        appleList.sort(
                Comparator.comparing(Apple::getWeight)
        );

        /**
         * 익명 클래스 Runnable 로 코드 블록 실행하기
         */

        Thread at = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello ");
            }
        });

        /**
         * 람다 식으로 스레드 코드 구현
      현  */
        Thread lt = new Thread( () -> System.out.println(" = " ));

    }
}

