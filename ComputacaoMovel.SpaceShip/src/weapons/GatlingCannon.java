package weapons;

import org.andengine.opengl.texture.region.ITextureRegion;
import bullets.PhysicalBullet;

/**
 * GatlingCannon.java<p>
 * 
 * Fires 1 projectil.
 * Disappears after hitting the first enemy.
 * Has a high fire rate.
 * Deals low damage.
 *
 * @category Weapons
 * @author Davide Teixeria
 * @version 1.0 15/04/2015
 */
public class GatlingCannon extends BaseWeaponComponent {
	private static final ITextureRegion texture = resources.placeholder;
	private static final float bulletAnchorX = 0;
	private static final float bulletAnchorY = resources.placeholder.getHeight()*.4f;
	
	/** Gatling Cannon constructor. Defines the weapon's atributes.
	 * @param <b>posX & posY</b> - relative position of the weapon.
	 * @see {@linkplain BaseWeaponComponent Base Weapon Component class}
	 */
	public GatlingCannon(float posX, float posY) {
		super(posX, posY, .5f, 2, texture);
	}
	
	@Override
	public void _fire() {
		PhysicalBullet bullet =
				new PhysicalBullet(bulletAnchorX, bulletAnchorY, this.damage);
		bullets.add(bullet);
	}
}