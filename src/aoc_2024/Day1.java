// author: Luka Pacar
package aoc_2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <a href="https://adventofcode.com/2024/day/1">Day 1</a>
 */
public class Day1 {

    public static String part1(List<String> input) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        // Store Left and Right Numbers
        for (String line : input) {
            String[] split_line = line.split("\\s+");
            left.add(Integer.parseInt(split_line[0]));
            right.add(Integer.parseInt(split_line[1]));
        }

        // Sort Lists
        left.sort(Integer::compareTo);
        right.sort(Integer::compareTo);

        // Add up distances
        int output = 0;
        for (int i = 0; i < left.size(); i++) {
            output += Math.abs(left.get(i) - right.get(i));
        }

        return "" + output;
    }

    public static String part2(List<String> input) {
        List<Integer> left = new ArrayList<>();
        HashMap<Integer, Integer> right = new HashMap<>();

        // Store Left and Right Numbers
        for (String line : input) {
            String[] split_line = line.split("\\s+");
            int left_value = Integer.parseInt(split_line[0]);
            int right_value = Integer.parseInt(split_line[1]);

            left.add(left_value);
            right.put(right_value, right.getOrDefault(right_value, 0) + 1);
        }

        // Add up occurrences
        int output = 0;
        for (int left_value : left) {
            if (right.containsKey(left_value)) {
                output += right.get(left_value) * left_value;
            }
        }

        return "" + output;
    }
}
