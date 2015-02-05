package Multiplayer;

import managers.DetritosManager;
import managers.SceneManager.SceneType;
import managers.TirosManager;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import scene.GameScene;
import base_classes.Player;

public class MultiplayerGame extends GameScene {
	private UDPclient otherListener;
	
	private Player oldPlayer;
	private Player otherPlayer;
	
	private TirosManager otherBullets;
	private boolean isConected = false;
	
	private Text otherText;
	
	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MULTYPLAYER;
	}
		
	@Override
	public void onBackKeyPressed() {
		super.onBackKeyPressed();
	}
	
	@Override
	public void disposeScene() {
		super.disposeScene();
	}
	
	@Override
	public void gameOver() {
		super.gameOver();
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return super.onSceneTouchEvent(pScene, pSceneTouchEvent);
	}
	
	
	@Override
	public void createScene(){
		super.createScene();
		
    	createHUD();
    	LoadGameObjects();
    	createUpdates();
    	otherListener = new UDPclient();
    	otherListener.start();
	}
	
	private void createHUD(){
    	int scorePosX = (int)(resourcesManager.camera.getWidth() - 20);
    	int scorePosY = 30;
    	float scoreScale = 1.0f;
    	
    	otherText = new Text(scorePosX, scorePosY, resourcesManager.fontDefault, "", 40, vbom);
    	otherText.setTextOptions(new TextOptions(HorizontalAlign.RIGHT));
    	otherText.setScale(scoreScale);
    	otherText.setColor(Color.RED);
    }
	
	private void LoadGameObjects(){
		otherBullets = new TirosManager(resourcesManager);
    	//Player tem de ser inicializado depois de todos os managers
    	otherPlayer = new Player(this, resourcesManager, otherBullets, 0.7f);
    	
    	oldPlayer = nave;
    	
    	attachChild(otherPlayer.getSprite());
    	attachChild(otherText);
	}
	
	private void createUpdates(){
		registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				if (frameCount && !gameOver) {
					frameCount = false;
					RefreshText();
					Update();
				}
				else frameCount = true;
			}
			@Override
			public void reset() {
			}
        });
	}
	
	private void Update() {
		otherListener.setPosition(nave.getX());
		if (oldPlayer.getLifes() != nave.getLifes())
			otherListener.setLifes(nave.getLifes());
		if (oldPlayer.getShield() != nave.getShield())
			otherListener.setShields(nave.getShield());
		
		if (oldPlayer.getSalto() != nave.getSalto())
			otherListener.setJump(nave.getSalto());
		
		oldPlayer = nave;
	}
	
	private void RefreshText() {
		otherText.setText(String.format("Shields: %.3f\nVidas: %d",otherPlayer.getShield(), otherPlayer.getLifes()));
    }
	
	
	///-------------------------------
	/// OTHER MOVEMENTS
	///-------------------------------
	
	private void otherFire() {
		otherPlayer.disparar();
	}
	
	private void otherJump() {
		otherPlayer.setSalto(true);
	}
	
	private void otherChangePosition(float newPosition) {
		otherPlayer.setX(newPosition);
	}

	private void otherShields(int newShiendNumber) {
		otherPlayer.setShield(newShiendNumber);
	}
	
	private void otherLifes(int newLifeNumber) {
		otherPlayer.setLifes(newLifeNumber);
	}
	
	private void otherListener(){
		Thread listener = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
		
		listener.start();
	}
}