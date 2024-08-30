package EmiCalc;

public class Customer extends User {
    public Customer(String username) {
        super(username);
    }

    public void makePayment(String loanId, double amount) {
        Loan loan = LoanDatabase.getLoanById(loanId);
        if (loan == null || !loan.getCustomerUsername().equals(this.getUserName())) {
            throw new SecurityException("Cannot make payment on another customer's loan.");
        }
        loan.makePayment(amount);
    }

    public void viewLoanDetails(String loanId) {
        Loan loan = LoanDatabase.getLoanById(loanId);
        if (loan == null || !loan.getCustomerUsername().equals(this.getUserName())) {
            throw new SecurityException("Cannot view details of another customer's loan.");
        }
        System.out.println(loan);
    }
}

