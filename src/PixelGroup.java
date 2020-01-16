import java.util.ArrayList;

public class PixelGroup
{
	private ArrayList<Pixel> pixels = new ArrayList<Pixel>();
	
	public PixelGroup(Pixel p)
	{
		pixels.add(p);
	}
	
	//PixelGroups given an ArrayList<Pixel> to add to ArrayList<Pixel> pixel
	public PixelGroup(ArrayList<Pixel> p)
	{
		pixels.addAll(p);
	}
	
	//add a Pixel to the ArrayList<Pixel> pixels
	public void add(Pixel p)
	{
		pixels.add(p);
	}
	
	//return boolean isVisible after updating boolean with 0 tolerance
	public void isVisible()
	{
		isVisible(0);
	}
	
	//return boolean isVisible after updating boolean with N tolerance
	public boolean isVisible(int tolerance)
	{
		//check if each Pixel is visible on the screen
		for(int i = 0; i < pixels.size(); i++)
		{
			//if the pixel being scanned is not visible, visible is false
			if(!pixels.get(i).isVisible(tolerance))
			{
				return false;
			}
		}
		
		//all pixels were visible, return true
		return true;
	}
	
	public String toString()
	{
		String str = "";
		
		for(int i = 0; i < pixels.size(); i++)
			str += i+": "+pixels.get(i)+"\n";
		
		return str;
	}
}