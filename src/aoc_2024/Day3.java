// author: Luka Pacar
package aoc_2024;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * <a href="https://adventofcode.com/2024/day/3">Day 3</a>
 */
public class Day3 {

    private static final Pattern MUL_REGEX = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    public static String part1(List<String> input) {
        long output = 0;
        for (String line : input) output += addTogetherMul(line);
        return "" + output;
    }

    public static String part2(List<String> input) {
        String one_input_line = input.stream().reduce("", (a, b) -> a + b);

        String[] do_not = one_input_line.split("don't\\(\\)");
        do_not[0] = "do()" + do_not[0]; // Adding do() to the beginning of the line

        String[] parts = Stream.of(do_not)
                .map(s -> {
                    if (s.contains("do()")) return s.substring(s.indexOf("do()"));
                    else return "";
                })
                .toList()
                .toArray(new String[0]);

        return "" + addTogetherMul(parts);
    }

    /**
     * Finds all mul-expressions in the String and adds the results together
     * @param lines The lines to search for mul-expressions
     * @return the added up results
     */
    private static int addTogetherMul(String ... lines) {
        int output = 0;
        for (String line : lines) {
            Matcher matcher = MUL_REGEX.matcher(line);
            while (matcher.find()) {
                int a = Integer.parseInt(matcher.group(1));
                int b = Integer.parseInt(matcher.group(2));
                output += a * b;
            }
        }
        return output;
    }

}
