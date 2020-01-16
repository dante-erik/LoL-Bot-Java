import java.awt.*;

public class Pixel
{
	private int x;
	private int y;
	private int red;
	private int green;
	private int blue;
	
	//create Pixel with (x,y) and (r,g,b)
	public Pixel(int x, int y, int red, int green, int blue)
	{
		this.x = x;
		this.y = y;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	//create Pixel with (x,y) and Color
	public Pixel(int x, int y, Color c)
	{
		this.x = x;
		this.y = y;
		red = c.getRed();
		green = c.getGreen();
		blue = c.getBlue();
	}
	
	//compare Pixel color to Color of Pixel on the screen at same x,y
	//return true if Colors RGB are within tolerance range
	public boolean isVisible(int tolerance)
	{
		try
		{
			Robot r = new Robot();
			Color c = r.getPixelColor(x,y);

            //if color.getRed() is outside of the range of c.getRed() +- tolerance, return false
            if (!(red <= c.getRed() + tolerance && red >= c.getRed() - tolerance))
				return false;

            //if color.getGreen() is outside of the range of c.getGreen() +- tolerance, return false
            if (!(green <= c.getGreen() + tolerance && green >= c.getGreen() - tolerance))
				return false;

			//return if all passed, or if blue test failed
            return blue <= c.getBlue() + tolerance && blue >= c.getBlue() - tolerance;
        }
		catch(AWTException ex)
		{
			System.out.println("AWTException error in Pixel.java");
		}
		
		//default to false if try{}catch{} fails
		return false;
	}
	
	public String toString()
	{
		return "("+x+", "+y+") ("+red+", "+green+", "+blue+")";
	}
}