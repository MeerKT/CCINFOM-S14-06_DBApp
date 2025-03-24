package Controller;

import Model.TransactionHistory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionHistoryGUI {
    private JFrame frame;
    private JPanel panel;
    private JButton accountTransactionButton, loanTransactionButton, annualTransactionButton,
                    annualLoanPaymentButton, monthlySavingsButton, viewTransactionHistoryButton,
                    viewLoanHistoryButton;

    public TransactionHistoryGUI() {
        frame = new JFrame("Transaction History Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        accountTransactionButton = new JButton("Record Account Transaction");
        loanTransactionButton = new JButton("Record Loan Transaction");
        annualTransactionButton = new JButton("Generate Annual Transaction Report");
        annualLoanPaymentButton = new JButton("Generate Annual Loan Payment Report");
        monthlySavingsButton = new JButton("Generate Monthly Savings Report");
        viewTransactionHistoryButton = new JButton("View Transaction History");
        viewLoanHistoryButton = new JButton("View Loan Payment History");

        panel.add(accountTransactionButton);
        panel.add(loanTransactionButton);
        panel.add(annualTransactionButton);
        panel.add(annualLoanPaymentButton);
        panel.add(monthlySavingsButton);
        panel.add(viewTransactionHistoryButton);
        panel.add(viewLoanHistoryButton);

        frame.add(panel);
        frame.setVisible(true);

        addActionListeners();
    }

    private void addActionListeners() {
        accountTransactionButton.addActionListener(e -> recordAccountTransaction());
        loanTransactionButton.addActionListener(e -> recordLoanTransaction());
        annualTransactionButton.addActionListener(e -> generateAnnualTransactionReport());
        annualLoanPaymentButton.addActionListener(e -> generateAnnualLoanPaymentReport());
        monthlySavingsButton.addActionListener(e -> generateMonthlySavings());
        viewTransactionHistoryButton.addActionListener(e -> viewTransactionHistory());
        viewLoanHistoryButton.addActionListener(e -> viewLoanHistory());
    }

    private void recordAccountTransaction() {
        String sender = JOptionPane.showInputDialog("Enter Sender ID (or leave blank for deposit):");
        String receiver = JOptionPane.showInputDialog("Enter Receiver ID (or leave blank for withdrawal):");
        String amountStr = JOptionPane.showInputDialog("Enter Amount:");

        try {
            Integer senderId = sender.isEmpty() ? null : Integer.parseInt(sender);
            Integer receiverId = receiver.isEmpty() ? null : Integer.parseInt(receiver);
            double amount = Double.parseDouble(amountStr);
            TransactionHistory.generateAccountTransactionRecord(senderId, receiverId, amount);
            JOptionPane.showMessageDialog(frame, "Transaction recorded successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void recordLoanTransaction() {
        String sender = JOptionPane.showInputDialog("Enter Sender ID:");
        String receiver = JOptionPane.showInputDialog("Enter Receiver Loan ID:");
        String amountStr = JOptionPane.showInputDialog("Enter Amount:");

        try {
            int senderId = Integer.parseInt(sender);
            int receiverId = Integer.parseInt(receiver);
            double amount = Double.parseDouble(amountStr);
            TransactionHistory.generateLoanTransactionRecord(senderId, receiverId, amount);
            JOptionPane.showMessageDialog(frame, "Loan transaction recorded successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void generateAnnualTransactionReport() {
        String year = JOptionPane.showInputDialog("Enter Year:");
        try {
            TransactionHistory.generateAnnualTransaction(year);
            JOptionPane.showMessageDialog(frame, "Annual transaction report generated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void generateAnnualLoanPaymentReport() {
        String year = JOptionPane.showInputDialog("Enter Year:");
        try {
            TransactionHistory.generateAnnualLoanPayment(year);
            JOptionPane.showMessageDialog(frame, "Annual loan payment report generated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void generateMonthlySavings() {
        String customerId = JOptionPane.showInputDialog("Enter Customer ID:");
        String year = JOptionPane.showInputDialog("Enter Year:");

        try {
            int id = Integer.parseInt(customerId);
            TransactionHistory history = new TransactionHistory();
            history.generateMonthlySavings(id, year);
            JOptionPane.showMessageDialog(frame, "Monthly savings report generated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void viewTransactionHistory() {
        String accountId = JOptionPane.showInputDialog("Enter Account ID:");
        try {
            TransactionHistory.viewTransactionHistoryOfAccount(accountId);
            JOptionPane.showMessageDialog(frame, "Transaction history displayed successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void viewLoanHistory() {
        String accountId = JOptionPane.showInputDialog("Enter Account ID:");
        try {
            TransactionHistory.viewLoanPaymentHistoryOfAccount(accountId);
            JOptionPane.showMessageDialog(frame, "Loan payment history displayed successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    public static void main(String[] args) {
        new TransactionHistoryGUI();
    }
}
