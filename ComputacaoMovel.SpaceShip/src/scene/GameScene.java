package scene;

import managers.AccelerometerManager;
import managers.DetritosManager;
import managers.TirosManager;
import managers.SceneManager;
import managers.SceneManager.SceneType;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import source.DatabaseHandler;

import android.util.Log;
import android.view.MotionEvent;
import base_classes.BaseScene;
import base_classes.Player;


public class GameScene extends BaseScene implements IOnSceneTouchListener{
	private DatabaseHandler dataBase;
	private AccelerometerManager accelerometer;
	private float touchX, touchY;
	private Player nave;
	private DetritosManager detritos;
	private TirosManager bullets;
	private boolean frameCount = false;
	
	private float ticker = 0;
	private Text scoreText;
	private float score = 0;
	
	boolean gameOver = false;
	private float maxScore;
	
    @Override
    public void createScene(){
    	dataBase = new DatabaseHandler(activity);
    	
    	setOnSceneTouchListener(this); 
    	
    	this.accelerometer = new AccelerometerManager(activity);
    	
    	createBackground();
    	createHUD();
    	LoadGameObjects();
    	createUpdates();
    }

    private void LoadGameObjects(){
    	detritos = new DetritosManager(resourcesManager);
    	bullets = new TirosManager(resourcesManager);
    	//Player tem de ser inicializado depois de todos os managers
    	nave = new Player(this, resourcesManager, detritos, 0.7f);
    	
    	attachChild(nave.getSprite());
    	attachChild(scoreText);
    	
    }
    
    @Override
    public void onBackKeyPressed(){
    	SceneManager.getInstance().createMenuScene();
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
    	float scoreScale = 1.0f;
    	
    	scoreText = new Text(scorePosX, scorePosY, resourcesManager.fontDefault, "Vidas: XX\nTempo: XXxxxxxXxxxXXxxxXXxxXxxxxxxX", vbom);
	    scoreText.setTextOptions(new TextOptions(HorizontalAlign.LEFT));
    	scoreText.setScale(scoreScale);
		scoreText.setColor(Color.BLUE);
    }
    
    private void createUpdates(){
    	registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
			
				resourcesManager.timer += pSecondsElapsed;
				gameUpdate(pSecondsElapsed);
				
				ticker += pSecondsElapsed;
				if (ticker >= 1) {
					ticker -= 1;
					tickEverySecond();
				}
				
				if (frameCount && !gameOver) {
					frameCount = false;
					RefreshText();
				}
				else frameCount = true;
			}
			@Override
			public void reset() {
			}
        });
    }
    
    private void gameUpdate(float pSecondsElapsed){
    	nave.Update(-accelerometer.getXAxis(), pSecondsElapsed);
    	detritos.Update(pSecondsElapsed, bullets);
    	bullets.Update(pSecondsElapsed);
    	
    	/*if(!nave.saltar && detritos.DetectColision(nave.Shape())){
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
    	}*/
    	
    	//debugs();
    }
    
    private void tickEverySecond() {
    	if (nave.lifes > 0) {
    		score++;
    	}
    }

    private void RefreshText() {
    	scoreText.setText("Shields: " + nave.getShield() + "\nVidas: " + nave.getLifes() + "\nTempo: " + String.format("%.0f", score));
    }
    
    private void debugs() {
    	Log.d("AccelerometerDEBUG", String.format("x:%.3f  y:%.3f  z:%.3f", accelerometer.getXAxis(), accelerometer.getYAxis(), accelerometer.getZAxis()));
    	//Log.d("TouchDEBUG", "x:")
    }
    
    public void gameOver() {
    	gameOver = true;
		maxScore = score;
		scoreText.setText("GAME OVER!\nyou survived for " + String.format("%.1f", maxScore) + " seconds.");
		//dataBase.insertRow("Player1", (int)maxScore, resourcesManager.timer);
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
		if (touchX > resourcesManager.camera.getWidth()/2)
			nave.disparar(bullets);
		else
			nave.setSalto(true);
	}
	
	private void onTouchMove(){
		
	}
	
	private void onTouchUp(){
		
	}
}