package enemies;

import java.util.ArrayList;

import gameObjects.BaseEnemyObject;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.opengl.texture.region.ITextureRegion;

import player.BaseShieldComponent;
import source.GameEntity;
import weapons.LaserCannon;

/** Pilot.java<p>
 * Comes out from the ships.
 *
 * @category basic enemy
 * @author David Malheiro
 * @version 1.0 24/06/2015
 */
public class Pilot extends BaseEnemyObject {
	private static final ITextureRegion texture = resources.placeholder;
	private static final LaserCannon mainWeapon = new LaserCannon(-texture.getHeight()/3, 0);
	private static final BaseShieldComponent shield = null; //TO-DO: Shield not initialized;
	private static final int hp = 1;
	private static final float speed = 2;

	/** Builds a simple drone in a specific position and a path.
	 * @param <b>posX & posY</b> - Initial enemy's position
	 * @param <b>{@linkplain BaseEnemyObject#patternPath patternPath}</b>
	 * @param <b>{@linkplain BaseEnemyObject#loop loop}</b>
	 */
	public Pilot(float posX, float posY, Path patternPath, LoopEntityModifier loop) {
		super(posX, posY, hp, speed, texture, patternPath, loop, mainWeapon, shield);
	}
	
	public void Update(ArrayList<GameEntity> obstacles, float elapsedTime, float shootSpeed) {
		
		//Movimento do Pilot
		Move(elapsedTime);
		//Roatacao da sprite
		Rotate(elapsedTime,1);
		//Components
		mainWeapon.Update(elapsedTime, obstacles);
		shield.Update(elapsedTime);
		
		//Dispara
		if (elapsedTime >= 1000 / shootSpeed)
			fire();
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
	/** Rotates the Sprite.
	 * @param <b>elaspsedTime</b>
	 * @param <b>rotationSpeed</b> - Speed for the rotation
	 */
	private void Rotate(float elapsedTime,float rotationSpeed){
		this.setRotationCenter(this.mWidth, this.mHeight);
		this.setRotation(elapsedTime*rotationSpeed);
	}
	
	@Override
	public void fire(){
		mainWeapon.fire();
		
	}

}
