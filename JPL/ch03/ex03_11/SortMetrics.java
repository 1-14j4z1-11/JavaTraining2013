package ex03_11;

public final class SortMetrics implements Cloneable
{
	public long probeCount;
	public long compareCount;
	public long swapCount;
	
	public void init()
	{
		probeCount = 0;
		compareCount = 0;
		swapCount = 0;
	}
	
	@Override
	public String toString()
	{
		return probeCount + " probes "
			+ compareCount + " compares "
			+ swapCount + " swaps";
	}
	
	@Override
	public SortMetrics clone()
	{
		try
		{
			return (SortMetrics)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new InternalError();
		}
	}
}
