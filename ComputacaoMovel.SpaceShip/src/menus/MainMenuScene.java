package menus;

import managers.ParallaxManager;
import managers.SceneManager;
import managers.SceneManager.SceneType;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import source.BaseScene;
import Parallax.ParallaxLayer;
import Parallax.ParallaxManager_v2;
import basicClasses.Button;


public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

	private void createMenuButtons()
	{
		float midScrX = resourcesManager.camera.getWidth()/2,
			  maxY = 5*resourcesManager.camera.getHeight()/6,
			  bottomSpaceY = resourcesManager.camera.getHeight() - resourcesManager.camera.getHeight()/12;
		
		new Button(midScrX, maxY/4, resourcesManager.ttStart, vbom, this) {
			@Override
			public void ActionUp() {
				SceneManager.getInstance().loadTestLevel(engine);
			}
			@Override
			public void ActionDown() {
				//resourcesManager.mButtonSound.play();
			}
		};
		
		new Button(midScrX, 2*maxY/4, resourcesManager.ttOptions, vbom, this) {
			@Override
			public void ActionUp() {
				
			}
			@Override
			public void ActionDown() {
				//resourcesManager.mButtonSound.play();
			}
		};
		
		new Button(midScrX, 3*maxY/4, resourcesManager.ttHighscores, vbom, this) {
			@Override
			public void ActionUp() {
				
			}
			@Override
			public void ActionDown() {
				//resourcesManager.mButtonSound.play();
			}
		};

		
		new Button(resourcesManager.camera.getWidth()/5, bottomSpaceY, resourcesManager.ttMute, vbom, this) {
			boolean isMuted = false;
			@Override
			public void ActionUp() {
				isMuted = !isMuted;
				
				if (isMuted) {
					
				}
				else {
					
				}
			}
			@Override
			public void ActionDown() {
				//resourcesManager.mButtonSound.play();
			}
		};

		new Button(4*resourcesManager.camera.getWidth()/5, bottomSpaceY, resourcesManager.ttCredits, vbom, this) {
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
		setBackground(new Background(0.44f, 0.815f, 0.878f));
		ParallaxManager_v2 manager = new ParallaxManager_v2(20.f);


		ParallaxLayer layer3 = new ParallaxLayer(0,
												 0,
												 0.3f,
												 resourcesManager.tBackgroundMenu3);
		layer3.setHeight(resourcesManager.camera.getHeight());
		layer3.setWidth(resourcesManager.camera.getWidth());
		manager.addLayer(this, layer3);

		ParallaxLayer layer2 = new ParallaxLayer(0,
												 0,
												 0.5f,
												 resourcesManager.tBackgroundMenu2);
		layer2.setHeight(resourcesManager.camera.getHeight());
		layer2.setWidth(resourcesManager.camera.getWidth());
		manager.addLayer(this, layer2);

		ParallaxLayer layer1 = new ParallaxLayer(0,
												 0,
												 0.9f,
												 resourcesManager.tBackgroundMenu1);
		layer1.setHeight(resourcesManager.camera.getHeight());
		layer1.setWidth(resourcesManager.camera.getWidth());
		manager.addLayer(this, layer1);
	    //attachChild(new Sprite(resourcesManager.tBackgroundMenu.getWidth()/2, resourcesManager.tBackgroundMenu.getHeight()/2, resourcesManager.tBackgroundMenu, vbom));
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		return false;
	}
}
