package player;

import gameObjects.ShipObject;

/**
 * BaseAbilityComponent.java<p>
 * Defines a base object for a bullet.
 *
 * @category Ship Component
 * @author Davide Teixeria
 * @version 1.0 09/04/2015
 */
public abstract class BaseAbilityComponent {
	private boolean isActive = false;
	private float duration;
	private float timer = 0;

	/** Activates the Ability component. */
	public void Activate() {
		isActive = true;
	}
	
	public void Update(ShipObject player, float elapsedTime) {
		if (isActive) {
			timer += elapsedTime;
			if (timer >= duration) {
				timer = 0;
				isActive = false;
			}
		}
	}

	/** Returns the string debugging the Ability. */
	public String Debug() {
		return String.format("Ability timer: %f\n", timer);
	}

}
