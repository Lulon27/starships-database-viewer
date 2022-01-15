package dbviewer.form;

public class FormAllCountries extends FormSingleTable
{
	@Override
	public String getQueryString()
	{
		return "SELECT "
			+ "country_code AS \"Code\","
			+ "country_name AS \"Name\" "
			+ "FROM COUNTRY";
	}
}
