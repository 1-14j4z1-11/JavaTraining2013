package ex16_12;

import java.io.InputStream;

public interface StrategyPlayable extends Playable
{
	public void setStrategy(InputStream in);
}
