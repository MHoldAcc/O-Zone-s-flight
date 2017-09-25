package onebit.o_zonesflight;

/**
 * Class is a Runnable that simply calls the Gametick on the uicontroller.
 * Created by silvan pfister on 20.09.2017.
 */
public class GameTickRunnable implements Runnable {

    private final UIController owner;

    /**
     * Creates a GameTickRunnable instance.
     * @param owner The UIController which GameTick method should be ran
     */
    public GameTickRunnable(UIController owner){
        this.owner = owner;
    }

    /**
     * Simply runs the GameTick method of the UIController associated with this instance
     */
    @Override
    public void run() { owner.GameTick(); }

}
