package ProjetAgatheWallet;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gère la création et modification d'exercices
 */
public class ExercisesManager {
    private static Scanner scanner = new Scanner(System.in);
    private Language language;

    /**
     * Constructeur du gestionnaire d'exercices
     * @param language correspond à la langue des exercices gérés
     */
    public ExercisesManager(Language language) {
        this.language = language;
    }

    /**
     * Permet de créer un nouvel exercice
     * @param lvl correspond au niveau pour lequel on veut créer un exercice
     */
    public void createNewExercise(Level lvl) {
        String filePath = "src/ProjetAgatheWallet/" + language + "/" + lvl + ".txt";
        File exoDatabase = new File(filePath);

        // On indique les instructions pour le format de l'exercice à entrer
        System.out.println("To enter a new gap-fill exercise, please enter the sentence using the sharp character (#) to mark the part to guess.");
        System.out.println("Ex: He #loves doing# the house chores. → Here, \"loves doing\" will be replaced by a blank in the exercise.");

        String newQuestion="";
        boolean formatError = true;
        while (formatError) {
            System.out.println("Please enter the desired exercise: ");
            newQuestion = scanner.nextLine();

            // Le pattern reconnaît un exercice bien formé
            Pattern patternAnswer = Pattern.compile("#.*?#");
            Matcher matcherAnswer = patternAnswer.matcher(newQuestion);

            // On vérifie si l'exercice est bien formé
            if (matcherAnswer.find()) {
                formatError=false;
            } else {
                System.out.println("Error in the format of the exercise. Please mark the answer by two sharps in the question.");
            }
        }

        // On ajoute l'exercice aux éventuels exercices pré-existants pour ce niveau
        try (FileWriter writer = new FileWriter(exoDatabase, true)) {
            writer.write(newQuestion + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error while writing in the file: " + e.getMessage());
        }
    }

    /**
     * Permet de modifier un exercice existant.
     * @param lvl correspond au niveau de l'exercice à modifier.
     * @throws IOException si une erreur d'entrée/sortie se produit dans la lecture ou l'écriture d'un fichier
     */
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

                        // On vérifie le format de l'exercice modifié
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

        // Suppression du fichier original et renommage du fichier temporaire avec le nom du fichier original
        if (exoDatabase.delete() && tempFile.renameTo(exoDatabase)) {
            System.out.println("Your modifications have been saved.");
        } else {
            System.out.println("Your modifications could not be saved. Please try again later.");
        }
    }
}
