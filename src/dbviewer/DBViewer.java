package dbviewer;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class DBViewer extends Application
{
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	private PreLoadingWindow preloader;
	
	@Override
	public void start(Stage stage)
	{
		try
		{
			this.preloader = new PreLoadingWindow();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		stage.setTitle("MegaStarshipsGame 2.0 2020 HD-Remaster - Database Viewer");
		stage.show();
		stage.close();
		
		this.preloader.show();
	}
}
