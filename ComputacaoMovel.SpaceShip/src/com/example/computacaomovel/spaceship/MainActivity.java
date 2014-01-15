package com.example.computacaomovel.spaceship;

import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.FixedResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Rect;
import android.view.MotionEvent;

public class MainActivity extends BaseGameActivity {

<<<<<<< HEAD
	/**
	 *-------------- Variaveis MENU ----------------
	 */
	
	/**
	 *---------------- MENU FIM ---------------------
	 */
=======
	//ISTO.É.UMA.ALTERAÇÃO
>>>>>>> branch 'master' of https://github.com/Davidepaalte/ComputacaoMovel.git
	
	Scene cena = new Scene();
	Camera mCamera = new Camera(0,0,CAMERA_WIDTH, CAMERA_HEIGHT);
	EngineOptions eo;
	static final int CAMERA_WIDTH = 480;
	static final int CAMERA_HEIGHT = 320;
	float R = 0, G = 0, B = 0;
	int r = -1, g = -1, b = -1;

	@Override
	public EngineOptions onCreateEngineOptions() {
		//eo = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);		
		eo = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);		
		eo.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return eo;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new FixedStepEngine(eo, 200);
	}
	
	public boolean onTouchEvent (MotionEvent event){
		int myEventAction = event.getAction();
		
		float x = event.getX();
		float y = event.getY();
		
		switch (myEventAction) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		
		cena.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				Update();
			}
		});
		
		pOnCreateSceneCallback.onCreateSceneFinished(cena);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	private void Update() {
		
		if(R >= 1) r =- 1;
			else if(R <= 0) r = 1;
		if(G >= 1) g =- 1;
			else if(G <= 0) g = 1;
		if(B >= 1) b =- 1;
			else if(B <= 0) b = 1;
		
		R = R + 0.0015f*r;
		G = G + 0.001f*g;
		B = B + 0.0005f*b;
		
		cena.setBackground(new Background(new Color(R,G,B)));
	}
}
