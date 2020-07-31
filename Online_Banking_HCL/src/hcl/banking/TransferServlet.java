package hcl.banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransferServlet extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRes = null;
		try {
			//getting a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_banking?autoReconnect=true&useSSL=false","root", "student");
			//creating a statement
			myStmt = myConn.createStatement();
			//Execute SQL Query
			myRes = myStmt.executeQuery("select * from accounts");
			//process the result
			out.println("<br>Ammount : 1000 is transfered to ACNO: ******** Successful!");
			myStmt.executeUpdate("update accounts set avl_balance = avl_balance-1000");
			myRes = myStmt.executeQuery("select * from transfer_details");
			myStmt.executeUpdate("update transfer_details set T5 = T4");
			myStmt.executeUpdate("update transfer_details set T4 = T3");
			myStmt.executeUpdate("update transfer_details set T3 = T2");
			myStmt.executeUpdate("update transfer_details set T2 = T1");
			myStmt.executeUpdate("update transfer_details set T1 = 1000");
			res.sendRedirect("http://localhost:8081/Online_Banking_HCL/OnlineBanking");
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if(myRes != null) {
				try {
					myRes.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(myConn != null) {
				try {
					myConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
