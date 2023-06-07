package StoredProcedure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ToVerifyStoredProcedureName {
	Connection cn = null;
	Statement stmt = null;
	ResultSet rs    ;
	
	@BeforeClass
	void setup() throws SQLException
	{
		cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","Taha@MySQL911");
				
	}  
 
	@Test
	void testProcedure() throws SQLException
	{
		stmt = cn.createStatement();
		rs = stmt.executeQuery("SHOW PROCEDURE STATUS WHERE Name ='SelectAllCustomerByCity'");
		rs.next();// it will point it to the current record.
		String actualResult = rs.getString("Name");
		Assert.assertEquals(actualResult, "SelectAllCustomerByCity");
		
	}
	
	
	@AfterClass
	void teardown() throws SQLException
	{
		cn.close();
	}

}
