package app.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBase {
    private static DataBase ourInstance = new DataBase();

    Connection connection;

    public static DataBase getInstance() {
        return ourInstance;
    }

    private DataBase() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","student","student");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public Connection getConnection() {
        return connection;
    }
}
