package dbviewer.form;

public class FormAllOffers extends FormSingleTable
{
	@Override
	public String getQueryString()
	{
		return "SELECT "
			+ "offer_name AS \"Offer\","
			+ "offer_description AS \"Description\", "
			+ "price AS \"Price\", "
			+ "offer_expire AS \"Expires on\", "
			+ "purchase_count AS \"Purchase count\" "
			+ "FROM OFFER";
	}
}
