package scene;

import managers.AccelerometerManager;
import managers.DetritosManager;
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

import com.example.computacaomovel.spaceship.*;

import android.util.Log;
import android.view.MotionEvent;
import base_classes.BaseScene;
import base_classes.Player;
import base_classes.Tiro;


public class GameScene extends BaseScene implements IOnSceneTouchListener{
	private AccelerometerManager accelerometer;
	private float timer = 0;
	private float touchX, touchY;
	private Player nave;
	private DetritosManager enemy;
	private Tiro bullets;
	
	private Text scoreText;
	private float score = 0;
	private int shields = 3;
	
	boolean gameOver = false;
	private float maxScore;
	
    @Override
    public void createScene(){
    	setOnSceneTouchListener(this); 
    	
    	this.accelerometer = new AccelerometerManager(activity);
    	
    	createBackground();
    	createHUD();
    	LoadGameObjects();
    	createUpdates();
    	gameUpdate();
    }

    private void LoadGameObjects(){
    	nave = new Player(resourcesManager,  0.7f);
    	enemy = new DetritosManager(resourcesManager);
    	bullets = new Tiro(resourcesManager);
    	
    	attachChild(bullets.Shape());
    	attachChild(enemy.Shape());
    	attachChild(nave.Shape());
    	attachChild(scoreText);
    	
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
    	int scorePosX = 20;
    	int scorePosY = 10;
    	float scoreScale = 0.5f;
    	
    	scoreText = new Text(scorePosX, scorePosY, resourcesManager.fontDefault, "Vidas: XX\nTempo: XXxxxxxXxxxXXxxxXXxxXxxxxxxX", vbom);
	    scoreText.setScale(scoreScale);
		scoreText.setColor(Color.BLUE);
	   
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
    	nave.Update(-accelerometer.getXAxis());
    	enemy.Update();
    	bullets.Update();
    	//COLISOES
    	if(enemy.DetectColision(bullets.Shape())){
    		bullets.Destroy();
    		enemy.Restart();
    	}
    	else if(!nave.saltar && enemy.DetectColision(nave.Shape())){
    		nave.lifes--;
    		resourcesManager.mImpact.stop();
    		resourcesManager.mImpact.play();
    		if(nave.lifes==0){
    			gameOver=true;
    			score = timer;
    			scoreText.setScale(0.6f);
    		}
    		else if(nave.lifes==-1){
    			//estado=mode.menu;
    			//resourcesManager.engine.setScene(cena);
    		}
    		enemy.Restart();
    	}
    	
    	//SCORE
    	if(!gameOver){
    		scoreText.setText("Vidas: " + nave.lifes + "\nTempo: " + String.format("%.0f", score));
    	}
    	else{
    		maxScore = score;
    		scoreText.setText("GAME OVER!\nyou survived for " + String.format("%.1f", maxScore) + " seconds.");
    	}
    	
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
		
		if (touchX > resourcesManager.camera.getWidth()/2){
		nave.disparar(bullets);}else{ nave.saltar();}
		
	}
	
	private void onTouchMove(){
		
	}
	
	private void onTouchUp(){
		
	}
}