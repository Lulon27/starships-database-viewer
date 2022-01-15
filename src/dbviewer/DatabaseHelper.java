package dbviewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper
{
	private static final String URL_PREFIX = "jdbc:oracle:thin:@";
	private static final String URL_SUFFIX = ":orcl";
	public static final String DEFAULT_PORT = "1521";
	
	public static Connection createConnection(DatabaseProfile profile) throws SQLException
	{
		if(profile == null)
		{
			throw new IllegalArgumentException("profile cannot be null");
		}
		
		StringBuilder sb = new StringBuilder(64);
		sb.append(URL_PREFIX);
		sb.append(profile.address);
		sb.append(":");
		sb.append(profile.port);
		sb.append(URL_SUFFIX);
		
		return DriverManager.getConnection(sb.toString(), profile.username, profile.password);
	}
}
