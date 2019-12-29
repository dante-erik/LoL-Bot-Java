import java.awt.AWTException;

public class AsheMidBot extends ClientBot
{
	AsheMidPlayer ashe;
	private boolean isNewGame;
	//all of these PixelGroups can change based on champion
	private PixelGroup inGame;
	private PixelGroup fullHp;
	private PixelGroup highHp;
	private PixelGroup lowHp;
	
	public AsheMidBot() throws AWTException
	{
		super();
		
		ashe = new AsheMidPlayer();
		isNewGame = true;
		
		inGame = new Pixel(0,0,0,0,0);
		fullHp = new Pixel(0,0,0,0,0);
		highHp = new Pixel(0,0,0,0,0);
		lowHp = new Pixel(0,0,0,0,0);
	}
	
	public void tick()
	{
		//check if inGame first, makes bot run fast while playing
		if(inGame.isVisible())
		{
			if(isNewGame)
			{
				ashe.buyStartingItems();
				ashe.delay(60000);
				isNewGame = false;
			}
			//stay in inGame cycle and avoid re-evaluating if(startingNewGame)
			else while(inGame.isVisible())
			{
				if(lowHp.isVisible())
				{
					ashe.useSummonerSpells();
					ashe.retreat();
					ashe.buyItems();
				}
				else if(highHp.isVisible())
				{
					ashe.castSpells();
					ashe.attack();
				}
				else if(fullHp.isVisible())
				{
					ashe.attack();
				}
			}
		}
		//if not inGame, check if in championSelect, locking champ before it's stolen is important
		//if champ gets stolen before the bot can select it, the bot will dodge by not picking a champ
		//championSelect and loadScreen are in ClientBot because they are not champion specific
		else if(championSelect.isVisible())
		{
			ashe.pickChampion();
			ashe.callRole();
			ashe.selectSummonerSpells();
			//if championSelect is reached, the bot has finished its previous game
			isNewGame = true;
			
			//halts the program until championSelect is finished
			//avoids repeating callRole()
			while(!loadScreen.isVisible())
				System.out.println("Waiting to finish champion select");
			
			System.out.println("Finished champion select");
			
			//halts the program until loadScreen is finished
			//explains what the bot is seeing
			while(loadScreen.isVisible())
				System.out.println("Waiting to finish loading into game");
			
			System.out.println("Finished loading into game");
		}
		else
		{
			//if the bot is not in game or in champ select, it must be in the client or load screen
			//client actions are all handled by ClientBot, so ClientBot's tick method is called
			super.tick();
		}
	}
}