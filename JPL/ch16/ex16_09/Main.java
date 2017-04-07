package ex16_09;

import java.lang.annotation.*;
import java.lang.reflect.*;

public class Main
{
	public static void main(String[] args)
	{
		Class<?> cls = java.lang.Thread.class;
		
		// Class
		{
			int mod = cls.getModifiers();
			Annotation[] annotations = cls.getAnnotations();
			Type baseCls = cls.getGenericSuperclass();
			Type[] interfaces = cls.getGenericInterfaces();
			
			for(Annotation a : annotations)
			{
				System.out.println(a.toString());
			}
			
			System.out.print(Modifier.toString(mod));
			System.out.print((mod == 0) ? "" : " ");
			System.out.print("class " + cls.getSimpleName());
			System.out.print((baseCls == Object.class) ? "" : " extends " + baseCls.getClass().getSimpleName());
			System.out.print((interfaces.length == 0) ? "" : " implements");
			
			for(int i = 0; i < interfaces.length; i++)
			{
				System.out.print((i == 0) ? " " : ", ");
				System.out.print(getClassName(interfaces[i]));
			}
		}
		
		System.out.println();
		System.out.println("{");
		
		
		// Field
		{
			Field[] fields = cls.getDeclaredFields();
			
			for(Field f : fields)
			{
				int mod = f.getModifiers();
				Annotation[] annotations = f.getAnnotations();
				
				for(Annotation a : annotations)
				{
					System.out.println("\t" + a.toString());
				}
				
				System.out.print("\t");
				System.out.print(Modifier.toString(mod));
				System.out.print((mod == 0) ? "" : " ");
				System.out.print(f.getType().getSimpleName());
				System.out.print(" ");
				System.out.print(f.getName());
				System.out.println(";");
			}
		}

		System.out.println();
		
		
		// Constructor
		{
			Constructor<?>[] constructors = cls.getDeclaredConstructors();
			
			for(Constructor<?> c : constructors)
			{
				int mod = c.getModifiers();
				Type[] vars = c.getGenericParameterTypes();
				Type[] exps = c.getGenericExceptionTypes();
				Annotation[] annotations = c.getAnnotations();
				
				for(Annotation a : annotations)
				{
					System.out.println("\t" + a.toString());
				}
				
				System.out.print("\t");
				System.out.print(Modifier.toString(mod));
				System.out.print((mod == 0) ? "" : " ");
				System.out.print(cls.getSimpleName());
				System.out.print("(");
				
				for(int i = 0; i < vars.length; i++)
				{
					System.out.print((i == 0) ? "" : ", ");
					System.out.print(getClassName(vars[i]));
				}

				System.out.print(")");
				System.out.print((exps.length == 0) ? "" : " throws");

				for(int i = 0; i < exps.length; i++)
				{
					System.out.print((i == 0) ? " " : ", ");
					System.out.print(getClassName(exps[i]));
				}

				System.out.println("{ }");
			}
		}

		System.out.println();
		
		
		// Method
		{
			Method[] methods = cls.getDeclaredMethods();
			
			for(Method m : methods)
			{
				int mod = m.getModifiers();
				Type[] vars = m.getGenericParameterTypes();
				Type[] exps = m.getGenericExceptionTypes();
				Annotation[] annotations = m.getAnnotations();
				
				for(Annotation a : annotations)
				{
					System.out.println("\t" + a.toString());
				}
				
				System.out.print("\t");
				System.out.print(Modifier.toString(mod));
				System.out.print((mod == 0) ? "" : " ");
				System.out.print(m.getReturnType().getSimpleName());
				System.out.print(" ");
				System.out.print(m.getName());
				System.out.print("(");
				
				for(int i = 0; i < vars.length; i++)
				{
					System.out.print((i == 0) ? "" : ", ");
					System.out.print(getClassName(vars[i]));
				}

				System.out.print(")");
				System.out.print((exps.length == 0) ? "" : " throws");

				for(int i = 0; i < exps.length; i++)
				{
					System.out.print((i == 0) ? " " : ", ");
					System.out.print(getClassName(exps[i]));
				}

				System.out.println("{ }");
			}
		}
		
		System.out.println("}");
	}
	
	private static String getClassName(Type type)
	{
		try
		{
			Class<?> cls = (Class<?>)type;
			return cls.getSimpleName();
		}
		catch(ClassCastException e)
		{
			ParameterizedType pType = (ParameterizedType)type;
			Type[] vars = pType.getActualTypeArguments();
			
			StringBuilder sb = new StringBuilder(((Class<?>)pType.getRawType()).getSimpleName());
			
			for(int j = 0; j < vars.length; j++)
			{
				sb.append((j == 0) ? "<" : ", ");
				sb.append(vars[j].getClass().getSimpleName());
			}

			sb.append(">");
			
			return sb.toString();
		}
	}
}
