package onebit.o_zonesflight;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by admin on 20.09.2017.
 */
public class GameTest {
    @Test
    public void DoFrame_Score() {
        Game game = new Game(new TestSaveState());
        game.DoFrame(0);
        Assert.assertTrue(game.GetScore() == Settings.Gameplay_MillisecondsPerFrame);
        game.DoFrame(0);
        Assert.assertTrue(game.GetScore() == Settings.Gameplay_MillisecondsPerFrame * 2);
    }

    @Test
    public void ResetGame() {
        Game game = new Game(new TestSaveState());
        game.DoFrame(0);
        game.ResetGame();
        Assert.assertTrue(game.GetMeteorites().size() == 0);
        Assert.assertTrue(game.GetScore() == 0);
        Assert.assertTrue(game.GetPlayer().GetPosition() == (float)(Settings.Environment_Width / 2));
    }
}