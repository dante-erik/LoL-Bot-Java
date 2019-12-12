public class PixelGroup
{
	private ArrayList<Pixel> pixels = new ArrayList<Pixel>();
	private boolean visible;
	
	//PixelGroups must be given an ArrayList of Pixel to be created
	public PixelGroup(ArrayList<Pixel> p)
	{
		pixels.addAll(p);
		visible = false;
	}
	
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
			if(!equalToScreen(pixels.get(i)))
			{
				visible = false;
				//loop is ended so the remaining pixels are not scanned, efficient
				i = pixels.size();
			}
		}
	}
	
	//return boolean of current state
	public boolean isVisible()
	{
		updateBoolean();
		
		return visible;
	}
}