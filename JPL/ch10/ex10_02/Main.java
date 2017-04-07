package ex10_02;

public class Main
{
	public static String toJavaString(String str)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < str.length(); i++)
		{
			switch(str.charAt(i))
			{
				case '\n':
					sb.append("\\n");
					break;
				
				case '\t':
					sb.append("\\t");
					break;
				
				case '\b':
					sb.append("\\b");
					break;
				
				case '\r':
					sb.append("\\r");
					break;
				
				case '\f':
					sb.append("\f");
					break;
				
				case '\\':
					sb.append("\\\\");
					break;
				
				case '\'':
					sb.append("\\\'");
					break;
				
				case '\"':
					sb.append("\\\"");
					break;
				
				default:
					sb.append(str.charAt(i));
					break;
			}
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
