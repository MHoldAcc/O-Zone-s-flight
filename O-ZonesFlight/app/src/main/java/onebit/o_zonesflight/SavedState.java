package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class SavedState {
    private int Highscore;

    public SavedState(int highscore){
        Highscore = highscore;
    }

    public int GetHighscore(){
        return Highscore;
    }
}
