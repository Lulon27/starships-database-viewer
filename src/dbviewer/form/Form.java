package dbviewer.form;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import dbviewer.DatabaseProfile;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public abstract class Form implements Initializable
{
	private DatabaseProfile dbProfile;
	
	private Parent uiContent;
	
	public abstract String getFXMLPath();
	
	public abstract void executeQuery(Connection conn) throws SQLException;
	
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
}
