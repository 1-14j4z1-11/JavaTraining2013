package ex24_02;

import java.util.Currency;
import java.util.Locale;

public class Main
{
	public static void main(String[] args)
	{
		Locale[] locales = new Locale[]
		{
			Locale.CHINA,
			Locale.FRANCE,
			Locale.ITALY,
			Locale.CANADA,
			Locale.JAPAN,
			Locale.GERMANY,
		};
		
		int length = locales.length;
		
		Currency[] currencies = new Currency[length];
		
		for(int i = 0; i < length; i++)
		{
			currencies[i] = Currency.getInstance(locales[i]);
		}
		
		System.out.print("\t|\t");
		for(int j = 0; j < length; j++)
		{
			System.out.print(String.format("%3d\t", j + 1));
		}
		System.out.println();
		
		for(int i = 0; i < length; i++)
		{
			System.out.print((i + 1) + " " + locales[i] + "\t|\t");
			
			for(int j = 0; j < length; j++)
			{
				String symbol = currencies[i].getSymbol(locales[j]);
				System.out.print(symbol + "\t");
			}
			System.out.println();
		}
	}
}
