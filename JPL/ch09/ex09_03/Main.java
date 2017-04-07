package ex09_03;

public class Main
{
	private static int DEPTH = 12;
	
	private static void printArray(int[][] array)
	{
		for(int i = 0; i < array.length; i++)
		{
			for(int j = 0; j < array[i].length; j++)
			{
				System.out.printf("%4d ", array[i][j]);
			}
			
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		int[][] pascalTri = new int[DEPTH][];
		
		for(int i = 0; i < DEPTH; i++)
		{
			final int length = i + 1;
			pascalTri[i] = new int[length];
			
			if(i == 0)
			{
				pascalTri[0][0] = 1;
			}
			
			for(int j = 0; j < length; j++)
			{
				if((j == 0) || (j == length - 1))
				{
					pascalTri[i][j] = 1;
				}
				else
				{
					pascalTri[i][j] =
						pascalTri[i - 1][j - 1] + pascalTri[i - 1][j];
				}
			}
		}
		
		printArray(pascalTri);
	}
}
