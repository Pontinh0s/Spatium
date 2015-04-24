/**
 * 
 */
package gameObjects;

import org.andengine.opengl.texture.region.ITextureRegion;

import source.GameEntity;

/**
 * Objects.java<p>
 * 
 *
 * @category gameObjects
 * @author Renato Teixeira
 * @version 1.0 20/04/2015
 */
public class BaseObstacleObject extends GameEntity {
	protected float hp;
	protected boolean isKillabe;
	protected boolean isDinamic;
	/** The damage that it causes when hitting the ship. */
	protected final float damage;
    //	private String tag;
	
	/**
	 * @param pX - 
	 * @param pY - 
	 * @param texture - 
	 */
	protected BaseObstacleObject(float pX, float pY, float damage, ITextureRegion texture) {
		super(pX, pY, texture);
		this.damage = damage;
	}
	
	/**
	 * @param pX - 
	 * @param damage - 
	 * @param texture - 
	 */
	protected BaseObstacleObject(float pX, float damage, ITextureRegion texture) {
		super(pX, resources.camera.getHeight(), texture);
		this.damage = damage;
	}
	
	/**
	 * Aplies the damage to the entity, weakening it.
	 * @param damage - The damage to be absorved by the shield.
	 */
	public void TakeDamage(float damage){
		if ((hp > 0) && (isKillabe != false)){
			hp = hp - damage;
			if (hp < 0)
				Destroy();
		}
	}

	
	//#- Getters & Setters
	/**
	 * @return {@link #isKillabe}
	 */
	public boolean isKillabe() {
		return isKillabe;
	}

	/**
	 * @return {@link #damage}
	 */
	public float getDamage() {
		return damage;
	}
	//#!
}
