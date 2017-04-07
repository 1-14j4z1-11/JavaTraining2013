package ex03_11;

public abstract class SortDouble
{
	private boolean sorted;
	private double[] values;
	private final SortMetrics metrics = new SortMetrics();
	
	public final SortMetrics sort(double[] data)
	{
		if(!sorted)
		{
			sorted = true;
			values = data;
			metrics.init();
			doSort();
		}
		
		return getMetrics();
	}

	public final SortMetrics getMetrics()
	{
		return metrics.clone();
	}
	
	protected final int getDataLength()
	{
		return values.length;
	}
	
	protected final double probe(int i)
	{
		metrics.probeCount++;
		return values[i];
	}
	
	protected final int compare(int i, int j)
	{
		metrics.compareCount++;
		double d1 = values[i];
		double d2 = values[j];
		
		if(d1 == d2)
			return 0;
		else
			return ((d1 < d2) ? -1 : 1);
	}
	
	protected final void swap(int i, int j)
	{
		metrics.swapCount++;
		double tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}
	
	protected abstract void doSort();
}
