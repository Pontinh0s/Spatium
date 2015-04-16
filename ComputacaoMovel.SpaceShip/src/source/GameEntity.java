package source;

import managers.ResourcesManager;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * GameEntity.java<p>
 * Defines every game object that is on scene.
 *
 * @category Base Classes
 * @author Davide Teixeria
 * @version 1.0 15/04/2015
 */
public class GameEntity extends Sprite {
	/**
	 * @param pX Sprite position at X axis.
	 * @param pY Sprite position at Y axis.
	 * @param texture ITextureRegion for the Sprite
	 */
	protected GameEntity(float pX, float pY, ITextureRegion texture) {
		super(pX, pY, texture, ResourcesManager.getInstance().vbom);
	}
	
}
