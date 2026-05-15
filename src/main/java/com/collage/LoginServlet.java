package com.collage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Null check
        if (username == null || password == null) {
            out.println("Error: Form data not received!");
            return;
        }

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to Railway MySQL
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://yamanote.proxy.rlwy.net:40653/railway?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "UqrPfiRJTHKKKGchiEioXgPNqIwsLvaS"
            );

            // SQL query
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM admin WHERE username=? AND password=?");

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // SUCCESS LOGIN
                HttpSession session = request.getSession();
                session.setAttribute("user", username);

                response.sendRedirect("dashboard.jsp");

            } else {

                // INVALID LOGIN
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Invalid Username or Password');");
                out.println("location='index.html';");
                out.println("</script>");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
            out.println("Database Error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("dashboard.jsp");
    }
}
