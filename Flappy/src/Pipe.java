public class Pipe
{
	private int top; //bottom left top
	public static int empty = 150; //empty space in pixels
	private int bottom; //top left of top
	private int pipeX;

	public Pipe(int h, int x)
	{
		pipeX = x;
		top = h;
		bottom = h + empty;
	}

	public int getTop()
	{
		return top;
	}

	public int getBottom()
	{
		return bottom;
	}

	public int getPipeX()
	{
		return pipeX;
	}
	
	public void setPipeX(double subtract)
	{
		pipeX -= subtract;
	}

}