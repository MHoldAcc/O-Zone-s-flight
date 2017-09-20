package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class TestSaveManager implements ISaveFileManager {
    public TestSaveManager(){
        save = new SavedState(0);
    }

    @Override
    public SavedState LoadGame() {
        return save;
    }

    @Override
    public void SaveGame(SavedState savedState) {
        save = savedState;
    }

    private SavedState save;
}
