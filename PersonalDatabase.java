import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Iterator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PersonalDatabase {
    static HashMap<String, ArrayList<String[]>> userPersonalBooks = new HashMap<>();

    public static void main(String[] args) {
        // Load personal books from CSV file
        userPersonalBooks = loadPersonalBooksFromCSV("personal_books.csv");
        new PersonalTable("username");
    }


    public static boolean hasDuplicateBook(String username, String[] newBook) {
        ArrayList<String[]> userBooks = userPersonalBooks.getOrDefault(username, new ArrayList<>());
        for (String[] book : userBooks) {
            if (book[0].equals(newBook[0]) && // Title
                    book[1].equals(newBook[1])) { // Review
                return true; // Found a duplicate
            }
        }
        return false; // No duplicate found
    }


    public static void updateBookAdmin(String[] oldBook, String[] newBook) {
        // Load existing personal books from CSV file
        HashMap<String, ArrayList<String[]>> personalBooks = loadPersonalBooksFromCSV("personal_books.csv");
    
        // Iterate through each user's books and update the book if found
        for (String username : personalBooks.keySet()) {
            ArrayList<String[]> userBooks = personalBooks.get(username);
            for (String[] book : userBooks) {
                if (Arrays.equals(Arrays.copyOfRange(book, 0, 4), oldBook)) {
                    // Update the first 4 elements of the book with the new information
                    System.arraycopy(newBook, 0, book, 0, 4);
                }
            }
            // Update the user's books after modifying
            personalBooks.put(username, userBooks);
        }
    
        // Save the updated personal books to the CSV file
        savePersonalBooksToCSV("personal_books.csv", personalBooks);
    }
    
    public static String getRating(String username, String author, String title)
    {
        String rating = "Rating not found"; // Default message if no review is found

        // Try to open and read the CSV file where reviews are stored
        try (BufferedReader br = new BufferedReader(new FileReader("personal_books.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                // Check if the current line's title and author match the requested review
                if (parts.length > 3 && parts[0].trim().equals(username) && parts[1].trim().equals(title) && parts[2].trim().equals(author)) {
                    
                    // Assuming the review is stored in a quoted section to handle commas within the review text
                    rating = parts[9]; // Removing potential quotes around the review
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    
        return rating;
    }
    

    public static boolean addBookToPersonalDB(JFrame database, String username, String[] book) {
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
            JOptionPane.showMessageDialog(database, "You cannot add nothing to your library.");
            return false;
        }
    
        // Load existing personal books from CSV file
        HashMap<String, ArrayList<String[]>> personalBooks = loadPersonalBooksFromCSV("personal_books.csv");
    
        ArrayList<String[]> userBooks = personalBooks.getOrDefault(username, new ArrayList<>());
    
        // Fill in empty fields with the previous values
        String[] newBook = new String[10];
        for (int i = 0; i < book.length; i++) {
            newBook[i] = book[i];
        }
        // Fill in missing fields with default values
        for (int i = book.length; i < 10; i++) {
            switch (i) {
                case 4:
                    newBook[i] = "Not started"; // Status
                    break;
                case 5:
                    newBook[i] = "Unknown"; // Time spent
                    break;
                case 8:
                    newBook[i] = "Add rating"; // User Rating
                    break;
                case 9:
                    newBook[i] = "Add review"; // User Review
                    break;
                default:
                    newBook[i] = ""; // Default value for other fields
            }
        }
        
        // Add the new book to the user's list of books
        userBooks.add(newBook);
        personalBooks.put(username, userBooks);
    
        // Save personal book information to CSV file every time a book is added
        savePersonalBooksToCSV("personal_books.csv", personalBooks);
        return true;
    }
    


    public static void deleteBook(String[] bookToDelete) {
        // Load existing personal books from CSV file
        HashMap<String, ArrayList<String[]>> personalBooks = loadPersonalBooksFromCSV("personal_books.csv");
    
        Iterator<Map.Entry<String, ArrayList<String[]>>> iterator = personalBooks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ArrayList<String[]>> entry = iterator.next();
            ArrayList<String[]> books = entry.getValue();
            Iterator<String[]> bookIterator = books.iterator();
            while (bookIterator.hasNext()) {
                String[] book = bookIterator.next();
                if (Arrays.equals(book, bookToDelete)) {
                    bookIterator.remove();
                    break; // Exit the loop once the book is deleted
                }
            }
        }
        // Save the updated personal books to the CSV file
        savePersonalBooksToCSV("personal_books.csv", personalBooks);
    }
   
    

    public static void updateBookUser(String username, String[] oldBook, String[] newBook, int selectedRow) {
        // Ensure the date strings are valid and parse them to LocalDate
        LocalDate startDate = null, endDate = null;
        int userRatingInt=Integer.parseInt(newBook[8]);
        if(userRatingInt<=5 && userRatingInt>=1)
        {
            double sum=(double)PersonalDatabase.sumRating("personal_books.csv", oldBook)+(double)userRatingInt;
            int counter=PersonalDatabase.countRating("personal_books.csv", oldBook)+1;
            double average=(double)sum/(double)counter;
            newBook[2]=String.valueOf(average)+" ("+String.valueOf(counter)+")";
        }
    
        // Check if the date strings are empty, and if so, set the corresponding LocalDate variables to null
        if (!newBook[6].isEmpty() && !newBook[7].isEmpty()) {
            if (!PanelForPersonalDB.isValidDate(newBook[6]) || !PanelForPersonalDB.isValidDate(newBook[7])) {
                JOptionPane.showMessageDialog(null, "Invalid date(s). Please ensure dates are correct and not in the future.", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                startDate = LocalDate.parse(newBook[6], formatter);
                endDate = LocalDate.parse(newBook[7], formatter);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Error parsing dates.", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    
        // Calculate the days between if both dates are valid
        if (startDate != null && endDate != null) {
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            newBook[5] = String.valueOf(daysBetween);  // Ensure this is assigned as a String
        }
    
        // Continue with updating the book as before
        HashMap<String, ArrayList<String[]>> personalBooks = loadPersonalBooksFromCSV("personal_books.csv");
        ArrayList<String[]> userBooks = personalBooks.getOrDefault(username, new ArrayList<>());

        userBooks.removeIf(book -> book[0].equals(oldBook[0]) && book[1].equals(oldBook[1]));


        try {
            int userRating = Integer.parseInt(newBook[8].trim()); // User's rating is assumed to be at index 8
            double currentSumRating = sumRating("personal_books.csv", newBook);
            int currentRatingCount = countRating("personal_books.csv", newBook);
            String userReview=newBook[9].trim();
    
            currentSumRating += userRating;
            currentRatingCount++;
    
            double newAvgRating = currentSumRating / currentRatingCount;
            newBook[2] = String.format("%.1f (%d)", newAvgRating, currentRatingCount);
            newBook[2]=newBook[2].replace(",", ".");
        } catch (NumberFormatException e) {
            // Handle error, log or notify user if needed
        }

        userBooks.add(newBook);
        personalBooks.put(username, userBooks);
        savePersonalBooksToCSV("personal_books.csv", personalBooks);
    
    
        // Update the table model directly if the index is valid
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) PersonalTable.table.getModel();
            for (int i = 0; i < newBook.length; i++) {
                model.setValueAt(newBook[i], selectedRow, i);
            }
        }
    }
    

    
    
    
    
    public static void deleteBookFromPersonal(String username, String[] bookToDelete) {
        ArrayList<String[]> userBooks = userPersonalBooks.getOrDefault(username, new ArrayList<>());
        ArrayList<String[]> booksToRemove = new ArrayList<>();
        for (String[] book : userBooks) {
            if (book[0].equals(bookToDelete[0]) && // Title
                book[1].equals(bookToDelete[1])) { // Review
                booksToRemove.add(book);
            }
        }
        userBooks.removeAll(booksToRemove);
        userPersonalBooks.put(username, userBooks);
        // Save personal book information to CSV file after deletion
        savePersonalBooksToCSV("personal_books.csv", userPersonalBooks);
    }
    

    protected static HashMap<String, ArrayList<String[]>> loadPersonalBooksFromCSV(String filename) {
        HashMap<String, ArrayList<String[]>> personalBooks = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] review=line.split("\"");
                String[] parts = line.split(",", -1); // Split on comma, keeping empty values
                if (parts.length >= 10) { // Ensure there are enough parts to avoid ArrayIndexOutOfBoundsException
                    String username = parts[0];
                    String[] book = new String[10]; // Ensure all books have exactly 10 elements
                    for (int i = 1; i <= 10; i++) { // Iterate up to index 9
                        book[i - 1] = parts[i]; // Fill book data
                    }
                    book[9] = review[1]; 
                    book[9]=book[9].replace("\"", "");
                    if (personalBooks.containsKey(username)) {
                        personalBooks.get(username).add(book);
                    } else {
                        ArrayList<String[]> books = new ArrayList<>();
                        books.add(book);
                        personalBooks.put(username, books);
                    }
                } else {
                    // Handle error or log warning about malformed data
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personalBooks;
    }
    
    

    private static void savePersonalBooksToCSV(String filename, HashMap<String, ArrayList<String[]>> personalBooks) {
        // Read existing contentw
        HashMap<String, ArrayList<String[]>> existingPersonalBooks = loadPersonalBooksFromCSV(filename);
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            // Merge existing content with new content
            existingPersonalBooks.putAll(personalBooks);
    
            // Write merged content to the file
            for (String username : existingPersonalBooks.keySet()) {
                for (String[] book : existingPersonalBooks.get(username)) {
                    book[2]=book[2].replace(",", ".");
                    book[9]="\""+book[9]+"\"";
                    bw.write(username + "," + String.join(",", book));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    


        public static void deleteUsersPersonalLibrary(String username) {
        Iterator<Map.Entry<String, ArrayList<String[]>>> iterator = userPersonalBooks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ArrayList<String[]>> entry = iterator.next();
            if (entry.getKey().equals(username)) {
                iterator.remove();
            } else {
                ArrayList<String[]> books = entry.getValue();
                for (int i = 0; i < books.size(); i++) {
                    String[] book = books.get(i);
                    if (book[0].equals(username)) {
                        books.remove(i);
                        i--;
                    }
                }
            }
        }
        savePersonalBooksToCSV("personal_books.csv", userPersonalBooks);
    }

    public static double sumRating(String filename, String[] book) {
        int sum = 0;
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // This regex will split on commas unless they are within quotes
                if (parts.length >= 10 && parts[1].trim().equals(book[0]) && parts[2].trim().equals(book[1])) { // Ensure there are enough parts and check title and author
                    try {
                        int rating = Integer.parseInt(parts[9].trim()); // Rating at index 8
                        sum += rating;
                        count++;
                    } catch (NumberFormatException e) {
                        // Ignore non-numeric ratings
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count > 0 ? (double) sum : 0; // Avoid division by zero and ensure there is at least one rating to average
    }



    public static double averageRating(String filename, String[] book) {
        int sum = 0;
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // This regex will split on commas unless they are within quotes
                if (parts.length >= 10 && parts[1].trim().equals(book[0]) && parts[2].trim().equals(book[1])) { // Ensure there are enough parts and check title and author
                    try {
                        int rating = Integer.parseInt(parts[9].trim()); // Rating at index 8
                        sum += rating;
                        count++;
                    } catch (NumberFormatException e) {
                        // Ignore non-numeric ratings
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count > 0 ? (double) sum / count : 0; // Avoid division by zero and ensure there is at least one rating to average
    }
    

public static String findUsersWithReview(String filename, String[] book) {
    Set<String> reviewers = new HashSet<>(); // Use a set to avoid duplicate usernames
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (parts.length >= 10) {
                if (parts[1].trim().equals(book[0]) && parts[2].trim().equals(book[1]) && !parts[9].trim().equals("Add review")) {
                    reviewers.add(parts[0].trim()); // Assuming the username is at index 0
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Convert the set of usernames to a comma-separated string
    return String.join(", ", reviewers);
}

    

    
    public static int countRating(String filename, String[] book) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 10) { // Ensure the line has enough elements
                    if (parts[1].equals(book[0]) && // Title
                            parts[2].equals(book[1])) { // Review
                        // Assuming rating is at index 8
                        try {
                            Integer.parseInt(parts[9].trim());
                            count++;
                        } catch (NumberFormatException e) {
                            // Ignore non-numeric ratings
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    

}