package dbviewer.form;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class FormAllResources extends Form
{
	@Override
	public String getFXMLPath()
	{
		return "res/forms/all_resources.fxml";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}

	@Override
	public void executeQuery(Connection conn) throws SQLException
	{
		//Example
		try(Statement st = conn.createStatement())
		{
			if(st.execute("SELECT * FROM PLANET_RESOURCE"))
			{
				ResultSet rs = st.getResultSet();
				while(rs.next())
				{
					System.out.println(rs.getString(1));
				}
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
}
