package Ship;

import org.andengine.entity.sprite.Sprite;

import android.graphics.Point;

/**
 * BaseShieldComponent.java
 * Defines a base object for the Shield.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.0 09/04/2015
 */
public abstract class BaseShieldComponent {
	//Shield status
	protected int maxShields;
	protected float currentShields;
	
	//Graphics
	protected Sprite shieldSprite;
	protected Point anchorPoint;
}