public class PixelGroup
{
	private ArrayList<Pixel> pixels = new ArrayList<Pixel>();
	private boolean state;
	
	//PixelGroups must be given an ArrayList of Pixel to be created
	public PixelGroup(ArrayList<Pixel> p)
	{
		pixels.addAll(p);
		state = false;
	}
	
	//updates the state of the PixelGroup
	//state becomes true if ALL Pixels in the PixelGroup are visible on the screen
	public void update()
	{
		//true until proven false
		state = true;
		
		//check if each Pixel is visible on the screen
		for(int i = 0; i < pixels.size(); i++)
			if(!equalToScreen(pixels.get(i)))
				state = false;
	}
	
	//return boolean of current state
	public boolean getState()
	{
		return state;
	}
}