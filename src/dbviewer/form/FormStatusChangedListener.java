package dbviewer.form;

@FunctionalInterface
public interface FormStatusChangedListener
{
	public void onStatusChanged(String newStatus);
}
