package rbadia.voidspace.main;
import java.awt.Graphics2D;
import java.util.Random;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.model.PowerUp_Life;
import rbadia.voidspace.sounds.SoundManager;

public class Level3State extends Level1State {

	private static final long serialVersionUID = -2094575762243216079L;
	protected Random random = new Random();
	protected Platform platform;
	int change = 0; //Variable used in drawPlatforms

	// Constructors
	public Level3State(int level, MainFrame frame, GameStatus status, 
			LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
	}


	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
	}

	@Override
	public void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		//First Asteroid
		if((asteroid.getX() + asteroid.getPixelsWide() >  0)) {

			asteroid.translate(-(asteroid.getSpeed() + random.nextInt(2) + 1), asteroid.getSpeed()/2);
			getGraphicsManager().drawAsteroid(asteroid, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				asteroid.setLocation(SCREEN_WIDTH - asteroid.getPixelsWide(),
						rand.nextInt(SCREEN_HEIGHT - asteroid.getPixelsTall() - 32));
			}
			else {
				// draw explosion
				getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	
		//Second Asteroid
		if((asteroid2.getX() + asteroid2.getPixelsWide() >  0)) {

			asteroid2.translate(-(asteroid2.getSpeed() + random.nextInt(2) + 1), asteroid2.getSpeed()/2);
			getGraphicsManager().drawAsteroid(asteroid2, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime2) > NEW_ASTEROID_DELAY){

				asteroid2.setLocation(SCREEN_WIDTH - asteroid2.getPixelsWide(),
						rand.nextInt(SCREEN_HEIGHT - asteroid2.getPixelsTall() - 32));
			}
			else {
				// draw explosion
				getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	

	}


	//Changed platform layout
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			//			if() {
			if(i<4) {
				platforms[i].setLocation(SCREEN_WIDTH / 4 + i * 2,
						SCREEN_HEIGHT/2 - 20 + i * 50);
			}
			if(i==4) {
				platforms[i].setLocation(SCREEN_WIDTH / 2 - i*10,
						SCREEN_HEIGHT/2 + i );
			}
			if(i>4){	
				int k=4;
				platforms[i].setLocation(SCREEN_WIDTH / 2 - i*15 - 125 ,
						(SCREEN_HEIGHT/2 - i*50) + 250 );
				k=k+2;
			}
		}
		return platforms;
	}

	//Draws and moves platforms from right to left and left to right
	protected void drawPlatforms() {
		//draw platforms
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<getNumPlatforms(); i++){
			getGraphicsManager().drawPlatform(platforms[i], g2d, this, i);
			if(change == 0) {
				platforms[i].translate(platforms[i].getDefaultSpeed(), 0);}
			if(platforms[i].getX() - 25 == SCREEN_WIDTH) {
				change++;}
			if(change ==1) {
				platforms[i].translate(-platforms[i].getDefaultSpeed(), 0);}
			if(platforms[i].getX() == 0) {
				change = 0;
			}
		}

	}
}
