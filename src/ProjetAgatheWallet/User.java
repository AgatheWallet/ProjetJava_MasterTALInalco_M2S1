package ProjetAgatheWallet;

import java.util.List;
import java.util.Scanner;

public class User {
    private String id;
    private String name;
    private String password;
    private Role role;
    protected Database db;
    private Language language;
    private Level lvl;
    private float progress;

    public User(String[] userInfo){
        this.id = userInfo[0];
        this.name = userInfo[1];
        this.password = userInfo[2];
        this.role = Role.valueOf(userInfo[3]);
        this.language = Language.valueOf(userInfo[4]);
        this.lvl = Level.valueOf(userInfo[5]);
        this.progress = Float.parseFloat((userInfo[6]));
    }

    public String[] getUser() {
        return new String[] {id, name, password, String.valueOf(role), String.valueOf(language), String.valueOf(lvl), String.valueOf(progress)};
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

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Level getLevel() {
        return lvl;
    }

    public void setLevel(Level newLvl) {
        this.lvl = newLvl;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float newPoints) {
        this.progress = newPoints;
    }

    public void updateProfile() {
        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;

        while (continueRunning) {
            System.out.println("Which information would you like to update?");
            System.out.println("1. Update id");
            System.out.println("2. Update name");
            System.out.println("3. Change password");
            System.out.println("4. Exit");

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
                case 4:
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}