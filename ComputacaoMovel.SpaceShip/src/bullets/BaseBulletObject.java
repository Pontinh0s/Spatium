package bullets;

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
	
	/** Creates a bullet of any kind going to front (as in dirX=0, dirY=1).
	 * @param <b>anchorX & anchorY</b> - From where the bullet will fire
	 * @param velocity - Bullet's inicial velocity
	 * @param texture - Bullet's texture
	 */
	protected BaseBulletObject(float anchorX, float anchorY, float velocity, ITextureRegion texture) {
		super(anchorX, anchorY, texture);
		this.anchorX = anchorX;
		this.anchorY = anchorY;
		speedX = velocity;
		speedY = 0;
	}

	/** Creates a bullet of any kind.
	 * @param <b>anchorX & anchorY</b> - From where the bullet will fire.
	 * @param <b>dirX & dirY</b> - Inicial bullet direction (doesn't need to be normalized)
	 * @param velocity - Bullet's inicial velocity
	 * @param texture - Bullet's texture.
	 */
	protected BaseBulletObject(float anchorX, float anchorY, float dirX, float dirY, float velocity, ITextureRegion texture) {
		super(anchorX, anchorY, texture);
		this.anchorX = anchorX;
		this.anchorY = anchorY;

		float dirMag = (float) Math.sqrt((dirX*dirX) + (dirY*dirY)); //Direction's vector's magnitude.
		this.speedX = (dirX/dirMag) * velocity;
		this.speedY = (dirY/dirMag) * velocity;
		this.setRotation((float) Math.cos(dirX/dirMag));
	}


	/** Moves the bullet. This function has to be repeated on an Update loop, generally called on {@link BaseWeaponComponent}.
	 * @param elapsedTime - Time since the last update
	 */
	public abstract void Update(float elapsedTime);
	
	/** Verifies if this bullet is or not out of bounds.
	 * @param cameraWidth - Wight of the camera.
	 * @param cameraHeight - Height of the camera.
	 * @return If the bullet is or not completely outside the camera bounds. */
	protected boolean isOutOfBounds(float cameraWidth, float cameraHeight) {
		if ((this.getX() < -this.getWidth()) ||
			(this.getY() < -this.getHeight()) ||
			(this.getX() > cameraWidth) ||
			(this.getY() > cameraHeight))
			return true;
		else
			return false;
	}
}