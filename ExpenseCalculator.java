import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ExpenseCalculator {
    double calculateTotalExpenses(List<Expense> expenses);

    double calculateAverageDailySpending(List<Expense> expenses, Date startDate, Date endDate);

    Map<Category, Double> calculateCategoryBreakdown(List<Expense> expenses);
}
