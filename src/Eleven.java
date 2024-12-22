import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Eleven {
    public static void main(String[] args) {
        List<String> input = getInput("resources/input11");
        String inputString = input.getFirst();

        // 1
        LinkedList<Long> stones = transformStones(inputString);
        for (int i = 1; i < 25; i++) {
            System.out.println("YO " + i);
            //transformStones(stones);
        }
        System.out.println("2 " + stones.size());


        //198075

        // too low 943069756
        long count = 0;
        LinkedList<Long> stones2 = transformStones(inputString);
        for (int i = 0; i < stones2.size(); i++) {
            long stone = stones2.get(i);
            count += blink(stone, 75-1);
        }
        System.out.println("COUNT: " + count);
    }

    static HashMap<Long, Long> map = new HashMap<>();
    private static long blink(long item, int times) {
        long hash = fastHash(item, times);
        if (map.containsKey(hash)) return map.get(hash);
        if (times == 0) return 1;
        long length;
        long output;
        if (item == 0) {
            output = blink(1L, times-1);
        } else if ((length = getDigitLength(item)) % 2 == 0) {
            long[] split = splitLongInto2Parts(item, length);
            output = blink(split[0], times-1) + blink(split[1], times-1);
        } else {
            output = blink(item * 2024, times-1);
        }
        map.put(hash,output);
        return output;
    }
    public static long fastHash(long x, int y) {
        return (x << 32) | (y & 0xFFFFFFFFL);
    }

    private static LinkedList<Long> transformStones(String stones) {
        LinkedList<Long> output = Arrays.stream(stones.split("\\s+")).map(Long::parseLong).collect(Collectors.toCollection(LinkedList::new));
        return transformStones(output);
    }
    private static LinkedList<Long> transformStones(LinkedList<Long> stones) {
        for (int i = 0; i < stones.size(); i++) {
            long stone = stones.get(i);
            long length = 0;
            long timeSpent = System.currentTimeMillis();
            if (stone == 0) {
                stones.set(i, 1L);
            } else if ((length = getDigitLength(stone)) % 2 == 0) {
                long[] split = splitLongInto2Parts(stone, length);
                stones.set(i, split[0]);
                stones.add(i+1, split[1]);
                i++;
            } else {
                stones.set(i, stone * 2024L);
            }
        }
        return stones;
    }

    private static long getDigitLength(long n) {
        return (long) Math.log10(n) + 1;
    }
    private static long[] splitLongInto2Parts(long n, long length) {
        long[] split = new long[2];
        long first = n / (long) Math.pow(10, length / 2);
        long second = n % (long) Math.pow(10, length / 2);
        split[0] = first;
        split[1] = second;
        return split;
    }
    public static List<String> getInput(String file) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(file))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file " + file, e);
        }
    }
}
