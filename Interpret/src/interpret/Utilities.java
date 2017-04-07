package interpret;

import java.lang.reflect.*;

public final class Utilities
{
	private Utilities() { }

	/**
	 * フィールドの文字列を取得します.
	 * Field.toString()と異なりパッケージ名を除去します.
	 * @param field 対象のフィールド
	 * @return フィールドの文字列
	 */
	public static String getFieldString(Field field)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(Modifier.toString(field.getModifiers()))
			.append((sb.length() != 0) ? " " : "")
			.append(field.getType().getSimpleName()).append(" ")
			.append(field.getName());
		
		return sb.toString();
	}

	/**
	 * メソッドの文字列を取得します.
	 * Method.toString()と異なりパッケージ名を除去します.
	 * @param method　対象のメソッド
	 * @return　メソッドの文字列
	 */
	public static String getMethodString(Method method)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(Modifier.toString(method.getModifiers()))
			.append((sb.length() != 0) ? " " : "")
			.append(method.getReturnType().getSimpleName()).append(" ")
			.append(method.getName()).append("(")
			.append(getArgString(method.getParameterTypes()))
			.append(")")
			.append(getExceptionString(method.getExceptionTypes()));
		
		return sb.toString();
	}

	/**
	 * コンストラクタの文字列を取得します.
	 * Constructor.toString()と異なりパッケージ名を除去します.
	 * @param constructor 対象のコンストラクタ
	 * @return コンストラクタの文字列
	 */
	public static String getConstructorString(Constructor<?> constructor)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(Modifier.toString(constructor.getModifiers()))
			.append((sb.length() != 0) ? " " : "")
			.append(constructor.getDeclaringClass().getSimpleName()).append("(")
			.append(getArgString(constructor.getParameterTypes()))
			.append(")")
			.append(getExceptionString(constructor.getExceptionTypes()));
			
		return sb.toString();
	}

	/**
	 * ラッパークラスの配列から対応するプリミティブ型の配列を生成します.
	 * @param componentType 配列要素の型
	 * @param wrapperArray ラッパークラスの配列
	 * @return プリミティブ型の配列
	 */
	public static Object getPrimitiveArray(Class<?> componentType, Object wrapperArray)
	{
		try
		{
			int length = Array.getLength(wrapperArray);
			
			if((componentType == Boolean.class) || (componentType == boolean.class))
			{
				boolean[] primeArray = new boolean[length];
				
				for(int i = 0; i < length; i++)
				{
					primeArray[i] = (boolean)Array.get(wrapperArray, i);
				}
				
				return primeArray;
			}
			else if((componentType == Byte.class) || (componentType == byte.class))
			{
				byte[] primeArray = new byte[length];

				for(int i = 0; i < length; i++)
				{
					primeArray[i] = (byte)Array.get(wrapperArray, i);
				}
				
				return primeArray;
			}
			else if((componentType == Short.class) || (componentType == short.class))
			{
				short[] primeArray = new short[length];

				for(int i = 0; i < length; i++)
				{
					primeArray[i] = (short)Array.get(wrapperArray, i);
				}
				
				return primeArray;
			}
			else if((componentType == Integer.class) || (componentType == int.class))
			{
				int[] primeArray = new int[length];

				for(int i = 0; i < length; i++)
				{
					primeArray[i] = (int)Array.get(wrapperArray, i);
				}
				
				return primeArray;
			}
			else if((componentType == Long.class) || (componentType == long.class))
			{
				long[] primeArray = new long[length];

				for(int i = 0; i < length; i++)
				{
					primeArray[i] = (long)Array.get(wrapperArray, i);
				}
				
				return primeArray;
			}
			else if((componentType == Float.class) || (componentType == float.class))
			{
				float[] primeArray = new float[length];

				for(int i = 0; i < length; i++)
				{
					primeArray[i] = (float)Array.get(wrapperArray, i);
				}
				
				return primeArray;
			}
			else if((componentType == Double.class) || (componentType == double.class))
			{
				double[] primeArray = new double[length];

				for(int i = 0; i < length; i++)
				{
					primeArray[i] = (double)Array.get(wrapperArray, i);
				}
				
				return primeArray;
			}
			else if((componentType == Character.class) || (componentType == char.class))
			{
				char[] primeArray = new char[length];
	
				for(int i = 0; i < length; i++)
				{
					primeArray[i] = (char)Array.get(wrapperArray, i);
				}
				
				return primeArray;
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
		catch(NullPointerException e)
		{
			throw new NullPointerException();
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException();
		}
	}

	/**
	 * オブジェクトを文字列に変換します.
	 * null, 文字, 文字列, 配列の場合は通常のtoString()とは異なる処理をします.
	 * @param obj 文字列に変換するオブジェクト
	 * @return オブジェクトから変換した文字列
	 */
	public static String getObjectString(Object obj)
	{
		if(obj == null)
		{
			return "null";
		}
		
		Class<?> cls = obj.getClass();
		
		if(cls.isArray())
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append(cls.getComponentType().getSimpleName())
				.append("[").append(Array.getLength(obj)).append("]");
			
			return sb.toString();
		}
		else if((cls == Character.class) || (cls == char.class))
		{
			return "\'" + obj.toString() + "\'";
		}
		else if(cls == String.class)
		{
			return "\"" + obj.toString() + "\"";
		}
		else
		{
			return obj.toString();
		}
	}
	
	private static String getArgString(Class<?>[] argTypes)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < argTypes.length; i++)
		{
			if(i > 0)
			{
				sb.append(", ");
			}
			sb.append(argTypes[i].getSimpleName());
		}

		return sb.toString();
	}
	
	private static String getExceptionString(Class<?>[] exceptions)
	{
		String str = getArgString(exceptions);
		
		if(str.length() == 0)
		{
			return "";
		}
		else
		{
			return " throws " + str;
		}
	}
}
