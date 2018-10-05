package lesson1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Main {
	static Connection conn;
	public static void main(String[] args) throws SQLException {
	String dbUrl = "jdbc:mysql://localhost:3306/universety?useSSL=false";	
	String username = "root";
	String password = "12345";
	conn = DriverManager.getConnection(dbUrl, username, password);
	System.out.println("Connected? " + !conn.isClosed());
	
	createTable();
	addStudent();
	for (int i = 0; i < 30; i++) {
		addStudents(i);
	}
	//selectStudent(5);
	//selectStudents();
	deleteStudent(2);
	conn.close();
	}
	private static void createTable() throws SQLException {
		String dropQuery = "drop table if exists student;";
		String query = "create table student("
			+ "id int not null primary key auto_increment,"
			+ "full_name varchar(60) not null,"
			+ "city varchar(45) not null,"
			+ "age int not null"
			+ ");";
	
		Statement stmt = conn.createStatement();
		stmt.execute(dropQuery);
		stmt.execute(query);
		System.out.println("table 'student' create");
		stmt.close();
	}
	private static void addStudent() throws SQLException {
		String query = "insert into student(full_name, city, age) values(?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "Join Jonson");//("Join Jonson",?,?)
		pstmt.setString(2, "Lviv");//("Join Jonson","Lviv",?)
		pstmt.setInt(3, 29);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
	private static void selectStudents() throws SQLException {
		String query = "select * from student;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(
					"id: " + rs.getInt("id") + "\t|" +
			        "Full name: " + rs.getString("full_name") + "\t|" +
					"City: " + rs.getString("city") + "\t|" +
			        "Age: " + rs.getString("age")
					);
		}
	}
	private static void addStudents(int i) throws SQLException {
		String query = "insert into student(full_name, city, age) values(?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, "Join Jonson #" + i);
		pstmt.setString(2, "Lviv #" + i);
		pstmt.setInt(3, 29 + i);
		
		pstmt.executeUpdate();
		pstmt.close();
	}
	private static void selectStudent(int id) throws SQLException {
		String query = "select * from student where id = ?;";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(
					"id: " + rs.getInt("id") + "\t|" +
			        "Full name: " + rs.getString("full_name") + "\t|" +
					"City: " + rs.getString("city") + "\t|" +
			        "Age: " + rs.getString("age")
					);
		}
	}
	private static void deleteStudent(int id) throws SQLException {
		String query = "delete from student where id = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		
	}
}
