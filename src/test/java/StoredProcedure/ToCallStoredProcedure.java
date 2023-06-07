package StoredProcedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ToCallStoredProcedure 
{
	Connection con = null;
	Statement stmt = null;
	CallableStatement cstmt ;
	ResultSet rs1    ;
	ResultSet rs2    ;

	
	
	@BeforeClass
	void setup() throws SQLException
	{
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","Taha@MySQL911");
				
	}  
 
	@Test
	void testProcedure() throws SQLException
	{
		cstmt = con.prepareCall("{Call SelectAllCustomer()}");//it is used to call stored procedure
		rs1 = cstmt.executeQuery();//it is use to execute the query written inside the stored procedure
		
		stmt = con.createStatement();
		rs2 = stmt.executeQuery("SELECT * FROM customers ");
		
		
		Assert.assertEquals(compareResultSets(rs1,rs2),true );
		
	}
	
	
	@AfterClass
	void teardown() throws SQLException
	{
		con.close();
	}
	
	public boolean compareResultSets(ResultSet resultset1 , ResultSet resultset2) 
	{
		try {
			while(resultset1.next())
			{
				resultset2.next();
				int count = resultset1.getMetaData().getColumnCount();
				for(int i = 1; i<=count ;i++)
				{
					if(!StringUtils.equals(resultset1.getString(i), resultset2.getString(i)))
					{
						return false;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	
	
	
	

}
