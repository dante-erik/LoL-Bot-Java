import java.awt.AWTException;

public class ClientPlayer
{
	private RobotPlus rp = new RobotPlus();
	
	public ClientPlayer() throws AWTException {}
	
	public void startQueue()
	{
		rp.mouseMove(858, 846);
		rp.mouseClick(1);
	}
	
	public void acceptMatch()
	{
		rp.mouseMove(957, 717);
		rp.mouseClick(1);
	}
	
	public void honorTeammate()
	{
		rp.mouseMove(1138, 521);
		rp.mouseClick(1);
		rp.mouseClick(1);
		rp.mouseClick(1);
		rp.mouseClick(1);
		rp.mouseClick(1);
	}
	
	public void dismissLevelUp()
	{
		rp.mouseMove(958, 839);
		rp.mouseClick(1);
	}
	
	public void dismissMissions()
	{
		rp.mouseMove(961, 838);
		rp.mouseClick(1);
	}
	
	public void playAgain()
	{
		rp.mouseMove(855, 848);
		rp.mouseClick(1);
	}
	
	public void acceptDailyPlay()
	{
		rp.mouseMove(955, 548);
		rp.mouseClick(1);
		rp.mouseMove(960, 837);
		rp.mouseClick(1);
	}
}