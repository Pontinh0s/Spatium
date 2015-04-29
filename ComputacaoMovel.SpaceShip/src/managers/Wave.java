package managers;

import java.util.Dictionary;

/**
 * Wave.java<p>
 *
 *
 * @category managers
 * @author David Malheiro
 * @version 1.0 29/04/2015
 */
public class Wave {
	
	enum wave_enum{
		WAVE_X
	}
	
	wave_enum wave;
	float waveTime;
	Dictionary waveIndex;
	
	public Wave(){
		switchToWave();
	}
	
	/**
	 * Suposed to called every frame during level's Update.<p>
	 * @param elapsedTime - Time since the last update
	 */
	public void Update(float elapsedTime) {
		waveTime += elapsedTime;

	}
	
	private void switchToWave(){
		
		switch (wave){
		case WAVE_X:
				Wave_X();
				if (waveTime == waveIndex.)
				break;
		}
	}

	private void Wave_X(){
		waveIndex.put(11111, 2);
	}
}
