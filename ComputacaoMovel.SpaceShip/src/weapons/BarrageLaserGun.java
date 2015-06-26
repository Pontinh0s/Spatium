/**
 * 
 */
package weapons;

import org.andengine.opengl.texture.region.ITextureRegion;

import bullets.HeavyLaserBullet;

/**
 * BarrageLaserGun.java<p>
 *
 *
 * @category weapons
 * @author David Malheiro
 * @version 1.0 26/06/2015
 */
public class BarrageLaserGun extends BaseWeaponComponent {
	private static final ITextureRegion texture = resources.placeholder;
	private static final float bulletAnchorX = 0;
	private static final float bulletAnchorY = resources.placeholder.getHeight()*.4f;
	public static final float reloadTime = 1.f;
	public static final float damage = .2f;

	/** Laser cannon constructor. Defines the weapon's atributes.
	 * @param <b>posX & posY</b> - relative position of the weapon.
	 * @see {@linkplain BaseWeaponComponent Base Weapon Component class}
	 */
	public BarrageLaserGun(float posX, float posY) {
		super(posX, posY, reloadTime, damage, texture);
	}

	/** Defines the weapon with no graphical representation.<p>
	 * Enemies should use this constructor.<p>
	 * Can be used as parameters the {@link #reloadTime} and {@link #damage} from this class.
	 * @param <b>posX & posY</b> - relative position of the weapon
	 * @param <b>{@linkplain #reloadTime}</b>
	 * @param <b>{@linkplain #damage}</b>
	 */
	public BarrageLaserGun(float posX, float posY, float reloadTime, float damage) {
		super(posX, posY, reloadTime, damage, resources.NULL);
	}
	
	@Override
	protected void _fire() {
		HeavyLaserBullet bullet =
				new HeavyLaserBullet(bulletAnchorX, bulletAnchorY, this.getBaseDamage());
		bullet.setRotation(0);
		bullets.add(bullet);
		HeavyLaserBullet bullet2 =
				new HeavyLaserBullet(bulletAnchorX, bulletAnchorY, this.getBaseDamage());
		bullets.add(bullet2);
		bullet2.setRotation(5);
		HeavyLaserBullet bullet3 =
				new HeavyLaserBullet(bulletAnchorX, bulletAnchorY, this.getBaseDamage());
		bullets.add(bullet3);
		bullet3.setRotation(10);
		HeavyLaserBullet bullet4 =
				new HeavyLaserBullet(bulletAnchorX, bulletAnchorY, this.getBaseDamage());
		bullets.add(bullet4);
		bullet4.setRotation(-5);
		HeavyLaserBullet bullet5 =
				new HeavyLaserBullet(bulletAnchorX, bulletAnchorY, this.getBaseDamage());
		bullet5.setRotation(-10);
		bullets.add(bullet5);
	}

}
