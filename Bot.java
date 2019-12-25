public class Bot
{
	private Champion champ;
	private PixelGroup startQueueButton = new PixelGroup();
	private PixelGroup acceptMatchButton = new PixelGroup();
	private PixelGroup championSelect = new PixelGroup();
	private PixelGroup flashHeal = new PixelGroup();
	private PixelGroup flashSmite = new PixelGroup();
	private PixelGroup loadScreen = new PixelGroup();
	private PixelGroup inGame = new PixelGroup();
	private PixelGroup fullHp = new PixelGroup();
	private PixelGroup highHp = new PixelGroup();
	private PixelGroup lowHp = new PixelGroup();
	private PixelGroup honorSelect = new PixelGroup();
	private PixelGroup playAgainButton = new PixelGroup();
	
	public Bot(LoLRole role, String championName)
	{
		setup(role, championName);
		run();
	}
	
	public void setup(LOLRole role, String championName)
	{
		switch(role)
		{
			case TOP:
				throw UnsupportedOperationException;
				break;
				
			case JUNGLE:
				if(championName.equals("Warwick")
					champ = new WarwickJungle();
				break;
				
			case MID:
				if(championName.equals("Ashe")
					champ = new AsheMid();
				break;
			
			case ADC:
				throw UnsupportedOperationException;
				break;
				
			case SUPPORT:
				if(championName.equals("Yuumi")
					champ = new YuumiSupport();
				break;
				
			default
				throw UnsupportedOperationException;
				break;
		}
		
		startQueueButton.add(new Pixel());
		acceptMatchButton.add(new Pixel());
		championSelect.add(new Pixel());
		flashHeal.add(new Pixel());
		flashSmite.add(new Pixel());
		loadScreen.add(new Pixel());
		inGame.add(new Pixel());
		fullHp.add(new Pixel());
		highHp.add(new Pixel());
		lowHp.add(new Pixel());
		honorSelect.add(new Pixel());
		playAgainButton.add(new Pixel());
	}
	
	public void run()
	{
		while(true)
		{
			if(inGame.isVisible())
			{
				if(lowHp.isVisible())
				{
					champ.retreat();
					champ.useSummonerSpells();
					champ.recall();
				}
				else if(highHp.isVisible())
				{
					champ.attack();
					champ.castSpells();
				}
				else if(fullHp.isVisible())
				{
					champ.attack();
				}
			}
			else
			{
				if(championSelect.isVisible())
				{
					champ.pickChampion();
					champ.callRole();
					champ.selectSummonerSpells();
				
					while(!loadScreen.isVisible())
					{
						System.out.println("Waiting to enter load screen");
					}
				
					while(loadScreen.isVisible())
					{
						System.out.println("Waiting to enter game");
					}
				}
				else if(acceptMatchButton.isVisible())
				{
					champ.acceptMatch();
				}
				else if(startQueueButton.isVisible())
				{
					champ.startQueue();
				}
				else if(playAgainButton.isVisible())
				{
					champ.playAgain();
				}
				else if(honorSelect.isVisible())
				{
					champ.honorTeammate();
				}
			}
		}
	}
}