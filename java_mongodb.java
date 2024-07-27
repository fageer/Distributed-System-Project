package mongoDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import com.mongodb.MongoTimeoutException;

public class GUIclass extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private String loggedInUsername; // Variable to store the logged-in username
    private JLabel welcomeLabel; // Label to display welcome message
    private JTextField usernameField; // Username text field

    private JTextField productNameField;
    private JTextField priceField;
    private JTextField expirationDateField;

    private String uri = "mongodb://localhost:27017/?serverSelectionTimeoutMS=60000"; // MongoDB URI with increased timeout

    public GUIclass() {
        productNameField = new JTextField(20);
        priceField = new JTextField(20);
        expirationDateField = new JTextField(20);

        setTitle("Product Management System");
        setSize(600, 400); // Set initial frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create CardLayout to switch between login and main menu panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for precise component placement

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30)); // Reduced size for username field
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);

        final JTextField passwordField = new JPasswordField(20); // Use JPasswordField for password
        passwordField.setPreferredSize(new Dimension(200, 30)); // Reduced size for password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for login button
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(createAccountButton, gbc);
        

        // Create ActionListener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the username entered directly from the instance variable
                loggedInUsername = usernameField.getText();
               String loggedInpassword = passwordField.getText();
                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");

                    MongoCollection<Document> collection = database.getCollection("Users");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));

                    // Creating a query to search for the document to update
                    Document query1 = new Document("username", loggedInUsername);
                    Document query2 = new Document("password", loggedInpassword);

                    // Searching the document from the collection
                    Document product1 = collection.find(query1).first();
                    Document product2 = collection.find(query2).first();

                    // Checking if product exists
                    if (product1 != null && product2 != null) {

                        welcomeLabel.setText("Welcome to our store " + loggedInUsername);
                        // For simplicity, assume successful login and switch to main menu
                        cardLayout.show(cardPanel, "mainMenu");
                    } else {
                        JOptionPane.showMessageDialog(null, "Username or Password incorrect");
                    }
                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }

                // Update welcome label text
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the username entered directly from the instance variable
                cardLayout.show(cardPanel, "CreateAccountPagePanel");
            }
        });

     
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel CreateAccountPagePanel = new JPanel();
        CreateAccountPagePanel.setLayout(new GridBagLayout());


        
        
        
        
        
        
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
       final JTextField usernameFieldCA = new JTextField(20);
        usernameFieldCA.setPreferredSize(new Dimension(200, 30)); // Reduced size for username field
        gbc.gridx = 0;
        gbc.gridy = 0;
        CreateAccountPagePanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        CreateAccountPagePanel.add(usernameFieldCA, gbc);
       final JTextField passwordFieldCA = new JPasswordField(20); // Use JPasswordField for password
        passwordFieldCA.setPreferredSize(new Dimension(200, 30)); // Reduced size for password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        CreateAccountPagePanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        CreateAccountPagePanel.add(passwordFieldCA, gbc);


        JButton createButton = new JButton("Create Account");
        createButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for login button
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        CreateAccountPagePanel.add(createButton, gbc);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the username entered directly from the instance variable
            	String UserNameValue = usernameFieldCA.getText();
            	String PasswordValue = passwordFieldCA.getText();
            	
                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    System.out.println("Created Mongo Connection successfully");
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");
                    System.out.println("Get database is successful");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Users");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));
                    // Creating a document
                    Document doc = new Document("username", UserNameValue)
                            .append("password", PasswordValue);

                    // Inserting the document into the collection
                    collection.insertOne(doc);
                    JOptionPane.showMessageDialog(null, "account created Successful!");
                    cardLayout.show(cardPanel, "login");
                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error inserting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }
            }
        });
   
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Create main menu panel
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for precise component placement

        // Display welcome message with username
        welcomeLabel = new JLabel("Welcome to our store " + loggedInUsername);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainMenuPanel.add(welcomeLabel, gbc);

        JButton insertButton = new JButton("Insert Product");
        insertButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for insert button
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainMenuPanel.add(insertButton, gbc);

        JButton showButton = new JButton("Show Products");
        showButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for show button
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainMenuPanel.add(showButton, gbc);

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for delete button
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainMenuPanel.add(deleteButton, gbc);

        JButton updateButton = new JButton("Update Product"); // New button for update
        updateButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for update button
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainMenuPanel.add(updateButton, gbc);

        JButton addToCartButton = new JButton("Add to Cart"); // New button for add to cart
        addToCartButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for add to cart button
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainMenuPanel.add(addToCartButton, gbc);

        JButton CartButton = new JButton("Cart"); // New button for add to cart
        CartButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for add to cart button
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainMenuPanel.add(CartButton, gbc);

        JButton UpdateUserButton = new JButton("Update Users"); // New button for add to cart
        UpdateUserButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for add to cart button
        gbc.gridx = 4;
        gbc.gridy = 1;
        mainMenuPanel.add(UpdateUserButton, gbc);

        JButton DeleteUsersButton = new JButton("Delete Users"); // New button for add to cart
        DeleteUsersButton.setPreferredSize(new Dimension(200, 30)); // Reduced size for add to cart button
        gbc.gridx = 4;
        gbc.gridy = 2;
        mainMenuPanel.add(DeleteUsersButton, gbc);

        // Create insertion panel
        JPanel insertionPanel = new JPanel();
        insertionPanel.setLayout(new GridBagLayout());

        productNameField = new JTextField(20);
        productNameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        insertionPanel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        insertionPanel.add(productNameField, gbc);

        priceField = new JTextField(20);
        priceField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        insertionPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        insertionPanel.add(priceField, gbc);

        expirationDateField = new JTextField(20);
        expirationDateField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        insertionPanel.add(new JLabel("Expiration Date:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        insertionPanel.add(expirationDateField, gbc);

        // Create buttons for insertion panel
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        insertionPanel.add(backButton, gbc);

        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        insertionPanel.add(addButton, gbc);

        // Action listener for back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to main menu panel
                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        // Action listener for add button (no specific functionality provided, goes back to main menu)
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform add product logic here if needed
                // Capture values from text fields
                String productNameValue = productNameField.getText();
                String priceValue = priceField.getText();
                String expirationDateValue = expirationDateField.getText();

                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    System.out.println("Created Mongo Connection successfully");
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");
                    System.out.println("Get database is successful");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Products");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));
                    // Creating a document
                    Document doc = new Document("name", productNameValue)
                            .append("price", priceValue+"$")
                            .append("expirationDate", expirationDateValue);

                    // Inserting the document into the collection
                    collection.insertOne(doc);
                    JOptionPane.showMessageDialog(null, "Insertion Successful!");
                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error inserting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }

                // Go back to main menu panel
                cardLayout.show(cardPanel, "mainMenu");
            }
        });

        // Action listener for insert button to show insertion panel
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to insertion panel when insert button is clicked
                cardLayout.show(cardPanel, "insertion");
            }
        });

        // Action listener for delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user for the product name to delete
                String productNameToDelete = JOptionPane.showInputDialog("Enter product name to delete:");

                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Products");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));
                    // Creating a query to search for the document to delete
                    Document query = new Document("name", productNameToDelete);

                    // Deleting the document from the collection
                    Document product = collection.find(query).first();

                    // Checking if product exists
                    if (product != null) {
                        collection.deleteOne(query);
                        JOptionPane.showMessageDialog(null, "Deletion Successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Product not found!");
                    }
                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }
            }
        });
        DeleteUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user for the product name to delete
                String UserNameToDelete = JOptionPane.showInputDialog("Enter Username to delete:");

                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Users");
