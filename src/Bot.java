import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
		if(role == LOLRole.TOP)
		{
			throw new UnsupportedOperationException();
		}
		else if(role == LOLRole.JUNGLE)
		{
			throw new UnsupportedOperationException();
		}
		else if(role == LOLRole.MID)
		{
			if(championName.equals("Ashe"))
				bot = new AsheMidBot();
			else
				throw new UnsupportedOperationException();
		}
		else if(role == LOLRole.ADC)
		{
			throw new UnsupportedOperationException();
		}
		else if(role == LOLRole.SUPPORT)
		{
			throw new UnsupportedOperationException();
		}
		else
		{
			throw new UnsupportedOperationException();
		}
	}
	
	public void run() throws AWTException
	{
		while(running.get())
			bot.tick();
	}
}