import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class PersonalTable extends JFrame {
    String username;
    static JTable table;
    Object[][] data;
    PanelForPersonalDB personalDataPanel;

    PersonalTable(String username) {
        this.username = username;
        // Initialize the hashmap first
        PersonalDatabase.userPersonalBooks = new HashMap<>();
        // Load personal books from CSV file
        PersonalDatabase.userPersonalBooks = PersonalDatabase.loadPersonalBooksFromCSV("personal_books.csv");
        setTitle("Personal Database");
        setBounds(500, 240, 800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        data = getPersonalData();
        String[] columnName = {"Title", "Author", "Rating", "Review", "Status", "Time spent", "Start Date", "End Date", "User Rating", "User Review"};

        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnName);
        table = new JTable(defaultTableModel);
        Font headerFont = table.getTableHeader().getFont();
        Font newHeaderFont = headerFont.deriveFont(Font.BOLD, 16);
        table.getTableHeader().setFont(newHeaderFont);

        //SortColumns
        FilterAndSorter.SortSelected(table);
        personalDataPanel = new PanelForPersonalDB(username);
        personalDataPanel.getPanel().setPreferredSize(new Dimension(380, getHeight()));
        add(personalDataPanel.getPanel(), BorderLayout.WEST);
        personalDataPanel.setExtendedState(JFrame.MAXIMIZED_BOTH);
        personalDataPanel.setUndecorated(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setVisible(true);
        

        // Add list selection listener to table
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    
            
                    // Use Object type to fetch the value and then convert to String as needed
                    Object title = table.getValueAt(selectedRow, 0);
                    Object author = table.getValueAt(selectedRow, 1);
                    Object rating = table.getValueAt(selectedRow, 2);  // This might be Integer
                    Object review = table.getValueAt(selectedRow, 3);
                    Object status = table.getValueAt(selectedRow, 4);
                    Object timeSpent = table.getValueAt(selectedRow, 5);
                    Object startDate = table.getValueAt(selectedRow, 6);
                    Object endDate = table.getValueAt(selectedRow, 7);
                    Object userRating = table.getValueAt(selectedRow, 8);
                    Object userReview = table.getValueAt(selectedRow, 9);
            
                    // Convert everything to String before setting it to the JTextField
                    personalDataPanel.titleField.setText(String.valueOf(title));
                    personalDataPanel.authorField.setText(String.valueOf(author));
                    personalDataPanel.ratingField.setText(String.valueOf(rating));
                    personalDataPanel.reviewField.setText(String.valueOf(review));
                    personalDataPanel.statusField.setText(String.valueOf(status));
                    personalDataPanel.timeSpentField.setText(String.valueOf(timeSpent));
                    personalDataPanel.startDateField.setText(String.valueOf(startDate));
                    personalDataPanel.endDateField.setText(String.valueOf(endDate));
                    personalDataPanel.userRatingField.setText(String.valueOf(userRating));
                    personalDataPanel.userReviewField.setText(String.valueOf(userReview));
                }
            }
            
            }
        );

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 9) { // Review column
                    String reviewText = (String) table.getValueAt(row, col);
                    if (reviewText.contains("<Click to See More>")) {
                        showFullReview(fetchFullReview((String)table.getValueAt(row, 0),(String)table.getValueAt(row, 1),username)); // Method to fetch full review text
                    }
                }
            }
        });
        

        
        
    }

    private void showFullReview(String fullReview) {
        JTextArea textArea = new JTextArea(fullReview);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaretPosition(0);
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Full Review", JOptionPane.INFORMATION_MESSAGE);
    }


protected static String fetchFullReview(String title, String author, String username) {
    String fullReview = "Review not found"; // Default message if no review is found
    try (BufferedReader br = new BufferedReader(new FileReader("personal_books.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] rev=line.split("\"");
            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (parts.length > 3 && parts[1].trim().equals(title) && parts[2].trim().equals(author) && parts[0].trim().equals(username)) {
                fullReview = rev[1]; // Assuming the review is at index 9
                fullReview = fullReview.replace("\"", ""); // Removing potential quotes around the review
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return fullReview;
}




    private Object[][] getPersonalData() {
   
        ArrayList<String[]> personalBooks = PersonalDatabase.userPersonalBooks.getOrDefault(username, new ArrayList<>());
        Object[][] data = new Object[personalBooks.size()][10]; // Adjusted to 10 columns
        for (int i = 0; i < personalBooks.size(); i++) {
            String[] book = personalBooks.get(i);
            for (int j = 0; j < 10; j++) {  // Start from index 0
                if (j < book.length) {
                    book[j]=book[j].replace("\"", "");
                    // If the current index is within the bounds of the book array, use its value
                            data[i][j] = book[j]; // Assign the parsed rating

                    if (j == 9) {  // Assuming review is at index 9
                        String review = book[j];
                        // Check if review length exceeds 50 characters
                        if (review.length() > 15) {
                            review = review.substring(0, 15) + "... <Click to See More>";
                        }
                        data[i][j] = review;
                    }
                    
                } else {
                    // If the current index is beyond the bounds of the book array, use default values
                    switch (j) {
                        case 4:
                            data[i][j] = "Not started"; // Status
                            break;
                        case 5:
                            data[i][j] = ""; // Time spent
                            break;
                        case 6:
                        case 7:
                            data[i][j] = ""; // Start Date and End Date
                            break;
                        case 8:
                            data[i][j] = ""; // User Rating
                            break;
                        case 9:
                            data[i][j] = "Add review"; // User Review 
                            break;
                        default:
                            data[i][j] = ""; // Default value for other fields
                    }
                }
            }
        }
        return data;
    }
    
    
    

    public static void updateBook(int selectedRow, String[] newBook) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < newBook.length; i++) {
            model.setValueAt(newBook[i], selectedRow, i);
        }
    }
    

    public void refreshTableData() {
        // Load updated data
        PersonalDatabase.userPersonalBooks = PersonalDatabase.loadPersonalBooksFromCSV("personal_books.csv");
        data = getPersonalData();  // Rebuild the data array from the updated books map
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(data, new String[]{"Title", "Author", "Rating", "Review", "Status", "Time spent", "Start Date", "End Date", "User Rating", "User Review"});
    }
    
}
