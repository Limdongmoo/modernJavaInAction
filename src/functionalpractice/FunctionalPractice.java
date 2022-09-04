package functionalpractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionalPractice {

    public static <T> List<T> filtering(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();

        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
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
    }


}
