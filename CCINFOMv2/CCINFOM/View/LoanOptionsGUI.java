package View;

import Model.LoanOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanOptionsGUI extends JFrame implements ActionListener {

    private JButton viewOptionsButton, addOptionButton;

    public LoanOptionsGUI() {
        setTitle("Loan Options Management");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        viewOptionsButton = new JButton("View Loan Options");
        viewOptionsButton.addActionListener(this);
        panel.add(viewOptionsButton);

        addOptionButton = new JButton("Add New Loan Option");
        addOptionButton.addActionListener(this);
        panel.add(addOptionButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewOptionsButton) {
            LoanOptions.defaultLoan();  // Display existing loan options
        } else if (e.getSource() == addOptionButton) {
            String type = JOptionPane.showInputDialog("Enter Loan Type:");
            String interestRateStr = JOptionPane.showInputDialog("Enter Interest Rate (e.g., 0.05):");
            String durationStr = JOptionPane.showInputDialog("Enter Loan Duration (in months):");
            String maxAmountStr = JOptionPane.showInputDialog("Enter Maximum Loan Amount:");
            String minAmountStr = JOptionPane.showInputDialog("Enter Minimum Loan Amount:");

            try {
                double interestRate = Double.parseDouble(interestRateStr);
                int duration = Integer.parseInt(durationStr);
                double maxAmount = Double.parseDouble(maxAmountStr);
                double minAmount = Double.parseDouble(minAmountStr);

                LoanOptions.addLoanOption(type, interestRate, duration, maxAmount, minAmount);
                JOptionPane.showMessageDialog(this, "Loan Option Added Successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new LoanOptionsGUI();
    }
}

