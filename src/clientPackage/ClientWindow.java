package clientPackage;


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Muhib on 8/4/2016.
 */
public class ClientWindow extends JFrame implements Runnable{
    private JPanel pnl;
    private JTextArea history;
    private JList<String> onlineUserList;
    private JScrollPane scroll;
    private DefaultCaret caret;
    private JTextField txtMessage;
    private JButton sendButton;
    private JLabel onlineUser;
    private Client client;
    private boolean running=true,connect=false;
    private String[] str;
    //private static DefaultListModel model=new DefaultListModel<String>();


    public ClientWindow(String name, String address, int port) {

        client=new Client(name,address,port);

        makeFrame();
        createTextArea();
        createTextField();
        createSendButton();
        createLabel();
        closeFrame();
        Thread setUp=new Thread(this);
        setUp.start();
    }

    private void makeFrame(){
        setTitle(client.getName());
        setSize(880,550);
        pnl=new JPanel();
        setContentPane(pnl);
        pnl.setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    private void createTextArea(){

        history=new JTextArea();
        history.setEditable(false);
        history.setFont(new Font("Times New Roman",Font.PLAIN,18));
       // scroll=new JScrollPane(history,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        history.setBounds(5,5,600,470);
        pnl.add(history);
        onlineUserList =new JList<String>();
        onlineUserList.setBounds(620,68,240,445);
        pnl.add(onlineUserList);
    }
    private void createTextField(){
        txtMessage=new JTextField();
        txtMessage.setFont(new Font("Times New Roman",Font.BOLD,18));
        txtMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    if(connect)
                        send(txtMessage.getText());
                }
            }
        });
        txtMessage.setBounds(5,485,490,30);
        txtMessage.requestFocusInWindow();
        pnl.add(txtMessage);
    }
    private void createSendButton(){
        sendButton=new JButton("Send");
        sendButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(connect)
                            send(txtMessage.getText());
                    }
                }
        );
        sendButton.setBounds(505,485,100,30);
        pnl.add(sendButton);
    }
    private void createLabel(){
        onlineUser=new JLabel("Online Users");
        onlineUser.setBounds(690,20,220,30);
        pnl.add(onlineUser);
    }
    private void closeFrame(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //"/d/"--disconnect sign
                if(connect) {
                    client.sendMessage("/d/");
                }
            }
        });
    }
    //"/m/"--message sign
    private void send(String message) {
        if(message.equals(""))
            return;
        client.sendMessage("/m/"+message);
        txtMessage.setText("");
    }
    private void console(String message){
        history.append(message+"\n\n");
    }
    private void showOnline(){
        client.receiveUserList();
        str=new String[client.userList.size()];
        for(int i=0;i<client.userList.size();i++){
            str[i] = client.userList.get(i);
        }
        onlineUserList.setListData(str);
    }

    @Override
    public void run() {
        if(isConnected()) {
            client.setUpStream();
            //"/c/" connection sign;
            client.sendMessage("/c/" + client.getName());
            while (running) {
                String message = client.receiveFromServer();
                if(message.equals("/d/")){
                    running=false;
                    client.closeAll();
                }
                else if(message.startsWith("/u/")){
                    //String user=message.substring(3,message.length());
                    showOnline();
                }
                else
                    console(message);
            }
        }
    }
    private boolean isConnected(){
        console("Attempting to Connect "+client.getAddress()+" at port "+client.getPort());
        connect=client.makeConnection();
        if(connect){
            console("Successfully Connected!!");
            return true;
        }
        console("Connection Failed !!!");
        txtMessage.setEditable(false);
        return false;
    }
}
