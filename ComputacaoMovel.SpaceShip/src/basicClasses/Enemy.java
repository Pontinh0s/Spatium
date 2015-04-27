package basicClasses;

import java.util.ArrayList;

import managers.ResourcesManager;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.EaseSineInOut;

import gameObjects.BaseObstacleObject;
import player.BaseAbilityComponent;
import player.BaseShieldComponent;
import player.BaseSpecialComponent;
import player.BaseWeaponComponent;

/**
 * Enemy.java<p>
 * Defines a base object for the Enemy.
 *
 * @category Enemies
 * @author David Malheiro
 * @version 1.1 21/04/2015
 */
public abstract class Enemy extends BaseObstacleObject{
	

	/** Ship's main weapon.<p>Suposed to be activated by pressing the screen's right side. */
	protected BaseWeaponComponent mainWeapon = null;
	/** Ship's shield generator.<p>As a passive element, the shields regenerate automaticly while enabled. */
	protected BaseShieldComponent shield = null;
	
	

	/** Creates a ship with predefined settings.
	 * @param {@link #mainWeapon}
	 * @param {@link #shield}
	 */
	public Enemy(float pX, float pY, int hp, float speed, ITextureRegion texture, Path patternPath, BaseWeaponComponent mainWeapon, BaseShieldComponent shield){
		super(pX, pY, texture);
		//Positions himself in the pattern and then loop the pattern
		 Path inicialPath = new Path(1).to(patternPath.getCoordinatesX()[0],patternPath.getCoordinatesY()[0]);  
		 this.registerEntityModifier(new SequenceEntityModifier(
			        new PathModifier(speed, inicialPath , EaseLinear.getInstance()),
			        new LoopEntityModifier(new PathModifier(speed, patternPath, EaseSineInOut.getInstance()))));
	}

	/**Faz o inimigo disparar*/
	public abstract void shoot();

	public void update(float elapsedTime)
	{
		//Components
		if(mainWeapon != null)
		mainWeapon.Update(elapsedTime);
		if(shield != null)
		shield.Update(elapsedTime);
	}

	/** Fires the main weapon.
	 * @see BaseWeaponComponent #fire()
	 */
	public void Fire(){
		if (mainWeapon != null)
			mainWeapon.fire();
	}
	
	public void Destroy() {
		super.Destroy();
		mainWeapon.Destroy();
		shield.Destroy();
	}

}
