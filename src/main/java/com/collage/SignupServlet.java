package com.collage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        if (username == null || password == null || confirmPassword == null) {
            showError(out, "Form data not received!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError(out, "Passwords do not match!");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");

            // Check if user already exists
            PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM admin WHERE username=?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                showError(out, "Username already exists!");
            } else {
                // Insert new user
                PreparedStatement insertStmt = con.prepareStatement("INSERT INTO admin (username, password) VALUES (?, ?)");
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                
                int i = insertStmt.executeUpdate();
                if (i > 0) {
                    showSuccess(out);
                } else {
                    showError(out, "Registration failed due to a database error.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError(out, "An error occurred: " + e.getMessage());
        }
    }

    private void showError(PrintWriter out, String message) {
        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Sign Up Error - ExamSeater</title>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
        out.println("<style>body{font-family:'Inter',sans-serif;background:#f4f7f6;margin:0;display:flex;justify-content:center;align-items:center;height:100vh;color:#333;}");
        out.println(".error-card{background:white;padding:40px;border-radius:12px;box-shadow:0 10px 30px rgba(0,0,0,0.05);text-align:center;max-width:400px;width:100%;box-sizing:border-box;}");
        out.println(".error-card h2{color:#e74c3c;margin-top:0;}");
        out.println(".error-card p{color:#666;margin-bottom:30px;line-height:1.5;}");
        out.println(".btn-try-again{display:inline-block;padding:12px 24px;background:#2c5364;color:white;text-decoration:none;border-radius:8px;font-weight:500;transition:background 0.3s;}");
        out.println(".btn-try-again:hover{background:#1e3a47;}");
        out.println("</style></head><body>");
        out.println("<div class='error-card'>");
        out.println("<h2>Sign Up Failed</h2>");
        out.println("<p>" + message + "</p>");
        out.println("<a href='signup.html' class='btn-try-again'>Try Again</a>");
        out.println("</div></body></html>");
    }

    private void showSuccess(PrintWriter out) {
        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Success - ExamSeater</title>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
        out.println("<style>body{font-family:'Inter',sans-serif;background:#f4f7f6;margin:0;display:flex;justify-content:center;align-items:center;height:100vh;color:#333;}");
        out.println(".success-card{background:white;padding:40px;border-radius:12px;box-shadow:0 10px 30px rgba(0,0,0,0.05);text-align:center;max-width:400px;width:100%;box-sizing:border-box;}");
        out.println(".success-card h2{color:#27ae60;margin-top:0;}");
        out.println(".success-card p{color:#666;margin-bottom:30px;line-height:1.5;}");
        out.println(".btn-login{display:inline-block;padding:12px 24px;background:#2c5364;color:white;text-decoration:none;border-radius:8px;font-weight:500;transition:background 0.3s;}");
        out.println(".btn-login:hover{background:#1e3a47;}");
        out.println("</style></head><body>");
        out.println("<div class='success-card'>");
        out.println("<h2>Registration Successful!</h2>");
        out.println("<p>Your admin account has been created successfully. You can now log in to the dashboard.</p>");
        out.println("<a href='index.html' class='btn-login'>Go to Login</a>");
        out.println("</div></body></html>");
    }
}
