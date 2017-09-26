package onebit.o_zonesflight;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by admin on 20.09.2017.
 */
public class PlayerTest {

    @Test
    public void ApplyBearing_Movement() {
        Player player = new Player();
        float firstPosition = player.GetPosition();
        player.ApplyBearing(1);
        Assert.assertTrue(player.GetPosition() > firstPosition);
        player.ApplyBearing(-1);
        player.ApplyBearing(-1);
        Assert.assertTrue(player.GetPosition() < firstPosition);
    }

    @Test
    public void ApplyBearing_TooManyLeft() {
        Player player = new Player();
        boolean successful = false;

        for (int i = 0; i < 1000; i++) {
            player.ApplyBearing(-1);
            if(player.GetPosition() == 0)
                successful = true;
            else if (player.GetPosition() < 0)
                successful = false;
        }
        Assert.assertTrue(successful);
    }

    @Test
    public void ApplyBearing_TooManyRight() {
        Player player = new Player();
        boolean successful = false;

        //Goes right
        for (int i = 0; i < 1000; i++) {
            player.ApplyBearing(1);
            if(player.GetPosition() == Settings.Environment_Width)
                successful = true;
            else if (player.GetPosition() > Settings.Environment_Width)
                successful = false;
        }
        Assert.assertTrue(successful);
    }
}