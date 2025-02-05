// author: Luka Pacar
package aoc_2024;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * <a href="https://adventofcode.com/2024/day/7">Day 7</a>
 */
public class Day7 {

    public static String part1(List<String> input) {
        return "" + solvePuzzle(input, false);
    }

    public static String part2(List<String> input) {
        return "" + solvePuzzle(input, true);
    }

    /**
     * Solves the Day 7 Puzzle
     * @param input The input to solve
     * @param allowConcat If the concatenation operator is allowed
     * @return the solution to the puzzle
     */
    private static long solvePuzzle(List<String> input, boolean allowConcat) {
        long output = 0;
        Pattern input_regex = Pattern.compile("(\\d+):(.*)");

        for (String line : input) {
            Matcher matcher = input_regex.matcher(line);
            if (matcher.matches()) {
                long target = Long.parseLong(matcher.group(1));
                List<Long> items = Stream.of(matcher.group(2).trim().split("\\s+"))
                        .map(Long::parseLong)
                        .toList();
                if (validEvaluation(target, items, allowConcat)) output += target;
            }
        }

        return output;
    }

    /**
     * Checks if any configuration of items can be evaluated to the target
     * @param target The target to reach
     * @param items The items to evaluate
     * @param concatenationOperator If the concatenation operator is allowed
     * @return true if the items can be evaluated to the target
     */
    private static boolean validEvaluation(long target, List<Long> items, boolean concatenationOperator) {
        if (items.size() == 1) return items.getFirst() == target;

        long first_item = items.getFirst();
        List<Long> newItems = new ArrayList<>(items);
        newItems.removeFirst();

        List<Long> addItems = new ArrayList<>(newItems);
        addItems.set(0, addItems.getFirst() + first_item);

        List<Long> multiItems = new ArrayList<>(newItems);
        multiItems.set(0, multiItems.getFirst() * first_item);

        boolean concat = false;
        if (concatenationOperator) {
            List<Long> concatItems = new ArrayList<>(newItems);
            concatItems.set(0, Long.parseLong(first_item + "" + concatItems.getFirst()));
            concat = validEvaluation(target, concatItems, true);
        }

        return
            // Operations
            validEvaluation(target, addItems, concatenationOperator) ||
            validEvaluation(target, multiItems, concatenationOperator) ||
            concat;
    }
}
