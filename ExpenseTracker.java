import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExpenseTracker extends Application {

    private List<User> users = new ArrayList<>();
    private User currentUser;
    private List<Budget> budgets = new ArrayList<>();

    private TextField amountField;
    private TextField descriptionField;
    private DatePicker datePicker;
    private ComboBox<Category> categoryComboBox;

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Expense Tracker");

        // Default users
        users.add(new User(1, "Hermela Dereje", "1111"));
        users.add(new User(2, "Hermela Getachew", "2222"));
        users.add(new User(3, "Kaleab Mureja", "3333"));
        users.add(new User(4, "Kaleb Demisse", "4444"));
        users.add(new User(5, "KalKidan Amare", "5555"));
        users.add(new User(6, "Mesud Ahmed", "6666"));

        showLoginScene();

        primaryStage.show();
    }

    private void showLoginScene() {
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(100, 10, 10, 10));

        Label userLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("_Login");
        loginButton.setMnemonicParsing(true);
        loginButton.setOnAction(e -> {
            User user = getUserByUsername(usernameField.getText());
            if (user != null && user.getPassword().equals(passwordField.getText())) {
                currentUser = user;
                showMainInterface();
            } else {
                showAlert("Login Error", "Incorrect username or password. Please try again.");
            }
        });

        Button signupButton = new Button("_Sign Up");
        signupButton.setMnemonicParsing(true);
        signupButton.setOnAction(e -> showSignUpScene());

        loginGrid.add(userLabel, 0, 3);
        loginGrid.add(usernameField, 1, 3);
        loginGrid.add(passwordLabel, 0, 4);
        loginGrid.add(passwordField, 1, 4);
        loginGrid.add(loginButton, 0, 5);
        loginGrid.add(signupButton, 1, 5);

        setSceneRoot(loginGrid);
    }


    private void showSignUpScene() {
        GridPane signupGrid = new GridPane();
        signupGrid.setAlignment(Pos.CENTER);
        signupGrid.setHgap(10);
        signupGrid.setVgap(10);
        signupGrid.setPadding(new Insets(100, 10, 10, 10));

        Label userLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signUpButton = new Button("_Sign Up");
        signUpButton.setMnemonicParsing(true);
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                User newUser = new User(users.size() + 1, username, password);
                users.add(newUser);
                showAlert("Sign Up Successful", "User created successfully. Please log in.");
                showLoginScene();
            } else {
                showAlert("Sign Up Error", "Username and password cannot be empty.");
            }
        });

        Button backButton = new Button("_Back to Login");
        backButton.setMnemonicParsing(true);
        backButton.setOnAction(e -> showLoginScene());

        signupGrid.add(userLabel, 0, 3);
        signupGrid.add(usernameField, 1, 3);
        signupGrid.add(passwordLabel, 0, 4);
        signupGrid.add(passwordField, 1, 4);
        signupGrid.add(signUpButton, 0, 5);
        signupGrid.add(backButton, 1, 5);

        setSceneRoot(signupGrid);
    }

    private void showMainInterface()  {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        StackPane  stackPane1 = new StackPane();

        Button homeButton = createHomeButton();

        Label userLabel = new Label("USER:   " + currentUser.getUsername());
        userLabel.setAlignment(Pos.CENTER_RIGHT);
        Button logoutButton = new Button("_Logout");
        userLabel.setMnemonicParsing(true);
        logoutButton.setAlignment(Pos.CENTER_RIGHT);
        logoutButton.setOnAction(e -> showLoginScene());

        HBox hBox = new HBox(30);
        hBox.setPadding(new Insets(15, 15, 15, 15));

        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-background-color: #b0b0b0;");
        hBox.getChildren().addAll(homeButton,userLabel,logoutButton);


        Button displayButton = new Button("_DisplayExpenses");
        displayButton.setMnemonicParsing(true);
        displayButton.setOnAction(e -> displayExpenses(stackPane1));
        grid.add(displayButton, 1, 6);

        Button budgetButton = new Button("_BudgetManagement");
        budgetButton.setMnemonicParsing(true);
        budgetButton.setOnAction(e -> openBudgetManagement());
        grid.add(budgetButton, 0, 7);

        Button reportsButton = new Button("_GenerateReport");
        budgetButton.setMnemonicParsing(true);
        reportsButton.setOnAction(e -> generateReport());
        grid.add(reportsButton, 1, 7);


        HBox hBox2 = new HBox(15);
        hBox2.setPadding(new Insets(15, 15, 15, 15));
        hBox2.setStyle("-fx-background-color: #b0b0b0;");
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(displayButton,budgetButton,reportsButton);

        
        Label amountLabel = new Label("Amount:");
        Label descriptionLabel = new Label("Description:");
        Label dateLabel = new Label("Date:");
        Label categoryLabel = new Label("Category:");


        grid.add(categoryLabel, 0, 2);
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(Category.values());
        categoryComboBox.setValue(Category.FOOD);
        grid.add(categoryComboBox, 1, 2);

        grid.add(amountLabel, 0, 3);
        amountField = new TextField();
        grid.add(amountField, 1, 3);

        grid.add(descriptionLabel, 0, 4);
        descriptionField = new TextField();
        grid.add(descriptionField, 1, 4);

        grid.add(dateLabel, 0, 5);
        datePicker = new DatePicker();
        grid.add(datePicker, 1, 5);


        Button addButton = new Button("_Add Expense");
        addButton.setMnemonicParsing(true);
        addButton.setOnAction(e -> addExpense());
        HBox hBox3 = new HBox();
        hBox3.setPadding(new Insets(15, 15, 15, 15));
        hBox3.setAlignment(Pos.CENTER);
        hBox3.getChildren().add(addButton);

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(15, 5, 5, 5));
        
        vBox.getChildren().addAll(hBox,hBox2);
        
        VBox vBox2 = new VBox(15);
        vBox2.getChildren().addAll(grid,hBox3);
        vBox2.setStyle("-fx-background-color: #E5E5E5;");
        
        stackPane1.getChildren().add(vBox2);

        vBox.getChildren().add(stackPane1);


        setSceneRoot(vBox);
    }

    private void setSceneRoot(javafx.scene.Node root) {
        if (primaryStage.getScene() == null) {
            Scene scene = new Scene(new VBox(), 400, 500);
            primaryStage.setScene(scene);
        }
    
        VBox rootVBox = (VBox) primaryStage.getScene().getRoot();
        rootVBox.getChildren().clear();
        rootVBox.getChildren().add(root);
    }

    private void addExpense() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();
            Date date = java.sql.Date.valueOf(datePicker.getValue());
            Category category = categoryComboBox.getValue();

            Budget userBudget = getBudgetForCategory(category);
            if (userBudget != null && amount > userBudget.getAmount()) {
                showAlert("Budget Exceeded", "Expense amount exceeds the budget for category " + category);
                return;
            }

            Expense expense = new Expense(amount, description, date, category);
            currentUser.addExpense(expense);
            clearFields();
        } catch (NumberFormatException ex) {
            showAlert("Invalid Input", "Please enter a valid amount.");
        }
    }
    private Budget getBudgetForCategory(Category category) {
        for (Budget budget : budgets) {
            if (budget.getCategory() == category) {
                return budget;
            }
        }
        return null;
    }

    private Button createHomeButton() {
        Button homeButton = new Button("_Home");
        homeButton.setMnemonicParsing(true);
        homeButton.setAlignment(Pos.CENTER_LEFT);
        homeButton.setOnAction(e -> showMainInterface());
        return homeButton;
    }

    private void displayExpenses(StackPane stackPane) {
        List<Expense> userExpenses = currentUser.getCurrentExpenses();
        if (userExpenses.isEmpty()) {
            showAlert("No Expenses", "No expenses to display.");
        } else {
            TableView<Expense> expenseTable = createExpenseTable(userExpenses);

            stackPane.getChildren().clear();
            stackPane.getChildren().addAll(expenseTable);

        }
    }

   private TableView<Expense> createExpenseTable(List<Expense> expenses) {
        TableView<Expense> table = new TableView<>();

        TableColumn<Expense, Category> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Expense, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Expense, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(data -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = dateFormat.format(data.getValue().getDate());
            return new SimpleStringProperty(dateStr);
        });

        TableColumn<Expense, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Add columns to the table
        table.getColumns().addAll(categoryColumn, amountColumn, dateColumn, descriptionColumn);

        // Add table data
        table.getItems().addAll(expenses);

        return table;
    }


    private void openBudgetManagement() {
      
        Dialog<Budget> budgetDialog = new Dialog<>();
        budgetDialog.setTitle("Budget Management");
        budgetDialog.setHeaderText("Enter budget for a category");

    
        budgetDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label categoryLabel = new Label("Category:");
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(Category.values());
        categoryComboBox.setValue(Category.FOOD);
        grid.add(categoryLabel, 0, 0);
        grid.add(categoryComboBox, 1, 0);

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        grid.add(amountLabel, 0, 1);
        grid.add(amountField, 1, 1);

        budgetDialog.getDialogPane().setContent(grid);

        budgetDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    Category category = categoryComboBox.getValue();
                    double amount = Double.parseDouble(amountField.getText());
                    return new Budget(category, amount);
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid amount.");
                    return null;
                }
            }
            return null;
        });

        budgetDialog.showAndWait().ifPresent(budget -> {
            if (budget != null) {
                budgets.add(budget);
                showAlert("Budget Set", "Budget set successfully for " + budget.getCategory());
            }
        });
    }
    

    private void showReportWindow(Report report) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Expense Report");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Label reportLabel = new Label("Expense Report for " +
                report.getUser().getUsername() + " (" +
                dateFormatter.format(report.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) +
                " to " +
                dateFormatter.format(report.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) +
                ")\n" +
                "\tTotal Expenses:\t\t$" + report.getTotalExpenses() + "\n" +
                "\tAverage Daily Spending: $" + report.getAverageDailySpending() + "\n" +
                "\n\tCategory Breakdown:");

        for (Map.Entry<Category, Double> entry : report.getCategoryBreakdown().entrySet()) {
            reportLabel.setText(reportLabel.getText() +
                    "\n\t\t" + entry.getKey() + ":\t$" + entry.getValue());
        }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(reportLabel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);

        window.showAndWait();
    }


    private void generateReport() {
        LocalDate currentDate = LocalDate.now();

        LocalDate startDate = currentDate.minusDays(7);

        List<Expense> expensesInDateRange = currentUser.getExpensesInDateRange(
                Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        Report report = new Report(currentUser, Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), expensesInDateRange);

        showReportWindow(report);
    }


    private void clearFields() {
        amountField.clear();
        descriptionField.clear();
        datePicker.getEditor().clear();
    }

    private User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.setWidth(300);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}