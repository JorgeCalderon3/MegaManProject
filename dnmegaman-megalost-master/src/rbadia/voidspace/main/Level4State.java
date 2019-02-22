package rbadia.voidspace.main;
import java.awt.Graphics2D;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Level very similar to LevelState1.  
 * Platforms arranged in triangular form. 
 * Asteroids travel at 225 degree angle
 */
public class Level4State extends Level3State {

	private static final long serialVersionUID = -2094575762243216079L;

	// Constructors
	public Level4State(int level, MainFrame frame, GameStatus status, 
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
		//First Asteroid
		Graphics2D g2d = getGraphics2D();
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
		if((asteroid2.getX() + asteroid2.getPixelsWide() <  SCREEN_WIDTH + asteroid.getPixelsWide())) {

			asteroid2.translate((asteroid2.getSpeed() + random.nextInt(2) + 1), asteroid2.getSpeed()/2);
			getGraphicsManager().drawAsteroid(asteroid2, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime2) > NEW_ASTEROID_DELAY){

				asteroid2.setLocation(0,
						rand.nextInt(SCREEN_HEIGHT - asteroid2.getPixelsTall() - 32));
			}
			else {
				// draw explosion
				getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	

	}
}
