package dbviewer;

import java.io.IOException;
import java.nio.file.Path;

import dbviewer.form.FormAllAccounts;
import dbviewer.form.FormAllResources;
import dbviewer.form.FormsHandler;
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
	
	public static final String DEFAULT_ADDRESS = "rs03-db-inf-min.ad.fh-bielefeld.de";
	
	private static final String MAIN_SCENE_FXML = "res/main_scene.fxml";
	
	private PreLoadingWindow preloader;
	private Scene mainScene;
	private MainController mainController;
	
	private final FormsHandler formsHandler = new FormsHandler();
	
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
			this.mainController.onFormsLoaded();
			this.preloader.close();
			stage.show();
		});
	}
	
	private void initMainWindow(Stage primaryStage)
	{
		Parent mainSceneRoot;
		this.mainController = new MainController(this.formsHandler);
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(Path.of(MAIN_SCENE_FXML).toUri().toURL());
			fxmlLoader.setController(this.mainController);
			mainSceneRoot = fxmlLoader.load();
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
		try
		{
			this.formsHandler.registerForm(FormAllAccounts.class, "Get all accounts");
			this.formsHandler.registerForm(FormAllResources.class, "Get all resources");
			this.formsHandler.loadForms();
		}
		catch(RuntimeException e)
		{
			e.printStackTrace();
			Platform.exit();
			return;
		}
	}
}
