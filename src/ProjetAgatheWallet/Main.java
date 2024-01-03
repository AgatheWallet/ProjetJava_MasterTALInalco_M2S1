package ProjetAgatheWallet;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        UserInterface userInterface = new UserInterface();
        Database userDatabase = new Database();
        User currentUser = userInterface.logInSignIn();
        if (currentUser.getRole().equals(Role.STUDENT)) {
            userInterface.displayMainMenuStudent();
        } else if (currentUser.getRole().equals(Role.TEACHER)) {
            userInterface.displayMainMenuTeacher();
        }
    }
}
