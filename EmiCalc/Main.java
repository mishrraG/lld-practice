package EmiCalc;

/**
 * main
 */
public class Main {
    public void main(String[] args) {
        Admin admin = new Admin("admin1");
        Customer customer = new Customer("cust1");

        // Admin creates a loan
        Loan loan = admin.createLoan("cust1", 10000, 5, 2);

        // Customer views loan details
        customer.viewLoanDetails(loan.getLoanId());

        // Customer makes an EMI payment
        customer.makePayment(loan.getLoanId(), loan.calculateEMI());

        // Customer views loan details again
        customer.viewLoanDetails(loan.getLoanId());

        // Admin fetches all loans
        admin.fetchAllLoans();
    }
}