package ProjetAgatheWallet;

import java.io.IOException;
import java.util.*;


/**
 * Cette classe gère l'interface utilisateur. C'est via cette classe que toutes les grandes opérations
 * vont avoir lieu
 */
public class UserInterface {
    private User currentUser;
    private Database usersDatabase = new Database();
    private List<String> idList = usersDatabase.getIdList();
    private List<User> usersInfos = usersDatabase.getUsersInfo();
    private static Scanner scanner = new Scanner(System.in);
    private Tools tools = new Tools();


    /**
     * Constructeur de la classe UserInterface.
     * Affiche un message de bienvenue et les options pour se connecter ou créer un compte.
     * @throws IOException si une erreur d'entrée/sortie se produit,
     * notamment au niveau de l'utilisation de la base de données
     */
    public UserInterface() throws IOException {
        System.out.println();
        System.out.println("Welcome on our learning app.");
        System.out.println("We offer courses for 3 languages : French, English and Korean.");
        System.out.println("If you already have an account, please login.");
        System.out.println("If you don't, please create an account.");
        System.out.println();
    }

    /**
     * Gère le processus de connexion ou de création de compte de l'utilisateur.
     * @return L'utilisateur connecté ou créé.
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'utilisation de
     * la base de données
     */
    public User logInSignIn() throws IOException {
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println();
            System.out.println("What do you want to do? Please type the corresponding number.");
            System.out.println("1. Login");
            System.out.println("2. Create an account");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Nettoyer le buffer

            if (choice == 1) {
                // Connexion
                System.out.println("Enter your id:");
                String id = scanner.nextLine();

                System.out.println("Enter your password:");
                String password = scanner.nextLine();

                if (idList.contains(id)) {
                    for (User user : usersInfos) {
                        if (user.getId().equals(id) && user.getPassword().equals(password)) {
                            currentUser = user;
                            continueRunning = false;
                        } else if (user.getId().equals(id) && ! user.getPassword().equals(password)) {
                            System.out.println("Id or/and password wrong. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Id or/and password wrong. Please try again.");
                }
            } else if (choice == 2) {
                // Création d'un nouveau compte
                String idUser = "";
                boolean alreadyExists = true;
                while (alreadyExists) {
                    System.out.println();
                    System.out.println("Choose an id:");
                    idUser = scanner.nextLine();
                    // Vérification que l'Id n'existe pas
                    if (!idList.contains(idUser)) {
                        alreadyExists = false;
                    } else {
                        System.out.println("This id already exists, please choose another one.");
                    }
                }

                System.out.println("Enter your name:");
                String nameUser = scanner.nextLine();

                System.out.println("Choose a password:");
                String passwordUser = scanner.nextLine();

                Role roleUser = null;
                Language languageUser = null;
                Level lvlUser = null;
                Map<Level, List<Float>> progressUser = new HashMap<>();

                System.out.println("Are you :");
                System.out.println("1. A student");
                System.out.println("2. A teacher");

                int secondChoice = scanner.nextInt();
                scanner.nextLine();
                boolean wrongChoice = true;

                while (wrongChoice) {
                    if (secondChoice == 1) {
                        // Pour un étudiant
                        roleUser = Role.STUDENT;
                        System.out.println();
                        System.out.println("Which language do you want to learn?");
                        System.out.println("1. French");
                        System.out.println("2. English");
                        System.out.println("3. Korean");
                        int thirdChoice = scanner.nextInt();
                        scanner.nextLine();
                        boolean wrongChoice2 = true;
                        while(wrongChoice2){
                            if (thirdChoice == 1) {
                                languageUser = Language.FRENCH;
                                wrongChoice2 = false;
                            } else if (thirdChoice == 2) {
                                languageUser = Language.ENGLISH;
                                wrongChoice2 = false;
                            } else if (thirdChoice == 3) {
                                languageUser = Language.KOREAN;
                                wrongChoice2 = false;
                            }
                            else {
                                System.out.println("Invalid choice. Please try again.");
                            }
                        }
                        System.out.println();
                        System.out.println("What is your level:");
                        System.out.println("1. Beginner");
                        System.out.println("2. Intermediate");
                        System.out.println("3. Advanced");
                        int fourthChoice = scanner.nextInt();
                        scanner.nextLine();
                        boolean wrongChoice3 = true;
                        while(wrongChoice3){
                            if (fourthChoice == 1) {
                                lvlUser = Level.BEGINNER;
                                wrongChoice3 = false;
                            } else if (fourthChoice == 2) {
                                lvlUser = Level.INTERMEDIATE;
                                wrongChoice3 = false;
                            } else if (fourthChoice == 3) {
                                lvlUser = Level.ADVANCED;
                                wrongChoice3 = false;
                            }
                            else {
                                System.out.println("Invalid choice. Please try again.");
                            }
                        }

                        List<Float> scores = new ArrayList<>();
                        scores.add((float) 0);
                        progressUser.put(lvlUser, scores);
                        wrongChoice = false;
                    } else if (secondChoice == 2) {
                        // Pour un enseignant
                        roleUser = Role.TEACHER;
                        lvlUser = Level.EXPERT;
                        List<Float> scores = new ArrayList<>();
                        scores.add((float) 0);
                        progressUser.put(lvlUser, scores);
                        System.out.println();
                        System.out.println("Which language are you teaching:");
                        System.out.println("1. French");
                        System.out.println("2. English");
                        System.out.println("3. Korean");
                        int thirdChoice = scanner.nextInt();
                        scanner.nextLine();
                        boolean wrongChoice2 = true;
                        while(wrongChoice2){
                            if (thirdChoice == 1) {
                                languageUser = Language.FRENCH;
                                wrongChoice2 = false;
                            } else if (thirdChoice == 2) {
                                languageUser = Language.ENGLISH;
                                wrongChoice2 = false;
                            } else if (thirdChoice == 3) {
                                languageUser = Language.KOREAN;
                                wrongChoice2 = false;
                            }
                            else {
                                System.out.println("Invalid choice. Please try again.");
                            }
                        }
                        wrongChoice = false;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }

                // Création de l'utilisateur
                String[] userData = {idUser, nameUser, passwordUser, String.valueOf(roleUser), String.valueOf(languageUser), String.valueOf(lvlUser), tools.mapToString(progressUser)};
                currentUser = new User(userData);
                usersDatabase.addUser(userData);
                continueRunning = false;
            } else {
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println();
        System.out.println("Welcome (back) " + currentUser.getName());
        // L'utilisateur est connecté
        return currentUser;
    }

    /**
     * Affiche le menu principal des étudiants et gère les différentes options
     * @throws IOException si il y a une erreur au moment de la manipulation des fichiers utilisés
     */
    public void displayMainMenuStudent() throws IOException {
        LevelManager lvlMan = new LevelManager(currentUser.getLanguage());
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println();
            System.out.println("What do you want to do today? Please type the corresponding number.");
            System.out.println("1. Do an exercise");
            System.out.println("2. See my progress");
            System.out.println("3. See my profile");
            System.out.println("4. Update my profile");
            System.out.println("5. Logout");
            System.out.println("6. Delete my account");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    // Faire un exercice
                    Level exerciseLevel = currentUser.getLevel();

                    // Si l'étudiant est au niveau intermédiaire ou avancé, il peut choisir un niveau inférieur
                    if (currentUser.getLevel().equals(Level.INTERMEDIATE)) {
                        System.out.println();
                        System.out.println("Please choose the desired level for the exercise by typing the corresponding number:");
                        System.out.println("1. Beginner");
                        System.out.println("2. Intermediate");
                        int levelNb = scanner.nextInt();
                        scanner.nextLine();
                        boolean wrongChoice = true;
                        while (wrongChoice) {
                            if (levelNb == 1) {
                                exerciseLevel = Level.BEGINNER;
                                wrongChoice = false;
                            } else if (levelNb == 2) {
                                exerciseLevel = Level.INTERMEDIATE;
                                wrongChoice = false;
                            } else {
                                System.out.println();
                                System.out.println("There is no option corresponding to this number. Please try again.");
                            }
                        }
                    } else if (currentUser.getLevel().equals(Level.ADVANCED)) {
                        boolean wrongChoice = true;
                        while (wrongChoice) {
                            System.out.println();
                            System.out.println("Please choose the desired level for the exercise by typing the corresponding number:");
                            System.out.println("1. Beginner");
                            System.out.println("2. Intermediate");
                            System.out.println("3. Advanced");
                            int levelNb = scanner.nextInt();
                            scanner.nextLine();
                            if (levelNb == 1) {
                                exerciseLevel = Level.BEGINNER;
                                wrongChoice = false;
                            } else if (levelNb == 2) {
                                exerciseLevel = Level.INTERMEDIATE;
                                wrongChoice = false;
                            } else if (levelNb == 3) {
                                exerciseLevel = Level.ADVANCED;
                                wrongChoice = false;
                            } else {
                                System.out.println();
                                System.out.println("There is no option corresponding to this number. Please try again.");
                            }
                        }
                    } else {
                        exerciseLevel = Level.BEGINNER;
                    }

                    Exercise exo = new Exercise(currentUser.getLanguage(), exerciseLevel);
                    System.out.println();
                    // Génération de l'exercice dans la langue de l'utilisateur au niveau choisi
                    exo.generate();
                    int nbOfQuestions = exo.getNumberofQuestion();

                    // On récupère les réponses de l'utilisateur
                    List<String> answers = new ArrayList<>();
                    for (int i = 1; i <= nbOfQuestions; i++) {
                        System.out.println(i + ".");
                        answers.add(scanner.nextLine());
                    }

                    // On évalue l'exercice et on ajoute le score dans la Map Progress
                    float score = exo.evaluate(answers);
                    currentUser.addScore(exerciseLevel,score);

                    // Passage de niveau si l'étudiant a obtenu un score suffisant
                    if (exerciseLevel.equals(currentUser.getLanguage()) && score > lvlMan.getLevelUpdateScore(currentUser.getLevel())) {
                        lvlMan.getNextLevel(currentUser);
                        System.out.println("Congratulations, your level has been upgraded to " + currentUser.getLevel());
                    }

                    // On update la base de données
                    usersDatabase.updateDatabase(currentUser, currentUser.getId());
                    break;
                case 2:
                    // Voir ses progrès
                    System.out.println();
                    System.out.println("Your level is " + currentUser.getLevel() + " with a score of " + currentUser.getCurrentScore());
                    System.out.println("Here is a view of your progress: " + tools.mapToString(currentUser.getProgress()));
                    break;
                case 3:
                    // Voir son profil
                    System.out.println();
                    currentUser.getUserProfile();
                    break;
                case 4:
                    // Modifier son profil (id, nom ou mot de passe uniquement)
                    String oldID = currentUser.getId();
                    currentUser.updateProfile();
                    usersDatabase.updateDatabase(currentUser, oldID);
                    break;
                case 5:
                    // Se déconnecter
                    System.out.println();
                    System.out.println("Thank you for your visit. See you soon.");
                    currentUser = null;
                    continueRunning=false;
                    break;
                case 6:
                    // Supprimer son compte
                    System.out.println();
                    System.out.println("Are you sure you want to delete your account? Please answer by typing 'yes' or 'no'.");
                    String deleteConfirmation = scanner.nextLine();
                    if (deleteConfirmation.equalsIgnoreCase("yes")) {
                        usersDatabase.deleteAccount(currentUser);
                        System.out.println("We're sorry to see you go. We hope you enjoyed learning with us. Please come back anytime :)");
                        currentUser = null;
                        continueRunning=false;
                    } else {
                        System.out.println("Deleting account cancelled.");
                    }
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } scanner.close();
    }


    /**
     * Affiche le menu principal des enseignants et gère les différentes options
     * @throws IOException si il y a une erreur au moment de la manipulation des fichiers utilisés
     */
    public void displayMainMenuTeacher() throws IOException {
        ExercisesManager exoMan = new ExercisesManager(currentUser.getLanguage());
        LevelManager lvlMan = new LevelManager(currentUser.getLanguage());
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println();
            System.out.println("What do you want to do today?");
            System.out.println("1. Manage my exercises");
            System.out.println("2. See the students progress");
            System.out.println("3. See my profile");
            System.out.println("4. Update my profile");
            System.out.println("5. Logout");
            System.out.println("6. Delete my account");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    // Gérer les exercices

                    // Choix du niveau à modifier
                    Level lvlToModify = Level.BEGINNER;
                    boolean wrongChoice = true;
                    while (wrongChoice) {
                        System.out.println();
                        System.out.println("Choose a level: ");
                        System.out.println("1. Beginner");
                        System.out.println("2. Intermediate");
                        System.out.println("3. Advanced");
                        int levelChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (levelChoice == 1) {
                            wrongChoice = false;
                        } else if (levelChoice == 2) {
                            lvlToModify = Level.INTERMEDIATE;
                            wrongChoice = false;
                        } else if (levelChoice == 3) {
                            lvlToModify = Level.ADVANCED;
                            wrongChoice = false;
                        } else {
                            System.out.println("This option is not available, please try again.");
                        }
                    }
                    wrongChoice=true;

                    // Choix de la modification à apporter pour le niveau choisi dans la langue enseignée
                    while (wrongChoice) {
                        System.out.println();
                        System.out.println("Choose an option: ");
                        System.out.println("1. Create a new exercise");
                        System.out.println("2. Modify/delete an exercise");
                        System.out.println("3. Set or modify the score to pass a level");
                        int thirdChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (thirdChoice) {
                            case 1:
                                exoMan.createNewExercise(lvlToModify);
                                wrongChoice = false;
                                break;
                            case 2:
                                exoMan.modifyExercise(lvlToModify);
                                wrongChoice = false;
                                break;
                            case 3:
                                lvlMan.setScoreToUpdateLevel(lvlToModify);
                                wrongChoice = false;
                                break;
                            default:
                                System.out.println("This option is not available. Please try again.");
                        }
                    }
                    break;
                case 2:
                    // Afficher le progrès des étudiants
                    boolean stu = false;
                    for (User student : usersInfos) {
                        if (student.getRole().equals(Role.STUDENT) && student.getLanguage().equals(currentUser.getLanguage())) {
                            stu = true;
                            System.out.println("Student " + student.getName() + ": " + student.getLevel() + " level with " + student.getCurrentScore());
                        }
                    }

                    // S'il n'y a pas d'étudiants dans cette langue
                    if (!stu){
                        System.out.println("There are no students learning " + currentUser.getLanguage() + " yet.");
                    }
                    break;

                case 3:
                    // Voir son profil
                    currentUser.getUserProfile();
                    break;

                case 4:
                    // Modifier son profil (id, nom, mot de passe uniquement)
                    String oldID = currentUser.getId();
                    currentUser.updateProfile();
                    System.out.println(oldID);
                    System.out.println(currentUser.getId());
                    usersDatabase.updateDatabase(currentUser, oldID);
                    break;

                case 5:
                    // Se déconnecter
                    System.out.println();
                    System.out.println("Thank you for your visit. See you soon.");
                    currentUser = null;
                    continueRunning=false;
                    break;

                case 6:
                    // Supprimer son compte
                    System.out.println();
                    System.out.println("Are you sure you want to delete your account? Please answer by typing 'yes' or 'no'.");
                    String deleteConfirmation = scanner.nextLine();
                    if (deleteConfirmation.equalsIgnoreCase("yes")) {
                        usersDatabase.deleteAccount(currentUser);
                        System.out.println("We're sorry to see you go. We hope you enjoyed learning with us. Please come back anytime :)");
                        currentUser = null;
                        continueRunning=false;
                    } else {
                        System.out.println("Deleting account cancelled.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } scanner.close();
    }
}
