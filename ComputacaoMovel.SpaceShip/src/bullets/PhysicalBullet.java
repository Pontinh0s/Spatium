package bullets;

import managers.ResourcesManager;
import basicClasses.BaseBulletObject;

/**
 * PhysicalBullet.java<p>
 * 
 *
 * @category bullets
 * @author Davide Teixeria
 * @version 1.0 16/04/2015
 */
public class PhysicalBullet extends BaseBulletObject {
	private static final float velocity = 3.2f; // Inicial velocity
	private final float speedX;
	private final float speedY;
	
	/**
	 * A normal looking bullet that doesn't cross enemies.
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 * @param <b>dirX & dirY</b> - Inicial bullet direction
	 */
	protected PhysicalBullet(float anchorX, float anchorY, float dirX, float dirY) {
			super(anchorX, anchorY, ResourcesManager.getInstance().placeholder);
		float dirMag = (float) Math.sqrt((dirX*dirX) + (dirY*dirY)); //Direction's vector's magnitude.
		this.speedX = (dirX/dirMag) * velocity;
		this.speedY = (dirY/dirMag) * velocity;
	}

	@Override
	protected void _fire() {
		setPosition(getX() + speedX, getY() + speedY);
	}
}