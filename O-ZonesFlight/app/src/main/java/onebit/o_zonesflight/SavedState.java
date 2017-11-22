package onebit.o_zonesflight;

import java.util.ArrayList;

/**
 * Created by Michael on 20.09.2017.
 */
public class SavedState {
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
    public SavedState(int highscore, int coins, ArrayList<Integer> unlockedItems){
        Highscore = highscore;
        Coins = coins;
        UnlockedItems = unlockedItems;
    }
    public SavedState(int highscore, int coins, String unlockedItemsSaveString){
        Highscore = highscore;
        Coins = coins;
        UnlockedItems = new ArrayList<>();
        if (unlockedItemsSaveString.startsWith("[") && unlockedItemsSaveString.endsWith("]"))
        {
            unlockedItemsSaveString = unlockedItemsSaveString.substring(1,unlockedItemsSaveString.length()-1);
            for (String item : unlockedItemsSaveString.split(",")) {
                try{
                    UnlockedItems.add(Integer.parseInt(item));
                } catch (Exception ex){}
            }
        }
    }

    /**
     * Returns Highscore
     * @return saved highscore
     */
    public int GetHighscore(){
        return Highscore;
    }
    public int GetCoins(){
        return Coins;
    }
    public ArrayList<Integer> GetUnlockedItems(){
        // Create new ArrayList to prevent mutation
        return new ArrayList<>(UnlockedItems);
    }
    public String GetUnlockedItemsSaveString(){
        String result = "[";
        for(int i = 0; i < UnlockedItems.size(); i++){
            if (i > 0) result += ",";
            result += UnlockedItems.get(i);
        }
        return result + "]";
    }
}
