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
	public static final float reloadTime = .5f;
	public static final float damage = 2.f;
	
	/** Defines the weapon's atributes.
	 * @param <b>posX & posY</b> - relative position of the weapon.
	 * @see {@linkplain BaseWeaponComponent Base Weapon Component class}
	 */
	public GatlingCannon(float posX, float posY) {
		super(posX, posY, reloadTime, damage, texture);
	}

	/** Defines the weapon with no graphical representation.<p>
	 * Enemies should use this constructor.<p>
	 * Can be used as parameters the {@link #reloadTime} and {@link #damage} from this class.
	 * @param <b>posX & posY</b> - relative position of the weapon
	 * @param <b>{@linkplain #reloadTime}</b>
	 * @param <b>{@linkplain #damage}</b>
	 */
	public GatlingCannon(float posX, float posY, float reloadTime, float damage) {
		super(posX, posY, reloadTime, damage, resources.NULL);
	}
	
	@Override
	public void _fire() {
		PhysicalBullet bullet =
				new PhysicalBullet(bulletAnchorX, bulletAnchorY, this.getBaseDamage());
		bullets.add(bullet);
	}
}