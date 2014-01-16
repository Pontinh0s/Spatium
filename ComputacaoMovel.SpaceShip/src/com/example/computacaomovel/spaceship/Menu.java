package com.example.computacaomovel.spaceship;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.widget.Toast;

public class Menu extends BaseGameActivity {
	
	//-- Variaveis
	BuildableBitmapTextureAtlas button_tiles;
	ITiledTextureRegion start_btn;
	ITiledTextureRegion high_btn;
	ITiledTextureRegion op_btn;
	ITiledTextureRegion credits_btn;
	ITiledTextureRegion quit_btn;
	Scene cena = new Scene();
	
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
		return eo;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
		start_btn  = (ITiledTextureRegion) new BitmapTexture(mEngine.getTextureManager(), LoadContent("StartGame.png"));
		high_btn  = (ITiledTextureRegion) new BitmapTexture(mEngine.getTextureManager(), LoadContent("Highscores.png"));
		op_btn  = (ITiledTextureRegion) new BitmapTexture(mEngine.getTextureManager(), LoadContent("Options.png"));
		credits_btn  = (ITiledTextureRegion) new BitmapTexture(mEngine.getTextureManager(), LoadContent("Credits.png"));
		quit_btn  = (ITiledTextureRegion) new BitmapTexture(mEngine.getTextureManager(), LoadContent("Quit.png"));
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		// Não sei o quê que isto faz... vai comentando o código para poder preceber :)
		cena.setTouchAreaBindingOnActionDownEnabled(true);
		pOnCreateSceneCallback.onCreateSceneFinished(cena);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	
	
	
	// ----------------------------------------------------------------------------------------------------------
	// --------------------------------------- BOTAO START ------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	
	/* Create the buttonSprite object in the center of the Scene */
	ButtonSprite buttonSpriteStart = new ButtonSprite(CAMERA_WIDTH * 0.5f,CAMERA_HEIGHT * 0.15f,start_btn,mEngine.getVertexBufferObjectManager()) {	
		
	/* Override the onAreaTouched() event method */
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {		
	/* If buttonSprite is touched with the finger */
		
	if(pSceneTouchEvent.isActionDown()){
		
	/* When the button is pressed, we can create an event
	* In this case, we're simply displaying a quick toast */
		Menu.this.runOnUiThread(new Runnable(){
		@Override 
		public void run() {	
		Toast.makeText(getApplicationContext(), "Button	Pressed!", Toast.LENGTH_SHORT).show();
		}
		});
	}
	
	/* In order to allow the ButtonSprite to swap tiled texture
	region
	* index on our buttonSprite object, we must return the super
	method */
	return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
	pTouchAreaLocalY);
	}
	};
	
	// ----------------------------------------------------------------------------------------------------------
	// --------------------------------------- BOTAO HIGHSCORE ------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	
	/* Create the buttonSprite object in the center of the Scene */
	ButtonSprite buttonSpriteHigh = new ButtonSprite(CAMERA_WIDTH * 0.5f,CAMERA_HEIGHT * 0.30f,high_btn,mEngine.getVertexBufferObjectManager()) {	
		
	/* Override the onAreaTouched() event method */
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {		
	/* If buttonSprite is touched with the finger */
		
	if(pSceneTouchEvent.isActionDown()){
		
	/* When the button is pressed, we can create an event
	* In this case, we're simply displaying a quick toast */
		Menu.this.runOnUiThread(new Runnable(){
		@Override 
		public void run() {	
		Toast.makeText(getApplicationContext(), "Button	Pressed!", Toast.LENGTH_SHORT).show();
		}
		});
	}
	
	/* In order to allow the ButtonSprite to swap tiled texture
	region
	* index on our buttonSprite object, we must return the super
	method */
	return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
	pTouchAreaLocalY);
	}
	};
	
	// ----------------------------------------------------------------------------------------------------------
	// --------------------------------------- BOTAO OPCAO ------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	
	/* Create the buttonSprite object in the center of the Scene */
	ButtonSprite buttonSpriteOP = new ButtonSprite(CAMERA_WIDTH * 0.5f,CAMERA_HEIGHT * 0.45f,op_btn,mEngine.getVertexBufferObjectManager()) {	
		
	/* Override the onAreaTouched() event method */
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {		
	/* If buttonSprite is touched with the finger */
		
	if(pSceneTouchEvent.isActionDown()){
		
	/* When the button is pressed, we can create an event
	* In this case, we're simply displaying a quick toast */
		Menu.this.runOnUiThread(new Runnable(){
		@Override 
		public void run() {	
		Toast.makeText(getApplicationContext(), "Button	Pressed!", Toast.LENGTH_SHORT).show();
		}
		});
	}
	/* In order to allow the ButtonSprite to swap tiled texture
	region
	* index on our buttonSprite object, we must return the super
	method */
	return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
	pTouchAreaLocalY);
	}
	};
	
	
	// ----------------------------------------------------------------------------------------------------------
	// --------------------------------------- BOTAO CREDITOS ------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	
	/* Create the buttonSprite object in the center of the Scene */
	ButtonSprite buttonSpriteCR = new ButtonSprite(CAMERA_WIDTH * 0.5f,CAMERA_HEIGHT * 0.6f,credits_btn,mEngine.getVertexBufferObjectManager()) {	
		
	/* Override the onAreaTouched() event method */
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {		
	/* If buttonSprite is touched with the finger */
		
	if(pSceneTouchEvent.isActionDown()){
		
	/* When the button is pressed, we can create an event
	* In this case, we're simply displaying a quick toast */
		Menu.this.runOnUiThread(new Runnable(){
		@Override 
		public void run() {	
		Toast.makeText(getApplicationContext(), "Button	Pressed!", Toast.LENGTH_SHORT).show();
		}
		});
	}
	/* In order to allow the ButtonSprite to swap tiled texture
	region
	* index on our buttonSprite object, we must return the super
	method */
	return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
	pTouchAreaLocalY);
	}
	};
	
	
	// ----------------------------------------------------------------------------------------------------------
	// --------------------------------------- BOTAO QUIT ------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------------
	
	/* Create the buttonSprite object in the center of the Scene */
	ButtonSprite buttonSpriteQuit = new ButtonSprite(CAMERA_WIDTH * 0.5f,CAMERA_HEIGHT * 0.75f,quit_btn,mEngine.getVertexBufferObjectManager()) {	
		
	/* Override the onAreaTouched() event method */
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {		
	/* If buttonSprite is touched with the finger */
		
	if(pSceneTouchEvent.isActionDown()){
		
	/* When the button is pressed, we can create an event
	* In this case, we're simply displaying a quick toast */
		Menu.this.runOnUiThread(new Runnable(){
		@Override 
		public void run() {	
		Toast.makeText(getApplicationContext(), "Button	Pressed!", Toast.LENGTH_SHORT).show();
		}
		});
	}
	/* In order to allow the ButtonSprite to swap tiled texture
	region
	* index on our buttonSprite object, we must return the super
	method */
	return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
	pTouchAreaLocalY);
	}
	};
	
	
	
	// ---------------------------------- BOTOES FIM ----------------------------------------
	
	
	public IInputStreamOpener LoadContent(final String pathName){
		IInputStreamOpener stream =
				new IInputStreamOpener()  {
				@Override
				public InputStream open() throws IOException {
	                return getAssets().open(pathName);
				}
			};
		
		return stream;
	}
	
	
	
	
}