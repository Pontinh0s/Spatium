package basicClasses;

/**
 * Achievement.java
 * Defines an achievement.
 * Could have a description variable in next versions.
 *
 * @category Gamification
 * @author Davide Teixeria
 * @version 1.0 17/02/2015
 */
public abstract class Achievement {
	private String name;
	private boolean unlocked = false;
	
	/**
	 * Creates an achievement.
	 * 
	 * @param name The name of the Achievement
	 */
	public Achievement(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the achievement.
	 * 
	 * @return The name of the achievement
	 */
	public String getName(){
		return name;
	}

	/**
	 * Verifies if the achievement has been unlocked using the overrided verifying function.
	 * 
	 * WARNING: This method only verifies the achievement if it hasn't been unlocked yet.
	 * 
	 * @return TRUE if it was been unlocked and FALSE if it wasn't
	 */
	public boolean isUnlocked() {
		if (! this.unlocked)
			this.unlocked = _isUnlocked();
		
		return this.unlocked;
	}

	/**
	 * This is the method used by the class to verify if the achievement was unlocked.
	 * 
	 * @return TRUE if it was been unlocked and FALSE if it wasn't
	 */
	protected abstract boolean _isUnlocked();
}
