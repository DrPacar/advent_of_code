// author: Luka Pacar
package aoc_2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * <a href="https://adventofcode.com/2024/day/5">Day 5</a>
 */
public class Day5 {


    /** Stores the left Side of the page-ordering-rules */
    private static List<Integer> pagesLeft;
    /** Stores the right Side of the page-ordering-rules */
    private static List<Integer> pagesRight;

    /** Stores input lines to check */
    private static List<List<Integer>> lines;

    public static String part1(List<String> input) {
       load_input(input);

       int output = 0;
       for (List<Integer> line : lines) {
           if (lineIsLegal(line)) output  += line.get(line.size()/2);
       }

       return output + "";
    }

    public static String part2(List<String> input) {
        load_input(input);

        int output = 0;
        for (List<Integer> line : lines) {
            if (!lineIsLegal(line)) {
                List<Integer> fixedLine = fixLine(line);
                if (lineIsLegal(fixedLine)) output += fixedLine.get(fixedLine.size()/2);
            }
        }

        return output + "";
    }

    /**
     * Fixes a line by ordering the pages correctly
     * @param line The line to fix
     * @return the fixed line
     */
    private static List<Integer> fixLine(List<Integer> line) {
        List<Integer> fixedLine = new ArrayList<>(line);
        fixedLine.sort((a,b) -> {
            for (int i = 0; i < pagesLeft.size(); i++) {
                int pageLeft = pagesLeft.get(i);
                int pageRight = pagesRight.get(i);
                if (pageLeft == a && pageRight == b) return -1;
                else if (pageLeft == b && pageRight == a) return 1;
            }
            return 0;
        });
        return fixedLine;
    }

    /**
     * Checks if the line is legal
     * @param line The line to check
     * @return true if the line is considered legal
     */
    private static boolean lineIsLegal(List<Integer> line) {
        for (int i = 0; i < pagesLeft.size(); i++) {
            int pageLeft = pagesLeft.get(i);
            int pageRight = pagesRight.get(i);
            if (!validRule(line, pageLeft, pageRight)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Looks if the line is following the given rule
     * @param line The line to check
     * @param page1 has to be before page2
     * @param page2 has to be after page1
     * @return true if the line is following the rule
     */
    private static boolean validRule(List<Integer> line, int page1, int page2) {
        boolean foundPage2 = false;
        for (int i : line) {
            if (i == page2) foundPage2 = true;
            else if (i == page1 && foundPage2) return false;
        }
        return true;
    }

    /**
     * Loads the input and stores it in the map and lines
     * @param input The input to load
     */
    private static void load_input(List<String> input) {

        pagesLeft = new ArrayList<>();
        pagesRight = new ArrayList<>();
        lines = new ArrayList<>();

        boolean readingPages = true;
        for (String line : input) {
            if (line.isEmpty()) {
                readingPages = false;
                continue;
            }
            if (readingPages) {
                String[] split = line.split("\\|");
                int page1 = Integer.parseInt(split[0]);
                int page2 = Integer.parseInt(split[1]);
                pagesLeft.add(page1);
                pagesRight.add(page2);
            } else {
                lines.add(
                        Stream.of(line.split(","))
                                .map(Integer::parseInt)
                                .toList()
                );
            }
        }
    }
}
