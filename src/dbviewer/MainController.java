package dbviewer;

import java.net.URL;
import java.util.ResourceBundle;

import dbviewer.form.FormsHandler;
import dbviewer.form.FormsHandler.FormInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

public class MainController implements Initializable
{
	private final FormsHandler formsHandler;
	
	public MainController(FormsHandler formsHandler)
	{
		this.formsHandler = formsHandler;
	}
	
	@FXML
	private ChoiceBox<FormInfo> formChooser;
	
	@FXML
	private Pane formRoot;
	
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
			this.formRoot.getChildren().add(newVal.getForm().getUIContent());
		});
	}
	
	public void onFormsLoaded()
	{
		this.formChooser.getItems().clear();
		for(int i = 0, n = this.formsHandler.getNumForms(); i < n; ++i)
		{
			this.formChooser.getItems().add(this.formsHandler.get(i));
		}
	}
}
