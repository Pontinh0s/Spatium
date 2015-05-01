package source;

import managers.ResourcesManager;

import org.andengine.engine.handler.IUpdateHandler;
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
public abstract class GameEntity extends Sprite {
	/** An instance of the ResourceManager class. */
	public static ResourcesManager resources;
	/** HealthPoints of the entity. */
	protected float hp;
	/** If the object can take damage. */
	protected final boolean isKillabe;
	
	/** Defines a sprite entity for Spatium game.
	 * @param <b>posX & posY</b> - Sprite's start position
	 * @param hp - Health points (0 if the object isn't killable)
	 * @param texture - Texture for the Sprite
	 */
	protected GameEntity(float posX, float posY, float hp, ITextureRegion texture) {
		super(posX, posY, texture, ResourcesManager.getInstance().vbom);
		resources = ResourcesManager.getInstance();
		this.isKillabe = (hp > 0);
		this.hp = hp;
	}
	

	/** Aplies the damage to the entity, weakening it.
	 * @param damage - The damage to be absorved by the shield.
	 */
	public void TakeDamage(float damage){
		if ((hp > 0) && (isKillabe != false)){
			hp = hp - damage;
			if (hp < 0)
				Destroy();
		}
	}

	/** Completely destroys and detaches the sprite from any scene. */
	public void Destroy() {
		this.detachSelf();
		this.dispose();
	}
	
	/** @return {@link #isKillabe} */
	public boolean isKillabe() {
		return isKillabe;
	}


	/** Disables all electronics.
	  * @param disableTime - Time that takes to re-enable
	  */
	public void Disable(final float disableTime) {
		resources.engine.registerUpdateHandler(new IUpdateHandler() {
			float timer;
			@Override
			public void reset() { }
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				timer += pSecondsElapsed;
				if (timer >= disableTime) Enable();
				resources.engine.unregisterUpdateHandler(this);
			}
		});
	}
	
	/** Enables all elecronics. */
	public void Enable() {
	}
}
