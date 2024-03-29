package weapons;

import gameObjects.BaseObstacleObject;
import gameObjects.ShipObject;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;

import source.GameEntity;
import bullets.BaseBulletObject;

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
	/** If the weapon component is working. */
	private boolean enabled;
	/** Time that takes to reload the weapon after firing. */
	private float reloadTime;
	/** Base damage that the weapon takes to the enemies. */
	private float damage;
	/** Bullet fired by the weapon. */
    protected ArrayList<BaseBulletObject> bullets;
	//#!
	
	// Functions and Methods
	/** Defines a weapon based on reloadTime and bullet damage
	 * @param <b>posX & posY</b> - relative position of the weapon
	 * @param <b>{@linkplain #reloadTime}</b>
	 * @param <b>{@linkplain #damage}</b>
	 * @param texture - The grafical representation of the weapon
	 */
	protected BaseWeaponComponent(float posX, float posY, float reloadTime, float damage, ITextureRegion texture) {
			super(posX, posY, 0, texture);
		this.reloadTime = reloadTime;
		this.damage = damage;
		bullets = new ArrayList<BaseBulletObject>();
	}

	/** Manages all the bullets shot by the player
	 * @param elapsedTime - Time since the last update
	 * @param obstacles - Objects in the scene that can take damage by the bullets.
	 */
	public void Update(float elapsedTime, ArrayList<BaseObstacleObject> obstacles) {
		for (int i = 0; i < bullets.size();i++)
			bullets.get(i).Update(elapsedTime, obstacles);
			
		CheckBullets();
	}

	/** Manages all the bullets shot by the enemy
	 * @param elapsedTime - Time since the last update
	 * @param levelObjects - Objects in the scene that can take damage by the bullets.
	 */
	public void Update(float elapsedTime, ShipObject player) {
		for (int i = 0; i < bullets.size();i++)
			bullets.get(i).Update(elapsedTime, player);
			
		CheckBullets();
	}
	
	/** If the weapon is enabled, it is used the abstract function
	 * <code>_fire()</code> to fire it.
	 */
	public void fire() {
		if (enabled) {
			_fire();
		}
	}
	
	/** Checks if the bullets fired by this weapon are out of the screen limits.<p>
	 * This is an Update function.
	 */
	private void CheckBullets() {
		// Verifica se alguma das balas atiradas j� passou dos limites laterais do ecr�
		for (int index = 0; index < bullets.size(); index++) {
			if ((bullets.get(index).getX() < -bullets.get(index).getWidth()) ||
				(bullets.get(index).getX() > resources.camera.getWidth()))
				bullets.get(index).Destroy();
		}
		// Verifica se a primeira bala atirada j� passou do limite superior/inferior do ecr�
		if ((bullets.size() > 0) &&
				(bullets.get(0).getY() < -(bullets.get(0).getHeight()) || (bullets.get(0).getY() > resources.camera.getHeight())))
			bullets.get(0).Destroy();
	}
	
	// Abstract Functions and Methods
	/** Defines how the weapon is fired. */
	protected abstract void _fire();
	
	
	//#- Getters & Setters
	/** @return {@link #reloadTime} */
	public float getReloadTime() {
		return reloadTime;
	}

	/** @return {@link #damage} */
	public float getBaseDamage() {
		return damage;
	}
	
	/** Enables the weapon. */
	public void enable() {
		enabled = true;
	}

	/** Disables the weapon. */
	public void disable() {
		enabled = false;
	}
	//#!

	/** Returns the string debugging the main Weapon. */
	public String Debug() {
		return String.format("Bullet number: %d\n", bullets.size());
	}
}
