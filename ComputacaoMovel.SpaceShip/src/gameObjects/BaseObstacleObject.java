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
	/** The damage that it causes when hitting the ship. */
	protected final float damage;
	
	/** Creates a sprite that can move and damage the player.
	 * @param <b>posX & posY</b> - Obstacle's start position
	 * @param damage - The damage that it causes when hitting the ship
	 * @param hp - Health points (0 if the object isn't killable)
	 * @param texture - Texture used by the object itself
	 */
	protected BaseObstacleObject(float posX, float posY, float damage,
			float hp, ITextureRegion texture) {
		super(posX, posY, hp, texture);
		this.damage = damage;
	}
	
	/** Creates a sprite that can move and damage the player.
	 * <p>This override allows to create the object just above the scene.
	 * @param posX - Obstacle's start position
	 * @param damage - The damage that it causes when hitting the ship
	 * @param hp - Health points (0 if the object isn't killable)
	 * @param texture - Texture used by the object itself
	 */
	protected BaseObstacleObject(float pX, float damage,
			float hp, ITextureRegion texture) {
		super(pX, resources.camera.getHeight(), hp, texture);
		this.damage = damage;
	}
	
	/** @return {@link #damage} */
	public float getDamage() {
		return damage;
	}
}