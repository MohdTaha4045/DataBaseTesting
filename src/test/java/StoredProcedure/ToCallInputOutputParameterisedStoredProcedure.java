package StoredProcedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ToCallInputOutputParameterisedStoredProcedure 
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
	cstmt = con.prepareCall("{Call orderByCus(?,?,?,?,?)}");
	cstmt.setInt(1, 141);

	cstmt.registerOutParameter(2, Types.INTEGER);
	cstmt.registerOutParameter(3, Types.INTEGER);
	cstmt.registerOutParameter(4, Types.INTEGER);
	cstmt.registerOutParameter(5, Types.INTEGER);

	cstmt.executeQuery();
	
	int shipped = cstmt.getInt(2);
	int canceled = cstmt.getInt(3);
	int resolved = cstmt.getInt(4);
	int disputed = cstmt.getInt(5);
	

	
	stmt = con.createStatement();
	rs = stmt.executeQuery("select(SELECT count(*) as shipped FROM orders WHERE customerNumber = 141 and status ='Shipped') as Shipped , ( SELECT count(*) as canceled FROM orders WHERE customerNumber = 141 and status ='Canceled') as Canceled,  (SELECT count(*) as resolved FROM orders WHERE customerNumber = 141 and status ='Resolved') as Resolved , (SELECT count(*) as disputed FROM orders WHERE customerNumber = 141 and status ='Disputed') as Disputed");
	rs.next();
	int ex_shipped = rs.getInt("shipped");
	int ex_canceled = rs.getInt("canceled");
	int ex_resolved = rs.getInt("resolved");
	int ex_disputed = rs.getInt("disputed");
	
	if( shipped == ex_shipped && canceled == ex_canceled && resolved == ex_resolved && disputed == ex_disputed )
	{
		Assert.assertTrue(true);
	}
	else
		Assert.assertTrue(false);
	
}


@AfterClass
void teardown() throws SQLException
{
	con.close();
}


}