collection.createIndex(Indexes.ascending("username","password"));
                    // Creating a query to search for the document to delete
                    Document query = new Document("username", UserNameToDelete);

                    // Deleting the document from the collection
                    Document user = collection.find(query).first();

                    // Checking if product exists
                    if (user != null) {
                        collection.deleteOne(query);
                        JOptionPane.showMessageDialog(null, "Deletion Successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "username not found!");
                    }
                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error deleting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }
            }
        });

        // Action listener for show button
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Document> productList = new ArrayList<>();

                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Products");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));
                    // Retrieving all documents from the collection
                    try (MongoCursor<Document> cursor = collection.find().iterator()) {
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();
                            productList.add(doc);
                        }
                    }

                    // Displaying the products in a JOptionPane
                    StringBuilder sb = new StringBuilder();
                    for (Document doc : productList) {
                        sb.append("Product Name: ").append(doc.getString("name")).append("\n");
                        sb.append("Price: ").append(doc.getString("price")).append("\n");
                        sb.append("Expiration Date: ").append(doc.getString("expirationDate")).append("\n\n");
                    }

                    if (sb.length() > 0) {
                        JOptionPane.showMessageDialog(null, sb.toString(), "Products List", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No products found!", "Products List", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error retrieving products: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }
            }
        });

        // Action listener for update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user for the product name to update
                String productNameToUpdate = JOptionPane.showInputDialog("Enter product name to update:");

                // Check if the product exists before allowing update
                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Products");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));

                    // Creating a query to search for the document to update
                    Document query = new Document("name", productNameToUpdate);

                    // Searching the document from the collection
                    Document product = collection.find(query).first();

                    // Checking if product exists
                    if (product != null) {
                        // Proceed with update
                        String newName = JOptionPane.showInputDialog("Enter new name:");
                    	String newPrice = JOptionPane.showInputDialog("Enter new price:");
                        String newExpirationDate = JOptionPane.showInputDialog("Enter new expiration date:");

                        // Creating a new document with updated values
                        Document updatedDocument = new Document("$set", new Document("name", newName)
                                .append("price", newPrice+"$")
                                .append("expirationDate", newExpirationDate));

                        // Updating the document in the collection
                        collection.updateOne(product, updatedDocument);
                        JOptionPane.showMessageDialog(null, "Update Successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Product not found!");
                    }
                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }
            }
        });
        UpdateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user for the product name to update
                String UserNameToUpdate = JOptionPane.showInputDialog("Enter username to update:");
                String PassWordToUpdate = JOptionPane.showInputDialog("Enter password:");
                // Check if the product exists before allowing update
                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Users");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));

                    // Creating a query to search for the document to update
                    Document query = new Document("username", UserNameToUpdate);

                    // Searching the document from the collection
                    Document product = collection.find(query).first();

                    // Checking if product exists
                    if (product != null) {
                        // Proceed with update
                        String newusername = JOptionPane.showInputDialog("Enter new username:");
                        String newpassword = JOptionPane.showInputDialog("Enter new password:");

                        // Creating a new document with updated values
                        Document updatedDocument = new Document("$set", new Document("username", newusername).append("password", newpassword));

                        // Updating the document in the collection
                        collection.updateOne(product, updatedDocument);
                        JOptionPane.showMessageDialog(null, "Update Successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "user not found!");
                    }
                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error updating product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }
            }
        });

        // Action listener for add to cart button
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame cartFrame = new JFrame("Shopping Cart");
                cartFrame.setSize(400, 300);
                cartFrame.setLocationRelativeTo(null);

                JPanel cartPanel = new JPanel(new GridLayout(0, 1));
                JScrollPane scrollPane = new JScrollPane(cartPanel);
                cartFrame.add(scrollPane);

                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Products");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));
                    // Retrieving all documents from the collection
                    MongoCursor<Document> cursor = collection.find().iterator();

                    // Iterate over the documents
                 // Iterate over the documents
                    while (cursor.hasNext()) {
                        Document doc = cursor.next();

                        JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                       final JLabel nameLabel = new JLabel("Name: " + doc.getString("name"));
                       final JLabel priceLabel = new JLabel("Price: " + doc.getString("price"));
                       final JLabel expirationLabel = new JLabel("Expiration Date: " + doc.getString("expirationDate"));

                        JButton addButton = new JButton("Add to Cart");

                        // ActionListener for "Add to Cart" button
                        addButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Access the labels' text when the button is clicked
                                String name = nameLabel.getText().substring(6); // Remove "Name: "
                                String price = priceLabel.getText().substring(7); // Remove "Price: "
                                String expiration = expirationLabel.getText().substring(18); // Remove "Expiration Date: "

                                // Example action: Show a message with the product details
                                /*JOptionPane.showMessageDialog(null, 
                                    "Product added to cart:\n" +
                                    "Name: " + name + "\n" +
                                    "Price: " + price + "\n" +
                                    "Expiration Date: " + expiration
                                );*/
                                try (MongoClient mongoClient = MongoClients.create(uri)) {
                                    System.out.println("Created Mongo Connection successfully");
                                    // Accessing the database
                                    MongoDatabase database = mongoClient.getDatabase("Store");
                                    System.out.println("Get database is successful");

                                    // Accessing the collection
                                    MongoCollection<Document> collection = database.getCollection("Cart");
                collection.createIndex(Indexes.ascending("name","price","expirationDate"));
                                    // Creating a document
                                    Document doc = new Document("name", name)
                                            .append("price", price+"$")
                                            .append("expirationDate", expiration);

                                    // Inserting the document into the collection
                                    collection.insertOne(doc);
                                    JOptionPane.showMessageDialog(null, "Added to cart Successfully!");
                                } catch (MongoTimeoutException ex) {
                                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    ex.printStackTrace(); // Print stack trace for detailed error information
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, "Error inserting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    ex.printStackTrace(); // Print stack trace for detailed error information
                                }


                                // Example action: Perform additional logic with the product details
                                // For instance, you could add these details to a shopping cart object or perform other operations.
                            }
                        });

                        productPanel.add(nameLabel);
                        productPanel.add(priceLabel);
                        productPanel.add(expirationLabel);
                        productPanel.add(addButton);

                        cartPanel.add(productPanel);
                    }

                    cursor.close(); // Close the cursor to release resources

                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error retrieving products: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }

                cartFrame.setVisible(true);
            }
        });
        
        CartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame cartFrame = new JFrame("Your Cart");
                cartFrame.setSize(400, 300);
                cartFrame.setLocationRelativeTo(null);

                JPanel cartPanel = new JPanel(new GridLayout(0, 1));
                JScrollPane scrollPane = new JScrollPane(cartPanel);
                cartFrame.add(scrollPane);

                try (MongoClient mongoClient = MongoClients.create(uri)) {
                    // Accessing the database
                    MongoDatabase database = mongoClient.getDatabase("Store");

                    // Accessing the collection
                    MongoCollection<Document> collection = database.getCollection("Cart");
collection.createIndex(Indexes.ascending("name","price","expirationDate"));
                    // Retrieving all documents from the collection
                    MongoCursor<Document> cursor = collection.find().iterator();

                    // Iterate over the documents
                 // Iterate over the documents
                    JButton OrderButton = new JButton("Purchase");
                    while (cursor.hasNext()) {
                        Document doc = cursor.next();

                        JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));


                       final JLabel nameLabel = new JLabel("Name: " + doc.getString("name"));
                       final JLabel priceLabel = new JLabel("Price: " + doc.getString("price"));
                       final JLabel expirationLabel = new JLabel("Expiration Date: " + doc.getString("expirationDate"));

                        // ActionListener for "Add to Cart" button
                        OrderButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                // Example action: Show a message with the product details
                                JOptionPane.showMessageDialog(null,"Items Purchased Successfully!");
                                
                                try (MongoClient mongoClient = MongoClients.create(uri)) {
                                    System.out.println("Created Mongo Connection successfully");
                                    // Accessing the database
                                    MongoDatabase database = mongoClient.getDatabase("Store");
                                    System.out.println("Get database is successful");

                                    // Accessing the collection
                                    MongoCollection<Document> collection = database.getCollection("Cart");
                collection.createIndex(Indexes.ascending("name","price","expirationDate"));
                                    // Deleting All Purchased Documents
      
                collection.deleteMany(new Document());
                                    JOptionPane.showMessageDialog(null, "Items Purchased Successfully!");
                                } catch (MongoTimeoutException ex) {
                                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    ex.printStackTrace(); // Print stack trace for detailed error information
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, "Error inserting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    ex.printStackTrace(); // Print stack trace for detailed error information
                                }


                                // Example action: Perform additional logic with the product details
                                // For instance, you could add these details to a shopping cart object or perform other operations.
                            }
                        });

                        productPanel.add(nameLabel);
                        productPanel.add(priceLabel);
                        productPanel.add(expirationLabel);
                        productPanel.add(OrderButton);

                        cartPanel.add(productPanel);
                    }

                    cursor.close(); // Close the cursor to release resources

                } catch (MongoTimeoutException ex) {
                    JOptionPane.showMessageDialog(null, "Timeout while connecting to MongoDB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error retrieving products: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Print stack trace for detailed error information
                }

                cartFrame.setVisible(true);
            }
        });
        
        // Add panels to cardPanel with respective names
        cardPanel.add(loginPanel, "login");
        cardPanel.add(CreateAccountPagePanel, "CreateAccountPagePanel");
        cardPanel.add(mainMenuPanel, "mainMenu");
        cardPanel.add(insertionPanel, "insertion");

        // Add cardPanel to the frame
        add(cardPanel);

        // Show login panel by default
        cardLayout.show(cardPanel, "login");

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Set Nimbus look and feel for a nicer GUI experience
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        // Create an instance of GUIclass
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIclass();
            }
        });
    }
}
