import org.apfloat.Apfloat;

class WorkerThread implements Runnable {
    private Apfloat[][] matrixCalc; // adiуngirano mnojestvo
    private int index; // nomer na iteraciqta; sledi za +- posledovatelnostta
    private Apfloat aij; // koeficienta pred adiungiranoto mnojestvo

    public WorkerThread(Apfloat[][] smaller, int i, Apfloat omatrix) {
        this.matrixCalc = smaller;
        this.index = i;
        this.aij = omatrix;

    }

    public void run() {
        long start = System.currentTimeMillis();
        processMessage(matrixCalc);
        if (!Result.isQuiet()) {
            System.out.println(Thread.currentThread().getName() + " found the " + (index + 1)
                    + " adjoint determinant in " + (System.currentTimeMillis() - start) + "milliseconds.");// prints
                                                                                                           // thread
                                                                                                           // name
        }
    }

    private void processMessage(Apfloat[][] m) {
        if (index % 2 == 0) {
            Result.add(aij.multiply(DeterminantMethod.determinant(m)));
        } else {
            Result.remove(aij.multiply(DeterminantMethod.determinant(m)));
        }

    }
}