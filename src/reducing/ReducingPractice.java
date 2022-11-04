package reducing;

import menu.Dish;
import menu.DishPractice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReducingPractice {
    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        /**
         * 모든 요소의 합을 연산하는 리듀싱 연산
         * 인수 : 초기값 0, 두 요소를 조합해 새로운 값을 만드는 BinaryOperator<T> -> { (a,b)->a+b }
         */
        Integer reduce = integerList.stream().reduce(0, (a, b) -> a + b);
        System.out.println("reduce = " + reduce);

        /**
         * 메소드 참조를 이용하여 더 간단하게 표현
         */
        Integer reduce1 = integerList.stream().reduce(0, Integer::sum);
        System.out.println("reduce1 = " + reduce1);

        /**
         * map 과 reduce 를 이용하여 menu 의 총 개수 구하기 예제
         */
        List<Dish> menu = DishPractice.menu;
        Integer reduce2 = menu.stream()
                .map(dish -> 1)
                .reduce(0, Integer::sum);
        System.out.println("reduce2 = " + reduce2);

        /**
         * 최대값과 최소값 구하는 예제
         * 초기값이 설정되지 않으면 reduce 연산의 주체인 소스가 비어있을 경우를 대비해 Optional<T> 로 반환한다.
         */
        Optional<Integer> max = integerList.stream()
                .reduce(Integer::max);
        Optional<Integer> min = integerList.stream()
                .reduce(Integer::min);

        System.out.println("max.get() = " + max.get());
        System.out.println("min.get() = " + min.get());

        /**
         * 거래자와 트랜잭션
         */
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        /**
         * 2011 년에 일어난 모든 트랜잭션을 찾아 오름차순으로 정리
         * 소스 : transactions
         * 중간연산 : filter -> sorted
         * 최종연산 : collect(Collectors.toList())
         */
        List<Transaction> collect = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        /**
         * 거래자가 근무하는 모든 도시를 중복없이 나열하시오
         * 소스 : List<Trader>
         * 중간연산 : map -> distinct
         * 최종연산 : collect(Collectors.toList())
         */
        List<Trader> traders = Arrays.asList(raoul, mario, alan, brian);
        traders.stream()
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());

        /**
         * 케임브릿지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오
         * 소스 : traders
         * 중간연산 : filter -> sorted
         * 최종연산 : collect(Collectors.toList())
         */
        traders.stream()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        /**
         * 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오
         * 소스 : traders
         * 중간 연산 : sorted
         * 최종 연산 : collect(Collectors.toList))
         */
        traders.stream()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        //수정
        String reduce3 = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (s1, s2) -> s1 + s2);
        System.out.println("reduce3 = " + reduce3);


        /**
         * 밀라노에 거래자가 있는가?
         * 소스 : traders
         * 중간연산 : filter
         * 최종연산 : findAny
         */
        traders.stream()
                .filter(trader -> trader.getCity().equals("Milan"))
                .findAny()
                .ifPresent(System.out::println);

        // 다른 방법
        boolean milan = traders.stream()
                .anyMatch(trader -> trader.getCity().equals("Milan"));
        if (milan) {
            System.out.println("밀란에 거래자가 있습니다.");
        }
        /**
         * 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오
         * 소스 : traders,transactions
         * 중간 연산 : filter -> flatMap
         * 최종연산 : reduce
         */
        Integer cambridge = traders.stream()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .flatMap(trader ->
                        transactions.stream()
                                .filter(transaction -> transaction.getTrader().equals(trader))
                                .map(Transaction::getValue))
                .reduce(0, Integer::sum);
        // 수정
        Integer cambridge1 = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(0, Integer::sum);
        System.out.println("cambridge1 = " + cambridge1);

        /**
         * 전체 트랜잭션 중 최대값과 최소값은 얼마인가 
         * 소스 : transactions
         * 중간연산 : map
         * 최종연산 : reduce
         */
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        Optional<Integer> minValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);

        System.out.println("maxValue = " + maxValue.get());
        System.out.println("minValue.get() = " + minValue.get());


        /*
        아래의 reduce 연산의 경우 박싱비용이 들어간다
        .map 에서는 Stream<Integer> 를 반환해주기 때문에 int 형에 저장하기 위해서는
        Integer 를 언박싱 해서 sum 연산을 수행해야 한다.
         */
        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);

        System.out.println("calories = " + calories);

        /*
        "기본형 특화 스트림" 을 사용하면 박싱 비용을 피할 수 있다.
        IntStream, DoubleStream, LongStream 을 제공한다.
        특화 스트림은 단지 박싱 과정에서 일어나는 효율성에만 관련있으며 추가 기능을 제공하지는 않는다.

        특화 스트림으로 변환시에는 mapToInt, mapToDouble, mapToLong 메서드를 가장 많이 사용한다.
        map 과 동일한 기능을 수행하지만
        기존 map 은 Stream<T> 를 반환한다면
        위의 메서드들은 특화 스트림을 반환한다.
         */

        int sum1 = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        double sum = menu.stream()
                .mapToDouble(Dish::getCalories)
                .sum();

        //언박싱된 IntStream -> 다시 박싱하기
        Stream<Integer> boxed = menu.stream()
                .mapToInt(Dish::getCalories)        // 언박싱, return -> IntStream
                .boxed();                           // 다시 박싱하기 return -> Stream<T>


        /*
        Optional 은 값을 반환하기 이전에 값의 존져여부를 가리킬 수 있는 컨테이너 클래스
        OptionalInt, OptionalDouble, OptionalLong 의 세가지 기본형 특화 스트림 제공

        기존의 IntStream.sum 에서는 값이 존재하지 않는 경우 0을 return 하기 때문에 int 자료형을 사용한다.
        하지만 max, min 등 의 메소드에서 값이 존재하지 않는 경우를 대비해 OptionalInt 로 값을 반환하며
        OptionalDouble, OptionalLong 의 경우에도 각 자료형에 맞는 Optional 로 반환해준다.
         */
        OptionalInt max1 = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        OptionalDouble max2 = menu.stream()
                .mapToDouble(Dish::getCalories)
                .max();

        OptionalLong min1 = menu.stream()
                .mapToLong(Dish::getCalories)
                .min();

    }
}
