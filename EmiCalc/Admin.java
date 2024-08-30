package EmiCalc;

public class Admin extends User{
    public Admin(String username){
        super(username);
    }
    //Factory pattern
    public Loan createLoan(String customerUsername, double principal, double rate, int tenure) {
        String loanId = Loan.generateUniqueLoanId();
        Loan loan = new Loan(loanId, this.getUserName(), customerUsername, principal, rate, tenure);
        LoanDatabase.addLoan(loan);
        return loan;
    }

    public void fetchAllLoans() {
        LoanDatabase.getAllLoans().forEach(loan -> System.out.println(loan));
    }
}
