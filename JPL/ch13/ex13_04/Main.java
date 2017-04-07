package ex13_04;

import java.util.ArrayList;

public class Main
{
	public static void main(String[] args)
	{
		String str = "Boolean false\nCharacter A\nDouble 3.1416\nInteger 65536\nByte -127\n";
		ArrayList<Object> values = new ArrayList<Object>();
		
		String[] words = str.split("[ \\n]");
		
		if(words.length % 2 != 0)
		{
			return;
		}
		
		for(int i = 0; i < words.length / 2; i++)
		{
			String typeStr = words[i * 2];
			String valueStr = words[i * 2 + 1];
			
			try
			{
				switch(typeStr)
				{
					case "Boolean":
						values.add(Boolean.parseBoolean(valueStr));
						break;
						
					case "Character":
						values.add(valueStr.charAt(0));
						break;
	
					case "Byte":
						values.add(Byte.parseByte(valueStr));
						break;
	
					case "Short":
						values.add(Short.parseShort(valueStr));
						break;
						
					case "Integer":
						values.add(Integer.parseInt(valueStr));
						break;
	
					case "Float":
						values.add(Float.parseFloat(valueStr));
						break;
	
					case "Double":
						values.add(Double.parseDouble(valueStr));
						break;
						
					default:
						System.out.println("NotSupportedClass : " + typeStr);
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println("IllegalValue : " + valueStr + " (Class : " + typeStr + ")");
			}
		}
		
		for(int i = 0; i < values.size(); i++)
		{
			System.out.println(values.get(i));
		}
	}
}
