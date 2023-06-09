package StoredProcedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ToValidateShippingDays 
{

	Connection con = null;
	Statement stmt = null;
	CallableStatement cstmt;
	ResultSet rs;



@BeforeClass
void setup() throws SQLException
{
	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","Taha@MySQL911");
			
}  

@Test
void testProcedure() throws SQLException
{
	cstmt = con.prepareCall("{Call shipping_days(?,?)}");
	cstmt.setInt(1, 112);
	cstmt.registerOutParameter(2, Types.VARCHAR);
	cstmt.executeQuery();	
	String shippingTime = cstmt.getString(2);

	

	
	stmt = con.createStatement();
	rs = stmt.executeQuery("SELECT country, CASE WHEN country = 'USA' THEN '2-day-shipping' WHEN country =  'Canada' THEN '3-day-shipping' ELSE '5-day-shipping' END AS ShippingTime FROM customers WHERE customerNumber = 112");
	rs.next();
	String exp_shippingTime = rs.getString("ShippingTime");
	
	Assert.assertEquals(shippingTime,exp_shippingTime);
	
	
	
	
}

@AfterClass
void teardown() throws SQLException
{
	con.close();
} 

}
