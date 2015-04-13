package managers;

import java.util.ArrayList;
import base_classes.Achievement;

/**
 * AchievementsManager.java
 * 
 * Manages all the pre-coded achievements in this class.
 *
 * @category Gamification
 * @author Davide Teixeria
 * @version 1.0 17/02/2015
 */
public class AchievementsManager {
	private ArrayList<Achievement> achievements;

	/**
	 * Creates all the achievements of the game.
	 */
	public AchievementsManager() {
		achievements.add(new Achievement("Over 9000"){
			@Override
			public boolean _isUnlocked() {
				//Condições para que o achievement seja desbloqueado
				return false;
			}
		});
	}
}
