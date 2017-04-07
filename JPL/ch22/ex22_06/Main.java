package ex22_06;

import java.util.Random;

public class Main
{
	private static final int UNIT_RANGE = 10;		// 分布1.0当りの分解能
	private static final int RANGE = 4;				// 分布の範囲(-RANGE ~ RANGEまで表示)
	private static final int UNIT_VALUE = 1000;		// ヒストグラムの*1個の値
	private static final int TRY_COUNT = 100000;	// 乱数発生回数
	private static final int ARRAY_SIZE = (int)(RANGE * UNIT_RANGE * 2);
	
	private static int value2Index(double value)
	{
		int index = (int)(value * UNIT_RANGE + RANGE * UNIT_RANGE);
		
		index = (index < 0) ? 0 : ((index >= ARRAY_SIZE) ? ARRAY_SIZE - 1 : index);
		
		return index;
	}

	public static void printHist(double[] array)
	{
		for(int i = 0; i < array.length; i++)
		{
			char c = (i == 0 || i == array.length - 1) ? '~' : ' ';
			
			System.out.printf(" %c%6.3f | ", c, ((double)i - RANGE * UNIT_RANGE) / UNIT_RANGE);
			
			for(int j = 0; j < (int)(array[i] * UNIT_VALUE); j++)
			{
				System.out.print("*");
			}
			
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		double[] hist = new double[(int)(RANGE * UNIT_RANGE * 2)];
		Random random = new Random();
		
		for(int i = 0; i < TRY_COUNT; i++)
		{
			double value = random.nextGaussian();
			
			hist[value2Index(value)]++;
		}
		
		for(int i = 0; i < hist.length; i++)
		{
			hist[i] /= TRY_COUNT;
		}
		
		printHist(hist);
	}
}
