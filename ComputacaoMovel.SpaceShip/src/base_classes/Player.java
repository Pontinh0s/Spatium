package base_classes;

import managers.ResourcesManager;
import managers.TirosManager;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.Sprite;

public class Player{
	private Sprite nave;
	private final float CAMERA_WIDTH, CAMERA_HEIGHT;

	//Integridade
	private float shield, rate;
	public int lifes = 5;
	
	//Movimento
    private final float acelerador = 2f;
    private final float força_das_molas = 0.17f;
    
    //Salto
    public Boolean saltar = false;
    private float salto = 0;
    private final float velocidade_de_salto = 0.065f;
    private final float altura_do_salto = 4;
    private float accelerationOLD = 0;
    private float scale;
    
    private float X, Y;
    
	public Player(ResourcesManager resources, float scale){
		this.CAMERA_WIDTH = resources.camera.getWidth();
		this.CAMERA_HEIGHT = resources.camera.getHeight();
		this.scale = scale;
		setShield(3);
		LoadContent(resources);
	}

	private void LoadContent(ResourcesManager resources) {
		X = CAMERA_WIDTH/2;
		Y = CAMERA_HEIGHT-70;
		nave = new Sprite(
				X, Y,
				resources.ttPlayer.getTextureRegion(1),
				resources.vbom);
		nave.setScale(this.scale, this.scale);
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

	public void disparar(TirosManager bullets) {
		bullets.Fire(nave.getX() + nave.getWidth() / 2, nave.getY());
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
	
}
