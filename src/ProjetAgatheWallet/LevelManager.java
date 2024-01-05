package ProjetAgatheWallet;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LevelManager {
    private Map<Level, Float> levelUpdateScore = new HashMap<>();
    private File levelUpgrade;
    private static Scanner scanner = new Scanner(System.in);

    public LevelManager(Language language) throws IOException {
        String path = "src/ProjetAgatheWallet/" + language + "/LevelUpgrade.csv";
        this.levelUpgrade = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(levelUpgrade));
        String separator = "\t";
        String line;

        while ((line = reader.readLine()) != null) {
            String[] infos = line.split(separator);
            levelUpdateScore.put(Level.valueOf(infos[0]), Float.parseFloat(infos[1]));
        }
    }

    public float getLevelUpdateScore(Level lvl) {
        return this.levelUpdateScore.get(lvl);
    }

    public void setScoreToUpdateLevel(Level lvl) throws IOException{
        System.out.println();
        System.out.println("Please choose the score a student must get to pass from the " + lvl + " level to the next one: ");
        float score = scanner.nextFloat();
        scanner.nextLine();
        levelUpdateScore.put(lvl, score);
        File tempFile = new File("tempfile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(levelUpgrade));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith(String.valueOf(lvl))) {
                writer.write(lvl + "\t" + score);
            } else {
                writer.write(line);
            }
            writer.newLine();
        }
        reader.close();
        writer.close();
        if (levelUpgrade.delete() && tempFile.renameTo(levelUpgrade)) {
            System.out.println("Your modifications have been saved.");
        } else {
            System.out.println("Your modifications could not be saved. Please try again later.");
        }
    }

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
