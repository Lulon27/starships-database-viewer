package dbviewer.form;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dbviewer.DatabaseHelper;
import dbviewer.DatabaseHelper.StringArrayTableRow;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public abstract class FormSingleTable extends Form
{
	@FXML
	private TableView<StringArrayTableRow> entries;
	
	@Override
	public final String getFXMLPath()
	{
		return "res/forms/single_table.fxml";
	}

	@Override
	public final void executeQuery(Connection conn) throws SQLException
	{
		try(Statement st = conn.createStatement())
		{
			if(st.execute(this.getQueryString()))
			{
				DatabaseHelper.fillFXTable(this.entries, st.getResultSet());
				this.setStatusText("Execution successful");
			}
			else
			{
				this.setStatusText("No results");
			}
		}
		catch(SQLException e)
		{
			this.setStatusText("Execution failed" + e);
			throw e;
		}
	}
	
	public abstract String getQueryString();
}
