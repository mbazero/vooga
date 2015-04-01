package game_engine.objective;

import game_engine.Behavior;
import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * Class that represents goals or winning/losing conditions of the game.
 * Each objective has a condition and a behavior. When the condition is true, the behavior will be executed.
 * @author Tony
 *
 */
public class Objective {
    /**
     * Condition for this objective to be complete.
     */
    private BooleanSupplier myCondition;
    /**
     * Action performed when objective is complete.
     */
    private Behavior myOnComplete;
    private boolean myIsComplete;
    private boolean myIsActive;
    /**
     * List of objectives that must be completed before this objective is active.
     */
    private List<Objective> myLinked;
    
    /**
     * Constructor 
     * @param linked List of objectives that must be completed before this objective is active.
     */
    public Objective(List<Objective> linked) {
        myIsComplete = false;
        myIsActive = false;
        myLinked = linked;
    }
    
    /**
     * Constructor
     * @param condition
     * @param onComplete
     * @param linked List of objectives that must be completed before this objective is active.
     */
    public Objective(BooleanSupplier condition, Behavior onComplete, List<Objective> linked) {
        this(linked);
        setCondition(condition);
        setOnComplete(onComplete);
    }
    
    public void setCondition(BooleanSupplier condition){
        myCondition = condition;
    }
    
    public void setOnComplete(Behavior onComplete){
        myOnComplete = onComplete;
    }
    
    /**
     * Updates active and completion status. Completes if objective is complete.
     */
    public void update() {
        if (isActive()){
            myIsComplete = myCondition.getAsBoolean();
            if (isComplete()){
                complete();
            }
        }
    }
    
    /**
     * Updates the active status and returns the status. An objective's completion status only if it is active.
     * @return true iff objective is active.
     */
    public boolean isActive() {
        boolean linkedFinished = myLinked.stream().anyMatch(objective -> !objective.isComplete());
        myIsActive |= (linkedFinished && !isComplete());
        return myIsActive;
    }
    
    public boolean isComplete(){
        return myIsComplete;
    }
    
    public void setActive(boolean active){
        myIsActive = active;
    }
    
    /**
     * Executes the behavior associated with this object. 
     * Also sets the objective to complete and objective is no longer active.
     */
    public void complete() {
        myOnComplete.execute();
        myIsComplete = true;
        myIsActive = false;
    }
}