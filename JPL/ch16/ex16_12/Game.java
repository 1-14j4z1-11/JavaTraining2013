package ex16_12;

import java.io.InputStream;

public class Game
{
	public enum Mark
	{
		None, Circle, Cross,
	}
	
	public static final int FIELD_SIZE = 3;
	private Mark[] field = new Mark[FIELD_SIZE * FIELD_SIZE];
	
	{
		for(int i = 0; i < this.field.length; i++)
		{
			this.field[i] = Mark.None;
		}
	}
	
	public boolean putMark(int i, int j, Mark mark)
	{
		if(this.field[i * FIELD_SIZE + j] == Mark.None)
		{
			this.field[i * FIELD_SIZE + j] = mark;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isFinished()
	{
		for(int i = 0; i < this.field.length; i++)
		{
			if(this.field[i] == Mark.None)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public int count(Mark mark)
	{
		int count = 0;
		
		for(int i = 0; i < this.field.length; i++)
		{
			if(this.field[i] == mark)
			{
				count++;
			}
		}
		
		return count;
	}
	
	public void print()
	{
		for(int i = 0; i < this.field.length; i++)
		{
			switch(this.field[i])
			{
				case Circle:
					System.out.print("○");
					break;

				case Cross:
					System.out.print("×");
					break;

				case None:
					System.out.print(" ");
					break;
				
				default:
					throw new AssertionError();
			}
			
			if((i + 1) % FIELD_SIZE == 0)
			{
				System.out.println();
			}
		}
	}
	
	public static void main(String[] args)
	{
		if(args.length < 4)
		{
			System.out.println("Usage : <class1> <class2> <strategy1> <strategy2>");
			return;
		}
		
		try
		{
			Game game = new Game();
			PlayerLoader loader = new PlayerLoader();
			
			Class<? extends StrategyPlayable> cls1 = loader.loadClass(args[0]).asSubclass(StrategyPlayable.class);
			Class<? extends StrategyPlayable> cls2 = loader.loadClass(args[1]).asSubclass(StrategyPlayable.class);
			StrategyPlayable player1 = cls1.newInstance();
			StrategyPlayable player2 = cls2.newInstance();
			
			InputStream in1 = loader.getResourceAsStream(args[2]);
			InputStream in2 = loader.getResourceAsStream(args[3]);
			
			if(in1 != null)
			{
				player1.setStrategy(in1);
				in1.close();
			}

			if(in2 != null)
			{
				player2.setStrategy(in2);
				in2.close();
			}
			
			while(!game.isFinished())
			{
				player1.play(game, Mark.Circle);
				
				if(!game.isFinished())
				{
					player2.play(game, Mark.Cross);
				}
			}
			
			System.out.println("○ : " + game.count(Mark.Circle));
			System.out.println("× : " + game.count(Mark.Cross));
			System.out.println();
			game.print();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
