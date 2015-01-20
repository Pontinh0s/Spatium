package scene;

import managers.SceneManager.SceneType;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import base_classes.BaseScene;



public class LoadingScene extends BaseScene
{
    @Override
    public void createScene()
    {
        setBackground(new Background(Color.WHITE));
        Text loadingText = new Text(0, 0, resourcesManager.fontDefault, "Loading...", vbom);
        loadingText.setPosition(camera.getWidth()/2 - loadingText.getWidth()/2, camera.getHeight()/2 - loadingText.getHeight()/2);
        attachChild(loadingText);
    }

    @Override
    public void onBackKeyPressed()
    {
        return;
    }

    @Override
    public SceneType getSceneType()
    {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene()
    {

    }
}