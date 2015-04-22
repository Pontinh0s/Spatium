package player;

import java.util.ArrayList;

import managers.ResourcesManager;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.opengl.texture.region.ITextureRegion;

import source.GameEntity;
import basicClasses.Detrito;
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
	/**
	 * Defines a weapon based on reloadTime and bullet damage.
	 * @param <b>posX & posY</b> - relative position of the weapon.
	 * @param <b>{@linkplain #reloadTime}</b>
	 * @param <b>{@linkplain #damage}</b>
	 */
	protected BaseWeaponComponent(float posX, float posY, float reloadTime, float damage, ITextureRegion texture) {
			super(posX, posY, texture);
		this.reloadTime = reloadTime;
		this.damage = damage;
		bullets = new ArrayList<BaseBulletObject>();
	}

	/**
	 * Manages all the bullets shot 
	 * @param elapsedTime - Time since the last update
	 */
	public void Update(float elapsedTime) {
		for (int i = 0; i < bullets.size();i++)
			bullets.get(i).Update(elapsedTime);
			
		CheckBullets();
	}
	
	/**
	 * If the weapon is enabled, it is used the abstract function
	 * <code>_fire()</code> to fire it.
	 */
	public void fire() {
		if (enabled) {
			_fire();
		}
	}
	
	/**
	 * Checks if the bullets fired by this weapon are out of the screen limits.<p>
	 * This is an Update function.
	 */
	private void CheckBullets() {
		// Verifica se alguma das balas atiradas já passou dos limites laterais do ecrã
		for (int index = 0; index < bullets.size(); index++) {
			if ((bullets.get(index).getX() < -bullets.get(index).getWidth()) ||
				(bullets.get(index).getX() > resources.camera.getWidth()))
				bullets.get(index).Destroy();
		}
		// Verifica se a primeira bala atirada já passou do limite superior/inferior do ecrã
		if ((bullets.size() > 0) &&
				(bullets.get(0).getY() < -(bullets.get(0).getHeight()) || (bullets.get(0).getY() > resources.camera.getHeight())))
			bullets.get(0).Destroy();
	}
	
	
	// Abstract Functions and Methods
	/** Defines how the weapon is fired. */
	protected abstract void _fire();
	
	
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
