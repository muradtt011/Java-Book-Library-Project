import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class RegistrationPage extends JFrame implements ActionListener {

    public static void main(String[] args) {
        RegistrationPage registerPage = new RegistrationPage();
        registerPage.setVisible(true);
    }

    

    protected JTextField usernameField;
    private JTextField favWordField;
    protected JPasswordField passwordField;
    protected JPasswordField passwordAgainField;
    protected JButton registerButton;
    protected JButton loginFrameBtn;

    public RegistrationPage() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 400);
        setLayout(new GridBagLayout());

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy ++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 1;
        gbc.gridy ++;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy++;
        add(passwordField, gbc);
        
        JLabel passwordAgainLabel = new JLabel("Password Again:");
        gbc.gridx = 1;
        gbc.gridy ++;
        add(passwordAgainLabel, gbc);

        passwordAgainField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy ++;
        add(passwordAgainField, gbc);

        gbc.gridy++;
        gbc.gridx=1;
        JLabel favWordLabel=new JLabel("<html>What Is Your Favorite Word?<br>(If You Forget Your Password)<html>");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(favWordLabel, gbc);

        gbc.gridy++;
        favWordField=new JTextField(15);
        add(favWordField,gbc);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy ++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(registerButton, gbc);

        
        loginFrameBtn = new JButton("Back To Login");
        loginFrameBtn.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy ++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginFrameBtn, gbc);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username;
        String password;
        String passwordAgain;
        String favoriteWord;
        if (e.getSource() == registerButton) {
            username = usernameField.getText().trim().toLowerCase();
            password = new String(passwordField.getPassword());
            passwordAgain = new String(passwordAgainField.getPassword());
            favoriteWord = favWordField.getText().trim().toLowerCase();
            if (username.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Username Cannot Be Admin.");
            } else {
                if (password.equals(passwordAgain)) {
                    if (registerUser(username, password, favoriteWord)) {
                        JOptionPane.showMessageDialog(this, "Registration Successful");
                        dispose();
                        OptionDatabase optionDatabase = new OptionDatabase(username);
                        optionDatabase.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Make Sure That Password Matches The Password Repetition.");
                }
            }
        } else if (e.getSource() == loginFrameBtn) {
            dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        }
    }
    

    private boolean registerUser(String username, String password,String favoriteWord) {
        if(username=="admin")
        {
            JOptionPane.showMessageDialog(this, "Username Cannot Be Admin.");
            return false;
        }
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username And Password Cannot Be Empty.");
            return false;
        }

        // Validate strong password with regex
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_])(?=\\S+$).{8,}$";
        if (!password.matches(passwordRegex)) {
            JOptionPane.showMessageDialog(this, "Password must contain at least 8 characters, including at least one digit, one uppercase letter, one lowercase letter, one special character(@#$%^&+=!), and no whitespaces.");
            return false;
        }

        // Proceed with user registration
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("users.csv"));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("users.csv", true))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    JOptionPane.showMessageDialog(this,"This Username Is Already In Use.");
                    return false;
                }
            }
            bufferedWriter.write(username + "," + password+","+favoriteWord);
            bufferedWriter.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public static void resetPassword(String username, String newPassword) {
        try {
            // Load the contents of the CSV file into a temporary data structure
            ArrayList<String> fileContents = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    fileContents.add(line);
                }
            }

            // Rewrite the contents of the CSV file, updating the password for the given username
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.csv"))) {
                for (String line : fileContents) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[0].equals(username)) {
                        // Update the password
                        parts[1] = newPassword;
                        line = String.join(",", parts);
                    }
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFavoriteWord(String username) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    // Return the favorite word stored in the database
                    return parts[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // If the username is not found or any error occurs, return null
        return null;
    }
}