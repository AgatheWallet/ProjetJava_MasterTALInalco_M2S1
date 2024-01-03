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

public class Exercise {
    private List<String> questions;
    private List<String> answers;
    private File exercisesDatabase;

    public Exercise(Language language, Level level) throws IOException {
        String path = "src/ProjetAgatheWallet/" + language + "/" + level + ".txt";
        this.exercisesDatabase = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(this.exercisesDatabase));
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        String line;
        Pattern pattern = Pattern.compile("([^#]*?)#([^#]*?)#([^#]*)");
        while ((line = br.readLine()) != null) {
            Matcher matchPattern = pattern.matcher(line);
            String question = null;
            String answer = null;
            if (matchPattern.find()) {
                question = matchPattern.group(1) + "_________" + matchPattern.group(3);
                answer = matchPattern.group(2);
            }
            this.questions.add(question);
            this.answers.add(answer);
        }
    }

    private String generateAnswers() {
        StringBuilder answers = new StringBuilder();
        List<String> answerList = new ArrayList<>(this.answers);
        Collections.shuffle(answerList);
        for (String a : answerList) {
            answers.append(a).append(", ");
        }
        // Suppression de la dernière virgule et de l'espace qui la suit
        answers = new StringBuilder(answers.substring(0, answers.length() - 2));
        return answers.toString();
    }

    public int getNumberofQuestion() {
        return questions.size();
    }
    public void generate() {
        String answers = generateAnswers();
        System.out.println("Please choose among these answers: " + answers);
        int compteur = 1;
        for (String q : this.questions) {
            System.out.println(compteur + ". " + q);
            compteur ++;
        }
    }

    public float evaluate(List<String> userAnswers) {
        float pointsPerQuestion = (float) 20 / userAnswers.size();
        float scoreUser = 0;
        for (int i = 0; i < userAnswers.size(); i++) {
            String userAns = userAnswers.get(i);
            String correctAns = this.answers.get(i);
            System.out.println(userAns + " // " + correctAns);
            if (userAns.equals(correctAns)) {
                scoreUser = scoreUser + pointsPerQuestion;
            }
        }
        DecimalFormat scoreFormat = new DecimalFormat("#.##");
        float formattedScore = Float.parseFloat(scoreFormat.format(scoreUser));
        System.out.println(formattedScore);
        return formattedScore;
    }
}
