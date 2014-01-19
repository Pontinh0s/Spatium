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

import android.content.Context;
import android.view.MotionEvent;

public class Player{
	private Sprite nave;
    private ITiledTextureRegion player;
	private Sound collision, laser;
	final int CAMERA_WIDTH, CAMERA_HEIGHT;

    public float sensibilidade = 9f;
    public float acelerador = 0.7f;
    public float força_das_molas = 0.2f;
    public int lifes = 5;
    public Boolean saltar = false;
    public float salto = 0;
    public float velocidade_de_salto = 0.2f;
    public float altura_do_salto = 8;
    private float accelerationOLD = 0;
    
	public Player(final int CAMERA_WIDTH, final int CAMERA_HEIGHT){
		this.CAMERA_WIDTH = CAMERA_WIDTH;
		this.CAMERA_HEIGHT = CAMERA_HEIGHT;
	}
		
	public void LoadContent(MainActivity game) throws IOException{
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
        //spritesheet = new BitmapTexture(textureManager, game.LoadContent("spritesheets/nave.png"));
        
		TextureRegion myTextureRegion;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(game.getEngine().getTextureManager(), 201, 65, TextureOptions.DEFAULT);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "nave.png", 0, 0);
		mBitmapTextureAtlas.load();
		player = TextureRegionFactory.extractTiledFromTexture(myTextureRegion.getTexture(), 3, 1);
        
		nave = new Sprite(
				CAMERA_WIDTH/2, CAMERA_WIDTH-10,
				player.getTextureRegion(1),
				game.getEngine().getVertexBufferObjectManager());
        
		
        // Sons
        collision = SoundFactory.createSoundFromAsset(game.getEngine().getSoundManager(), game, "collision.ogg");
        laser = SoundFactory.createSoundFromAsset(game.getEngine().getSoundManager(), game, "laser.ogg");
}
    
    public void Update(final float accelerationX, final float accelerationY, VertexBufferObjectManager VBOmanager)
    {
    	//Salto
    	if (saltar)
        {
        	nave.setScale((float) (Math.sin(salto) * altura_do_salto * 0.01f));
            salto += velocidade_de_salto;
            if (salto >= Math.PI * 2)
            {
                salto = 0f;
                saltar = false;
            }
        }

    	// Movimento em X
    	float limite = 5f;
        if (nave.getX() < -limite)
    		nave.setX(nave.getX() + (nave.getX()+limite) * força_das_molas * 0.2f);
        if (nave.getX() > limite)
    		nave.setX(nave.getX() - (nave.getX()-limite) * força_das_molas * 0.2f);
    	
        // mudança de sprites
    	float viragem = 0.2f;
    	if((accelerationX < viragem) && (accelerationOLD >= viragem))
    		nave = new Sprite(nave.getX(), nave.getY(), player.getTextureRegion(0), VBOmanager);
    	else if ((accelerationX > -viragem) && (accelerationOLD <= -viragem))
    		nave = new Sprite(nave.getX(), nave.getY(), player.getTextureRegion(2), VBOmanager);
        else if ((accelerationOLD < -viragem) || (accelerationOLD > viragem))
    		nave = new Sprite(nave.getX(), nave.getY(), player.getTextureRegion(1), VBOmanager);
    	
    	accelerationOLD = accelerationX;
    }

	public Sprite getNave() {
		return nave;
	}

	public void disparar() {
        //    Instantiate(tiro, transform.position, transform.localRotation);
	}

	public void saltar() {
        saltar = true;
    }
}
