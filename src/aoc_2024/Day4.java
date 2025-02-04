// author: Luka Pacar
package aoc_2024;

import utility.Tools.Point2D;
import java.util.HashMap;
import java.util.List;

import static utility.Tools.generateMap;

/**
 * <a href="https://adventofcode.com/2024/day/4">Day 4</a>
 */
public class Day4 {

    // Part 1
    /** Saves the next Character for the given Character */
    private static final HashMap<Character, Character> NEXT_CHAR = new HashMap<>() {{
        put('X', 'M');
        put('M', 'A');
        put('A', 'S');
        put('S', '-');
    }};

    /** The valid Direction-Inputs for X (for Part 1) */
    private static final int[] X_DIRECTIONS_PART1 = {-1,-1,-1, 0, 0, 1, 1, 1};
    /** The valid Direction-Inputs for Y (for Part 1) */
    private static final int[] Y_DIRECTIONS_PART1 = {-1, 0, 1,-1, 1,-1, 0, 1};

    // Part 2
    /** The valid Direction-Inputs for X (for Part 2) */
    private static final int[] X_DIRECTIONS_PART2 = {-1,-1, 1, 1};
    /** The valid Direction-Inputs for Y (for Part 2) */
    private static final int[] Y_DIRECTIONS_PART2 = {-1, 1,-1, 1};

    /** The valid XMAS Configurations for Part 2 */
    private static final String[][] VALID_XMAS_CONFIGURATIONS = new String[][] {
            new String[] {"M", "M", "S", "S"},
            new String[] {"S", "S", "M", "M"},
            new String[] {"M", "S", "M", "S"},
            new String[] {"S", "M", "S", "M"}
    };

    public static String part1(List<String> input) {
        long output = 0;
        List<List<Character>> map = generateMap(input);

        for (int x = 0; x < map.size(); x++) {
            for (int y = 0; y < map.getFirst().size(); y++) {
                if (map.get(x).get(y) == 'X') {
                    output += find_XMAS(map, new Point2D(x, y), X_DIRECTIONS_PART1, Y_DIRECTIONS_PART1);
                }
            }
        }

        return "" + output;
    }

    public static String part2(List<String> input) {
        long output = 0;
        List<List<Character>> map = generateMap(input);

        for (int x = 0; x < map.size(); x++) {
            for (int y = 0; y < map.getFirst().size(); y++) {
                if (map.get(x).get(y) == 'A') {
                    output += isXMAS(map, new Point2D(x, y)) ? 1 : 0;
                }
            }
        }

        return "" + output;
    }


    /**
     * Finds all XMAS that start on the current Location
     * @param map The Map
     * @param location The Start-Location
     * @param directionsX The valid X-Directions
     * @param directionsY The valid Y-Directions
     * @return The amount of valid XMAS that start on the current Location
     */
    private static int find_XMAS(List<List<Character>> map, Point2D location, int[] directionsX, int[] directionsY) {
        if (directionsX.length != directionsY.length) throw new IllegalArgumentException("directionsX and directionsY must have the same length");
        int count = 0;
        char nextChar = NEXT_CHAR.get(map.get(location.getX()).get(location.getY()));
        if (nextChar == '-') return 1;
        for (int i = 0; i < directionsX.length; i++) {
            int newX = location.getX() + directionsX[i];
            int newY = location.getY() + directionsY[i];
            try {
                char charAt = map.get(newX).get(newY);
                if (charAt == nextChar) count += find_XMAS(map, new Point2D(newX, newY), new int[] {directionsX[i]}, new int[] {directionsY[i]});
            } catch (IndexOutOfBoundsException e) {
                // Do nothing
            }
        }
        return count;
    }

    /**
     * Checks if the given Location is the Start of an X Shaped XMAS
     * @param map The Map
     * @param location The Location
     * @return True if the Location is the Start of an X Shaped XMAS
     */
    private static boolean isXMAS(List<List<Character>> map, Point2D location) {
        for (String[] config : VALID_XMAS_CONFIGURATIONS) {
            boolean valid = true;
            for (int i = 0; i < X_DIRECTIONS_PART2.length; i++) {
                int newX = location.getX() + X_DIRECTIONS_PART2[i];
                int newY = location.getY() + Y_DIRECTIONS_PART2[i];

                try {
                    if (map.get(newX).get(newY) != config[i].charAt(0)) {
                        valid = false;
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    valid = false;
                    break;
                }
            }
            if (valid) return true;
        }

        return false;
    }

}
