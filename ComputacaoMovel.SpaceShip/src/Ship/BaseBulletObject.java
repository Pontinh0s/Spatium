package Ship;

import org.andengine.entity.sprite.Sprite;

import android.graphics.Point;

/**
 * BaseBulletObject.java
 * Defines a base object for a bullet.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.0 09/04/2015
 */
public abstract class BaseBulletObject {
	public enum BulletType {
		Simple,
		Laser
	}
	
	protected BulletType bulletType;
	protected Point direction;
	protected Sprite bulletSprite;
	
	abstract void fire();
}