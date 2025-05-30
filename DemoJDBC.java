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
           String insertSQL = "INSERT INTO student (sid, sname, marks) VALUES (?, ?, ?)";
           try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
               ps.setInt(1, 101); // sid
               ps.setString(2, "Omkar"); // sname
               ps.setInt(3, 95); // marks
               int rows = ps.executeUpdate();
               System.out.println("🟢 Rows Inserted: " + rows);
           }

            // --- 3. READ operation ---
            // Retrieve all students
           String selectSQL = "SELECT * FROM student";
           try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(selectSQL)) {
               System.out.println("📄 Student Records:");
               while (rs.next()) {
                   int id = rs.getInt("sid");       // column 1: id
                   String name = rs.getString("sname"); // column 2: name
                   int marks = rs.getInt("marks");     // column 3: marks
                   System.out.println(id + " | " + name + " | " + marks);
               }
           }

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


🔍 Breakdown of CRUD
    
Operation    	SQL	              Code Involved
Create	    INSERT INTO	          PreparedStatement with .executeUpdate()
Read	    SELECT * FROM	      Statement + ResultSet loop
Update	    UPDATE ... SET	      PreparedStatement with updated values
Delete	    DELETE FROM	          PreparedStatement for deletion
 
    
Why Use PreparedStatement?
Prevents SQL Injection
Optimizes repeated executions (compiled once)



How to add/update/delete multiple records at once efficiently using JDBC. --> Use JDBC Batch Processing

✅ 1. Insert Multiple Rows (Batch INSERT)

String insertSQL = "INSERT INTO student (sid, sname, marks) VALUES (?, ?, ?)";
try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
    // First student
    ps.setInt(1, 102);
    ps.setString(2, "Rahul");
    ps.setInt(3, 90);
    ps.addBatch(); // add to batch

    // Second student
    ps.setInt(1, 103);
    ps.setString(2, "Sneha");
    ps.setInt(3, 85);
    ps.addBatch();

    // Third student
    ps.setInt(1, 104);
    ps.setString(2, "Aman");
    ps.setInt(3, 88);
    ps.addBatch();

    // Execute all at once
    int[] rows = ps.executeBatch();
    System.out.println("🟢 Inserted Rows Count: " + rows.length);
}


✅ 2. Update Multiple Rows (Batch UPDATE)

String updateSQL = "UPDATE student SET marks = ? WHERE sid = ?";
try (PreparedStatement ps = con.prepareStatement(updateSQL)) {
    // Update Rahul
    ps.setInt(1, 95);
    ps.setInt(2, 102);
    ps.addBatch();

    // Update Sneha
    ps.setInt(1, 91);
    ps.setInt(2, 103);
    ps.addBatch();

    int[] rows = ps.executeBatch();
    System.out.println("🟡 Updated Rows Count: " + rows.length);
}


✅ 3. Delete Multiple Rows (Batch DELETE)

String deleteSQL = "DELETE FROM student WHERE sid = ?";
try (PreparedStatement ps = con.prepareStatement(deleteSQL)) {
    ps.setInt(1, 102);
    ps.addBatch();

    ps.setInt(1, 103);
    ps.addBatch();

    ps.setInt(1, 104);
    ps.addBatch();

    int[] rows = ps.executeBatch();
    System.out.println("🔴 Deleted Rows Count: " + rows.length);
}


    
