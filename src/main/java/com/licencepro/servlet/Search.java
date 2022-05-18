package com.licencepro.servlet;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Search extends HttpServlet {
    private void writeJsonRestaurant(ResultSet rs, PrintWriter writer)
            throws SQLException {

        writer.print("{\"id\":" + rs.getInt(1) + ",\"name\":\""
            + rs.getString(2) + "\",\"kitchenType\":\""
            + rs.getString(3) + "\"}");
    }

    private void writeJsonResult(ResultSet rs, PrintWriter writer)
            throws SQLException {

        writer.print("[");

        if(rs.next()) {
            writeJsonRestaurant(rs, writer);

            while(rs.next()) {
                writer.print(",");
                writeJsonRestaurant(rs, writer);
            }
        }

        writer.print("]");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        Connection conn = Database.getConnection(res);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        PrintWriter writer = res.getWriter();
        String query = req.getParameter("query");

        try {
            if(query == null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select restaurant.id, restaurant.name, kitchenType.name from restaurant, kitchenType where restaurant.kitchenTypeId = kitchenType.id");
                writeJsonResult(rs, writer);
            } else {
                PreparedStatement stmt = conn.prepareStatement("select restaurant.id, restaurant.name, kitchenType.name from restaurant, kitchenType where restaurant.kitchenTypeId = kitchenType.id and (restaurant.name like ? or kitchenType.name like ?)");

                String queryExp = "%" + query + "%";
                stmt.setString(1, queryExp);
                stmt.setString(2, queryExp);

                ResultSet rs = stmt.executeQuery();
                writeJsonResult(rs, writer);
            }
        } catch(SQLException exception) {
            writer.println("sql error: " + exception.getMessage());
        }
    }
}
