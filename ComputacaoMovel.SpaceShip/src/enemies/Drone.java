package enemies;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.opengl.texture.region.ITextureRegion;

import player.BaseShieldComponent;
import weapons.LaserCannon;
import gameObjects.BaseEnemyObject;

/** Drone.java<p>
 * A simple drone.
 *
 * @category basic enemy
 * @author Davide Teixeira
 * @version 1.0 20/06/2015
 */
public class Drone extends BaseEnemyObject {
	private static final ITextureRegion texture = resources.placeholder;
	private static final LaserCannon mainWeapon = new LaserCannon(-texture.getHeight()/3, 0);
	private static final BaseShieldComponent shield; //TO-DO: Shield not initialized;
	private static final int hp = 2;
	private static final float speed = 2;

	/** Builds a simple drone in a specific position and a path.
	 * @param <b>posX & posY</b> - Initial enemy's position
	 * @param <b>{@linkplain BaseEnemyObject#patternPath patternPath}</b>
	 * @param <b>{@linkplain BaseEnemyObject#loop loop}</b>
	 */
	public Drone(float posX, float posY, Path patternPath, LoopEntityModifier loop) {
		super(posX, posY, hp, speed, texture, patternPath, loop, mainWeapon, shield);
	}
}
