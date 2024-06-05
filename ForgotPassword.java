import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPassword extends JFrame implements ActionListener {
    private JButton renewPasswordButton;
    private JLabel favoriteWordJLabel;
    private JTextField usernameField, favoriteWordField;
    private JLabel passwordLabel, passwordAgainLabel;
    private JPasswordField passwordField, passwordAgainField;
    private JButton backToLoginButton;

    public ForgotPassword() {
        setTitle("Forgot Password");
        setLayout(new GridBagLayout()); 
        setSize(430,400);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel, gbc);

        gbc.gridy++;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        gbc.gridy++;
        passwordLabel = new JLabel("Password:");
        add(passwordLabel, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridy++;
        passwordAgainLabel = new JLabel("Password Again:");
        add(passwordAgainLabel, gbc);

        gbc.gridy++;
        passwordAgainField = new JPasswordField(15);
        add(passwordAgainField, gbc);

        gbc.gridy++;
        favoriteWordJLabel = new JLabel("Write down your most favorite word");
        add(favoriteWordJLabel, gbc);

        gbc.gridy++;
        favoriteWordField = new JTextField(15);
        add(favoriteWordField, gbc);

        gbc.gridy++;
        renewPasswordButton = new JButton("Renew Password");
        renewPasswordButton.addActionListener(this);
        add(renewPasswordButton, gbc);

        
        gbc.gridy ++;
        backToLoginButton = new JButton("Back to Login");
        backToLoginButton.addActionListener(this);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(backToLoginButton, gbc);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == renewPasswordButton) {
            String username = usernameField.getText().toLowerCase().trim();
            String password = new String(passwordField.getPassword()).toLowerCase();
            String passwordAgain = new String(passwordAgainField.getPassword()).toLowerCase();
            String favoriteWord = favoriteWordField.getText().toLowerCase().trim();

         
            if (checkFavoriteWord(username, favoriteWord)) {
             
                if (password.trim().equals(passwordAgain.trim())) {
                    
                    resetPassword(username, password);
                    JOptionPane.showMessageDialog(this, "Password reset successful.");
                } else {
                    JOptionPane.showMessageDialog(this, "Make sure that password matches to the password repetition.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Favorite word does not match.");
            }
        }
            else if(e.getSource()==backToLoginButton)
            {
                this.dispose();
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);
            }
        }

    private boolean checkFavoriteWord(String username, String favoriteWord) {
        
        String storedFavoriteWord = RegistrationPage.getFavoriteWord(username);
      
        return favoriteWord.equals(storedFavoriteWord);
    }

    private void resetPassword(String username, String password) {
        RegistrationPage.resetPassword(username, password);
        JOptionPane.showMessageDialog(this,"Resetting password for username: " + username + ", New password: " + password);
    }

    
}