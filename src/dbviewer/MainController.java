package dbviewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dbviewer.form.FormsHandler;
import dbviewer.form.FormsHandler.FormInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainController implements Initializable
{
	private final FormsHandler formsHandler;
	
	@FXML
	private ChoiceBox<FormInfo> formChooser;
	
	@FXML
	private VBox formRoot;
	
	@FXML
	private Label statusLabel;
	
	private DatabaseProfile profile;
	
	private ConnectionSettingsDialog settingsDialog;
	
	public MainController(FormsHandler formsHandler)
	{
		this.formsHandler = formsHandler;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.formChooser.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) ->
		{
			this.formRoot.getChildren().clear();
			if(newVal == null)
			{
				return;
			}
			final Parent uiContent = newVal.getForm().getUIContent();
			VBox.setVgrow(uiContent, Priority.ALWAYS);
			this.formRoot.getChildren().add(uiContent);
			this.setFormStatus(newVal, newVal.getForm().getStatusText());
		});
		
		this.settingsDialog = new ConnectionSettingsDialog();
	}
	
	private void setFormStatus(FormInfo form, String status)
	{
		this.statusLabel.setText("[" + form.getDisplayName() + "] " + status);
	}
	
	public void onClickConnectionSettings()
	{
		Optional<DatabaseProfile> result = this.settingsDialog.showAndWait();
		if(result.isPresent())
		{
			this.profile = result.get();
		}
	}
	
	public void onClickedExecute()
	{
		FormInfo selectedForm = this.formChooser.getSelectionModel().getSelectedItem();
		if(selectedForm == null)
		{
			return;
		}
		
		//If no settings have been set
		if(this.profile == null)
		{
			Optional<DatabaseProfile> result = this.settingsDialog.showAndWait();
			if(result.isPresent())
			{
				this.profile = result.get();
			}
			else
			{
				//If the user quits the dialog
				return;
			}
		}
		
		try(Connection conn = DatabaseHelper.createConnection(this.profile))
		{
			try
			{
				selectedForm.getForm().executeQuery(conn);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				new ExceptionDialog(e).showAndWait();
			}
		}
		catch(SQLException e)
		{
			//Set profile to null so that the settings dialog
			//is shown after failed connection attempt
			this.profile = null;
			e.printStackTrace();
			new ExceptionDialog(e).showAndWait();
		}
	}
	
	public void onFormsLoaded()
	{
		this.formChooser.getItems().clear();
		for(int i = 0, n = this.formsHandler.getNumForms(); i < n; ++i)
		{
			FormInfo fi = this.formsHandler.get(i);
			this.formChooser.getItems().add(fi);
			fi.getForm().setStatusChangedListener(status ->
			{
				this.setFormStatus(fi, status);
			});
		}
	}
}
