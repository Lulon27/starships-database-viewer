package dbviewer;

import java.io.IOException;
import java.nio.file.Path;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DBViewer extends Application
{
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	private static final String MAIN_SCENE_FXML = "res/main_scene.fxml";

	private PreLoadingWindow preloader;
	private Scene mainScene;
	
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
			Platform.exit();
			return;
		}
		this.preloader.show();
		Platform.runLater(() ->
		{
			this.initMainWindow(stage);
			this.loadForms();
			this.preloader.close();
			stage.show();
		});
	}
	
	private void initMainWindow(Stage primaryStage)
	{
		Parent mainSceneRoot;
		try
		{
			mainSceneRoot = FXMLLoader.load(Path.of(MAIN_SCENE_FXML).toUri().toURL());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			Platform.exit();
			return;
		}
		
		this.mainScene = new Scene(mainSceneRoot);
		
		primaryStage.setScene(this.mainScene);
		primaryStage.setTitle("MegaStarshipsGame 2.0 2020 HD-Remaster - Database Viewer");
	}
	
	private void loadForms()
	{
		
	}
}
