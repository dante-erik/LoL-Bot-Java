import java.awt.*;
import java.awt.event.KeyEvent;

public class Champion
{
	protected RobotPlus rp = new RobotPlus();
	
	public Champion() throws AWTException {}
	
	public void selectChampion(String championName)
	{
		//accuracy over speed
		rp.setAutoDelay(200);
		
		//text box for champ search
		rp.mouseMove(1087, 264);
		rp.mouseClick(1);
		
		rp.keyClick(championName);
		
		//icon for champ searched
		rp.mouseMove(702, 330);
		rp.mouseClick(1);
		
		//speed up
		rp.setAutoDelay(0);
	}
	
	public void callRole(String role)
	{
		//accuracy over speed
		rp.setAutoDelay(75);
		
		//text bot for msging other players
		rp.mouseMove(387, 842);
		rp.mouseClick(1);
		
		rp.keyClick(role);
		
		rp.keyPress(KeyEvent.VK_ENTER);
		rp.keyRelease(KeyEvent.VK_ENTER);
		
		//no delay for other methods
		rp.setAutoDelay(0);
	}
	
	public void lockIn()
	{
		//accuracy over speed
		rp.setAutoDelay(75);
		
		//lock in button
		rp.mouseMove(963, 766);
		rp.mouseClick(1);
		
		//no delay for other methods
		rp.setAutoDelay(0);
	}
	
	public void lockCamera()
	{
		rp.keyClick("y");
	}
	
	public void shopSearchHotkey()
	{
		rp.keyPress(KeyEvent.VK_CONTROL);
		rp.keyClick("l");
		rp.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public void shopBuyHotkey()
	{
		rp.keyClick(KeyEvent.VK_ENTER);
		rp.keyClick(KeyEvent.VK_ENTER);
	}
	
	public void buyItem(String item)
	{
		rp.setAutoDelay(75);
		
		shopSearchHotkey();
		rp.keyClick(item);
		shopBuyHotkey();
		
		rp.setAutoDelay(0);
	}
	
	public void delay(int milliseconds)
	{
		rp.delay(milliseconds);
	}
}