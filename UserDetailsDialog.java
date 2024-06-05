import javax.swing.*;
import java.awt.*;

public class UserDetailsDialog extends JDialog {
    public UserDetailsDialog(Frame owner, String username, String title, String author, String rating, String[] reviews) {
        super(owner, "Review Details", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        for (String review : reviews) {
            textArea.append("Username: " + username + "\n" +
                             "Title: " + title + "\n" +
                             "Author: " + author + "\n" +
                             "Rating: " + rating + "\n" +
                             "Review: " + review + "\n\n");
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }
}
