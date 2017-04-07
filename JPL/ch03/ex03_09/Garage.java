package ex03_09;

public class Garage implements Cloneable
{
	private static final int CAPACITY = 10;
	private Vehicle[] array = new Vehicle[CAPACITY];
	private int count = 0;
	
	public int getCount()
	{
		return this.count;
	}
	
	public boolean add(Vehicle item)
	{
		if(count >= CAPACITY)
		{
			return false;
		}
		
		this.array[count++] = item;
		return true;
	}
	
	public Vehicle getAt(int index)
	{
		if((index < 0) || (index >= this.count))
		{
			throw new IndexOutOfBoundsException();
		}
		
		return this.array[index];
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		
		sb.append("<Count = ").append(this.count).append(">").append(newLine);
		
		for(int i = 0; i < this.count; i++)
		{
			sb.append(this.array[i]).append(newLine);
		}
		
		return sb.toString();
	}
	
	@Override
	public Garage clone()
	{
		Garage g;
		
		try
		{
			g = (Garage)super.clone();
			g.array = new Vehicle[this.count];
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError("Garage");
		}
		
		for(int i = 0; i < g.count; i++)
		{
			g.array[i] = this.array[i].clone();
		}
		
		return g;
	}
}
