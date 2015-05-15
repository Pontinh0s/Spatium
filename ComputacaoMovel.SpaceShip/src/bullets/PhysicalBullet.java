package bullets;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;

import source.GameEntity;
import weapons.GatlingCannon;

/**
 * PhysicalBullet.java<p>
 * Used by {@linkplain GatlingCannon}.
 *
 * @category bullets
 * @author Davide Teixeria
 * @version 1.0 16/04/2015
 */
public class PhysicalBullet extends BaseBulletObject {
	private static final ITextureRegion texture = resources.placeholder; // Bullet texture
	private static final float velocity = 3.2f; // Inicial velocity
	
	/** A normal looking bullet that doesn't cross enemies.<p>
	 * Using this constructor means that the bullet fires in up direction (as in dirX=0, dirY=1).
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 * @param damage - The damage the bullet takes
	 */
	public PhysicalBullet(float anchorX, float anchorY, float damage) {
			super(anchorX, anchorY, velocity, damage, texture);
	}
	
	/** A normal looking bullet that doesn't cross enemies.
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 * @param <b>dirX & dirY</b> - Inicial bullet direction
	 * @param damage - The damage the bullet takes
	 */
	public PhysicalBullet(float anchorX, float anchorY, float dirX, float dirY, float damage) {
			super(anchorX, anchorY, dirX, dirY, velocity, damage, texture);
	}
	
	@Override
	public void Update(float elapsedTime, ArrayList<GameEntity> levelObjects) {
		LinearMovement(elapsedTime);
		super.Update(elapsedTime, levelObjects);
	}
}