package dbviewer.form;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FormsHandler
{
	private final List<FormInfo> forms;
	
	public FormsHandler()
	{
		
		this.forms = new ArrayList<>();
	}
	
	public void registerForm(Class<? extends Form> formClass, String displayName)
	{
		final FormInfo form = new FormInfo();
		form.formClass = formClass;
		form.displayName = displayName;
		this.forms.add(form);
	}
	
	public FormInfo get(Class<? extends Form> formClass)
	{
		for(FormInfo fr : this.forms)
		{
			if(fr.formClass == formClass)
			{
				return fr;
			}
		}
		return null;
	}
	
	public FormInfo get(int index)
	{
		return this.forms.get(index);
	}
	
	public int getNumForms()
	{
		return this.forms.size();
	}
	
	public void loadForms()
	{
		for(FormInfo fr : this.forms)
		{
			if(fr.form != null)
			{
				continue;
			}
			try
			{
				fr.form = fr.formClass.getConstructor().newInstance();
				fr.form.load();
			}
			catch(NoSuchMethodException e)
			{
				throw new RuntimeException(fr.formClass.getSimpleName() + " must have a public constructor with no arguments");
			}
			catch(SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				throw new RuntimeException("Failed to create instance of class " + fr.formClass.getSimpleName(), e);
			}
			catch(IOException e)
			{
				throw new RuntimeException("Failed to load UI for " + fr.formClass.getSimpleName(), e);
			}
		}
	}
	
	public static class FormInfo
	{
		private Form form;
		private Class<? extends Form> formClass;
		private String displayName;
		
		private FormInfo() {}
		
		public Form getForm()
		{
			return this.form;
		}
		
		public String getDisplayName()
		{
			return this.displayName;
		}
		
		public Class<? extends Form> getFormClass()
		{
			return this.formClass;
		}
		
		@Override
		public String toString()
		{
			return this.displayName;
		}
	}
}
