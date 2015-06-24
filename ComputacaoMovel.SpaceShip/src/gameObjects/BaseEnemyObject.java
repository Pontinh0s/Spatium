package gameObjects;

import java.util.ArrayList;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseSineInOut;

import gameObjects.BaseObstacleObject;
import player.BaseShieldComponent;
import source.GameEntity;
import weapons.BaseWeaponComponent;

/** EnemyObject.java<p>
 * Defines a base object for the Enemy.
 *
 * @category Enemies
 * @author David Malheiro
 * @version 1.1 21/04/2015
 */
public abstract class BaseEnemyObject extends BaseObstacleObject{
	/** Ship's main weapon.<p>Suposed to be activated by pressing the screen's right side. */
	protected BaseWeaponComponent mainWeapon = null;
	/** Ship's shield generator.<p>As a passive element, the shields regenerate automaticly while enabled. */
	protected BaseShieldComponent shield = null;
	

	/** Creates a ship with predefined settings.
	 * @param {@link #mainWeapon}
	 * @param {@link #shield}
	 */
	public BaseEnemyObject(float pX, float pY, int hp, float speed, ITextureRegion texture, LoopEntityModifier loop, BaseWeaponComponent mainWeapon, BaseShieldComponent shield){
		super(pX, pY,speed, texture);
		this.mainWeapon = mainWeapon;
		this.shield = shield;
		//Positions himself in the pattern and then loop the pattern
		 this.registerEntityModifier(loop);
	}
	

	/** Faz o inimigo disparar. */
	public void fire() {
		this.mainWeapon.fire();
	}

	public void update(float elapsedTime, ArrayList<GameEntity> gameObjects)
	{
		//Components
		if(mainWeapon != null)
		mainWeapon.Update(elapsedTime, gameObjects);
		if(shield != null)
		shield.Update(elapsedTime);
		if (isOutOfBounds(resources.camera.getWidth(), resources.camera.getHeight()))
		Destroy();
	}
	
	/** Verifies if this enemy is or not out of bounds.
	 * @param cameraWidth - Wight of the camera.
	 * @param cameraHeight - Height of the camera.
	 * @return If the bullet is or not completely outside the camera bounds. */
	protected boolean isOutOfBounds(float cameraWidth, float cameraHeight) {
		if ((this.getX() < -this.getWidth()) ||
			(this.getY() < -this.getHeight()) ||
			(this.getX() > cameraWidth) ||
			(this.getY() > cameraHeight))
			return true;
		else
			return false;
	}


	/** Fires the main weapon.
	 * @see BaseWeaponComponent #fire()
	 */
	public void Fire(){
		if (mainWeapon != null)
			mainWeapon.fire();
	}
	
	public void Destroy() {
		mainWeapon.Destroy();
		shield.Destroy();
		super.Destroy();
	}

}
