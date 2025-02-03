// author: Luka Pacar
package aoc_2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <a href="https://adventofcode.com/2024/day/2">Day 2</a>
 */
public class Day2 {

    public static String part1(List<String> input) {
        int output = 0;
        for (String line : input) {
            List<Integer> levels = Stream.of(line.split("\\s+"))
                                    .map(Integer::parseInt)
                                    .toList();
            if (isSafe(levels)) output++;
        }
        return "" + output;
    }

    public static String part2(List<String> input) {
        int output = 0;
        for (String line : input) {
            List<Integer> levels = Stream.of(line.split("\\s+"))
                    .map(Integer::parseInt)
                    .toList();

            if (isSafe(levels)) {
                output++;
            } else {
                for (int i = 0; i < levels.size(); i++) {
                    List<Integer> copy = new ArrayList<>(levels);
                    copy.remove(i);
                    if (isSafe(copy)) {
                        output++;
                        break;
                    }
                }
            }
        }
        return "" + output;
    }

    private static boolean isSafe(List<Integer> levels) {
        Integer prev = null;
        Boolean increasing = null;
        for (int level : levels) {
            if (prev != null) {
                int level_differ = Math.abs(level - prev);
                if (increasing == null) {
                    if (level_differ == 0 || level_differ > 3) return false;
                    increasing = level - prev > 0;
                }
                else if (level_differ == 0 || level_differ > 3) return false;
                else if (increasing != level - prev > 0) return false;
            }
            prev = level;
        }
        return true;
    }
}
