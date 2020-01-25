import java.awt.AWTException;

public class AsheMidBot extends ClientBot
{
	private AsheMidPlayer player;
	private boolean isNewGame;
	//all of these PixelGroups can change based on champion
	private PixelGroup inGame;
	private PixelGroup fullHp;
	private PixelGroup lowHp;
	private PixelGroup qStacksIcon;
	
	public AsheMidBot() throws AWTException
	{
		super();
		
		player = new AsheMidPlayer();
		
		isNewGame = false;
		
		inGame = new PixelGroup(new Pixel(730, 1075, 26, 52, 53));
		fullHp = new PixelGroup(new Pixel(1094, 1044, 8, 210, 0));
		lowHp = new PixelGroup(new Pixel(895, 1045, 11, 97, 17));
		qStacksIcon = new PixelGroup(new Pixel(652, 895, 29, 53, 94));
	}
	
	public void tick()
	{
		//check if inGame first, most important PixelGroup
		if(inGame.isVisible())
		{
			if(isNewGame)
			{
				System.out.println("waiting 5 sec before buying items");
				player.delay(5000);
				player.buyStartingItems();
				player.upgradeRQWE();
				player.lockCamera();
				System.out.println("waiting 1 minute for minions to spawn");
				player.delay(60000);
				isNewGame = false;
			}
			//stay in inGame cycle and avoid re-evaluating if(startingNewGame)
			else while(inGame.isVisible())
			{
				//if lowHp is not visible, go back to base and buy items
				if(!lowHp.isVisible())
				{
					player.useFlashHeal();
					player.retreat();
					player.buyItems();
					player.upgradeRQWE();
				}
				//if not fullHp, but above lowHp, must be in lane taking some kind of damage, so cast abilities will hit enemy
				else if(!fullHp.isVisible() || qStacksIcon.isVisible())
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
		else if(championSelect.isVisible())
		{
			//pauses program until 1 of 4 things is visible, in order of most to least common
			//1: champion search box (default)
			//2: accept match button (if someone dodged)
			//3: start queue button (if someone dodged and the bot couldnt accept next game)
			//4: in game (if by some unholy miracle the bot accidentally picked a champ and entered a game)
			while(!championSearchBox.isVisible() && !acceptMatchButton.isVisible() && !startQueueButton.isVisible() && !inGame.isVisible())
				System.out.println("champion search box not visible");
			
			player.selectAshe();
			
			player.callMid();
			
			//different methods for flash and heal / flash and heal with runes because
			//their positions change when runes are visible
			if(runesTab.isVisible())
			{
				player.selectFlashHealMovedByRunes();
				player.selectHighestRunePage();
				//if runes are not locked, edit runes (preset pages are locked, edit unlocks at lvl 10)
				if(!runesLocked.isVisible())
					player.editRunePage();
				player.saveAndExitRunePage();
			}
			else
			{
				player.selectFlashHeal();
			}
			
			player.lockIn();
			
			//if championSelect is reached, the bot has finished its previous game, so it must be entering a new game
			isNewGame = true;
		}
		else
		{
			//if the bot is not in game or in champ select, it must be in the client or load screen
			//client actions are all handled by ClientBot, so ClientBot's tick method is called
			super.tick();
		}
	}
}
