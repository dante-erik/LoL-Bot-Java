import java.awt.AWTException;

public class AsheMidPlayer
{
	private RobotPlus rp = new RobotPlus();
	
	public AsheMidPlayer() throws AWTException {}
	
	public void pickChampion()
	{
		rp.mouseMove(textbox, textbox);
		rp.mouseClick(1);
		rp.keyClick("ashe");
		rp.mouseMove(asheicon, asheicon);
		rp.mouseClick(1);
		rp.mouseMove(lockin, lockin);
		rp.mouseClick(1);
	}
	
	public void callRole()
	{
		rp.mouseMove(chatbox, chatbox);
		rp.mouseClick(1);
		rp.keyClick("mid");
	}
	
	public void selectSummonerSpells()
	{
		rp.mouseMove(leftsummonerspell, leftsummonerspell);
		rp.mouseClick(1);
		rp.mouseMove(flash, flash);
		rp.mouseMove(rightsummonerspell, rightsummonerspell);
		rp.mouseClick(1);
		rp.mouseMove(heal, heal);
	}
	
	public void useSummonerSpells()
	{
		rp.mouseMove(safespot, safespot);
		rp.keyClick("df");
	}
	
	public void retreat()
	{
		rp.mouseMove(baseonminimap, baseonminimap);
		rp.mouseClick(2);
	}
	
	public void buyItems()
	{
		rp.keyClick("p");
		rp.mouseMove(bers, bers);
		rp.mouseClick(2);
		rp.mouseMove(boots, boots);
		rp.mouseClick(2);
		rp.mouseMove(bork, bork);
		rp.mouseClick(2);
		rp.mouseMove(cutlass, cutlass);
		rp.mouseClick(2);
		rp.mouseMove(rec, rec);
		rp.mouseClick(2);
		rp.keyClick("p");
	}
	
	public void attack()
	{
		rp.mouseMove(enemybase, enemybase);
		rp.keyClick("a");
		rp.mouseClick(1);
	}
	
	public void castSpells()
	{
		rp.mouseMove(attackspot, attackspot);
		rp.keyClick("qwr");
	}
}