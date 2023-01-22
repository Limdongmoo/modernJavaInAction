package makestream;

import java.util.stream.Stream;

public class Fibo {
    public static void main(String[] args) {
        Stream.iterate(new int[]{0, 1}, ints -> new int[]{ints[1], ints[0] + ints[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

    }
}
