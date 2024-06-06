package com.example.iss;

import java.io.File;
import java.util.*;

import domain.Exemplar;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;

import domain.User;
import domain.Carte;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import repo.CarteDBRepository;
import repo.RepositoryException;
import repo.UserDBRepository;

import java.util.concurrent.atomic.AtomicInteger;

public class LibraryApp extends Application {

    private final UserDBRepository userDBRepository = new UserDBRepository();
    private final CarteDBRepository carteDBRepository = new CarteDBRepository();

    private final AtomicInteger userIdCounter = new AtomicInteger(1);

    private Stage primaryStage;
    private FlowPane bookDisplay;
    private final Map<String, User> connectedUsers = new HashMap<>();

    public LibraryApp() throws RepositoryException {
    }


    @Override
    public void start(Stage primaryStage) {

        showLoginPage();

        StackPane loginLayout = new StackPane();
        Scene loginScene = new Scene(loginLayout, 300, 250);
        primaryStage.setScene(loginScene);
        //primaryStage.show();

        Stage libraryStage = new Stage();
        //showLibraryPage();

        StackPane libraryLayout = new StackPane();
        Scene libraryScene = new Scene(libraryLayout, 300, 250);
        libraryStage.setScene(libraryScene);
    }

    public void showLoginPage() {

        Stage loginStage = new Stage();
        loginStage.setTitle("Login Page");

        BorderPane loginRoot = new BorderPane();
        VBox loginPage = new VBox();
        loginPage.getStyleClass().add("login-page");

        VBox container = new VBox();
        container.getStyleClass().add("container");
        container.setAlignment(Pos.TOP_CENTER);

        HBox welcomeContainer = new HBox();
        welcomeContainer.getStyleClass().add("welcome-container");

        Label welcomeLabel = new Label("Welcome back!");
        welcomeLabel.getStyleClass().add("welcome-back");

        welcomeContainer.getChildren().add(welcomeLabel);

        VBox usernameRow = new VBox();
        VBox passwordRow = new VBox();

        Label usernameLabel = new Label("Username:  ");
        usernameLabel.getStyleClass().add("username");
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("password-container");
        usernameRow.getChildren().addAll(usernameLabel, usernameField);

        Label passwordLabel = new Label("Password:  ");
        passwordLabel.getStyleClass().add("password");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("password-container");
        passwordRow.getChildren().addAll(passwordLabel, passwordField);


        HBox buttonContainer = new HBox();
        buttonContainer.getStyleClass().add("button-container");

        Button loginButton = new Button("login");
        loginButton.getStyleClass().add("login-button");
        buttonContainer.getChildren().add(loginButton);

        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });


        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            authenticateUser(username, password);
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                authenticateUser(username, password);
            }
        });

        VBox dontHaveAccountContainer = new VBox();
        dontHaveAccountContainer.getStyleClass().add("dont-have-account-container");

        Hyperlink registerLink = new Hyperlink("Don't have an account? Register ;)");
        registerLink.getStyleClass().add("dont-have-account-container");
        registerLink.setOnAction(e -> showRegisterPage());

        dontHaveAccountContainer.getChildren().add(registerLink);
        container.getChildren().addAll(welcomeContainer, usernameRow, passwordRow, buttonContainer, dontHaveAccountContainer);

        welcomeContainer.setAlignment(Pos.TOP_CENTER);
        buttonContainer.setAlignment(Pos.TOP_CENTER);
        dontHaveAccountContainer.setAlignment(Pos.TOP_CENTER);
        usernameRow.setAlignment(Pos.TOP_CENTER);
        passwordRow.setAlignment(Pos.TOP_CENTER);

        VBox blueContainer = new VBox();
        blueContainer.getStyleClass().add("blue-container");

        HBox rectangleContainer = new HBox();
        rectangleContainer.setAlignment(Pos.CENTER);

        Region rectangle1 = new Region();
        rectangle1.getStyleClass().add("rectangle-1");

        Region rectangle2 = new Region();
        rectangle2.getStyleClass().add("rectangle-2");

        rectangleContainer.getChildren().addAll(rectangle1, rectangle2);
        blueContainer.getChildren().add(rectangleContainer);

        loginPage.getChildren().add(container);
        loginRoot.setCenter(loginPage);
        loginRoot.setRight(blueContainer);

        Scene scene = new Scene(loginRoot, 900, 700);

        String cssPath = "C:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\style.css";
        scene.getStylesheets().add(new File(cssPath).toURI().toString());

        loginStage.setScene(scene);
        loginStage.show();
    }


    public void showRegisterPage() {
        BorderPane root = new BorderPane();
        VBox registerPage = new VBox();
        registerPage.getStyleClass().add("register-page");

        VBox container = new VBox();
        container.getStyleClass().add("container");
        container.setAlignment(Pos.TOP_CENTER);


        HBox registerContainer = new HBox();
        registerContainer.getStyleClass().add("welcome-container");
        Label registerLabel = new Label("register!");
        registerLabel.getStyleClass().add("welcome-back");
        registerContainer.getChildren().add(registerLabel);


        VBox usernameRow = new VBox();
        VBox passwordRow = new VBox();
        VBox confirmPasswordRow = new VBox();
        VBox addressRow = new VBox();
        VBox phoneRow = new VBox();
        VBox roleRow = new VBox();

        Label usernameLabel = new Label("Username:  ");
        usernameLabel.getStyleClass().add("username");
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("password-container");
        usernameRow.getChildren().addAll(usernameLabel, usernameField);

        Label passwordLabel = new Label("Password:  ");
        passwordLabel.getStyleClass().add("password");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("password-container");
        passwordRow.getChildren().addAll(passwordLabel, passwordField);

        Label confirmPasswordLabel = new Label("Confirm Password:  ");
        confirmPasswordLabel.getStyleClass().add("password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("password-container");
        confirmPasswordRow.getChildren().addAll(confirmPasswordLabel, confirmPasswordField);

        Label addressLabel = new Label("Address: ");
        addressLabel.getStyleClass().add("username");
        TextField addressField = new TextField();
        addressField.getStyleClass().add("password-container");
        addressRow.getChildren().addAll(addressLabel, addressField);

        Label phoneLabel = new Label("Phone: ");
        phoneLabel.getStyleClass().add("username");
        TextField phoneField = new TextField();
        phoneField.getStyleClass().add("password-container");
        phoneRow.getChildren().addAll(phoneLabel, phoneField);

        Label roleLabel = new Label("Role: ");
        roleLabel.getStyleClass().add("username");
        TextField roleField = new TextField();
        roleField.getStyleClass().add("password-container");
        roleRow.getChildren().addAll(roleLabel, roleField);

        HBox buttonContainer = new HBox();
        buttonContainer.getStyleClass().add("button-container");

        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("login-button");
        buttonContainer.getChildren().add(registerButton);

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String role = roleField.getText();

            if (password.equals(confirmPassword)) {
                int userId = userIdCounter.incrementAndGet();
                User newUser = new User(userId, username, address, phone, role, password);
                try {
                    userDBRepository.add(newUser);
                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully!");
                    showLoginPage();
                } catch (RepositoryException ex) {
                    showAlert(Alert.AlertType.ERROR, "Registration Error", ex.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "Passwords do not match.");
            }
        });

        VBox login = new VBox();
        login.getStyleClass().add("dont-have-account-container");

        Hyperlink loginLink = new Hyperlink("Already have an account? Login ;)");
        loginLink.getStyleClass().add("dont-have-account-container");
        loginLink.setOnAction(e -> showLoginPage());

        login.getChildren().add(loginLink);

        container.getChildren().addAll(registerLabel, usernameRow, passwordRow, confirmPasswordRow, addressRow, phoneRow, roleRow, buttonContainer, login);

        buttonContainer.setAlignment(Pos.TOP_CENTER);
        usernameRow.setAlignment(Pos.TOP_CENTER);
        passwordRow.setAlignment(Pos.TOP_CENTER);
        confirmPasswordRow.setAlignment(Pos.TOP_CENTER);
        addressRow.setAlignment(Pos.TOP_CENTER);
        phoneRow.setAlignment(Pos.TOP_CENTER);
        roleRow.setAlignment(Pos.TOP_CENTER);
        login.setAlignment(Pos.TOP_CENTER);


        registerPage.getChildren().add(container);
        root.setCenter(registerPage);

        StackPane blueShapesContainer = new StackPane();
        blueShapesContainer.getStyleClass().add("blue-shapes-container");

        Region blueShape1 = new Region();
        blueShape1.getStyleClass().add("blue-shape-1");
        Region blueShape2 = new Region();
        blueShape2.getStyleClass().add("blue-shape-2");
        blueShapesContainer.getChildren().addAll(blueShape1, blueShape2);

        HBox mainContainer = new HBox(blueShapesContainer, container);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setSpacing(150);

        registerPage.getChildren().add(mainContainer);

        Scene scene = new Scene(root, 1000, 740);

        String cssPath = "C:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\style.css";
        scene.getStylesheets().add(new File(cssPath).toURI().toString());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void showLibraryPageUser() {

        Stage libraryStageUser = new Stage();
        libraryStageUser.setTitle("Library Page");

        BorderPane libraryRoot = new BorderPane();

        libraryRoot.getStyleClass().add("root");

        Pane backgroundOverlay = new Pane();
        backgroundOverlay.getStyleClass().add("background-shadow");
        backgroundOverlay.setVisible(false);

        VBox bookDetailsContainer = new VBox();
        bookDetailsContainer.getStyleClass().add("book-details-container");
        bookDetailsContainer.setVisible(false);

        //search bar
        VBox topContainer = new VBox();
        topContainer.getStyleClass().add("top-container");

        Image logoImage = new Image("file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\images\\logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.getStyleClass().add("logo-image");
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);

        logoImageView.setOnMouseClicked(event -> showLibraryPageAdmin());

        HBox img = new HBox(logoImageView);
        img.setAlignment(Pos.TOP_LEFT);
        img.setPadding(new Insets(10, 0, 10, 10));

        TextField searchBar = new TextField();
        searchBar.setPromptText("search");
        searchBar.getStyleClass().add("search-bar");

        HBox searchBox = new HBox(searchBar);
        searchBox.setAlignment(Pos.TOP_RIGHT);
        searchBox.setPadding(new Insets(10, 10, 10, 0));
        searchBox.getStyleClass().add("search");

        searchBar.setOnAction(event -> {
            String searchText = searchBar.getText().trim();
            updateBookDisplayBySearch(searchText);
        });

/*
        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("logout");
        logoutButton.setOnAction(event -> disconnectUser());*/

        HBox header = new HBox();
        header.setSpacing(20);
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(img, searchBox);
        HBox.setHgrow(searchBox, Priority.ALWAYS);

        topContainer.getChildren().add(header);

        Label textLabel = new Label("A curated list of every book ever written");
        textLabel.getStyleClass().add("blue-line-text");
        VBox blueLine = new VBox();
        blueLine.getChildren().add(textLabel);
        blueLine.getStyleClass().add("blue-line");

        topContainer.getChildren().add(blueLine);

        libraryRoot.setTop(topContainer);

        // navbar
        ListView<String> categoryList = new ListView<>();
        ObservableList<String> categories = FXCollections.observableArrayList(
                "Adventure", "Romance", "Fantasy", "Thriller", "Conteporary", "Dystopian", "Historical");
        categoryList.setItems(categories);

        categoryList.getStyleClass().add("container-category");


        categoryList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle(null);
                } else {
                    setText(item);
                    getStyleClass().add("category");

                    Circle circle = new Circle(15);
                    circle.setFill(Color.SALMON);

                    Text text = new Text(item.substring(0, Math.min(item.length(), 2)));
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    text.setFill(Color.WHITE);

                    StackPane stackPane = new StackPane(circle, text);

                    setGraphic(stackPane);
                }
            }
        });

        VBox navBar = new VBox(categoryList);
        navBar.setPadding(new Insets(0));
        libraryRoot.setLeft(navBar);

        categoryList.setOnMouseClicked(event -> {
            String selectedCategory = categoryList.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                updateBookDisplay(selectedCategory);
            }
        });

        // books display
        bookDisplay = new FlowPane();
        bookDisplay.setStyle("-fx-background-color: #c5d7f5;" +
                "-fx-border-color: transparent;");
        bookDisplay.setPadding(new Insets(10));
        bookDisplay.setHgap(10);
        bookDisplay.setVgap(10);

        updateBookDisplay("All");

        ScrollPane scrollPane = new ScrollPane(bookDisplay);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #c5d7f5; -fx-border-color: transparent;");
        libraryRoot.setCenter(scrollPane);

        Scene libraryScene = new Scene(libraryRoot, 900, 700);

        String cssPath = "C:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\style.css";
        libraryScene.getStylesheets().add(new File(cssPath).toURI().toString());

        libraryStageUser.setScene(libraryScene);
        libraryStageUser.show();

    }

    public void showLibraryPageAdmin() {

        Stage libraryStage = new Stage();
        libraryStage.setTitle("Library Page");

        BorderPane libraryRoot = new BorderPane();

        libraryRoot.getStyleClass().add("root");

        Pane backgroundOverlay = new Pane();
        backgroundOverlay.getStyleClass().add("background-shadow");
        backgroundOverlay.setVisible(false);

        VBox bookDetailsContainer = new VBox();
        bookDetailsContainer.getStyleClass().add("book-details-container");
        bookDetailsContainer.setVisible(false);

        //search bar
        VBox topContainer = new VBox();
        topContainer.getStyleClass().add("top-container");

        Image logoImage = new Image("file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\images\\logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.getStyleClass().add("logo-image");
        logoImageView.setFitWidth(50);
        logoImageView.setFitHeight(50);

        logoImageView.setOnMouseClicked(event -> showLibraryPageAdmin());

        HBox img = new HBox(logoImageView);
        img.setAlignment(Pos.TOP_LEFT);
        img.setPadding(new Insets(10, 0, 10, 10));

        TextField searchBar = new TextField();
        searchBar.setPromptText("search");
        searchBar.getStyleClass().add("search-bar");

        HBox searchBox = new HBox(searchBar);
        searchBox.setAlignment(Pos.TOP_RIGHT);
        searchBox.setPadding(new Insets(10, 10, 10, 0));
        searchBox.getStyleClass().add("search");

        searchBar.setOnAction(event -> {
            String searchText = searchBar.getText().trim();
            updateBookDisplayBySearch(searchText);
        });

        Button addButton = new Button("add book");
        addButton.setOnAction(event -> showAddBookPage());
        addButton.getStyleClass().add("logout");

        Button deleteButton = new Button("delete book");
        deleteButton.setOnAction(event -> showDeleteBookPage());
        deleteButton.getStyleClass().add("logout");

        Button updateButton = new Button("update book");
        updateButton.setOnAction(event -> showUpdateBookPage());
        updateButton.getStyleClass().add("logout");

        Button manageUsersButton = new Button("management");
        manageUsersButton.setOnAction(event -> showManageUsersPage());
        manageUsersButton.getStyleClass().add("logout");

        HBox buttonContainer = new HBox(addButton, deleteButton, updateButton, manageUsersButton);
        buttonContainer.setSpacing(10);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

/*
        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("logout");
        logoutButton.setOnAction(event -> disconnectUser());*/

        HBox header = new HBox();
        header.setSpacing(20);
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(img, searchBox, buttonContainer);
        HBox.setHgrow(searchBox, Priority.ALWAYS);

        topContainer.getChildren().add(header);

        Label textLabel = new Label("A curated list of every book ever written");
        textLabel.getStyleClass().add("blue-line-text");
        VBox blueLine = new VBox();
        blueLine.getChildren().add(textLabel);
        blueLine.getStyleClass().add("blue-line");

        topContainer.getChildren().add(blueLine);

        libraryRoot.setTop(topContainer);

        // navbar
        ListView<String> categoryList = new ListView<>();
        ObservableList<String> categories = FXCollections.observableArrayList(
                "Adventure", "Romance", "Fantasy", "Thriller", "Conteporary", "Dystopian", "Historical");
        categoryList.setItems(categories);

        categoryList.getStyleClass().add("container-category");


        categoryList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle(null);
                } else {
                    setText(item);
                    getStyleClass().add("category");

                    Circle circle = new Circle(15);
                    circle.setFill(Color.SALMON);

                    Text text = new Text(item.substring(0, Math.min(item.length(), 2)));
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    text.setFill(Color.WHITE);

                    StackPane stackPane = new StackPane(circle, text);

                    setGraphic(stackPane);
                }
            }
        });

        VBox navBar = new VBox(categoryList);
        navBar.setPadding(new Insets(0));
        libraryRoot.setLeft(navBar);

        categoryList.setOnMouseClicked(event -> {
            String selectedCategory = categoryList.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                updateBookDisplay(selectedCategory);
            }
        });

        // books display
        bookDisplay = new FlowPane();
        bookDisplay.setStyle("-fx-background-color: #c5d7f5;" +
                "-fx-border-color: transparent;");
        bookDisplay.setPadding(new Insets(10));
        bookDisplay.setHgap(10);
        bookDisplay.setVgap(10);

        updateBookDisplay("All");

        ScrollPane scrollPane = new ScrollPane(bookDisplay);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #c5d7f5; -fx-border-color: transparent;");
        libraryRoot.setCenter(scrollPane);

        Scene libraryScene = new Scene(libraryRoot, 1250, 700);

        String cssPath = "C:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\style.css";
        libraryScene.getStylesheets().add(new File(cssPath).toURI().toString());

        libraryStage.setScene(libraryScene);
        libraryStage.show();

    }

    public void showAddBookPage() {
        Stage addBookStage = new Stage();
        addBookStage.setTitle("Add Book Page");

        BorderPane addBookRoot = new BorderPane();
        addBookRoot.setPadding(new Insets(10));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();

        Label authorLabel = new Label("Author:");
        TextField authorField = new TextField();

        Label categoryLabel = new Label("Category:");
        TextField categoryField = new TextField();

        gridPane.addRow(0, titleLabel, titleField);
        gridPane.addRow(1, authorLabel, authorField);
        gridPane.addRow(2, categoryLabel, categoryField);

        VBox buttonContainer = new VBox();
        buttonContainer.setSpacing(10);
        buttonContainer.setPadding(new Insets(10));

        Button addButton = new Button("Add Book");
        addButton.setOnAction(event -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();

            Carte newBook = new Carte(20, title, author, category);
            try {
                carteDBRepository.add(newBook);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully.");
                addBookStage.close();
            } catch (RepositoryException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add book to the database.");
            }
        });

        buttonContainer.getChildren().add(addButton);

        addBookRoot.setCenter(gridPane);
        addBookRoot.setBottom(buttonContainer);

        Scene scene = new Scene(addBookRoot, 400, 200);
        addBookStage.setScene(scene);
        addBookStage.show();
    }

    public void showDeleteBookPage() {
        Stage deleteBookStage = new Stage();
        deleteBookStage.setTitle("Delete Book Page");

        BorderPane deleteBookRoot = new BorderPane();
        deleteBookRoot.setPadding(new Insets(10));

        ListView<String> bookListView = new ListView<>();
        ObservableList<String> bookTitles = FXCollections.observableArrayList();

        // Load books from the repository
        Iterable<Carte> carti = carteDBRepository.getAll();
        carti.forEach(carte -> bookTitles.add(carte.getTitlu()));

        bookListView.setItems(bookTitles);

        VBox buttonContainer = new VBox();
        buttonContainer.setSpacing(10);
        buttonContainer.setPadding(new Insets(10));

        Button deleteButton = new Button("Delete Book");
        deleteButton.setOnAction(event -> {
            String selectedTitle = bookListView.getSelectionModel().getSelectedItem();
            if (selectedTitle != null) {
                Carte selectedBook = null;
                for (Carte carte : carti) {
                    if (carte.getTitlu().equals(selectedTitle)) {
                        selectedBook = carte;
                        break;
                    }
                }
                if (selectedBook != null) {
                    int bookId = selectedBook.getId();
                    carteDBRepository.delete(bookId);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully.");
                    bookTitles.remove(selectedTitle);
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select a book to delete.");
            }
        });

        buttonContainer.getChildren().add(deleteButton);

        deleteBookRoot.setCenter(bookListView);
        deleteBookRoot.setBottom(buttonContainer);

        Scene scene = new Scene(deleteBookRoot, 400, 300);
        deleteBookStage.setScene(scene);
        deleteBookStage.show();
    }

    public void showUpdateBookPage() {
        Stage updateBookStage = new Stage();
        updateBookStage.setTitle("Update Book Page");

        BorderPane updateBookRoot = new BorderPane();
        updateBookRoot.setPadding(new Insets(10));
        Iterable<Carte> existingBooks = carteDBRepository.getAll();

        ListView<String> bookListView = new ListView<>();
        ObservableList<String> bookTitles = FXCollections.observableArrayList();

        existingBooks.forEach(carte -> bookTitles.add(carte.getTitlu()));
        bookListView.setItems(bookTitles);

        VBox buttonContainer = new VBox();
        buttonContainer.setSpacing(10);
        buttonContainer.setPadding(new Insets(10));

        Button selectButton = new Button("Select Book");
        selectButton.setOnAction(event -> {
            String selectedTitle = bookListView.getSelectionModel().getSelectedItem();
            if (selectedTitle != null) {
                Carte selectedBook = null;
                for (Carte carte : existingBooks) {
                    if (carte.getTitlu().equals(selectedTitle)) {
                        selectedBook = carte;
                        break;
                    }
                }
                if (selectedBook != null) {
                    TextField titleField = new TextField(selectedBook.getTitlu());
                    TextField authorField = new TextField(selectedBook.getAutor());
                    TextField categoryField = new TextField(selectedBook.getCategorie());

                    VBox inputContainer = new VBox();
                    inputContainer.setSpacing(10);
                    inputContainer.setPadding(new Insets(10));
                    inputContainer.getChildren().addAll(titleField, authorField, categoryField);

                    TextField exemplarCodeField = new TextField();
                    exemplarCodeField.setPromptText("Exemplar Code");

                    Button updateButton = new Button("Update Book");
                    Carte finalSelectedBook = selectedBook;
                    updateButton.setOnAction(updateEvent -> {
                        Carte updatedCarte = new Carte(finalSelectedBook.getId(), titleField.getText(), authorField.getText(), categoryField.getText());

                        carteDBRepository.update(finalSelectedBook.getId(), updatedCarte);

                        if (!exemplarCodeField.getText().isEmpty()) {
                            int exemplarCode = Integer.parseInt(exemplarCodeField.getText());
                            Exemplar newExemplar = new Exemplar(exemplarCode, 1, updatedCarte);
                            try {
                                carteDBRepository.addExemplar(updatedCarte, newExemplar);
                                showAlert(Alert.AlertType.INFORMATION, "Success", "Book updated successfully.");
                                updateBookStage.close();
                            } catch (RepositoryException e) {
                                e.printStackTrace();
                                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add exemplar to database.");
                            }
                        } else {
                            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in the exemplar code.");
                        }
                    });

                    VBox root = new VBox(inputContainer, exemplarCodeField, updateButton);
                    root.setSpacing(10);
                    root.setPadding(new Insets(10));

                    Scene scene = new Scene(root, 400, 250);
                    updateBookStage.setScene(scene);
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select a book to update.");
            }
        });

        buttonContainer.getChildren().add(selectButton);

        updateBookRoot.setCenter(bookListView);
        updateBookRoot.setBottom(buttonContainer);

        Scene scene = new Scene(updateBookRoot, 400, 300);
        updateBookStage.setScene(scene);
        updateBookStage.show();
    }

    public void showManageUsersPage() {
        Stage manageUsersStage = new Stage();
        manageUsersStage.setTitle("Manage Users");

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Set<String> connectedUserIds = connectedUsers.keySet();

        for (String userId : connectedUserIds) {
            User user = connectedUsers.get(userId);

            HBox userBox = new HBox();
            userBox.setAlignment(Pos.CENTER);
            userBox.setSpacing(10);

            Label usernameLabel = new Label(user.getNume());
            Button disconnectButton = new Button("Disconnect");

            disconnectButton.setOnAction(event -> {
                disconnectUser(userId);
                root.getChildren().remove(userBox);
            });

            userBox.getChildren().addAll(usernameLabel, disconnectButton);
            root.getChildren().add(userBox);
        }

        Scene scene = new Scene(root, 400, 300);
        manageUsersStage.setScene(scene);
        manageUsersStage.show();
    }


    private void updateBookDisplay(String selectedCategory) {
        bookDisplay.getChildren().clear();

        Iterable<Carte> carti = carteDBRepository.getAll();
        List<Carte> allBooks = new ArrayList<>();
        for (Carte carte : carti) {
            allBooks.add(carte);
        }

        List<Carte> booksToDisplay;
        if (selectedCategory.equals("All")) {
            booksToDisplay = allBooks;
        } else {
            booksToDisplay = new ArrayList<>();
            for (Carte book : allBooks) {
                if (book.getCategorie().equalsIgnoreCase(selectedCategory)) {
                    booksToDisplay.add(book);
                }
            }
        }

        for (Carte carte : booksToDisplay) {
            VBox bookBox = createBookBox(carte);
            bookBox.setOnMouseClicked(event -> showBookDetailsModal(carte));
            bookDisplay.getChildren().add(bookBox);
        }
    }

    private VBox createBookBox(Carte carte) {
        VBox bookBox = new VBox();
        bookBox.setSpacing(10);
        bookBox.getStyleClass().add("container-book");


        String imagePath = getBookImagePath(carte.getTitlu());
        if (imagePath != null) {
            Image coverImage = new Image(imagePath);
            ImageView coverImageView = new ImageView(coverImage);
            coverImageView.getStyleClass().add("cover");
            coverImageView.setFitWidth(150);
            coverImageView.setPreserveRatio(true);

            StackPane.setAlignment(coverImageView, Pos.CENTER);
            StackPane stackPane = new StackPane(coverImageView);
            bookBox.getChildren().add(stackPane);
        }

        Label titleLabel = new Label(carte.getTitlu());
        titleLabel.getStyleClass().add("title");
        Label authorLabel = new Label(carte.getAutor());
        authorLabel.getStyleClass().add("author");
        bookBox.getChildren().addAll(titleLabel, authorLabel);

        return bookBox;
    }


    private void updateBookDisplayBySearch(String searchText) {
        bookDisplay.getChildren().clear();

        Iterable<Carte> carti = carteDBRepository.getAll();
        List<Carte> allBooks = new ArrayList<>();
        for (Carte carte : carti) {
            allBooks.add(carte);
        }

        List<Carte> booksToDisplay = new ArrayList<>();
        for (Carte book : allBooks) {
            if (book.getTitlu().toLowerCase().contains(searchText.toLowerCase()) ||
                    book.getAutor().toLowerCase().contains(searchText.toLowerCase()) ||
                    book.getCategorie().toLowerCase().contains(searchText.toLowerCase())) {
                booksToDisplay.add(book);
            }
        }

        for (Carte carte : booksToDisplay) {
            VBox bookBox = createBookBox(carte);
            bookBox.setOnMouseClicked(event -> showBookDetailsModal(carte));
            bookDisplay.getChildren().add(bookBox);
        }
    }

    private void showBookDetailsModal(Carte carte) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Book Details");

        VBox modalContent = new VBox(10);

        String imagePath = getBookImagePath(carte.getTitlu());
        if (imagePath != null) {
            Image coverImage = new Image(imagePath);
            ImageView coverImageView = new ImageView(coverImage);
            coverImageView.getStyleClass().add("covers");
            coverImageView.setFitWidth(150);
            coverImageView.setPreserveRatio(true);

            StackPane.setAlignment(coverImageView, Pos.CENTER);
            StackPane stackPane = new StackPane(coverImageView);
            modalContent.getChildren().add(stackPane);
        }

        String randomDescription = generateRandomDescription();
        Label descriptionLabel = new Label(randomDescription);
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("description-area");

        Label titleLabel = new Label("Title: " + carte.getTitlu());
        titleLabel.getStyleClass().add("label");
        Label authorLabel = new Label("Author: " + carte.getAutor());
        authorLabel.getStyleClass().add("label");
        Label availabilityLabel = new Label("Availability: " + carte.getNumarExemplareDisponibile() + " copies");
        availabilityLabel.getStyleClass().add("label");

        Button rentButton = new Button("rent");
        rentButton.getStyleClass().add("buttons");
        Button returnButton = new Button("return");
        returnButton.getStyleClass().add("buttons");

        rentButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Borrow Book");
            alert.setHeaderText("Confirm Borrowing");
            alert.setContentText("Do you want to borrow this book?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // logica de imprumut
                    boolean success = borrowBook(carte);
                    if (success) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Borrow Book");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Book borrowed successfully!");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Borrow Book");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Failed to borrow book!");
                        errorAlert.showAndWait();
                    }
                }
            });
        });

        returnButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Return Book");
            alert.setHeaderText("Confirm Returning");
            alert.setContentText("Do you want to return this book?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // logica de returnare
                    boolean success = returnBook(carte);
                    if (success) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Return Book");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Book returned successfully!");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Return Book");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Failed to return book!");
                        errorAlert.showAndWait();
                    }
                }
            });
        });

        HBox buttonsBox = new HBox(10, rentButton, returnButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox blueButtons = new VBox();
        blueButtons.getChildren().add(buttonsBox);
        blueButtons.setAlignment(Pos.BOTTOM_CENTER);
        blueButtons.getStyleClass().add("button-container");

        modalContent.getStyleClass().add("modal-content");
        modalContent.getChildren().addAll(descriptionLabel, titleLabel, authorLabel, availabilityLabel, blueButtons);
        Scene modalScene = new Scene(modalContent);

        String cssPath = "C:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\modal.css";
        modalScene.getStylesheets().add(new File(cssPath).toURI().toString());


        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }

    private String generateRandomDescription() {
        List<String> descriptions = Arrays.asList(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vitae magna sed metus fringilla gravida. Quisque faucibus aliquam lacus, eget volutpat ipsum commodo vel. Integer ut ipsum nec felis gravida viverra at vel nisi.",
                "Suspendisse potenti. Duis pretium semper augue, vel ultricies dui venenatis eu. Vestibulum convallis quam nec lacus eleifend, nec condimentum tortor vehicula. Curabitur non diam ut libero convallis rhoncus. Nullam vehicula aliquet risus non fringilla.",
                "Donec consequat ligula ac mauris convallis, sed blandit mauris tempus. Vivamus pellentesque magna ut orci volutpat, a rhoncus urna laoreet. Integer eget nulla sit amet est varius hendrerit vel in ex. Quisque sodales metus eget orci dapibus, nec pharetra ante consequat.",
                "Phasellus varius dictum mi, nec fermentum mauris sagittis vel. Nulla facilisi. Integer volutpat ex sit amet dui consequat auctor. Proin auctor, turpis at commodo vestibulum, odio velit rhoncus odio, non pharetra libero mi sed sapien.",
                "Etiam hendrerit nisl eu massa aliquam, sed euismod urna convallis. Cras suscipit vitae enim nec dictum. Praesent eu fringilla arcu. Nam semper sagittis nisl, nec vestibulum risus pellentesque eget.",
                "Nunc suscipit, risus nec tincidunt fringilla, sapien ipsum aliquam tellus, eu commodo odio libero sit amet magna. Fusce a turpis ligula. Nulla facilisi. Phasellus a orci non magna auctor bibendum."
        );

        Random random = new Random();
        int index = random.nextInt(descriptions.size());
        return descriptions.get(index);
    }

    private String getBookImagePath(String bookTitle) {
        Map<String, String> bookCoverMap = new HashMap<>();
        bookCoverMap.put("Harry Potter și Piatra Filozofală", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title1.jpg");
        bookCoverMap.put("Domnișoara Peregrine", "file:C:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title2.jpg");
        bookCoverMap.put("Cronicile din Narnia", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title3.jpg");
        bookCoverMap.put("Game of Thrones", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title4.jpg");
        bookCoverMap.put("To Kill a Mockingbird", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title5.jpg");
        bookCoverMap.put("1984", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title6.png");
        bookCoverMap.put("Pride and Prejudice", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title7.jpg");
        bookCoverMap.put("The Catcher in the Rye", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title8.jpg");
        bookCoverMap.put("The Great Gatsby", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title9.jpg");
        bookCoverMap.put("The Hobbit", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title10.jpg");
        bookCoverMap.put("Moby Dick", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title11.jpg");
        bookCoverMap.put("Dracula", "file:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\src\\main\\resources\\covers\\title12.jpg");

        return bookCoverMap.get(bookTitle);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void authenticateUser(String username, String password) {
        User authenticatedUser = userDBRepository.authenticate(username, password);
        if (authenticatedUser != null) {
            String sessionId = UUID.randomUUID().toString();
            authenticatedUser.setSessionId(sessionId);
            connectedUsers.put(sessionId, authenticatedUser);
            if (authenticatedUser.getRol().equals("user")) {
                showLibraryPageUser();
            } else if (authenticatedUser.getRol().equals("administrator")) {
                showLibraryPageAdmin();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Invalid username or password. Please try again.");

        }

    }
    private void disconnectUser(String sessionId) {
        connectedUsers.remove(sessionId);
        showLoginPage();
    }

    private boolean borrowBook(Carte carte) {
        Set<Exemplar> exemplare = carte.getCoduriExemplare();

        for (Exemplar exemplar : exemplare) {
            if (exemplar.getDisponibil() > 0 && !carteDBRepository.verificaExemplarImprumutat(exemplar.getCodExemplar())) {
                exemplar.setDisponibil(exemplar.getDisponibil() - 1);
                carteDBRepository.imprumutaExemplar(exemplar.getCodExemplar());
                return true;
            }
        }
        return false;
    }

    private boolean returnBook(Carte carte) {
        Set<Exemplar> exemplare = carte.getCoduriExemplare();

        for (Exemplar exemplar : exemplare) {
            if (carteDBRepository.verificaExemplarImprumutat(exemplar.getCodExemplar())) {
                exemplar.setDisponibil(exemplar.getDisponibil() + 1);
                carteDBRepository.returneazaExemplar(exemplar.getCodExemplar());
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) throws RepositoryException {
        launch(args);
        HibernateUtil.shutdown();

    }
}
