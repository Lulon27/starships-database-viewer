package dbviewer.form;

public class FormAllPeople extends FormSingleTable
{
	@Override
	public String getQueryString()
	{
		return "SELECT "
			+ "first_name AS \"First name\","
			+ "last_name AS \"Last name\", "
			+ "date_of_birth AS \"Date of birth\", "
			+ "email AS \"Email\" "
			+ "FROM PERSON";
	}
}
