package rbadia.voidspace.model;

public class PowerUp_Life extends GameObject {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 45;
	public static final int HEIGHT = 20;
	private boolean visibility;

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public PowerUp_Life(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
	}   
	
}



