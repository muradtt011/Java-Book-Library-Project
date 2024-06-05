import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserControlPanel extends JFrame implements ActionListener {
    private JPanel panel;
    private JTextField usernameField, passwordField, favoriteWordField;
    private JButton deleteBtn, generalDbButton;
    String username;

    public UserControlPanel(String username) {
        this.username = username;
        panel = new JPanel();
        panel.setLayout(null);
        usernameField = new JTextField();
        passwordField = new JTextField();
        favoriteWordField = new JTextField();
        deleteBtn = new JButton("Delete User");
        generalDbButton = new JButton("GeneralDB");


        JLabel CrudLabel = new JLabel("User Manager");
        CrudLabel.setFont(new Font("Arial", Font.BOLD, 22));
        CrudLabel.setBounds(125, 160, 200, 40);
        panel.add(CrudLabel);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(40, 250, 150, 30);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(usernameLabel);
        usernameField.setBounds(140, 250, 200, 30);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(40, 300, 150, 30);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(passwordLabel);
        passwordField.setBounds(140, 300, 200, 30);
        panel.add(passwordField);

        JLabel favoriteWordLabel = new JLabel("<html>Favorite<br> Word: <html>");
        favoriteWordLabel.setBounds(40, 350, 120, 30);
        favoriteWordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(favoriteWordLabel);
        favoriteWordField.setBounds(140, 350, 200, 30);
        panel.add(favoriteWordField);


        deleteBtn.setBounds(110, 400, 150, 35);
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
        deleteBtn.addActionListener(this);
        panel.add(deleteBtn);

        generalDbButton.setBounds(80, 450, 200, 50);
        generalDbButton.setFont(new Font("Arial", Font.BOLD, 14));
        generalDbButton.addActionListener(this);
        panel.add(generalDbButton);

    }

    JPanel getPanel() {
        return panel;
    }

    public void setUsernameField(String text) {
        usernameField.setText(text);
    }

    public void setPasswordField(String text) {
        passwordField.setText(text);
    }

    public void setFavoriteWordField(String text) {
        favoriteWordField.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteBtn) {
            this.dispose();
            String selectedUsername = usernameField.getText();
            if (!selectedUsername.isEmpty()) {
                UserControlTable userControlTable = new UserControlTable(username);
                userControlTable.deleteUser(selectedUsername);
            }
        } else if (e.getSource() == generalDbButton) {
            this.dispose();
            MyGeneralTable generalTable = new MyGeneralTable(username);
            PanelForGeneralDB.disabled();
        }
    }
}