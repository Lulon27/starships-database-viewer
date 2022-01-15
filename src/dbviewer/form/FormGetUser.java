package dbviewer.form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class FormGetUser extends Form
{
	@FXML
	private TextField output_first_name;
	
	@FXML
	private TextField output_last_name;
	
	@FXML
	private TextField output_date_of_birth;
	
	@FXML
	private TextField output_email;
	
	@FXML
	private TextField output_street1;
	
	@FXML
	private TextField output_street2;
	
	@FXML
	private TextField output_postal_code;
	
	@FXML
	private TextField output_city;
	
	@FXML
	private TextField output_state;
	
	@FXML
	private TextField output_country;
	
	@FXML
	private TextField input_email;
	
	@FXML
	private TextField input_id;
	
	@FXML
	private RadioButton switch_email;
	
	@FXML
	private RadioButton switch_id;
	
	private static final String QUERY_EMAIL = "SELECT\r\n"
		+ "PERSON.first_name AS \"First name\",\r\n"
		+ "PERSON.last_name AS \"Last name\",\r\n"
		+ "PERSON.date_of_birth AS \"Date of birth\",\r\n"
		+ "PERSON.email AS \"Email\",\r\n"
		+ "ADDRESS.street1 AS \"Street and house number\",\r\n"
		+ "ADDRESS.street2 AS \"Street (Details)\",\r\n"
		+ "LOCATION.postal_code AS \"Postal code\",\r\n"
		+ "LOCATION.place_name AS \"City\",\r\n"
		+ "LOCATION.admin_name1 AS \"State\",\r\n"
		+ "COUNTRY.country_name AS \"Country\"\r\n"
		+ "FROM PERSON\r\n"
		+ "LEFT JOIN ADDRESS ON PERSON.address_id = ADDRESS.address_id\r\n"
		+ "LEFT JOIN LOCATION ON ADDRESS.location_id = LOCATION.location_id\r\n"
		+ "LEFT JOIN COUNTRY ON LOCATION.country_code = COUNTRY.country_code\r\n"
		+ "WHERE PERSON.email = ?";
	
	private static final String QUERY_ID = "SELECT\r\n"
		+ "PERSON.first_name AS \"First name\",\r\n"
		+ "PERSON.last_name AS \"Last name\",\r\n"
		+ "PERSON.date_of_birth AS \"Date of birth\",\r\n"
		+ "PERSON.email AS \"Email\",\r\n"
		+ "ADDRESS.street1 AS \"Street and house number\",\r\n"
		+ "ADDRESS.street2 AS \"Street (Details)\",\r\n"
		+ "LOCATION.postal_code AS \"Postal code\",\r\n"
		+ "LOCATION.place_name AS \"City\",\r\n"
		+ "LOCATION.admin_name1 AS \"State\",\r\n"
		+ "COUNTRY.country_name AS \"Country\"\r\n"
		+ "FROM PERSON\r\n"
		+ "LEFT JOIN ADDRESS ON PERSON.address_id = ADDRESS.address_id\r\n"
		+ "LEFT JOIN LOCATION ON ADDRESS.location_id = LOCATION.location_id\r\n"
		+ "LEFT JOIN COUNTRY ON LOCATION.country_code = COUNTRY.country_code\r\n"
		+ "WHERE PERSON.person_id = ?";
	
	@Override
	public String getFXMLPath()
	{
		return "res/forms/user_info.fxml";
	}
	
	@Override
	public void executeQuery(Connection conn) throws SQLException
	{
		String queryString;
		String param;
		if(this.switch_email.isSelected())
		{
			queryString = QUERY_EMAIL;
			param = this.input_email.getText();
		}
		else if(this.switch_id.isSelected())
		{
			queryString = QUERY_ID;
			param = this.input_id.getText();
		}
		else
		{
			return;
		}
		
		if(param.isBlank())
		{
			return;
		}
		
		try(PreparedStatement st = conn.prepareStatement(queryString))
		{
			st.setString(1, param);
			final ResultSet rs = st.executeQuery();
			if(!rs.next())
			{
				return;
			}
			this.output_first_name.setText(rs.getString(1));
			this.output_last_name.setText(rs.getString(2));
			this.output_date_of_birth.setText(rs.getString(3));
			this.output_email.setText(rs.getString(4));
			this.output_street1.setText(rs.getString(5));
			this.output_street2.setText(rs.getString(6));
			this.output_postal_code.setText(rs.getString(7));
			this.output_city.setText(rs.getString(8));
			this.output_state.setText(rs.getString(9));
			this.output_country.setText(rs.getString(10));
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
}
