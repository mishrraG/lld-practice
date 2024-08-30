package EmiCalc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Loan {
    private String loanId;
    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    private String adminUsername;
    private String customerUsername;
    private double principal;
    private double rate;
    private int tenure; // in years
    private double interest;
    private double totalAmount;
    private double emiAmount;
    private List<Double> emiPaid;
    private int emiRemaining;

    public Loan(String loanId, String adminUsername, String customerUsername, double principal, double rate, int tenure) {
        this.loanId = loanId;
        this.adminUsername = adminUsername;
        this.customerUsername = customerUsername;
        this.principal = principal;
        this.rate = rate;
        this.tenure = tenure;
        this.interest = calculateInterest();
        this.totalAmount = principal + interest;
        this.emiAmount = calculateEMI();
        this.emiPaid = new ArrayList<>();
        this.emiRemaining = tenure * 12; // Convert years to months
    }

    private double calculateInterest() {
        return (principal * tenure * rate) / 100;
    }

    public double calculateEMI() {
        return totalAmount / (tenure * 12);
    }

    public void makePayment(double amount) {
        if (emiRemaining <= 0) {
            throw new IllegalStateException("Loan already paid off");
        }

        emiPaid.add(amount);
        emiRemaining--;

        double totalPaid = emiPaid.stream().mapToDouble(Double::doubleValue).sum();
        if (totalPaid >= totalAmount) {
            emiRemaining = 0;
            System.out.println("Loan fully paid off!");
        } else {
            System.out.println("Payment successful! " + emiRemaining + " EMIs remaining.");
        }
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public static String generateUniqueLoanId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", adminUsername='" + adminUsername + '\'' +
                ", customerUsername='" + customerUsername + '\'' +
                ", principal=" + principal +
                ", rate=" + rate +
                ", tenure=" + tenure +
                ", interest=" + interest +
                ", totalAmount=" + totalAmount +
                ", emiAmount=" + emiAmount +
                ", emiPaid=" + emiPaid +
                ", emiRemaining=" + emiRemaining +
                '}';
    }
}
