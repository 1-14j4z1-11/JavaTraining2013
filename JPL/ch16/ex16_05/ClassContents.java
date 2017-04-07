package ex16_05;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ClassContents
{
	public static void main(String[] args)
	{
		try
		{
			Class<?> type = Class.forName("java.lang.String");
			System.out.println(type.getSimpleName());
			
			Method[] methods = getAllMethods(type, true);
			Field[] fields = getAllFields(type, true);
			Constructor<?>[] consts = type.getConstructors();
			
			System.out.println("  <Fields>");
			printMembers(fields);
			System.out.println("  <Methods>");
			printMembers(methods);
			System.out.println("  <Constructors>");
			printMembers(consts);
		}
		catch(ClassNotFoundException e)
		{
			System.err.println(e);
		}
	}
	public static Field[] getAllFields(Class<?> type, boolean notPublic)
	{
		Field[] fields = new Field[0];
		
		if(notPublic)
		{	
			ArrayList<Field> fieldList = new ArrayList<Field>();
			int modFlag = Modifier.PRIVATE | Modifier.FINAL | Modifier.STATIC;
			
			fieldList.addAll(Arrays.asList(type.getDeclaredFields()));
			type = type.getSuperclass();
			
			while(type != null)
			{
				Field[] array = type.getDeclaredFields();
				
				for(int i = 0; i < array.length; i++)
				{
					if((array[i].getModifiers() & modFlag) == 0)
					{
						fieldList.add(array[i]);
					}
				}
				
				type = type.getSuperclass();
			}
			
			fields = fieldList.toArray(fields);
		}
		else
		{
			fields = type.getFields();
		}

		return fields;
	}
	
	public static Method[] getAllMethods(Class<?> type, boolean notPublic)
	{
		Method[] methods = new Method[0];
		
		if(notPublic)
		{	
			ArrayList<Method> methodList = new ArrayList<Method>();
			
			while(type != null)
			{
				Method[] array = type.getDeclaredMethods();
				
				for(int i = 0; i < array.length; i++)
				{
					boolean exists = false;
					
					for(int j = 0; j < methodList.size(); j++)
					{
						if(isSameMethod(array[i], methodList.get(j)))
						{
							exists = true;
						}
					}
					
					if(!exists)
					{
						methodList.add(array[i]);
					}
				}
				
				type = type.getSuperclass();
			}
			
			methods = methodList.toArray(methods);
		}
		else
		{
			methods = type.getMethods();
		}
		
		return methods;
	}

	private static boolean isSameMethod(Method m1, Method m2)
	{
		Class<?>[] args1 = m1.getParameterTypes();
		Class<?>[] args2 = m2.getParameterTypes();
		
		if(args1.length != args2.length)
		{
			return false;
		}
		
		for(int i = 0; i < args1.length; i++)
		{
			if(args1[i] != args2[i])
			{
				return false;
			}
		}
		
		return (m1.getName() == m2.getName()) && (m1.getReturnType() == m2.getReturnType());
	}
	
	private static void printMembers(Object[] array)
	{
		Member[] members;
		
		if(array instanceof Member[])
		{
			members = (Member[])array;
		}
		else
		{
			System.out.println(array.getClass());
			throw new IllegalArgumentException();
		}
		
		Arrays.sort(members, new MemberComparator());
		
		for(Member m : members)
		{
			System.out.println("    " + m);
			
			Annotation[] annotations = null;
			
			if(m instanceof Field)
			{
				annotations = ((Field)m).getAnnotations();
			}
			else if(m instanceof Method)
			{
				annotations = ((Method)m).getAnnotations();
			}
			else if(m instanceof Constructor)
			{
				annotations = ((Constructor<?>)m).getAnnotations();
			}
			else
			{
				continue;
			}
			
			for(Annotation a : annotations)
			{
				System.out.println("      <Annotation> " + a);
			}
		}
	}
	
	private static class MemberComparator implements Comparator<Member>
	{
		@Override
		public int compare(Member m1, Member m2)
		{
			return m1.getName().compareTo(m2.getName());
		}
	}
}
