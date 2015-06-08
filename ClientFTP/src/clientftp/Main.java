package clientftp;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Nadia
 */
public class Main {

    private static Pattern pattern = Pattern.compile("^\\d+$");
    private static Matcher matcher = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        boolean limiter = true;
        int selectItem = 0;
        Scanner scannerMenuSelection = new Scanner(System.in);
        try {
            FTPClient ftpClient = new FTPClient();
            ConnectToServer connectToServer = new ConnectToServer();
            ActionsOnClient actionOnClient = new ActionsOnClient();
            String menuSelection = null;

            while (limiter) {
                MainMenu.menu();
                do {
                    System.out.println("ENTER YOUR CHOICE:");
                    menuSelection = scannerMenuSelection.nextLine();
                    matcher = pattern.matcher(menuSelection);
                } while (!matcher.matches());
                selectItem = Integer.parseInt(menuSelection);
                switch (selectItem) {
                    case 1: {
                        connectToServer.connectionToServer(ftpClient);
                        break;
                    }
                    case 2: {
                        actionOnClient.showListOfFiles(ftpClient, ActionsOnClient.START_DIRECTORY);
                        break;
                    }
                    case 3: {
                        actionOnClient.goToFolder(ftpClient);
                        break;
                    }
                    case 4: {
                        actionOnClient.goToParentDirectory(ftpClient);
                        break;
                    }
                    case 5: {
                        actionOnClient.downloadeFile(ftpClient);
                        break;
                    }
                    default: {
                        limiter = false;
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
