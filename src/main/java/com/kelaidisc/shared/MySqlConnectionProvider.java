package com.kelaidisc.shared;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionProvider {

    private static Connection conn = null;

    static {
        String url = "jdbc:mysql://localhost:3306/university";
        String user = "root";
        String pass = "root";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
//            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Couldn't connect to database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
        return conn;
    }
}
