package Model;

import HelperClass.UserInput;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;

public class TransactionHistory {
    private int transaction_id, sender_id, receiver_id;
    private double amount;
    private Date transaction_date;
    private String transaction_status;

    public static void generateAccountTransactionRecord(Integer sender_id, Integer receiver_id, double amount){
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bankdb",
                    "java",
                    "password");

            String insert = "INSERT INTO account_transaction_history (amount, " +
                            "transaction_date, transaction_status, sender_acc_ID, receiver_acc_ID)" +
                            "VALUES (?, NOW(), ?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(insert)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, "success");
                if(sender_id == null && receiver_id != null){
                    preparedStatement.setNull(3,Types.INTEGER);
                    preparedStatement.setInt(4, receiver_id);
                } else if (sender_id != null && receiver_id == null){
                    preparedStatement.setInt(3,sender_id);
                    preparedStatement.setNull(4, Types.INTEGER);
                } else {
                    preparedStatement.setInt(3,sender_id);
                    preparedStatement.setInt(4, receiver_id);
                }



                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Transaction recorded!");
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void generateLoanTransactionRecord(int sender_id, int receiver_id, double amount){
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bankdb",
                    "java",
                    "password");

            String insert = "INSERT INTO loan_transaction_history (amount, " +
                    "transaction_date, transaction_status, sender_acc_ID, receiver_loan_ID)" +
                    "VALUES (?, NOW(), ?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(insert)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, "success");
                preparedStatement.setInt(3,sender_id);
                preparedStatement.setInt(4, receiver_id);




                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Transaction recorded!");
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String generateAnnualTransaction(int year) {
        double totalOutgoing = 0;
        double totalIncoming = 0;
        StringBuilder report = new StringBuilder();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                return "Failed to connect to the database.";
            }

            Calendar calendar = GregorianCalendar.getInstance();
            calendar.set(Calendar.YEAR, year);

            String depositQuery = "SELECT SUM(amount) FROM account_transaction_history WHERE YEAR(transaction_date) = ? " +
                    "AND sender_acc_id IS NULL";
            PreparedStatement incomingStatement = connection.prepareStatement(depositQuery);
            incomingStatement.setInt(1, year);
            ResultSet depositResult = incomingStatement.executeQuery();

            String withdrawQuery = "SELECT SUM(amount) FROM account_transaction_history WHERE YEAR(transaction_date) = ? " +
                    "AND receiver_acc_id IS NULL";
            PreparedStatement outgoingStatement = connection.prepareStatement(withdrawQuery);
            outgoingStatement.setInt(1, year);
            ResultSet withdrawResult = outgoingStatement.executeQuery();

            if (withdrawResult.next()) {
                totalOutgoing = withdrawResult.getDouble(1);
            }

            if (depositResult.next()) {
                totalIncoming = depositResult.getDouble(1);
            }

            double netSavings = totalIncoming - totalOutgoing;

            report.append("Transaction Volume for the Year ").append(year).append("\n")
                    .append("Total Incoming: ₱").append(totalIncoming).append("\n")
                    .append("Total Outgoing: ₱").append(totalOutgoing).append("\n")
                    .append("Net Savings: ₱").append(netSavings).append("\n");

        } catch (SQLException e) {
            e.printStackTrace();
            report.append("An error occurred: ").append(e.getMessage());
        }
        
        return report.toString();
    }

    public static String generateAnnualLoanPayment(int year) {
        double totalLoanPayment = 0;
        int totalNumberOfLoanPayment = 0;
        StringBuilder report = new StringBuilder();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                return "Failed to connect to the database.";
            }

            String query = "SELECT * FROM loan_transaction_history WHERE YEAR(transaction_date) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, year);
            ResultSet reportResult = preparedStatement.executeQuery();

            while (reportResult.next()) {
                totalLoanPayment += reportResult.getDouble("amount");
                totalNumberOfLoanPayment++;
            }

            report.append("Annual Loan Payment Volume Report for ").append(year).append("\n")
                    .append("Total Loan Payment Made: ₱").append(Math.round(totalLoanPayment * 100.0) / 100.0).append("\n")
                    .append("Total Number of Loan Payments Made: ").append(totalNumberOfLoanPayment).append("\n");

        } catch (SQLException e) {
            e.printStackTrace();
            report.append("An error occurred: ").append(e.getMessage());
        }

        return report.toString();
    }

