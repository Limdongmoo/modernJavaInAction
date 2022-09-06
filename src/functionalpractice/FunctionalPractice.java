package functionalpractice;

import apple.model.Apple;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FunctionalPractice {

    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    public static <T> List<T> filtering(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();

        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static <T, R> List<R> map(List<T> weights, Function<T, R> f) {
        List<R> list = new ArrayList<>();
        for (T t : weights) {
            list.add(f.apply(t));
        }
        return list;
    }

    public static <T, R> R mapping(T t,Function<T, R> f) {
        return f.apply(t);
    }

    public static void main(String[] args) {
        List<String> stringArray = Arrays.asList(
                "aaa", "bbb", "", "ddd", "eee"
        );

        /**
         * filtering 의 파라미터 Predicate<T>를 메소드 참조로 전달
         */
        List<String> emptyString = filtering(stringArray, String::isEmpty);
        for (String s : emptyString) {
            System.out.println("s = " + s);
        }

        /**
         * filtering 의 파라미터 Predicate<T>를 람다로 전달
         */
        List<String> notEmptyString = filtering(stringArray, (String s) -> !s.isEmpty());
        for (String s : notEmptyString) {
            System.out.println("s = " + s);
        }

        /**
         * 람다로 forEach 의 파라미터 Consumer<T> 를 직접 전달
         */
        stringArray.forEach(
                (String s) -> System.out.println(s)
        );

        /**
         * 메소드 참조로 forEach 의 파라미터 Consumer<T> 를 직접 전달
         */
        stringArray.forEach(
                System.out::println
        );

        /**
         *람다 표현식을 이용하여 mapping 의 파라미터 직접 전달
         */
        for (String st : stringArray) {
            int mapping = mapping(st, (String s) -> s.length());
            System.out.println("mapping = " + mapping);
        }
        /**
         *메소드 참조를 이용하여 map의 파라미터 Function<T,R> 전달
         */
        List<Integer> collect = stringArray.stream()
                .map(String::length).collect(Collectors.toList());

        for (int e : collect) {
            System.out.println("e = " + e);
        }

        /**
         * forEach 의 Consumer<T> 파라미터를 람다 T -> () 를 이용하여 직접 전달
         */
        forEach(stringArray,a-> System.out.println("a = " + a));

        stringArray
                .forEach(a-> System.out.println("a = " + a));

        /**
         * 메소드 참조를 이용하여 Apple 의 생성자 호출
         */
        Supplier<Apple> appleSupplier = Apple::new;
        Apple apple = appleSupplier.get();

        /**
         * 람다 표현식을 이용하여 default 생성자를 가진 Apple 객체 생성
         */
        Supplier<Apple> apple1 = () -> new Apple();
        Apple apple2 = apple1.get();

        /**
         *Function<T,R> 의 R apply(T t) 메소드에 무게를 인수로 호출하여 새로운 Apple 객체를 만들 수 있다.
         * 이때 Function 의 제네릭으로 형식을 추론한다.
         */
        Function<Integer, Apple> fApple = (weight) -> new Apple();
        fApple.apply(10);
        Function<Color, Apple> fAppleC = (Color) -> new Apple();
        fAppleC.apply(Color.RED);
        /**
         * 위의 방식을 메소드 참조로 변환, map 메소드의 파라미터를 List<T> , Function<T,R> 로 정의하고 메소드 참조를 이용하여
         * Function<T,R> 을 전달
         */
        List<Integer> weights = Arrays.asList(1, 2, 3, 4, 5, 6, 11, 22, 33);
        map(weights, Apple::new);


    }


}
