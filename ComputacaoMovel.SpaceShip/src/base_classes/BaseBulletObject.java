package base_classes;

import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * BaseBulletObject.java<p>
 * Defines a base object for a bullet.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.1 15/04/2015
 */
public abstract class BaseBulletObject extends GameEntity {
	protected float speedX;
	protected float speedY;
	protected final float anchorX;
	protected final float anchorY;
	
	/**
	 * Constructs a bullet of any kind.
	 * @param <b>anchorX & anchorY</b> - From where the bullet will fire.
	 * @param <b>speedX & speedY</b> - Bullet's inicial velocity.
	 * @param texture - Bullet's texture.
	 */
	protected BaseBulletObject(float anchorX, float anchorY, float speedX, float speedY, ITextureRegion texture) {
		super(anchorX, anchorY, texture);
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	/** Places the bullet on its anchor point and fires the bullet with {@link #_fire()} function. */
	public void fire() {
		this.setPosition(anchorX, anchorY);
		_fire();
	}
	
	/** Fires the bullet. */
	abstract void _fire();
}