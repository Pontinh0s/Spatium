/**
 * 
 */
package enemies;

import java.util.ArrayList;
import java.util.Random;

import managers.Pattern;

import org.andengine.opengl.texture.region.ITextureRegion;

import player.BaseShieldComponent;
import source.GameEntity;
import weapons.BarrageLaserGun;
import weapons.LaserCannon;
import gameObjects.BaseEnemyObject;
import gameObjects.Explosion;
import gameObjects.ShipObject;

/**
 * BarrageShooter.java<p>
 *
 *
 * @category enemies
 * @author David Malheiro
 * @version 1.0 26/06/2015
 */
public class BarrageShooter extends BaseEnemyObject{
	private static final ITextureRegion texture = resources.placeholder;
	private static final BarrageLaserGun mainWeapon = new BarrageLaserGun(-texture.getHeight()/3, 0);
	private static final BaseShieldComponent shield = null; //TO-DO: Shield not initialized;
	private static final int hp = 2;
	private static final float speed = 2;

	/** Builds a simple drone in a specific position and a path.
	 * @param <b>posX & posY</b> - Initial enemy's position
	 * @param <b>{@linkplain BaseEnemyObject#patternPath patternPath}</b>
	 * @param <b>{@linkplain BaseEnemyObject#loop loop}</b>
	 */
	public BarrageShooter(float posX, float posY, Pattern path) {
		super(posX, posY, hp, speed, texture, path, mainWeapon, shield);
		
	}
	
	public void Update(ShipObject player, float elapsedTime, float shootSpeed) {
		
		//Components
		mainWeapon.Update(elapsedTime, player);
		shield.Update(elapsedTime);
		
		//Dispara
		if (elapsedTime >= 1000 / shootSpeed)
			fire();
	}


	
	@Override
	public void fire(){
		mainWeapon.fire();
		
	}
	
	@Override
	public void Destroy(){
		super.Destroy();
		explosion(null, null);
		Random Rand =new Random();
		int r = Rand.nextInt(100);
		
		if(r >= 50)
			resources.engine.getScene().attachChild(new Pilot(this.getX(), this.getY(), 1.f));
		
	}
	
	/** Makes the game object explode. */
	public void explosion(final ArrayList<GameEntity> levelObjects, final ShipObject player){
		resources.engine.getScene().attachChild(new Explosion(this.getX(), this.getY(),1.0f, levelObjects, player));
		this.Destroy();
	}


}
