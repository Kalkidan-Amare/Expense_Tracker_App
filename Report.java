import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Report implements ExpenseCalculator{
    private User user;
    private Date startDate;
    private Date endDate;
    private double totalExpenses;
    private double averageDailySpending;
    private Map<Category, Double> categoryBreakdown;

    public Report(User user, Date startDate, Date endDate, List<Expense> expenses) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalExpenses = calculateTotalExpenses(expenses);
        this.averageDailySpending = calculateAverageDailySpending(expenses, startDate, endDate);
        this.categoryBreakdown = calculateCategoryBreakdown(expenses);
    }

    public Report(User user, Date startDate, Date endDate, double totalExpenses, double averageDailySpending, Map<Category, Double> categoryBreakdown) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalExpenses = totalExpenses;
        this.averageDailySpending = averageDailySpending;
        this.categoryBreakdown = categoryBreakdown;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getAverageDailySpending() {
        return averageDailySpending;
    }

    public void setAverageDailySpending(double averageDailySpending) {
        this.averageDailySpending = averageDailySpending;
    }

    public Map<Category, Double> getCategoryBreakdown() {
        return categoryBreakdown;
    }

    public void setCategoryBreakdown(Map<Category, Double> categoryBreakdown) {
        this.categoryBreakdown = categoryBreakdown;
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
}
