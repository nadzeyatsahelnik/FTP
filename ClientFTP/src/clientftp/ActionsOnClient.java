package clientftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Nadia
 */
public class ActionsOnClient {

    private ResourceBundle resource;
    private static String SEPARATOR = "/";
    public static String START_DIRECTORY = SEPARATOR;
    private final String SAVE_PATH;

    public ActionsOnClient() {
        resource = ResourceBundle.getBundle("clientftp.ftp_information", Locale.ENGLISH);
        this.SAVE_PATH = resource.getString("SAVE_PATH");
    }

    public void showListOfFiles(FTPClient ftpClient, String directory) throws IOException {
        FTPFile[] filesInDirectory = ftpClient.listFiles(directory);
        System.out.println("FILES LIST:");
        for (FTPFile files : filesInDirectory) {
            if (files.isFile()) {
                System.out.println("FILE: " + files.getName());
            } else {
                System.out.println("FOLDER: " + files.getName());
            }
        }
    }

    public void goToFolder(FTPClient ftpClient) throws IOException {

        System.out.println("ENTER FOLDER:");
        Scanner scan = new Scanner(System.in);
        String nameFolder = scan.nextLine();
        START_DIRECTORY += nameFolder + SEPARATOR;
        System.out.println(START_DIRECTORY);
        showListOfFiles(ftpClient, START_DIRECTORY);

    }

    public void goToParentDirectory(FTPClient ftpClient) throws IOException {
        String[] parentDirectory = START_DIRECTORY.split(SEPARATOR);
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < parentDirectory.length - 1; i++) {
            resultString.append(parentDirectory[i]).append(SEPARATOR);
            START_DIRECTORY = resultString.toString();
        }
        System.out.println(START_DIRECTORY);
        showListOfFiles(ftpClient, START_DIRECTORY);
    }

    public void downloadeFile(FTPClient ftpClient) throws IOException {
        Scanner scanfile = new Scanner(System.in);
        System.out.println("ENTER FILE IN SELECTED FOLDER:");
        String nameFile = scanfile.nextLine();
        String remoteFile = START_DIRECTORY + SEPARATOR + nameFile;
        String saveName = SAVE_PATH + nameFile;
        File fileToDownload = new File(saveName);
        if (fileToDownload.isFile()) {
            OutputStream outputStream;
            outputStream = new BufferedOutputStream(new FileOutputStream(fileToDownload));
            boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
            if (success) {
                System.out.println("File " + nameFile + " has been downloaded.");
            }
        } else {
            System.out.println("It's a folder!");
        }
    }
}