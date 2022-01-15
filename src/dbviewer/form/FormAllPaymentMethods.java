package dbviewer.form;

public class FormAllPaymentMethods extends FormSingleTable
{
	@Override
	public String getQueryString()
	{
		return "SELECT payment_method_name AS \"Payment Method\" FROM PAYMENT_METHOD";
	}
}
