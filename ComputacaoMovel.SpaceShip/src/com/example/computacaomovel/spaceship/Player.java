package com.example.computacaomovel.spaceship;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.view.MotionEvent;

public class Player{
	private Sprite nave;
	private ITexture spritesheet;
	private ITextureRegion direita, frente, esquerda;
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
    
	public Player(final int CAMERA_WIDTH, final int CAMERA_HEIGHT){
		this.CAMERA_WIDTH = CAMERA_WIDTH;
		this.CAMERA_HEIGHT = CAMERA_HEIGHT;
		
		nave.setX(CAMERA_WIDTH/2);
		nave.setY(10);
	}
		
    public void LoadContent(TextureManager textureManager, MainActivity game) throws IOException{
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
    	//spritesheet = new BitmapTexture(textureManager, game.LoadContent("spritesheets/nave.png"));
    	spritesheet.load();
        
    	// 
    	esquerda = TextureRegionFactory.extractFromTexture(spritesheet, 0, 0, (int)(spritesheet.getWidth()/3), spritesheet.getHeight());
    	frente = TextureRegionFactory.extractFromTexture(spritesheet, (int)(spritesheet.getWidth()/3), 0, (int)(spritesheet.getWidth()/3), spritesheet.getHeight());
    	direita = TextureRegionFactory.extractFromTexture(spritesheet, (int)(spritesheet.getWidth()*2/3), 0, (int)(spritesheet.getWidth()/3), spritesheet.getHeight());
    	
    	// Sons
    	collision = SoundFactory.createSoundFromAsset(game.getEngine().getSoundManager(), game, "collision.ogg");
    	laser = SoundFactory.createSoundFromAsset(game.getEngine().getSoundManager(), game, "laser.ogg");
    }
    
    public void Update(final MotionEvent event, final float accelerationX, final float accelerationY, final float accelerationZ)
    {
    	//Salto
    	if (saltar)
        {
        	nave.setScale((float) (Math.sin(salto) * altura_do_salto*0.01f));
            salto += velocidade_de_salto;
            if (salto >= Math.PI * 2)
            {
                salto = 0f;
                saltar = false;
            }
        }

        // movimento em X   - feito
        nave.setX((CAMERA_WIDTH/2) + (accelerationX * -acelerador));
        if (nave.getX() < -3f) nave.setX(nave.getX() + 3f);
        if (nave.getX() > 3f) nave.setX((nave.getX() - 3f) * força_das_molas);
    }

	public Sprite getNave() {
		return nave;
	}

	public void disparar() {
        //    Instantiate(tiro, transform.position, transform.localRotation);
	}

	public void saltar() {
        saltar = true;}
}
