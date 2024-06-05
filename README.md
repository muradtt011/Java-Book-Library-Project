https://youtu.be/-spMlaEZVXE


# LoginFrame.java

### 1. Imports:

o The code imports necessary classes from javax.swing, java.awt, and java.io packages.

### 2. Main Class (LoginFrame):

o Contains a main method that creates an instance of LoginFrame and displays it.

### 3. Constructor:

o Initializes the login frame with a title and size.
o Constructs the login interface with labels for username and password, text fields for input, and buttons for login, registration, and password renewal.
o Adds action listeners to the login, registration, and password renewal buttons.

### 4. Action Handling (actionPerformed method):

o Handles actions performed by the user, such as clicking the login, registration, or password renewal buttons.
o If the login button is clicked:
 Validates the entered username and password against the credentials stored in a CSV file named "users.csv".
 If the credentials are valid, it opens an option database window (OptionDatabase).
 If the credentials are invalid, it displays an error message.
o If the registration button is clicked, it opens a registration page (RegistrationPage).
o If the password renewal button is clicked, it opens a password renewal page (ForgotPassword).

### 5. Helper Method (checkCredentials):

o Reads the username-password pairs from the "users.csv" file.
o Checks if the entered username and password match any of the pairs in the file.
o Returns true if the credentials are valid, false otherwise.

# RegistrationPage.java

### 1. User Interface:

o It creates a JFrame titled "Register" with various input fields for registration, including username, password, password confirmation, and a favorite word.
o The registration form includes buttons for registration (registerButton) and returning to the login page (loginFrameBtn).

### 2. Event Handling:

o The class implements the ActionListener interface to handle button clicks.
o When the "Register" button is clicked (registerButton), it validates the entered data and registers the user if the data is valid.
o If the "Back to Login" button is clicked (loginFrameBtn), it closes the registration window and opens the login window.

### 3. Registration Logic:

o The registerUser method validates the entered username and password.
o It checks if the username is not already in use and if the password meets certain criteria (e.g., length, complexity).
o If the registration is successful, it appends the user's information to a CSV file (users.csv), which stores user credentials.

### 4. Password Reset:

o The resetPassword method allows resetting the password for a given username. It updates the password in the users.csv file.

### 5. Favorite Word Retrieval:

o The getFavoriteWord method retrieves the favorite word associated with a given username from the users.csv file.

# ForgotPassword.java

### 1. Imports:

The code imports necessary classes from javax.swing and java.awt packages.

### 2. Class Declaration:

The ForgotPassword class is declared, which extends JFrame and implements the ActionListener interface to handle button click events.

### 3. GUI Components:

o Several Swing components are initialized, including labels (JLabel), text fields (JTextField), password fields (JPasswordField), and buttons (JButton).
o These components are added to the frame (JFrame) using a GridBagLayout for organized layout.

### 4. Constructor:

o Sets the title of the frame to "Forgot Password".
o Sets the layout manager to GridBagLayout.
o Initializes and adds various components to the frame with specified constraints (GridBagConstraints).

### 5. Action Handling (actionPerformed method):

o Implements the actionPerformed method to handle button clicks.
o If the "Renew Password" button is clicked:
 Retrieves input values (username, password, password repetition, and favorite word) from the respective fields.
 Checks if the favorite word matches the one stored during registration.
 If the favorite word matches and the password matches its repetition:
 Resets the password for the given username.
 Displays a success message.
o If the passwords do not match, displays an error message.
o If the favorite word does not match, displays an error message.
o If the "Back to Login" button is clicked:
o Disposes the current frame (Forgot Password).
o Opens a new login frame (LoginFrame).

### 6. Helper Methods:

o checkFavoriteWord: Compares the provided favorite word with the one stored during registration to verify the user's identity.
o resetPassword: Resets the password for the given username and displays a message with the new password.

# OptionDatabase.java

### 1. Main Class (OptionDatabase):

o Contains a main method, which is currently commented out.
o Initializes the option database frame.

### 2. Constructor:

o Initializes the frame with a title, size, layout (set to null for manual positioning), and default close operation.
o Creates buttons for accessing the general and personal databases.
o Sets button positions and fonts.
o Adds action listeners to the buttons.

### 3. Action Handling (actionPerformed method):

o Handles button clicks.
o If the "General DB" button is clicked, it opens a new instance of MyGeneralTable, passing the username.
o If the "Personal DB" button is clicked, it opens a new instance of PersonalTable, passing the username.

### 4. Additional Notes:

o The username field is used to pass the username to the database frames.
o The frame is set to not resizable (setResizable(false)) and positioned at the center of the screen (setLocationRelativeTo(null)).

# GeneralDatabase.java

### 1. Imports:

The code imports necessary classes from javax.swing, java.awt, and java.io packages.

### 2. Main Class (GeneralDatabase):

o Declares a static variable myGeneraltable of type MyGeneralTable.
o Contains an empty main method.

### 3. MyGeneralTable Class (Subclass of JFrame):

