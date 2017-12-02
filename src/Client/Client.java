package Client;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Ronshmul on 28/05/2017.
 */
public class Client {
    private InetAddress IP;
    private int port;
    private IClientStrategy clientStrategy;

    public Client(InetAddress IP, int port, IClientStrategy clientStrategy) {
        this.IP = IP;
        this.port = port;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer(){
        try {
            Socket theServer = new Socket(IP, port);
            System.out.println("Connected to server!");
            clientStrategy.clientStrategy(theServer.getInputStream(),theServer.getOutputStream());
            theServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
