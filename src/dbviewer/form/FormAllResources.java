package dbviewer.form;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import dbviewer.DatabaseHelper;
import dbviewer.DatabaseHelper.StringArrayTableRow;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class FormAllResources extends Form
{
	@FXML
	private TableView<StringArrayTableRow> entries;
	
	@Override
	public String getFXMLPath()
	{
		return "res/forms/all_resources.fxml";
	}

	@Override
	public void executeQuery(Connection conn) throws SQLException
	{
		try(Statement st = conn.createStatement())
		{
			if(st.execute("SELECT resource_name AS \"Resource\" FROM PLANET_RESOURCE"))
			{
				DatabaseHelper.fillFXTable(this.entries, st.getResultSet());
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
}
