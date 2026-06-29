package Frontend;

import Backend.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

    CampusData data;

    public LoginFrame(CampusData cd) {

        this.data = cd;
        setTitle("User Login");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (!UserSession.getInstance().isLoggedIn()) {
                    System.exit(0);
                }
            }
        });

        //Creating a panel that holds the username and password fields
        JPanel mainPanel = new JPanel(new BorderLayout());
        //GridBagLayout //Grid Bag Constraints
        //This will be the west panel
        JPanel westPanel = new JPanel();
        westPanel.setBorder(new EmptyBorder(20,20,20,20));
        westPanel.setPreferredSize(new Dimension(250, 0));
        westPanel.setBackground(AppTheme.NAVY);

        JLabel titleLabel = new JLabel("Welcome!");
        JLabel infoLabel = new JLabel("UniVerse Lite");
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        titleLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Segeo UI", Font.PLAIN, 20));
        infoLabel.setForeground(Color.WHITE);
        westPanel.add(titleLabel);
        westPanel.add(infoLabel);



        JPanel eastPanel = new JPanel(new GridBagLayout());
        eastPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        eastPanel.setPreferredSize(new Dimension(250, 0));
        eastPanel.setBackground(AppTheme.BLOOM);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; //for everything in a single column
        gbc.gridy = GridBagConstraints.RELATIVE; //automatically bump into next row
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8,0,8,0); //padding vertical
        gbc.weightx = 1; //allow components to grow horizontally with the window

        JLabel userLabel = new JLabel("Username:");
        JTextField usernamefield = AppTheme.styledField(10);
        
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passwordfield = AppTheme.styledPasswordField(10);
        
        JButton loginbutton = AppTheme.primaryButton("Login");

        //Add them to eastPanel
        eastPanel.add(userLabel, gbc);
        eastPanel.add(usernamefield, gbc);
        eastPanel.add(passLabel, gbc);
        eastPanel.add(passwordfield, gbc);

        //Separate the login button a bit more
        gbc.insets = new Insets(18, 0, 8, 0);
        eastPanel.add(loginbutton, gbc);

        //Action of Login Button 
        loginbutton.addActionListener(new ActionListener() {
            @Override
            //action performed by clicking the login button
            public void actionPerformed(ActionEvent a) {

                String username = usernamefield.getText().trim();
                String pass = new String (passwordfield.getPassword());

                for(int i=0; i<data.users.size(); i++) {
                    User user = data.users.get(i);
                    if(user.getUsername().equals(username) && user.verifyPassword(pass)) {

                        UserSession.getInstance().login(user);
                        JOptionPane.showMessageDialog(mainPanel, "Login Successful");
                        LoginFrame.this.dispose();
                        new MainFrame(data);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(mainPanel, "Invalid Credentials");
            }
        });
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(westPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}//end of loginframe class 


