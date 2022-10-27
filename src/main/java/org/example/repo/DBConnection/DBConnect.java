package org.example.repo.DBConnection;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * class for creating connection to DB
 *
 * @author Yakunin Rostyslav
 * @version 2.0
 */

public class DBConnect {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "Rost";

    private DBConnect() {
    }

    public static Connection authorization() throws IOException, SQLException, ClassNotFoundException {
        System.out.println("""
                Input your action !\s
                                1 - registration
                                2 - connect to DB
                                """);
        int a = Integer.parseInt(inputReader());

        System.out.println("Input your login and password !" );

        switch (a) {
            case 1 -> registrationUsers(inputReader(), inputReader());
            case 2 -> successfulConnection(inputReader(), inputReader());
            default -> System.err.println("Something wrong !");
        }
        return connection;
    }

    private static String inputReader() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        return bf.readLine();
    }

    private static void registrationUsers(String login, String password) throws IOException {
        FileWriter fw = new FileWriter("users.txt", true);
        fw.write(login + "\n");
        fw.write(password + "\n");
        fw.close();
    }

    private static void successfulConnection(String login, String password) throws IOException, SQLException, ClassNotFoundException {
        if (findUser(login, password)) {
            getConnection();
        }
    }

    private static boolean findUser(String login, String password) throws IOException {
        List<String> list = Files.readAllLines(Paths.get("users.txt"));
        return list.contains(login) && list.contains(password);
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.err.println("CONNECTION SUCCESSFUL");
            return connection;
        }
        return connection;
    }

}
