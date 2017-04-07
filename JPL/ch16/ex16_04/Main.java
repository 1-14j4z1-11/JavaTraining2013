package ex16_04;

import java.lang.annotation.Annotation;

public class Main
{
	public static void main(String[] args)
	{
		Class<?> cls = null;
		
		try
		{
			cls = Class.forName(args[0]);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("ClassNotFound");
		}
		
		Annotation[] annotations = cls.getAnnotations();
		
		for(Annotation a : annotations)
		{
			System.out.println(a.toString());
		}
	}
}
