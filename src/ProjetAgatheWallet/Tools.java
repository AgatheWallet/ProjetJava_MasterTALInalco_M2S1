package ProjetAgatheWallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {
    public Tools(){}
    public String mapToString(Map<Level, List<Float>> mapProgress) {
        StringBuilder stringProgress = new StringBuilder();
        for (Map.Entry<Level, List<Float>> entry : mapProgress.entrySet()) {
            stringProgress.append(String.valueOf(entry.getKey())).append(": [");
            for (float score : entry.getValue()) {
                stringProgress.append(score).append(", ");
            }
            stringProgress = new StringBuilder(stringProgress.substring(0, stringProgress.length() - 2));
            stringProgress.append("]; ");
        }
        stringProgress = new StringBuilder(stringProgress.substring(0, stringProgress.length() - 2));
        return stringProgress.toString();
    }

    public Map<Level, List<Float>> stringToMap(String stringProgress) {
        Map<Level, List<Float>> mapProgress = new HashMap<>();

        String[] entries = stringProgress.split("; ");
        for (String entry : entries) {
            String[] parts = entry.split(": \\[");
            Level level = Level.valueOf(parts[0]);

            String scoresString = parts[1].substring(0, parts[1].length() - 1);
            String[] scoresArray = scoresString.split(", ");

            List<Float> scoresList = new ArrayList<>();
            for (String score : scoresArray) {
                scoresList.add(Float.parseFloat(score));
            }

            mapProgress.put(level, scoresList);
        }

        return mapProgress;
    }
}
