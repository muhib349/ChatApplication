package serverPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib on 8/11/2016.
 */
public class ManageClient {
    private Socket socket;
    private Thread receiveThread,streamThread;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean running = false;
    private String name;
    private ServerClient sClient;
    private static List<ServerClient>socketList=new ArrayList<ServerClient>();
    private static ArrayList<String>userList=new ArrayList<String>();

    public ManageClient(Socket socket) {
        this.socket = socket;
        running = true;
        setUpStream();
    }

    private void setUpStream(){
        streamThread=new Thread("Stream Thread"){
            public void run(){
                try {
                    output = new ObjectOutputStream(socket.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(socket.getInputStream());
                    sClient=new ServerClient(socket,output,input);
                    socketList.add(sClient);
                    receiveFromClient();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        streamThread.start();
    }

    private void receiveFromClient() {
        receiveThread = new Thread("Receive Thread") {
            public void run() {
                while (running) {
                    try {
                        String message = (String) input.readObject();
                        manageText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        receiveThread.start();
    }
    private void manageText(String message){
        if (message.startsWith("/c/")) {
            name = message.substring(3, message.length());
            System.out.println(name+" is Connected");
            userList.add(name);
            sendUser();

        } else if (message.startsWith("/m/")) {
            String finalMess = message.substring(3, message.length());
            System.out.println(name + " :" + finalMess);
            sendClient(name+":"+finalMess);
        } else if (message.startsWith("/d/")) {
            try {
                output.writeObject("/d/");
                output.flush();
                socketList.remove(this.sClient);
                userList.remove(this.name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            closeAll();
            System.out.println(name + " is Disconnected");
        }

    }
    private void sendClient(String message){
        try {
            for(int i=0;i<socketList.size();i++){
                ServerClient sc=socketList.get(i);
                sc.output.writeObject(message);
                sc.output.flush();
            }
            } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    private void sendUser(){
        Thread user=new Thread("Online User"){
            public void run(){
                int check=0;

                while (running){
                   // if(socketList.size()>check) {
                        for (int i = 0; i < socketList.size(); i++) {
                            ServerClient sc = socketList.get(i);
                            try {
                                sc.output.writeObject("/u/");
                                sc.output.flush();
                                sc.output.writeObject(userList);
                                sc.output.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        //}
                        //check=socketList.size();
                    }
                    System.out.println(name+":"+userList.size());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        user.start();
    }
    private void closeAll(){
        try {
            running = false;
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
