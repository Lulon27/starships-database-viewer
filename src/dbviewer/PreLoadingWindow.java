package dbviewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PreLoadingWindow
{
	private static final String PRELOADING_IMAGE = "res/logo.png";
	
	private final Stage stage;
	private final ImageView imageView;
	private final Group rootGroup;
	private final Scene scene;
	
	public PreLoadingWindow() throws IOException
	{
		this.stage = new Stage(StageStyle.UNDECORATED);
		
		this.imageView = new ImageView();
		this.rootGroup = new Group(imageView);
		this.scene = new Scene(this.rootGroup);
		this.stage.setScene(this.scene);
		
		this.loadImage();
	}
	
	public void show()
	{
		this.stage.show();
	}
	
	public void close()
	{
		this.stage.close();
	}
	
	private void loadImage() throws IOException
	{
		try(InputStream imgInput = new FileInputStream(new File(PRELOADING_IMAGE)))
		{
			this.imageView.setImage(new Image(imgInput));
		}
		catch(IOException e)
		{
			throw e;
		}
	}
}
