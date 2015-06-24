/**
 * 
 */
package Prototipo;

import managers.ResourcesManager;
import managers.Wave;
import managers.SceneManager.SceneType;

import org.andengine.audio.music.Music;
import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import source.BaseScene;
import android.app.Activity;

/**
 * Level_0.java<p>
 *
 *
 * @category Prototipo
 * @author David Malheiro
 * @version 1.0 24/06/2015
 */
public class Level_0 extends BaseScene{
	  
	
	    @Override
	    public void createScene()
	    {
	    	createBackground();
	    	
	    }

	    @Override
	    public void onBackKeyPressed()
	    {
	        
	    }

	    @Override
	    public SceneType getSceneType()
	    {
	        return SceneType.SCENE_GAME;
	    }

	    @Override
	    public void disposeScene()
	    {
	        
	    }
	
	    private void createBackground()
	    {
	        setBackground(new Background(Color.BLUE));
	    }
}
