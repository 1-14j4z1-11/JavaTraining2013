package ex16_11;

import java.util.Random;
import ex16_11.Game.Mark;

public class RandomPlayer implements Playable
{
	@Override
	public void play(Game game, Mark mark)
	{
		Random random = new Random();
		
		while(!game.putMark(random.nextInt(Game.FIELD_SIZE),
			random.nextInt(Game.FIELD_SIZE), mark));
	}
}
