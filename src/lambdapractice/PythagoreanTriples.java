package lambdapractice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PythagoreanTriples {

    public static void main(String[] args) {
        Stream<double[]> pythaWithDouble = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)}))
                .filter(t -> t[2] % 1 == 0);

//        collect.forEach(a -> Arrays.stream(a).forEach(System.out::println));

        pythaWithDouble.limit(5)
                        .forEach(
                                doubles -> Arrays.stream(doubles)
                                        .forEach(System.out::println)
                        );
    }

}
