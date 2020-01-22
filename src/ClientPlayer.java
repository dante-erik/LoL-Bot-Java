import java.awt.*;

public class ClientPlayer
{
	private RobotPlus rp = new RobotPlus();
	
	public ClientPlayer() throws AWTException {}
	
	public void startQueue()
	{
		//start queue button
		rp.mouseMove(858, 846);
		rp.mouseClick(1);
	}
	
	public void acceptMatch()
	{
		//accept button
		rp.mouseMove(957, 717);
		rp.mouseClick(1);
	}
	
	public void honorTeammate()
	{
		//teammate portrait
		rp.mouseMove(1138, 521);
		rp.mouseClick(1);
	}
	
	public void dismissLevelUp()
	{
		//ok button
		rp.mouseMove(958, 839);
		rp.mouseClick(1);
	}
	
	public void dismissMissions()
	{
		//ok button
		rp.mouseMove(961, 838);
		rp.mouseClick(1);
	}
	
	public void playAgain()
	{
		//play again button
		rp.mouseMove(855, 848);
		rp.mouseClick(1);
	}
	
	public void acceptDailyReward()
	{
		//champion portrait's select button
		rp.mouseMove(955, 548);
		rp.mouseClick(1);
		//ok button
		rp.mouseMove(960, 837);
		rp.mouseClick(1);
	}
	
	public void declineSaveRunePageRequest()
	{
		//No button
		rp.mouseMove(994, 554);
		rp.mouseClick(1);
	}
}