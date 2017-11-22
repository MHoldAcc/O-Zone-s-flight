package onebit.o_zonesflight;

/**
 *
 * Created by Silvan Pfister on 22.11.2017.
 */
public interface ITickable {
    void Tick(Game game, Object... data);
    boolean CanBeRemoved();
}
