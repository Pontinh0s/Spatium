package com.example.computacaomovel.spaceship;
/*package com.example.computacaomovel.spaceship;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
//import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
//import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
//import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
//import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
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

import com.example.computacaomovel.spaceship.SceneManager.SceneType;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity  implements IOnSceneTouchListener {
        
	//WINDOWS
	private Intent w_credits;
	private Intent w_options;
	
	//OPTIONS
	public static Boolean mute = false;
	public static int Difficulty = 1;
	public static int musicVolume = 10;
	public static int soundVolume = 10;
	
    //-- Variaveis
	private ITexture mTexture;
    private BuildableBitmapTextureAtlas button_tiles;
    private BitmapTextureAtlas buttonAtlas;
    private TiledTextureRegion buttonRegion;
    private ITiledTextureRegion start_btn, op_btn, credits_btn, quit_btn;
    private Music mMusic, mCollision, mImpact;
    private Scene cena = new Scene();
    private SpriteBackground backgroundMenu, backgroundGame;
    
    //-----Camera------
    private Camera mCamera;
    private EngineOptions eo;
    static Point windowSize;
    //-----Fim camera----
    
    //GAME
    private GameLevel level;
    private EngineOptions gameEO;
    private Scene game = new Scene();
    private float timer=0;
    private Text score;
    private float maxScore;
    private Font mFont;
    private ITexture fontTexture;
    private Player nave;
    private Meteorito enemy;
    private Tiro bullets;
    private boolean gameOver = false;

    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;

    //SENSOR
    AccelerometerHelper mAccelerometerHelper;
    float accelerationX = 0;
    
    //GLOBAL
    SceneType estado = SceneType.SCENE_MENU;
    
    @Override
     public EngineOptions onCreateEngineOptions() {
    	Display defaultDisplay = getWindowManager().getDefaultDisplay();
    	try{
    		windowSize = new Point(defaultDisplay.getWidth(), defaultDisplay.getHeight());
    	}
    	catch (Exception e){
    		System.out.print(e.getMessage());
    		//defaultDisplay.getSize(windowSize);
    	}
    	mCamera = new Camera(0,0,windowSize.x, windowSize.y);
    	
        eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);
        eo.setWakeLockOptions(WakeLockOptions.SCREEN_BRIGHT);
        eo.getAudioOptions().setNeedsMusic(true);
        eo.getAudioOptions().setNeedsSound(true);
        //CreateNewGameEngine();

        level = new GameLevel(mCamera);
        
        //SENSOR
        mAccelerometerHelper = new AccelerometerHelper(this);
        
        return eo;
    }

    @Override
    public void onCreateResources(
                    OnCreateResourcesCallback pOnCreateResourcesCallback)
                    throws Exception {
    	//WINDOWS
    	w_credits = new Intent(this, Credits.class);
    	//w_heighscores = new Intent(this, Credits.class);
    	w_options = new Intent(this, Options.class);

        //MENU
        LoadButtons();
        LoadSounds();
        
        //GAME
        level.onCreateResources();
            
    	pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

	public void LoadButtons(){
		BitmapTextureAtlas startBitmap = new BitmapTextureAtlas(this.getTextureManager(), 720, 56, TextureOptions.DEFAULT);
        TextureRegion startRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(startBitmap, this, "StartGame.png", 0, 0);
        startBitmap.load();
        start_btn = TextureRegionFactory.extractTiledFromTexture(startRegion.getTexture(), 2, 1);

        BitmapTextureAtlas creditsBitmap = new BitmapTextureAtlas(this.getTextureManager(), 720, 56, TextureOptions.DEFAULT);
        TextureRegion creditsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(creditsBitmap, this, "Credits.png", 0, 0);
        creditsBitmap.load();
        credits_btn = TextureRegionFactory.extractTiledFromTexture(creditsRegion.getTexture(), 2, 1);

        BitmapTextureAtlas opBitmap = new BitmapTextureAtlas(this.getTextureManager(), 720, 56, TextureOptions.DEFAULT);
        TextureRegion opRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(opBitmap, this, "Options.png", 0, 0);
        opBitmap.load();
        op_btn = TextureRegionFactory.extractTiledFromTexture(opRegion.getTexture(), 2, 1);
	}
    
	public void LoadSounds(){
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
	
    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
                    throws Exception {
        cena.setBackground(backgroundMenu);
        cena.setTouchAreaBindingOnActionDownEnabled(true);
        CreateButtons();

        //level.StartGame(this, mEngine);
        
    	pOnCreateSceneCallback.onCreateSceneFinished(cena);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
    	game.attachChild(bullets.Shape());
    	game.attachChild(enemy.Shape());
    	game.attachChild(nave.Shape());
    	game.attachChild(score);
    	
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
     
    public void CreateButtons(){
        TiledSprite startButton = new TiledSprite(windowSize.x/2-start_btn.getWidth(0)/2, windowSize.y/4-start_btn.getHeight(0)/2, start_btn, getVertexBufferObjectManager()){
        @Override
        public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
                switch(pAreaTouchEvent.getAction()){
                case TouchEvent.ACTION_DOWN:
                    this.setCurrentTileIndex(1);
                    mCollision.play();
                    break;
		        case TouchEvent.ACTION_OUTSIDE:
	                this.setCurrentTileIndex(0);
	                break;
		        case TouchEvent.ACTION_UP:
		            this.setCurrentTileIndex(0);
		        	estado = mode.game;
		        	 gameReset();
		        	//mMusic.stop();
		        	//backMusic.play();
		        	mEngine.setScene(level.StartGame(mEngine));
		            break;
                }
                return true;
        }
        };
        cena.registerTouchArea(startButton);
        cena.attachChild(startButton);

        TiledSprite optionsButton = new TiledSprite(windowSize.x/2-op_btn.getWidth(0)/2, (float) (windowSize.y*2/4-op_btn.getHeight(0)/2), op_btn, getVertexBufferObjectManager()){
        @Override
        public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
                switch(pAreaTouchEvent.getAction()){
                case TouchEvent.ACTION_DOWN:
                        this.setCurrentTileIndex(1);
                        mCollision.play();
                        break;
        case TouchEvent.ACTION_OUTSIDE:
                this.setCurrentTileIndex(0);
                break;
        case TouchEvent.ACTION_UP:
            this.setCurrentTileIndex(0);
            startActivity(w_options);
            break;
                }
                return true;
        }
        };
        cena.registerTouchArea(optionsButton);
        cena.attachChild(optionsButton);

        TiledSprite creditsButton = new TiledSprite(windowSize.x/2-credits_btn.getWidth(0)/2, windowSize.y*3/4-credits_btn.getHeight(0)/2, credits_btn, getVertexBufferObjectManager()){
        @Override
        public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
                switch(pAreaTouchEvent.getAction()){
                case TouchEvent.ACTION_DOWN:
                        this.setCurrentTileIndex(1);
                        mCollision.play();
                        break;
        case TouchEvent.ACTION_OUTSIDE:
                this.setCurrentTileIndex(0);
                break;
        case TouchEvent.ACTION_UP:
            this.setCurrentTileIndex(0);
            startActivity(w_credits);
            break;
                }
                return true;
        }
        };
        cena.registerTouchArea(creditsButton);
        cena.attachChild(creditsButton);
    }
    
    //GAME
    public void CreateNewGameEngine(){
		mEngine = level.CreateNewGameEngine();
    }
    
    public void LoadBackgrounds(){
        TextureRegion myTextureRegion;
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 384, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "fundoMenu.jpg", 0, 0);
		mBitmapTextureAtlas.load();
		TextureRegion region=TextureRegionFactory.extractFromTexture(myTextureRegion.getTexture());
		Sprite sprite = new Sprite(0,0, region,mEngine.getVertexBufferObjectManager());
			sprite.setScale(windowSize.x/sprite.getWidth()*1.2f);
		backgroundMenu = new SpriteBackground(sprite);
    }
    
    public void gameUpdate(){
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
    			estado=mode.menu;
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
    
    @Override
    public synchronized void onPauseGame() {
        super.onPauseGame();
    	level.onPauseGame();
    }
    
    @Override
    public synchronized void onResume() {
        super.onResume();
        
        if(mMusic!=null){
            if(mute){
            	mMusic.setVolume(0);
            	mCollision.setVolume(0);
            	mImpact.setVolume(0);
            }
            else{
            	mMusic.setVolume(musicVolume/10);
            	mCollision.setVolume(soundVolume/10);
            	mImpact.setVolume(soundVolume/10);
            }
            enemy.speed = 3 + Difficulty*3;
        }
        
        if(mMusic != null)
        	mMusic.play();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (estado == mode.game))
        {
        	if (estado == mode.game)
        		estado = mode.menu;
        	
        	mEngine.setScene(cena);
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && (estado == mode.menu)){
        	System.runFinalizersOnExit(true);
        	System.exit(0);
        }
        return false;
    }

    
    //SENSOR
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		level.onSceneTouchEvent(pSceneTouchEvent);
		return false;
	}

}*/