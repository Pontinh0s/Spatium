package com.example.computacaomovel.spaceship;

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

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
    private Camera mCamera = new Camera(0,0,CAMERA_WIDTH, CAMERA_HEIGHT);
    private EngineOptions eo;
    static final int CAMERA_WIDTH = 320;
    static final int CAMERA_HEIGHT = 480;
    //-----Fim camera----
    
    //GAME
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
    boolean gameOver = false;

    //SENSOR
    AccelerometerHelper mAccelerometerHelper;
    float accelerationX=0;
    
    //GLOBAL
    mode estado = mode.menu;
    public enum mode{
    	menu,
    	game;
    }
    
    @Override
     public EngineOptions onCreateEngineOptions() {
    	//if(estado==mode.menu){
            eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);
            eo.setWakeLockOptions(WakeLockOptions.SCREEN_BRIGHT);
    	//}
    	//else{
    		gameEO = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
    		gameEO.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        	CreateNewGameEngine();
    	//}

        eo.getAudioOptions().setNeedsMusic(true);
        eo.getAudioOptions().setNeedsSound(true);
        gameEO.getAudioOptions().setNeedsMusic(true);
        gameEO.getAudioOptions().setNeedsSound(true);

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
    	
    	//GAME
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
        MusicFactory.setAssetBasePath("sounds/");
        FontFactory.setAssetBasePath("fonts/");

        //MENU
        LoadButtons();
        LoadSounds();
        LoadBackgrounds();
        
        //GAME
        LoadGameObjects();
        LoadGameFont();
            
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

		game.setBackground(backgroundGame);
		game.setOnSceneTouchListener(this);
        game.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
	        	if(estado == mode.game){
					timer += pSecondsElapsed;
					gameUpdate();
	        	}
			}
			@Override
			public void reset() {
			}
        });
        
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

        TiledSprite startButton = new TiledSprite(CAMERA_WIDTH/2-start_btn.getWidth(0)/2, CAMERA_HEIGHT/4-start_btn.getHeight(0)/2, start_btn, getVertexBufferObjectManager()){
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
        	mEngine.setScene(game);
            break;
                }
                return true;
        }
        };
        cena.registerTouchArea(startButton);
        cena.attachChild(startButton);

        TiledSprite optionsButton = new TiledSprite(CAMERA_WIDTH/2-op_btn.getWidth(0)/2, (float) (CAMERA_HEIGHT*2/4-op_btn.getHeight(0)/2), op_btn, getVertexBufferObjectManager()){
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

        TiledSprite creditsButton = new TiledSprite(CAMERA_WIDTH/2-credits_btn.getWidth(0)/2, CAMERA_HEIGHT*3/4-credits_btn.getHeight(0)/2, credits_btn, getVertexBufferObjectManager()){
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
		mEngine = new FixedStepEngine(eo, 200);
    }
    
    public void LoadGameFont(){
    	FontFactory.setAssetBasePath("font/");
    	fontTexture = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        this.mFont = new Font(getFontManager(), fontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, new Color(1f,0.97f,0.57f));
		mFont.load();
	    score = new Text(-20, 10, mFont, "Vidas: XX\nTempo: XXxxxxxXxxxXXxxxXXxxXxxxxxxX", mEngine.getVertexBufferObjectManager());
	    score.setScale(0.5f);
    }
    
    public void LoadBackgrounds(){
        TextureRegion myTextureRegion;
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 384, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "fundoMenu.jpg", 0, 0);
		mBitmapTextureAtlas.load();
		TextureRegion region=TextureRegionFactory.extractFromTexture(myTextureRegion.getTexture());
		Sprite sprite = new Sprite(0,0, region,mEngine.getVertexBufferObjectManager());
			sprite.setScale(CAMERA_WIDTH/sprite.getWidth()*1.2f);
		backgroundMenu = new SpriteBackground(sprite);

		mBitmapTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 576, 324, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "earth-wallpaper-1080p.png", 0, 0);
		mBitmapTextureAtlas.load();
		region=TextureRegionFactory.extractFromTexture(myTextureRegion.getTexture());
		sprite = new Sprite(0,0, region,mEngine.getVertexBufferObjectManager());
			sprite.setScale(sprite.getWidth()*1.6f/CAMERA_WIDTH);
			sprite.setX(-(sprite.getWidth()-CAMERA_WIDTH)/2);
			sprite.setY(-(sprite.getHeight()-CAMERA_HEIGHT)/2);
		backgroundGame = new SpriteBackground(sprite);
    }

    public void LoadGameObjects() throws IOException{
    	nave = new Player(CAMERA_WIDTH, CAMERA_HEIGHT, this, 0.7f);
    	enemy = new Meteorito(this, CAMERA_WIDTH, CAMERA_HEIGHT);
    	bullets = new Tiro(this);
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
    	mMusic.pause();
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
	public boolean onSceneTouchEvent(Scene pScene,
			TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionDown())
	    {
	    	if (pSceneTouchEvent.getX() > CAMERA_WIDTH/2)
    			nave.disparar(bullets);
	    	if (pSceneTouchEvent.getX() < CAMERA_WIDTH/2)
	    		nave.saltar();
	    }
		return false;
	}

}