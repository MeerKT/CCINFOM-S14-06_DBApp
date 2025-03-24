package View;

import Model.AccountType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountTypeGUI extends JFrame implements ActionListener {

    private JButton viewTypesButton, modifyInterestButton, modifyMinBalanceButton;
    private JTextField typeField, interestField, minBalanceField;

    public AccountTypeGUI() {
        setTitle("Account Type Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        viewTypesButton = new JButton("View Account Types");
        viewTypesButton.addActionListener(this);
        
        modifyInterestButton = new JButton("Modify Interest Rate");
        modifyInterestButton.addActionListener(this);

        modifyMinBalanceButton = new JButton("Modify Minimum Balance");
        modifyMinBalanceButton.addActionListener(this);

        panel.add(viewTypesButton);
        panel.add(new JLabel());

        panel.add(new JLabel("Account Type:"));
        typeField = new JTextField();
        panel.add(typeField);

        panel.add(new JLabel("New Interest Rate:"));
        interestField = new JTextField();
        panel.add(interestField);

        panel.add(modifyInterestButton);
        
        panel.add(new JLabel("New Min Balance:"));
        minBalanceField = new JTextField();
        panel.add(minBalanceField);

        panel.add(modifyMinBalanceButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewTypesButton) {
            AccountType.showAccountTypes();
        } else if (e.getSource() == modifyInterestButton) {
            String accountType = typeField.getText();
            try {
                double interestRate = Double.parseDouble(interestField.getText());
                AccountType.changeInterestRate(accountType);
                JOptionPane.showMessageDialog(this, "Interest Rate Updated Successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid interest rate.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == modifyMinBalanceButton) {
            String accountType = typeField.getText();
            try {
                double minBalance = Double.parseDouble(minBalanceField.getText());
                AccountType.changeMinimumBalance(accountType);
                JOptionPane.showMessageDialog(this, "Minimum Balance Updated Successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid minimum balance.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new AccountTypeGUI();
    }
}

