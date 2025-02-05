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
import java.util.Objects;

public class Tools {

    // Direction

    /** Represents a Direction in a 2D Space */
    public enum Direction {
        TOP_LEFT(-1, -1),
        TOP(-1, 0),
        TOP_RIGHT(-1, 1),
        RIGHT(0, 1),
        BOTTOM_RIGHT(1, 1),
        BOTTOM(1, 0),
        BOTTOM_LEFT(1, -1),
        LEFT(0, -1),
        NONE(0, 0);

        final int rowDir;
        final int colDir;

        /**
         * Represents a Direction in a 2D Space
         * @param rowDir The vertical (row) direction-change-value
         * @param colDir The horizontal (col) direction-change-value
         */
        Direction (int rowDir, int colDir) {
            this.rowDir = rowDir;
            this.colDir = colDir;
        }

        // Getters
        /** Returns the vertical (row) direction-change-value */
        public int getVerticalChange() {return rowDir;}
        /** Returns the horizontal (col) direction-change-value */
        public int getHorizontalChange() {return colDir;}
        /** Returns the Direction for the given row and col change */
        public int[] getChange() {return new int[]{rowDir, colDir};}

        /** Returns the Direction for the given row and col change */
        public static Direction getDirection(int rowChange, int colChange) {
            for (Direction dir : Direction.values()) {
                if (dir.rowDir == rowChange && dir.colDir == colChange) return dir;
            }
            return NONE;
        }

        /** Turns the Direction 90° to the right */
        public Direction turn90DegreesRight() {
            return getDirection(TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT, TOP);
        }

        /** Turns the Direction 45° to the right */
        public Direction turn45DegreesRight() {
            return getDirection(TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT);
        }

        /** Turns the Direction 90° to the left */
        public Direction turn90DegreesLeft() {
            return getDirection(BOTTOM_LEFT, LEFT, TOP_LEFT, TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM);
        }

        /** Turns the Direction 45° to the left */
        public Direction turn45DegreesLeft() {
            return getDirection(LEFT, TOP_LEFT, TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT);
        }


        /** Turns the Direction 180° */
        public Direction turn180Degrees() {
            return getDirection(BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT, TOP, TOP_RIGHT, RIGHT);
        }

        /**
         * Executes Direction Mapping
         * @param direction  Source: TOP_LEFT
         * @param direction2 Source: TOP
         * @param direction3 Source: TOP_RIGHT
         * @param direction4 Source: RIGHT
         * @param direction5 Source: BOTTOM_RIGHT
         * @param direction6 Source: BOTTOM
         * @param direction7 Source: BOTTOM_LEFT
         * @param direction8 Source: LEFT
         * @return The mapped Direction
         */
        private Direction getDirection(Direction direction, Direction direction2, Direction direction3, Direction direction4, Direction direction5, Direction direction6, Direction direction7, Direction direction8) {
            return switch (this) {
                case TOP_LEFT       -> direction;
                case TOP            -> direction2;
                case TOP_RIGHT      -> direction3;
                case RIGHT          -> direction4;
                case BOTTOM_RIGHT   -> direction5;
                case BOTTOM         -> direction6;
                case BOTTOM_LEFT    -> direction7;
                case LEFT           -> direction8;
                default             -> NONE;
            };
        }
    }

    // Point

    /** Represents a Point in a 2D Space (using long as coordinates)*/
    public static class Point2D {
        int x;
        int y;

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Getters
        public int getX() {return x;}
        public int getY() {return y;}

        // Setters
        public void setX(int x) {this.x = x;}
        public void setY(int y) {this.y = y;}

        // Hash

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point2D that = (Point2D) o;
            return x == that.x && y == that.y;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        // toString

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }

    /** Represents a Point in a 2D Space (using long as coordinates)*/
    public static class Point2D_Long {
        long x;
        long y;

        public Point2D_Long(long x, long y) {
            this.x = x;
            this.y = y;
        }

        // Getters
        public long getX() {return x;}
        public long getY() {return y;}

        // Setters
        public void setX(long x) {this.x = x;}
        public void setY(long y) {this.y = y;}

        // Hash

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point2D_Long that = (Point2D_Long) o;
            return x == that.x && y == that.y;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        // toString

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }

    /** Represents a Point in a 2D Space (using double as coordinates)*/
    public static class Point2D_Double {
        double x;
        double y;

        public Point2D_Double(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // Getters
        public double getX() {return x;}
        public double getY() {return y;}

        // Setters
        public void setX(double x) {this.x = x;}
        public void setY(double y) {this.y = y;}

        // Hash

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point2D_Double that = (Point2D_Double) o;
            return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        // toString

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }


    // Maps

    /**
     * Generates a Character Map from the given Input
     * @param input The Input
     * @return The Map
     */
    public static List<List<Character>> generateMap(List<String> input) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : input) {
            List<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            map.add(row);
        }
        return map;
    }

    /**
     * Looks for a specific Character in a 2D-Map and returns the first Point where it was found
     * @param map The Map to search in
     * @param startChar The Character to search for
     * @return The first Point where the Character was found
     */
    public static Point2D findMapPoint(List<List<Character>> map, char startChar) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.getFirst().size(); j++) {
                if (map.get(i).get(j) == startChar) return new Point2D(i, j);
            }
        }
        return null;
    }

    /**
     * Prints a 2D-Map
     * @param map The Map to print
     */
    public static void printMap(List<List<Character>> map) {
        for (List<?> row : map) {
            for (Object c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    // Executing Day-Puzzles
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
     * Solves a Day of a specific year
     * @param year The AOC_Year
     * @param day The Day to solve
     */
    public static void solve_Day(int year, int day) {
        solve_Day(year, day, "resources/aoc_" + year + "/Day" + day);
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
