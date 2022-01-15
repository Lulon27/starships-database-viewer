package dbviewer.form;

public class FormAllAccountTiers extends FormSingleTable
{
	@Override
	public String getQueryString()
	{
		return "SELECT "
			+ "tier_name AS \"Tier name\","
			+ "ads AS \"Sees ads\", "
			+ "boost AS \"Boost\" "
			+ "FROM ACCOUNT_TIER";
	}
}
