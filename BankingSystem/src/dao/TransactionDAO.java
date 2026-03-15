package dao;

import util.DBConnection;
import java.sql.*;

public class TransactionDAO {

    public void addTransaction(Connection con,int accNo,String type,double amount) throws Exception {

        String query = "INSERT INTO transactions(account_no,transaction_type,amount) VALUES(?,?,?)";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, accNo);
        ps.setString(2, type);
        ps.setDouble(3, amount);

        ps.executeUpdate();
    }

    public void showTransactions(int accNo) throws Exception{

        Connection con = DBConnection.getConnection();

        String query="SELECT * FROM transactions WHERE account_no=?";

        PreparedStatement ps=con.prepareStatement(query);

        ps.setInt(1,accNo);

        ResultSet rs=ps.executeQuery();

        while(rs.next()){

            System.out.println(
                    rs.getInt("transaction_id")+" | "+
                            rs.getString("transaction_type")+" | "+
                            rs.getDouble("amount")+" | "+
                            rs.getTimestamp("transaction_date")
            );
        }
    }
}