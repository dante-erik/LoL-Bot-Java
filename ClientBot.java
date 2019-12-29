import java.awt.AWTException;

public class ClientBot
{
	ClientPlayer player;
	//all of these PixelGroups do not change based on champion
	private PixelGroup startQueueButton;
	private PixelGroup acceptMatchButton;
	private PixelGroup championSelect;
	private PixelGroup loadScreen;
	private PixelGroup honorSelect;
	private PixelGroup playAgainButton;
	
	public ClientBot() throws AWTException
	{
		player = new ClientPlayer();
		
		startQueueButton = new Pixel(0,0,0,0,0);
		acceptMatchButton = new Pixel(0,0,0,0,0);
		championSelect = new Pixel(0,0,0,0,0);
		loadScreen = new Pixel(0,0,0,0,0);
		honorSelect = new Pixel(0,0,0,0,0);
		startQueueButton = new Pixel(0,0,0,0,0);
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
	}
}