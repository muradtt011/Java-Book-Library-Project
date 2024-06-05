import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class PanelForPersonalDB extends JFrame implements ActionListener {
    private JPanel panel;
    protected JComboBox<String> statusComboBox;
    protected JTextField searchField;
    protected JTextField titleField;
    protected JTextField authorField;
    protected JTextField ratingField;
    protected JTextField reviewField;
    protected JTextField statusField;
    protected JTextField timeSpentField;
    protected JTextField startDateField;
    protected JTextField endDateField;
    protected JTextField userRatingField;
    protected JTextField userReviewField;
    private JButton addBtn;
    protected JButton deleteBtn;
    private JButton updateBtn;
    private static JButton generalDbButton;


    private String username;

    public PanelForPersonalDB(String username) {
        this.username = username;
        panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        titleField = new JTextField(20);
        authorField = new JTextField(20);
        ratingField = new JTextField(20);
        reviewField = new JTextField(20);
        statusField = new JTextField(20);
        timeSpentField = new JTextField(20);
        startDateField = new JTextField(20);
        endDateField = new JTextField(20);
        userRatingField = new JTextField(20);
        userReviewField = new JTextField(20);

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        generalDbButton = new JButton("GeneralDB");
        String[] statuses = {"Select","Not Started", "Ongoing", "Completed"};
        statusComboBox = new JComboBox<>(statuses);


        JLabel operationsLabel = new JLabel("Operations:");
        operationsLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(operationsLabel, gbc);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(authorLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(ratingLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(ratingField, gbc);

        JLabel reviewLabel = new JLabel("Review:");
        reviewLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(reviewLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(reviewField, gbc);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(statusLabel, gbc);
        gbc.gridwidth = 4;
        gbc.gridx = 1;
        panel.add(statusComboBox, gbc);
        

        JLabel timeSpentLabel = new JLabel("Time spent: ");
        timeSpentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(timeSpentLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(timeSpentField, gbc);

        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(startDateLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(startDateField, gbc);

        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(endDateLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(endDateField, gbc);

        JLabel userRatingLabel = new JLabel("Your Rating:");
        userRatingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(userRatingLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(userRatingField, gbc);

        JLabel userReviewLabel = new JLabel("Your Review:");
        userReviewLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(userReviewLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        panel.add(userReviewField, gbc);

        addBtn.setBounds(50, 620, 80, 35);
        addBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addBtn.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(addBtn, gbc);

        updateBtn.setBounds(140, 620, 80, 35);
        updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        updateBtn.addActionListener(this);
        gbc.gridx = 1;
        panel.add(updateBtn, gbc);

        deleteBtn.setBounds(230, 620, 80, 35);
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
        deleteBtn.addActionListener(this);
        gbc.gridx = 2;
        panel.add(deleteBtn, gbc);


        generalDbButton.setBounds(250, 700, 150, 80);
        generalDbButton.setFont(new Font("Arial", Font.BOLD, 14));
        generalDbButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(generalDbButton, gbc);



        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));

// Add the "Search" label
        gbc.gridx = 0;
        gbc.gridy++; // Move to the next row
        gbc.gridwidth = 1; // Span only one column
        panel.add(searchLabel, gbc);

// Create and add the search field
        searchField = new JTextField(20); // Reduced width to 10 characters
        gbc.gridx = 1; // Move to the next column
        gbc.gridwidth = 3; // Span across two columns
        panel.add(searchField, gbc);
    

        // searchField = new JTextField(20);
        //  gbc.gridx = 0;
        //  gbc.gridy++;
        // gbc.gridwidth = 3; // Span across three columns
        // panel.add(searchField, gbc);


       
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




        timeSpentField.setEditable(false);
        ratingField.setEditable(false);
        reviewField.setEditable(false);
        authorField.setEditable(false);
        titleField.setEditable(false);


    statusComboBox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateDateFieldsBasedOnStatus();
        }
    });



    
    setupDateValidation(startDateField);
    setupDateValidation(endDateField);
    setupDateListeners();
        add(panel);
    }

    private void updateDateFieldsBasedOnStatus() {
        String selectedStatus = (String) statusComboBox.getSelectedItem();
        switch (selectedStatus) {
            case "Not Started":
                startDateField.setEditable(false);
                endDateField.setEditable(false);
                startDateField.setText("");
                endDateField.setText("");
                break;
            case "Ongoing":
                startDateField.setEditable(true);
                endDateField.setEditable(false);
                endDateField.setText("Not Finished");
                break;
            case "Completed":
                startDateField.setEditable(true);
                endDateField.setEditable(true);
                break;
        }
    }
    private void setupDateValidation(JTextField dateField) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        dateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = dateField.getText();
                try {
                    LocalDate date = LocalDate.parse(text, dateFormatter);
                    dateField.setText(date.format(dateFormatter));  // Reformat to ensure consistent formatting
                } catch (DateTimeParseException ex) {
                    dateField.setText("");
                }
            }
        });
    
        dateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_SLASH && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();  // Ignore non-digit and non-slash characters
                } else {
                    String currentText = dateField.getText();
                    int slashCount = (int) currentText.chars().filter(ch -> ch == '/').count();
                    
                    if (Character.isDigit(c)) {
                        // Append slash after day and month digits if needed
                        if ((currentText.length() == 2 || currentText.length() == 5) && slashCount < 2) {
                            dateField.setText(currentText + "/" + c);
                            e.consume();
                        }
                        // Limit input length and enforce correct format
                        else if (currentText.length() >= 10) {
                            e.consume();
                        }
                    } else if (c == KeyEvent.VK_SLASH && slashCount >= 2) {
                        e.consume();  // Limit to two slashes
                    }
                }
            }
            
        
            @Override
            public void keyPressed(KeyEvent e) {
                // To handle backspace correctly when the cursor is after a slash
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    String text = dateField.getText();
                    if (text.length() > 0 && text.charAt(text.length() - 2) == '/') {
                        dateField.setText(text.substring(0, text.length() - 1));
                    }
                }
            }
        });
    }
     // Assuming startDateField and endDateField are the JTextFields for start date and end date respectively
     private void validateAndCalculateDates() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate now = LocalDate.now(); // Current date
        LocalDate startDate = null;
        LocalDate endDate = null;
        
    
        // Parse the dates from the input fields
        try {
            if (!startDateField.getText().isEmpty() && !startDateField.getText().equals("dd/MM/yyyy")) {
                startDate = LocalDate.parse(startDateField.getText(), dateFormatter);
                System.out.println("Parsed Start Date: " + startDate);
            }
            if (!endDateField.getText().isEmpty() && !endDateField.getText().equals("dd/MM/yyyy")) {
                endDate = LocalDate.parse(endDateField.getText(), dateFormatter);
                System.out.println("Parsed End Date: " + endDate);
            }
        } catch (DateTimeParseException e) {
            return;
        }
    
        // Validate that dates are not in the future
        if ((startDate != null && startDate.isAfter(now)) || (endDate != null && endDate.isAfter(now))) {
            JOptionPane.showMessageDialog(this, "Dates cannot be in the future.", "Date Error", JOptionPane.ERROR_MESSAGE);
            if (startDate != null && startDate.isAfter(now)) {
                startDateField.setText(""); // Clear start date if it's in the future
            }
            if (endDate != null && endDate.isAfter(now)) {
                endDateField.setText(""); // Clear end date if it's in the future
            }
            return;
        }
    
        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                JOptionPane.showMessageDialog(this, "Start Date cannot be after End Date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                startDateField.setText(endDate.format(dateFormatter)); // Reset Start Date
                return;
            }
    
            // Calculate time spent in days and update the relevant table row
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            int selectedRow = PersonalTable.table.getSelectedRow();
            if (selectedRow != -1) {
                PersonalTable.table.setValueAt(daysBetween, selectedRow, 5); // Assuming the Time Spent column is at index 5
            }
        }
    }
    
    private void setupDateListeners() {
        FocusAdapter dateFocusAdapter = new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                validateAndCalculateDates();
            }
        };
    
        startDateField.addFocusListener(dateFocusAdapter);
        endDateField.addFocusListener(dateFocusAdapter);
    }
    

    public static boolean isValidDate(String dateStr) {
        if(dateStr=="Not Started" || dateStr=="Not Finished")
        {
            return true;
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    



    

    public JPanel getPanel() {
        return panel;
    }

    public static void disabled() {
        generalDbButton.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == generalDbButton) {
            new MyGeneralTable(username);
            this.dispose();
        }
        else if(e.getSource()==addBtn) {
            // Retrieve data from text fields
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String rating = ratingField.getText().trim();
            String review = reviewField.getText().trim();
            String status = statusField.getText().trim();
            String timeSpent = timeSpentField.getText().trim();
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();
            String userRating = userRatingField.getText().trim();
            String userReview = userReviewField.getText().trim();
        
            // Validate user rating
            try {
                int userRatingInt = Integer.parseInt(userRating);
                if (userRatingInt < 1 || userRatingInt > 5) {
                    JOptionPane.showMessageDialog(this, "You can only add an integer between 1 and 5 to the rating.");
                    return; // Stop execution if rating is invalid
                }
            } catch (NumberFormatException ex) {
                if(userRating.trim()!="Add rating"){
                    JOptionPane.showMessageDialog(this, "You can only add an integer between 1 and 5 to the rating.");
                    }
                return; // Stop execution if rating is not an integer
            }
        
            // Create the new book array
            String[] newBook = {title, author, rating, review, status, timeSpent, startDate, endDate, userRating, userReview};
        
            // Add the book to the database

            if(newBook[7]!="Not Finished"&&((!isValidDate(newBook[6]) && !newBook[6].isEmpty()) || (!isValidDate(newBook[7]) && !newBook[7].isEmpty()))) 
            {
                JOptionPane.showMessageDialog(this, "Not proper date(s).");
            }
            else{
                PersonalDatabase.addBookToPersonalDB(this, username, newBook);
                JOptionPane.showMessageDialog(this, "Book updated successfully.");
            }

        
            // Add the book to the table model
            DefaultTableModel model = (DefaultTableModel) PersonalTable.table.getModel();
            model.addRow(newBook);
        
            JOptionPane.showMessageDialog(this, "Book added successfully.");
        
            // Clear the text fields after adding the book
            emptyFields();
        }
     
     
        else if (e.getSource() == updateBtn) {
            int selectedRow = PersonalTable.table.getSelectedRow();
            int userRatingInt;
            if (selectedRow != -1) {
                String[] oldBook = getBookFromTable(selectedRow); // Implement this method to fetch the book data from the table
                String[] newBook = gatherBookDataFromFields(); // Implement this method to fetch new book data from input fields
                try {
                    userRatingInt = Integer.parseInt(newBook[8]);
                    if (userRatingInt < 1 || userRatingInt > 5) {
                        JOptionPane.showMessageDialog(this, "You can only add an integer between 1 and 5 to the rating.");
                        return; // Stop execution if rating is invalid
                    }
                } catch (NumberFormatException ex) {
                    if(newBook[8].trim()!="Add rating"){
                    JOptionPane.showMessageDialog(this, "You can only add an integer between 1 and 5 to the rating.");
                    }
                    return; // Stop execution if rating is not an integer
                }
                // Call update method with the row index
                if(newBook[7]!="Not Finished"&&((!isValidDate(newBook[6]) && !newBook[6].isEmpty()) || (!isValidDate(newBook[7]) && !newBook[7].isEmpty()))) 
                {
                    JOptionPane.showMessageDialog(this, "Not proper date(s).");
                }
                else{
                PersonalDatabase.updateBookUser(username, oldBook, newBook, selectedRow);
                JOptionPane.showMessageDialog(this, "Book updated successfully.");
                }
                PersonalTable.updateBook(selectedRow, newBook);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to update.");
            }
            emptyFields();
        }
          
        
        else if (e.getSource() == deleteBtn) {
            int selectedRow = PersonalTable.table.getSelectedRow();
            if (selectedRow != -1) {
                // Retrieve the book information from the selected row
                String title = (String) PersonalTable.table.getValueAt(selectedRow, 0);
                String author = (String) PersonalTable.table.getValueAt(selectedRow, 1);
                String rating = (String) PersonalTable.table.getValueAt(selectedRow, 2);
                String review = (String) PersonalTable.table.getValueAt(selectedRow, 3);
                String status = (String) PersonalTable.table.getValueAt(selectedRow, 4);
                String timeSpent = (String) PersonalTable.table.getValueAt(selectedRow, 5);
                String startDate = (String) PersonalTable.table.getValueAt(selectedRow, 6);
                String endDate = (String) PersonalTable.table.getValueAt(selectedRow, 7);
                String userRating = (String) PersonalTable.table.getValueAt(selectedRow, 8);
                String userReview = (String) PersonalTable.table.getValueAt(selectedRow, 9);
    
                // Create the book array to delete
                String[] bookToDelete = {title, author, rating, review, status, timeSpent, startDate, endDate, userRating, userReview};
    
                // Delete the book from the personal database
                PersonalDatabase.deleteBookFromPersonal(username, bookToDelete);
    
                // Remove the selected row from the table
                DefaultTableModel model = (DefaultTableModel) PersonalTable.table.getModel();
                model.removeRow(selectedRow);
    
                JOptionPane.showMessageDialog(this, "Book deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }

            emptyFields();
        }
    }

    private void emptyFields()
    {
        titleField.setText("");
        authorField.setText("");
        ratingField.setText("");
        reviewField.setText("");
        statusField.setText("");
        timeSpentField.setText("");
        startDateField.setText("");
        endDateField.setText("");
        userRatingField.setText("");
        userReviewField.setText("");
    }


    private void filterTable() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (PersonalTable.table != null) {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) PersonalTable.table.getModel());
            PersonalTable.table.setRowSorter(sorter);
            if (searchText.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
            }
        } else {
            // Handle the case where MyGeneralTable.table is null
            System.err.println("MyGeneralTable.table is null");
        }
    }


    public void updateTableModel(int rowIndex, String[] newBookData) {
        DefaultTableModel model = (DefaultTableModel) PersonalTable.table.getModel();
        for (int i = 0; i < newBookData.length; i++) {
            model.setValueAt(newBookData[i], rowIndex, i);
        }
    }
  
  
    String[] getBookFromTable(int selectedRow) {
        // Using Object to fetch the value and then converting it to String as needed
        Object title = PersonalTable.table.getValueAt(selectedRow, 0);
        Object author = PersonalTable.table.getValueAt(selectedRow, 1);
        Object oldRating = PersonalTable.table.getValueAt(selectedRow, 2);  // This might be Integer
        Object review = PersonalTable.table.getValueAt(selectedRow, 3);
        Object status = PersonalTable.table.getValueAt(selectedRow, 4);
        Object timeSpent = PersonalTable.table.getValueAt(selectedRow, 5);
        Object startDate = PersonalTable.table.getValueAt(selectedRow, 6);
        Object endDate = PersonalTable.table.getValueAt(selectedRow, 7);
        Object userRating = PersonalTable.table.getValueAt(selectedRow, 8);
        Object userReview = PersonalTable.table.getValueAt(selectedRow, 9);
    
        // Convert everything to String before storing it in the array
        String[] oldBook = {
            String.valueOf(title),
            String.valueOf(author),
            String.valueOf(oldRating),
            String.valueOf(review),
            String.valueOf(status),
            String.valueOf(timeSpent),
            String.valueOf(startDate),
            String.valueOf(endDate),
            String.valueOf(userRating),
            String.valueOf(userReview)
        };
    
        return oldBook;
    }


    String[] gatherBookDataFromFields()
    {
    // Getting new book data from the input fields (make sure to include your rating correctly)
    String[] newBook = {
        titleField.getText().trim(),
        authorField.getText().trim(),
        ratingField.getText().trim(), // Ensure this captures the actual rating input correctly
        reviewField.getText().trim(),
        (String)statusField.getSelectedText(),
        timeSpentField.getText().trim(),
        startDateField.getText().trim(),
        endDateField.getText().trim(),
        userRatingField.getText().trim(), // This should be correctly parsing the input
        userReviewField.getText().trim()
    };
    return newBook;
}
    

}
