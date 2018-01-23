package clientPackage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Muhib on 8/4/2016.
 */
public class Client {
    private String name,address;
    private int port;
    private InetAddress serverIp;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket socket;
    public static ArrayList<String>userList=new ArrayList<String>();

    public Client(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }
    public boolean makeConnection(){

        try {
            serverIp=InetAddress.getByName(address);
           socket=new Socket(serverIp,port);
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }
    public void setUpStream(){
        try {
            input=new ObjectInputStream(socket.getInputStream());
            output=new ObjectOutputStream(socket.getOutputStream());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String message){
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Something went wrong!!");
            //e.printStackTrace();
        }
    }
    public String receiveFromServer() {
        String mess="";
        try {
            mess=(String)input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return mess;
    }
    public void receiveUserList(){
        try {
            userList=(ArrayList<String>) input.readObject();
            System.out.println(userList.size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public void closeAll(){
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
