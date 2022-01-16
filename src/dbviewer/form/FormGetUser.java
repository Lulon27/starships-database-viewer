package dbviewer.form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class FormGetUser extends Form
{
	@FXML
	private RadioButton switch_email;
	
	@FXML
	private RadioButton switch_id;
	
	@FXML
	private TextField input_email;
	
	@FXML
	private TextField input_id;
	
	@FXML
	private Label label_no_account;
	
	@FXML
	private Pane account_pane;
	
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
	private TextField output_registration_date;
	
	@FXML
	private TextField output_tier;
	
	@FXML
	private TextField output_currency;
	
	@FXML
	private TextArea output_user_note;
	
	@FXML
	private ListView<String> output_players;
	
	private static final String QUERY_EMAIL = "SELECT\r\n"
		+ "PERSON.first_name AS \"First name\",\r\n"
		+ "PERSON.last_name AS \"Last name\",\r\n"
		+ "PERSON.date_of_birth AS \"Date of birth\",\r\n"
		+ "PERSON.email AS \"Email\",\r\n"
		+ "ADDRESS.street1 AS \"Street and house number\",\r\n"
		+ "ADDRESS.street2 AS \"Street (details)\",\r\n"
		+ "LOCATION.postal_code AS \"Postal code\",\r\n"
		+ "LOCATION.place_name AS \"City\",\r\n"
		+ "LOCATION.admin_name1 AS \"State\",\r\n"
		+ "COUNTRY.country_name AS \"Country\",\r\n"
		+ "ACCOUNT.account_id AS \"Account ID\",\r\n"
		+ "ACCOUNT.registration_date AS \"Registration date\",\r\n"
		+ "ACCOUNT.tier_name AS \"Tier\",\r\n"
		+ "ACCOUNT.account_currency AS \"Currency\",\r\n"
		+ "ACCOUNT.user_note AS \"User note\"\r\n"
		+ "FROM PERSON\r\n"
		+ "LEFT JOIN ADDRESS ON PERSON.address_id = ADDRESS.address_id\r\n"
		+ "LEFT JOIN LOCATION ON ADDRESS.location_id = LOCATION.location_id\r\n"
		+ "LEFT JOIN COUNTRY ON LOCATION.country_code = COUNTRY.country_code\r\n"
		+ "LEFT JOIN ACCOUNT ON PERSON.person_id = ACCOUNT.person_id\r\n"
		+ "WHERE PERSON.email = ?";
	
	private static final String QUERY_ID = "SELECT\r\n"
		+ "PERSON.first_name AS \"First name\",\r\n"
		+ "PERSON.last_name AS \"Last name\",\r\n"
		+ "PERSON.date_of_birth AS \"Date of birth\",\r\n"
		+ "PERSON.email AS \"Email\",\r\n"
		+ "ADDRESS.street1 AS \"Street and house number\",\r\n"
		+ "ADDRESS.street2 AS \"Street (details)\",\r\n"
		+ "LOCATION.postal_code AS \"Postal code\",\r\n"
		+ "LOCATION.place_name AS \"City\",\r\n"
		+ "LOCATION.admin_name1 AS \"State\",\r\n"
		+ "COUNTRY.country_name AS \"Country\",\r\n"
		+ "ACCOUNT.account_id AS \"Account ID\",\r\n"
		+ "ACCOUNT.registration_date AS \"Registration date\",\r\n"
		+ "ACCOUNT.tier_name AS \"Tier\",\r\n"
		+ "ACCOUNT.account_currency AS \"Currency\",\r\n"
		+ "ACCOUNT.user_note AS \"User note\"\r\n"
		+ "FROM PERSON\r\n"
		+ "LEFT JOIN ADDRESS ON PERSON.address_id = ADDRESS.address_id\r\n"
		+ "LEFT JOIN LOCATION ON ADDRESS.location_id = LOCATION.location_id\r\n"
		+ "LEFT JOIN COUNTRY ON LOCATION.country_code = COUNTRY.country_code\r\n"
		+ "LEFT JOIN ACCOUNT ON PERSON.person_id = ACCOUNT.person_id\r\n"
		+ "WHERE PERSON.person_id = ?";
	
	private static final String QUERY_PLAYERS = "SELECT\r\n"
		+ "PLAYER.display_name FROM PLAYER\r\n"
		+ "JOIN ACCOUNT\r\n"
		+ "ON PLAYER.account_id = ACCOUNT.account_id\r\n"
		+ "WHERE ACCOUNT.account_id = ?";
	
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
			this.setStatusText("Please select email or ID");
			return;
		}
		
		if(param.isBlank())
		{
			this.setStatusText("Please enter valid data");
			return;
		}
		
		try(PreparedStatement st = conn.prepareStatement(queryString))
		{
			st.setString(1, param);
			final ResultSet rs = st.executeQuery();
			if(!rs.next())
			{
				this.setStatusText("No results");
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
			
			if(rs.getObject(11) == null) //Account ID
			{
				this.label_no_account.setVisible(true);
				this.account_pane.setVisible(false);
				return;
			}
			
			this.label_no_account.setVisible(false);
			this.account_pane.setVisible(true);

			this.output_registration_date.setText(rs.getString(12));
			this.output_tier.setText(rs.getString(13));
			this.output_currency.setText(rs.getString(14));
			this.output_user_note.setText(rs.getString(15));
			this.output_players.getItems().clear();
			
			this.fillPlayers(conn, rs.getInt(11));
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
	
	private void fillPlayers(Connection conn, int accountID) throws SQLException
	{
		try(PreparedStatement st = conn.prepareStatement(QUERY_PLAYERS))
		{
			st.setInt(1, accountID);
			final ResultSet rs = st.executeQuery();
			while(rs.next())
			{
				this.output_players.getItems().add(rs.getString(1));
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
	}
}
