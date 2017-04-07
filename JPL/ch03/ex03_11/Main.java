package ex03_11;

public class Main
{
	static double[] testData = { 0.3, 1.3e-2, 7.9, 3.17 };
	
	public static void main(String[] args)
	{
		SortDouble sortDouble = new SimpleSortDouble();
		SortMetrics metrics = sortDouble.sort(testData);
		
		System.out.println("Metrics: " + metrics);
		
		for(int i = 0; i < testData.length; i++)
		{
			System.out.println("\t" + testData[i]);
		}
	}
}

class SimpleSortDouble extends SortDouble
{
	boolean sorted;
	
	@Override
	protected void doSort()
	{
		if(sorted)
			return;
		
		sorted = true;
		
		for(int i = 0; i < getDataLength(); i++)
		{
			for(int j = i + 1; j < getDataLength(); j++)
			{
				if(compare(i, j) > 0)
					swap(i, j);
			}
		}
		
		// 再度別のデータのソートを行う >> Metricsを上書き
		super.sort(new double[] { 0.0 });
	}
}