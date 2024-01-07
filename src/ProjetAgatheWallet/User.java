package ProjetAgatheWallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Cette classe correspond à un utilisateur de l'application
 */
public class User {
    private String id;
    private String name;
    private String password;
    private Role role;
    private Language language;
    private Level lvl;
    private Map<Level, List<Float>> progress;
    private Tools tool = new Tools();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Constructeur de la classe User qui initialise les informations de l'utilisateur à partir d'un tableau de strings.
     * @param userInfo correspond au tableau contenant les informations de l'utilisateur
     */
    public User(String[] userInfo) {
        this.id = userInfo[0];
        this.name = userInfo[1];
        this.password = userInfo[2];
        this.role = Role.valueOf(userInfo[3]);
        this.language = Language.valueOf(userInfo[4]);
        this.lvl = Level.valueOf(userInfo[5]);
        this.progress = tool.stringToMap(userInfo[6]);
    }

    /**
     * Permet de récupérer les informations de l'utilisateur sous la forme d'un tableau de strings
     * @return un tableau contenant les informations de l'utilisateur
     */
    public String[] getUser() {
        return new String[] {id, name, password, String.valueOf(role), String.valueOf(language), String.valueOf(lvl), tool.mapToString(progress)};
    }

    /**
     * Permet d'afficher le profil de l'utilisateur.
     * Le niveau n'est pas affiché car cela correspond au progrès pour les étudiants et n'est pas
     * intéressant pour les enseignants. De même pour le progrès.
     * Le mot de passe n'est pas affiché car confidentiel.
     */
    public void getUserProfile(){
        System.out.println("User's id: " + this.id);
        System.out.println("User's name: " + this.name);
        System.out.println("User's role: " + this.role);
        System.out.println("User's language: " + this.language);
    }

    /**
     * Retourne le nom de l'utilisateur
     * @return le nom de l'utilisateur sous forme de String
     */
    public String getName() {
        return name;
    }

    /**
     * Permet de modifier le nom de l'utilisateur
     * @param newName le nouveau nom de l'utilisateur
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Permet de récupérer l'identifiant de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public String getId() {
        return id;
    }

    /**
     * Permet de modifier l'identifiant de l'utilisateur
     * @param newId correspond au nouvel identifiant choisi par l'utilisateur
     */
    public void setId(String newId) {
        this.id = newId;
    }

    /**
     * Permet de récupérer le mot de passe de l'utilisateur
     * @return le mot de passe de l'utilisateur
     */
    public String getPassword() {
        return password;
    }

    /**
     * Permet de modifier le mot de passe de l'utilisateur
     * @param newPassword le nouveau mot de passe choisi par l'utilisateur
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Permet de récupérer le rôle de l'utilisateur, c'est-à-dire soit étudiant, soit enseignant
     * @return le rôle de l'utilisateur
     */
    public Role getRole() {
        return role;
    }

    /**
     * Permet de récupérer la langue étudiée/enseignée par l'utilisateur
     * @return la langue de l'utilisateur
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Permet de récupérer le niveau de l'utilisateur
     * (beginner, intermediate, advanced ou expert pour les enseignants)
     * @return le niveau de l'utilisateur
     */
    public Level getLevel() {
        return lvl;
    }

    /**
     * Permet de récupérer la progression de l'utilisateur
     * @return la progression de l'utilisateur sous forme de Map avec
     *         le niveau en clé et la liste des scores correspondants en valeur.
     */
    public Map<Level, List<Float>> getProgress() {
        return progress;
    }

    /**
     * Ajoute le score obtenu à un exercice à la liste des scores obtenus pour le niveau de l'exercice
     * @param exerciseLvl permet de savoir pour quel niveau il faut ajouter le score, si un intermédiaire
     *                    ou avancé fait un exercice plus simple que son niveau actuel
     * @param newScore correspond au nouveau score obtenu qu'il faut ajouter
     */
    public void addScore(Level exerciseLvl, float newScore) {
        List<Float> scoreList = progress.get(exerciseLvl);
        scoreList.add(newScore);
    }

    /**
     * Met à jour le niveau et indique que le score correspondant à ce nouveau niveau est 0.
     * @param newLevel le nouveau niveau atteint par l'utilisateur
     * @return le score actuel de l'utilisateur.
     */
    public void setLevel(Level newLevel) {
        this.lvl = newLevel;
        List<Float> newScoreList = new ArrayList<>();
        newScoreList.add((float) 0.0);
        progress.put(newLevel, newScoreList);
    }

    /**
     * Renvoie le score actuel de l'utilisateur pour son niveau actuel.
     * @return le score actuel de l'utilisateur.
     */
    public float getCurrentScore() {
        List<Float> scoreList = progress.get(lvl);
        return scoreList.get(scoreList.size() - 1);
    }

    /**
     * Permet de mettre à jour les informations de l'utilisateur modifiables (id, name et password)
     * @throws IOException imposée par Database si une erreur d'entrée/sortie se produit
     */
    public void updateProfile() throws IOException {
        Database db = new Database();
        System.out.println("Which information would you like to update?");
        System.out.println("1. Update id");
        System.out.println("2. Update name");
        System.out.println("3. Change password");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                String newId="";
                boolean existingID = true;
                while (existingID) {
                    System.out.println("What is your new id/username?");
                    newId = scanner.nextLine();
                    List<String> idList = db.getIdList();

                    // Vérifie que l'id n'est pas déjà utilisé
                    if (idList.contains(newId)) {
                        System.out.println(idList);
                        System.out.println("This id is already taken, please choose another one.");
                    } else {
                        existingID = false;
                    }
                }
                setId(newId);
                break;
            case 2:
                System.out.println("What is your name?");
                String newName = scanner.nextLine();
                setName(newName);
                break;
            case 3:
                System.out.println("What is your new password?");
                String newPassword = scanner.nextLine();
                setPassword(newPassword);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}
