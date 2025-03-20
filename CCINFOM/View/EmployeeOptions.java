package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.AccountType;
import Model.LoanOptions;
import Model.TransactionHistory;

public class EmployeeOptions {
    public static void showOptions() {
        JFrame frame = new JFrame("Employee Options");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        JButton addLoanButton = new JButton("Add Loan Options");
        JButton viewAccountTypesButton = new JButton("View Account Types");
        JButton viewCustomersButton = new JButton("View Customers");
        JButton viewTransactionHistoryButton = new JButton("View Transaction History");
        JButton viewLoanHistoryButton = new JButton("View Loan Payment History");
        JButton viewAnnualTransactionButton = new JButton("View Annual Transaction Volume");
        JButton viewAnnualLoanButton = new JButton("View Annual Loan Payment Volume");
        
        addLoanButton.addActionListener(e -> addLoanOptions());
        viewAccountTypesButton.addActionListener(e -> AccountType.showAccountTypes());
        viewCustomersButton.addActionListener(e -> EmployeeOptions.showCustomersOfBank());
        viewTransactionHistoryButton.addActionListener(e -> TransactionHistory.viewTransactionHistoryOfAccount());
        viewLoanHistoryButton.addActionListener(e -> TransactionHistory.viewLoanPaymentHistoryOfAccount());
        viewAnnualTransactionButton.addActionListener(e -> TransactionHistory.generateAnnualTransaction());
        viewAnnualLoanButton.addActionListener(e -> TransactionHistory.generateAnnualLoanPayment());
        
        panel.add(addLoanButton);
        panel.add(viewAccountTypesButton);
        panel.add(viewCustomersButton);
        panel.add(viewTransactionHistoryButton);
        panel.add(viewLoanHistoryButton);
        panel.add(viewAnnualTransactionButton);
        panel.add(viewAnnualLoanButton);
        
        frame.add(panel);
        frame.setVisible(true);
    }
    
    private static void addLoanOptions() {
        String loanType = JOptionPane.showInputDialog("Enter Loan Type:");
        String interestRateStr = JOptionPane.showInputDialog("Enter Interest Rate (decimal):");
        String loanDurStr = JOptionPane.showInputDialog("Enter Loan Duration (months):");
        String maxLoanStr = JOptionPane.showInputDialog("Enter Max Loan Price:");
        String minLoanStr = JOptionPane.showInputDialog("Enter Minimum Loan Price:");
        
        if (loanType != null && interestRateStr != null && loanDurStr != null && maxLoanStr != null && minLoanStr != null) {
            try {
                double interestRate = Double.parseDouble(interestRateStr);
                int loanDur = Integer.parseInt(loanDurStr);
                double maxLoan = Double.parseDouble(maxLoanStr);
                double minLoan = Double.parseDouble(minLoanStr);
                LoanOptions.addLoanOption(loanType, interestRate, loanDur, maxLoan, minLoan);
                JOptionPane.showMessageDialog(null, "Loan Option Added Successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}