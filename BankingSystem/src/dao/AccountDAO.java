package dao;

import util.DBConnection;
import java.sql.*;

public class AccountDAO {

    public void createAccount(int customerId,double balance) throws Exception{

        Connection con = DBConnection.getConnection();

        String query="INSERT INTO accounts(customer_id,balance) VALUES(?,?)";

        PreparedStatement ps=con.prepareStatement(query);

        ps.setInt(1,customerId);
        ps.setDouble(2,balance);

        ps.executeUpdate();

        System.out.println("Account Created");
    }

    public double getBalance(int accountNo) throws Exception{

        Connection con = DBConnection.getConnection();

        String query="SELECT balance FROM accounts WHERE account_no=?";

        PreparedStatement ps=con.prepareStatement(query);

        ps.setInt(1,accountNo);

        ResultSet rs=ps.executeQuery();

        if(rs.next())
            return rs.getDouble("balance");

        return 0;
    }
}