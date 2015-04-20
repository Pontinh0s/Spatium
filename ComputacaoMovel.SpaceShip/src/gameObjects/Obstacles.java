/**
 * 
 */
package gameObjects;

import org.andengine.opengl.texture.region.ITextureRegion;

import source.GameEntity;

/**
 * Objects.java<p>
 * 
 *
 * @category gameObjects
 * @author Renato Teixeira
 * @version 1.0 20/04/2015
 */
public class Obstacles extends GameEntity {
	
	protected float hp;
	protected boolean isKillabe;
	protected boolean isDinamic;

	/**
	 * @param pX
	 * @param pY
	 * @param texture
	 */
	
	protected Obstacles(float pX, float pY, ITextureRegion texture) {
		super(pX, pY, texture);
		// TODO Auto-generated constructor stub
	}
	
	protected Obstacles(float pX,  ITextureRegion texture) {
		super(pX, resources.camera.getHeight(), texture);
		// TODO Auto-generated constructor stub
	}
	
	public void GetDamaged(float damage){
		if ((hp > 0) && (isKillabe != false)){
			
				hp = hp - damage;
				
				if (hp < 0){
					
					Destroy();
					
				}
		}
	}
	
	/** Completely detaches this sprite from the scene. */
	public void Destroy() {
		this.detachSelf();
		this.dispose();
	}
}