o Defines the main GUI class responsible for displaying the database.
o Initializes a JTable to display book information.
o Adds a PanelForGeneralDB instance to the frame's west side for managing book entries.
o Implements logic for updating, deleting, and adding books to the database.
o Contains methods to handle refreshing the table, updating the book data, deleting a book entry, and adding a new book entry.

### 4. PanelForGeneralDB Class:

o Provides a panel for managing book entries.
o Contains fields for entering book title, author, rating, and review.
o Contains a constructor that initializes the panel with specified constraints.
o Provides a method to disable editing if the user is not an admin.

### 5. Data Management:

o Reads book data from a CSV file named "generalDB.csv".
o If the file doesn't exist, it imports data from another CSV file named "brodsky.csv" and writes it to "generalDB.csv".
o If the file already exists, it reads data directly from "generalDB.csv".

### 6. Event Handling:

o Listens to table selection events to populate text fields with selected book information.
o Listens to button clicks for updating, deleting, and adding books to the database.

### 7. Helper Methods:

o updateBook: Updates a book entry in the CSV file and refreshes the table.
o refreshTable: Refreshes the table with updated data.
o deleteBook: Deletes a book entry from the CSV file and refreshes the table.
o addBook: Adds a new book entry to the CSV file and refreshes the table.

# PanelForGeneralDB.java

### 1. Main Method:

o The main method is currently empty and unused.

### 2. Constructor:

o Initializes the panel with various Swing components such as labels, text fields, and buttons.
o Sets the layout to null for manual positioning of components.
o Adds action listeners to buttons.

### 3. Action Handling (actionPerformed method):

o Handles actions performed by buttons.
o If the "PersonalDB" button is clicked, it opens a new instance of PersonalTable specific to the current user.
o If the "User Manager" button is clicked, it opens a new instance of UserControlTable.
o If the "Add to Personal Library" button is clicked, it checks if the book already exists in the personal library and adds it if not.
o If the "Add" button is clicked, it adds the book to the general database (MyGeneralTable) and clears the text fields.
o If the "Delete" button is clicked, it removes the selected row from the general database and personal database, if applicable.
o If the "Update" button is clicked, it updates the selected row in the general database and personal database, if applicable.

### 4. Filtering Method (filterTable):

o Implements a document listener on the search field to filter rows in the table based on the search text.
o Uses a TableRowSorter to dynamically filter rows in the table based on the search text entered by the user.

### 5. Utility Methods:

o disabled: Disables certain buttons and sets text fields editable based on user privileges.
o isAdmin: Checks if the user is an admin based on the username.

# User.java

### 1. Properties:

username: Represents the username of the user.
password: Represents the password of the user. It's marked as private to ensure encapsulation.

### 2. Constructor:

Initializes the User object with the provided username and password.

### 3. Getter and Setter Methods:

getUsername(): Returns the username of the user.
setUsername(String username): Sets the username of the user.
getPassword(): Returns the password of the user.
setPassword(String password): Sets the password of the user.

### 4. setUser Method:

Takes another User object as an argument and sets the current user's username and password based on the provided user.

### 5. isAdmin Method:

Checks if the provided username and password match the admin credentials (admin/admin). However, it's implemented incorrectly because it uses == for comparing strings, which compares references rather than string contents. It should use the equals() method for string comparison.

# UserControl.java

### 1. User Interface:

o It creates a JFrame titled "User Control" with a table displaying user information, including usernames, passwords, and favorite words.
o The table allows the administrator to select a user, and their details appear in a panel (UserControlPanel) on the left side for editing.

### 2. Data Loading:

o The getData method reads user information from a CSV file named "users.csv" and populates the table with this data.

### 3. User Deletion:

o The deleteUser method allows the administrator to delete a selected user from the table.
o It removes the corresponding row from the table model, deletes the user's personal library using the deleteUsersPersonalLibrary method from PersonalDatabase, and saves the updated user information to the CSV file.

### 4. Data Saving:

o The saveDataToFile method saves the updated user information (after deletion) back to the "users.csv" file.

# UserControlPanel.java

### 1. User Interface Creation:

o It creates a JPanel with fields to display and edit user information, including username, password, and favorite word.
o Additionally, it provides buttons for deleting a user and accessing the general database.

### 2. Action Handling:

o The actionPerformed method handles button clicks.
o If the "Delete User" button (deleteBtn) is clicked, it retrieves the username from the usernameField and calls the deleteUser method of UserControlTable to delete the user.
o If the "GeneralDB" button (generalDbButton) is clicked, it closes the current panel and opens the general database panel (MyGeneralTable).

### 3. Field Population:

o It provides methods (setUsernameField, setPasswordField, setFavoriteWordField) to populate the corresponding text fields with user information.

### 4. Panel Retrieval:

o It includes a getPanel method to retrieve the panel for adding it to other components.

# PanelForPersonalDB.java

### 1. Constructor:

o Initializes the panel with various Swing components such as labels, text fields, and buttons using the GridBagLayout for layout management.
o Initializes text fields for book attributes such as title, author, rating, review, status, time spent, start date, end date, user rating, and user review.
o Initializes buttons for adding, updating, and deleting books from the personal database, as well as navigating to the general database.

