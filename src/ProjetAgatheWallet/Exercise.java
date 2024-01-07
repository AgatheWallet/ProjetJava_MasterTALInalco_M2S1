package ProjetAgatheWallet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.*;

/**
 * Représente un exercice
 */
public class Exercise {
    private List<String> questions;
    private List<String> answers;
    private List<String> questionsAnswers;
    private File exercisesDatabase;

    /**
     * Constructeur de la classe Exercice initialisant les questions et réponses de l'exercice
     * @param language corresond à la langue de l'exercice
     * @param level correspond au niveau de l'exercice
     * @throws IOException si une erreur d'entrée et/ou de sortie se produit au moment de la lecture
     *                     du fichier d'exercice
     */
    public Exercise(Language language, Level level) throws IOException {

        // Chaque exercice se trouve dans un répertoire dont le nom est celui de la langue.
        // Le nom de l'exercice correspond à son niveau
        String path = "src/ProjetAgatheWallet/" + language + "/" + level + ".txt";
        this.exercisesDatabase = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(this.exercisesDatabase));
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.questionsAnswers = new ArrayList<>();
        String line;

        // Ce pattern permet de récupérer la question et la réponse : la question correspond aux groupes
        // 1 et 3 et la réponse au groupe 2
        Pattern patternQA = Pattern.compile("([^#]*?)#([^#]*?)#([^#]*)");
        while ((line = br.readLine()) != null) {
            questionsAnswers.add(line);
            Matcher matchPatternQA = patternQA.matcher(line);
            String question = "";
            String answer = "";
            // Si on trouve le pattern distinguant question et réponse, on affecte la question à la chaîne
            // de caractères correspondante en remplaçant la réponse par des underscores pour faire un blank.
            // La réponse est également affectée à la variable answer.
            if (matchPatternQA.find()) {
                question = matchPatternQA.group(1) + "_________" + matchPatternQA.group(3);
                answer = matchPatternQA.group(2);
            }
            // On ajoute la question et la réponse aux listes correspondantes.
            this.questions.add(question);
            this.answers.add(answer);
        }
    }

    /**
     * Renvoie la liste des questions/réponses de l'exercice
     * @return la liste des questions/réponses de l'exercice
     */
    public List<String> getExercise() {
        return this.questionsAnswers;
    }

    /**
     * Génère une liste de réponses mélangées pour l'exercice.
     * @return une chaîne de caractères contenant les réponses dans un ordre aléatoire
     */
    private String generateAnswers() {
        StringBuilder answers = new StringBuilder();
        List<String> answerList = new ArrayList<>(this.answers);

        // Mélange la liste des réponses
        Collections.shuffle(answerList);
        for (String a : answerList) {
            answers.append(a).append(", ");
        }
        // Suppression de la dernière virgule et de l'espace qui la suit
        answers = new StringBuilder(answers.substring(0, answers.length() - 2));
        return answers.toString();
    }

    /**
     * Renvoie le nombre de questions dans l'exercice.
     * @return le nombre de questions dans l'exercice.
     */
    public int getNumberofQuestion() {
        return questions.size();
    }

    /**
     * Génère et affiche l'exercice avec les réponses mélangées. Les questions sont elles dans l'ordre.
     */
    public void generate() {
        String answers = generateAnswers();
        System.out.println("Please choose among these answers: " + answers);
        int compteur = 1;
        for (String q : this.questions) {
            System.out.println(compteur + ". " + q);
            compteur ++;
        }
    }


    /**
     * Évalue les réponses fournies par l'utilisateur et calcule le score obtenu.
     * @param userAnswers correspond à la liste des réponses données par l'utilisateur
     * @return le score obtenu.
     */
    public float evaluate(List<String> userAnswers) {

        // On calcule le nombre de points par question
        float pointsPerQuestion = (float) 20 / userAnswers.size();

        // Initialisation du score obtenu par l'utilisateur
        float scoreUser = 0;

        // On affiche les réponses pour que l'utilisateur ait la correction et on calcule le
        // score obtenu
        System.out.println("Answers:");
        for (int i = 0; i < userAnswers.size(); i++) {
            String userAns = userAnswers.get(i);
            String correctAns = this.answers.get(i);
            System.out.println(correctAns);
            if (userAns.equals(correctAns)) {
                scoreUser = scoreUser + pointsPerQuestion;
            }
        }
        // On ne garde que 2 chiffres après la virgule
        DecimalFormat scoreFormat = new DecimalFormat("#.##");
        float formattedScore = Float.parseFloat(scoreFormat.format(scoreUser));

        // On affiche le résultat obtenu
        System.out.println(formattedScore);
        return formattedScore;
    }
}
