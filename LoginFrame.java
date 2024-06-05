import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerNowButton;
    private JButton forgotPasswordButton;
    private JLabel NoAccount;
    private JLabel forgotPasswordJLabel;


    public static void main(String[] args) {
   
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

    }

    public LoginFrame() {
        setTitle("Book Library Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 400);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel("Welcome Our Library");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, gbc);

        gbc.gridy++;
        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridy++;
        panel.add(usernameField, gbc);

        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridy++;
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx--;
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton, gbc);

        NoAccount = new JLabel("Don't you have an account?");
        gbc.gridy++;
        panel.add(NoAccount, gbc);

        gbc.gridy++;
        registerNowButton = new JButton("Register");
        registerNowButton.addActionListener(this);
        panel.add(registerNowButton, gbc);

        gbc.gridy++;
        forgotPasswordJLabel=new JLabel("Did you forgot your password?");
        panel.add(forgotPasswordJLabel, gbc);

        gbc.gridy++;
        forgotPasswordButton=new JButton("Renew Password");
        forgotPasswordButton.addActionListener(this);
        panel.add(forgotPasswordButton, gbc);

        add(panel);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {

        
        if (e.getSource() == loginButton) {
            String username = usernameField.getText().toLowerCase().trim();
            String password = new String(passwordField.getPassword()).toLowerCase();
            if(username.equals("admin") && password.equals("admin")){
                new MyGeneralTable(username);
                PanelForGeneralDB.disabled();
                
              
            }
            else if (checkCredentials(username, password) ) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                
                
                dispose();
                

                    OptionDatabase optionDatabase = new OptionDatabase(username);
                    optionDatabase.setVisible(true);
            } 
            else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
           
            }
        } 
        
        else if (e.getSource() == registerNowButton) {
            this.dispose();
            RegistrationPage rP=new RegistrationPage();
            rP.setLocationRelativeTo(null);
            rP.setVisible(true);

        }
        else if( e.getSource()== forgotPasswordButton)
        {
            this.dispose();
            ForgotPassword rP=new ForgotPassword();
            rP.setLocationRelativeTo(null);
            rP.setVisible(true);
        }
    }

    private boolean checkCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].toLowerCase().equals(username) && parts[1].toLowerCase().equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
