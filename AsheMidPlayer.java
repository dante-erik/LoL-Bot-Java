import java.awt.AWTException;
import java.awt.event.KeyEvent;

public class AsheMidPlayer extends Champion
{
	public AsheMidPlayer() throws AWTException
	{
		super();
	}
	
	public void selectChampion()
	{
		super.selectChampion("ashe");
	}
	
	public void callRole()
	{
		super.callRole("mid");
	}
	
	public void selectSummonerSpells()
	{
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
	}
	
	public void useSummonerSpells()
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
		buy("bla");
		
		//warding totem
		buy("w");
		
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
		buy("ber");
		
		//boots of speed
		buy("bo");
		
		//blade of ruined king
		buy("rui");
		
		//cutlass
		buy("lg");
		
		//recurve bow
		buy("rv");
		
		//vampiric scepter
		buy("sc");
		
		//long sword
		buy("sw");
		
		//dagger
		buy("gg");

		//close shop with escape, because if an item couldnt be bought, [p] will not close the shop
		//escape is the safest way to close the shop
		rp.keyClick(KeyEvent.VK_ESCAPE);
		
		//speeds bot back up
		rp.setAutoDelay(0);
	}
	
	public void upgradeAbilities()
	{
		rp.keyPress(KeyEvent.VK_CONTROL);
		rp.keyClick("rqwe");
		rp.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public void attack()
	{
		rp.mouseMove(1883, 840);
		rp.keyClick("a");
		rp.mouseClick(1);
	}
	
	public void castSpells()
	{
		rp.mouseMove(1076, 213);
		rp.keyClick("qwr");
	}
}