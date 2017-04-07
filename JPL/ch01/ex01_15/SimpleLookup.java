package ex01_15;

public class SimpleLookup implements Lookup{
	// フィールド
	
	private int size = 0;
	private String[] names;
	private Object[] values;
	
	// コンストラクタ
	
	public SimpleLookup(int size)
	{
		names = new String[size];
		values = new Object[size];
	}
	
	// アクセサ/インデクサ
	
	public int getSize()
	{
		return size;
	}
	
	public String getNameAt(int index)
	{
		if((index < 0) || (index >= size))
		{
			throw new IndexOutOfBoundsException();
		}
		
		return names[index];
	}
	
	// メソッド
	
	@Override
	public String toString()
	{
		String str = "";
		
		for(int i = 0; i < size; i++)
		{
			str += "[" + names[i] + "," + values[i] + "] ";
		}
		
		return str;
	}
	
	@Override
	public Object find(String name)
	{
		for(int i = 0; i < names.length; i++)
		{
			if(names[i].equals(name))
			{
				return values[i];
			}
		}
		
		return null;
	}

	@Override
	public boolean add(String name, Object value)
	{
		if(size >= names.length) return false;
		
		names[size] = name;
		values[size] = value;
		size++;
		
		return true;
	}

	@Override
	public void remove(String name) 
	{
		for(int i = 0; i < size; i++)
		{
			if(names[i] == name)
			{
				size--;
				
				for(int j = i; j < size; j++)
				{
					names[j] = names[j + 1];
					values[j] = values[j + 1];
				}
				
				names[size] = null;
				values[size] = null;
				
				i--;
			}
		}
	}
}
