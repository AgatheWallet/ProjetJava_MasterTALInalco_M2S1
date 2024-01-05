package ProjetAgatheWallet;

import javax.tools.Tool;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> usersInfo ;
    protected List<String> idList;
    private File database = new File("src/ProjetAgatheWallet/usersDataBase.csv");
    private Tools tools = new Tools();

    public Database() throws IOException {
        String separator = "\t";
        BufferedReader br = new BufferedReader(new FileReader(database));
        String line;
        this.idList = new ArrayList<>();
        this.usersInfo = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(separator);
            String id = lineInfo[0];
            this.idList.add(id);
            this.usersInfo.add(new User(lineInfo));
        }
        br.close();
    }

    public List<String> getIdList() {
        return idList;
    }

    public List<User> getUsersInfo(){
        return usersInfo;
    }

    public void addUser(String[] userD) {
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

    public void updateDatabase(User currentUser) throws IOException {
        File tempFile = new File("src/ProjetAgatheWallet/tempfile.csv");
        BufferedReader reader = new BufferedReader(new FileReader(database));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(currentUser.getId())) {
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

    public void deleteAccount(User currentUser) throws IOException {
        File tempFile = new File("src/ProjetAgatheWallet/tempfile.csv");
        BufferedReader reader = new BufferedReader(new FileReader(database));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String line;

        while ((line = reader.readLine()) != null) {
            if (! line.startsWith(currentUser.getId())) {
                writer.write(line);
            }
            writer.newLine();
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

