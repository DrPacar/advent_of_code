// author: Luka Pacar
package aoc_2024;

import static utility.Tools.generateMap;
import static utility.Tools.Point2D;

import java.util.*;


/**
 * <a href="https://adventofcode.com/2024/day/8">Day 8</a>
 */
public class Day8 {

    public static String part1(List<String> input) {
       List<List<Character>> map = generateMap(input);
       return "" + calculateAntiNodes(map, true);
    }

    public static String part2(List<String> input) {
        List<List<Character>> map = generateMap(input);
        return "" + calculateAntiNodes(map, false);
    }

    /**
     * Calculates the number of anti-nodes in the given map
     * @param map The map to calculate the anti-nodes in
     * @param cappedDistance Whether the distance should be capped or not (part1|part2)
     * @return The number of anti-nodes in the given map
     */
    private static int calculateAntiNodes(List<List<Character>> map, boolean cappedDistance) {
        HashMap<Character, List<Point2D>> antennas = load_input(map);
        Set<Point2D> antiNodePoints = new HashSet<>();

        for (char key : antennas.keySet()) {
            List<Point2D> positions = antennas.get(key);
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i+1; j < positions.size(); j++) {

                    Point2D position = positions.get(i);
                    Point2D other_position = positions.get(j);

                    int x_diff = other_position.getX() - position.getX();
                    int y_diff = other_position.getY() - position.getY();

                    // Anti-node behind position
                    int position_curr_x = position.getX();
                    int position_curr_y = position.getY();
                    if (cappedDistance) {
                        position_curr_x -= x_diff;
                        position_curr_y -= y_diff;
                    }
                    while (true) {
                        try {
                            char _ = map.get(position_curr_x).get(position_curr_y);
                            antiNodePoints.add(new Point2D(position_curr_x, position_curr_y));
                            map.get(position_curr_x).set(position_curr_y, 'X');
                            if (cappedDistance) break;
                            position_curr_x -= x_diff;
                            position_curr_y -= y_diff;
                        } catch (IndexOutOfBoundsException ignored) {
                            break;
                        }
                    }

                    // Anti-node behind other_position
                    int other_position_curr_x = other_position.getX();
                    int other_position_curr_y = other_position.getY();
                    if (cappedDistance) {
                        other_position_curr_x += x_diff;
                        other_position_curr_y += y_diff;
                    }
                    while (true) {
                        try {
                            char _ = map.get(other_position_curr_x).get(other_position_curr_y);
                            antiNodePoints.add(new Point2D(other_position_curr_x, other_position_curr_y));
                            map.get(other_position_curr_x).set(other_position_curr_y, 'X');
                            if (cappedDistance) break;
                            other_position_curr_x += x_diff;
                            other_position_curr_y += y_diff;
                        } catch (IndexOutOfBoundsException ignored) {
                            break;
                        }
                    }
                }
            }
        }

        return antiNodePoints.size();
    }


    /**
     * Returns a HashMap with the frequencies as keys and the positions as values
     * @param map The map to load
     * @return The HashMap with the frequencies as keys and the positions as values
     */
    public static HashMap<Character, List<Point2D>> load_input(List<List<Character>> map) {

        HashMap<Character, List<Point2D>> antennas = new HashMap<>();

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.getFirst().size(); j++) {
                char frequency = map.get(i).get(j);
                if (frequency == '.') continue;
                if (antennas.containsKey(frequency)) {
                    antennas.get(frequency).add(new Point2D(i, j));
                } else {
                    List<Point2D> list = new ArrayList<>();
                    list.add(new Point2D(i, j));
                    antennas.put(frequency, list);
                }
            }
        }

        return antennas;
    }
}
