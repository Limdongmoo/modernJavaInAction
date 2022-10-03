package reducing;

import menu.Dish;
import menu.DishPractice;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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


    }
}
