package MenuScenes;

import managers.SceneManager.SceneType;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

import base_classes.BaseScene;

public class SplashScene extends BaseScene{

	private Sprite splash;
	
	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));
		splash = new Sprite(0, 0, resourcesManager.tSplashScreen, vbom);
		splash.setPosition(resourcesManager.camera.getWidth()/2, resourcesManager.camera.getHeight()/2);
		splash.setPosition(splash.getX() - splash.getWidth()/2, splash.getY()- splash.getWidth()/4);
		attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
	    splash.detachSelf();
	    splash.dispose();
	    this.detachSelf();
	    this.dispose();
	}

}
