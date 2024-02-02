import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class projectC {

    public static void main(String[] args) {
        String input = "wordsCollection.txt";
        int disT = 0;

        Scanner input_Fuser = new Scanner(System.in);
        System.out.print("Enter Your Word: ");
        String userInput = input_Fuser.next().toLowerCase();

        System.out.println("\n");
        System.out.print("First words with 2 letter change");
        System.out.println("\n");

        try {
            File allWordsFile = new File(input);
            Scanner reader = new Scanner(allWordsFile);

            // Read all words from the file ant put it into an array
            String[] allWordsArray = readAllWordsToArray(reader);

            // Compare user input with all words in the array

            for (String word : allWordsArray) {
                disT = checkingDistance(userInput, word);
                if (disT == 2) {
                    System.out.print(word + " , ");
                }
            }
            System.out.println("\n");
            System.out.print("Now the best answers which means with 1 letter change");
            System.out.println("\n");
            for (String word : allWordsArray) {
                disT = checkingDistance(userInput, word);
                if (disT == 1) {
                    System.out.print(word + " , ");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Read all words from the file into an array
    private static String[] readAllWordsToArray(Scanner reader) {
        java.util.List<String> wordsList = new java.util.ArrayList<>();

        // Read all words from the file
        while (reader.hasNext()) {
            String collection = reader.next().toLowerCase();
            wordsList.add(collection);
        }

        // Convert the ArrayList to an array
        return wordsList.toArray(new String[0]);
    }

    // using Levenshtein distance algorithm
    public static int checkingDistance(String wordC, String wordU) {
        int[][] dp = new int[wordC.length() + 1][wordU.length() + 1];
        for (int i = 0; i <= wordC.length(); i++) {
            for (int j = 0; j <= wordU.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + (wordC.charAt(i - 1) == wordU.charAt(j - 1) ? 0 : 1),
                            dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }
        return dp[wordC.length()][wordU.length()];
    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}

