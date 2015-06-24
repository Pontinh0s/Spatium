package managers;

import gameObjects.ShipObject;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.IEntity;
import org.andengine.util.modifier.ease.EaseSineInOut;

public class Pattern {
	
	enum pattern{
		STRAIGHT_LINE,
		ZIGZAG,
		DIAMOND,
		LEFT_TURN,
		RIGHT_TURN
	}
	
	private Path pathToFollow; 
	LoopEntityModifier ourLoop;
	pattern patternEnum;
	ResourcesManager resources;
	private float speed, xInitial, YInitial;
	
	public Pattern(float xInitial, float yInitial, pattern path, float speed){
		
		this.xInitial = xInitial;
		this.YInitial = yInitial;
		this.speed = speed;
		patternEnum = path;
		resources = ResourcesManager.getInstance();
		switchToPattern();
	}	
	
	
	private void switchToPattern(){
		
		switch (patternEnum){
		case STRAIGHT_LINE:
				StraightLine();
				break;
		case ZIGZAG:
			ZigZag();
			break;
		}
	}
	
	
	//ZigZag Done, acaba fora do ecrã
	private LoopEntityModifier ZigZag(){
		
		pathToFollow = new Path(4).to(
				xInitial, 0.1f*resources.camera.getHeight()).to(
				this.xInitial-(resources.camera.getWidth()/6), 0.4f*resources.camera.getHeight()).to(
				this.xInitial+(resources.camera.getWidth()/6), 0.7f*resources.camera.getHeight()).to(
				this.xInitial+(resources.camera.getWidth()/6), 1.5f*resources.camera.getHeight()); 

		 ourLoop = new LoopEntityModifier(new PathModifier(speed, pathToFollow, new IPathModifierListener()  
		 {  
		      @Override  
		      public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) { }
		      		
		      @Override  
		      public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) { } 
		 
		      @Override  
		      public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  

		      @Override  
		      public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  
		 }, EaseSineInOut.getInstance()));  
		
		
		return ourLoop;		
	}	
	
	//StraightLine ,falta explodir quando chega ao destino
	private LoopEntityModifier StraightLine(){
		
		pathToFollow = new Path(1).to(xInitial, ShipObject.startPositionY); 

		 ourLoop = new LoopEntityModifier(new PathModifier(speed, pathToFollow, new IPathModifierListener()  
		 {  
		      @Override  
		      public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) { }
		      	
		      @Override  
		      public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) { } 
		 
		      @Override  
		      public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  

		      @Override  
		      public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  
		 }, EaseSineInOut.getInstance()));  
		
		
		return ourLoop;		
	}	
	
	private LoopEntityModifier Diamond(){
		
		pathToFollow = new Path(5).to(xInitial, 0.1f*resources.camera.getHeight()).to(
		xInitial - resources.camera.getWidth()/6, 0.4f*resources.camera.getHeight()).to(
		xInitial, 0.6f*resources.camera.getHeight()).to(
		xInitial + resources.camera.getWidth()/6,0.4f*resources.camera.getHeight()).to(
		xInitial, 0.1f*resources.camera.getHeight()); 

		 ourLoop = new LoopEntityModifier(new PathModifier(speed, pathToFollow, new IPathModifierListener()  
		 {  
		      @Override  
		      public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) { }
		      		
		      @Override  
		      public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) { } 
		 
		      @Override  
		      public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  

		      @Override  
		      public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  
		 }, EaseSineInOut.getInstance()));  
		
		
		return ourLoop;		
	}	
	
	private LoopEntityModifier LeftTurn(){
		
		pathToFollow = new Path(1).to(xInitial, 0.1f*resources.camera.getHeight()).to(
		xInitial-resources.camera.getWidth()/6, 0.5f*resources.camera.getHeight()).to(
		xInitial-resources.camera.getWidth()/6, 1.5f*resources.camera.getHeight()); 

		ourLoop = new LoopEntityModifier(new PathModifier(speed, pathToFollow, new IPathModifierListener()  
		 {  
		      @Override  
		      public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) { }
		      		
		      @Override  
		      public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) { } 
		 
		      @Override  
		      public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  

		      @Override  
		      public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  
		 }, EaseSineInOut.getInstance()));  
		
		
		return ourLoop;		
	}	
	
	private LoopEntityModifier RightTurn(){
		
		pathToFollow = new Path(1).to(xInitial, 0.1f*resources.camera.getHeight()).to(
		xInitial+resources.camera.getWidth()/6, 0.5f*resources.camera.getHeight()).to(
		xInitial+resources.camera.getWidth()/6, 1.5f*resources.camera.getHeight()); 

		 ourLoop = new LoopEntityModifier(new PathModifier(speed, pathToFollow, new IPathModifierListener()  
		 {  
		      @Override  
		      public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) { }
		      		
		      @Override  
		      public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) { } 
		 
		      @Override  
		      public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  

		      @Override  
		      public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity,  
		                final int pWaypointIndex) { }  
		 }, EaseSineInOut.getInstance()));  
		
		
		return ourLoop;		
	}	

	
	
	/**
	 * @return {@link #pathToFollow}
	 */
	public Path getPathToFollow() {
		return pathToFollow;
	}
	
	/**
	 * @return {@link #pathToFollow}
	 */
	public LoopEntityModifier getLoopToFollow() {
		return ourLoop;
	}

}
