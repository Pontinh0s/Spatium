package components_weapons;

import Ship.BaseWeaponComponent;

/**
 * GatlingCannon.java<p>
 * 
 * Fires 1 projectil.
 * Disappears after hitting the first enemy.
 * Has a high fire rate.
 * Deals 2 damage.
 *
 * @category Weapons
 * @author Davide Teixeria
 * @version 1.0 15/04/2015
 */
public class GatlingCannon extends BaseWeaponComponent {
	/**
	 * Gatling Cannon constructor. Defines the weapon's atributes.
	 * @see {@linkplain BaseWeaponComponent Base Weapon Component class}
	 */
	public GatlingCannon() {
			super(0.2f, 2, );
	}
	
	@Override
	public void _fire() {
		// TODO Auto-generated method stub

	}
}