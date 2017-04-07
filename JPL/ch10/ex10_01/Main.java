package ex10_01;

public class Main
{
	public static String toJavaString(String str)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < str.length(); i++)
		{
			char ch = str.charAt(i);
			
			if(ch == '\n')
				sb.append("\\n");
			
			else if(ch == '\t')
				sb.append("\\t");
			
			else if(ch == '\b')
				sb.append("\\b");
			
			else if(ch == '\r')
				sb.append("\\r");
			
			else if(ch == '\f')
				sb.append("\f");
			
			else if(ch == '\\')
				sb.append("\\\\");
			
			else if(ch == '\'')
				sb.append("\\\'");
			
			else if(ch == '\"')
				sb.append("\\\"");
			
			else
				sb.append(ch);
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		String str = "\t\"HELLO WORLD\"\n";

		System.out.println(str);
		System.out.println(toJavaString(str));
	}
}
