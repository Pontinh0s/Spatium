package Ship;

import org.andengine.opengl.texture.region.ITextureRegion;
import base_classes.BaseBulletObject;
import base_classes.GameEntity;

/**
 * BaseWeaponComponent.java<p>
 * Defines a base object for the Main Weapon.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.1 15/04/2015
 */
public abstract class BaseWeaponComponent extends GameEntity {
	//#- Variables
	//Position
	final static float anchorX = 0.0f;
	final static float anchorY = 0.0f;
	
	//Weapon status
	/** If the weapon component is working. */
	private boolean enabled;
	/** Time that takes to reload the weapon after firing. */
	private float reloadTime;
	/** Base damage that the weapon takes to the enemies. */
	private float damage;
	/** Bullet fired by the weapon. */
	protected BaseBulletObject bullet;
	//#!
	
	// Functions and Methods
	/**
	 * Defines a weapon based on reloadTime and bullet damage.
	 * @param {@linkplain #reloadTime}
	 * @param {@linkplain #damage}
	 */
	protected BaseWeaponComponent(float reloadTime, float damage, ITextureRegion texture) {
			super(anchorX, anchorY, texture);
		this.reloadTime = reloadTime;
		this.damage = damage;
	}
	
	/**
	 * If the weapon is enabled, it is used the abstract function
	 * <code>_fire()</code> to fire it.
	 */
	public void fire() {
		if (enabled) _fire();
	}
	
	
	// Abstract Functions and Methods
	/**
	 * Fires the weapon.
	 */
	public abstract void _fire();
	
	
	//#- Getters & Setters
	/**
	 * @return {@link #reloadTime}
	 */
	public float getReloadTime() {
		return reloadTime;
	}

	/**
	 * @return {@link #damage}
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
	//#!
}
