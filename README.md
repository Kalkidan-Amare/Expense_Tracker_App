# Expense Tracker App

Expense Tracker App is a JavaFX application designed to help you track your daily expenses, manage budget limits for specific categories, and provide insightful weekly reports. The app allows you to view your total expenses, average daily expenses, and expenses for different categories. Additionally, it sends reminders when you exceed budget limits for specific categories and displays all expenses in a tabulated form.

## Prerequisites

Before you begin, make sure you have the JavaFX SDK installed on your system. If you don't have it, you can download it from [https://openjfx.io/](https://openjfx.io/).

## Getting Started

1. Clone the Expense Tracker App repository to your local machine.

    ```bash
    git clone https://github.com/Kalkidan-Amare/Expense_Tracker_App
    ```

2. Open a terminal and navigate to the project directory.

    ```bash
    cd expense-tracker-app
    ```

3. Place the JavaFX SDK folder in the project directory.

4. Compile the ExpenseTracker.java file using the following command:

    ```bash
    javac --module-path "javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.fxml ExpenseTracker.java
    ```

5. Run the Expense Tracker App with the following command:

    ```bash
    java --module-path "javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.fxml ExpenseTracker
    ```

## Usage

Once the app is running, you can perform the following actions:

- **Track Daily Expenses:**
  - Enter your daily expenses and categorize them.
  
- **Set Budget Limits:**
  - Set budget limits for specific expense categories.

- **Receive Reminders:**
  - Receive reminders when you exceed the budget limits for specific categories.

- **View Weekly Reports:**
  - Access weekly reports that include total expenses, average daily expenses, and expenses for different categories.

- **Tabulated Expense View:**
  - View all expenses in a tabulated form.

## Contribution

If you'd like to contribute to the Expense Tracker App, feel free to submit pull requests or report issues on the GitHub repository.

## License

This project is licensed under the [MIT License](LICENSE).

