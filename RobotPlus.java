import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

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
    //map connecting characters to their key combo
    private final static HashMap<Character, Integer> keyLookup = new HashMap<>(37);

    //initialize values in the map, this runs as the program scans this for the first time
    static
    {
        keyLookup.put(' ', KeyEvent.VK_SPACE);
        keyLookup.put('a', KeyEvent.VK_A);
        keyLookup.put('b', KeyEvent.VK_B);
        keyLookup.put('c', KeyEvent.VK_C);
        keyLookup.put('d', KeyEvent.VK_D);
        keyLookup.put('e', KeyEvent.VK_E);
        keyLookup.put('f', KeyEvent.VK_F);
        keyLookup.put('g', KeyEvent.VK_G);
        keyLookup.put('h', KeyEvent.VK_H);
        keyLookup.put('i', KeyEvent.VK_I);
        keyLookup.put('j', KeyEvent.VK_J);
        keyLookup.put('k', KeyEvent.VK_K);
        keyLookup.put('l', KeyEvent.VK_L);
        keyLookup.put('m', KeyEvent.VK_M);
        keyLookup.put('n', KeyEvent.VK_N);
        keyLookup.put('o', KeyEvent.VK_O);
        keyLookup.put('p', KeyEvent.VK_P);
        keyLookup.put('q', KeyEvent.VK_Q);
        keyLookup.put('r', KeyEvent.VK_R);
        keyLookup.put('s', KeyEvent.VK_S);
        keyLookup.put('t', KeyEvent.VK_T);
        keyLookup.put('u', KeyEvent.VK_U);
        keyLookup.put('v', KeyEvent.VK_V);
        keyLookup.put('w', KeyEvent.VK_W);
        keyLookup.put('x', KeyEvent.VK_X);
        keyLookup.put('y', KeyEvent.VK_Y);
        keyLookup.put('z', KeyEvent.VK_Z);
        keyLookup.put('0', KeyEvent.VK_0);
        keyLookup.put('1', KeyEvent.VK_1);
        keyLookup.put('2', KeyEvent.VK_2);
        keyLookup.put('3', KeyEvent.VK_3);
        keyLookup.put('4', KeyEvent.VK_4);
        keyLookup.put('5', KeyEvent.VK_5);
        keyLookup.put('6', KeyEvent.VK_6);
        keyLookup.put('7', KeyEvent.VK_7);
        keyLookup.put('8', KeyEvent.VK_8);
        keyLookup.put('9', KeyEvent.VK_9);
    }

    //milliseconds waited after each method call, some methods dont include this wait time
    private int autoDelay;

    //create default RobotPlus with 0 autoDelay
    public RobotPlus() throws AWTException
    {
        super();
        autoDelay = 0;
    }

    //create RobotPlus with n autoDelay
    public RobotPlus(int milliseconds) throws AWTException
    {
        super();
        autoDelay = milliseconds;
    }

    //moves the cursor in a straight line from current position to inputted x and y with a duration based on distance
    @Override
    public void mouseMove(int xEnd, int yEnd)
    {
        var xStart = MouseInfo.getPointerInfo().getLocation().getX();
        var yStart = MouseInfo.getPointerInfo().getLocation().getY();
        //an approximation of the distance formula for the distance between the cursor and the inputted x and y
        var milliseconds = fastDistance((int)xStart, (int)yStart, xEnd, yEnd);

        //try{}catch{} because throws AWTException caused error: cannot override Robot mouseMove(int x, int y)
        try
        {
            mouseMove(xEnd, yEnd, (int)Math.round(milliseconds));
        }
        catch(AWTException ex)
        {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    //moves the cursor in a straight line from current position to inputted x and y over N milliseconds
    public void mouseMove(int xEnd, int yEnd, int milliseconds) throws AWTException
    {
        var xStart = MouseInfo.getPointerInfo().getLocation().getX();
        var yStart = MouseInfo.getPointerInfo().getLocation().getY();
        var xDiff = xEnd - xStart;
        var yDiff = yEnd - yStart;

        Robot r = new Robot();

        r.setAutoDelay(1);

        //currentMillisecond starting at 0 would move the cursor to its current location (pointless action), so currentMillisecond starts at 1
        for(var currentMillisecond = 1; currentMillisecond <= milliseconds; currentMillisecond++)
            //move cursor to new x y based on currentMillisecond
            super.mouseMove((int) (((currentMillisecond / milliseconds) * xDiff) + xStart),
	                (int) (((currentMillisecond / milliseconds) * yDiff) + yStart));

        if(autoDelay > 0)
            r.delay(autoDelay);
    }

    //clicks the left click or right click mouse buttons holding either for 25 milliseconds
    public void mouseClick(int button) throws AWTException, IllegalArgumentException
    {
        mouseClick(button, 25);
    }

    //clicks the left click or right click mouse buttons holding either for N milliseconds
    public void mouseClick(int button, int milliseconds) throws AWTException, IllegalArgumentException
    {
        Robot r = new Robot();
        //left click
        if(button == 1)
        {
            r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            r.delay(milliseconds);
            r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
        //right click
        if(button == 2)
        {
            r.mousePress(InputEvent.BUTTON2_DOWN_MASK);
            r.delay(milliseconds);
            r.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
        }

        if(autoDelay > 0)
            r.delay(autoDelay);
    }

    //clicks keys in a String holding each key press for 50 milliseconds
    public void keyClick(String str) throws AWTException
    {
        keyClick(str, 50);
    }

    //clicks keys in a String holding each key press for N milliseconds
    public void keyClick(String str, int milliseconds) throws AWTException
    {
        Robot r = new Robot();
        //[2][36] space, a-z, 0-9
        //[0][i] for .equals check
        //[1][i] for robot
        for(int i = 0; i < str.length(); i++)
        {
            var keyCode = keyLookup.get(str.charAt(i));
            r.keyPress(keyCode);
            r.delay(milliseconds);
            r.keyRelease(keyCode);
        }

        if(autoDelay > 0)
            r.delay(autoDelay);
    }

    /**
     * Converts a String into an ArrayList of KeyEvents in O(n) time,
     * where n is the length of the string.
     *
     * This is accomplished by using a HashMap to store links between
     * characters and their KeyEvent codes.
     *
     * @param str String to convert to KeyEvents
     * @return ArrayList<Integer> An ArrayList containing the KeyEvent codes for each character in the String in order
     * @author Dante Barbieri <pulchroxloom>
     * @see ArrayList
     * @see HashMap
     * @see KeyEvent
     * @deprecated
     */
    @Deprecated
    protected ArrayList<Integer> stringToKeypress(String str)
    {
        ArrayList<Integer> keyPresses = new ArrayList<>(str.length());
        for(var i = 0; i < str.length(); ++i)
            keyPresses.add(keyLookup.get(str.charAt(i)));
        return keyPresses;
    }

    //clicks a key using java's KeyEvent class constants holding the key press for 50 milliseconds
    public void keyClick(int key) throws AWTException
    {
        keyClick(key, 50);
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

    // It hurts me that this is done this way. Generally if the
    // getters and setters do no checking (bounds checking, error checking,
    // verification that the value makes sense at all), then the data member
    // should be public but you're Overriding Java's bad design. Perhaps add
    // some kind of Math.abs(milliseconds) test to ensure negative time is
    // not set.

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
        for(int i = 0; i < n; ++i) if((i * i) > n) return i - 1;
        return n;
    }

    //return double representing an approximation of the square root of n
    protected double fastSqrt(int n)
    {
        int i = getLowerSqrt(n);
        return i + ((n - (i * i)) / ((i << 1) + 1d));
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
