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

public class AddServlet  extends HttpServlet {
	@SuppressWarnings("resource")
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRes = null;
		try {
			//getting a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_banking?autoReconnect=true&useSSL=false","root", "student");
			//out.println("Database Connection Successful");
			//creating a statement
			myStmt = myConn.createStatement();
			//Execute SQL Query
			myRes = myStmt.executeQuery("select * from accounts");
			//process the result
			out.println("<html><body>");
			if(myRes.next())
			{
			out.println("<br>Name			:" + myRes.getString("NAME_")); //printing account details
			out.println("<br>Account Number	:" + myRes.getString("ACNO"));
			out.println("<br>Date Of Birth	:" + myRes.getString("DOB"));
			out.println("<br>Mobile Number	:" + myRes.getString("MOB_NUM"));
			out.println("<br><mark>Available Balance:</mark>" + myRes.getString("AVL_BALANCE"));
			}
			out.println("<br>Last 5 Transaction Details : ");
			myRes = myStmt.executeQuery("select * from transfer_details");
			if(myRes.next())
			{
				out.println(myRes.getString("T1")); //printing last five transaction details from transcation table
				out.println(myRes.getString("T2"));
				out.println(myRes.getString("T3"));
				out.println(myRes.getString("T4"));
				out.println(myRes.getString("T5"));
			}
			out.println("</body></html>");
			out.println("<br><form action = \"Transfer\"><input type = \"submit\" value = \"Transfer Money\"></form>");
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
