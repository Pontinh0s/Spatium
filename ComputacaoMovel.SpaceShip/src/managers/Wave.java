package managers;

import java.util.ArrayList;

import managers.Pattern;
import managers.Pattern.pattern;
import gameObjects.Enemy;

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
		WAVE_1_1
	}
	
	ResourcesManager resources;
	wave_enum wave;
	float waveTime;
	int[] waveTimerIndex;
	ArrayList<Pattern> waveIndex;
	
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
		case WAVE_1_1:
				Wave_1_1();
					for (int i = 0 ; i < waveTimerIndex.length ;i++)
						if (waveTime == waveTimerIndex[i])
						{
							Enemy();
						}
				break;
		}
	}

	private void Wave_1_1(){
		
		waveIndex = new ArrayList<Pattern>();
		waveTimerIndex = new int [3];
		
		waveTimerIndex[0] = 1;
		waveTimerIndex[1] = 2;
		waveTimerIndex[2] = 1;
		
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.25f,
						(-Enemy.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.5f,
						(-Enemy.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.75f,
						(-Enemy.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		
	}
}
