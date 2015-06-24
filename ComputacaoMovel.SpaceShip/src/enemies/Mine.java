/**
 * 
 */
package enemies;

import java.util.ArrayList;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import player.BaseShieldComponent;
import source.GameEntity;
import weapons.LaserCannon;
import gameObjects.BaseEnemyObject;
import gameObjects.Explosion;
import gameObjects.ShipObject;

/**
 * Mine.java<p>
 *
 *
 * @category basic enemy
 * @author David Malheiro
 * @version 1.0 24/06/2015
 */
public class Mine  extends GameEntity{

	ShipObject player;
	/**
	 * @param posX
	 * @param posY
	 */
	protected Mine(float posX, float posY) {
		super(posX, posY, 1, resources.placeholder);
		// TODO Auto-generated constructor stub
	}
	
	
	public void Update(ShipObject player, float elapsedTime) {
		
		//Movimento do Pilot
		Move(elapsedTime);

		
		//Dispara
		if (this.getY() >= player.getY()){
			
			this.player = player;
			Destroy();
		}
	}
	
	/** Moves the Sprite.
	 * @param <b>elaspsedTime</b>
	 */
	private void Move(float elapsedTime){
		float X = 0;
		float Y = 0;
    	
		// Pre-atribution
        X *= elapsedTime*20;
        X += getX();

        Y *= elapsedTime*20;
        Y += getY();
        
        // Atribution
        setPosition(X, Y);
	}
	
	
	@Override
	public void Destroy() {
		super.Destroy();
		new Explosion(this.getX(),this.getY(),0.4f,null,player);
		// GAME OVER scene
	}
	
}
