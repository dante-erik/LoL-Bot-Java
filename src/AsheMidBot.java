import java.awt.AWTException;

public class AsheMidBot extends ClientBot
{
	private AsheMidPlayer player;
	
	private int globalDelayMultiplier;
	private int globalRGBTolerance;
	
	private int lockInDelay;
	private int championSelectPrintDelay;
	
	private boolean isNewGame;
	
	//all of these PixelGroups can change based on champion
	private PixelGroup inGame;
	private PixelGroup fullHp;
	private PixelGroup lowHp;
	private PixelGroup qStacksIcon;
	
	public AsheMidBot(int globalDelayMultiplier, int globalRGBTolerance) throws AWTException
	{
		super(globalDelayMultiplier, globalRGBTolerance);
		
		player = new AsheMidPlayer(globalDelayMultiplier);
		
		this.globalDelayMultiplier = globalDelayMultiplier;
		this.globalRGBTolerance = globalRGBTolerance;
		
		lockInDelay = (int)(1.5 * globalDelayMultiplier);
		championSelectPrintDelay = (int)(1.0 * globalDelayMultiplier);
		
		isNewGame = false;
		
		inGame = new PixelGroup(new Pixel(1106, 1011, 16, 31, 30));
		fullHp = new PixelGroup(new Pixel(1092, 1032, 9, 193, 11));
		lowHp = new PixelGroup(new Pixel(895, 1030, 12, 123, 16));
		qStacksIcon = new PixelGroup(new Pixel(664, 839, 255, 255, 255));
	}
	
	public void tick()
	{
		//check if inGame first, most important PixelGroup
		if(inGame.isVisible(globalRGBTolerance))
		{
			if(isNewGame)
			{
				System.out.println("waiting 4 sec before buying items");
				player.delay(4000);
				player.buyStartingItems();
				player.upgradeRQWE();
				player.lockCamera();
				System.out.println("waiting 1 minute for minions to spawn");
				player.delay(60000);
				isNewGame = false;
			}
			//stay in inGame cycle and avoid re-evaluating if(startingNewGame)
			else while(inGame.isVisible(globalRGBTolerance))
			{
				//if lowHp is not visible, go back to base and buy items
				if(!lowHp.isVisible(globalRGBTolerance))
				{
					player.useFlashHeal();
					player.retreat();
					player.buyItems();
					player.upgradeRQWE();
				}
				//if not fullHp, but above lowHp, must be in lane taking some kind of damage, so cast abilities will hit enemy
				else if(!fullHp.isVisible(globalRGBTolerance) || qStacksIcon.isVisible(globalRGBTolerance))
				{
					player.castAbilities();
					player.attack();
					player.upgradeRQWE();
				}
				//if fullHp is visible, this will be reached, no point in re-evaluating
				else
				{
					player.attack();
					player.upgradeRQWE();
				}
			}
		}
		//if not inGame, check if in championSelect, locking champ before it's stolen is important
		//if champ gets stolen before the bot can select it, the bot will dodge by not picking a champ
		//championSelect and loadScreen are in ClientBot because they are not champion specific
		else if(championSelect.isVisible(globalRGBTolerance))
		{
			//pauses bot when in champion select and the search box is not visible
			while(!championSearchBox.isVisible(globalRGBTolerance) && championSelect.isVisible(globalRGBTolerance))
				System.out.println("champion search box not visible");
			
			player.selectAshe();
			
			player.callMid();
			
			//unlocks at level 8
			if(runesNowUnlockedNotification.isVisible(globalRGBTolerance))
				player.closeUnlockNotification();
			
			//different methods for flash and heal / flash and heal with runes because
			//their positions change when runes are visible
			if(runesTab.isVisible(globalRGBTolerance))
			{
				player.selectFlashHealMovedByRunes();
				player.selectHighestRunePage();
				//if runes are not locked, edit runes (preset pages are locked, edit unlocks at lvl 10)
				if(!runesLocked.isVisible(globalRGBTolerance))
					player.editRunePage();
				player.saveAndExitRunePage();
			}
			else
			{
				player.selectFlashHeal();
			}
			
			player.lockIn();
			//lock in lags, so delay 1 sec before continuing to scan screen
			//fixes rune selection bug, champ select phase would rune more than once
			player.delay(lockInDelay);
			
			//if championSelect is reached, the bot has finished its previous game, so it must be entering a new game
			isNewGame = true;
			
			while(championLockedIn.isVisible(globalRGBTolerance) && championSelect.isVisible(globalRGBTolerance))
			{
				System.out.println("waiting for champion select to end");
				//delay reduces lag
				player.delay(championSelectPrintDelay);
			}
		}
		else
		{
			//if the bot is not in game or in champ select, it must be in the client or load screen
			//client actions are all handled by ClientBot, so ClientBot's tick method is called
			super.tick();
		}
	}
}
