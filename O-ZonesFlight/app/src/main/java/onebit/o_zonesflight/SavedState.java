package onebit.o_zonesflight;

import java.util.ArrayList;

/**
 * Represents the save data
 * Created by Michael on 20.09.2017.
 */
class SavedState {
    /**
     * Highscore to save
     */
    private int Highscore;

    private int Coins;

    private ArrayList<Integer> UnlockedItems;

    /**
     * Initializes save state
     * @param highscore highscore to save
     */
    SavedState(int highscore, int coins, ArrayList<Integer> unlockedItems){
        Highscore = highscore;
        Coins = coins;
        UnlockedItems = unlockedItems;
    }
    SavedState(int highscore, int coins, String unlockedItemsSaveString){
        Highscore = highscore;
        Coins = coins;
        UnlockedItems = new ArrayList<>();
        if (unlockedItemsSaveString.startsWith("[") && unlockedItemsSaveString.endsWith("]"))
        {
            unlockedItemsSaveString = unlockedItemsSaveString.substring(1,unlockedItemsSaveString.length()-1);
            for (String item : unlockedItemsSaveString.split(",")) {
                try{
                    UnlockedItems.add(Integer.parseInt(item));
                } catch (Exception ignored){}
            }
        }
    }

    /**
     * Returns Highscore
     * @return saved highscore
     */
    int GetHighscore(){
        return Highscore;
    }
    int GetCoins(){
        return Coins;
    }
    ArrayList<Integer> GetUnlockedItems(){
        // Create new ArrayList to prevent mutation
        return new ArrayList<>(UnlockedItems);
    }
    String GetUnlockedItemsSaveString(){
        StringBuilder result = new StringBuilder("[");
        for(int i = 0; i < UnlockedItems.size(); i++){
            if (i > 0) result.append(",");
            result.append(UnlockedItems.get(i));
        }
        result.append("]");
        return result.toString();
    }
}
