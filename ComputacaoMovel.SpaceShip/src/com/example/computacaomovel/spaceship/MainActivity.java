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
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
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
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity  implements IOnSceneTouchListener, IAccelerationListener{
        
        //-- Variaveis
        ITexture mTexture;
        BuildableBitmapTextureAtlas button_tiles;
        BitmapTextureAtlas buttonAtlas;
        TiledTextureRegion buttonRegion;
        ITiledTextureRegion start_btn;
        ITiledTextureRegion high_btn;
        ITiledTextureRegion op_btn;
        ITiledTextureRegion credits_btn;
        ITiledTextureRegion quit_btn;
        Music mMusic;
        Music mCollision;
        Scene cena = new Scene();
        SpriteBackground background;
        
        //-----Camera------
        Camera mCamera = new Camera(0,0,CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions eo;
        static final int CAMERA_WIDTH = 320;
        static final int CAMERA_HEIGHT = 480;
        //-----Fim camera----
        
        //GAME
        Scene game = new Scene();
    	Camera gameCamera = new Camera(0, 0, CAMERA_HEIGHT, CAMERA_WIDTH);
        private float timer;
        private Music backMusic, collision, laser;
        private Text score;
        private Font mFont;
        private Player nave;
        private Meteorito enemy;
        private Tiro bullets;
        AccelerationData acceleration;
        mode estado = mode.menu;
        
        
        //GLOBAL
        public enum mode{
        	menu,
        	game;
        }
        
        @Override
        public EngineOptions onCreateEngineOptions() {
                eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);                
                eo.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
                eo.getAudioOptions().setNeedsMusic(true);
                eo.getAudioOptions().setNeedsSound(true);
                
                if(estado==mode.game){
                	CreateNewGameEngine();
                }
                return eo;
        }

        @Override
        public void onCreateResources(
                        OnCreateResourcesCallback pOnCreateResourcesCallback)
                        throws Exception {
            BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
            MusicFactory.setAssetBasePath("sounds/");
            FontFactory.setAssetBasePath("fonts/");

            if(estado==mode.menu){
            //MENU
            LoadButtons();
            LoadSounds();
            }
            else{
            //GAME
            LoadGameFont();
            LoadGameMusic();
            LoadGameObjects();
            }
                
        	pOnCreateResourcesCallback.onCreateResourcesFinished();
        }
    
    	public void LoadButtons(){
    		BitmapTextureAtlas startBitmap = new BitmapTextureAtlas(this.getTextureManager(), 360, 28, TextureOptions.DEFAULT);
            TextureRegion startRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(startBitmap, this, "StartGame.png", 0, 0);
            startBitmap.load();
            start_btn = TextureRegionFactory.extractTiledFromTexture(startRegion.getTexture(), 2, 1);

            BitmapTextureAtlas creditsBitmap = new BitmapTextureAtlas(this.getTextureManager(), 360, 28, TextureOptions.DEFAULT);
            TextureRegion creditsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(creditsBitmap, this, "Credits.png", 0, 0);
            creditsBitmap.load();
            credits_btn = TextureRegionFactory.extractTiledFromTexture(creditsRegion.getTexture(), 2, 1);

            BitmapTextureAtlas highBitmap = new BitmapTextureAtlas(this.getTextureManager(), 360, 28, TextureOptions.DEFAULT);
            TextureRegion highRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(highBitmap, this, "Highscores.png", 0, 0);
            highBitmap.load();
            high_btn = TextureRegionFactory.extractTiledFromTexture(highRegion.getTexture(), 2, 1);

            BitmapTextureAtlas opBitmap = new BitmapTextureAtlas(this.getTextureManager(), 360, 28, TextureOptions.DEFAULT);
            TextureRegion opRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(opBitmap, this, "Options.png", 0, 0);
            opBitmap.load();
            op_btn = TextureRegionFactory.extractTiledFromTexture(opRegion.getTexture(), 2, 1);

            
			BuildableBitmapTextureAtlas texBanana = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			TextureRegion regBanana = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texBanana, this.getAssets(), "fundoMenu.jpg");
			texBanana.load();
			background = new SpriteBackground(new Sprite(CAMERA_WIDTH/2-regBanana.getWidth()/2, 0,TextureRegionFactory.extractFromTexture(texBanana),mEngine.getVertexBufferObjectManager()));
    	}

    	public void LoadSounds(){
    		try {
                mMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "a-call-to-duty.mp3");
                mMusic.setLooping(true);
                mMusic.setVolume(0.7f);
                mCollision = MusicFactory.createMusicFromAsset(getMusicManager(), this, "collision.ogg");
            } catch (IOException e) {
                    e.printStackTrace();
            }
    	}
    	
        @Override
        public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
                        throws Exception {
            cena.setBackground(background/*new Background(0.35f,0.2f,0.8f)*/);
            cena.setTouchAreaBindingOnActionDownEnabled(true);

            if(estado == mode.menu){
	            //MENU
	            CreateButtons();
            }
            else{	
	            //GAME
	            game.registerUpdateHandler(new IUpdateHandler() {
					@Override
					public void reset() {
					}
					
					@Override
					public void onUpdate(float pSecondsElapsed) {
						timer+=pSecondsElapsed;
						gameUpdate();
					}
	            });
            }
            
            pOnCreateSceneCallback.onCreateSceneFinished(cena);
        }

        @Override
        public void onPopulateScene(Scene pScene,
                        OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
                pOnPopulateSceneCallback.onPopulateSceneFinished();
        }
         
        public void CreateButtons(){

            TiledSprite startButton = new TiledSprite(CAMERA_WIDTH/2-start_btn.getWidth(0)/2, CAMERA_HEIGHT/5-start_btn.getHeight(0)/2, start_btn, getVertexBufferObjectManager()){
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
                if (this.getCurrentTileIndex() == 1){
                	//estado = mode.game;
                	mEngine.setScene(game);
                }
                break;
                    }
                    return true;
            }
            };
            cena.registerTouchArea(startButton);
            cena.attachChild(startButton);

            TiledSprite highscoresButton = new TiledSprite(CAMERA_WIDTH/2-high_btn.getWidth(0)/2, CAMERA_HEIGHT*2/5-high_btn.getHeight(0)/2, high_btn, getVertexBufferObjectManager()){
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
                //if (this.getCurrentTileIndex() == 1) startGame();
                break;
                    }
                    return true;
            }
            };
            cena.registerTouchArea(highscoresButton);
            cena.attachChild(highscoresButton);

            TiledSprite optionsButton = new TiledSprite(CAMERA_WIDTH/2-op_btn.getWidth(0)/2, (float) (CAMERA_HEIGHT*3/5-op_btn.getHeight(0)/2), op_btn, getVertexBufferObjectManager()){
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
                //if (this.getCurrentTileIndex() == 1) startGame();
                break;
                    }
                    return true;
            }
            };
            cena.registerTouchArea(optionsButton);
            cena.attachChild(optionsButton);

            TiledSprite creditsButton = new TiledSprite(CAMERA_WIDTH/2-credits_btn.getWidth(0)/2, CAMERA_HEIGHT*4/5-credits_btn.getHeight(0)/2, credits_btn, getVertexBufferObjectManager()){
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
                //if (this.getCurrentTileIndex() == 1) startGame();
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
        	eo = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
        	eo.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
    		//eo.getAudioOptions().setNeedsMusic(true);
    		//eo.getAudioOptions().setNeedsSound(true);
    		mEngine = new FixedStepEngine(eo, 200);
        }
        
        public void LoadGameFont(){
        	final ITexture fontTexture = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    		mFont = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 256, 256, this.getAssets(),
    			    "Peric.ttf", 46, true, android.graphics.Color.BLACK);
    	    mFont.load();
    	    score = new Text(5, 5, mFont, "Vidas: "+nave.lifes+"\nTempo: "+timer, mEngine.getVertexBufferObjectManager());
        }
        
        public void LoadGameMusic(){
	        try {
		    	backMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "background.ogg");
		    	backMusic.setLooping(true);
		    	//collision = mCollision;
		    	laser = MusicFactory.createMusicFromAsset(getMusicManager(), this, "laser.ogg");
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
        }
        
        public void LoadGameObjects() throws IOException{
        	nave = new Player(CAMERA_HEIGHT, CAMERA_WIDTH);
        	nave.LoadContent(this);
        	enemy = new Meteorito(mEngine, this, CAMERA_HEIGHT);
        	bullets = new Tiro(this,mEngine);
        }
        
        public void gameUpdate(){
        	nave.Update(acceleration.getX(), acceleration.getY(), mEngine.getVertexBufferObjectManager());
        	enemy.Update();
        	bullets.Update();
        	score.setText("Vidas: "+nave.lifes+"\nTempo: "+timer);
        }
        
        @Override
        public synchronized void onPauseGame() {
            mMusic.pause();
            super.onResumeGame();
        }
        
        @Override
        public synchronized void onResumeGame() {
                    if(mMusic != null && !mMusic.isPlaying()){
                            mMusic.resume();
                    }
                    super.onResumeGame();
            }

		@Override
		public void onAccelerationAccuracyChanged(
				AccelerationData pAccelerationData) {
			acceleration = pAccelerationData;
		}

		@Override
		public void onAccelerationChanged(AccelerationData pAccelerationData) {
			acceleration = pAccelerationData;
		}

		@Override
		public boolean onSceneTouchEvent(Scene pScene,
				TouchEvent pSceneTouchEvent) {
			if (pSceneTouchEvent.isActionDown())
		    {
		    	if (pSceneTouchEvent.getX() > CAMERA_WIDTH/2)
		    		nave.disparar();
		    	if (pSceneTouchEvent.getX() < CAMERA_WIDTH/2)
		    		nave.saltar();
		    }
			return false;
		}
    }