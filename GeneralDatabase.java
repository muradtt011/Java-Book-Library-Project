import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GeneralDatabase {
    public static MyGeneralTable myGeneraltable;

    public static void main(String[] args) {;

    }

}

class MyGeneralTable extends JFrame {

    
    static JTable table;
    private PanelForGeneralDB panelForGeneralDB;
    private Object[][] data;
    private String username;
    private JTextField titleField, authorField, ratingField, reviewField;

    public MyGeneralTable(String username) {
        this.username = username;
        setBounds(500, 240, 800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        data = getData();
        String[] columnName = {"Title", "Author", "Rating", "Review"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnName);
        table = new JTable(defaultTableModel);
        FilterAndSorter.SortSelected(table);

        titleField = new JTextField();
        authorField = new JTextField();
        ratingField = new JTextField();
        reviewField = new JTextField();

        PanelForGeneralDB panelForGeneralDB = new PanelForGeneralDB(username);
        panelForGeneralDB.getPanel().setPreferredSize(new Dimension(380, getHeight()));
        add(panelForGeneralDB.getPanel(), BorderLayout.WEST);
        if (username.equals("admin")) {
            PanelForGeneralDB.disabled();
        }
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        panelForGeneralDB.titleField.setText((String) table.getValueAt(selectedRow, 0));
                        panelForGeneralDB.authorField.setText((String) table.getValueAt(selectedRow, 1));
                        panelForGeneralDB.ratingField.setText((String) table.getValueAt(selectedRow, 2));
                        panelForGeneralDB.reviewField.setText((String) table.getValueAt(selectedRow, 3));
                    }
                }
            }
        });

        Font headerFont = table.getTableHeader().getFont();
        Font newHeaderFont = headerFont.deriveFont(Font.BOLD, 16);
        table.getTableHeader().setFont(newHeaderFont);


        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 3) {  // Assuming review column index is 3
                    String html = "<html><body style='width: 200px;'>" + value.toString() + "</body></html>";
                    setText(html);
                }
                return this;
            }
        });

        // Add mouse listener to the table to handle clicks on the review column
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 3 && row != -1) { // Review column
                    String reviewText = (String) table.getValueAt(row, col);
                    handleReviewClick(reviewText, e.getX(), e.getY());
                }
            }
        });
    
        


        // Make the review column clickable
        table.getColumnModel().getColumn(3).setCellRenderer(new ReviewCellRenderer());
        add(new JScrollPane(table));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        validate();

        setupTableMouseListener();
    }


    private void setupTableMouseListener() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 3 && row != -1) { // Assuming the review column index is 3
                    openUserDetailsDialog(row);
                }
            }
        });
    }

    
    // Custom cell renderer for clickable usernames in the review column
    static class ReviewCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 3 && value != null) { // Ensure this is the review column
                String[] users = value.toString().split(","); // Assuming usernames are comma-separated
                StringBuilder sb = new StringBuilder("<html>");
                for (String user : users) {
                    sb.append("<a href='#' style='color: blue;'>").append(user.trim()).append("</a> ");
                }
                sb.append("</html>");
                setText(sb.toString());
            } else {
                setText(value.toString());
            }
            return this;
        }
    }

    
    public void openReviewDetails(String reviewText) {
        JTextArea textArea = new JTextArea(reviewText);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Review Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    private void openUserDetailsDialog(int rowIndex) {
        String title = (String) table.getValueAt(rowIndex, 0);
        String author = (String) table.getValueAt(rowIndex, 1);
        String[] reviews = fetchReviews(title, author); // Assuming this method fetches all reviews for the book
        String rating = PersonalDatabase.getRating(username, title, author); // Fetch rating
        
        // Display each review separately
        for (String review : reviews) {
            String username = extractUsernameFromReview(title, author, review); // Extract username for this review
            UserDetailsDialog userDetails = new UserDetailsDialog(this, username, title, author, rating, new String[]{review});
            userDetails.setVisible(true);
        }
    }
    

    protected static String[] fetchReviews(String title, String author) {
        ArrayList<String> reviewsList = new ArrayList<>(); // ArrayList to store reviews
        try (BufferedReader br = new BufferedReader(new FileReader("personal_books.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rev = line.split("\"");
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (parts.length > 3 && parts[1].trim().equals(title) && parts[2].trim().equals(author)) {
                    if (rev.length > 1) { // Check if a review is found
                        String review = rev[1].replace("\"", ""); // Remove potential quotes around the review
                        reviewsList.add(review); // Add the review to the list
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Convert ArrayList to array
        String[] reviewsArray = new String[reviewsList.size()];
        reviewsArray = reviewsList.toArray(reviewsArray);
        return reviewsArray;
    }
    
    
    
    private String extractUsernameFromReview(String title,String author, String review) {
        String username="No username found";
        // Try to open and read the CSV file where reviews are stored
        try (BufferedReader br = new BufferedReader(new FileReader("personal_books.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] reviewInFile = line.split("\"");
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                // Check if the current line's title and author match the requested review
                if (parts.length > 3 && parts[1].trim().equals(title) && parts[2].trim().equals(author) && reviewInFile[1].trim().equals(review)) {
                    
                    // Assuming the review is stored in a quoted section to handle commas within the review text
                    // Removing potential quotes around the review
                    username=parts[0];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    
        return username;
    }
    

    public static void updateBook(int rowIndex, String[] newBook) {
        try {
            File file = new File("generalDB.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                line=line.replace(",",".");
                lines.add(line);
            }
            br.close();
    
            // Remove both the old line and updated line corresponding to the selected row
            lines.remove(rowIndex); // Add 1 to rowIndex to skip the header line
    
            // Insert the updated line into the list of lines
            lines.add(rowIndex + 1, String.join(",", newBook));
    
            // Write the updated lines back to the file
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false)); // false for overwriting
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();
    
            // Update the table model to reflect the changes
            DefaultTableModel model = (DefaultTableModel) MyGeneralTable.table.getModel();
            // Remove the old row
            model.removeRow(rowIndex);
            // Insert the updated row
            model.insertRow(rowIndex, newBook);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
public static void refreshTable() {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); // Clear the existing rows
    Object[][] newData = getData();
    for (Object[] row : newData) {
        model.addRow(row); // Add the updated data to the table
    }
}
    
private void handleReviewClick(String reviewText, int x, int y) {
        openUserDetail(reviewText.trim());
}

private void openUserDetail(String username) {
    JOptionPane.showMessageDialog(this, "Details for: " + username);
}

    

    public static void deleteBook(int rowIndex) {
        try {
            File file = new File("generalDB.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
    
            // Remove the corresponding line from the ArrayList
            lines.remove(rowIndex + 1); // Add 1 to rowIndex to skip the header line
    
            // Reopen the file for writing, this time in append mode
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false)); // false for overwriting
            for (String l : lines) {
                l=l.replace(",", ".");
                bw.write(l);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // Method to add a new book
    public static boolean addBook(String[] book) {
        // Check if all fields of the new book are empty
        boolean allFieldsEmpty = true;
        for (String field : book) {
            if (!field.isEmpty()) {
                allFieldsEmpty = false;
                break;
            }
        }
    
        // If all fields are empty, don't add the book
        if (allFieldsEmpty) {
            return false;
        }
    
        // Fill in empty fields with default values
        for (int i = 0; i < book.length; i++) {
            if (book[i].isEmpty()) {
                book[i] = (i == 0) ? "Unknown" : (i==1 ? "Unknown":"No " + (i == 2 ? "rating" : "review"));
            }
        }
    
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(book);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("generalDB.csv", true)); // Append mode
            bw.write(String.join(",", book));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return true;
    }
    


    public void setTextFields(JTextField titleField, JTextField authorField, JTextField ratingField, JTextField reviewField) {
        this.titleField = titleField;
        this.authorField = authorField;
        this.ratingField = ratingField;
        this.reviewField = reviewField;
    }

    static Object[][] getData() {
        File generalDBFile = new File("generalDB.csv");
        if (!generalDBFile.exists()) {
            // Only import data from brodsky.csv if generalDB.csv doesn't exist
            try {
                BufferedReader br = new BufferedReader(new FileReader("brodsky.csv"));
                BufferedWriter bw = new BufferedWriter(new FileWriter("generalDB.csv"));
                bw.write("Title,Author,Rating,Review"); // Corrected formatting
                bw.newLine();
                ArrayList<Object[]> list = new ArrayList<>();
                String str;
                int count = 0;
                while ((str = br.readLine()) != null) {
                    if (count != 0) {
                        String[] parts;
                        String[] authorBooks = str.split("\"");
                        if (authorBooks.length > 1) {
                            String author = (authorBooks[authorBooks.length - 1].trim().length() > 0) ? authorBooks[authorBooks.length - 1].trim() : "Unknown";
                            if (author.contains(",")) {
                                author = author.replace(",", "");
                            }
                            parts = authorBooks[1].split(",");
                            String rating = "No rating";
                            String review = "No review";
                            for (String title : parts) {
                                title = title.replace("[", "");
                                title = title.replace("]", "");
                                list.add(new Object[]{title.trim(), author, rating, review});
                                bw.write(title.trim() + "," + author + "," + rating + "," + review);
                                bw.newLine();
                            }
                        } else {
                            parts = str.split(",");
                            String title = parts.length > 0 && !parts[0].trim().isEmpty() ? parts[0].trim() : "Unknown";
                            String author = parts.length > 1 ? parts[1].trim() : "Unknown";
                            String rating = "No rating";
                            String review = "No review";
                            title = title.replace("[", "");
                            title = title.replace("]", "");
                            list.add(new Object[]{title.trim(), author, rating, review});
                            bw.write(title.trim() + "," + author + "," + rating + "," + review);
                            bw.newLine();
                        }
                    }
                    count++;
                }
                br.close();
                bw.close();
        
                Object[][] data = new Object[list.size()][4]; // Changed size to 4 for the correct number of columns
                for (int i = 0; i < list.size(); i++) {
                    data[i] = list.get(i);
                }
                return data;
        
            } catch (Exception x) {
                x.printStackTrace();
                return null;
            }
        } else {
            // If generalDB.csv already exists, simply read data from it
            try {
                BufferedReader br = new BufferedReader(new FileReader("generalDB.csv"));
                ArrayList<Object[]> list = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    String average=(PersonalDatabase.averageRating("personal_books.csv",parts)+" ("+PersonalDatabase.countRating("personal_books.csv",parts)+")");
                    if(average.contains(","))
                    {
                        average=average.replace(",", ".");
                    }
                    String rating = PersonalDatabase.countRating("personal_books.csv",parts)>0 ? average :"No rating";
                    parts[2]=rating;
                    parts[3]= PersonalDatabase.findUsersWithReview("personal_books.csv", parts).length()>0? PersonalDatabase.findUsersWithReview("personal_books.csv", parts):"No review";
                    list.add(parts);
                }
                br.close();
                Object[][] data = new Object[list.size()][4]; // Changed size to 4 for the correct number of columns
                for (int i = 0; i < list.size(); i++) {
                    data[i] = list.get(i);
                }
                return data;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
}
}



