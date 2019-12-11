import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/*
methods:

//create default RobotPlus with 0 autoDelay
	public RobotPlus()
	
//create RobotPlus with n autoDelay
	public RobotPlus(int milliseconds)
	
//moves the cursor in a straight line from current position to inputted x and y with a duration based on distance
	@Override
	public void cursorMove(int xEnd, int yEnd)
	
//moves the cursor in a straight line from current position to inputted x and y over N milliseconds
	public void cursorMove(int xEnd, int yEnd, int milliseconds) throws AWTException
	
//clicks the left click or right click mouse buttons holding either for 25 milliseconds
	public void mouseClick(int button) throws AWTException, IllegalArgumentException
	
//clicks the left click or right click mouse buttons holding either for N milliseconds
	public void mouseClick(int button, int milliseconds) throws AWTException, IllegalArgumentException
	
//clicks keys in a String holding each key press for 50 milliseconds
	public void keyClick(String str) throws AWTException
	
//clicks keys in a String holding each key press for N milliseconds
	public void keyClick(String str, int milliseconds) throws AWTException

//clicks a key using java's KeyEvent class constants holding the key press for 50 milliseconds
	public void keyClick(int key) throws AWTException
	
//clicks a key using java's KeyEvent class constants holding the key press for N milliseconds
	public void keyClick(int key, int milliseconds) throws AWTException
	
//resets autoDelay to milliseconds
	@Override
	public void setAutoDelay(int milliseconds)
	
//returns int value for autoDelay
	public int getAutoDelay()
	
//return int representing the square root of the largest perfect square less than or equal to n
	protected int getLowerSqrt(int n)
	
//return double representing an approximation of the square root of n
	protected double fastSqrt(int n)
	
//return double representing the euclidian distance between two cartesian points passed componentially as integers
	public double fastDistance(int x1, int y1, int x2, int y2)
	
//bit shifting System.nanoTime() for efficient random number generation
	public int getRandInt(int min, int max)
*/

public class RobotPlus extends Robot
{
	//milliseconds waited after each method call, some methods dont include this wait time
	private int autoDelay;
	
	//create default RobotPlus with 0 autoDelay
	public RobotPlus() throws AWTException
	{
		autoDelay = 0;
	}
	
	//create RobotPlus with n autoDelay
	public RobotPlus(int milliseconds) throws AWTException
	{
		autoDelay = milliseconds;
	}
	
	//moves the cursor in a straight line from current position to inputted x and y with a duration based on distance
	@Override
	public void mouseMove(int xEnd, int yEnd)
	{	
		int xStart = (int)MouseInfo.getPointerInfo().getLocation().getX();
		int yStart = (int)MouseInfo.getPointerInfo().getLocation().getY();
		int xDiff = xEnd - xStart;
		int yDiff = yEnd - yStart;
		
		//an approximation of the distance formula for the distance between the cursor and the inputted x and y
		int milliseconds = (int)(0.5 + fastDistance(xStart, yStart, xEnd, yEnd));
		
		//try{}catch{} because throws AWTException caused error: cannot override Robot mouseMove(int x, int y)
		try
		{
			Robot r = new Robot();
		
			r.setAutoDelay(1);
		
			//currentMillisecond starting at 0 would move the cursor to its current location (pointless action), so currentMillisecond starts at 1
			for(int currentMillisecond = 1; currentMillisecond <= milliseconds; currentMillisecond++)
			{
				//move cursor to new x y based on currentMillisecond
				super.mouseMove((int)((double)currentMillisecond/milliseconds*xDiff+xStart), (int)((double)currentMillisecond/milliseconds*yDiff+yStart));
			}
		
			if(autoDelay > 0)
				r.delay(autoDelay);
		}
		catch(AWTException ex)
		{
			System.out.println("AWTException Error in RobotPlus.java mouseMove(int xEnd, int yEnd)");
		}
	}	
	
