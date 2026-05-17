package Frontend;

import Backend.*;
import java.awt.Label;
import java.awt.event.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    CampusData data;

    public LoginFrame(CampusData cd) {

        this.data = cd;
        setTitle("User Login");
        setSize(250, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creating a panel that holds the username and password fields
        JPanel jp = new JPanel();
        JTextField usernamefield = new JTextField(10);
        JPasswordField passwordfield = new JPasswordField(10);
        JButton loginbutton = new JButton("Login");

        //Adding the created fields on to the panel 
        jp.add(new Label("Username : "));
        jp.add(usernamefield);
        jp.add(new Label("Password : "));
        jp.add(passwordfield);
        jp.add(loginbutton);

        //Action of Login Button 
        loginbutton.addActionListener(new ActionListener() {
            @Override
            //action performed by clicking the logib nutton
            public void actionPerformed(ActionEvent a) {

                String username = usernamefield.getText().trim();
                String pass = new String (passwordfield.getPassword());

                for(int i=0; i<data.users.size(); i++) {
                    User user = data.users.get(i);
                    if(user.getUsername().equals(username) && user.getPassword().equals(pass)) {

                        JOptionPane.showMessageDialog(jp, "Login Successful");
                        new MainFrame(user, data);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(jp, "Invalid Credentials");
            }
        });

        add(jp);
        setVisible(true);
    }
}//end of loginframe class 


