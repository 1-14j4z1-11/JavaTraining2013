package ex04_02;

public abstract class SortHarness<T extends Comparable<T>>
	implements ISortHarness<T>
{
	private boolean sorted;
	private T[] values;
	private final SortMetrics metrics = new SortMetrics();
	
	public final SortMetrics sort(T[] data)
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
	
	protected final Object probe(int i)
	{
		metrics.probeCount++;
		return values[i];
	}
	
	protected final int compare(int i, int j)
	{
		metrics.compareCount++;
		
		return values[i].compareTo(values[j]);
	}
	
	protected final void swap(int i, int j)
	{
		metrics.swapCount++;
		T tmp = values[i];
		values[i] = values[j];
		values[j] = tmp;
	}
	
	protected abstract void doSort();
}
