package dbviewer.form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class FormNewUser extends Form
{
	private static final String CALL = "{CALL S_P_NEW_USER(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	
	@FXML
	private TextField input_first_name;
	
	@FXML
	private TextField input_last_name;
	
	@FXML
	private DatePicker input_date_of_birth;
	
	@FXML
	private TextField input_email;
	
	@FXML
	private TextField input_street1;
	
	@FXML
	private TextField input_street2;
	
	@FXML
	private TextField input_postal_code;
	
	@FXML
	private TextField input_city;
	
	@FXML
	private TextField input_state;
	
	@FXML
	private TextField input_country;
	
	@Override
	public String getFXMLPath()
	{
		return "res/forms/new_user.fxml";
	}

	@Override
	public void executeQuery(Connection conn) throws SQLException
	{
		try(PreparedStatement st = conn.prepareCall(CALL))
		{
			st.setString(1, this.input_first_name.getText());
			st.setString(2, this.input_last_name.getText());
			st.setDate(3, java.sql.Date.valueOf(this.input_date_of_birth.getValue()));
			st.setString(4, this.input_email.getText());
			st.setString(5, this.input_street1.getText());
			st.setString(6, this.input_street2.getText());
			st.setString(7, this.input_postal_code.getText());
			st.setString(8, this.input_city.getText());
			st.setString(9, this.input_state.getText());
			st.setString(10, this.input_country.getText());
			st.execute();
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
}
