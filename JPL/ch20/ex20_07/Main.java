package ex20_07;

import java.io.*;

public class Main
{
	public static void main(String[] args)
	{
		Attr attr1 = new Attr("Color", "Black");
		
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		attr1.output(dos);
		
		byte[] data = baos.toByteArray();
		
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
		Attr attr2 = new Attr(dis);
		
		System.out.println(attr2);
	}
}
