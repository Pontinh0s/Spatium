/**
 * 
 */
package Prototipo;

import java.util.ArrayList;

import gameObjects.BaseObstacleObject;
import gameObjects.ShipObject;
import managers.AccelerometerManager;
import managers.SceneManager;
import managers.Wave;
import managers.SceneManager.SceneType;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import source.BaseScene;
import android.view.MotionEvent;

/** BaseLevel.java<p>
 *	Creates a base level, with no enemies.
 *
 * @category Prototipo
 * @author David Malheiro
 * @version 1.0 24/06/2015
 */
public class BaseLevel extends BaseScene implements IOnSceneTouchListener {
	private AccelerometerManager accelerometer;
	private float touchX, touchY;

	private float ticker = 0;
	private Text scoreText;
	private float score = 0;
	
	protected ShipObject player;
	protected ArrayList<BaseObstacleObject> enemies;

	@Override
	public void createScene() {
		setOnSceneTouchListener(this);
    	this.accelerometer = new AccelerometerManager(activity);
    	
    	createBackground();
    	createHUD();
    	LoadGameObjects();
    	createUpdates();
	}
    
    private void createUpdates(){
    	registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				ticker += pSecondsElapsed;
				if (ticker >= 1) {
					ticker -= 1;
					tickEverySecond();
				}
				
				resourcesManager.timer += pSecondsElapsed;
				Update(pSecondsElapsed);
				RefreshText();
			}
			@Override
			public void reset() {
			}
        });
    }
    
    protected void Update(float pSecondsElapsed) {
    	player.Update(-accelerometer.getYAxis(), enemies, pSecondsElapsed);
    	for (int i = 0; i<enemies.size(); i++)
    		enemies.get(i).Update(pSecondsElapsed, enemies, player);
    }
    
    private void tickEverySecond() {
		score++;
    }

    private void RefreshText() {
    	scoreText.setText(player.Debug() + "\nTempo: " + String.format("%.0f", score));
    }
    
    /** Runs when the game is over. */
    public void gameOver() {
    	SceneManager.getInstance().createMenuScene();
		//dataBase.insertRow("Player1", (int)maxScore, resourcesManager.timer);
	}
    
    /** Usa-se esta função para criar o HUD do jogo.
     * O HUD é toda a informação do jogo importante para o jogador que é apresentado no ecrã
     */
    private void createHUD(){
    	int scorePosX = 20;
    	int scorePosY = 10;
    	float scoreScale = 1.0f;
    	
    	scoreText = new Text(scorePosX, scorePosY, resourcesManager.fontDefault,
    			"Vidas: XX\nTempo: XXxxxxxXxxxXXxxxXXxxXxxxxxxxxXXxxxXXxxXxxxxxxxxXXxxxXXxxXxxxxxxxxXXxxxXXxxXxxxxxxX", vbom);
	    scoreText.setTextOptions(new TextOptions(HorizontalAlign.LEFT));
    	scoreText.setScale(scoreScale);
		scoreText.setColor(Color.BLUE);
    }

    private void LoadGameObjects(){
    	player = new ShipObject();
    	
    	attachChild(player);
    }

	@Override
	public void onBackKeyPressed() {
    	SceneManager.getInstance().createMenuScene();
	}



	@Override
	public SceneType getSceneType() {
        return SceneType.SCENE_GAME;
	}



	@Override
	public void disposeScene() {
    	accelerometer.unRegisterListener();
	}
	
    private void createBackground() {
        setBackground(new Background(Color.BLUE));
    }
	
	private void onTouchDown(){
		if (touchX > resourcesManager.camera.getWidth()/2)
			player.Fire();
		else
			player.FireSpecial();
	}
	
	private void onTouchMove(){
		
	}
	
	private void onTouchUp(){
		
	}
	

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
    	switch (pSceneTouchEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchX = pSceneTouchEvent.getX();
				touchY = pSceneTouchEvent.getX();
				onTouchDown();
				break;
			case MotionEvent.ACTION_MOVE:
				touchX = pSceneTouchEvent.getX();
				touchY = pSceneTouchEvent.getX();
				onTouchMove();
				break;
			case MotionEvent.ACTION_UP:
				onTouchUp();
				break;
		}
        return true;
	}
}