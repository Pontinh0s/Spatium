package bullets;

import gameObjects.BaseObstacleObject;
import gameObjects.ShipObject;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;

import source.GameEntity;
import weapons.BaseWeaponComponent;

/**
 * BaseBulletObject.java<p>
 * Defines a base object for a bullet.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.3 16/04/2015
 */
public abstract class BaseBulletObject extends GameEntity {
	protected final float anchorX;
	protected final float anchorY;
	protected final float speedX;
	protected final float speedY;
	protected final float damage;
	
	/** Creates a bullet of any kind going to front (as in dirX=0, dirY=1).
	 * @param <b>anchorX & anchorY</b> - From where the bullet will fire
	 * @param velocity - Bullet's inicial velocity
	 * @param damage - The damage the bullet takes
	 * @param texture - Bullet's texture
	 */
	protected BaseBulletObject(float anchorX, float anchorY, float velocity, float damage, ITextureRegion texture) {
		super(anchorX, anchorY, 0, texture);
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.damage = damage;
		speedX = velocity;
		speedY = 0;
	}

	/** Creates a bullet of any kind.
	 * @param <b>anchorX & anchorY</b> - From where the bullet will fire.
	 * @param <b>dirX & dirY</b> - Inicial bullet direction (doesn't need to be normalized)
	 * @param velocity - Bullet's inicial velocity
	 * @param damage - The damage the bullet takes
	 * @param texture - Bullet's texture.
	 */
	protected BaseBulletObject(float anchorX, float anchorY, float dirX, float dirY, float velocity, float damage, ITextureRegion texture) {
		super(anchorX, anchorY, 0, texture);
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.damage = damage;

		float dirMag = (float) Math.sqrt((dirX*dirX) + (dirY*dirY)); //Direction's vector's magnitude.
		this.speedX = (dirX/dirMag) * velocity;
		this.speedY = (dirY/dirMag) * velocity;
		this.setRotation((float) Math.cos(dirX/dirMag));
	}


	/** Moves the bullet and detects collisions.
	 * This function has to be repeated on an Update loop, generally called on {@link BaseWeaponComponent}.
	 * @param elapsedTime - Time since the last update
	 * @param obstacles - Objects in the scene that can take damage by the bullets
	 */
	public void Update(float elapsedTime,
			ArrayList<BaseObstacleObject> obstacles) {
		//Verifica se colide com algum dos objetos
		for (int index = 0; index < obstacles.size(); index++) {
			if (this.collidesWith(obstacles.get(index)))
				CollidesWithObject(obstacles.get(index));
		}
	}
	
	/** Moves the bullet and detects collisions only with the player.
	 * This function has to be repeated on an Update loop, generally called on {@link BaseWeaponComponent}.
	 * @param elapsedTime - Time since the last update
	 * @param player - The player that can take damage by the bullets
	 */
	public void Update(float elapsedTime, ShipObject player) {
		//Verifica se colide com algum dos objetos
		if (this.collidesWith(player))
			CollidesWithObject(player);
	}
	
	/** It's called when the bullet is colliding with an object
	 * from the {@linkplain BaseObstacleObject obstacle's} list recieved in the {@linkplain #Update(float, ArrayList)}.
	 */
	protected void CollidesWithObject(GameEntity gameEntity) {
		gameEntity.TakeDamage(damage);
		this.Destroy();
	}

	/** Moves the bullet in a straight line, in the bullet's direction. */
	protected void LinearMovement(float elapsedTime) {
		setPosition(getX() + speedX*elapsedTime, getY() + speedY*elapsedTime);
	}
}