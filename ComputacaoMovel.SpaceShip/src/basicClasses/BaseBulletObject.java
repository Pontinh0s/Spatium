package basicClasses;

import managers.ResourcesManager;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.opengl.texture.region.ITextureRegion;

import source.GameEntity;

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
	
	/**
	 * Creates a bullet of any kind.
	 * @param <b>anchorX & anchorY</b> - From where the bullet will fire.
	 * @param texture - Bullet's texture.
	 */
	protected BaseBulletObject(float anchorX, float anchorY, ITextureRegion texture) {
		super(anchorX, anchorY, texture);
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}
	
	/** Places the bullet on its anchor point and fires the bullet with {@link #_fire()} function. */
	public void fire() {
		this.setPosition(anchorX, anchorY);

		ResourcesManager.getInstance().engine.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				_fire();
			}
		});
	}

	/** Fires the bullet. This function is repeated automaticly on Update.*/
	protected abstract void _fire();
	
	/**
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
	
	/** Completely detaches this sprite from the scene. */
	public void Destroy() {
		this.detachSelf();
		this.dispose();
	}
}