package ex24_01;

import java.util.ListResourceBundle;

public class ListGlobalRes extends ListResourceBundle
{
	public static final String HELLO = "hello";
	public static final String GOODBYE = "goodbye";
	
	private static final Object[][] contents = 
	{
		{ ListGlobalRes.HELLO, "Ciao" },
		{ ListGlobalRes.GOODBYE, "Ciao" },
	};
	
	@Override
	protected Object[][] getContents()
	{
		return contents;
	}
}
