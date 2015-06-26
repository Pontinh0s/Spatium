package player;

/**
 * BaseSpecialComponent.java<p>
 * Defines a base object for the special power.
 *
 * @category Ship Component
 * @author Davide Teixeria
 * @version 1.0 09/04/2015
 */
public abstract class BaseSpecialComponent {
	
	/** Causes the effect pretended for the specific booster type. */
	public abstract void effect();

	/** Returns the string debugging the shield. */
	public String Debug() {
		return String.format("");
	}
}
