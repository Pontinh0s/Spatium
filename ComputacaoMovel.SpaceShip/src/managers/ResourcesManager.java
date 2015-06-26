package managers;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

/**
 * ResourcesManager.java<p>
 * Loads, unloads and saves all resources used in the game.
 * Also contains the engine, camera, activity and vertexBufferObjectManager from the game. 
 * 
 * @category Managers
 * @author Davide Teixeria
 * @version 1.0 --/--/----
 */
public class ResourcesManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    
    public Engine engine;
    public BaseGameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;

	public float timer = 0;

    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------

    public ITiledTextureRegion
    	ttPlayer,
    	ttStart,
    	ttCredits,
    	ttOptions,
    	ttHighscores,
    	ttMute;
    public ITextureRegion
		tBackgroundMenu1,
		tBackgroundMenu2,
		tBackgroundMenu3,
    	tbackground,
    	tTiro,
    	tMeteorito,
    	tSplashScreen;
    public BitmapTextureAtlas
    	bSplashScreen;

    public ITextureRegion NULL;
    public ITextureRegion placeholder;
    public ITiledTextureRegion sheetHolder;
    
    //---------------------------------------------
    // FONTS
    //---------------------------------------------
    
    public Font
    	fontDefault;
    
    //---------------------------------------------
    // MUSICS & SOUNDS
    //---------------------------------------------
    
    public Music
    	mMusic,
    	mMenuMusic;
    public Sound
    	mCollision,
    	mImpact,
    	mlaser;
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public void loadMenuResources(){
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }
    
    public void loadGameResources(){
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    
    private void loadMenuGraphics(){
    	tBackgroundMenu1 = loadTexture("fundo_layer1.png", 1320, 2112);
    	tBackgroundMenu2 = loadTexture("fundo_layer2.png", 1320, 2112);
    	tBackgroundMenu3 = loadTexture("fundo_layer3.png", 1320, 2112);
    	ttStart = loadTileTexture("ss_button_play.png", 1712, 197, 2, 1);
    	ttOptions = loadTileTexture("ss_button_hangar.png", 1712, 197, 2, 1);
    	ttHighscores = loadTileTexture("ss_button_highscores.png", 1712, 197, 2, 1);
    	ttCredits = loadTileTexture("ss_hardSaveGames.png", 460, 275, 2, 1);
    	ttMute = loadTileTexture("ss_button_mute.png", 594, 256, 2, 1);
    }
    
    public void unloadMenuGraphics(){
    	tBackgroundMenu1.getTexture().unload();
    	tBackgroundMenu2.getTexture().unload();
    	tBackgroundMenu3.getTexture().unload();
    	ttStart.getTexture().unload();
    	ttCredits.getTexture().unload();
    	ttOptions.getTexture().unload();
    	ttHighscores.getTexture().unload();
    }
    
    private void loadMenuAudio(){
    	mMenuMusic = loadMusic("menu.wav", true);
    }
    
    private void loadMenuFonts(){
    	fontDefault = loadFont("Peric.ttf", 32, Color.BLUE_ABGR_PACKED_INT, false);
    }

    
    
    private void loadGameGraphics(){
    	tbackground = loadTexture("earth-wallpaper-1080p.png", 576, 324);
    	ttPlayer = loadTileTexture("nave.png", 201, 65, 3, 1);
    	tTiro = loadTexture("Tiro.png", 50, 25);
    	tMeteorito = loadTexture("asteroidBig01.png", 64, 64);
    }
    
    public void unloadGameGraphics(){
    	tbackground.getTexture().unload();
    	ttPlayer.getTexture().unload();
    	tTiro.getTexture().unload();
    	tMeteorito.getTexture().unload();
    }
    
    private TextureRegion loadTexture(String textureName, int width, int height){
    	TextureRegion myTextureRegion;
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(activity.getEngine().getTextureManager(), width, height, TextureOptions.DEFAULT);
		myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, activity, textureName, 0, 0);
		mBitmapTextureAtlas.load();
		return myTextureRegion;
    }
    
    private TiledTextureRegion loadTileTexture(String textureName, int width, int height, int columns, int rows){
    	return TextureRegionFactory.extractTiledFromTexture(loadTexture(textureName, width, height).getTexture(), columns, rows);
    }
    
    
    public void loadSplashScreen(){
    	//tSplashScreen = loadTexture("Logo.png", width, height);
    	String textureName = "logo.jpg";
    	int width = 594,
    		height = 274;

		bSplashScreen = new BitmapTextureAtlas(activity.getEngine().getTextureManager(), width, height, TextureOptions.DEFAULT);
		tSplashScreen = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSplashScreen, activity, textureName, 0, 0);
		bSplashScreen.load();
    }
    
    public void unloadSplashScreen(){
    	bSplashScreen.unload();
    	tSplashScreen = null;
    }

    
    
    private void loadGameFonts(){
    	fontDefault = loadFont("Peric.ttf", 32, Color.WHITE_ABGR_PACKED_INT, false);
    }
    
    private void loadGameAudio(){
    	mMusic = loadMusic("a-call-to-duty.mp3", true);
		mCollision = loadSound("collision.ogg");
		mImpact = loadSound("impact.ogg");
		mlaser = loadSound("laser.ogg");
    }
    
    private Music loadMusic(String musicName, boolean setLooping){
    	Music musica = null;
    	try{
    		musica = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, musicName);
    		musica.setLooping(setLooping);
    	}
    	catch (Exception e){
    		System.out.print(e);
    	}
    	return musica;
    }
    
    private Sound loadSound(String soundName){
    	Sound sound = null;
    	try {
    		sound = SoundFactory.createSoundFromAsset(activity.getEngine().getSoundManager(), activity, soundName);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return sound;
    }

    private Font loadFont(String fontName, float size, int color, boolean antiAliasing){
    	BitmapTextureAtlas fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        Font newFont = FontFactory.createFromAsset(activity.getFontManager(), fontTexture, activity.getAssets(), fontName, size, antiAliasing, color); // new Font(activity.getFontManager(), fontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, new Color(1f,0.97f,0.57f));
        newFont.load();
		return newFont;
    }
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, BaseGameActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
        
    	//DEFAULTS
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("spritesheets/");
        MusicFactory.setAssetBasePath("sounds/");
        SoundFactory.setAssetBasePath("sounds/");
        FontFactory.setAssetBasePath("fonts/");
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager getInstance(){
        return INSTANCE;
    }
}