package ex16_11;

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
		if(args.length < 2)
		{
			return;
		}
		
		try
		{
			Game game = new Game();
			PlayerLoader loader = new PlayerLoader();
			
			Class<? extends Playable> cls1 = loader.loadClass(args[0]).asSubclass(Playable.class);
			Class<? extends Playable> cls2 = loader.loadClass(args[1]).asSubclass(Playable.class);
			Playable player1 = cls1.newInstance();
			Playable player2 = cls2.newInstance();
			
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
