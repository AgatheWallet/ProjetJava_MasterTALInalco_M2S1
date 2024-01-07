package ProjetAgatheWallet;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Cette classe gère les niveaux d'apprentissage des utilisateurs
 */
public class LevelManager {
    private Map<Level, Float> levelUpdateScore = new HashMap<>();
    private File levelUpgrade;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Constructeur de la classe LevelManager initialisant les scores de passage de niveau à partir
     * d'un fichier CSV
     * @param language correspond à la langue pour laquelle les niveaux sont gérés
     * @throws IOException si une erreur d'entrée ou de sortie ce produit au moment de la lecture
     * du fichier CSV
     */
    public LevelManager(Language language) throws IOException {
        String path = "src/ProjetAgatheWallet/" + language + "/LevelUpgrade.csv";
        this.levelUpgrade = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(levelUpgrade));
        String separator = "\t";
        String line;

        // Lecture du fichier CSV pour initialiser les scores de passage de niveau
        while ((line = reader.readLine()) != null) {
            String[] infos = line.split(separator);
            levelUpdateScore.put(Level.valueOf(infos[0]), Float.parseFloat(infos[1]));
        }
    }

    /**
     * Renvoie le score requis pour passer au niveau suivant.
     * @param lvl correspond au niveau actuel de l'utilisateur.
     * @return le score requis pour passer au niveau suivant.
     */
    public float getLevelUpdateScore(Level lvl) {
        return this.levelUpdateScore.get(lvl);
    }

    /**
     * Permet de définir le nouveau score requis pour passer au niveau suivant.
     * @param lvl le niveau pour lequel on souhaite modifier le score de passage
     * @throws IOException si une erreur d'entrée/sortie se produit se produit au moment
     * de la lecture et de la réécriture du fichier.
     */
    public void setScoreToUpdateLevel(Level lvl) throws IOException{
        System.out.println();
        System.out.println("Please choose the score a student must get to pass from the " + lvl + " level to the next one: ");
        float score = scanner.nextFloat();
        scanner.nextLine();
        levelUpdateScore.put(lvl, score);

        // Création d'un fichier temporaire pour y stocker les modifications
        File tempFile = new File("tempfile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(levelUpgrade));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String line;

        // Lecture du fichier original et écriture des modifications dans le fichier temporaire
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(String.valueOf(lvl))) {
                writer.write(lvl + "\t" + score);
            } else {
                writer.write(line);
            }
            writer.newLine();
        }

        // Fermeture du lecteur et de l'écrivain
        reader.close();
        writer.close();

        // Suppression fichier original et renommage du fichier temporaire avec le nom du fichier original
        if (levelUpgrade.delete() && tempFile.renameTo(levelUpgrade)) {
            System.out.println("Your modifications have been saved.");
        } else {
            System.out.println("Your modifications could not be saved. Please try again later.");
        }
    }

    /**
     * Permet de faire passer l'utilisateur au niveau suivant.
     * @param user l'utilisateur dont le niveau doit être mis à jour.
     */
    public void getNextLevel(User user) {
        Level userLvl = user.getLevel();
        if (userLvl.equals(Level.BEGINNER)) {
            user.setLevel(Level.INTERMEDIATE);
        } else if (userLvl.equals(Level.INTERMEDIATE)) {
            user.setLevel(Level.ADVANCED);
        } else {
            System.out.println("You have aced the last level. Congratulations on completing the course!");
        }
    }
}
