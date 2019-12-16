public class Bot
{
	private Champion champ;
	
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
		
		//setup all pixel groups here (?)
	}
	
	public void run()
	{
		while(true)
		{
			//check all PixelGroups with else if chain, make champ play game
		}
	}
}