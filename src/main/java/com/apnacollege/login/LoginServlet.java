package com.apnacollege.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registerpage";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "vipin@123";  // Update with your MySQL root password

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get email and password from the form
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        try {
            // Load the MySQL driver
             Class.forName("com.mysql.cj.jdbc.Driver");
            // Validate the login credentials
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM register WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Login successful

            	response.setContentType("text/html");
//                PrintWriter out = response.getWriter();
            	   out.println("<html><head>");
                   out.println("<style>");
                   out.println("body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
                   out.println(".popup-container { text-align: center; font-family: Arial, sans-serif; }");
                   out.println("</style>");
                   out.println("</head><body>");
                   out.println("<div class='popup-container'>");
                   out.println("<script type='text/javascript'>");
                   out.println("alert('Login Successful!');");
                   out.println("window.location.href = 'home.html';");  // Redirect to home page after alert
                   out.println("</script>");
                   out.println("</div>");
                   out.println("</body></html>");
            } else {
                // Login failed
                out.println("<html><body>");
                out.println("<h2>Invalid credentials</h2>");
                out.println("<p>Login failed. Please try again.</p>");
                out.println("<a href=\"Login.html\">Go back to login page</a>");
                out.println("</body></html>");
            }
        }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
