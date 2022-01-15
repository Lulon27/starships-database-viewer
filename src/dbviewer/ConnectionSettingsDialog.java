package dbviewer;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

public class ConnectionSettingsDialog extends Dialog<DatabaseProfile>
{
	final TextField inputAddress;
	final TextField inputPort;
	final TextField inputUsername;
	final TextField inputPassword;
	
	public ConnectionSettingsDialog()
	{
		this.initModality(Modality.APPLICATION_MODAL);
		this.setTitle("Connection settings");
		this.setHeaderText("Change the connection settings");
		
		GridPane grid = new GridPane();
		grid.setVgap(5);
		grid.setHgap(5);

		this.inputAddress = new TextField(DBViewer.DEFAULT_ADDRESS);
		this.inputPort = new TextField(DatabaseHelper.DEFAULT_PORT);
		this.inputUsername = new TextField();
		this.inputPassword = new PasswordField();
		
		
		this.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
		final Button applyButton = (Button)this.getDialogPane().lookupButton(ButtonType.APPLY);
		
		applyButton.setDefaultButton(true);
		applyButton.disableProperty().bind(
			this.inputAddress.textProperty().isEmpty()
			.or(this.inputPort.textProperty().isEmpty())
			.or(this.inputUsername.textProperty().isEmpty())
			.or(this.inputPassword.textProperty().isEmpty())
			);
		

		grid.add(new Label("Address"), 0, 0);
		grid.add(new Label("Port"), 0, 1);
		grid.add(new Label("Username"), 0, 2);
		grid.add(new Label("Password"), 0, 3);

		grid.add(inputAddress, 1, 0);
		grid.add(inputPort, 1, 1);
		grid.add(inputUsername, 1, 2);
		grid.add(inputPassword, 1, 3);
		
		this.getDialogPane().setContent(grid);
		
		this.setResultConverter(c ->
		{
			if(c == ButtonType.APPLY)
			{
				DatabaseProfile p = new DatabaseProfile();
				p.address = this.inputAddress.getText();
				p.port = this.inputPort.getText();
				p.username = this.inputUsername.getText();
				p.password = this.inputPassword.getText();
				return p;
			}
			return null;
		});
	}
	
	public String getAddress()
	{
		return this.inputAddress.getText();
	}
	
	public String getPort()
	{
		return this.inputPort.getText();
	}
	
	public String getUsername()
	{
		return this.inputUsername.getText();
	}
	
	public String getPassword()
	{
		return this.inputPassword.getText();
	}
}
