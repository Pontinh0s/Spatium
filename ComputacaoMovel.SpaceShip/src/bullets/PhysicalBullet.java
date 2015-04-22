package bullets;

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
	
	/**
	 * A normal looking bullet that doesn't cross enemies.<p>
	 * Using this constructor means that the bullet fires in up direction (as in dirX=0, dirY=1).
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 */
	protected PhysicalBullet(float anchorX, float anchorY) {
			super(anchorX, anchorY, velocity, resources.placeholder);
	}
	
	/**
	 * A normal looking bullet that doesn't cross enemies.
	 * @param <b>anchorX & anchorY</b> - Position from where the bullets will be fired
	 * @param <b>dirX & dirY</b> - Inicial bullet direction
	 */
	protected PhysicalBullet(float anchorX, float anchorY, float dirX, float dirY) {
			super(anchorX, anchorY, dirX, dirY, velocity, resources.placeholder);
	}
	
	@Override
	public void Update(float elapsedTime) {
		setPosition(getX() + speedX*elapsedTime, getY() + speedY*elapsedTime);
	}
}