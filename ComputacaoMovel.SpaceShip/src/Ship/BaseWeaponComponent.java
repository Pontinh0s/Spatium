package Ship;

import org.andengine.entity.sprite.Sprite;

import android.graphics.Point;

/**
 * BaseWeaponComponent.java
 * Defines a base object for the Main Weapon.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.0 09/04/2015
 */
public abstract class BaseWeaponComponent {
	//Weapon parameters
	protected boolean enabled;
	protected float reloadTime;
	protected float damage;
	protected BaseBulletObject bullet;
	
	//Weapon graphics
	protected Sprite weaponSprite;
	protected Point AnchorPoint;
	
	
	
	/// Abstract Functions and Methods
	public abstract void fire();
	
	
	/// Getters & Setters
	/**
	 * @return The time that takes to reload the weapon after firing.
	 */
	public float getReloadTime() {
		return reloadTime;
	}

	/**
	 * @return The base damage that the weapon takes to the enemies.
	 */
	public float getDamage() {
		return damage;
	}

	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
}
