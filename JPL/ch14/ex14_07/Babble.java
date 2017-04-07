package ex14_07;

public class Babble extends Thread
{
	static boolean doYield;
	static int howOften;
	private String word;
	
	Babble(String whatToSay)
	{
		this.word = whatToSay;
	}
	
	public void run()
	{
		for(int i = 0; i < howOften; i++)
		{
			System.out.println(word);
			
			if(doYield)
			{
				Thread.yield();
			}
		}
	}
	
	public static void main(String[] args)
	{
		doYield = new Boolean(args[0]);
		howOften = Integer.parseInt(args[1]);
		
		for(int i = 2; i < args.length; i++)
		{
			new Babble(args[i]).start();
		}
	}
}
