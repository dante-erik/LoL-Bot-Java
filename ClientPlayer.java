import java.awt.AWTException;

public class ClientPlayer
{
	private RobotPlus rp = new RobotPlus();
	
	public ClientPlayer() throws AWTException {}
	
	public void startQueue()
	{
		rp.mouseMove(startqueuebutton, startqueuebutton);
		rp.mouseClick(1);
	}
	
	public void acceptMatch()
	{
		rp.mouseMove(acceptmatchbutton, acceptmatchbutton);
		rp.mouseClick(1);
	}
	
	public void honorTeammate()
	{
		rp.mouseMove(honorportrait, honorportrait);
		rp.mouseClick(1);
	}
	
	public void dismissLevelUp()
	{
		rp.mouseMove(okbutton, okbutton);
		rp.mouseClick(1);
	}
	
	public void dismissQuestComplete()
	{
		rp.mouseMove(okbutton2, okbutton2);
		rp.mouseClick(1);
	}
	
	public void playAgain()
	{
		rp.mouseMove(playagainbutton, playagainbutton);
		rp.mouseClick(1);
	}
}