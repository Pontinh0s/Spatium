package Parallax;

import managers.ResourcesManager;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * ParallaxLayer.java<p>
 * 
 *
 * @category Parallax
 * @author Davide Teixeira
 * @version 1.0 25/06/2015
 */
public class ParallaxLayer extends Sprite {
	private final float z;
	private final float anchorX;
	private final float anchorY;
	
	/** Creates a Parallax layer.
	 * @param <b>pX, pY, pZ</b> - position of sprite (Z is from 1 to 0, being 1 the closest layer
	 * @param pTextureRegion - Texture to be added to the sprite
	 */
	public ParallaxLayer(float pX, float pY, float pZ, ITextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion, ResourcesManager.getInstance().vbom);
		this.z = pZ;
		this.anchorX = pX;
		this.anchorY = pY;
	}

	public float getZ() {
		return z;
	}
	public float getAnchorX() {
		return anchorX;
	}
	public float getAnchorY() {
		return anchorY;
	}
}
