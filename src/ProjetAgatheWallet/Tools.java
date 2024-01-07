package ProjetAgatheWallet;

import java.util.*;

/**
 * Cette classe permet de gérer la Map utilisée pour détailler le progrès des étudiants
 */
public class Tools {
    public Tools(){}

    /**
     * Convertit une Map en String pour pouvoir l'écrire au sein de notre base de données CSV
     * @param mapProgress correspond à notre Map qui permet de tracker les progrès de chaque étudiant
     *                    pour un niveau donné. On a donc le niveau en clé et une liste contenant
     *                    les différents scores obtenus par l'étudiant pour le dit niveau.
     * @return une chaîne de caractères qui représente la Map
     */
    public String mapToString(Map<Level, List<Float>> mapProgress) {
        StringBuilder stringProgress = new StringBuilder();

        // Récupère chaque entrée de la Map
        for (Map.Entry<Level, List<Float>> entry : mapProgress.entrySet()) {

            // Ajoute le niveau à la String et ajoute ": [" pour pouvoir ouvrir la liste des scores
            stringProgress.append(String.valueOf(entry.getKey())).append(": [");

            // Itère parmi les scores présents dans la liste de Float et les ajoute à la chaîne de
            // caractères, suivis d'une virgule pour les séparer les uns des autres.
            for (float score : entry.getValue()) {
                stringProgress.append(score).append(", ");
            }

            // On retire la dernière virgule suivie d'un espace et on ferme la liste des scores
            stringProgress = new StringBuilder(stringProgress.substring(0, stringProgress.length() - 2));
            stringProgress.append("]; ");
        }

        // On retire le dernier point virgule et l'espace qui le suit
        stringProgress = new StringBuilder(stringProgress.substring(0, stringProgress.length() - 2));
        return stringProgress.toString();
    }

    /**
     * Permet de faire l'inverse de la fonction du dessus, c'est-à-dire de récupérer à partir de
     * la chaîne de caractères présente dans la base de données la Map correspondantes.
     * @param stringProgress correspond à la chaîne de caractères correspondant à la Map dans la base de données
     * @return une Map ayant comme clé un niveau et comme valeur une liste de Float, correspond à la liste des
     *          scores otenus
     */
    public Map<Level, List<Float>> stringToMap(String stringProgress) {
        Map<Level, List<Float>> mapProgress = new LinkedHashMap<>();

        // On sépare la chaîne de caractères au niveau du point virgule pour récupérer chaque entry
        String[] entries = stringProgress.split("; ");

        // On itère sur chaque entry
        for (String entry : entries) {

            // On découpe pour récupérer d'un côté le niveau et de l'autre la liste des scores
            String[] parts = entry.split(": \\[");
            Level level = Level.valueOf(parts[0]);

            // On retire le crochet fermant de la liste puis on la split au niveau de la virgule + espace
            // pour obtenir une liste des scores
            String scoresString = parts[1].substring(0, parts[1].length() - 1);
            String[] scoresArray = scoresString.split(", ");

            // On convertit la liste de string en une Array list de floats
            List<Float> scoresList = new ArrayList<>();
            for (String score : scoresArray) {
                scoresList.add(Float.parseFloat(score));
            }

            // On ajoute le niveau et la liste de scores à la Map
            mapProgress.put(level, scoresList);
        }

        return mapProgress;
    }
}
