package interpret;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ObjectManager
{
	private static final int DEF_ARRAYLIST_SIZE = 64;
	
	private ArrayList<Object> objects = new ArrayList<Object>(DEF_ARRAYLIST_SIZE);
	
	public final int getObjectCount()
	{
		return this.objects.size();
	}

	public final Object getObjectAt(int index)
	{
		if((index < 0) || (index >= this.objects.size()))
		{
			throw new IndexOutOfBoundsException();
		}
		
		return objects.get(index);
	}
	
	public final boolean isArrayAt(int index)
	{
		if((index < 0) || (index >= this.objects.size()))
		{
			throw new IndexOutOfBoundsException();
		}
		
		return this.objects.get(index).getClass().isArray();
	}
	
	public ReturnValue createObject(Constructor<?> constructor, Object[] args)
		throws IllegalArgumentException, InstantiationException
	{
		boolean defaultAccessible = true;
		
		try
		{
			if(!constructor.isAccessible())
			{
				defaultAccessible = false;
				constructor.setAccessible(true);
			}
			
			this.objects.add(constructor.newInstance(args));
		}
		catch(InvocationTargetException e)
		{
			return new ReturnValue(null, e.getTargetException());
		}
		catch (IllegalAccessException e)
		{
			throw new InternalError();
		}
		finally
		{
			if(!defaultAccessible)
			{
				constructor.setAccessible(false);
			}
		}
		
		return new ReturnValue(null, null);
	}
	
	public void addObject(Object object)
	{
		this.objects.add(object);
	}
	
	public void createArray(Class<?> arrayType, int length)
	{
		try
		{
			this.objects.add(Array.newInstance(arrayType, length));
		}
		catch(NegativeArraySizeException e)
		{
			throw new IllegalArgumentException();
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

		Arrays.sort(fields, new FieldComparator());
		
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
		
		Arrays.sort(methods, new MethodComparator());
		
		return methods;
	}
	
	public static Constructor<?>[] getAllConstructors(Class<?> type, boolean notPublic)
	{
		Constructor<?>[] constructors;
		
		if(notPublic)
		{
			constructors = type.getDeclaredConstructors();
		}
		else
		{
			constructors = type.getConstructors();
		}
		
		Arrays.sort(constructors, new ConstructorComparator());
		
		return constructors;
	}
	
	public static Object getFieldValue(Object target, Field field)
		throws IllegalArgumentException
	{
		boolean defaultAccessible = true;
		
		try
		{
			if(!field.isAccessible())
			{
				defaultAccessible = false;
				field.setAccessible(true);
			}
		
			return field.get(target);
		}
		catch (IllegalAccessException e)
		{
			throw new InternalError();
		}
		finally
		{
			if(!defaultAccessible)
			{
				field.setAccessible(false);
			}
		}
	}
	
	public static void changeFieldValue(Object target, Field field, Object value)
		throws IllegalArgumentException, IllegalAccessException
	{
		boolean defaultAccessible = true;
		
		try
		{
			if(!field.isAccessible())
			{
				defaultAccessible = false;
				field.setAccessible(true);
			}
		
			field.set(target, value);
		}
		finally
		{
			if(!defaultAccessible)
			{
				field.setAccessible(false);
			}
		}
	}
	
	public static ReturnValue invokeMethod(Object target, Method method, Object[] args)
	{
		boolean defaultAccessible = true;
		
		try
		{
			if(!method.isAccessible())
			{
				defaultAccessible = false;
				method.setAccessible(true);
			}
			
			return new ReturnValue(method.invoke(target, args), null);
		}
		catch (IllegalAccessException e)
		{
			throw new InternalError();
		}
		catch (InvocationTargetException e)
		{
			return new ReturnValue(null, e.getTargetException());
		}
		finally
		{
			if(!defaultAccessible)
			{
				method.setAccessible(false);
			}
		}
	}
	
	public static Object[] getArrayObjects(Object array)
		throws IllegalArgumentException
	{
		int length = Array.getLength(array);
		Object[] objects = new Object[length];
		
		for(int i = 0; i < length; i++)
		{
			objects[i] = Array.get(array, i);
		}
		
		return objects;
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
	
	private static class FieldComparator implements Comparator<Field>
	{
		@Override
		public int compare(Field f1, Field f2)
		{
			return f1.getName().compareTo(f2.getName());
		}
	}
	
	private static class MethodComparator implements Comparator<Method>
	{
		@Override
		public int compare(Method m1, Method m2)
		{
			int comp = m1.getName().compareTo(m2.getName());
			
			if(comp == 0)
			{
				return m1.getParameterTypes().length - m2.getParameterTypes().length;
			}
			else
			{
				return comp;
			}
		}
	}
	
	private static class ConstructorComparator implements Comparator<Constructor<?>>
	{
		@Override
		public int compare(Constructor<?> c1, Constructor<?> c2)
		{
			return c1.getParameterTypes().length - c2.getParameterTypes().length;
		}
	}
}
