/**
 * 
 */
package enemies;

import managers.Pattern;

import org.andengine.opengl.texture.region.ITextureRegion;

import player.BaseShieldComponent;
import weapons.BaseWeaponComponent;
import weapons.LaserCannon;
import gameObjects.BaseEnemyObject;
import gameObjects.ShipObject;

/**
 * shieldon.java<p>
 *
 *
 * @category enemies
 * @author David Malheiro
 * @version 1.0 25/06/2015
 */
public class shieldon extends BaseEnemyObject{

	private static final ITextureRegion texture = resources.placeholder;
	private static final LaserCannon mainWeapon = null;
	/** Ship's shield generator.<p>As a passive element, the shields regenerate automaticly while enabled. */
	private static BaseShieldComponent shield = null; //TO-DO: Shield not initialized;
	private static final int hp = 3;
	private static final float speed = 1;
	private float timeU, timeDisabled;
	private Boolean shieldDisabled;
	/**
	 * @param pX
	 * @param pY
	 * @param hp
	 * @param speed
	 * @param path
	 */
	public shieldon(float pX, float pY, int hp, float speed, Pattern path) {
		super(pX, pY, hp, speed, texture, path, mainWeapon, shield);
		// TODO Auto-generated constructor stub
		shield = new BaseShieldComponent(this.getX(),this.getY(),5,1,0.5f);
	
	}
	
	public void Update(ShipObject player, float elapsedTime, float shootSpeed) {
		
		//Components
		mainWeapon.Update(elapsedTime, player);
		
			shield.Update(elapsedTime);
		
		//Dispara
		if (timeU >= 1000 / shootSpeed)
		{
			shield.disable();
			timeU = 0;
			shieldDisabled = true;
		} else if (timeDisabled >= 1000)
		
		if (shieldDisabled == true)
			timeDisabled += elapsedTime;
		

		timeU += elapsedTime;
	}

}
