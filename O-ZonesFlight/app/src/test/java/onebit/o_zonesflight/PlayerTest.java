package onebit.o_zonesflight;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

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
}