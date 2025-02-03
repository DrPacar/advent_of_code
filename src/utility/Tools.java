// author: Luka Pacar
package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Tools {
    /**
     * Returns all the Lines of the parsed File
     * @param fileName The Path to the File
     * @return List of all the Lines contained inside the File
     */
    public static List<String> readFile(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Path.of(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) { throw new RuntimeException(e); }
        return list;
    }

    /**
     * Solves a Day of a specific year
     * @param year The AOC_Year
     * @param day The Day to solve
     * @param input The Input for the Puzzle
     */
    public static void solve_Day(int year, int day, List<String> input) {
        Object[] results =  invokeMethods("aoc_" + year + ".Day" + day, input);
        System.out.println("Part 1: " + (results[0] == null ? "Error occurred" : results[0]));
        System.out.println("Part 2: " + (results[1] == null ? "Error occurred" : results[1]));
    }

    /**
     * Solves a Day of a specific year
     * @param year The AOC_Year
     * @param day The Day to solve
     * @param input The Path to the Input-File
     */
    public static void solve_Day(int year, int day, String input) {
        solve_Day(year, day, readFile(input));
    }

    /**
     * Invokes 2 Methods of a given class
     * @param className class the methods belong to
     * @return Array with the Outputs of the 2 Methods
     */
    private static Object[] invokeMethods(String className, List<String> input) {
        try {
            // Load the class dynamically
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // Get the specified methods
            Method m1 = clazz.getMethod("part1", List.class);
            Method m2 = clazz.getMethod("part2", List.class);

            // Invoke methods and store results
            Object result1 = null;
            Object result2 = null;
            try {
                result1 = m1.invoke(instance, input);
                result2 = m2.invoke(instance, input);
            } catch (InvocationTargetException e) { e.printStackTrace(); }

            return new Object[]{result1, result2};
        } catch (Exception e) {
            throw new IllegalArgumentException("Seems like there was an Implementation Error : " + className, e);
        }
    }
}
