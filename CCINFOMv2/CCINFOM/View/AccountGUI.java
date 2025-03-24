package View;

import Model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountGUI extends JFrame implements ActionListener {

    private JTextField accountIdField, depositAmountField, withdrawAmountField, transferAmountField, transferToField;
    private JButton viewButton, depositButton, withdrawButton, transferButton, closeButton;

    public AccountGUI() {
        setTitle("Account Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        panel.add(new JLabel("Account ID:"));
        accountIdField = new JTextField();
        panel.add(accountIdField);

        viewButton = new JButton("View Account");
        viewButton.addActionListener(this);
        panel.add(viewButton);

        panel.add(new JLabel("Deposit Amount:"));
        depositAmountField = new JTextField();
        panel.add(depositAmountField);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        panel.add(depositButton);

        panel.add(new JLabel("Withdraw Amount:"));
        withdrawAmountField = new JTextField();
        panel.add(withdrawAmountField);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        panel.add(withdrawButton);

        panel.add(new JLabel("Transfer Amount:"));
        transferAmountField = new JTextField();
        panel.add(transferAmountField);

        panel.add(new JLabel("Transfer To (Account ID):"));
        transferToField = new JTextField();
        panel.add(transferToField);

        transferButton = new JButton("Transfer");
        transferButton.addActionListener(this);
        panel.add(transferButton);

        closeButton = new JButton("Close Account");
        closeButton.addActionListener(this);
        panel.add(closeButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int accountId = Integer.parseInt(accountIdField.getText());

            if (e.getSource() == viewButton) {
                Account.viewAccountInfo(accountId, 1);  // Assuming customer_id is 1 for testing
            }
            if (e.getSource() == depositButton) {
                Account.depositToAccount(accountId);
            }
            if (e.getSource() == withdrawButton) {
                Account.withdrawFromAccount(accountId);
            }
            if (e.getSource() == transferButton) {
                Account.transferToAnother(accountId);
            }
            if (e.getSource() == closeButton) {
                Account.closeAccount(accountId);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Account ID or Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AccountGUI();
    }
}

