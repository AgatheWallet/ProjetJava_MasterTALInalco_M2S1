package ProjetAgatheWallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class User {
    private String id;
    private String name;
    private String password;
    private Role role;
    protected Database db;
    private Language language;
    private Level lvl;
    private Map<Level, List<Float>> progress;
    private Tools tool = new Tools();

    public User(String[] userInfo){
        this.id = userInfo[0];
        this.name = userInfo[1];
        this.password = userInfo[2];
        this.role = Role.valueOf(userInfo[3]);
        this.language = Language.valueOf(userInfo[4]);
        this.lvl = Level.valueOf(userInfo[5]);
        this.progress = tool.stringToMap(userInfo[6]);
    }

    public String[] getUser() {
        return new String[] {id, name, password, String.valueOf(role), String.valueOf(language), String.valueOf(lvl), tool.mapToString(progress)};
    }
    public void getUserProfile(){
        System.out.println("User's id: " + this.id);
        System.out.println("User's name: " + this.name);
        System.out.println("User's role: " + this.role);
        System.out.println("User's language: " + this.language);
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getId() {
        return id;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public Language getLanguage() {
        return language;
    }

    public Level getLevel() {
        return lvl;
    }

    public Map<Level, List<Float>> getProgress() {
        return progress;
    }

    public void addScore(Level exerciseLvl, float newScore) {
        List<Float> scoreList = progress.get(exerciseLvl);
        scoreList.add(newScore);
    }

    public void setLevel(Level newLevel) {
        this.lvl = newLevel;
        List<Float> newScoreList = new ArrayList<>();
        newScoreList.add((float) 0.0);
        progress.put(newLevel, newScoreList);
    }

    public float getCurrentScore() {
        List<Float> scoreList = progress.get(lvl);
        return scoreList.get(scoreList.size() - 1);
    }

    public void updateProfile() {
        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;

        while (continueRunning) {
            System.out.println("Which information would you like to update?");
            System.out.println("1. Update id");
            System.out.println("2. Update name");
            System.out.println("3. Change password");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("What is your new id/username?");
                    String newId = scanner.nextLine();
                    List<String> idList = db.getIdList();
                    boolean existingID = true;
                    while (existingID) {
                        if (!idList.contains(id)) {
                            setId(newId);
                            existingID = false;
                        } else {
                            System.out.println("This id is already taken, please choose another one.");
                        }
                    }
                    continueRunning = false;
                    break;
                case 2:
                    System.out.println("What is your name?");
                    String newName = scanner.nextLine();
                    setName(newName);
                    continueRunning = false;
                    break;
                case 3:
                    System.out.println("What is your new password?");
                    String newPassword = scanner.nextLine();
                    setPassword(newPassword);
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
