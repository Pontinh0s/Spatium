package scene;

import managers.DetritosManager;
import managers.TirosManager;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import base_classes.Player;

public class MultiplayerGame extends GameScene {
	private Player otherPlayer;
	private TirosManager otherBullets;
	private float otherScore = 0;
	
	private Text otherText;
	
	@Override
	public void createScene(){
		super.createScene();
		
    	createHUD();
    	LoadGameObjects();
    	createUpdates();
	}
	
	private void createHUD(){
    	int scorePosX = 20;
    	int scorePosY = 10;
    	float scoreScale = 1.0f;

    	otherText = new Text(scorePosX, scorePosY, resourcesManager.fontDefault, "", 40, vbom);
    	otherText.setTextOptions(new TextOptions(HorizontalAlign.LEFT));
    	otherText.setScale(scoreScale);
    	otherText.setColor(Color.RED);
    }
	
	private void LoadGameObjects(){
		otherBullets = new TirosManager(resourcesManager);
    	//Player tem de ser inicializado depois de todos os managers
    	otherPlayer = new Player(this, resourcesManager, 0.7f);
    	
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
				}
				else frameCount = true;
			}
			@Override
			public void reset() {
			}
        });
	}
	private void RefreshText() {
		otherText.setText(String.format("Shields: %.3f\nVidas: %d\nTempo: %.0f",otherPlayer.getShield(), otherPlayer.getLifes(), otherScore));
    }
}