package ProjetAgatheWallet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Gère la base de données des utilisateurs de l'application
 */
public class Database {
    private List<User> usersInfo ;
    protected List<String> idList;
    private File database = new File("src/ProjetAgatheWallet/usersDataBase.csv");
    private Tools tools = new Tools();

    /**
     * Construction de la classe Database
     * @throws IOException si une erreur se produit au moment de la manipulation du fichier de
     * base de données
     */
    public Database() throws IOException {
        String separator = "\t";
        BufferedReader br = new BufferedReader(new FileReader(database));
        String line;
        this.idList = new ArrayList<>();
        this.usersInfo = new ArrayList<>();

        // Lecture des informations ligne par ligne : une ligne = un utilisateur
        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(separator);
            String id = lineInfo[0];
            this.idList.add(id);
            this.usersInfo.add(new User(lineInfo));
        }
        br.close();
    }

    /**
     * Récupère la liste des identifiants des utilisateurs utilisant l'application
     * @return la liste des identifiants
     */
    public List<String> getIdList() {
        return idList;
    }

    /**
     * Récupère la liste des informations des utilisateurs
     * @return la liste des utilisateurs et leurs informations
     */
    public List<User> getUsersInfo(){
        return usersInfo;
    }

    /**
     * Permet d'ajouter un nouvel utilisateur à la base de données
     * @param userD correspond aux informations de l'utilisateur à ajouter
     * @throws IOException si une erreur se produit au moment de la manipulation du fichier de
     * base de données
     */
    public void addUser(String[] userD) throws IOException{
        User newUser = new User(userD);
        this.usersInfo.add(newUser);
        String id = newUser.getId();
        idList.add(id);
        String userData = newUser.getId() + "\t" + newUser.getName() + "\t" + newUser.getPassword() + "\t" + newUser.getRole() +  "\t" + newUser.getLanguage() +  "\t" + newUser.getLevel() + "\t" + tools.mapToString(newUser.getProgress());
        try (FileWriter writer = new FileWriter(database, true)) {
            writer.write(userData + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error while writing in the file: " + e.getMessage());
        }
    }

    /**
     * Met à jour les informations de l'utilisateur dans la base de données
     * @param currentUser correspond à l'utilisateur pour lequel on modifie les informations
     * @param oldID l'ancien ID si celui-ci a été modifié pour retrouver l'utilisateur dans la
     *              base de données
     * @throws IOException si une erreur se produit au moment de la manipulation du fichier de
     * base de données
     */
    public void updateDatabase(User currentUser, String oldID) throws IOException {
        File tempFile = new File("src/ProjetAgatheWallet/tempfile.csv");
        BufferedReader reader = new BufferedReader(new FileReader(database));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(oldID)) {
                StringBuilder newUserInfo = new StringBuilder();
                for (String info : currentUser.getUser()) {
                    newUserInfo.append(info).append("\t");
                }
                // On enlève la dernière virgule
                newUserInfo = new StringBuilder(newUserInfo.substring(0, newUserInfo.length() - 1));
                writer.write(String.valueOf(newUserInfo));
            } else {
                writer.write(line);
            }
            writer.newLine();
        }
        reader.close();
        writer.close();

        if (database.delete() && tempFile.renameTo(database)) {
            System.out.println("Your progress and/or your new information have been registered.");
        } else {
            System.out.println("Your progress and/or your new information could not be registered.");
        }
    }

    /**
     * Supprime le compte de l'utilisateur de la base de données
     * @param currentUser correspond à l'utilisateur à supprimer de la base de données
     * @throws IOException si une erreur se produit au moment de la manipulation du fichier de
     * base de données
     */
    public void deleteAccount(User currentUser) throws IOException {
        File tempFile = new File("src/ProjetAgatheWallet/tempfile.csv");
        BufferedReader reader = new BufferedReader(new FileReader(database));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String line;

        while ((line = reader.readLine()) != null) {
            if (! line.startsWith(currentUser.getId())) {
                writer.write(line);
                writer.newLine();
            }
        }
        reader.close();
        writer.close();

        if (database.delete() && tempFile.renameTo(database)) {
            System.out.println("Your account has been deleted.");
        } else {
            System.out.println("Your account could not been deleted. Please try again later.");
        }
    }
}

