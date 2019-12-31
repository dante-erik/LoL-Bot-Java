import java.awt.AWTException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.Dimension;

public class Bot
{
	public AtomicBoolean running;
	
	private ClientBot bot;
	
	public Bot(LOLRole role, String championName, Dimension clientRes) throws AWTException
	{
		setupBot(role, championName, clientRes);
		running = new AtomicBoolean(true);
	}
	
	public void setupBot(LOLRole role, String championName, Dimension clientRes) throws AWTException
	{
		switch(role)
		{
			case TOP:
				throw UnsupportedOperationException;
				break;
				
			case JUNGLE:
				if(championName.equals("Warwick"))
					bot = new WarwickJungleBot();
				else
					throw UnsupportedOperationException
				break;
				
			case MID:
				if(championName.equals("Ashe"))
					bot = new AsheMidBot();
				else
					throw UnsupportedOperationException
				break;
			
			case ADC:
				throw UnsupportedOperationException;
				break;
				
			case SUPPORT:
				if(championName.equals("Yuumi"))
					bot = new YuumiSupportBot();
				else
					throw UnsupportedOperationException
				break;
				
			default:
				throw UnsupportedOperationException;
				break;
		}
	}
	
	public void run() throws AWTException
	{
		while(running.get())
			bot.tick();
	}
}