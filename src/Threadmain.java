//Alin & Jothan
// 12/4/25

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Threadmain {

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

        long totalStart = System.currentTimeMillis();

        readFile("wonderland2.txt");
        readFile("shakespere2.txt");

        long totalEnd = System.currentTimeMillis();

        System.out.println("------------------------------------");
        System.out.println("TOTAL SINGLE THREAD TIME: " + (totalEnd - totalStart) + " ms");
    }
}
