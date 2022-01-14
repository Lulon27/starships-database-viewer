package dbviewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseProfile
{
	private static final String URL_PREFIX = "jdbc:oracle:thin:@";
	private static final String URL_SUFFIX = ":orcl";
	public static final String DEFAULT_PORT = "1521";
	
	private Connection conn;
	
	public void connect(String address, String port, String username, String password) throws SQLException
	{
		if(this.conn != null)
		{
			throw new IllegalStateException("a connection has already been established");
		}
		
		StringBuilder sb = new StringBuilder(64);
		sb.append(URL_PREFIX);
		sb.append(address);
		sb.append(":");
		sb.append(port);
		sb.append(URL_SUFFIX);
		
		this.conn = DriverManager.getConnection(sb.toString(), username, password);
	}
	
	public Connection getConnection()
	{
		if(this.conn == null)
		{
			throw new IllegalStateException("connect() must be called before calling getConnection()");
		}
		return this.conn;
	}
	
	public void closeConnection()
	{
		if(this.conn == null)
		{
			return;
		}
		try
		{
			this.conn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		this.conn = null;
	}
}
