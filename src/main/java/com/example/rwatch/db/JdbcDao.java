package com.example.rwatch.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcDao {

    Connection connection;

    private static final String DbUrl = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7607943?useSSL=false";
    private static final String DbUser = "sql7607943";
    private static final String DbPassword = "Fxb6J8puBV";
    private static final String INSERT_QUERY = "INSERT INTO person(fisrtname, surename, lastname, birthday," +
            "phonenumber, serialpas, numpas, regfrom, regto, patfrom, patto, city) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertQuery(String firstName, String sureName, String lastName,
                            String birthDay, String phoneNumber, String serialPass,
                            String numPass, String regOne, String regTo, String patOne,
                            String patTo, String city) throws Exception
    {

        Connection connection = DriverManager.getConnection(DbUrl, DbUser, DbPassword);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, sureName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, birthDay);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, serialPass);
            preparedStatement.setString(7, numPass);
            preparedStatement.setString(8, regOne);
            preparedStatement.setString(9, regTo);
            preparedStatement.setString(10, patOne);
            preparedStatement.setString(11, patTo);
            preparedStatement.setString(12, city);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof  SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
