
import org.apfloat.Apfloat;

public class DeterminantMethod {

    public static Apfloat determinant(Apfloat[][] matrix) {
        Apfloat sum = new Apfloat(0, Apfloat.INFINITE);
        Apfloat s;
        if (matrix.length == 1) {
            return (matrix[0][0]);
        }
        for (int i = 0; i < matrix.length; i++) { // finds determinant using
                                                  // row-by-row expansion
            Apfloat[][] smaller = new Apfloat[matrix.length - 1][matrix.length - 1];
            for (int a = 1; a < matrix.length; a++) {
                for (int b = 0; b < matrix.length; b++) {
                    if (b < i) {
                        smaller[a - 1][b] = matrix[a][b];
                    } else if (b > i) {
                        smaller[a - 1][b - 1] = matrix[a][b];
                    }
                }
            }
            if (i % 2 == 0) { // sign changes based on i
                s = new Apfloat(1);
            } else {
                s = new Apfloat(-1);
            }
            sum = sum.add(matrix[0][i].multiply(determinant(smaller).multiply(s))); // recursive
                                                                                    // step:
                                                                                    // determinant
                                                                                    // of
                                                                                    // larger
                                                                                    // determined
                                                                                    // by
                                                                                    // smaller.
        }
        return (sum); // returns determinant value. once stack is finished,
                      // returns final determinant.
    }

    public static Apfloat[][] toSmaller(Apfloat[][] matrix, int i) { // purva
                                                                     // iteracia
                                                                     // na
                                                                     // adiungiranite
                                                                     // mnojestva
        Apfloat[][] smaller = new Apfloat[matrix.length - 1][matrix.length - 1];
        for (int a = 1; a < matrix.length; a++) {
            for (int b = 0; b < matrix.length; b++) {
                if (b < i) {
                    smaller[a - 1][b] = matrix[a][b];
                } else if (b > i) {
                    smaller[a - 1][b - 1] = matrix[a][b];
                }
            }
        }

        return smaller;
    }

}
