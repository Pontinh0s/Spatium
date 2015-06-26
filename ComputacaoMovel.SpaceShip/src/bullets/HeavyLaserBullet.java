package bullets;

import gameObjects.BaseObstacleObject;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;

import source.GameEntity;
import weapons.LaserCannon;

/**
 * HeavyLaserBullet.java<p>
 * Used by {@linkplain LaserCannon}.
 *
 * @category bullets
 * @author Davide Teixeira
 * @version 1.0 30/04/2015
 */
public class HeavyLaserBullet extends BaseBulletObject {
	private static final ITextureRegion texture = resources.placeholder; // Bullet texture
	private static final float velocity = 2.5f; // Inicial velocity

	
	/** A heavy laser bullet that pierces enemies in a line.
	 * <p>Deals more damage than the Gatling gun, although is slower.
	 * <p>Using this constructor means that the bullet fires in up direction (as in dirX=0, dirY=1).
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 * @param damage - The damage the bullet takes
	 */
	public HeavyLaserBullet(float anchorX, float anchorY, float damage) {
		super(anchorX, anchorY, velocity, damage, texture);
	}

	/** A heavy laser bullet that pierces enemies in a line.
	 * <p>Deals more damage than the Gatling gun, although is slower.
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 * @param <b>dirX & dirY</b> - Inicial bullet direction
	 * @param damage - The damage the bullet takes
	 */
	public HeavyLaserBullet(float anchorX, float anchorY, float dirX, float dirY, float damage) {
		super(anchorX, anchorY, dirX, dirY, velocity, damage, texture);
	}

	
	@Override
	public void Update(float elapsedTime, ArrayList<BaseObstacleObject> levelObjects) {
		LinearMovement(elapsedTime);
		super.Update(elapsedTime, levelObjects);
	}
	
	// Overrided so the bullet is not destroyed when colliding with an object
	@Override
	public void CollidesWithObject(GameEntity object) {
		object.TakeDamage(damage);
	}
}