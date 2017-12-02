package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Properties;

/**
 * Created by Ronshmul on 22/05/2017.
 */
public class Server {
    private int port;
    private int listeningInterval;
    private ServerStrategy serverStrategy;
    private volatile boolean stop;
    private ExecutorService executor;
    private Properties proper = new Properties();
    private InputStream input = null;
    private properties prop;

    public Server(int port, int listeningInterval, ServerStrategy clientHandler) {
        properties.RunProp();
        try {
            input = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            proper.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = clientHandler;
        executor = Executors.newFixedThreadPool(Integer.parseInt(proper.getProperty("NumOfThreads")));

    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
//        executor.execute(() -> {
//            runServer();
//        });
    }

    private void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            while (!stop) {
                try {
                    Socket aClient = server.accept(); // blocking call
//                    new Thread(() -> {
//                        handleClient(aClient);
//                    }).start();
                    executor.execute(() -> {handleClient(aClient);});
                } catch (SocketTimeoutException e) {
                }
            }
            server.close();
        } catch (IOException e) {
        }
    }

    private void handleClient(Socket aClient) {
        try {
            serverStrategy.serverStrategy(aClient.getInputStream(), aClient.getOutputStream());
            aClient.getInputStream().close();
            aClient.getOutputStream().close();
            aClient.close();
        } catch (IOException e) {
        }
    }

    public void stop() {
        System.out.println("Stopping server..");
        stop = true;
        executor.shutdown();
    }

    public static class properties {

        public static Properties prop = new Properties();
        public static OutputStream output = null;

        public static void RunProp() {
            try

            {
                output = new FileOutputStream("config.properties");
                // set the properties value
                prop.setProperty("MazeGenerator", "MyMazeGenerator");
                prop.setProperty("SearchingAlgorithm", "BreadthFirstSearch");
                prop.setProperty("NumOfThreads", "10");

                // save properties to project root folder
                try {
                    prop.store(output, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
