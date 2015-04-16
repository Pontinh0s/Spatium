package player;

import java.util.List;

import org.andengine.opengl.texture.region.ITiledTextureRegion;

import source.GameEntity;
import managers.ResourcesManager;

/**
 * ShipObject.java<p>
 * Defines a base object for the Main Weapon.
 *
 * @category Objects
 * @author Davide Teixeria
 * @version 1.1 15/04/2015
 */
public class ShipObject extends GameEntity{
	//#- variables
	/** Ship's main weapon.<p>Suposed to be activated by pressing the screen's right side. */
	protected BaseWeaponComponent mainWeapon = null;
	/** Ship's shield generator.<p>As a passive element, the shields regenerate automaticly while enabled. */
	protected BaseShieldComponent shield = null;
	/** Ship's ability.<p>Suposed to be activated by pressing the screen's left side. */
	protected BaseAbilityComponent ability = null;
	/** Ship's special passive.<p>As a passive element, the special passive is always working and cannot be deactivated. */
	protected BaseSpecialComponent special = null;
	/** Ship's boosters.<p>As a passive element, the boosters are always working and cannot be deactivated.
	 * Boosters here are being used and cannot be returned to player. */
	protected List<BaseBoosterComponent> boosters = null;
	
	//Status
	/** Ship's health points. */
	float HP = 100;
	/** Maximum booster amount that a ship can use. */
	final static int boosterLimit = 4;
	/** Ship's start position on X axis. */
	final static int startPositionX = 50;
	/** Ship's start position on Y axis. */
	final static int startPositionY = 50;
	/**
	 * Ship's texture's array.
	 * <p><b>0</b> - Ship turning left.
	 * <p><b>1</b> - Ship at normal state.
	 * <p><b>2</b> - Ship turning right.
	 */
	final static ITiledTextureRegion texture = ResourcesManager.getInstance().ttPlayer;
	//#!
	
	/** Creates a new empty ship. */
	public ShipObject() {
		super(startPositionX, startPositionY, texture.getTextureRegion(1));
	}
	
	/**
	 * Equips a new weapon to the ship.
	 * @param mainWeapon - New weapon to be equiped.
	 * @return Previous weapon equiped.
	 */
	public BaseWeaponComponent Equip(BaseWeaponComponent mainWeapon) {
		BaseWeaponComponent oldWeapon = this.mainWeapon;
		this.mainWeapon = mainWeapon;
		return oldWeapon;
	}

	/**
	 * Equips a new shield to the ship.
	 * @param shield - New shield to be equiped.
	 * @return Previous shield equiped.
	 */
	public BaseShieldComponent Equip(BaseShieldComponent shield) {
		BaseShieldComponent oldShield = this.shield;
		this.shield = shield;
		return oldShield;
	}

	/**
	 * Equips a new ability to the ship.
	 * @param ability - New ability to be equiped.
	 * @return Previous ability equiped.
	 */
	public BaseAbilityComponent Equip(BaseAbilityComponent ability) {
		BaseAbilityComponent oldAbility = this.ability;
		this.ability = ability;
		return oldAbility;
	}

	/**
	 * Equips a new special passive to the ship.
	 * @param special - New special passive to be equiped.
	 * @return Previous special passive equiped.
	 */
	public BaseSpecialComponent Equip(BaseSpecialComponent special) {
		BaseSpecialComponent oldSpecial = this.special;
		this.special = special;
		return oldSpecial;
	}

	/**
	 * Equips a new booster to the ship.
	 * @param booster - New booster to be equiped.
	 * @return if the booster was or not equiped
	 */
	public boolean Equip(BaseBoosterComponent booster) {
		if (boosters.size() >= boosterLimit)
			return false;
		else {
			boosters.add(booster);
			return true;
		}
	}
}