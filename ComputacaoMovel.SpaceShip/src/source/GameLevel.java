package source;
/*package com.example.computacaomovel.spaceship;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.texturepack.TexturePackLibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class GameLevel extends Scene{

	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;
    
	private ITexture mTexture;
    private ITiledTextureRegion start_btn, op_btn, credits_btn, quit_btn;
    private Music mMusic, mCollision, mImpact;
    private Scene cena = new Scene();
    private SpriteBackground backgroundGame;
    
    private EngineOptions eo;
    //-----Fim camera----
    
    //GAME
    private Scene game = new Scene();
    private float timer=0;
    private Text score;
    private float maxScore;
    private Font mFont;
    private ITexture fontTexture;
    private Player nave;
    private Meteorito enemy;
    private Tiro bullets;
    boolean gameOver = false;

    //SENSOR
    AccelerometerHelper mAccelerometerHelper;
    float accelerationX=0;
    
    public GameLevel(){
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        createScene();
    }

    public void onCreateResources(BitmapTextureAtlas fontFactory, BitmapTextureAtlasTextureRegionFactory bitmapFactory){
    	//DEFAULTS
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
        MusicFactory.setAssetBasePath("sounds/");
        FontFactory.setAssetBasePath("fonts/");
        
    	LoadGameFont();
    	LoadBackground();
    	LoadGameObjects();
    	LoadSounds();
    }

    private void LoadGameFont(){
    	//FontFactory.setAssetBasePath("font/");
    }
    
    private void LoadBackground(){
		TextureRegion myTextureRegion;
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 576, 324, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "earth-wallpaper-1080p.png", 0, 0);
		mBitmapTextureAtlas.load();
		TextureRegion region = TextureRegionFactory.extractFromTexture(myTextureRegion.getTexture());
		Sprite sprite = new Sprite(0,0, region, getVertexBufferObjectManager());
			sprite.setScale(sprite.getWidth()*1.6f/windowSize.x);
			sprite.setX(-(sprite.getWidth()-windowSize.x)/2);
			sprite.setY(-(sprite.getHeight()-windowSize.y)/2);
		backgroundGame = new SpriteBackground(sprite);
    }

    private void LoadGameObjects(){
    	nave = new Player(windowSize.x, windowSize.y, this, 0.7f);
    	enemy = new Meteorito(this, windowSize.x, windowSize.y);
    	bullets = new Tiro(this);
    }

	private void LoadSounds(){
		try {
			
            mMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "a-call-to-duty.mp3");
            mMusic.setLooping(true);
            mMusic.setVolume(0.7f);
            mMusic.play();
            mCollision = MusicFactory.createMusicFromAsset(getMusicManager(), this, "collision.ogg");
            mImpact = MusicFactory.createMusicFromAsset(getMusicManager(), this, "impact.ogg");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public Scene StartGame(final org.andengine.engine.Engine mEngine){
		game.setBackground(backgroundGame);
        game.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				timer += pSecondsElapsed;
				gameUpdate(mEngine);
			}
			@Override
			public void reset() {
			}
        });
        
        onPopulateScene();
        return game;
    }

    private void onPopulateScene(){
        	game.attachChild(bullets.Shape());
        	game.attachChild(enemy.Shape());
        	game.attachChild(nave.Shape());
        	game.attachChild(score);
    }
    
    public org.andengine.engine.Engine CreateNewGameEngine(){
		return new FixedStepEngine(eo, 200);
    }
    
    private void gameUpdate(org.andengine.engine.Engine mEngine){
    	nave.Update(-AccelerometerHelper.TILT);
    	enemy.Update();
    	bullets.Update();
    	
    	//COLISOES
    	if(enemy.DetectColision(bullets.Shape())){
    		bullets.Destroy();
    		enemy.Restart();
    	}
    	else if(!nave.saltar && enemy.DetectColision(nave.Shape())){
    		nave.lifes--;
    		mImpact.stop();
    		mImpact.play();
    		if(nave.lifes==0){
    			gameOver=true;
    			maxScore=timer;
        		score.setScale(0.6f);
    		}
    		else if(nave.lifes==-1){
    			//estado=mode.menu;
    			mEngine.setScene(cena);
    		}
    		enemy.Restart();
    	}
    	
    	//SCORE
    	if(!gameOver){
    		score.setText("Vidas: " + nave.lifes + "\nTempo: " + String.format("%.0f", timer));
    	}
    	else{
    		score.setText("GAME OVER!\nyou survived for " + String.format("%.1f", maxScore) + " seconds.");
    	}
    }
    
    public void gameReset(){
    	nave.lifes=5;
    	timer=0;
    	enemy.Restart();
    	gameOver=false;
    }
    
    public synchronized void onPauseGame() {
    	mMusic.pause();
    }

    
    //SENSOR
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionDown())
	    {
	    	if (pSceneTouchEvent.getX() > windowSize.x/2)
    			nave.disparar(bullets);
	    	if (pSceneTouchEvent.getX() < windowSize.x/2)
	    		nave.saltar();
	    }
		return false;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
	    EngineOptions gameEO = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
    	gameEO.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        gameEO.getAudioOptions().setNeedsMusic(true);
        gameEO.getAudioOptions().setNeedsSound(true);

		//this.game.setOnSceneTouchListener(game);
		return gameEO;
	}
}*/