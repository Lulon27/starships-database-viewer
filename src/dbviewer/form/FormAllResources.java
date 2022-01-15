package dbviewer.form;

public class FormAllResources extends FormSingleTable
{
	@Override
	public String getQueryString()
	{
		return "SELECT resource_name AS \"Resource\" FROM PLANET_RESOURCE";
	}
}
