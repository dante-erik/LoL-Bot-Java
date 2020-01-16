import java.awt.*;
import java.awt.event.KeyEvent;

public class Champion
{
	protected RobotPlus rp = new RobotPlus();
	
	public Champion() throws AWTException {}
	
	public void selectChampion(String championName)
	{
		rp.setAutoDelay(75);
		//text box for champ search
		rp.mouseMove(1087, 264);
		rp.mouseClick(1);
		rp.keyClick(championName);
		//icon for champ searched
		rp.mouseMove(702, 330);
		rp.mouseClick(1);
	}
	
	public void callRole(String role)
	{
		//text bot for msging other players
		rp.mouseMove(387, 842);
		rp.mouseClick(1);
		rp.keyClick(role);
		rp.keyPress(KeyEvent.VK_ENTER);
		rp.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void lockIn()
	{
		//lock in button
		rp.mouseMove(963, 766);
		rp.mouseClick(1);
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
		shopSearchHotkey();
		rp.keyClick(item);
		shopBuyHotkey();
	}
}