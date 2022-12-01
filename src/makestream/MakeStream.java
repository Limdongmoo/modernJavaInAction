package makestream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MakeStream {
    public static void main(String[] args) {
        //대문자 만들기
        Stream<String> modern = Stream.of("Modern", "Java", "In", "Action");
        modern.map(String::toUpperCase)
                .forEach(System.out::println);

        // 배열로 스트림 만들기
        int[] number = {1, 2, 3, 4, 5};
        int sum = Arrays.stream(number).sum();
        System.out.println(sum);

        // 파일 입출력 행별 스트림
        try (Stream<String> lines =
                     Files.lines(Paths.get("/Users/imjongho/Downloads/newmodernjavapractice/src/makestream/aaa"), Charset.defaultCharset())) {
            lines.flatMap(line -> Arrays.stream(line.split("\n")))
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 무한 스트림 만들기 iterate / generate
        Stream.iterate(10, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        // 무한 스트림으로 피타고라스 수 만들기
        List<double[]> collect = Stream.iterate(1, n -> n + 1)
                .flatMap(n -> IntStream.rangeClosed(n, 100)
                        .mapToObj(m -> new double[]{n, m, Math.sqrt(n * n + m * m)})
                        .filter(doubles -> doubles[2] % 1 == 0))
                .limit(10)
                .collect(Collectors.toList());
        collect.forEach(doubles -> Arrays.stream(doubles).forEach(System.out::println));

    }
}
