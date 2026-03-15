package service;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import dao.TransferDAO;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BankingService {
    CustomerDAO customerDAO = new CustomerDAO();
    AccountDAO accountDAO = new AccountDAO();
    TransactionDAO transactionDAO = new TransactionDAO();
    TransferDAO transferDAO = new TransferDAO();

    public void createCustomerAccount(String name,String phone,String email,String address,double balance) throws Exception{

        int customerId = customerDAO.addCustomer(name,phone,email,address);

        accountDAO.createAccount(customerId,balance);

        System.out.println("Customer and Account Created Successfully");
    }
    public void transferMoney(int fromAcc, int toAcc, double amount) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            // start transaction
            con.setAutoCommit(false);

            // check sender balance
            double balance = accountDAO.getBalance(fromAcc);

            if(balance < amount){
                System.out.println("Insufficient Balance");
                return;
            }

            // withdraw from sender
            String withdrawQuery =
                    "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";

            PreparedStatement ps1 = con.prepareStatement(withdrawQuery);
            ps1.setDouble(1, amount);
            ps1.setInt(2, fromAcc);
            ps1.executeUpdate();

            // deposit to receiver
            String depositQuery =
                    "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";

            PreparedStatement ps2 = con.prepareStatement(depositQuery);
            ps2.setDouble(1, amount);
            ps2.setInt(2, toAcc);
            ps2.executeUpdate();
            transferDAO.addTransfer(con,fromAcc,toAcc,amount);
            // record sender transaction
            transactionDAO.addTransaction(con, fromAcc, "TRANSFER_DEBIT", amount);

            // record receiver transaction
            transactionDAO.addTransaction(con, toAcc, "TRANSFER_CREDIT", amount);

            // commit transaction
            con.commit();

            System.out.println("Money Transfer Successful");
            double senderBalance = accountDAO.getBalance(fromAcc);
            double receiverBalance = accountDAO.getBalance(toAcc);

            System.out.println("Sender Available Balance: " + senderBalance);
            System.out.println("Receiver Available Balance: " + receiverBalance);

        } catch (Exception e) {

            try {

                if(con != null){
                    con.rollback();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Transfer Failed");

        } finally {

            try {

                if(con != null){
                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void deposit(int accNo, double amount) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            // start transaction
            con.setAutoCommit(false);

            String query = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, amount);
            ps.setInt(2, accNo);

            ps.executeUpdate();

            // insert transaction record
            transactionDAO.addTransaction(con, accNo, "DEPOSIT", amount);

            // commit if both succeed
            con.commit();
            double balance = accountDAO.getBalance(accNo);
            System.out.println("Deposit Successful");
            System.out.println("Current Available Balance: " + balance);

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                    double balance = accountDAO.getBalance(accNo);
                    System.out.println("Transaction Failed");
                    System.out.println("Current Available Balance: " + balance);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Transaction Failed");

        } finally {

            try {

                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void withdraw(int accNo, double amount) {

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            double balance = accountDAO.getBalance(accNo);

            if (balance < amount) {

                System.out.println("Insufficient Balance");
                return;
            }

            String query = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, amount);
            ps.setInt(2, accNo);

            ps.executeUpdate();

            transactionDAO.addTransaction(con, accNo, "WITHDRAW", amount);

            con.commit();
            double updatedBalance = accountDAO.getBalance(accNo);
            System.out.println("Withdraw Successful");
            System.out.println("Current Available Balance: " + updatedBalance);

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                    double updatedBalance = accountDAO.getBalance(accNo);
                    System.out.println("Transaction Failed");
                    System.out.println("Current Available Balance: " + updatedBalance);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Transaction Failed");

        } finally {

            try {

                if (con != null) {

                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}