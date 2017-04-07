package ex07_02;

public class Main
{	
	public static void main(String[] args)
	{
		Values values = new Values();
		
		//values.boolVal = 1;				// �s�� - boolean�ւ�int�̑��
		//values.intVal = 0.5F;				// �s�� - int�ւ�float�̑��
		values.charVal = 40;				// �\ - \u0040�Ɠ���
		values.doubleVal = '\u007F';		// �\ - 0x7F�Ɠ���
		values.shortVal = (short)'\uFFFF';	// �L���X�g���K�v
		
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