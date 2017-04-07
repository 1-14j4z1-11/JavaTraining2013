package ex22_15;

import java.lang.reflect.*;
import java.util.*;
import java.util.regex.*;

public class Main
{
	public static void main(String[] args)
	{
		String text = "3.5 4.3 + max(1.0,2.0) sqrt(4.0) + *";
		
		Pattern pat = Pattern.compile("\\s*([^\\s]+)\\s*");
		Matcher matcher = pat.matcher(text);
		
		ArrayList<Double> stack = new ArrayList<>();
		
		while(matcher.find())
		{
			String word = matcher.group(1);
			
			if(word.matches("[\\+\\-\\*\\/\\%]"))
			{
				if(stack.size() < 2)
				{
					System.out.println("OperandError");
					stack.clear();
					break;
				}

				double b = stack.remove(0);
				double a = stack.remove(0);
				
				switch(word)
				{
					case "+":
						stack.add(0, a + b);
						break;

					case "-":
						stack.add(0, a - b);
						break;

					case "*":
						stack.add(0, a * b);
						break;

					case "/":
						stack.add(0, a / b);
						break;

					case "%":
						stack.add(0, a % b);
						break;
						
					default:
						System.out.println("InvalidOperator : " + word);
						stack.clear();
						break;
				}
			}
			else
			{
				Matcher matcher1 = Pattern.compile("([0-9a-zA-Z]+)\\(([\\-\\.0-9]+),([\\-\\.0-9]+)\\)").matcher(word);
				Matcher matcher2 = Pattern.compile("([0-9a-zA-Z]+)\\(([\\-\\.0-9]+)\\)").matcher(word);
				Matcher matcher3 = Pattern.compile("([\\-\\.0-9]+)").matcher(word);

				double value = 0;
				
				if(matcher1.find())
				{
					try
					{
						double a = Double.parseDouble(matcher1.group(2));
						double b = Double.parseDouble(matcher1.group(3));
						
						Method method = Math.class.getMethod(matcher1.group(1), new Class<?>[]{ double.class, double.class });
						value = (double)method.invoke(null, new Object[]{ a, b });
					}
					catch(SecurityException | ReflectiveOperationException e)
					{
						e.printStackTrace();
						System.out.println("InvalidMethod : " + word);
						break;
					}
				}
				else if(matcher2.find())
				{
					try
					{
						double a = Double.parseDouble(matcher2.group(2));
						
						Method method = Math.class.getMethod(matcher2.group(1), new Class<?>[]{ double.class });
						value = (double)method.invoke(null, new Object[]{ a });
					}
					catch(SecurityException | ReflectiveOperationException e)
					{
						e.printStackTrace();
						System.out.println("InvalidMethod : " + word);
						break;
					}
				}
				else if(matcher3.find())
				{
					try
					{
						value = Double.parseDouble(matcher3.group(1));
					}
					catch(SecurityException e)
					{
						System.out.println("InvalidValue : " + word);
						break;
					}
				}
				else
				{
					System.out.println("InvalidValue : " + word);
					break;
				}
				
				stack.add(0, value);
			}
		}
		
		if(stack.size() == 1)
		{
			System.out.println("value = " + stack.get(0));
		}
	}
}
