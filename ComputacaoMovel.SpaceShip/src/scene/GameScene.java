package scene;

import managers.AccelerometerManager;
import managers.ResourcesManager;
import managers.SceneManager;
import managers.SceneManager.SceneType;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.ITouchController;
import org.andengine.util.color.Color;

import android.util.Log;
import android.view.MotionEvent;
import base_classes.BaseScene;


public class GameScene extends BaseScene implements IOnSceneTouchListener{
	private AccelerometerManager accelerometer;
	private float timer = 0;
	private float touchX, touchY;
	
	private Text scoreText;
	private int score = 0;
	private int shields = 3;
	
    @Override
    public void createScene(){
    	this.accelerometer = new AccelerometerManager(activity);
    	
    	createBackground();
    	createHUD();
    	
    	createUpdates();
    }

    @Override
    public void onBackKeyPressed(){
    	SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneType getSceneType(){
        return SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene(){
    	accelerometer.unRegisterListener();
    }
    
    private void createBackground(){
        setBackground(new SpriteBackground(new Sprite(0,0, resourcesManager.tbackground, vbom)));
    }
    
    /**
     * Usa-se esta função para criar o HUD do jogo.
     * O HUD é toda a informação do jogo importante para o jogador que é apresentado no ecrã
     */
    private void createHUD(){
    	int scorePosX = -20;
    	int scorePosY = 10;
    	float scoreScale = 0.5f;
    	
    	scoreText = new Text(scorePosX, scorePosY, resourcesManager.fontDefault, "Vidas: XX\nTempo: XXxxxxxXxxxXXxxxXXxxXxxxxxxX", vbom);
	    scoreText.setScale(scoreScale);
    }
    
    private void updateInfo(int shields, int score){
        this.shields = shields;
        this.score = score;
        scoreText.setText("Shields: " + shields + "\nTempo: " + score);
    }
    
    private void createUpdates(){
    	registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				timer += pSecondsElapsed;
				gameUpdate();
			}
			@Override
			public void reset() {
			}
        });
    }
    
    private void gameUpdate(){
    	debugs();
    }
    
    private void debugs(){
    	Log.d("AccelerometerDEBUG", String.format("x:%.3f  y:%.3f  z:%.3f", accelerometer.getXAxis(), accelerometer.getYAxis(), accelerometer.getZAxis()));
    	//Log.d("TouchDEBUG", "x:")
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
	
	private void onTouchDown(){
		
	}
	
	private void onTouchMove(){
		
	}
	
	private void onTouchUp(){
		
	}
}