package serverPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Muhib on 8/4/2016.
 */
public class Server implements Runnable {
    private int port;
    private Thread serverRun;
    private ServerSocket server;
    private Socket connection;

    public Server(int port) {
        this.port = port;
        serverRun = new Thread(this, "ServerThread");
        serverRun.start();
    }

    @Override
    public void run() {
        makingServer();
        System.out.println("Server Opened at port=" + port);
        clientConnection();
    }
    private void makingServer(){
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clientConnection() {
        while (true) {
            try {
                connection = server.accept();
               /* ManageClient.socketList.add(connection);*/
                new ManageClient(connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}