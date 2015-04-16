package menus;

import managers.SceneManager;
import managers.SceneManager.SceneType;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;

import source.BaseScene;
import basicClasses.Button;


public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

	private void createMenuButtons()
	{
		float midScrX = resourcesManager.camera.getWidth()/2,
			  midScrY = resourcesManager.camera.getHeight()/2;
		
		new Button(midScrX, midScrY - resourcesManager.camera.getHeight()/6, resourcesManager.ttStart, vbom, this) {
			@Override
			public void ActionUp() {
				SceneManager.getInstance().loadGameScene(engine);
			}
			@Override
			public void ActionDown() {
				//resourcesManager.mButtonSound.play();
			}
		};
		
		new Button(midScrX, midScrY + resourcesManager.camera.getHeight()/6, resourcesManager.ttHighscores, vbom, this) {
			@Override
			public void ActionUp() {
				
			}
			@Override
			public void ActionDown() {
				//resourcesManager.mButtonSound.play();
			}
		};
		
	}
	
	public void playMusic() {
		resourcesManager.mMenuMusic.play();
	}
	
	@Override
	public void createScene() {
		playMusic();
		createBackground();
		createMenuButtons();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		
	}
	
	private void createBackground()
	{
	    attachChild(new Sprite(resourcesManager.tBackgroundMenu.getWidth()/2, resourcesManager.tBackgroundMenu.getHeight()/2, resourcesManager.tBackgroundMenu, vbom));
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		return false;
	}
}
