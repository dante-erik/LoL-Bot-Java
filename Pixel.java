import java.awt.Robot;
import java.awt.Color;
import java.awt.AWTException;

public class Pixel
{
	private int x;
	private int y;
	private Color color;
	
	//create Pixel with (x,y) and (r,g,b)
	public Pixel(int x, int y, int r, int g, int b)
	{
		this.x = x;
		this.y = y;
		color = new Color(r,g,b);
	}
	
	//create Pixel with (x,y) and Color
	public Pixel(int x, int y, Color c)
	{
		this.x = x;
		this.y = y;
		//avoids Color list error, Color has many constructors, bug fix
		color = new Color(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	//compare Pixel color to Color of Pixel on the screen at same x,y
	//return true if Colors are exact same
	public boolean equalToScreen() throws AWTException
	{
		Robot r = new Robot();
		
		return color.equals(r.getPixelColor(x,y));
	}
	
	//compare Pixel color to Color of Pixel on the screen at same x,y
	//return true if Colors RGB are within tolerance range
	public boolean equalToScreen(int tolerance) throws AWTException
	{
		Robot r = new Robot();
		Color c = r.getPixelColor(x,y);
		
		//if color.getRed() is outside of the range of c.getRed() +- tolerance, return false
		if(!(color.getRed() <= c.getRed() + tolerance && color.getRed() >= c.getRed() - tolerance))
			return false;
		
		//if color.getGreen() is outside of the range of c.getGreen() +- tolerance, return false
		if(!(color.getGreen() <= c.getGreen() + tolerance && color.getGreen() >= c.getGreen() - tolerance))
			return false;
		
		//if color.getBlue() is outside of the range of c.getBlue() +- tolerance, return false
		if(!(color.getBlue() <= c.getBlue() + tolerance && color.getBlue() >= c.getBlue() - tolerance))
			return false;
		
		return true;
	}
	
	public String toString()
	{
		return "("+x+", "+y+", "+color+")";
	}
}