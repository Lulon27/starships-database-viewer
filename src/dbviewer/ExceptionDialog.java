package dbviewer;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionDialog extends Alert
{
	public ExceptionDialog(Exception ex)
	{
		super(AlertType.ERROR);
		this.setTitle("Error");
		this.setHeaderText("An error occured");
		this.setContentText(ex.toString());
		
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		
		TextArea textArea = new TextArea(sw.toString());
		textArea.setEditable(false);
		textArea.setWrapText(true);
		
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 1);
		
		this.getDialogPane().setExpandableContent(expContent);
	}
}
