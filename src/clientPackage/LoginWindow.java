package clientPackage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Muhib on 8/4/2016.
 */
public class LoginWindow extends JFrame {
    private JPanel panel,pnlMain;
    private JLabel lblName,lblAddress,lblPort;
    private JTextField nameTextF,addressTextF,portTextF;
    private GridBagConstraints gbc;
    private Font fnt;
    private JButton loginButton;


    public LoginWindow  (){
        pnlMain=new JPanel();
        setContentPane(pnlMain);
        setTitle("Login");
        setSize(300,380);
        setResizable(false);
        setLocationRelativeTo(null);
        panel=new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        //panel.setBackground(new Color(102,178,255));
        panel.setLayout(new GridBagLayout());
        pnlMain.add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gbc=new GridBagConstraints();
        createLabel();
        createTextField();
        createButton();
    }

    private void createLabel() {
        fnt=new Font("Times New Roman",Font.PLAIN,18);
        gbc.insets=new Insets(10,5,10,10);
        gbc.anchor=GridBagConstraints.LINE_END;
        gbc.gridx=0;
        gbc.gridy=0;
        lblName=new JLabel("User Name");
        lblName.setFont(fnt);
        panel.add(lblName,gbc);

        gbc.gridy++;
        lblAddress=new JLabel("IP Address");
        lblAddress.setFont(fnt);
        panel.add(lblAddress,gbc);

        gbc.gridy++;
        lblPort=new JLabel("Port Number");
        lblPort.setFont(fnt);
        panel.add(lblPort,gbc);
    }
    private void createTextField(){
        gbc.anchor=GridBagConstraints.LINE_START;
        gbc.gridx=1;
        gbc.gridy=0;
        nameTextF=new JTextField(10);
        nameTextF.setFont(fnt);
        panel.add(nameTextF,gbc);

        gbc.gridy++;
        addressTextF=new JTextField(10);
        addressTextF.setFont(fnt);
        panel.add(addressTextF,gbc);

        gbc.gridy++;
        portTextF=new JTextField(10);
        portTextF.setFont(fnt);
        panel.add(portTextF,gbc);
    }
    private void createButton(){
        //gbc.anchor=GridBagConstraints.CENTER;
        gbc.gridx=1;
        gbc.gridy++;
        loginButton=new JButton("Login");
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name=nameTextF.getText();
                        String address=addressTextF.getText();
                        int port=0;
                        try{
                            port=Integer.parseInt(portTextF.getText());
                        }catch (NumberFormatException n){
                            n.printStackTrace();
                        }

                        login(name,address,port);
                    }
                }
        );
        pnlMain.add(loginButton,gbc);
    }

    private void login(String nam,String add,int port){
        dispose();
        new ClientWindow(nam,add,port);
    }

}

