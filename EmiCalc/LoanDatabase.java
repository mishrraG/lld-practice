package EmiCalc;

import java.util.ArrayList;
import java.util.List;


public class LoanDatabase {
    private static LoanDatabase instance;
    private static List<Loan> loans = new ArrayList<>();


    private LoanDatabase() {
        loans = new ArrayList<>();
    }

    // Public method to provide access to the single instance
    public static LoanDatabase getInstance() {
        if (instance == null) {
            synchronized (LoanDatabase.class) {
                if (instance == null) {
                    instance = new LoanDatabase();
                }
            }
        }
        return instance;
    }

    public static void addLoan(Loan loan) {
        loans.add(loan);
    }

    public static Loan getLoanById(String loanId) {
        return loans.stream().filter(loan -> loanId.equals(loan.getLoanId())).findFirst().orElse(null);
    }

    public static List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }
}
