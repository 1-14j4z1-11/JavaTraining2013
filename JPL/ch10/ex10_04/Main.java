package ex10_04;

// ���K���10.2��while��
// ������̒�����0�̏ꍇ�̓��[�v���̏������s��Ȃ��̂ŁA�K��1��̓��[�v�������s��do-while�ŏ����͓̂K���Ȃ�

public class Main
{
	public static String toJavaString(String str)
	{
		StringBuilder sb = new StringBuilder();
		int index = -1;
		
		while(++index < str.length())
		{
			switch(str.charAt(index))
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
					sb.append(str.charAt(index));
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
