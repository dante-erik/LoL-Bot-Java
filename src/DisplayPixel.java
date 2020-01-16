import java.awt.*;
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
		
		boolean lockedOnPixel = false;
		
		Scanner kb = new Scanner(System.in);
		
		System.out.print("Lock onto specific pixel x,y? (y/N): ");
		if(kb.next().toLowerCase().equals("y"))
		{
			lockedOnPixel = true;
			System.out.println("Enter the x value of specific pixel: ");
			x = kb.nextInt();
			System.out.println("Enter the y value of specific pixel: ");
			y = kb.nextInt();
		}
		
		System.out.print("Enter the delay in ms between prints (int): ");
		int milliseconds = Math.abs(kb.nextInt());
		
		if(lockedOnPixel)
			while(true)
			{
				c = r.getPixelColor(x, y);
				System.out.println(x+", "+y+", "+c.getRed()+", "+c.getGreen()+", "+c.getBlue());
				r.delay(milliseconds);
			}
		else
			while(true)
			{
				p = MouseInfo.getPointerInfo().getLocation();
				x = (int)p.getX();
				y = (int)p.getY();
				c = r.getPixelColor(x, y);
				System.out.println(x+", "+y+", "+c.getRed()+", "+c.getGreen()+", "+c.getBlue());
				r.delay(milliseconds);
			}
	}
}