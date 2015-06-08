package clientftp;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

/**
 *
 * @author Nadia
 */
public class ConnectToServer {

    private ResourceBundle resource;
    private final String SERVER;
    private final String LOGIN;
    private final String PASSWORD;

    public ConnectToServer() {
        resource = ResourceBundle.getBundle("clientftp.ftp_information", Locale.ENGLISH);
        this.SERVER = resource.getString("SERVER");
        this.LOGIN = resource.getString("LOGIN");
        this.PASSWORD = resource.getString("PASSWORD");
    }

    public void connectionToServer(FTPClient ftpClient) throws IOException {

        FTPClientConfig config = new FTPClientConfig();
        ftpClient.configure(config);
        ftpClient.connect(SERVER);
        ftpClient.enterLocalPassiveMode();
        System.out.println("CONNECT TO " + SERVER + ".");
        String loging_success = ftpClient.login(LOGIN, PASSWORD) == true ? "success" : "failed";
        System.out.println("LOGIN: " + loging_success);


    }
}
