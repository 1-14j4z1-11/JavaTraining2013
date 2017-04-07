package ex22_03;

import java.util.*;

public class WhichChars
{
	private HashMap<Byte, BitSet> used = new HashMap<Byte, BitSet>();
	
	public WhichChars(String str)
	{
		for(int i = 0; i < str.length(); i++)
		{
			byte u = (byte)((str.charAt(i) >>> 8) & 0xFF);
			byte d = (byte)(str.charAt(i) & 0xFF);
			
			if(used.containsKey(u))
			{
				used.get(u).set(d);
			}
			else
			{
				BitSet bits = new BitSet();
				bits.set(d);
				used.put(u, bits);
			}
		}
	}
	
	@Override
	public String toString()
	{
		String desc = "[";
		
		for(Byte b : used.keySet())
		{
			char mask = (char)((int)b << 8);
			BitSet bits = used.get(b);
			
			for(int i = bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i + 1))
			{
				desc += (char)(mask | i);
			}
		}
		
		return desc + "]";
	}
}
