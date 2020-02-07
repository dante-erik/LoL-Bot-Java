import java.awt.*;

public class RunWithoutGUI
{
	public static void main(String[] args) throws AWTException
	{
		Dimension d = new Dimension(0,0);
		//1000ms delay is default, 0 RGB tolerance
		Bot asheBot = new Bot(LOLRole.MID, "Ashe", d, 5000, 0);
		asheBot.run();
	}
}