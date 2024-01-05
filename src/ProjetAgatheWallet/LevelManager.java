package ProjetAgatheWallet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LevelManager {
    private Map<Level, Float> levelUpdateScore = new HashMap<>();
    private File levelUpgrade;
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

    public Map<Level, Float> getLevelUpdateScore() {
        return this.levelUpdateScore;
    }

    public void setLevelUpdateScore(Level lvl, float newScore) {
        levelUpdateScore.put(lvl, newScore);

    }
}
