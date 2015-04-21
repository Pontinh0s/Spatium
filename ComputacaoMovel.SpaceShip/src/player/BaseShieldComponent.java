package player;

import source.GameEntity;
import managers.ResourcesManager;

/**
 * BaseShieldComponent.java<p>
 * Defines a base object for the Shield.
 *
 * @category Ship Components
 * @author Davide Teixeria
 * @version 1.2 15/04/2015
 */
public abstract class BaseShieldComponent extends GameEntity {
	//#- variables
	//Position
	final static float anchorX = 0.0f;
	final static float anchorY = 0.0f;
	
	//Shield Status
	/** If the shield component is working. */
	protected boolean enabled;
	/** Maximum activated shield number. */
	protected int maxShields;
	/** Current activated shield number. */
	protected float currentShields;
	/** Strenght of each shield. */
	protected float strenght;
	/** Shield recharging speed. */
	protected float rechargeSpeed;
	//#!
	
	
	//#- Functions and Methods
	/** Automatic shield's onstructor. */
	protected BaseShieldComponent() {
			super(anchorX, anchorY, resources.placeholder);
		this.setAlpha(.0f);
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
	public void update(float deltaTime) {
		if (enabled){
			if (currentShields < maxShields){
				int prevShields = (int)Math.floor(currentShields);
				currentShields += rechargeSpeed*deltaTime;
				if (currentShields > maxShields)
					currentShields = maxShields;
				if ((int)Math.floor(currentShields) != prevShields)
					updateShieldColor();
			}
		}
	}

	/**
	 * Changes the shield color depending on the {@link #currentShields current activated shield number}.
	 * <p>
	 * WARNING: At this moment, this function isn't coloring the sprite.
	 */
	private void updateShieldColor(){
		switch ((int)Math.floor(currentShields)) {
			case 0:
				this.setColor(0, 0, 0, 0);
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
		}
	}
	//#!
	
	
	//#- Getters & Setters
	/**@return {@link #maxShields}*/
	public int getMaxShields() {
		return maxShields;
	}

	/**@return {@link #strenght}*/
	public float getStrenght() {
		return strenght;
	}
	
	/**Enables the shelds.*/
	public void enable() {
		enabled = true;
	}

	/**Disables the shields.*/
	public void disable() {
		enabled = false;
	}
	//#!
}