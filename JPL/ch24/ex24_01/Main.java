package ex24_01;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		ResourceBundle list = new ListGlobalRes();
		System.out.println(list.getString(ListGlobalRes.HELLO));
		System.out.println(list.getString(ListGlobalRes.GOODBYE));
		System.out.println();
		
		ResourceBundle res = new GlobalRes();
		
		for(Enumeration<String> e = res.getKeys(); e.hasMoreElements();)
		{
			System.out.println(res.getObject(e.nextElement()));
		}
		System.out.println();
		
		if(args.length > 0)
		{
			try
			{
				ResourceBundle properties = new PropertyResourceBundle(new FileReader(args[0]));
				System.out.println(properties.getString("hello"));
				System.out.println(properties.getString("goodbye"));
				System.out.println();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
