import java.awt.AWTException;

public abstract class Champion
{
	protected RobotPlus rp = new RobotPlus();
	
	public Champion() throws AWTException {}
	
	public abstract void pickChampion();
	public abstract void callRole();
	public abstract void selectSummonerSpells();
	public abstract void useSummonerSpells();
	public abstract void retreat();
	public abstract void buyItems();
	public abstract void attack();
	public abstract void castSpells();
	
	public void startQueue()
	{
		rp.mouseMove(500,500);
		rp.mouseClick(2);
	}
	
	public void acceptMatch()
	{
		
	}
	
	public void honorTeammate()
	{
		
	}
	
	public void dismissLevelUp()
	{
		
	}
	
	public void dismissQuestComplete()
	{
		
	}
	
	public void playAgain()
	{
		
	}
}