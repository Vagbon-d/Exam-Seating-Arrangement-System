package com.collage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import java.sql.*;

@WebServlet("/SeatingServlet")
public class SeatingServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String examDate = request.getParameter("examDate");
        String academicYear = request.getParameter("academicYear");
        String shift = request.getParameter("shift");
        String test = request.getParameter("test");
        String branch = request.getParameter("branch");
        String subject = request.getParameter("subject");

        int startRoll = Integer.parseInt(request.getParameter("startRoll"));
        int students = Integer.parseInt(request.getParameter("students"));
        int originalStudents = students;

        String[] rooms = request.getParameterValues("room");

        if (rooms == null) {
            out.println("Please select at least one classroom.");
            return;
        }

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("A", 15);
        map.put("B", 12);
        map.put("C", 10);
        map.put("D", 8);
        map.put("E", 20);
        map.put("F", 25);

        int roll = startRoll;

        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Seating Plan - ExamSeater</title>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
        out.println("<style>");
        out.println("body{font-family:'Inter',sans-serif;background:#f4f7f6;margin:0;padding:0;color:#333;}");
        out.println(".topbar{background:#2c5364;color:white;padding:15px 40px;display:flex;justify-content:space-between;align-items:center;box-shadow:0 2px 10px rgba(0,0,0,0.1);}");
        out.println(".topbar .logo{font-size:20px;font-weight:700;letter-spacing:1px;}");
        out.println(".actions{display:flex;gap:15px;}");
        out.println(".btn{padding:8px 16px;border-radius:6px;text-decoration:none;font-weight:500;cursor:pointer;font-size:14px;border:none;font-family:'Inter',sans-serif;transition:background 0.3s;}");
        out.println(".btn-back{background:rgba(255,255,255,0.1);color:white;border:1px solid rgba(255,255,255,0.3);}");
        out.println(".btn-back:hover{background:rgba(255,255,255,0.2);}");
        out.println(".btn-print{background:white;color:#2c5364;font-weight:600;}");
        out.println(".btn-print:hover{background:#f8f9fa;}");
        out.println(".container{max-width:900px;margin:40px auto;background:white;padding:40px;border-radius:12px;box-shadow:0 4px 20px rgba(0,0,0,0.05);}");
        out.println(".doc-header{text-align:center;margin-bottom:40px;padding-bottom:20px;border-bottom:2px solid #eee;}");
        out.println(".doc-header h1{margin:0 0 10px;color:#111;font-size:28px;}");
        out.println(".doc-header p{margin:0;color:#555;font-size:16px;}");
        out.println(".meta-grid{display:grid;grid-template-columns:1fr 1fr;gap:20px;margin-bottom:40px;background:#f8fafc;padding:20px;border-radius:8px;border:1px solid #e1e5eb;}");
        out.println(".meta-item{display:flex;flex-direction:column;}");
        out.println(".meta-label{font-size:12px;color:#666;text-transform:uppercase;letter-spacing:0.5px;font-weight:600;margin-bottom:4px;}");
        out.println(".meta-value{font-size:16px;font-weight:500;color:#111;}");
        out.println("table{width:100%;margin:0 0 40px;border-collapse:collapse;}");
        out.println("th{background:#f1f5f9;color:#334155;padding:12px 16px;text-align:left;font-weight:600;border-bottom:2px solid #cbd5e1;}");
        out.println("td{padding:12px 16px;border-bottom:1px solid #e2e8f0;color:#334155;}");
        out.println(".room-title{color:#0f172a;margin-bottom:15px;font-size:20px;font-weight:600;display:flex;align-items:center;}");
        out.println(".room-title::before{content:'';display:block;width:6px;height:24px;background:#2c5364;margin-right:10px;border-radius:3px;}");
        out.println(".message{text-align:center;font-weight:500;margin-top:20px;padding:16px;border-radius:8px;}");
        out.println(".success{background:#ecfdf5;color:#065f46;border:1px solid #a7f3d0;}");
        out.println(".error{background:#fef2f2;color:#991b1b;border:1px solid #fecaca;}");
        out.println("@media print{");
        out.println("  body{background:white;padding:0;}");
        out.println("  .topbar, .btn-back, .btn-print{display:none;}");
        out.println("  .container{box-shadow:none;margin:0;padding:0;max-width:100%;}");
        out.println("}");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<div class='topbar'>");
        out.println("<div class='logo'>ExamSeater</div>");
        out.println("<div class='actions'>");
        out.println("<a href='dashboard.jsp' class='btn btn-back'>Back to Dashboard</a>");
        out.println("<button onclick='window.print()' class='btn btn-print'>Print Document</button>");
        out.println("</div></div>");

        out.println("<div class='container'>");

        out.println("<div class='doc-header'>");
        out.println("<h1>Official Seating Plan</h1>");
        out.println("<p>Generated by ExamSeater</p>");
        out.println("</div>");

        out.println("<div class='meta-grid'>");
        out.println("<div class='meta-item'><span class='meta-label'>Date</span><span class='meta-value'>" + (examDate != null && !examDate.isEmpty() ? examDate : "N/A") + "</span></div>");
        out.println("<div class='meta-item'><span class='meta-label'>Academic Year</span><span class='meta-value'>" + (academicYear != null ? academicYear : "N/A") + "</span></div>");
        out.println("<div class='meta-item'><span class='meta-label'>Shift</span><span class='meta-value'>" + (shift != null ? shift : "N/A") + "</span></div>");
        out.println("<div class='meta-item'><span class='meta-label'>Examination</span><span class='meta-value'>" + test + "</span></div>");
        out.println("<div class='meta-item'><span class='meta-label'>Branch</span><span class='meta-value'>" + branch + "</span></div>");
        out.println("<div class='meta-item' style='grid-column: 1 / -1;'><span class='meta-label'>Subject</span><span class='meta-value'>" + subject + "</span></div>");
        out.println("</div>");

        for (String r : rooms) {
            int benches = map.get(r);
            out.println("<div class='room-title'>Room " + r + "</div>");
            out.println("<table>");
            out.println("<tr><th>Bench No.</th><th>Seat 1 (Roll No)</th><th>Seat 2 (Roll No)</th></tr>");

            for (int i = 1; i <= benches; i++) {
                out.print("<tr><td><strong>Bench " + i + "</strong></td>");
                for (int j = 1; j <= 2; j++) {
                    out.print("<td>");
                    if (students > 0) {
                        out.print("R" + roll);
                        roll++;
                        students--;
                    } else {
                        out.print("<span style='color:#ccc'>-</span>");
                    }
                    out.print("</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
            if (students <= 0) break;
        }

        if (students > 0) {
            out.println("<div class='message error'>Warning: Remaining students not seated - <b>" + students + "</b></div>");
        } else {
            out.println("<div class='message success'>Success: All students have been seated.</div>");
        }

        out.println("</div>");
        out.println("</body></html>");

        // Database persistence logic
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "");

            // Ensure table exists
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS seating_plans (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "exam_date VARCHAR(50), " +
                    "academic_year VARCHAR(50), " +
                    "shift VARCHAR(50), " +
                    "test VARCHAR(50), " +
                    "branch VARCHAR(100), " +
                    "subject VARCHAR(100), " +
                    "start_roll INT, " +
                    "total_students INT, " +
                    "rooms_allocated VARCHAR(100), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            stmt.close();

            // Insert seating configuration
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO seating_plans (exam_date, academic_year, shift, test, branch, subject, start_roll, total_students, rooms_allocated) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, examDate);
            ps.setString(2, academicYear);
            ps.setString(3, shift);
            ps.setString(4, test);
            ps.setString(5, branch);
            ps.setString(6, subject);
            ps.setInt(7, startRoll);
            ps.setInt(8, originalStudents);
            ps.setString(9, String.join(",", rooms));
            ps.executeUpdate();

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}