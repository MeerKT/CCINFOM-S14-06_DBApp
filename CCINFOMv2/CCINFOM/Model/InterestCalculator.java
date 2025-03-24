package Model; 

import java.sql.*;
import java.text.DecimalFormat;

public class InterestCalculator {
    private String url = "jdbc:mysql://localhost:3306/bankdb";
    private String user = "root";
    private String password = "1234";

    public void calculateInterest(int accountID) {
        String query = "SELECT a.account_type, at.interest_rate, a.current_balance " +
                       "FROM Account a " +
                       "JOIN AccountType at ON a.account_type = at.account_name " +
                       "WHERE a.account_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String accountType = rs.getString("account_type");
                double interestRate = rs.getDouble("interest_rate");
                double currentBalance = rs.getDouble("current_balance");

                // Calculate interest
                double interest = currentBalance * interestRate;

                // Format monetary values for better readability
                DecimalFormat df = new DecimalFormat("#,###.00");

                System.out.println("Account Type: " + accountType);
                System.out.println("Interest Rate: " + df.format(interestRate * 100) + "%");
                System.out.println("Current Balance: $" + df.format(currentBalance));
                System.out.println("Calculated Interest: $" + df.format(interest));
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}