
import org.apfloat.Apfloat;

public class Result {
    private static boolean quiet;
    private static Apfloat determinant = new Apfloat(0);

    // private static int counter = 0;

    public static synchronized void add(Apfloat i) {
        determinant = determinant.add(i);
    }

    public static synchronized void remove(Apfloat i) {
        determinant = determinant.subtract(i);
    }

    public static Apfloat finalResult() {
        return determinant;
    }

    public static boolean isQuiet() {
        return quiet;
    }

    public static void setQuiet(boolean quiet) {
        Result.quiet = quiet;
    }
}
