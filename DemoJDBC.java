//import java.sql.*;
//
////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//
///*
//Import Package
//Load & Register
//Create Connection
//Create Statement
//Execute statement
//process the result
//close connection
//*/
//
//
//public class DemoJDBC {
//    public static void main(String[] args) throws SQLException {
//
//        String url = "jdbc:postgresql://localhost:5432/demo";
//        String uname = "postgres";
//        String pass = "2406";
//        String sql = "select * from student";
//
//        Connection con = DriverManager.getConnection(url, uname,pass);
//
//        System.out.println("Connection Established");
//
//        Statement st = con.createStatement();
//        ResultSet rs = st.executeQuery(sql);
////        rs.next();
////        String name = rs.getString("sname");
////        System.out.println("Name of the Student is " + name );
//
////        while(rs.next()){
////            System.out.print(rs.getInt(1) + "-");
////            System.out.print(rs.getString(2) + "-");
////            System.out.println(rs.getInt(3));
////        }
//
//        con.close();
//        System.out.println("Connection Closed");
//    }
//}

import java.sql.*;

public class DemoJDBC {
    public static void main(String[] args) {
        // JDBC URL Format: jdbc:<dbtype>://<host>:<port>/<dbname>
        String url = "jdbc:postgresql://localhost:5432/demo";
        String user = "postgres";
        String pass = "2406";

        // --- 1. Create Connection ---
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            System.out.println("✅ Connection Established");

            // --- 2. CREATE operation ---
            // Insert a new student
//            String insertSQL = "INSERT INTO student (sid, sname, marks) VALUES (?, ?, ?)";
//            try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
//                ps.setInt(1, 101); // sid
//                ps.setString(2, "Omkar"); // sname
//                ps.setInt(3, 95); // marks
//                int rows = ps.executeUpdate();
//                System.out.println("🟢 Rows Inserted: " + rows);
//            }

            // --- 3. READ operation ---
//             Retrieve all students
//            String selectSQL = "SELECT * FROM student";
//            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(selectSQL)) {
//                System.out.println("📄 Student Records:");
//                while (rs.next()) {
//                    int id = rs.getInt("sid");       // column 1: id
//                    String name = rs.getString("sname"); // column 2: name
//                    int marks = rs.getInt("marks");     // column 3: marks
//                    System.out.println(id + " | " + name + " | " + marks);
//                }
//            }

            // --- 4. UPDATE operation ---
            // Update a student's marks
            String updateSQL = "UPDATE student SET marks = ? WHERE sid = ?";
            try (PreparedStatement ps = con.prepareStatement(updateSQL)) {
                ps.setInt(1, 60);  // new marks
                ps.setInt(2, 101); // where sid = 101
                int rows = ps.executeUpdate();
                System.out.println("🟡 Rows Updated: " + rows);
            }

            // --- 5. DELETE operation ---
            // Delete a student
            String deleteSQL = "DELETE FROM student WHERE sid = ?";
            try (PreparedStatement ps = con.prepareStatement(deleteSQL)) {
                ps.setInt(1, 101);
                int rows = ps.executeUpdate();
                System.out.println("🔴 Rows Deleted: " + rows);
            }

        } catch (SQLException e) {
            System.err.println("❌ DB Error: " + e.getMessage());
        }
        System.out.println("🔚 Program Ended");
    }
}
