import java.awt.*;
import java.awt.event.KeyEvent;

public class AsheMidPlayer extends Champion
{
	public AsheMidPlayer() throws AWTException
	{
		super();
	}
	
	public void selectAshe()
	{
		super.selectChampion("ashe");
	}
	
	public void callMid()
	{
		super.callRole("mid");
	}
	
	public void selectFlashHeal()
	{
		//slow down
		rp.setAutoDelay(100);
		
		//left summoner spell
		rp.mouseMove(888, 842);
		rp.mouseClick(1);
		
		//flash icon
		rp.mouseMove(943, 636);
		rp.mouseClick(1);
		
		//right summoner spell
		rp.mouseMove(935, 841);
		rp.mouseClick(1);
		
		//heal icon
		rp.mouseMove(823, 699);
		rp.mouseClick(1);
		
		//if the second summoner spell couldnt be selected, this closes the selection screen
		//enemy summoner 4, arbitrary
		rp.mouseMove(1369, 549);
		rp.mouseClick(1);
		
		//speed up
		rp.setAutoDelay(0);
	}
	
	public void selectRunesFlashHeal()
	{
		//slow down
		rp.setAutoDelay(100);
		
		//runes tab
		rp.mouseMove(873, 845);
		rp.mouseClick(1);
		
		//preset precision runes
		rp.mouseMove(877, 739);
		rp.mouseClick(1);
		
		//left summoner spell
		rp.mouseMove(1009, 843);
		rp.mouseClick(1);
		
		//flash icon
		rp.mouseMove(1064, 636);
		rp.mouseClick(1);
		
		//right summoner spell
		rp.mouseMove(1056, 843);
		rp.mouseClick(1);
		
		//heal icon
		rp.mouseMove(945, 698);
		rp.mouseClick(1);
		
		//if the second summoner spell couldnt be selected, this closes the selection screen
		//enemy summoner 4, arbitrary
		rp.mouseMove(1369, 549);
		rp.mouseClick(1);
		
		//speed up
		rp.setAutoDelay(0);
	}
	
	public void useFlashHeal()
	{
		rp.mouseMove(321, 888);
		rp.keyClick("df");
	}
	
	public void retreat()
	{
		rp.mouseMove(1658, 1065);
		rp.mouseClick(2);
	}
	
	public void buyStartingItems()
	{
		rp.setAutoDelay(100);
		
		//open shop
		rp.keyClick("p");
		
		//dorans blade
		buyItem("bla");
		
		//warding totem
		buyItem("w");
		
		//close shop with escape, because if an item couldnt be bought, [p] will not close the shop
		//escape is the safest way to close the shop
		rp.keyClick(KeyEvent.VK_ESCAPE);
		
		rp.setAutoDelay(0);
	}
	
	public void buyItems()
	{
		//slows bot down to buy items
		rp.setAutoDelay(100);
		
		//open shop
		rp.keyClick("p");
		
		//berserkers greaves
		buyItem("ber");
		
		//boots of speed
		buyItem("bo");
		
		//blade of ruined king
		buyItem("rui");
		
		//cutlass
		buyItem("lg");
		
		//recurve bow
		buyItem("rv");
		
		//vampiric scepter
		buyItem("sc");
		
		//long sword
		buyItem("sw");
		
		//dagger
		buyItem("gg");

		//close shop with escape, because if an item couldnt be bought, [p] will not close the shop
		//escape is the safest way to close the shop
		rp.keyClick(KeyEvent.VK_ESCAPE);
		
		//speeds bot back up
		rp.setAutoDelay(0);
	}
	
	public void upgradeRQWE()
	{
		rp.keyPress(KeyEvent.VK_CONTROL);
		rp.keyClick("rqwe");
		rp.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public void attack()
	{
		rp.mouseMove(1883, 844);
		rp.keyClick("a");
		rp.mouseClick(1);
	}
	
	public void castAbilities()
	{
		rp.mouseMove(1403, 211);
		rp.keyClick("qwr");
	}
}