package ex24_01;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GlobalRes extends ResourceBundle
{
	private static final String[] KEYS = new String[]
	{
		"hello",
		"goodbye",
	};
	
	private final static HashMap<String, String> map = new HashMap<>();
	
	static
	{
		map.put(KEYS[0], "Bonjour");
		map.put(KEYS[1], "Au revoir");
	}
	
	@Override
	public Enumeration<String> getKeys()
	{
		return new Enumerator();
	}

	@Override
	protected Object handleGetObject(String key)
	{
		return map.get(key);
	}
	
	private static class Enumerator implements Enumeration<String>
	{
		private int index = 0;
		
		@Override
		public boolean hasMoreElements()
		{
			return (index < KEYS.length);
		}

		@Override
		public String nextElement()
		{
			if(!this.hasMoreElements())
			{
				throw new IndexOutOfBoundsException();
			}
			
			return KEYS[this.index++];
		}
	}
}
