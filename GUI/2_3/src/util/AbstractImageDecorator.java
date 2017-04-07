package util;

import java.awt.image.BufferedImage;

public class AbstractImageDecorator implements ImageDecorator
{
	private final ImageDecorator decorator;
	
	public AbstractImageDecorator()
	{
		decorator = null;
	}

	public AbstractImageDecorator(ImageDecorator decorator)
	{
		this.decorator = decorator;
	}
	
	public final BufferedImage decorate(BufferedImage image)
	{
		if(decorator != null)
		{
			image = decorator.decorate(image);
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int j = 0; j < height; j++)
		{
			for(int i = 0; i < width; i++)
			{
				int value = image.getRGB(i, j);
				int r = (value >>> 16) & 0xFF;
				int g = (value >>> 8) & 0xFF;
				int b = value & 0xFF;
				
				r = (int)(r * 0.75 + 64.0 * (j % (height / 2)) / (height / 2.0));
				g = (int)(g * 0.75 + 64.0 * (j % (height / 2)) / (height / 2.0));
				b = (int)(b * 0.75 + 64.0 * (j % (height / 2)) / (height / 2.0));
				
				value = 0xFF000000 | (r << 16) | (g << 8) | b;
				image.setRGB(i, j, value);
			}
		}
		
		return image;
	}
}
