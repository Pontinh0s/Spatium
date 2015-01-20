package scene;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.TiledSprite;
//import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
//import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
//import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
//import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.input.touch.TouchEvent;
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
import org.andengine.util.debug.Debug;
import org.andengine.util.texturepack.TexturePackLibrary;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class Menu_prev extends BaseGameActivity {
	
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
	
	/* Define a cor de fundo RGBA */
	final float red = 0.3f;
	final float green = 0.5f;
	final float blue = 0.85f;
	final float alpha = 1;
	
	
	/* Cria o fundo */
	Background background = new Background(red, green, blue);
	
	//-----Camera------
	Camera mCamera = new Camera(0,0,CAMERA_WIDTH, CAMERA_HEIGHT);
	EngineOptions eo;
	static final int CAMERA_WIDTH = 320;
	static final int CAMERA_HEIGHT = 480;
	//-----Fim camera----
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		//eo = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);		
		eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);		
		eo.setWakeLockOptions(WakeLockOptions.SCREEN_DIM);
		eo.getAudioOptions().setNeedsMusic(true);
		return eo;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO Auto-generated method stub
		//BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
		
		// ---------- Botoes ----------------
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 360, 28, TextureOptions.DEFAULT);
		TextureRegion myTextureRegion;
		
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "StartGame.png", 0, 0);
		mBitmapTextureAtlas.load();
        start_btn = TextureRegionFactory.extractTiledFromTexture(myTextureRegion.getTexture(), 2, 1);

		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "Credits.png", 0, 0);
		mBitmapTextureAtlas.load();
		credits_btn = TextureRegionFactory.extractTiledFromTexture(myTextureRegion.getTexture(), 2, 1);

		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "Highscores.png", 0, 0);
		mBitmapTextureAtlas.load();
		high_btn = TextureRegionFactory.extractTiledFromTexture(myTextureRegion.getTexture(), 2, 1);

		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "Quit.png", 0, 0);
		mBitmapTextureAtlas.load();
		quit_btn = TextureRegionFactory.extractTiledFromTexture(myTextureRegion.getTexture(), 2, 1);

		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "Options.png", 0, 0);
		mBitmapTextureAtlas.load();
		op_btn = TextureRegionFactory.extractTiledFromTexture(myTextureRegion.getTexture(), 2, 1);
        
		
		// ---------------Sounds---------------
	    SoundFactory.setAssetBasePath("sounds/");
	    MusicFactory.setAssetBasePath("sounds/");
	    
		// Load our "music.mp3" file into a music object
	    try {
	    	mMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "a-call-to-duty.mp3");
		    mMusic.setLooping(true);
	    	mCollision = MusicFactory.createMusicFromAsset(getMusicManager(), this, "collision.ogg");
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		// -------------Sounds_END-------------
		
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		cena.setBackground(background);
		cena.setTouchAreaBindingOnActionDownEnabled(true);

		TiledSprite startButton = new TiledSprite(CAMERA_WIDTH/2-start_btn.getWidth(0)/2, CAMERA_HEIGHT/2-start_btn.getHeight(0)/2, start_btn, getVertexBufferObjectManager()){
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
	                    //if (this.getCurrentTileIndex() == 1) startGame();
	                    break;
		        }
		        return true;
		}
		};
		cena.registerTouchArea(startButton);
		cena.attachChild(startButton);

		TiledSprite highscoresButton = new TiledSprite(CAMERA_WIDTH/2-start_btn.getWidth(0)/2, CAMERA_HEIGHT/2-start_btn.getHeight(0)/2, start_btn, getVertexBufferObjectManager()){
	        @Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
		        switch(pAreaTouchEvent.getAction()){
		        case TouchEvent.ACTION_DOWN:
		                this.setCurrentTileIndex(1);
                        mCollision.play();
		                break;
                case TouchEvent.ACTION_CANCEL:
                        this.setCurrentTileIndex(0);
                        break;
                case TouchEvent.ACTION_UP:
                    //if (this.getCurrentTileIndex() == 1) startGame();
                    break;
		        }
		        return true;
		}
		};
		cena.registerTouchArea(startButton);
		cena.attachChild(startButton);

		TiledSprite optionsButton = new TiledSprite(CAMERA_WIDTH/2-start_btn.getWidth(0)/2, CAMERA_HEIGHT/2-start_btn.getHeight(0)/2, start_btn, getVertexBufferObjectManager()){
	        @Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
		        switch(pAreaTouchEvent.getAction()){
		        case TouchEvent.ACTION_DOWN:
		                this.setCurrentTileIndex(1);
                        mCollision.play();
		                break;
                case TouchEvent.ACTION_CANCEL:
                        this.setCurrentTileIndex(0);
                        break;
                case TouchEvent.ACTION_UP:
                    //if (this.getCurrentTileIndex() == 1) startGame();
                    break;
		        }
		        return true;
		}
		};
		cena.registerTouchArea(startButton);
		cena.attachChild(startButton);

		TiledSprite creditsButton = new TiledSprite(CAMERA_WIDTH/2-start_btn.getWidth(0)/2, CAMERA_HEIGHT/2-start_btn.getHeight(0)/2, start_btn, getVertexBufferObjectManager()){
	        @Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
		        switch(pAreaTouchEvent.getAction()){
		        case TouchEvent.ACTION_DOWN:
		                this.setCurrentTileIndex(1);
                        mCollision.play();
		                break;
                case TouchEvent.ACTION_CANCEL:
                        this.setCurrentTileIndex(0);
                        break;
                case TouchEvent.ACTION_UP:
                    //if (this.getCurrentTileIndex() == 1) startGame();
                    break;
		        }
		        return true;
		}
		};
		cena.registerTouchArea(startButton);
		cena.attachChild(startButton);

		TiledSprite quitButton = new TiledSprite(CAMERA_WIDTH/2-start_btn.getWidth(0)/2, CAMERA_HEIGHT/2-start_btn.getHeight(0)/2, start_btn, getVertexBufferObjectManager()){
	        @Override
		public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
		        switch(pAreaTouchEvent.getAction()){
		        case TouchEvent.ACTION_DOWN:
		                this.setCurrentTileIndex(1);
                        mCollision.play();
		                break;
                case TouchEvent.ACTION_CANCEL:
                        this.setCurrentTileIndex(0);
                        break;
                case TouchEvent.ACTION_UP:
                    //if (this.getCurrentTileIndex() == 1) startGame();
                    break;
		        }
		        return true;
		}
		};
		cena.registerTouchArea(startButton);
		cena.attachChild(startButton);
		
		
		
		pOnCreateSceneCallback.onCreateSceneFinished(cena);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
 	
	
	@Override
	public synchronized void onPauseGame() {
		if(mMusic != null && !mMusic.isPlaying()){
			mMusic.pause();
		}
		super.onResumeGame();
	}
	
	@Override
	public synchronized void onResumeGame() {
		if(mMusic != null && !mMusic.isPlaying()){
			mMusic.resume();
		}
		super.onResumeGame();
	}
	/* Music objects which loop continuously should be paused in
	* onPauseGame() of the activity life cycle
	*/
	
	
}