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
	private Sound laser;
	private final int CAMERA_WIDTH, CAMERA_HEIGHT;

	//Integridade
	private float shield, rate;
	public int lifes = 5;
	
	//Movimento
	private final float sensibilidade = 9f;
    private final float acelerador = 2f;
    private final float força_das_molas = 0.17f;
    
    //Salto
    public Boolean saltar = false;
    private float salto = 0;
    private final float velocidade_de_salto = 0.065f;
    private final float altura_do_salto = 4;
    private float accelerationOLD = 0;
    private float scale;
    
    private int X, Y;
    
	public Player(final int CAMERA_WIDTH, final int CAMERA_HEIGHT, MainActivity game, float scale) throws IOException{
		this.CAMERA_WIDTH = CAMERA_WIDTH;
		this.CAMERA_HEIGHT = CAMERA_HEIGHT;
		this.scale = scale;
		LoadContent(game);
	}
		
	private void LoadContent(MainActivity game) throws IOException{
        
		TextureRegion myTextureRegion;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(game.getEngine().getTextureManager(), 201, 65, TextureOptions.DEFAULT);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, game, "nave.png", 0, 0);
		mBitmapTextureAtlas.load();
		player = TextureRegionFactory.extractTiledFromTexture(myTextureRegion.getTexture(), 3, 1);
        
		X = CAMERA_WIDTH/2;
		Y = CAMERA_HEIGHT-70;
		nave = new Sprite(
				X, Y,
				player.getTextureRegion(1),
				game.getEngine().getVertexBufferObjectManager());
        nave.setScale(this.scale, this.scale);
		
        // Sons
        laser = SoundFactory.createSoundFromAsset(game.getEngine().getSoundManager(), game, "sounds/laser.ogg");
	}
    
    public void Update(final float accelerationX)
    {
    	//É necessario fazer setShield antes do primeiro update
    	regenerateShield(rate);
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
    		X=X + (int)((limite-X-nave.getWidth()/2) * força_das_molas);
        if (X > CAMERA_WIDTH - limite-nave.getWidth()/2)
    		X=X - (int)((X- (CAMERA_WIDTH-limite-nave.getWidth()/2)) * força_das_molas);
    	
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
	
	//Remove 1 escudo por cada ponto em i, quando tiver 0 escudos a proxima colisão destroi a nave
	public void removeShield(int i)
	{
		if (shield < i){
			shield = 0;
		}else if (shield < 1){
			lifes -= 1;
		}else
			shield = shield - i;
	}
		
	//Regenera o escudo de acordo com o rate recebido
	private void regenerateShield(float rate)
	{
		shield += rate;
	}

	public float getShield() {
		return shield;
	}

	public void setShield(float shield) {
		this.shield = shield;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
	
}
