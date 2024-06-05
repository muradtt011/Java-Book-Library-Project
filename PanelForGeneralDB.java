import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
    
    
    public class PanelForGeneralDB extends JFrame implements ActionListener {
        private JTextField searchField;
        private JPanel panel;
        static JTextField titleField;
        static JTextField authorField;
        static JTextField ratingField;
        static JTextField reviewField;
        private static JButton addBtn,deleteBtn,updateBtn,addToPersonalBtn;
        private static JButton personalDbButton,userManagerButton;
        private static String username;
     
        public static void main(String[] args) {
            
        }
        
        PanelForGeneralDB(String username) {
            this.username = username;
            panel = new JPanel();
            panel.setLayout(null);
        
            JLabel searchLabel = new JLabel("Search");
            searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
            searchLabel.setBounds(50, 40, 200, 30);
            panel.add(searchLabel);
        
            searchField = new JTextField();
            searchField.setBounds(110, 40, 200, 30); // Adjust bounds as needed
            panel.add(searchField);
        
            JLabel CrudLabel = new JLabel("Operations");
            CrudLabel.setFont(new Font("Arial", Font.BOLD, 22));
            CrudLabel.setBounds(135, 150, 200, 40);
            panel.add(CrudLabel);
        
            JLabel titleLabel = new JLabel("Title:");
            titleLabel.setBounds(40, 200, 150, 30);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(titleLabel);
        
            titleField = new JTextField();
            titleField.setBounds(110, 200, 200, 30);
            panel.add(titleField);
        
            JLabel authorLabel = new JLabel("Author:");
            authorLabel.setBounds(40, 250, 150, 30);
            authorLabel.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(authorLabel);
        
            authorField = new JTextField();
            authorField.setBounds(110, 250, 200, 30);
            panel.add(authorField);
        
            JLabel ratingLabel = new JLabel("Rating:");
            ratingLabel.setBounds(40, 300, 120, 30);
            ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(ratingLabel);
        
            ratingField = new JTextField();
            ratingField.setBounds(110, 300, 200, 30);
            panel.add(ratingField);
        
            JLabel reviewLabel = new JLabel("Review:");
            reviewLabel.setBounds(40, 350, 120, 30);
            reviewLabel.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(reviewLabel);
        
            reviewField = new JTextField();
            reviewField.setBounds(110, 350, 200, 30);
            panel.add(reviewField);
        
            
        
            addBtn = new JButton("Add");
            addBtn.setBounds(50, 410, 80, 35);
            addBtn.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(addBtn);
            addBtn.setVisible(false);
        
            updateBtn = new JButton("Update");
            updateBtn.setBounds(140, 410, 80, 35);
            updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(updateBtn);
        
            deleteBtn = new JButton("Delete");
            deleteBtn.setBounds(230, 410, 80, 35);
            deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(deleteBtn);

            addToPersonalBtn = new JButton("Add to Personal Library");
            addToPersonalBtn.setBounds(90, 420, 200, 35);
            addToPersonalBtn.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(addToPersonalBtn);

            personalDbButton = new JButton("PersonalDB");
            personalDbButton.setBounds(115, 480, 150, 70);
            personalDbButton.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(personalDbButton);
        
            
        
            userManagerButton = new JButton("User Manager");
            userManagerButton.setBounds(120, 470, 150, 70);
            userManagerButton.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(userManagerButton);
        
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    filterTable();
                }
        
                @Override
                public void removeUpdate(DocumentEvent e) {
                    filterTable();
                }
        
                @Override
                public void changedUpdate(DocumentEvent e) {
                    filterTable();
                }
            });


            addBtn.setVisible(false);
            addBtn.setEnabled(false);
            updateBtn.setEnabled(false);
            updateBtn.setVisible(false);
            deleteBtn.setEnabled(false);
            deleteBtn.setVisible(false);
            userManagerButton.setVisible(false);
            userManagerButton.setEnabled(false);

            personalDbButton.addActionListener(this);
            addToPersonalBtn.addActionListener(this);
            addBtn.addActionListener(this);
            updateBtn.addActionListener(this);
            deleteBtn.addActionListener(this);
            userManagerButton.addActionListener(this);
            authorField.setEditable(false);
            titleField.setEditable(false);
            ratingField.setEditable(false);
            reviewField.setEditable(false);

        }
        

    
        JPanel getPanel() {
            return panel;
        }
       
    
    
        
        public static void disabled(){
            personalDbButton.setVisible(false);
            personalDbButton.setEnabled(false);
            addBtn.setVisible(true);
            addBtn.setEnabled(true);
            updateBtn.setEnabled(true);
            updateBtn.setVisible(true);
            deleteBtn.setEnabled(true);
            deleteBtn.setVisible(true);
            addToPersonalBtn.setVisible(false);
            addToPersonalBtn.setEnabled(false);
            userManagerButton.setVisible(true);
            userManagerButton.setEnabled(true);
            personalDbButton.setVisible(false);
            personalDbButton.setEnabled(false);
            authorField.setEditable(true);
            titleField.setEditable(true);
        
    }
    public static boolean isAdmin(String username) {
        return username.equals("admin");
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Getting the current book information from the text fields
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String rating = ratingField.getText().trim();
        String review = reviewField.getText().trim();
      
        String[] book = {title, author, rating, review};
    
        if (e.getSource() == personalDbButton) {
            this.dispose();
            new PersonalTable(username);
        } else if (e.getSource() == userManagerButton) {
            this.dispose();
            new UserControlTable(username);
        } else if (e.getSource() == addToPersonalBtn) {
            if(PersonalDatabase.hasDuplicateBook(username, book)) {
                 JOptionPane.showMessageDialog(this, "You already have this book in Personal Library");
            } else {
                if(PersonalDatabase.addBookToPersonalDB(this,username, book))
                {
                // Display a message to indicate success
                JOptionPane.showMessageDialog(this, "Book added to Personal Library");
                }
            }
        } else if(e.getSource()== addBtn) {
            // Add the book to the general database
            if(MyGeneralTable.addBook(book))
            {
                JOptionPane.showMessageDialog(this, "Book successfully added to library.");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Nothing cannot be added to library.");
            }
            // Clear the text fields after adding the book
            titleField.setText("");
            authorField.setText("");
            ratingField.setText("");
            reviewField.setText("");
        } else if (e.getSource() == deleteBtn) {
            int selectedRow = MyGeneralTable.table.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) MyGeneralTable.table.getModel();
                model.removeRow(selectedRow);
                PersonalDatabase.deleteBook(book);
                // Pass the correct row index to the deleteBook method
                MyGeneralTable.deleteBook(selectedRow - 1); // Adjust index to skip header line
                PersonalDatabase.deleteBook(book);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
            titleField.setText("");
            authorField.setText("");
            ratingField.setText("");
            reviewField.setText("");
        }
        
        
        else if (e.getSource() == updateBtn) {
            int selectedRow = MyGeneralTable.table.getSelectedRow();
            if (selectedRow != -1) {
                // Retrieve the old book information from the selected row
                String oldTitle = (String) MyGeneralTable.table.getValueAt(selectedRow, 0);
                String oldAuthor = (String) MyGeneralTable.table.getValueAt(selectedRow, 1);
                String oldRating = (String) MyGeneralTable.table.getValueAt(selectedRow, 2);
                String oldReview = (String) MyGeneralTable.table.getValueAt(selectedRow, 3);
        
                // Create the old book array
                String[] oldBook = {oldTitle, oldAuthor, oldRating, oldReview};
        
                // Update the text fields with new information
                String newTitle = titleField.getText().trim();
                String newAuthor = authorField.getText().trim();
                String newRating = ratingField.getText().trim();
                String newReview = reviewField.getText().trim();
        
                // Create the new book array
                String[] newBook = {newTitle, newAuthor, newRating, newReview};
        
                // Update the database file
                MyGeneralTable.updateBook(selectedRow, newBook);
                PersonalDatabase.updateBookAdmin(oldBook, newBook);
        
                // Update the table model
                DefaultTableModel model = (DefaultTableModel) MyGeneralTable.table.getModel();
                model.setValueAt(newTitle, selectedRow, 0);
                model.setValueAt(newAuthor, selectedRow, 1);
                model.setValueAt(newRating, selectedRow, 2);
                model.setValueAt(newReview, selectedRow, 3);
        
                // Refresh the table with updated data
                this.dispose();
                new MyGeneralTable(username);
        
                JOptionPane.showMessageDialog(this, "Book updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to update.");
            }
            // Clear the text fields after updating the book
            titleField.setText("");
            authorField.setText("");
            ratingField.setText("");
            reviewField.setText("");
        }
        
        
        
       
    }
    

    private void filterTable() {
    String searchText = searchField.getText().trim().toLowerCase();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) MyGeneralTable.table.getModel());
    MyGeneralTable.table.setRowSorter(sorter);
    if (searchText.length() == 0) {
        sorter.setRowFilter(null);
    } else {
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }  
}
}

