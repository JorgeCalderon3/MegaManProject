package rbadia.voidspace.main;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Level very similar to LevelState1.  
 * Platforms arranged in triangular form. 
 * Asteroids travel at 225 degree angle
 */
public class Level5State extends Level1State {
	protected static final int NEW_B_bullets_DELAY = 1500;
	protected long lastB_bulletsTime;
	protected List<Bullet> B_bullets;
	private int BossHealth = 0;
	protected Boss boss= new Boss(SCREEN_WIDTH - Boss.WIDTH - 30 ,0);

	private static final long serialVersionUID = -2094575762243216079L;


	// Constructors
	public Level5State(int level, MainFrame frame, GameStatus status, 
			LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		B_bullets = new ArrayList<Bullet>();
	}

	// Getters
	public Boss getBoss()						{ return boss;		}
	public List<Bullet> getBullets() 	 		{ return B_bullets; }

	
	@Override
	public boolean isLevelWon() {
		return BossHealth >= 100;
	}
	

	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		lastB_bulletsTime = -NEW_B_bullets_DELAY;

	}


	public void updateScreen(){
		Graphics2D g2d = getGraphics2D();
		GameStatus status = this.getGameStatus();

		// save original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		clearScreen();
		drawStars(50);
		drawFloor();
		drawPlatforms();
		drawPowerUp_Life();
		drawMegaMan();
		drawAsteroid();
		drawBullets();
		drawBigBullets();
		checkBullletAsteroidCollisions();
		checkBigBulletAsteroidCollisions();
		checkMegaManAsteroidCollisions();
		checkAsteroidFloorCollisions();
		checkPowerUp_LifeCollisions();
		drawBoss();
		drawB_bullets();
		fireB_bullets();
		checkBossBulletCollision( );
		checkBbullletMegamanCollision();
		checkBossBigBulletCollision();

		// update asteroids destroyed (score) label 
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		// update lives left label
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
	}
	
	//Draws boss
	public void drawBoss() {
		Graphics2D g2d = getGraphics2D();
		getGraphicsManager().drawBoss(boss, g2d, this);
		boss.translate(0, (this.megaMan.y-boss.y)/2 -30);
	}

	//Fires boss' bullets
	public void fireB_bullets(){
		long currentTime = System.currentTimeMillis();
		int xPos = boss.x + boss.width/2;
		int yPos = boss.y + boss.height - boss.height/4;
		if((currentTime - lastB_bulletsTime) > NEW_B_bullets_DELAY){
			lastB_bulletsTime = currentTime;
			Bullet bullet = new Bullet(xPos, yPos); 
			bullet.setSpeed(-bullet.getSpeed()/3);
			B_bullets.add(bullet);
			this.getSoundManager().playBulletSound();
		}

	}
	
	//Draws boss' bullets
	protected void drawB_bullets() {
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<B_bullets.size(); i++){
			Bullet bullet = B_bullets.get(i);
			getGraphicsManager().drawB_bullets(bullet, g2d, this);

			boolean remove =   this.moveB_bullets(bullet);
			if(remove){
				B_bullets.remove(i);
				i--;
			}
		}
	}
	
	//Translates boss' bullets
	public boolean moveB_bullets(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(bullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	}
	
	//Verifies megaman's collision with Boss' bullets
	protected void checkBbullletMegamanCollision() {
		GameStatus status = getGameStatus();
		for(int i=0; i<B_bullets.size(); i++){
			Bullet bullet = B_bullets.get(i);
			if(megaMan.intersects(bullet)){
				// decreases megaMan's life
				status.setLivesLeft(status.getLivesLeft() - 1);
				// remove bullet
				B_bullets.remove(i);
				break;
			}
		}
	}

	//Verifies boss' collision with Megaman's bullets
	protected void checkBossBulletCollision() {
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(boss.intersects(bullet)){
				// decreases megaMan's life
				BossHealth ++;
				// remove bullet
				bullets.remove(i);
				break;
			}
		}
	}
	
	//Verifies boss' collision with Megaman's bigBullets
	protected void checkBossBigBulletCollision() {
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(boss.intersects(bigBullet)){
				// decreases megaMan's life
				BossHealth = BossHealth + 5;
				// remove bullet
				bigBullets.remove(i);
				break;
			}
		}
		
	}

	//Verifies Megaman's collision with boss
	protected void checkBossMegaManCollision() {
		if(boss.intersects(megaMan)) {
			status.setLivesLeft(status.getLivesLeft() - 1);
		}
	}
	
	//Changed platform layout
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(i*50 , SCREEN_HEIGHT/2 + 140 - i*40);
		}
		return platforms;
	}
	
}

