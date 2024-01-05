package ProjetAgatheWallet;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExercisesManager {
    private static Scanner scanner = new Scanner(System.in);
    private Language language;

    public ExercisesManager(Language language) {
        this.language = language;
    }


    public void createNewExercise(Level lvl) {
        String filePath = "src/ProjetAgatheWallet/" + language + "/" + lvl + ".txt";
        File exoDatabase = new File(filePath);
        System.out.println("To enter a new gap-fill exercise, please enter the sentence using the sharp character (#) to mark the part to guess.");
        System.out.println("Ex: He #loves doing# the house chores. → Here, \"loves doing\" will be replaced by a blank in the exercise.");
        String newQuestion="";
        boolean formatError = true;
        while (formatError) {
            System.out.println("Please enter the desired exercise: ");
            newQuestion = scanner.nextLine();
            Pattern patternAnswer = Pattern.compile("#.*?#");
            Matcher matcherAnswer = patternAnswer.matcher(newQuestion);
            if (matcherAnswer.find()) {
                formatError=false;
            } else {
                System.out.println("Error in the format of the exercise. Please mark the answer by two sharps in the question.");
            }
        }
        try (FileWriter writer = new FileWriter(exoDatabase, true)) {
            writer.write(newQuestion + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error while writing in the file: " + e.getMessage());
        }
    }

    public void modifyExercise(Level lvl) throws IOException {
        String filePath = "src/ProjetAgatheWallet/" + language + "/" + lvl + ".txt";
        File exoDatabase = new File(filePath);
        String tempPath = "src/ProjetAgatheWallet/" + language + "tempfile.txt";
        File tempFile = new File(tempPath);
        BufferedReader reader = new BufferedReader(new FileReader(exoDatabase));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            System.out.println("Do you want to modify this exercise ? (Y/N)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                System.out.println("Do you want to delete this exercise ? (Y/N)");
                String secondChoice = scanner.nextLine();
                if (secondChoice.equalsIgnoreCase("n")) {
                    String newQuestion = "";
                    boolean formatError = true;
                    while (formatError) {
                        System.out.println("Please enter the modified exercise, using sharps to indicate the answer:");
                        newQuestion = scanner.nextLine();
                        Pattern patternAnswer = Pattern.compile("#.*?#");
                        Matcher matcherAnswer = patternAnswer.matcher(newQuestion);
                        if (matcherAnswer.find()) {
                            formatError = false;
                        } else {
                            System.out.println("Error in the format of the exercise. Please mark the answer by two sharps in the question.");
                        }
                    }
                    writer.write(newQuestion);
                } else if (secondChoice.equalsIgnoreCase("y")) {
                    continue;
                }
            } else {
                writer.write(line);
            }
            writer.newLine();
        }
        reader.close();
        writer.close();
        if (exoDatabase.delete() && tempFile.renameTo(exoDatabase)) {
            System.out.println("Your modifications have been saved.");
        } else {
            System.out.println("Your modifications could not be saved. Please try again later.");
        }
    }
}
