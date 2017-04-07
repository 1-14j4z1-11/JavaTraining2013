package ex07_02;

public class Main
{	
	public static void main(String[] args)
	{
		Values values = new Values();
		
		//values.boolVal = 1;				// 不可 - booleanへのintの代入
		//values.intVal = 0.5F;				// 不可 - intへのfloatの代入
		values.charVal = 40;				// 可能 - \u0040と同じ
		values.doubleVal = '\u007F';		// 可能 - 0x7Fと同じ
		values.shortVal = (short)'\uFFFF';	// キャストが必要
		
		System.out.println(values.charVal);
		System.out.println(values.doubleVal);
		System.out.println(values.shortVal);
	}
}

class Values
{
	public boolean boolVal;
	public short shortVal;
	public int intVal;
	public float floatVal;
	public double doubleVal;
	public char charVal;
}