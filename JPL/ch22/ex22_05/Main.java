package ex22_05;

import java.util.Random;

public class Main
{
	private static final int DICE_FACES = 6;

	public static int[] dice_Case(int diceNum)
	{
		if(diceNum <= 0)
		{
			throw new IllegalArgumentException();
		}
		
		int[] array = new int[diceNum * DICE_FACES];
		
		if(diceNum == 1)
		{
			for(int i = 0; i < array.length; i++)
			{
				array[i] = 1;
			}
		}
		else
		{
			int[] array1 = dice_Case(diceNum - 1);
			
			for(int i = diceNum - 1; i < array.length; i++)
			{
				for(int j = 1; j <= DICE_FACES; j++)
				{
					if((i - j >= 0) && (i - j < array1.length))
					{
						array[i] += array1[i - j];
					}
				}
			}
		}
		
		return array;
	}
	
	public static double[] dice_Calc(int diceNum)
	{
		double[] array = new double[diceNum * DICE_FACES];
		double total = Math.pow(DICE_FACES, diceNum);
		
		int[] arrayCase = dice_Case(diceNum);
		
		for(int i = 0; i < array.length; i++)
		{
			array[i] = (double)arrayCase[i] / total;
		}
		
		return array;
	}
	
	public static double[] dice_Random(int diceNum, int tryCount)
	{
		double[] array = new double[diceNum * DICE_FACES];
		Random random = new Random();

		for(int i = 0; i < tryCount; i++)
		{
			int value = 0;
			
			for(int j = 0; j < diceNum; j++)
			{
				value += random.nextInt(6) + 1;
			}

			array[value - 1]++;
		}
		
		for(int i = 0; i < array.length; i++)
		{
			array[i] /= tryCount;
		}
		
		return array;
	}
	
	public static void printArray(double[] array1, double[] array2)
	{
		for(int i = 0; (i < array1.length) && (i < array2.length); i++)
		{
			System.out.printf("%3d : %5.3f % 5.3f%n", i + 1, array1[i], array2[i]);
		}
	}
	
	public static void main(String[] args)
	{
		int dice = 5;
		double[] calc = dice_Calc(dice);
		double[] rand = dice_Random(dice, 10000000);
		
		printArray(calc, rand);
	}
}
