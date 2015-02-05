package managers;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.IEaseFunction;

import MenuScenes.MainMenuScene;
import MenuScenes.SplashScene;
import Multiplayer.MultiplayerGame;
import android.util.Log;
import base_classes.BaseScene;


import scene.GameScene;
import scene.LoadingScene;



public class SceneManager
{
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene multiplayerScene;
    private BaseScene loadingScene;
    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    
    private BaseScene currentScene;
    
    private Engine engine = ResourcesManager.getInstance().engine;
    
    public enum SceneType{
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_MULTYPLAYER,
        SCENE_LOADING,
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void setScene(final BaseScene scene){
		engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }
    
    public void setScene(SceneType sceneType){
        switch (sceneType){
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_MULTYPLAYER:
                setScene(multiplayerScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            default:
                break;
        }
    }
    
    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback){
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    
    private void disposeSplashScene(){
    	try {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    	} catch (Exception e) {
    	}
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance(){
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType(){
        return currentSceneType;
    }
    
    public BaseScene getCurrentScene(){
        return currentScene;
    }

    //---------------------------------------------
    // MENU
    //---------------------------------------------
    
    public void createMenuScene(){
    	ResourcesManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
        disposeSplashScene();
    }
    
    public void loadMenuScene(final Engine mEngine){
        setScene(loadingScene);
        gameScene.disposeScene();
        ResourcesManager.getInstance().unloadGameGraphics();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuResources();
                setScene(menuScene);
            }
        }));
    }
    
    //---------------------------------------------
    // GAME
    //---------------------------------------------
    
    public void loadGameScene(final Engine mEngine){
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadMenuGraphics();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                gameScene = new GameScene();
                setScene(gameScene);
            }
        }));
    }
    
    //---------------------------------------------
    // MULTIPLAYER
    //---------------------------------------------
    
    public void loadMultiplayerScene(final Engine mEngine){
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadMenuGraphics();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                multiplayerScene = new MultiplayerGame();
                setScene(multiplayerScene);
            }
        }));
    }
}