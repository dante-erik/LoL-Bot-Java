import java.util.ArrayList;

public class PixelGroup
{
	private ArrayList<Pixel> pixels = new ArrayList<Pixel>();
	private boolean visible;
	
	//default constructor has no Pixels
	public PixelGroup()
	{
		visible = false;
	}
	
	//PixelGroups given an ArrayList<Pixel> to add to ArrayList<Pixel> pixel
	public PixelGroup(ArrayList<Pixel> p)
	{
		pixels.addAll(p);
		visible = false;
	}
	
	//add a Pixel to the ArrayList<Pixel> pixels
	public void add(Pixel p)
	{
		pixels.add(p);
	}
	
	/*
	//updates the boolean visible of the PixelGroup
	//visible becomes true if ALL Pixels in the PixelGroup are visible on the screen
	public void updateBoolean()
	{
		//true until proven false
		visible = true;
		
		//check if each Pixel is visible on the screen
		for(int i = 0; i < pixels.size(); i++)
		{
			//if the pixel being scanned is not visible, visible is false
			if(!pixels.get(i).equalToScreen())
			{
				visible = false;
				//loop is ended so the remaining pixels are not scanned, efficient
				i = pixels.size();
			}
		}
	}
	*/
	
	//updates the boolean visible of the PixelGroup
	//visible becomes true if ALL Pixels in the PixelGroup are visible with a tolerance for N difference in RGB values
	public void updateBoolean(int tolerance)
	{
		//true until proven false
		visible = true;
		
		//check if each Pixel is visible on the screen
		for(int i = 0; i < pixels.size(); i++)
		{
			//if the pixel being scanned is not visible, visible is false
			if(!pixels.get(i).equalToScreen(tolerance))
			{
				visible = false;
				//loop is ended so the remaining pixels are not scanned, efficient
				i = pixels.size();
			}
		}
	}
	
	//return boolean isVisible after updating boolean with 0 tolerance
	public boolean isVisible()
	{
		updateBoolean(0);
		
		return visible;
	}
	
	
	//return boolean isVisible after updating boolean with N tolerance
	public boolean isVisible(int tolerance)
	{
		updateBoolean(tolerance);
		
		return visible;
	}
	
	public String toString()
	{
		String str = "";
		
		for(int i = 0; i < pixels.size(); i++)
			str += i+": "+pixels.get(i)+"\n";
		
		return str;
	}
}