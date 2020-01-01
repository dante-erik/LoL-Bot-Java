import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Color;
import java.util.Scanner;

public class DisplayPixel
{	
	public static void main(String[] args) throws AWTException
	{
		Point p = new Point();
		int x = 0;
		int y = 0;
		Color c = new Color(0,0,0);
		Robot r = new Robot();
		
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter the delay in ms between prints (int): ");
		int milliseconds = Math.abs(kb.nextInt());
		
		while(true)
		{
			p = MouseInfo.getPointerInfo().getLocation();
			x = (int)Math.round(p.getX());
			y = (int)Math.round(p.getY());
			c = r.getPixelColor(x, y);
			System.out.println(x+", "+y+", "+c.getRed()+", "+c.getGreen()+", "+c.getBlue());
			r.delay(milliseconds);
		}
	}
}