package onebit.o_zonesflight;

/**
 * Created by Michael on 20.09.2017.
 */
public class SavedState {
    /**
     * Highscore to save
     */
    private int Highscore;

    /**
     * Initializes save state
     * @param highscore highscore to save
     */
    public SavedState(int highscore){
        Highscore = highscore;
    }

    /**
     * Returns Highscore
     * @return saved highscore
     */
    public int GetHighscore(){
        return Highscore;
    }
}
