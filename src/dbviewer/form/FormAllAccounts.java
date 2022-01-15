package dbviewer.form;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FormAllAccounts extends Form
{
	@Override
	public String getFXMLPath()
	{
		return "res/forms/all_accounts.fxml";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}

	@Override
	public void executeQuery(Connection conn) throws SQLException
	{
	}
}
