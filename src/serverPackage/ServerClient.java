package serverPackage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Muhib on 8/8/2016.
 */
public class ServerClient {
    public Socket socket;
    public ObjectInputStream input;
    public ObjectOutputStream output;

    public ServerClient(Socket socket, ObjectOutputStream output, ObjectInputStream input) {
        this.socket = socket;
        this.output = output;
        this.input = input;
    }
}
