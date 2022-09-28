package menu;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsingStream {
    public static void main(String[] args) {
        List<Dish> list = DishPractice.menu;

        /**
         * filtering 연습
         * 소스 : list
         * 요소 : Dish
         * 중간 연산 : filter ( 프레디케이트 함수를 파라미터로 받아서 프레디케이트와 일치하는 요소를 포함하는 Stream 을 반환하는 연산 )
         * 최종 연산 : collect
         */
        List<Dish> collect = list.stream().filter(
                Dish::isVegetarian
        ).collect(Collectors.toList());

        /**
         * filtering 연습
         * 소스 : intList
         * 요소 : Integer
         * 중간 연산 : filter -> distinct() (고유 요소만 필터링 할 수 있도록 하는 연산)
         * 최종 연산 : forEach
         */
        List<Integer> intList = Arrays.asList(1, 2, 7, 5, 9, 10, 2, 7, 6);
        intList.stream().filter(a -> a % 2 == 0)
                .distinct()
                .forEach(System.out::println);


        /**
         * Stream 슬라이싱
         * 소스 : list
         * 요소 : Dish
         * 중간 연산 : takeWhile (Predicate<T> 를 파라미터로 갖는 연산으로 최초 false 가 되는 지점 직전가지의 값들만 추출한다.)
         * 최종 연산 : forEach
         */
        list.stream()
                .takeWhile(a -> a.getCalories() > 320)
                .forEach(System.out::println);

        /**
         * Stream 슬라이싱
         * 소스 : list
         * 요소 : Dish
         * 중간 연산 : dropWhile (Predicate<T> 를 파라미터로 갖는 연산으로 최초 false 가 되는 지점 이후부터의 값들만 추출한다,
         * 앞의 takeWhile 과 정반대의 연산을 수행한다.)
         * 최종 연산 : forEach
         */
        list.stream()
                .dropWhile(a -> a.getCalories() > 320)
                .forEach(System.out::println);

        /** Stream 축소
         * 소스 : list
         * 요소 : Dish
         * 중간 연산 : filter -> limit
         * 최종 연산 : collect
         */
        list.stream()
                .filter(a -> a.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());

        /**
         * Stream 요소 건너뛰기
         * 소스 : list
         * 요소 : Dish
         * 중간 연산 : filter -> skip
         * 최종연산 : forEach
         */
        System.out.println("요소 건너뛰기");
        list.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .forEach(System.out::println);

        /**
         * 예제 : 처음 등장하는 두 고기 요리를 필터링 하시오
         * 소스 : list
         * 요소 : Dish
         * 중간 연선 : filter -> limit(2)
         * 최종 연산 : forEach
         */
        System.out.println("에제");
        list.stream()
                .filter(d->d.getType().equals(Type.MEAT))
                .limit(2)
                .forEach(System.out::println);

        /**
         * mapping
         * : 기존의 값을 고치는 것이 아닌, 새로운 버전을 만든다는 개념
         * 함수를 인수로 받는 연산으로 각 요소에 함수 적용 결과 새로운 요소로 변환된다.
         * 요소들의 요리명을 추출하는 연산
         * 소스 : list
         * 요소 : Dish
         * 중간연선 : map
         * 최종연산 : collect
         */
        List<String> collect1 = list.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
        /**
         * 추출한 요리명의 글자 수를 추출하는 연산
         * 소스 : collect1
         * 요소 : String
         * 중간 연선 : map
         * 최종 연산 : collect
         */
        List<Integer> collect2 = collect1.stream()
                .map(String::length)
                .collect(Collectors.toList());

        /**
         * 위의 두 과정 합치기
         */
        List<Integer> collect3 = list.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());

        /**
         * 문자열 배열에서 고유 문자로 이루어진 List 를 반환하는 기능 실패 예제
         *
         */
        String[] arr = {"Hello", "world"};
        Arrays.stream(arr)
                .map(s -> s.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        List<String> collect4 = Arrays.stream(arr)
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        for (String s : collect4) {
            System.out.println(s);
        }

        /**
         * 숫자 리스트 -> 제곱 리스트 예제
         * 소스 : {1,2,3,4,5}
         * 요소 : int
         * 중간 연산 : map
         * 최종 연산 : toArray
         */
        int[] ints = {1, 2, 3, 4, 5};
        int[] ints1 = Arrays.stream(ints)
                .map(i -> i * i)
                .toArray();
        for (int i : ints1) {
            System.out.println(i);
        }

        /**
         * 두개의 숫자 리스트 -> 모든 숫자 2쌍의 리스트 반환
         */
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        List<Integer> integerList1 = Arrays.asList(4, 5);

        List<int[]> collect5 = integerList.stream()
                .flatMap(i -> integerList1.stream()
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());
        for (int[] ints2 : collect5) {
            System.out.println(ints2[0]);
            System.out.println(ints2[1]);
        }

        /**
         * 이전 예제에서 숫자 쌍의 합이 3의 배수인 것만 추출
         */
        List<int[]> collect6 = integerList.stream()
                .flatMap(i -> integerList1.stream()
                        .filter(j->(i+j)%3==0)
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());
        for (int[] ints2 : collect6) {
            System.out.println(ints2[0]);
            System.out.println(ints2[1]);
        }

        /**
         * 검색과 매칭
         * 쇼트 서킷 : 모든 요소를 확인하지 않고도 (처리하지 않고도) 반환할 수 있는, 원하는 결과를 얻은경우 바로 반환할 수 있는 스트림
         */

        //1. predicate 가 적어도 한 요소와 일치하는지
        if (list.stream()
                .anyMatch(a -> a.getType().equals(Type.FISH))) {
            System.out.println("the menu has seaFood");
        }

        //2. predicate 가 모든 요소와 일치하는지
        if (list.stream()
                .allMatch(a -> a.getType().equals(Type.MEAT))) {
            System.out.println("the menu is not vegetarian friendly ");
        }

        //3. predicate 가 모든 요소와 불일치 하는지
        if (list.stream()
                .noneMatch(Dish::isVegetarian)) {
            System.out.println("the menu is not vegetarian friendly");
        }

        /**
         * 숫자 리스트에서 제곱수가 3으로 나누어 떨어지는 첫 요소 반환
         * 소스 : numList
         * 요소 : Integer
         * 중간 연선 : map -> filter
         * 최종 연산 : findFirst / findAny
         */
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        numList.stream()
                .map(n -> n * n)
                .filter(a -> a % 3 == 0)
                .findFirst();


    }
}
