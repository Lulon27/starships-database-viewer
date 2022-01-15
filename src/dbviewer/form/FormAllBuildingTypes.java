package dbviewer.form;

public class FormAllBuildingTypes extends FormSingleTable
{
	@Override
	public String getQueryString()
	{
		return "SELECT building_type_name AS \"Building Type\" FROM BUILDING_TYPE";
	}
}
