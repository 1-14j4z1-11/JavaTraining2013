package ex13_05;

public class Main
{
	public static String toCommaString(String str, int splitLength, char ch)
	{
		if(splitLength <= 0)
		{
			throw new IllegalArgumentException();
		}
		
		int length = str.length();
		StringBuilder sb = new StringBuilder(length + (length - 1) / 3);

		int start = 0;
		int end = (length % splitLength == 0) ? splitLength : length % splitLength;
		
		for(; end < length; start = end, end += splitLength)
		{
			sb.append(str, start, end).append(ch);
		}
		
		if(end <= length)
		{
			sb.append(str, start, end);
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		System.out.println(toCommaString("", 3, ','));
		System.out.println(toCommaString("123", 3, ','));
		System.out.println(toCommaString("12345", 3, ','));
		System.out.println(toCommaString("123456", 3, ','));
		System.out.println(toCommaString("12345678910", 3, ','));
	}
}
