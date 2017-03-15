

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apfloat.Apfloat;

public class JavaThreadPool {  
    
    static Random rand = new Random();
//    private static double[][] matrix;
    private static Apfloat[][] omatrix;
    
    public static void main(String[] args) throws ParseException, IOException  { 
        long startTime = System.currentTimeMillis();
        
        Options options = new Options();
        options.addOption("n", true, "Dimensions of the matrix");
        options.addOption("t", "tasks", true, "Number of threads");
        options.addOption("q", false, "Quiet mode");
        options.addOption("i", true, "Input file of matrix");
        options.addOption("o", true, "Output file of matix");
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(options, args);

        int matrixLenght = 10;
        int threadCount = 1;
        if (commandLine.hasOption("n")) {
            matrixLenght = Integer.parseInt(commandLine.getOptionValue("n"));
        }
        if (commandLine.hasOption("t")){
            threadCount = Integer.parseInt(commandLine.getOptionValue("t"));
        }
        
        boolean quiet = commandLine.hasOption("q");
        Result.setQuiet(quiet);
        if (commandLine.hasOption("i")) {
            String pathToFile = commandLine.getOptionValue("i");
            
            try {
                ReadFile file = new ReadFile(pathToFile);
                String[] aryLines = file.readFile();
                System.out.println(Arrays.asList(aryLines).toString());
                int i;
                int n = Integer.parseInt(aryLines[0]);
                omatrix = new Apfloat[n][n];
                for (i = 1; i < aryLines.length; i++) {
                    for (int j = 0; j < n; j++){
                        omatrix[i-1][j] = new Apfloat(aryLines[i].split(" ")[j], 20);
                    }
                
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Fail");
            }
        }
        else {
            omatrix = new Apfloat[matrixLenght][matrixLenght];
            for (int i = 0; i < omatrix.length; i++){
                for (int j = 0; j < omatrix.length; j++){
                    omatrix[i][j] = new Apfloat(rand.nextDouble()*1000, 20); //filling up the matrix with random values
                }
            }
        }
        
//        System.out.println(matrix[1][1]);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount); //creating a pool of threads
        for (int i = 0; i < omatrix.length; i++) {  
           Apfloat[][] smaller = DeterminantMethod.toSmaller(omatrix, i);
           Runnable worker = new WorkerThread(smaller, i, omatrix[0][i]);  
           executor.execute(worker);//calling execute method of ExecutorService  
         }  
        executor.shutdown();  
        while (!executor.isTerminated()) {   }  
    
        System.out.println("The determinant equals to " + Result.finalResult());
        if (!Result.isQuiet()){
           System.out.println("Finished in " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        }
        if (commandLine.hasOption("o")){
           PrintWriter out = new PrintWriter(commandLine.getOptionValue("o"));
           out.println(Result.finalResult());
           out.close();
        }
    }  
}  