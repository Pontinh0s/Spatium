package gameObjects;

import java.util.ArrayList;

import managers.ResourcesManager;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import source.GameEntity;


/**
 * Explosion.java<p>
 * 
 *
 * @category gameObjects
 * @author Davide Teixeira
 * @version 1.0 30/04/2015
 */
public class Explosion extends TiledSprite {
	private static ResourcesManager resources;
	private static final ITiledTextureRegion texture = resources.sheetHolder;

	/** Damage that causes to objects per update. */
	private static final float damage = .25f; // Heavy Laser bullet's damage is .2f per update
	/** Time taken to change the frame. */
	private static final float frameTime = 100.f;
	/** for how long the current frame is being shown. */
	private float time = 0.f;

	/** Creates the explosion in the given position.
	 * @param <b>posX & posY</b> - position of the explosion.
	 * @param levelObjects - Objects in the scene that can take damage by the explosion
	 * @param player - the ship controlled by the player
	 */
	public Explosion(float posX, float posY, final ArrayList<GameEntity> levelObjects,
			final ShipObject player) {
			super(posX, posY, texture, resources.vbom);
			
		resources.engine.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				Update(pSecondsElapsed, levelObjects, player);
			}
		});
	}

	/** Responsible for animating the sprite, detect collisions and destroying itself.
	 * @param elapsedTime - Time since the last update
	 * @param levelObjects - Objects in the scene that can take damage by the explosion
	 * @param player - the ship controlled by the player
	 */
	protected void Update(float elapsedTime, ArrayList<GameEntity> levelObjects,
			ShipObject player) {
		time += elapsedTime; //Time count
		
		if (time >= frameTime) {
			time -= frameTime; //Time reset
			
			if (this.getCurrentTileIndex() < this.getTileCount())
				this.getTiledTextureRegion().nextTile(); //Frame update
			else
				Destroy(); //End of explosion
		}
		
		for (int index = 0; index < levelObjects.size(); index++) {
			if (this.collidesWith(levelObjects.get(index)))
				levelObjects.get(index).TakeDamage(damage);
		}
		
		if (this.collidesWith(player)) player.TakeDamage(damage);
	}

	@Override
	public boolean collidesWith(IShape pOtherShape) {
		// TODO Auto-generated method stub
		return super.collidesWith(pOtherShape);
	}

	/** Completely destroys and detaches the sprite from any scene. */
	public void Destroy() {
		this.detachSelf();
		this.dispose();
	}
}
