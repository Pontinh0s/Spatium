package base_classes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontLibrary;
import org.andengine.opengl.font.FontUtils;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;

public abstract class Button {
	/**
     * @param positionX
     * @param positionY
     * @param texture
     * @param vbom
     * @param cena
     * <br><br>
     * Esta classe serve para facilitar a criação de um botão.
     * Recebe como parâmetro a posição do centro do botão em X e em Y,
     * a textura do botão, o vbom e a cena onde o botão vai ser aplicado
     */
	public Button(float positionX, float positionY, ITiledTextureRegion texture, VertexBufferObjectManager vbom, Scene cena){
		TiledSprite button = new TiledSprite(positionX - texture.getWidth(0)/2, positionY - texture.getHeight(0)/2, texture, vbom){
	        @Override
	        public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
		        switch(pAreaTouchEvent.getAction()){
			        case TouchEvent.ACTION_DOWN:
		                this.setCurrentTileIndex(1);
		                ActionDown();
		                break;
	                case TouchEvent.ACTION_OUTSIDE:
	                    this.setCurrentTileIndex(0);
	                    ActionOutside();
	                    break;
	                case TouchEvent.ACTION_UP:
	                    this.setCurrentTileIndex(0);
	                	ActionUp();
	                    break;
	                case TouchEvent.ACTION_CANCEL:
	                    this.setCurrentTileIndex(0);
	                	ActionCancel();
	                	break;
	                case TouchEvent.ACTION_MOVE:
	                	//System.out.print("x:" + pTouchAreaLocalX + " y:" + pTouchAreaLocalY);
		                //this.setCurrentTileIndex(0);
	                	ActionMove();
	                	break;
		        }
		        return true;
	    	}
		};
		cena.registerTouchArea(button);
		cena.attachChild(button);
	}

	public abstract void ActionDown();

	public void ActionOutside(){}

	public abstract void ActionUp();

	public void ActionCancel(){}

	public void ActionMove(){}
}
