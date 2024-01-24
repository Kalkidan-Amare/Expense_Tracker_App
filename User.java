import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class User implements ExpenseCalculator{
    private int userId;
    private String username;
    private String password;
    private List<Expense> currentExpenses;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.currentExpenses = new ArrayList<>();
    }

    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Expense> getCurrentExpenses() {
        return currentExpenses;
    }

    public void addExpense(Expense expense) {
        currentExpenses.add(expense);
    }

    public void removeExpense(Expense expense) {
        currentExpenses.remove(expense);
    }

    @Override
    public double calculateTotalExpenses(List<Expense> expenses) {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    @Override
    public double calculateAverageDailySpending(List<Expense> expenses, Date startDate, Date endDate) {
        long days = ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant()) + 1;
        return calculateTotalExpenses(expenses) / days;
    }

    @Override
    public Map<Category, Double> calculateCategoryBreakdown(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));
    }

    public Report generateExpenseReport(Date startDate, Date endDate) {
        List<Expense> expensesInDateRange = getExpensesInDateRange(startDate, endDate);

        double totalExpenses = calculateTotalExpenses(expensesInDateRange);
        double averageDailySpending = calculateAverageDailySpending(expensesInDateRange, startDate, endDate);
        Map<Category, Double> categoryBreakdown = calculateCategoryBreakdown(expensesInDateRange);

        return new Report(this, startDate, endDate, totalExpenses, averageDailySpending, categoryBreakdown);
    }

    List<Expense> getExpensesInDateRange(Date startDate, Date endDate) {
        return currentExpenses.stream()
                .filter(expense -> expense.getDate().after(startDate) && expense.getDate().before(endDate))
                .collect(Collectors.toList());
    }

}
