package Ship;

import org.andengine.entity.sprite.Sprite;

import android.graphics.Point;

/**
 * BaseWeaponComponent.java<p>
 * Defines a base object for the Main Weapon.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.0 09/04/2015
 */
public abstract class BaseWeaponComponent {
	//Weapon status
	protected boolean enabled;
	protected float reloadTime;
	protected float damage;
	protected BaseBulletObject bullet;
	
	//Weapon graphics
	protected Sprite weaponSprite;
	protected Point AnchorPoint;
	
	/// Functions and Methods
	/**
	 * If the weapon is enabled, it is used the abstract function
	 * <code>_fire()</code> to fire it.
	 */
	public void fire() {
		if (enabled) _fire();
	}
	
	public void Draw() {
		
	}
	
	
	/// Abstract Functions and Methods
	/**
	 * Fires the weapon.
	 */
	public abstract void _fire();
	
	
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
	
	/**
	 * enables the weapon
	 */
	public void enable() {
		enabled = true;
	}

	/**
	 * disables the weapon
	 */
	public void disable() {
		enabled = false;
	}
}
