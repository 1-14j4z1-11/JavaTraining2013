package interpret;

import java.lang.reflect.*;

public final class Utilities
{
	private Utilities() { }

	/**
	 * �t�B�[���h�̕�������擾���܂�.
	 * Field.toString()�ƈقȂ�p�b�P�[�W�����������܂�.
	 * @param field �Ώۂ̃t�B�[���h
	 * @return �t�B�[���h�̕�����
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
	 * ���\�b�h�̕�������擾���܂�.
	 * Method.toString()�ƈقȂ�p�b�P�[�W�����������܂�.
	 * @param method�@�Ώۂ̃��\�b�h
	 * @return�@���\�b�h�̕�����
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
	 * �R���X�g���N�^�̕�������擾���܂�.
	 * Constructor.toString()�ƈقȂ�p�b�P�[�W�����������܂�.
	 * @param constructor �Ώۂ̃R���X�g���N�^
	 * @return �R���X�g���N�^�̕�����
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
	 * ���b�p�[�N���X�̔z�񂩂�Ή�����v���~�e�B�u�^�̔z��𐶐����܂�.
	 * @param componentType �z��v�f�̌^
	 * @param wrapperArray ���b�p�[�N���X�̔z��
	 * @return �v���~�e�B�u�^�̔z��
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
	 * �I�u�W�F�N�g�𕶎���ɕϊ����܂�.
	 * null, ����, ������, �z��̏ꍇ�͒ʏ��toString()�Ƃ͈قȂ鏈�������܂�.
	 * @param obj ������ɕϊ�����I�u�W�F�N�g
	 * @return �I�u�W�F�N�g����ϊ�����������
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
