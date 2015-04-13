package Ship;

import java.util.List;

import android.graphics.Point;

/**
 * ShipObject.java<p>
 * Defines a base object for the Main Weapon.
 *
 * @category Objects
 * @author Davide Teixeria
 * @version 1.0 09/04/2015
 */
public class ShipObject {
	// Components
	BaseWeaponComponent mainWeapon;
	BaseShieldComponent shield;
	BaseAbilityComponent ability;
	BaseSpecialComponent special;
	List<BaseBoosterComponent> boosters;
	
	//Status
	float HP;
	Point position;
	
	public ShipObject() {
		
	}
}