import java.util.Date;

public class Expense extends Budget {
    private String description;
    private Date date;

    public Expense(double amount, String description, Date date, Category category) {
        super(category, amount);
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
