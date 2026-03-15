package dao;

import util.DBConnection;
import java.sql.*;

public class CustomerDAO {

    public int addCustomer(String name,String phone,String email,String address) throws Exception{

        Connection con = DBConnection.getConnection();

        String query = "INSERT INTO customers(full_name,phone,email,address) VALUES(?,?,?,?)";

        //PreparedStatement ps = con.prepareStatement(query);
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1,name);
        ps.setString(2,phone);
        ps.setString(3,email);
        ps.setString(4,address);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if(rs.next()){
            System.out.println("cutomer id");
            int customerId = rs.getInt(1);

            System.out.println("Customer Created with ID: " + customerId);

            return customerId;
        }
        throw new Exception("Customer creation failed");
    }
}