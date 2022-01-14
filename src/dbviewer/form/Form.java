package dbviewer.form;

import java.io.IOException;
import java.nio.file.Path;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public abstract class Form implements Initializable
{
	private Parent uiContent;
	
	public abstract String getFXMLPath();
	
	final void load() throws IOException
	{
		final FXMLLoader fxmlLoader = new FXMLLoader(Path.of(this.getFXMLPath()).toUri().toURL());
		fxmlLoader.setController(this);
		this.uiContent = fxmlLoader.load();
	}
	
	public final Parent getUIContent()
	{
		return this.uiContent;
	}
}
