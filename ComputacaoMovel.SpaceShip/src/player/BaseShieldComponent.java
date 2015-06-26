package player;

import source.GameEntity;

/**
 * BaseShieldComponent.java<p>
 * Defines a base object for the Shield.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.2 15/04/2015
 */
public class BaseShieldComponent extends GameEntity {
	//#- variables
	/** If the shield component is working. */
	protected boolean enabled;
	/** Maximum activated shield number. */
	protected int maxShields;
	/** Current activated shield number. */
	protected int currentShields;
	/** Shield recharging speed. */
	protected float rechargeSpeed;
	/** Recharging value. Varies between 0 and {@link #strength},
	 * and when it reaches its max, 1 is added to {@link #currentShields}. */
	protected float rechargeValue = 0;
	/** The damage that a single shield can absorve. */
	private float strength = 10;
	//#!
	
	
	//#- Functions and Methods
	/**
	 * Automatic shield's constructor.
	 * @param <b>posX & posY</b> - shield's sprite's position
	 */
	public BaseShieldComponent(float posX, float posY) {
			super(posX, posY, 0, resources.placeholder);
		this.setAlpha(.0f);
	}
	//#- Functions and Methods
	/**
	* Automatic shield's constructor.
	* @param <b>posX & posY</b> - shield's sprite's position
	* @param <b>strength</b> - The damage that a single shield can absorve.
	* @param <b>rechargeValue</b> - Recharging value. Varies between 0 and {@link #strength}, and when it reaches its max, 1 is added to {@link #currentShields}
	* @param <b>rechargeSpeed</b> - Shield recharging speed.
	*/
	public BaseShieldComponent(float posX, float posY, float strength, float rechargeValue, float rechargeSpeed) {
				super(posX, posY, 0, resources.placeholder);
			this.setAlpha(.0f);
			this.strength = strength;
			this.rechargeValue = rechargeValue;
			this.rechargeSpeed = rechargeSpeed;
	}	
	
	/**
	 * Is responsible for recharging the shields.
	 * <p>
	 * WARNING: In a next version, it will change shield colors depending on the
	 * active shield number, using {@link #updateShieldColor()}.
	 * 
	 * @param deltaTime  the time between Update calls. It is used to balance the
	 * framerate drop lag.
	 */
	public void Update(float elapsedTime) {
		if (enabled && (currentShields < maxShields)){
			rechargeValue += strength*rechargeSpeed*elapsedTime;
			if (rechargeValue >= strength) {
				rechargeValue -= strength;
				currentShields++;
				UpdateShieldColor();
			}
		}
	}

	/**
	 * Changes the shield color depending on the {@link #currentShields current activated shield number}.
	 * <p>
	 * WARNING: At this moment, this function isn't coloring the sprite.
	 */
	private void UpdateShieldColor(){
		switch ((int)Math.floor(currentShields)) {
			case 0:
				setColor(0, 0, 0, 0); // Transparent
				break;
			case 1:
				setColor(0.9333f, 0.102f, 0.102f, 1.f); // Red #F01A1A
				break;
			case 2:
				setColor(0.9373f, 0.5216f, 0.102f, 1.f); // Orange #EF851A
				break;
			case 3:
				setColor(0.102f, 0.5216f, 0.9412f, 1.f); // Blue #1A85F0
				break;
		}
	}
	
	/**
	 * Applies the damage to the shield, weakening it.
	 * @param damage - The damage to be absorbed by the shield.
	 */
	@Override
	public void TakeDamage(float damage) {
		rechargeValue -= damage;
		
		while (rechargeValue < 0 && currentShields > 0) {
			currentShields--;
			rechargeValue += strength;
		}
		
		UpdateShieldColor();
	}
	//#!
	
	
	//#- Getters & Setters
	/**@return {@link #maxShields}*/
	public int getMaxShields() {
		return maxShields;
	}
	
	/**@return If there is some active shielding. */
	public boolean isActive(){
		if (currentShields > 0)
			return true;
		else return false;
	}
	
	/**Enables the shields.*/
	public void enable() {
		enabled = true;
	}

	/**Disables the shields.*/
	public void disable() {
		enabled = false;
	}
	//#!

	/** Returns the string debugging the shield. */
	public String Debug() {
		return String.format("Shields: %d\n", currentShields);
	}
}