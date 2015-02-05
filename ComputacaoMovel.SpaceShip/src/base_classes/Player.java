package base_classes;

import managers.DetritosManager;
import managers.ResourcesManager;
import managers.TirosManager;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;

import scene.GameScene;

public class Player{
	private Sprite sprite;
	private final float CAMERA_WIDTH, CAMERA_HEIGHT;
	private GameScene game;
	
	//MANTER
	private DetritosManager detritos;
	private TirosManager tiros;

	//Integridade
	private float shield = 1, shieldRegenRate = 0.05f, shieldLimit = 3;
	public int lifes = 2;
	
	//Movimento
    private final float acelerador = 2f;
    private final float força_das_molas = 0.17f;
    
    //Salto
    public Boolean saltar = false;
    private float salto = 0;
    private final float velocidade_de_salto = 0.065f;
    private final float altura_do_salto = 4;
    private float scale;
    
    private float X, Y;
    
    //MANTER
	public Player(GameScene game, ResourcesManager resources, TirosManager bullets, DetritosManager detritos, float scale){
		this.CAMERA_WIDTH = resources.camera.getWidth();
		this.CAMERA_HEIGHT = resources.camera.getHeight();
		this.game = game;
		this.detritos = detritos;
		this.tiros = bullets;
		this.scale = scale;
		X = CAMERA_WIDTH/2;
		Y = CAMERA_HEIGHT-170;
		LoadContent(resources);
	}

	//MANTER
	public Player(GameScene game, ResourcesManager resources, TirosManager bullets, float scale){
		this.CAMERA_WIDTH = resources.camera.getWidth();
		this.CAMERA_HEIGHT = resources.camera.getHeight();
		this.game = game;
		this.detritos = null;
		this.tiros = bullets;
		this.scale = scale;
		X = CAMERA_WIDTH/2;
		Y = CAMERA_HEIGHT-170;
		LoadContent(resources);
	}

	private void LoadContent(ResourcesManager resources) {
		sprite = new Sprite(
				X, Y,
				resources.ttPlayer.getTextureRegion(1),
				resources.vbom);
		sprite.setScale(this.scale, this.scale);
	}
    
	public void Update(final float accelerationX, float elapsedTime)
    {
		//Colisões - MANTER
		if (!saltar)
			detectColisions();
    	regenerateShield(elapsedTime);
    	
    	//Salto
    	if (saltar)
    		saltar();
    	
    	// Movimento em X
    	X=(int)(X + accelerationX * acelerador);
    	
    	int limite = 100;
        if (X < limite-sprite.getWidth()/2)
    		X=X + (int)((limite-X-sprite.getWidth()/2) * força_das_molas);
        if (X > CAMERA_WIDTH - limite-sprite.getWidth()/2)
    		X=X - (int)((X- (CAMERA_WIDTH-limite-sprite.getWidth()/2)) * força_das_molas);
    	
        sprite.setPosition(X, Y);
    }
	
	private boolean detectColisions(){
		for(int i = 0; i<detritos.Size(); i++) {
			if (detritos.colidesWith(this.sprite, i)) {
				detritos.Destroy(i);
				removeShield(detritos.getDamage());
				return true;
			}
		}
		
		return false;
	}

	public void disparar() {
		tiros.Fire(sprite.getX() + sprite.getWidth() / 2, sprite.getY());
	}

	private void saltar() {
		sprite.setScaleX(this.scale + (float) (Math.sin(salto) * altura_do_salto * 0.1f));
		sprite.setScaleY(this.scale + (float) (Math.sin(salto) * altura_do_salto * 0.1f));
    	salto += velocidade_de_salto;
    	
    	if (salto >= Math.PI * 1)
    	{
    		salto = 0f;
    		saltar = false;
    	}
    }
	
	//Remove 1 escudo por cada ponto em i, quando tiver 0 escudos a proxima colisão destroi a nave
	private void removeShield(int i)
	{
		if (shield < 1){
			lifes --;
			if (lifes <= 0)
				gameOver();
		}
		else if (shield < i){
			shield = 0;
		}
		else
			shield -= i;
	}
	
	//Regenera o escudo de acordo com o rate
	private void regenerateShield(float elapsedTime) {
		shield += shieldRegenRate * elapsedTime;
		if (shield > shieldLimit)
			shield = shieldLimit;
	}
	
	private void gameOver() {
		game.gameOver();
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getLifes() {
		return lifes;
	}
	
	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public float getShield() {
		return shield;
	}

	public void setShield(float shield) {
		this.shield = shield;
	}

	public float getRate() {
		return shieldRegenRate;
	}

	public void setRate(float rate) {
		this.shieldRegenRate = rate;
	}

	public float getX() {
		return X;
	}

	public void setX(float x) {
		X = x;
	}

	public float getY() {
		return Y;
	}

	public void setY(float y) {
		Y = y;
	}
	
	public void setSalto(boolean isJumping) {
		saltar = isJumping;
	}
	
	//MANTER
	public void setTirosManager(TirosManager tiros) {
		this.tiros = tiros;
	}
	
	//MANTER
	public TirosManager getTirosManager() {
		return tiros;
	}

	public boolean getSalto() {
		return saltar;
	}
}
