package com.example.computacaomovel.spaceship;

import java.io.IOException;
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
import org.andengine.entity.text.Text;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.view.MotionEvent;

public class Game extends BaseGameActivity implements IOnSceneTouchListener, IAccelerationListener{

	Scene cena = new Scene();
	Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	EngineOptions eo;
	static final int CAMERA_WIDTH = 480;
	static final int CAMERA_HEIGHT = 320;
    private Timer timer;
    private Music backMusic, collision, laser;
    private Text score;
    private Font mFont;
    private Player nave;

	@Override
	public EngineOptions onCreateEngineOptions() {
		//eo = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);		
		eo = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);		
		eo.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		eo.getAudioOptions().setNeedsMusic(true);
		
		return eo;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new FixedStepEngine(eo, 200);
	}

	@Override
	public void onCreateResources (OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		
		// ---------------Font---------------
	    final ITexture fontTexture = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFont = FontFactory.createFromAsset
	    			(getFontManager(), fontTexture, getAssets(),
	    					"Peric.ttf", 40, true, Color.BLACK_ABGR_PACKED_INT);
	    mFont.load();
		// -------------Font_END-------------

	    
	    
		// ---------------Sounds---------------
	    MusicFactory.setAssetBasePath("sounds/");
	    
	    try {
	    	backMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "background.ogg");
	    	collision = MusicFactory.createMusicFromAsset(getMusicManager(), this, "background.ogg");
	    	laser = MusicFactory.createMusicFromAsset(getMusicManager(), this, "background.ogg");
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
		// -------------Sounds_END-------------
	    
	    

		// ---------------Player---------------
	    nave = new Player(CAMERA_WIDTH, CAMERA_HEIGHT);
	    // -------------Player_END-------------
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		cena.setOnSceneTouchListener(this);

		cena.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				Update();
			}
		});
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
		
	}


	// Update
	private void Update() {
		// TODO Auto-generated method stub
		
	}

	// Para permitir a leitura do touch no jogo
	@Override
	public boolean onSceneTouchEvent(Scene pScene, final TouchEvent pSceneTouchEvent)
	{
	    if (pSceneTouchEvent.isActionDown())
	    {
	    	if (pSceneTouchEvent.getX() > CAMERA_WIDTH/2)
	    		nave.disparar();
	    	if (pSceneTouchEvent.getX() < CAMERA_WIDTH/2)
	    		nave.saltar();
	    }
	    return false;
	}
	
	// Para permitir a leitura do acelerómetro no jogo
	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}

}