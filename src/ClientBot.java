import java.awt.*;

public class ClientBot
{
	ClientPlayer player;
	
	//if bot is running too fast for the PC, increase the delay between actions, default is 1000ms
	private int globalDelayMultiplier;
	
	//how accurate visible pixels must match the PixelGroups' pixels
		//tolerance = 0 means PixelGroups must match EXACTLY for each RGB on the screen
		//tolerance > 0 means PixelGroups must be within tolerance +- for each RGB on the screen
	private int globalTolerance;
	
	private int dismissProblemSelectingChampionDelay;
	
	//all of these PixelGroups do not change based on champion
	//these are protected because they are used in ChampionRoleBot files
	protected PixelGroup championSelect;
	protected PixelGroup championLockedIn;
	protected PixelGroup runesTab;
	protected PixelGroup runesLocked;
	protected PixelGroup runesNowUnlockedNotification;
	protected PixelGroup championSearchBox;
	protected PixelGroup acceptMatchButton;
	protected PixelGroup startQueueButton;
	private PixelGroup problemSelectingChampionOrRunesFailedToSave;
	private PixelGroup errorSettingSummonerSpells;
	private PixelGroup loadScreen;
	private PixelGroup honorSelect;
	private PixelGroup playAgainButton;
	private PixelGroup cannotCreateCustomRunes;
	private PixelGroup dailyReward;
	private PixelGroup levelUp;
	private PixelGroup missions;

	public ClientBot(int globalDelayMultiplier, int globalTolerance) throws AWTException
	{
		player = new ClientPlayer(globalDelayMultiplier);
		
		this.globalDelayMultiplier = globalDelayMultiplier;
		this.globalTolerance = globalTolerance;
		
		dismissProblemSelectingChampionDelay = (int)(.1 * globalDelayMultiplier);
		
		championSelect = new PixelGroup(new Pixel(669, 223, 94, 71, 29));
		championLockedIn = new PixelGroup(new Pixel(658, 373, 205, 190, 145));
		runesTab = new PixelGroup(new Pixel(1179, 860, 90, 89, 85));
		runesLocked = new PixelGroup(new Pixel(512, 397, 170, 170, 170));
		runesNowUnlockedNotification = new PixelGroup(new Pixel(1012, 738, 205, 190, 145));
		championSearchBox = new PixelGroup(new Pixel(1146, 264, 3, 8, 8));
		acceptMatchButton = new PixelGroup(new Pixel(994, 361, 33, 77, 98));
		startQueueButton = new PixelGroup(new Pixel(635, 585, 26, 55, 32));
		problemSelectingChampionOrRunesFailedToSave = new PixelGroup(new Pixel(855, 497, 93, 94, 89));
		errorSettingSummonerSpells = new PixelGroup(new Pixel(1106, 568, 96, 73, 31));
		loadScreen = new PixelGroup(new Pixel(955, 578, 57, 53, 50));
		honorSelect = new PixelGroup(new Pixel(882, 216, 225, 230, 210));
		playAgainButton = new PixelGroup(new Pixel(1128, 731, 12, 20, 18));
		cannotCreateCustomRunes = new PixelGroup(new Pixel(1074, 503, 132, 130, 119));
		dailyReward = new PixelGroup(new Pixel(323, 296, 50, 40, 30));
		levelUp = new PixelGroup(new Pixel(1017, 314, 238, 228, 208));
		missions = new PixelGroup(new Pixel(1263, 353, 86, 66, 35));
	}
	
	public void tick()
	{
		if(acceptMatchButton.isVisible(globalTolerance))
		{
			System.out.println("accept match");
			
			player.acceptMatch();
		}
		else if(startQueueButton.isVisible(globalTolerance))
		{
			System.out.println("start queue");
			
			player.startQueue();
		}
		else if(playAgainButton.isVisible(globalTolerance))
		{
			System.out.println("play again");
			
			player.playAgain();
		}
		else if(honorSelect.isVisible(globalTolerance))
		{
			System.out.println("honor teammate");
			
			player.honorTeammate();
		}
		else if(levelUp.isVisible(globalTolerance))
		{
			System.out.println("level up");
			
			player.dismissLevelUp();
		}
		else if(dailyReward.isVisible(globalTolerance))
		{
			System.out.println("accept daily reward");
			
			player.acceptDailyReward();
			
			//if accepting doesnt work, bypass the daily reward screen
			if(dailyReward.isVisible(globalTolerance))
			{
				System.out.println("bypass daily reward");
				
				player.bypassDailyReward();
			}
		}
		else if(missions.isVisible(globalTolerance))
		{
			System.out.println("mission complete");
			
			player.dismissMissions();
		}
		else if(problemSelectingChampionOrRunesFailedToSave.isVisible(globalTolerance))
		{
			//problem selecting champion is more common
			//so it is assumed before rune save failure
			System.out.println("problem selecting champion");
			
			player.dismissProblemSelectingChampion();
			
			//delay so the client can register dismissProblemSelectingChampion
			player.delay(dismissProblemSelectingChampionDelay);
			
			//true when runes fail to save
			//same pixels visible for runes and champion error messages
			if(problemSelectingChampionOrRunesFailedToSave.isVisible(globalTolerance))
			{
				System.out.println("rune save failed");
				
				//this will almost never run, edge case
				//bot chooses to not save runes, safest choice
				player.declineRunePageSaveRequest();
			}
		}
		else if(cannotCreateCustomRunes.isVisible(globalTolerance))
		{
			System.out.println("cannot create custom runes");
			
			player.dismissCannotCreateCustomRunes();
			//only runs if a different part of the LoL-Bot fails
			//runs when champion select sees wrong pixels
			
			//this will almost never run, edge case
		}
		else if (errorSettingSummonerSpells.isVisible(globalTolerance))
		{
			System.out.println("error setting summoner spells");
			
			player.dismissErrorSettingSummonerSpells();
		}
		else if(loadScreen.isVisible(globalTolerance))
		{
			//player.cursorDance();
			System.out.println("load screen");
		}
		else
		{
			System.out.println("nothing visible");
		}
	}
}