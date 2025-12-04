//Alin & Jothan
// 12/4/25

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* ========== MULTI THREAD TASK ========== */
class FileTask implements Callable<Result> {

    private final String filePath;

    FileTask(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Result call() {

        long start = System.currentTimeMillis();
        int charCount = 0;
        int wordCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                charCount += line.length();

                if (!line.trim().isEmpty()) {
                    String[] words = line.trim().split("\\s+");
                    wordCount += words.length;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Finished reading: " + filePath);
        System.out.println("Characters counted: " + charCount);
        System.out.println("Words counted: " + wordCount);
        System.out.println("Time for " + filePath + ": " + (end - start) + " ms\n");

        return new Result(charCount, wordCount, end - start);
    }
}

/* ========== RESULT CLASS ========== */
class Result {
    int characters;
    int words;
    long time;

    Result(int characters, int words, long time) {
        this.characters = characters;
        this.words = words;
        this.time = time;
    }
}

/* ========== MAIN CLASS ========== */
public class Threadmain {

    /* -------- SINGLE THREAD METHOD -------- */
    public static void readFile(String filePath) {

        long start = System.currentTimeMillis();
        int charCount = 0;
        int wordCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                charCount += line.length();

                if (!line.trim().isEmpty()) {
                    String[] words = line.trim().split("\\s+");
                    wordCount += words.length;
                }
            }

            System.out.println("Finished reading: " + filePath);
            System.out.println("Characters counted: " + charCount);
            System.out.println("Words counted: " + wordCount);

        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Time for " + filePath + ": " + (end - start) + " ms\n");
    }

    public static void main(String[] args) {

        /* ========== SINGLE THREAD ========== */
        System.out.println("===== SINGLE THREAD =====\n");

        long singleStart = System.currentTimeMillis();

        readFile("wonderland2.txt");
        readFile("shakespere2.txt");

        long singleEnd = System.currentTimeMillis();

        System.out.println("------------------------------------");
        System.out.println("TOTAL SINGLE THREAD TIME: " + (singleEnd - singleStart) + " ms\n");

        /* ========== MULTI THREAD ========== */
        System.out.println("===== MULTI THREAD (ExecutorService) =====\n");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        long multiStart = System.currentTimeMillis();

        Future<Result> f1 = executor.submit(new FileTask("wonderland2.txt"));
        Future<Result> f2 = executor.submit(new FileTask("shakespere2.txt"));

        try {
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        long multiEnd = System.currentTimeMillis();

        System.out.println("------------------------------------");
        System.out.println("TOTAL MULTI THREAD TIME: " + (multiEnd - multiStart) + " ms");

        executor.shutdown();
    }
}
