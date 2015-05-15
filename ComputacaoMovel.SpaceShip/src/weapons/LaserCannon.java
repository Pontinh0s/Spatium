package weapons;

import org.andengine.opengl.texture.region.ITextureRegion;

import bullets.HeavyLaserBullet;

/**
 * LaserCannon.java<p>
 * 
 * Fires 1 projectil.
 * Pierces enemies.
 * Has a medium fire rate.
 * Deals low damage.
 *
 * @category weapons
 * @author Davide Teixeira
 * @version 1.0 30/04/2015
 */
public class LaserCannon extends BaseWeaponComponent {
	private static final ITextureRegion texture = resources.placeholder;
	private static final float bulletAnchorX = 0;
	private static final float bulletAnchorY = resources.placeholder.getHeight()*.4f;

	/** Laser cannon constructor. Defines the weapon's atributes.
	 * @param <b>posX & posY</b> - relative position of the weapon.
	 * @see {@linkplain BaseWeaponComponent Base Weapon Component class}
	 */
	public LaserCannon(float posX, float posY) {
		super(posX, posY, 1.f, .2f, texture);
	}

	@Override
	protected void _fire() {
		HeavyLaserBullet bullet =
				new HeavyLaserBullet(bulletAnchorX, bulletAnchorY, this.damage);
		bullets.add(bullet);
	}
}