import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

sealed interface GeometricShape {}
record Point   ( int x,
                 int y)         implements GeometricShape { }
record Line    ( Point start,
                 Point end)     implements GeometricShape { }
record Triangle( Point pointA,
                 Point pointB,
                 Point PointC)  implements GeometricShape { }
record Square  ( Point pointA,
                 Point pointB,
                 Point PointC,
                 Point pointD)  implements GeometricShape { }

public class Main {
    public static void main(String[] args) {

        //JEP 456: Unnamed Variables and Patterns
        //https://openjdk.org/jeps/456

        //Unnamed variables for unused variables
        var names = List.of("Alice", "Bob", "Charlie");
        var count = 0;
        for (var _ : names) {
            //In this case, we are doing something for each name in the list,
            // but we don't necessarily care about the name itself.
            System.out.println("Incrementing count for a name");
            count++;
        }
        System.out.println("Count: " + count);

        //Unnamed variables for try-with resources
        try (Connection _ = DriverManager.getConnection("localhost", "user", "password")) {
            //Do something like create some tables, insert some data, etc.
            //If we don't plan on using the connection object, we can use an unnamed variable
            // as a placeholder.
        } catch (SQLException e) {
            // Handle the exception
        }

        //Unnamed variables for handling exceptions
        try {
            throw new Exception("Something went wrong");
        } catch (Exception _) {
            System.out.println("We dont care about the exception object, lets just run some code to handle it.");
        }

        //Lambdas
        names.forEach(_ -> System.out.println("Hello"));

        /////////////////////////////
        //Unnamed Patterns
        var pointA = new Point(1, 67);
        if (pointA instanceof Point(int x, _)) {
            System.out.println("Point A is at x: " + x);
        }

        //Nested record patterns with unnamed variables
        var Line = new Line(new Point(1, 2), new Point(3, 4));
        if (Line instanceof Line(_, Point (int x, int _))) {
            System.out.println("Line ends at x: " + x);
        }

    }

    //Unnamed variables for record patterns
    //From: https://blog.jetbrains.com/idea/2024/03/drop-the-baggage-use-_-for-unnamed-local-variables-and-patterns-in-java-22/
//    int calcArea(GeometricShape figure) {
//        return switch (figure) {
//            case Point    (int x, int y)                        -> 0;
//            case Line     (Point a, Point b)                    -> 0;
//            case Triangle (Point a, Point b, Point c)           -> areaTriangle(a, b, c);
//            case Square   (Point a, Point b, Point c, Point d)  -> areaSquare  (a, b, c, d);
//        };
//    }

    //Improved using unnamed patterns
    int calcArea(GeometricShape figure) {
        return switch (figure) {
            case Point _, Line _                                -> 0;
            case Triangle (Point a, Point b, Point c)           -> areaTriangle(a, b, c);
            case Square   (Point a, Point b, Point c, Point d)  -> areaSquare  (a, b, c, d);
        };
    }

    private int areaSquare(Point a, Point b, Point c, Point d) {
        return 0;
    }

    private int areaTriangle(Point a, Point b, Point c) {
        return 0;
    }

}