### 2. Action Handling (actionPerformed method):

o Handles actions performed by buttons.
o If the "GeneralDB" button is clicked, it opens a new instance of MyGeneralTable and disposes of the current frame.
o If the "Add" button is clicked, it retrieves data from text fields, adds the book to the personal database, updates the table model, and clears the text fields.
o If the "Update" button is clicked, it updates the selected row in the personal database, updates the table model, and clears the text fields.
o If the "Delete" button is clicked, it deletes the selected row from the personal database, removes it from the table model, and clears the text fields.

### 3. Utility Methods:

o emptyFields: Clears all text fields.
o filterTable: Filters rows in the personal database table based on the search text entered by the user.

# PersonalDatabase.java

### 1. Data Storage and Initialization:

o It initializes a HashMap named userPersonalBooks to store personal book data. Each user's books are stored as an ArrayList of String arrays.

### 2. Main Method:

o It loads personal books from a CSV file named "personal_books.csv" into the userPersonalBooks map.
o It then creates a new instance of PersonalTable with a placeholder username.

### 3. Utility Methods:

o hasDuplicateBook: Checks if a given book already exists in a user's personal library to avoid duplicates.
o updateBookAdmin: Updates a book's information across all users' personal libraries. It takes old and new book arrays as parameters.
o addBookToPersonalDB: Adds a book to a user's personal library. If any fields are empty, it notifies the user and doesn't add the book.
o deleteBook: Deletes a book from all users' personal libraries based on the book array provided.
o updateBookUser: Updates a book in a specific user's personal library. It removes the old book and adds the new one.
o deleteBookFromPersonal: Deletes a book from a specific user's personal library based on the book array provided.
o loadPersonalBooksFromCSV: Reads personal book data from a CSV file and returns it as a HashMap.
o savePersonalBooksToCSV: Writes personal book data to a CSV file. It merges existing data with new data and writes it to the file.
o deleteUsersPersonalLibrary: Deletes a user's entire personal library from the userPersonalBooks map and updates the CSV file.
These utility methods provide functionality for adding, updating, and deleting books from personal libraries, as well as loading and saving library data from/to CSV files.

# FilterAndSorter.java

### 1. Imports:

The code imports necessary classes from javax.swing and java.awt.event packages.

### 2. Class Declaration:

The FilterAndSorter class is declared, containing static methods and fields for sorting and filtering a JTable.

### 3. Fields:

o CountTitle and CountAuthor: These static integers are used to keep track of the sorting state for each column (Title and Author).
o originalData: An ArrayList of Object arrays used to store the original data of the table.

### 4. Sorting Method (Sorting):

o This method initializes the sorting functionality for the given JTable.
o It sets up a mouse listener (SortMouseListener) on the table header to detect clicks for sorting.

### 5. Initialization Method (initializeTable):

o This method initializes the originalData ArrayList by iterating over the rows and columns of the table and copying the data into it.

### 6. Nested Class (SortMouseListener):

o This is a nested class that extends MouseAdapter and is responsible for sorting the table when the column headers are clicked.
o It overrides the mouseClicked method to handle sorting logic based on the clicked column.
o It retrieves the clicked column index, creates a copy of the original data, and sorts it according to the clicked column.
o Sorting is toggled between ascending, descending, and original order based on the number of clicks.
o After sorting, the table model is updated with the sorted data.

### 7. Sorting Logic:

o Sorting is based on the clicked column (0 for Title, 1 for Author).
o The sorting order toggles between ascending, descending, and original order.
o The Comparator interface is used to define custom sorting logic for String values.

### 8. Restoring Original Data:

o If a column is clicked multiple times to cycle through sorting orders, the method restoreOriginalData is called to revert the table to its original data state.

# PersonalTable.java

### 1. Initialization:

o It initializes a JFrame with the title "Personal Database" and sets its dimensions.
o It loads personal book data from a CSV file into the userPersonalBooks map using the loadPersonalBooksFromCSV method.

### 2. Table Creation:

o It creates a JTable to display the personal book data.
o The table is populated with data retrieved from the userPersonalBooks map.
o Column names for the table are specified as an array of strings.
o A default table model is created using the data and column names.

### 3. Table Appearance:

o The font of the table header is customized using a larger and bold font.

### 4. Panel Initialization:

o It creates an instance of PanelForPersonalDB to display input fields for adding, updating, or deleting book data.
o The panel is added to the JFrame's west region and set to a preferred size.

### 5. Event Handling:

o A list selection listener is added to the table to update the input fields in the panel when a row is selected.
o When a row is selected, the data of the selected book is retrieved and displayed in the input fields of the panel.

### 6. Utility Methods:

o getPersonalData: Retrieves personal book data from the userPersonalBooks map and converts it into a two-dimensional array to populate the table.

### 7. Static Method:

o updateBook: Updates a specific row in the table with new book data. This method is called externally to update the table when a book is added or updated.
