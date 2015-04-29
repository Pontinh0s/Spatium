package basicClasses;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.IEntity;
import org.andengine.util.modifier.ease.EaseSineInOut;

public class Pattern {
	
	enum pattern{
		STRAIGHT_LINE
	}
	
	private Path pathToFollow; 
	pattern patternEnum;
	private float speed, xInitial, YInitial;
	
	
	public Pattern(float xInitial, float yInitial, pattern path, float speed){
		
		this.xInitial = xInitial;
		this.YInitial = yInitial;
		this.speed = speed;
		patternEnum = path;
		switchToPattern();
	}
	
	
	private void switchToPattern(){
		
		switch (patternEnum){
		case STRAIGHT_LINE:
				StraightLine();
				break;
		}
	}
	
	private LoopEntityModifier StraightLine(){
		
		pathToFollow = new Path(5).to(xInitial, 900); 

		 LoopEntityModifier ourLoop = new LoopEntityModifier(new PathModifier(speed, pathToFollow, new IPathModifierListener()  
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
	
}
