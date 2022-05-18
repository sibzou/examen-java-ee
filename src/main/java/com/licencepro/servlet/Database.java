package com.licencepro.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class Database {
    public static Connection getConnection(HttpServletResponse response) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:/home/martin/lp/systeme/examen/db");
        } catch(SQLException exception) {
            try {
                PrintWriter writer = response.getWriter();
                writer.println("sql connection error: " + exception.getMessage());
            } catch(IOException ioException) {}

            System.exit(1);
            return null;
        }
    }
}
