// author: Luka Pacar
package aoc_2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utility.Tools.generateMap;
import static utility.Tools.findMapPoint;
import utility.Tools.Point2D;
import utility.Tools.Direction;

/**
 * <a href="https://adventofcode.com/2024/day/6">Day 6</a>
 */
public class Day6 {

    public static String part1(List<String> input) {
        List<List<Character>> map = generateMap(input);
        Point2D startingPoint = findMapPoint(map, '^');

        return "" + traverseMap(map, startingPoint, Direction.TOP)
                    .stream()
                    .flatMap(List::stream)
                    .filter(c -> c == 'X')
                    .count();
    }

    public static String part2(List<String> input) {
        List<List<Character>> map = generateMap(input);
        Point2D startingPoint = findMapPoint(map, '^');

        return "" + findPossibleBlockerMaps(map, startingPoint, Direction.TOP, new HashSet<>())
                    .stream()
                    .filter(m -> mapIsLoop(m,startingPoint,Direction.TOP,new HashSet<>()))
                    .count();
    }


    /**
     * Walks through the map and marks all visited places by placing a 'X' at those positions.
     * Encountering a '#' turns the direction 90° to the right.
     * @param map The map to traverse
     * @param currPosition The current position
     * @param currDirection The current direction
     * @return The map with the visited places marked with 'X'
     */
    private static List<List<Character>> traverseMap(List<List<Character>> map, Point2D currPosition, Direction currDirection) {
        map.get(currPosition.getX()).set(currPosition.getY(), 'X');
        int newX = currPosition.getX() + currDirection.getVerticalChange();
        int newY = currPosition.getY() + currDirection.getHorizontalChange();
        try {
            if (map.get(newX).get(newY) == '#') {
                // Turning 90° to the right
                Direction newDirection = currDirection.turn90DegreesRight();
                return traverseMap(map, currPosition, newDirection);
            } else {
                return traverseMap(map, new Point2D(newX, newY), currDirection);
            }
        } catch (IndexOutOfBoundsException ignored) {
            return map;
        }
    }


    /**
     * Finds all valid maps that can be created by placing a '#' at the natural path.
     * @param map The map to check
     * @param currPosition The current position
     * @param currDirection The current direction
     * @param currentlyFoundValidBlockerMaps The currently found valid maps
     * @return All valid maps that can be created by placing a '#' at the natural path
     */
    private static Set<List<List<Character>>> findPossibleBlockerMaps(List<List<Character>> map, Point2D currPosition, Direction currDirection, Set<List<List<Character>>> currentlyFoundValidBlockerMaps) {
        int newX = currPosition.getX() + currDirection.getVerticalChange();
        int newY = currPosition.getY() + currDirection.getHorizontalChange();
        try {
            if (map.get(newX).get(newY) == '#') {
                // Turning 90° to the right
                Direction newDirection = currDirection.turn90DegreesRight();
                return findPossibleBlockerMaps(map, currPosition, newDirection, currentlyFoundValidBlockerMaps);
            } else {
                List<List<Character>> deepCopy = new ArrayList<>();
                for (List<Character> innerList : map) deepCopy.add(new ArrayList<>(innerList));
                deepCopy.get(newX).set(newY, '#');
                currentlyFoundValidBlockerMaps.add(deepCopy);
                return findPossibleBlockerMaps(map, new Point2D(newX, newY), currDirection, currentlyFoundValidBlockerMaps);
            }
        } catch (IndexOutOfBoundsException ignored) {
            return currentlyFoundValidBlockerMaps;
        }
    }

    /**
     * Stores a direction and a point
     * @param direction The direction
     * @param point The point
     */
    private record DirectionPoint(Direction direction, Point2D point) {}

    /**
     * Checks if the map contains a loop
     * @param map The map to check
     * @param currPosition The current position
     * @param currDirection The current direction
     * @param visitedStates The visited states
     * @return True if the map contains a loop
     */
    private static boolean mapIsLoop(List<List<Character>> map, Point2D currPosition, Direction currDirection, HashSet<DirectionPoint> visitedStates) {
        if (visitedStates.contains(new DirectionPoint(currDirection, currPosition))) return true;
        visitedStates.add(new DirectionPoint(currDirection, currPosition));

        int newX = currPosition.getX() + currDirection.getVerticalChange();
        int newY = currPosition.getY() + currDirection.getHorizontalChange();
        try {
            if (map.get(newX).get(newY) == '#') {
                // Turning 90° to the right
                Direction newDirection = currDirection.turn90DegreesRight();
                return mapIsLoop(map, currPosition, newDirection, visitedStates);
            } else {
                return mapIsLoop(map, new Point2D(newX, newY), currDirection, visitedStates);
            }
        } catch (IndexOutOfBoundsException ignored) {
            return false;
        }
    }
}
