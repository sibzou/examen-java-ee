package com.licencepro.servlet;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Restaurant extends HttpServlet {
    private void writeJsonDish(ResultSet rs, PrintWriter writer)
            throws SQLException {

        writer.write("{\"name\":\"" + rs.getString(1) + "\",\"type\":\""
            + rs.getString(2) + "\",\"price\":" + rs.getFloat(3) + "}");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        Connection conn = Database.getConnection(res);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        PrintWriter writer = res.getWriter();
        String idStr = req.getParameter("id");
        int id = 0;

        try {
            id = Integer.parseInt(idStr);
        } catch(NumberFormatException exception) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.println("Not found");
            return;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement("select restaurant.name, address, kitchenType.name from restaurant, kitchenType where restaurant.kitchenTypeId = kitchenType.id and restaurant.id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next()) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writer.println("Not found");
                return;
            }

            writer.print("{\"name\":\"" + rs.getString(1) + "\",\"address\":\""
                + rs.getString(2) + "\",\"kitchenType\":\"" + rs.getString(3)
                + "\",\"dishes\":[");


            stmt = conn.prepareStatement("select dish.name, dishType.name, price from dish, dishType, restaurant where dish.dishTypeId = dishType.id and dish.restaurantId = restaurant.id and restaurant.id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                writeJsonDish(rs, writer);

                while(rs.next()) {
                    writer.print(",");
                    writeJsonDish(rs, writer);
                }
            }
        } catch(SQLException exception) {
            writer.println("sql error: " + exception.getMessage());
        }

        writer.print("]}");
    }
}
