import java.awt.*;

public class Pixel
{
	private int x;
	private int y;
	private int r;
	private int g;
	private int b;
	
	//create Pixel with (x,y) and (r,g,b)
	public Pixel(int x, int y, int r, int g, int b)
	{
		this.x = x;
		this.y = y;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	//create Pixel with (x,y) and Color
	public Pixel(int x, int y, Color c)
	{
		this.x = x;
		this.y = y;
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
	}
	
	//compare Pixel color to Color of Pixel on the screen at same x,y
	//return true if Colors RGB are within tolerance range
	public boolean equalToScreen(int tolerance)
	{
		try
		{
			RobotPlus rp = new RobotPlus();
			Color c = rp.getPixelColor(x,y);

            //if color.getRed() is outside of the range of c.getRed() +- tolerance, return false
            if (!(r <= c.getRed() + tolerance && r >= c.getRed() - tolerance)) return false;

            //if color.getGreen() is outside of the range of c.getGreen() +- tolerance, return false
            if (!(g <= c.getGreen() + tolerance && g >= c.getGreen() - tolerance)) return false;

            //if color.getBlue() is outside of the range of c.getBlue() +- tolerance, return false
            return b <= c.getBlue() + tolerance && b >= c.getBlue() - tolerance;
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
		return "("+x+", "+y+") ("+r+", "+g+", "+b+")";
	}
}