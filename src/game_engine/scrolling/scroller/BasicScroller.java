package game_engine.scrolling.scroller;

import javafx.scene.Group;


/**
 * 
 * Implements IScroller. Scrolls by translating group in the opposite direction. Scrolling is
 * instantaneous.
 * 
 * @author Tony
 *
 */
public class BasicScroller implements IScroller {
    private Group myGroup;
    
    /**
     * Constructor
     * @param group Target of the scrolling.
     */
    public BasicScroller (Group group) {
        myGroup = group;
    }
    
    @Override
    public void scroll (double changeX, double changeY) {
        myGroup.setTranslateX(myGroup.getTranslateX() - changeX);
        myGroup.setTranslateY(myGroup.getTranslateY() - changeY);
    }

    @Override
    public Group getGroup () {
        return myGroup;
    }

}