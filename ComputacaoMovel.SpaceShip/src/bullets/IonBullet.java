package bullets;

import java.util.ArrayList;
import org.andengine.opengl.texture.region.ITextureRegion;
import source.GameEntity;
import weapons.IonGun;

/**
 * IonBullet.java<p>
 * Used by {@linkplain IonGun}.
 *
 * @category bullets
 * @author Davide Teixeira
 * @version 1.0 30/04/2015
 */
public class IonBullet extends BaseBulletObject {
	private static final ITextureRegion texture = resources.placeholder; // Bullet texture
	private static final float velocity = 3.7f; // Inicial velocity
	private final float disableTime;

	/** An ion bullet that deactivates eletronics from the enemy.
	 * <p>Deals no damage.
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 * @param disableTime - Time that takes to the enemy to re-enable
	 */
	public IonBullet(float anchorX, float anchorY, float disableTime) {
			super(anchorX, anchorY, velocity, 0, texture);
		this.disableTime = disableTime;
	}

	/** An ion bullet that deactivates eletronics from the enemy.
	 * <p>Deals no damage.
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 * @param <b>dirX & dirY</b> - Inicial bullet direction
	 * @param disableTime - Time that takes to the enemy to re-enable
	 */
	public IonBullet(float anchorX, float anchorY, float dirX, float dirY, float disableTime) {
			super(anchorX, anchorY, dirX, dirY, velocity, 0, texture);
		this.disableTime = disableTime;
	}

	@Override
	public void Update(float elapsedTime, ArrayList<GameEntity> levelObjects) {
		LinearMovement(elapsedTime);
		super.Update(elapsedTime, levelObjects);
	}
	
	// Overrided so the bullet is not destroyed when colliding with an object
	@Override
	public void CollidesWithObject(GameEntity object) {
			object.Disable(disableTime);
		this.Destroy();
	}
}