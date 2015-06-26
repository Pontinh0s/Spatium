package managers;

import java.util.ArrayList;

import enemies.Drone;
import managers.Pattern;
import managers.Pattern.pattern;
import gameObjects.BaseEnemyObject;

/**
 * Wave.java<p>
 *
 *
 * @category managers
 * @author David Malheiro
 * @version 1.0 29/04/2015
 */
public class Wave {
	
	// WAVE_"n enemies"_"style or organization"
	enum wave_enum{
		WAVE_3_V,
		WAVE_5_V,
		WAVE_3_V_2_ll
		
	}
	
	ResourcesManager resources;
	wave_enum wave;
	float waveTime;
	int[] waveTimerIndex;
	ArrayList<Pattern> waveIndex;
	
	public Wave(wave_enum wave){
		this.wave = wave;
		switchToWave();
	}
	
	/**
	 * Suposed to called every frame during level's Update.<p>
	 * @param elapsedTime - Time since the last update
	 */
	public void Update(float elapsedTime) {
		waveTime += elapsedTime;

	}
	
	
	/**
	 * Switch's to the wave called
	 * WaveTime - Time it takes to spawn after calling the wave
	 * WaveIndex - Index of different Patterns
	 * WaveTimerIndex - Connects the WaveIndex with WaveTime
	 */
	private void switchToWave(){
		
		switch (wave){
		case WAVE_3_V:
				Wave_3_V();
					for (int i = 0 ; i < waveTimerIndex.length ;i++)
						if (waveTime <= waveTimerIndex[i])
						{
							if (i == 0)
							{
								resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.25f,-25,waveIndex.get(i)));
							} else if (i == 1)
							{
								resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.5f,-25,waveIndex.get(i)));

							} else if (i == 2)
							{
								resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.75f,-25,waveIndex.get(i)));

							}
							
						}
				break;
		case WAVE_5_V:
			Wave_5_V();
				for (int i = 0 ; i < waveTimerIndex.length ;i++)
					if (waveTime == waveTimerIndex[i])
					{
						if (i == 0)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.23f,-25,waveIndex.get(i)));
						} else if (i == 1)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.39f,-25,waveIndex.get(i)));

						} else if (i == 2)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.5f,-25,waveIndex.get(i)));

						}else if (i == 3)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.66f,-25,waveIndex.get(i)));

						}else if (i == 4)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.82f,-25,waveIndex.get(i)));

						}
					}
			break;
		case WAVE_3_V_2_ll:
			Wave_3_V_2_ll();
				for (int i = 0 ; i < waveTimerIndex.length ;i++)
					if (waveTime <= waveTimerIndex[i])
					{
						if (i == 0)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.25f,-25,waveIndex.get(i)));
						} else if (i == 1)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.5f,-25,waveIndex.get(i)));

						} else if (i == 2)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.75f,-25,waveIndex.get(i)));

						}else if (i == 3)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.75f,-25,waveIndex.get(i)));

						}else if (i == 4)
						{
							resources.engine.getScene().attachChild(new Drone(resources.camera.getWidth()*0.75f,-25,waveIndex.get(i)));

						}
						
					}
			break;
		}
	}

	
	private void Wave_3_V(){
		
		waveIndex = new ArrayList<Pattern>();
		waveTimerIndex = new int [3];
		
		waveTimerIndex[0] = 2000;
		waveTimerIndex[1] = 1000;
		waveTimerIndex[2] = 2000;
		
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.25f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.5f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.75f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		
	}
	
	private void Wave_5_V(){
		
		waveIndex = new ArrayList<Pattern>();
		waveTimerIndex = new int [5];
		
		waveTimerIndex[0] = 2;
		waveTimerIndex[1] = 1;
		waveTimerIndex[2] = 2;
		waveTimerIndex[3] = 3;
		waveTimerIndex[4] = 3;
		
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.23f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.39f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.5f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.66f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.82f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		
	}
	
	private void Wave_3_V_2_ll(){
		
		waveIndex = new ArrayList<Pattern>();
		waveTimerIndex = new int [5];
		
		waveTimerIndex[0] = 2000;
		waveTimerIndex[1] = 1000;
		waveTimerIndex[2] = 2000;
		
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.25f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.5f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		waveIndex.add(
				new Pattern(resources.camera.getWidth()*0.75f,
						(-BaseEnemyObject.SPRITE_SIZE),
						pattern.STRAIGHT_LINE,
						5));
		
	}
}
