import java.awt.*;

public class RunWithoutGUI
{
	public static void main(String[] args) throws AWTException
	{
		Dimension d = new Dimension(0,0);
		Bot asheBot = new Bot(LOLRole.MID, "Ashe", d);
		asheBot.run();
	}
}