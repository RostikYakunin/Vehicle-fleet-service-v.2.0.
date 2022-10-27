package org.example;

import org.example.console.ConsoleMain;
import org.example.repo.DBConnection.DBConnect;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnect.getConnection();

        ConsoleMain consoleMain = new ConsoleMain(connection);
        consoleMain.startPanel();
    }
}
