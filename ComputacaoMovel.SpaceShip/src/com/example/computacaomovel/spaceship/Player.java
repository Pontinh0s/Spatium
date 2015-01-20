package com.example.computacaomovel.spaceship;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.view.MotionEvent;

public class Player{
	private Sprite nave;
    private ITiledTextureRegion player;
	private Sound laser;
	private final int CAMERA_WIDTH, CAMERA_HEIGHT;

    private final float sensibilidade = 9f;
    private final float acelerador = 2f;
    private final float for�a_das_molas = 0.17f;
    public int lifes = 5;
    public Boolean saltar = false;
    private float salto = 0;
    private final float velocidade_de_salto = 0.065f;
    private final float altura_do_salto = 4;
    private float accelerationOLD = 0;
    private float scale;
    
    private int X, Y;
    
	public Player(final int CAMERA_WIDTH, final int CAMERA_HEIGHT, BaseGameActivity game, float scale){
		this.CAMERA_WIDTH = CAMERA_WIDTH;
		this.CAMERA_HEIGHT = CAMERA_HEIGHT;
		this.scale = scale;
		LoadContent(game);
	}
		
	private void LoadContent(BaseGameActivity game){
        try {
			TextureRegion myTextureRegion;
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
			BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(game.getEngine().getTextureManager(), 201, 65, TextureOptions.DEFAULT);
			myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "nave.png", 0, 0);
			mBitmapTextureAtlas.load();
			player = TextureRegionFactory.extractTiledFromTexture(myTextureRegion.getTexture(), 3, 1);
	        
			X = CAMERA_WIDTH/2;
			Y = CAMERA_HEIGHT-80;
			nave = new Sprite(
				X, Y,
				player.getTextureRegion(1),
				game.getEngine().getVertexBufferObjectManager());
			nave.setScale(this.scale, this.scale);
		
			// Sons
			laser = SoundFactory.createSoundFromAsset(game.getEngine().getSoundManager(), game, "sounds/laser.ogg");
		
        	} catch (Exception e) {
			e.printStackTrace();
		}
}
    
    public void Update(final float accelerationX)
    {
    	//Salto
    	if (saltar)
        {
        	nave.setScaleX(this.scale + (float) (Math.sin(salto) * altura_do_salto * 0.1f));
        	nave.setScaleY(this.scale + (float) (Math.sin(salto) * altura_do_salto * 0.1f));
            salto += velocidade_de_salto;
            
            if (salto >= Math.PI * 1)
            {
                salto = 0f;
                saltar = false;
            }
        }
    	
    	// Movimento em X
    	X=(int)(X + accelerationX * acelerador);
    	
    	int limite = 100;
        if (X < limite-nave.getWidth()/2)
    		X=X + (int)((limite-X-nave.getWidth()/2) * for�a_das_molas);
        if (X > CAMERA_WIDTH - limite-nave.getWidth()/2)
    		X=X - (int)((X- (CAMERA_WIDTH-limite-nave.getWidth()/2)) * for�a_das_molas);
    	
    	accelerationOLD = accelerationX;
    	nave.setPosition(X, Y);
    }

	public Sprite Shape() {
		return nave;
	}

	public void disparar(Tiro bala) {
		laser.play();
		bala.Fire(nave.getX() + nave.getWidth() / 2, nave.getY());
	}

	public void saltar() {
        saltar = true;
    }
}