package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransferDAO {

    public void addTransfer(Connection con,int fromAcc,int toAcc,double amount) throws Exception {

        String query = "INSERT INTO transfers(from_account,to_account,amount) VALUES(?,?,?)";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, fromAcc);
        ps.setInt(2, toAcc);
        ps.setDouble(3, amount);

        ps.executeUpdate();
    }
}