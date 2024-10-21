package com.apnacollege.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registerpage";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "vipin@123"; // Update with your MySQL root password

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the form data
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("Gender");
        String state = request.getParameter("state");

        // Validate gender
        if ((!"Male".equals(gender) && !"Female".equals(gender) && !"Other".equals(gender))) {
            out.println("<html><body>");
            out.println("<h2>Registration Failed</h2>");
            out.println("<p>Invalid gender value.</p>");
            out.println("<a href=\"register.html\">Go back to registration page</a>");
            out.println("</body></html>");
        } else {
            // Connect to MySQL and insert the data
            try {
                // Load the MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    String sql = "INSERT INTO register (name, number, email, password, dob, gender, state) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, name);
                    stmt.setString(2, number);
                    stmt.setString(3, email);
                    stmt.setString(4, password);
                    stmt.setString(5, dob);
                    stmt.setString(6, gender);
                    stmt.setString(7, state);

                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        out.println("<html><body>");
                        out.println("<h2>Registration Successful</h2>");
                        out.println("<a href=\"Login.html\">Go to login page</a>");
                        out.println("</body></html>");
                    }
                }
            } catch (ClassNotFoundException e) {
                out.println("<html><body>");
                out.println("<h2>Registration Failed</h2>");
                out.println("<p>Error: Driver not found - " + e.getMessage() + "</p>");
                out.println("<a href=\"register.html\">Go back to registration page</a>");
                out.println("</body></html>");
                e.printStackTrace();
            } catch (SQLException e) {
                out.println("<html><body>");
                out.println("<h2>Registration Failed</h2>");
                out.println("<p>Error: " + e.getMessage() + "</p>");
                out.println("<a href=\"register.html\">Go back to registration page</a>");
                out.println("</body></html>");
                e.printStackTrace();
            }
        }
    }
}