public void generateMonthlySavings(int customer_id, String yearToGenerate) {
    double totalOutgoing = 0;
    double totalIncoming = 0;

    try {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/bankdb",
                "java",
                "password"
        );

        
        String accountQuery = "SELECT account_ID FROM account_records WHERE customer_ID = ?";
        PreparedStatement accountStmt = connection.prepareStatement(accountQuery);
        accountStmt.setInt(1, customer_id);
        ResultSet accountResult = accountStmt.executeQuery();


        while (accountResult.next()) {
            int accountId = accountResult.getInt("account_ID");


            String outgoingQuery = "SELECT SUM(amount) AS totalOutgoing FROM account_transaction_history " +
                    "WHERE sender_acc_ID = ? AND DATE_FORMAT(transaction_date, '%Y') = ?";
            PreparedStatement outgoingStmt = connection.prepareStatement(outgoingQuery);
            outgoingStmt.setInt(1, accountId);
            outgoingStmt.setString(2, yearToGenerate);
            ResultSet outgoingResult = outgoingStmt.executeQuery();

            if (outgoingResult.next()) {
                totalOutgoing += outgoingResult.getDouble("totalOutgoing");
            }


            String incomingQuery = "SELECT SUM(amount) AS totalIncoming FROM account_transaction_history " +
                    "WHERE receiver_acc_ID = ? AND DATE_FORMAT(transaction_date, '%Y') = ?";
            PreparedStatement incomingStmt = connection.prepareStatement(incomingQuery);
            incomingStmt.setInt(1, accountId);
            incomingStmt.setString(2, yearToGenerate);
            ResultSet incomingResult = incomingStmt.executeQuery();

            if (incomingResult.next()) {
                totalIncoming += incomingResult.getDouble("totalIncoming");
            }
        }


        double yearlySavings = totalIncoming - totalOutgoing;


        System.out.println("Yearly Savings Report for " + yearToGenerate);
        System.out.println("Total Incoming: PHP " + Math.round(totalIncoming * 100.0) / 100.0);
        System.out.println("Total Outgoing: PHP " + Math.round(totalOutgoing * 100.0) / 100.0);
        System.out.println("Net Savings: PHP " + Math.round(yearlySavings * 100.0) / 100.0);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}




    public static void viewTransactionHistoryOfAccount(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankdb",
                    "java",
                    "password"
            );

            System.out.print("Input account id to view transaction history: ");
            int id = Integer.parseInt(UserInput.getScanner().nextLine());

            System.out.println("Sort By\n1 - Date\n2 - Size of Transaction");
            System.out.print("Choose option: ");
            int sort = Integer.parseInt(UserInput.getScanner().nextLine());

            String query = "SELECT * FROM account_transaction_history\n" +
                    "WHERE sender_acc_ID = ? OR receiver_acc_ID = ?";
            String orderAmt = " ORDER BY amount DESC;";
            String orderDate = " ORDER BY transaction_date DESC;";

            if(sort == 1){
                query += orderDate;
            } else if (sort == 2){
                query += orderAmt;
            }

            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, id);
                statement.setInt(2, id);

                ResultSet res = statement.executeQuery();
                if (!res.isBeforeFirst() ) {
                    System.out.println("Account has no transaction yet");
                }

                while(res.next()){
                    System.out.println("Transaction ID: " + res.getInt("transaction_ID") +
                            "\tTransaction Date: " + res.getDate("transaction_date") +
                            "\tSender Acc ID: " + res.getInt("sender_acc_ID") +
                            "\tReceiver Acc ID: " + res.getInt("receiver_acc_ID") +
                            "\tAmount: " + res.getDouble("amount"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void viewLoanPaymentHistoryOfAccount() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank_db",
                    "root",
                    "password"
            );
    
            while (true) { // Loop to allow the user to go back
                String idStr = JOptionPane.showInputDialog("Enter Account ID to view Loan Payment History:");
                if (idStr == null) return; // Exit if user cancels
    
                int accountId;
                try {
                    accountId = Integer.parseInt(idStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid Account ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue; // Restart the loop
                }
    
                // Ask for Sorting Option with "Back" button
                String[] sortOptions = {"Sort by Date", "Sort by Payment Amount", "Back"};
                int sortChoice = JOptionPane.showOptionDialog(
                        null,
                        "Choose Sorting Option:",
                        "Sort Payments",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        sortOptions,
                        sortOptions[0]
                );
    
                if (sortChoice == 2 || sortChoice == JOptionPane.CLOSED_OPTION) {
                    continue; // Go back to Account ID input
                }
    
                // Determine Sorting Order
                String orderClause = (sortChoice == 1) ? "ORDER BY loan_amount DESC" : "ORDER BY loan_transaction_date DESC";
    
                // SQL Query to Retrieve Loan Payment History
                String query = "SELECT loan_transaction_ID, loan_transaction_date, lender_acc_ID, borrower_acc_ID, loan_amount " +
                               "FROM loan_transaction_history WHERE lender_acc_ID = ? " + orderClause;
    
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, accountId);
                    ResultSet res = statement.executeQuery();
    
                    // Store Loan Payments in a List
                    List<String> payments = new ArrayList<>();
                    while (res.next()) {
                        String paymentInfo = "Payment ID: " + res.getInt("loan_transaction_ID") +
                                "\nDate: " + res.getDate("loan_transaction_date") +
                                "\nSender Acc ID: " + res.getInt("lender_acc_ID") +
                                "\nReceiver Loan ID: " + res.getInt("borrower_acc_ID") +
                                "\nAmount: PHP " + res.getDouble("loan_amount") + "\n------------------------";
                        payments.add(paymentInfo);
                    }
    
                    // Display Results
                    if (payments.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No loan payment history found for this account.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, String.join("\n", payments), "Loan Payment History", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                break; // Exit loop if everything is completed
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
