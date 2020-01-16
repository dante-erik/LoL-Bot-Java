import java.awt.*;

public class ClientBot
{
	ClientPlayer player;
	//all of these PixelGroups do not change based on champion
	//these 2 are protected because they are used in ChampionRoleBot files
	protected PixelGroup championSelect;
	protected PixelGroup loadScreen;
	private PixelGroup startQueueButton;
	private PixelGroup acceptMatchButton;
	private PixelGroup honorSelect;
	private PixelGroup playAgainButton;
	private PixelGroup dailyReward;
	private PixelGroup levelUp;
	private PixelGroup missions;
	private long a = 0;

	public ClientBot() throws AWTException {
		player = new ClientPlayer();

		startQueueButton = new PixelGroup(new Pixel(635, 585, 26, 55, 32));
		acceptMatchButton = new PixelGroup(new Pixel(994, 361, 33, 77, 98));
		championSelect = new PixelGroup(new Pixel(1148, 749, 3, 58, 70));
		loadScreen = new PixelGroup(new Pixel(955, 578, 57, 53, 50));
		honorSelect = new PixelGroup(new Pixel(882, 216, 225, 230, 210));
		playAgainButton = new PixelGroup(new Pixel(1128, 731, 9, 32, 40));
		dailyReward = new PixelGroup(new Pixel(323, 296, 50, 40, 30));
		levelUp = new PixelGroup(new Pixel(1017, 314, 238, 228, 208));
		missions = new PixelGroup(new Pixel(1263, 353, 86, 66, 35));
	}
	
	public void tick()
	{
		if(acceptMatchButton.isVisible())
		{
			player.acceptMatch();
		}
		else if(startQueueButton.isVisible())
		{
			player.startQueue();
		}
		else if(playAgainButton.isVisible())
		{
			player.playAgain();
		}
		else if(honorSelect.isVisible())
		{
			player.honorTeammate();
		}
		else if(levelUp.isVisible())
		{
			player.dismissLevelUp();
		}
		else if(dailyReward.isVisible())
		{
			player.acceptDailyReward();
		}
		else if(missions.isVisible())
		{
			player.dismissMissions();
		}
		else
		{
			System.out.println("I don't see anything");
		}
	}
}