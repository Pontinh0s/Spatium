package weapons;

import org.andengine.opengl.texture.region.ITextureRegion;

import bullets.IonBullet;

/** IonGun.java<p>
 * 
 * Fires 1 projectil.
 * Deactivates enemies.
 * Has a high fire rate.
 * Deals no damage.
 *
 * @category weapons
 * @author Davide Teixeira
 * @version 1.0 30/04/2015
 */
public class IonGun extends BaseWeaponComponent {
	private static final ITextureRegion texture = resources.placeholder;
	private static final float bulletAnchorX = 0;
	private static final float bulletAnchorY = resources.placeholder.getHeight()*.4f;

	/** Ion gun constructor. Defines the weapon's atributes.
	 * @param <b>posX & posY</b> - relative position of the weapon.
	 * @see {@linkplain BaseWeaponComponent Base Weapon Component class}
	 */
	public IonGun(float posX, float posY) {
		super(posX, posY, .3f, 0, texture);
	}

	@Override
	protected void _fire() {
		IonBullet bullet = new IonBullet(bulletAnchorX, bulletAnchorY, 3.f);
		bullets.add(bullet);
	}

}