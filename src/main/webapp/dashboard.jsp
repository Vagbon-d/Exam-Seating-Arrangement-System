<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String user = (String) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard - ExamSeater</title>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<style>
    body {
        font-family: 'Inter', sans-serif;
        background-color: #f4f7f6;
        margin: 0;
        color: #333;
    }
    .navbar {
        background: #2c5364;
        color: white;
        padding: 15px 40px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }
    .navbar .logo {
        font-size: 20px;
        font-weight: 700;
        letter-spacing: 1px;
    }
    .navbar .profile {
        display: flex;
        align-items: center;
        gap: 15px;
    }
    .btn-logout {
        background: transparent;
        border: 1px solid rgba(255,255,255,0.5);
        color: white;
        padding: 8px 16px;
        border-radius: 4px;
        cursor: pointer;
        transition: background 0.3s;
        text-decoration: none;
        font-size: 14px;
    }
    .btn-logout:hover {
        background: rgba(255,255,255,0.1);
    }
    .content {
        max-width: 1000px;
        margin: 40px auto;
        padding: 0 20px;
    }
    .page-header {
        margin-bottom: 30px;
    }
    .page-header h2 {
        margin: 0 0 10px 0;
        font-size: 28px;
        color: #111;
    }
    .page-header p {
        margin: 0;
        color: #666;
    }
    .card {
        background: white;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0,0,0,0.05);
        padding: 30px;
    }
    .form-grid {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 24px;
    }
    .form-section-title {
        grid-column: 1 / -1;
        font-size: 18px;
        font-weight: 600;
        border-bottom: 1px solid #eee;
        padding-bottom: 10px;
        margin-top: 10px;
        color: #2c5364;
    }
    .form-group {
        display: flex;
        flex-direction: column;
    }
    label {
        font-weight: 500;
        margin-bottom: 8px;
        font-size: 14px;
        color: #444;
    }
    input[type="text"], input[type="number"], input[type="date"], select {
        padding: 12px 16px;
        border: 1px solid #ddd;
        border-radius: 8px;
        font-family: 'Inter', sans-serif;
        font-size: 15px;
        transition: border-color 0.3s;
        box-sizing: border-box;
    }
    input[type="text"]:focus, input[type="number"]:focus, input[type="date"]:focus, select:focus {
        outline: none;
        border-color: #2c5364;
    }
    .checkbox-group {
        grid-column: 1 / -1;
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 15px;
        background: #f8fafc;
        padding: 20px;
        border-radius: 8px;
        border: 1px solid #e1e5eb;
    }
    .checkbox-item {
        display: flex;
        align-items: center;
        font-size: 14px;
        background: white;
        padding: 10px;
        border: 1px solid #eee;
        border-radius: 6px;
    }
    .checkbox-item input {
        margin-right: 12px;
        width: 18px;
        height: 18px;
        accent-color: #2c5364;
        cursor: pointer;
    }
    .form-actions {
        grid-column: 1 / -1;
        margin-top: 20px;
        display: flex;
        justify-content: flex-end;
    }
    .btn-submit {
        padding: 14px 32px;
        background: #2c5364;
        color: white;
        border: none;
        border-radius: 8px;
        font-size: 16px;
        font-weight: 600;
        cursor: pointer;
        transition: background 0.3s, transform 0.2s;
    }
    .btn-submit:hover {
        background: #1e3a47;
        transform: translateY(-1px);
    }
</style>
</head>
<body>

<div class="navbar">
    <div class="logo">ExamSeater</div>
    <div class="profile">
        <span>Welcome, <%= user %></span>
        <a href="LogoutServlet" class="btn-logout">Sign Out</a>
    </div>
</div>

<div class="content">
    <div class="page-header">
        <h2>Seating Configuration</h2>
        <p>Set the parameters for the upcoming examination to generate a seating plan.</p>
    </div>

    <div class="card">
        <form action="SeatingServlet" method="post" class="form-grid">
            
            <div class="form-section-title">Examination Details</div>

            <div class="form-group">
                <label>Examination Date</label>
                <input type="date" name="examDate" required>
            </div>

            <div class="form-group">
                <label>Academic Year</label>
                <select name="academicYear">
                    <option>2023 - 2024</option>
                    <option>2024 - 2025</option>
                    <option>2025 - 2026</option>
                </select>
            </div>

            <div class="form-group">
                <label>Shift</label>
                <select name="shift">
                    <option>Morning (9:00 AM - 12:00 PM)</option>
                    <option>Afternoon (2:00 PM - 5:00 PM)</option>
                </select>
            </div>

            <div class="form-group">
                <label>Internal Test</label>
                <select name="test">
                    <option>Internal 1</option>
                    <option>Internal 2</option>
                    <option>Internal 3</option>
                    <option>Final Semester</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>Branch</label>
                <select name="branch">
                    <option>Computer Engineering</option>
                    <option>Civil Engineering</option>
                    <option>Mechanical Engineering</option>
                    <option>Electrical Engineering</option>
                    <option>Electronics & Communication</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>Subject</label>
                <input type="text" name="subject" placeholder="e.g. Advanced Data Structures" required>
            </div>
            
            <div class="form-section-title">Student Allocation</div>

            <div class="form-group">
                <label>Starting Roll No</label>
                <input type="number" name="startRoll" placeholder="e.g. 101" required>
            </div>
            
            <div class="form-group">
                <label>Total Number of Students</label>
                <input type="number" name="students" placeholder="e.g. 60" required>
            </div>
            
            <div class="form-section-title">Classroom Availability</div>

            <div class="checkbox-group">
                <label class="checkbox-item"><input type="checkbox" name="room" value="A"> <div><strong>Room A</strong><br><small>15 benches (30 seats)</small></div></label>
                <label class="checkbox-item"><input type="checkbox" name="room" value="B"> <div><strong>Room B</strong><br><small>12 benches (24 seats)</small></div></label>
                <label class="checkbox-item"><input type="checkbox" name="room" value="C"> <div><strong>Room C</strong><br><small>10 benches (20 seats)</small></div></label>
                <label class="checkbox-item"><input type="checkbox" name="room" value="D"> <div><strong>Room D</strong><br><small>8 benches (16 seats)</small></div></label>
                <label class="checkbox-item"><input type="checkbox" name="room" value="E"> <div><strong>Room E</strong><br><small>20 benches (40 seats)</small></div></label>
                <label class="checkbox-item"><input type="checkbox" name="room" value="F"> <div><strong>Room F</strong><br><small>25 benches (50 seats)</small></div></label>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn-submit">Generate Seating Plan</button>
            </div>
        </form>
    </div>
</div>

</body>
</html>