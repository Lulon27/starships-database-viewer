package dbviewer.form;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbviewer.DatabaseProfile;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public abstract class Form implements Initializable
{
	private DatabaseProfile dbProfile;
	
	private Parent uiContent;
	
	private String statusText = "Ready";
	
	private FormStatusChangedListener statusChangedListener;
	
	public abstract String getFXMLPath();
	
	public abstract void executeQuery(Connection conn) throws SQLException;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}
	
	final void load() throws IOException
	{
		final FXMLLoader fxmlLoader = new FXMLLoader(Path.of(this.getFXMLPath()).toUri().toURL());
		fxmlLoader.setController(this);
		this.uiContent = fxmlLoader.load();
	}
	
	final void setDBProfile(DatabaseProfile dbProfile)
	{
		this.dbProfile = dbProfile;
	}
	
	protected final DatabaseProfile getDBProfile()
	{
		return this.dbProfile;
	}
	
	public final Parent getUIContent()
	{
		return this.uiContent;
	}
	
	public final String getStatusText()
	{
		return this.statusText;
	}
	
	public final void setStatusText(String newStatus)
	{
		this.statusText = newStatus;
		if(this.statusChangedListener != null)
		{
			this.statusChangedListener.onStatusChanged(newStatus);
		}
	}
	
	public void setStatusChangedListener(FormStatusChangedListener l)
	{
		this.statusChangedListener = l;
	}
}
