import java.awt.*;

public class ClientBot
{
	ClientPlayer player;
	//all of these PixelGroups do not change based on champion
	//these are protected because they are used in ChampionRoleBot files
	protected PixelGroup championSelect;
	protected PixelGroup acceptMatchButton;
	protected PixelGroup runesTab;
	protected PixelGroup runesFailedToSave;
	private PixelGroup loadScreen;
	private PixelGroup startQueueButton;
	private PixelGroup honorSelect;
	private PixelGroup playAgainButton;
	private PixelGroup dailyReward;
	private PixelGroup levelUp;
	private PixelGroup missions;

	public ClientBot() throws AWTException
	{
		player = new ClientPlayer();

		championSelect = new PixelGroup(new Pixel(1148, 749, 3, 58, 70));
		acceptMatchButton = new PixelGroup(new Pixel(994, 361, 33, 77, 98));
		runesTab = new PixelGroup(new Pixel(1179, 860, 90, 89, 85));
		runesFailedToSave = new PixelGroup(new Pixel(855, 497, 93, 94, 89));
		loadScreen = new PixelGroup(new Pixel(955, 578, 57, 53, 50));
		startQueueButton = new PixelGroup(new Pixel(635, 585, 26, 55, 32));
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
		else if(runesFailedToSave.isVisible())
		{
			player.declineSaveRunesRequest();
			//decline because it's likely the bot messed up and
			//left rune slots open, better to go with a filled in but
			//possibly non-optimal rune page than an incomplete one
			
			//this will almost never run, edge case
		}
		else if(loadScreen.isVisible())
		{
			//player.cursorDance();
			System.out.println("I see load screen");
		}
		else
		{
			System.out.println("I don't see anything");
		}
	}
}