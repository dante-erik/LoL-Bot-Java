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
		rp.setAutoDelay(200);
		
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
		
		//if the second summoner spell couldnt be selected
		//this closes the selection screen
		//enemy summoner 4 (arbitrary location)
		rp.mouseMove(1369, 549);
		rp.mouseClick(1);
		
		//speed up
		rp.setAutoDelay(0);
	}
	
	public void selectFlashHealMovedByRunes()
	{
		//slow down
		rp.setAutoDelay(200);
		
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
		
		//if the second summoner spell couldnt be selected
		//this closes the selection screen
		//enemy summoner 4 (arbitrary location)
		rp.mouseMove(1369, 549);
		rp.mouseClick(1);
		
		//speed up
		rp.setAutoDelay(0);
	}
	
	public void selectHighestRunePage()
	{
		//slow down
		rp.setAutoDelay(200);
		
		//runes edit button
		rp.mouseMove(756, 842);
		rp.mouseClick(1);
		
		//rune pages selection
		rp.mouseMove(611, 284);
		rp.mouseClick(1);
		
		//top editable rune page
		rp.mouseMove(598, 369);
		rp.mouseClick(1);
		
		//speed up
		rp.setAutoDelay(0);
	}
	
	public void editRunePage()
	{
		//slow down
		rp.setAutoDelay(200);
		
		//the pattern of action 1, action 2, action 1 is repeated intentionally
		
		//this pattern is used to deal with the 2 modes for
		//building rune pages the user may have selected
		//and also catches edge cases where riots client is buggy
		//one bug is some runes are in the open state upon opening rune editing
		
		//precision keystone tree icon
		rp.mouseMove(561, 367);
		rp.mouseClick(1);
		
		//if the keystone selection tree wasnt already open, this opens it
		//keystone trees selection
		rp.mouseMove(488, 370);
		rp.mouseClick(1);
		
		//always closes keystone trees selection
		//precision keystone tree icon
		rp.mouseMove(561, 367);
		rp.mouseClick(1);
		
		//fleet footwork keystone
		rp.mouseMove(664, 490);
		rp.mouseClick(1);
		
		//keystones in precision
		rp.mouseMove(488, 488);
		rp.mouseClick(1);
		
		//fleet footwork keystone
		rp.mouseMove(664, 490);
		rp.mouseClick(1);
		
		//triumph
		rp.mouseMove(639, 585);
		rp.mouseClick(1);
		
		//top row
		rp.mouseMove(488, 584);
		rp.mouseClick(1);
		
		//triumph
		rp.mouseMove(639, 585);
		rp.mouseClick(1);
		
		//legend: bloodline
		rp.mouseMove(705, 674);
		rp.mouseClick(1);
		
		//middle row
		rp.mouseMove(488, 674);
		rp.mouseClick(1);
		
		//legend: bloodline
		rp.mouseMove(705, 674);
		rp.mouseClick(1);
		
		//coup de grace
		rp.mouseMove(574, 763);
		rp.mouseClick(1);
		
		//bottom row
		rp.mouseMove(488, 762);
		rp.mouseClick(1);
		
		//coup de grace
		rp.mouseMove(574, 763);
		rp.mouseClick(1);
		
		//resolve secondary tree
		rp.mouseMove(989, 371);
		rp.mouseClick(1);
		
		//secondary tree selection
		rp.mouseMove(813, 371);
		rp.mouseClick(1);
		
		//resolve secondary tree
		rp.mouseMove(989, 371);
		rp.mouseClick(1);
		
		//second wind
		rp.mouseMove(964, 534);
		rp.mouseClick(1);
		
		//top secondary row
		rp.mouseMove(813, 481);
		rp.mouseClick(1);
		
		//second wind
		rp.mouseMove(964, 534);
		rp.mouseClick(1);
		
		//revitalize
		rp.mouseMove(965, 611);
		rp.mouseClick(1);
		
		//middle secondary row
		rp.mouseMove(814, 587);
		rp.mouseClick(1);
		
		//revitalize
		rp.mouseMove(965, 611);
		rp.mouseClick(1);
		
		//attack speed
		rp.mouseMove(965, 674);
		rp.mouseClick(1);
		
		//top stats row
		rp.mouseMove(813, 674);
		rp.mouseClick(1);
		
		//attack speed
		rp.mouseMove(965, 674);
		rp.mouseClick(1);
		
		//attack damage
		rp.mouseMove(899, 716);
		rp.mouseClick(1);
		
		//middle stats row
		rp.mouseMove(814, 716);
		rp.mouseClick(1);
		
		//attack damage
		rp.mouseMove(899, 716);
		rp.mouseClick(1);
		
		//armor
		rp.mouseMove(965, 763);
		rp.mouseClick(1);
		
		//bottom stats row
		rp.mouseMove(814, 763);
		rp.mouseClick(1);
		
		//armor
		rp.mouseMove(965, 763);
		rp.mouseClick(1);
		
		//speed up
		rp.setAutoDelay(0);
	}
	
	public void saveAndExitRunePage()
	{
		//slow down
		rp.setAutoDelay(200);
		
		//save button
		rp.mouseMove(828, 283);
		rp.mouseClick(1);
		
		//rune editing [x] button
		rp.mouseMove(1484, 232);
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
		//speed is not important at the very start of each game
		rp.setAutoDelay(200);
		
		//open shop
		rp.keyClick("p");
		
		//dorans blade
		buyItem("bla");
		
		//warding totem
		buyItem("w");
		
		//close shop with escape, because if an item couldnt be bought, [p] will not close the shop
		//escape is the safest way to close the shop
		rp.keyClick(KeyEvent.VK_ESCAPE);
		
		//speed up
		rp.setAutoDelay(0);
	}
	
	public void buyItems()
	{
		//delayed inside buyItem method
		
		//open shop
		rp.keyClick("p");
		
		//opening shop can be laggy, extra time to load it
		rp.delay(250);
		
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
		rp.keyClick("qa");
		rp.mouseClick(1);
	}
	
	public void castAbilities()
	{
		rp.mouseMove(1403, 160);
		rp.keyClick("wr");
	}
}