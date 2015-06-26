package gameObjects;

import java.util.ArrayList;
import java.util.List;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.R.string;
import player.BaseAbilityComponent;
import player.BaseBoosterComponent;
import player.BaseShieldComponent;
import player.BaseSpecialComponent;
import source.GameEntity;
import weapons.BaseWeaponComponent;
import weapons.GatlingCannon;

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
	
	//Movimento
	private float acceleration = 0;
    private final float speeder = 2f;
    private final float frictionForce = 2f;
    private final float springForce = 0.17f;
	
	//Status
	/** Ship's health points. */
	private static float StartHP = 100;
	/** Maximum booster amount that a ship can use. */
	private static int boosterLimit = 1;
	/** Ship's start position on X axis. */
	private final static float startPositionX = resources.camera.getWidth()/2;
	/** Ship's start position on Y axis. */
	public final static float startPositionY = resources.camera.getHeight()*5/6 - 30;
	/**
	 * Ship's texture's array.
	 * <p><b>0</b> - Ship turning left.
	 * <p><b>1</b> - Ship at normal state.
	 * <p><b>2</b> - Ship turning right.
	 */
	private final static ITextureRegion texture = resources.player;
	//#!
	
	/** Creates a new empty ship. */
	public ShipObject() {
		super(startPositionX, startPositionY, StartHP, texture);
		Equip(new GatlingCannon(0, getHeight() - getHeight()/5));
	}

	/** Creates a ship with predefined settings.
	 * @param {@link #mainWeapon}
	 * @param {@link #shield}
	 * @param {@link #ability}
	 * @param {@link #special}
	 */
	public ShipObject(BaseWeaponComponent mainWeapon, BaseShieldComponent shield, BaseAbilityComponent ability, BaseSpecialComponent special) {
		super(startPositionX, startPositionY, StartHP, texture);
		Equip(mainWeapon);
		Equip(shield);
		Equip(ability);
		Equip(special);
	}

	
	/** Suposed to called every frame during level's Update.<p>
	 * Handles every component's Update.
	 * @param accelerationX - Accelerometer reading for the X axis
	 * @param elapsedTime - Time since the last update
	 */
	public void Update(float accelerationX, ArrayList<BaseObstacleObject> enemies, float elapsedTime) {
		Move(accelerationX, elapsedTime);
		
		//Components
		mainWeapon.Update(elapsedTime, enemies);
		shield.Update(elapsedTime);
		ability.Update(this, elapsedTime);
		
		// Obstacles & Enemies
		detectColisions(enemies);
	}
	
	/** Moves the ship.
	 * @param accelerationX - Accelerometer reading for the X axis
	 * @param elapsedTime - Time since the last update
	 * @see #Update(float, ArrayList, float)
	 */
	private void Move(float accelerationX, float elapsedTime) {
    	float X = 0;
    	
    	// Acceleration
    	acceleration += accelerationX * speeder;
    	X += acceleration;
    	
    	// Friction
    	acceleration = acceleration / (frictionForce/2 + 1);

    	// Pre-atribution
        X *= elapsedTime*20;
        X += getX();
        
        //Spring Effect
    	int limite = 100;
        if (X < limite-getWidth()/2)
    		X = X + (limite-X-getWidth()/2) * springForce;
        if (X > resources.camera.getWidth() - limite - getWidth()/2)
    		X = X - (X- (resources.camera.getWidth() - limite - getWidth()/2)) * springForce;

        // Atribution
        setPosition(X, getY());
	}

	/** Fires the main weapon.
	 * @see BaseWeaponComponent #fire()
	 */
	public void Fire(){
		if (mainWeapon != null)
			mainWeapon.fire();
	}
	
	/** Activates the special active action. */
	public void FireSpecial() {
		ability.Activate();
	}
	
	/** Detects if there is something coliding with the ship,
	 * damages the ship and deletes the object.
	 * @param obstaculos - 
	 * @return <b>true</b> if there was a collision and
	 * <b>false</b> if not.
	 */
	private boolean detectColisions(ArrayList<BaseObstacleObject> obstacles) {
		for(int i = 0; i<obstacles.size(); i++) {
			if (obstacles.get(i).collidesWith(this)) {
				if ((shield != null) && shield.isActive())
					shield.TakeDamage(obstacles.get(i).getDamage());
				else
					TakeDamage(obstacles.get(i).getDamage());
				
				obstacles.get(i).Destroy();
				return true;
			}
		}
		
		return false;
	}
	
	
	/** Equips a new weapon to the ship.
	 * @param mainWeapon - New weapon to be equiped.
	 * @return Previous weapon equiped.
	 */
	public BaseWeaponComponent Equip(BaseWeaponComponent mainWeapon) {
		if (mainWeapon != null) {
			this.detachChild(this.mainWeapon);
			BaseWeaponComponent oldWeapon = this.mainWeapon;
			this.mainWeapon = mainWeapon;
			this.attachChild(this.mainWeapon);
			return oldWeapon;
		}
		else return null;
	}

	/** Equips a new shield to the ship.
	 * @param shield - New shield to be equiped.
	 * @return Previous shield equiped.
	 */
	public BaseShieldComponent Equip(BaseShieldComponent shield) {
		if (shield != null) {
			this.detachChild(this.shield);
			BaseShieldComponent oldShield = this.shield;
			this.shield = shield;
			this.attachChild(this.shield);
			return oldShield;
		}
		else return null;
	}

	/** Equips a new ability to the ship.
	 * @param ability - New ability to be equiped.
	 * @return Previous ability equiped.
	 */
	public BaseAbilityComponent Equip(BaseAbilityComponent ability) {
		if (ability != null) {
			BaseAbilityComponent oldAbility = this.ability;
			this.ability = ability;
			return oldAbility;
		}
		else return null;
	}

	/** Equips a new special passive to the ship.
	 * @param special - New special passive to be equiped.
	 * @return Previous special passive equiped.
	 */
	public BaseSpecialComponent Equip(BaseSpecialComponent special) {
		if (special != null) {
			BaseSpecialComponent oldSpecial = this.special;
			this.special = special;
			return oldSpecial;
		}
		else return null;
	}

	/** Equips a new booster to the ship.
	 * @param booster - New booster to be equiped.
	 * @return if the booster was or not equiped
	 */
	public boolean Equip(BaseBoosterComponent booster) {
		if (boosters.size() >= boosterLimit)
			return false;
		else if (booster != null) {
			boosters.add(booster);
			return true;
		}
		else return false;
	}

	@Override
	public void Destroy() {
		mainWeapon.Destroy();
		shield.Destroy();
		super.Destroy();
		// GAME OVER scene
	}

	/** Returns a string debugging instant Player status. */
	public String Debug() {
		return shield.Debug() + mainWeapon.Debug() + ability.Debug() + special.Debug();
	}
}