package ProjetAgatheWallet;

import java.io.IOException;

/**
 * Cette classe est celle qui doit être exécutée pour lancer l'application.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // On affiche l'écran de présentation de l'application
        UserInterface userInterface = new UserInterface();

        // On affiche l'écran de connection/d'inscription et on récupère l'utilisateur
        User currentUser = userInterface.logInSignIn();

        // Si c'est un étudiant, on affiche le main menu correspondant
        if (currentUser.getRole().equals(Role.STUDENT)) {
            userInterface.displayMainMenuStudent();
        }

        // Si c'est un énseignant, on affiche le main menu corresondant.
        else if (currentUser.getRole().equals(Role.TEACHER)) {
            userInterface.displayMainMenuTeacher();
        }
    }
}