	//moves the cursor in a straight line from current position to inputted x and y over N milliseconds
	public void mouseMove(int xEnd, int yEnd, int milliseconds) throws AWTException
	{
		int xStart = (int)MouseInfo.getPointerInfo().getLocation().getX();
		int yStart = (int)MouseInfo.getPointerInfo().getLocation().getY();
		int xDiff = xEnd - xStart;
		int yDiff = yEnd - yStart;
		
		Robot r = new Robot();
		
		r.setAutoDelay(1);
		
		//currentMillisecond starting at 0 would move the cursor to its current location (pointless action), so currentMillisecond starts at 1
		for(int currentMillisecond = 1; currentMillisecond <= milliseconds; currentMillisecond++)
		{
			//move cursor to new x y based on currentMillisecond
			super.mouseMove((int)((double)currentMillisecond/milliseconds*xDiff+xStart), (int)((double)currentMillisecond/milliseconds*yDiff+yStart));
		}
		
		if(autoDelay > 0)
			r.delay(autoDelay);
	}
	
	//clicks the left click or right click mouse buttons holding either for 25 milliseconds
	public void mouseClick(int button) throws AWTException, IllegalArgumentException
	{
		Robot r = new Robot();
		//left click
		if(button == 1)
		{
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			r.delay(25);
			r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		//right click
		if(button == 2)
		{
			r.mousePress(InputEvent.BUTTON2_DOWN_MASK);
			r.delay(25);
			r.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
		}
		
		if(autoDelay > 0)
			r.delay(autoDelay);
	}
	
	//clicks the left click or right click mouse buttons holding either for N milliseconds
	public void mouseClick(int button, int milliseconds) throws AWTException, IllegalArgumentException
	{
		Robot r = new Robot();
		//left click
		if(button == 1)
		{
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.delay(milliseconds);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		//right click
		if(button == 2)
		{
			r.mousePress(InputEvent.BUTTON2_MASK);
			r.delay(milliseconds);
			r.mouseRelease(InputEvent.BUTTON2_MASK);
		}
		
		if(autoDelay > 0)
			r.delay(autoDelay);
	}
	
	//clicks keys in a String holding each key press for 50 milliseconds
	public void keyClick(String str) throws AWTException
	{
		Robot r = new Robot();
		//[2][36] space, a-z, 0-9
		//[0][i] for .equals check
		//[1][i] for robot
		//TODO: more efficient implementation
		Object keys[][] = new Object[][]{{" ","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"},
										 {KeyEvent.VK_SPACE,KeyEvent.VK_A,KeyEvent.VK_B,KeyEvent.VK_C,KeyEvent.VK_D,KeyEvent.VK_E,KeyEvent.VK_F,KeyEvent.VK_G,KeyEvent.VK_H,KeyEvent.VK_I,KeyEvent.VK_J,KeyEvent.VK_K,KeyEvent.VK_L,KeyEvent.VK_M,KeyEvent.VK_N,KeyEvent.VK_O,KeyEvent.VK_P,KeyEvent.VK_Q,KeyEvent.VK_R,KeyEvent.VK_S,KeyEvent.VK_T,KeyEvent.VK_U,KeyEvent.VK_V,KeyEvent.VK_W,KeyEvent.VK_X,KeyEvent.VK_Y,KeyEvent.VK_Z,KeyEvent.VK_0,KeyEvent.VK_1,KeyEvent.VK_2,KeyEvent.VK_3,KeyEvent.VK_4,KeyEvent.VK_5,KeyEvent.VK_6,KeyEvent.VK_7,KeyEvent.VK_8,KeyEvent.VK_9}};
		for(int i = 0; i < str.length(); i++)
		{
			for(int j = 0; j < keys[0].length; j++)
			{
				if(str.substring(i,i+1).equals(keys[0][j]))
				{
					r.keyPress((Integer)keys[1][j]);
					r.delay(50);
					r.keyRelease((Integer)keys[1][j]);
					j = keys[0].length;
				}
			}
		}
		
		if(autoDelay > 0)
			r.delay(autoDelay);
	}
	
	//clicks keys in a String holding each key press for N milliseconds
	public void keyClick(String str, int milliseconds) throws AWTException
	{
		Robot r = new Robot();
		//[2][36] space, a-z, 0-9
		//[0][i] for .equals check
		//[1][i] for robot
		//TODO: more efficient implementation
		Object keys[][] = new Object[][]{{" ","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"},
										 {KeyEvent.VK_SPACE,KeyEvent.VK_A,KeyEvent.VK_B,KeyEvent.VK_C,KeyEvent.VK_D,KeyEvent.VK_E,KeyEvent.VK_F,KeyEvent.VK_G,KeyEvent.VK_H,KeyEvent.VK_I,KeyEvent.VK_J,KeyEvent.VK_K,KeyEvent.VK_L,KeyEvent.VK_M,KeyEvent.VK_N,KeyEvent.VK_O,KeyEvent.VK_P,KeyEvent.VK_Q,KeyEvent.VK_R,KeyEvent.VK_S,KeyEvent.VK_T,KeyEvent.VK_U,KeyEvent.VK_V,KeyEvent.VK_W,KeyEvent.VK_X,KeyEvent.VK_Y,KeyEvent.VK_Z,KeyEvent.VK_0,KeyEvent.VK_1,KeyEvent.VK_2,KeyEvent.VK_3,KeyEvent.VK_4,KeyEvent.VK_5,KeyEvent.VK_6,KeyEvent.VK_7,KeyEvent.VK_8,KeyEvent.VK_9}};
		for(int i = 0; i < str.length(); i++)
		{
			for(int j = 0; j < keys[0].length; j++)
			{
				if(str.substring(i,i+1).equals(keys[0][j]))
				{
					r.keyPress((Integer)keys[1][j]);
					r.delay(milliseconds);
					r.keyRelease((Integer)keys[1][j]);
					j = keys[0].length;
				}
			}
		}
		
		if(autoDelay > 0)
			r.delay(autoDelay);
	}
	
	//clicks a key using java's KeyEvent class constants holding the key press for 50 milliseconds
	public void keyClick(int key) throws AWTException
	{
		Robot r = new Robot();
		r.keyPress(key);
		r.delay(50);
		r.keyRelease(key);
		
		if(autoDelay > 0)
			r.delay(autoDelay);
	}
	
	//clicks a key using java's KeyEvent class constants holding the key press for N milliseconds
	public void keyClick(int key, int milliseconds) throws AWTException
	{
		Robot r = new Robot();
		r.keyPress(key);
		r.delay(milliseconds);
		r.keyRelease(key);
		
		if(autoDelay > 0)
			r.delay(autoDelay);
	}
	
	//resets autoDelay to milliseconds
	@Override
	public void setAutoDelay(int milliseconds)
	{
		autoDelay = milliseconds;
	}
	
	//returns int value for autoDelay
	@Override
	public int getAutoDelay()
	{
		return autoDelay;
	}
	
	//return int representing the square root of the largest perfect square less than or equal to n
	protected int getLowerSqrt(int n)
	{
		for(int i = 0; i < n; ++i) if(i*i > n) return i - 1;
		return n;
	}
	
	//return double representing an approximation of the square root of n
	protected double fastSqrt(int n)
	{
		int i = getLowerSqrt(n);
		return i + (n - i * i) / ((i << 1) + 1d);
	}
	
	//return double representing the euclidian distance between two cartesian points passed componentially as integers
	public double fastDistance(int x1, int y1, int x2, int y2)
	{
		int deltaX = x2-x1;
		int deltaY = y2-y1;
		return fastSqrt((deltaX)*(deltaX)+(deltaY)*(deltaY));
	}
	
	//bit shifting System.nanoTime() for efficient random number generation
	public int getRandInt(int min, int max)
	{
		long x = System.nanoTime();
		
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);
		
		return (int)(Math.abs(x)%(max+1-min)+min);
	}
}