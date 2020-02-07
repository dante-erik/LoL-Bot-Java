import java.awt.*;
import java.awt.event.KeyEvent;

public class Champion
{
	//used in specific ChampionRolePlayer.java files
	protected RobotPlus rp;
	
	private int globalDelayMultiplier;
	
	private int selectChampionDelay;
	private int callRoleDelay;
	private int closeUnlockNotificationDelay;
	private int lockInDelay;
	private int buyItemDelay;
	
	public Champion(int globalDelayMultiplier) throws AWTException
	{
		rp = new RobotPlus();
		
		this.globalDelayMultiplier = globalDelayMultiplier;
		
		selectChampionDelay = (int)(.25 * globalDelayMultiplier);
		callRoleDelay = (int)(.15 * globalDelayMultiplier);
		closeUnlockNotificationDelay = (int)(.15 * globalDelayMultiplier);
		lockInDelay = (int)(.15 * globalDelayMultiplier);
		buyItemDelay = (int)(.1 * globalDelayMultiplier);
		
	}
	
	public void delay(int milliseconds)
	{
		rp.delay(milliseconds);
	}
	
	public void selectChampion(String championName)
	{
		//accuracy over speed
		rp.setAutoDelay(selectChampionDelay);
		
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
		rp.setAutoDelay(callRoleDelay);
		
		//text bot for msging other players
		rp.mouseMove(387, 842);
		rp.mouseClick(1);
		
		rp.keyClick(role);
		
		rp.keyPress(KeyEvent.VK_ENTER);
		rp.keyRelease(KeyEvent.VK_ENTER);
		
		//no delay for other methods
		rp.setAutoDelay(0);
	}
	
	public void closeUnlockNotification()
	{
		rp.setAutoDelay(closeUnlockNotificationDelay);
		
		// "X" button
		rp.mouseMove(1012, 738);
		rp.mouseClick(1);
		
		rp.setAutoDelay(0);
	}
	
	public void lockIn()
	{
		rp.setAutoDelay(lockInDelay);
		
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
		rp.setAutoDelay(buyItemDelay);
		
		shopSearchHotkey();
		rp.keyClick(item);
		shopBuyHotkey();
		
		rp.setAutoDelay(0);
	}
}