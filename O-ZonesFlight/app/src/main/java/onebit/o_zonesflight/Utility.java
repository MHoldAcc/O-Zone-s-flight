package onebit.o_zonesflight;

/**
 * Provides functionality which is missing by default
 * Created by Silvan Pfister on 27.11.2017.
 */

@SuppressWarnings( "unused" )
public class Utility {
	//unbelievable that these aren't provided by default...

	/** Interface for a virtual method with no return value or parameters */
	public interface IAction {
		void apply( );
	}

	/**
	 * Interface for a virtual method with no return value and one parameter
	 *
	 * @param <T>
	 * 		Type of the parameter
	 */
	public interface IAction1 <T> {
		void apply(T in);
	}

	/**
	 * Interface for a virtual method with no return value and two parameters
	 *
	 * @param <T1>
	 * 		Type of the first parameter
	 * @param <T2>
	 * 		Type of the second parameter
	 */
	public interface IAction2 <T1, T2> {
		void apply(T1 in1, T2 in2);
	}

	/**
	 * Interface for a virtual method with no return value and three parameters
	 *
	 * @param <T1>
	 * 		Type of the first parameter
	 * @param <T2>
	 * 		Type of the second parameter
	 * @param <T3>
	 * 		Type of the third parameter
	 */
	public interface IAction3 <T1, T2, T3> {
		void apply(T1 in1, T2 in2, T3 in3);
	}

	/**
	 * Interface for a virtual method with no return value and four parameters
	 *
	 * @param <T1>
	 * 		Type of the first parameter
	 * @param <T2>
	 * 		Type of the second parameter
	 * @param <T3>
	 * 		Type of the third parameter
	 * @param <T4>
	 * 		Type of the fourth parameter
	 */
	public interface IAction4 <T1, T2, T3, T4> {
		void apply(T1 in1, T2 in2, T3 in3, T4 in4);
	}

	/**
	 * Interface for a virtual method with a return value and no parameters
	 *
	 * @param <R>
	 * 		Type of the return value
	 */
	public interface IFunc <R> {
		R apply( );
	}

	/**
	 * Interface for a virtual method with a return value and one parameter
	 *
	 * @param <T>
	 * 		Type of the parameter
	 * @param <R>
	 * 		Type of the return value
	 */
	public interface IFunc1 <T, R> {
		R apply(T in);
	}

	/**
	 * Interface for a virtual method with a return value and two parameters
	 *
	 * @param <T1>
	 * 		Type of the first parameter
	 * @param <T2>
	 * 		Type of the second parameter
	 * @param <R>
	 * 		Type of the return value
	 */
	public interface IFunc2 <T1, T2, R> {
		R apply(T1 in1, T2 in2);
	}

	/**
	 * Interface for a virtual method with a return value and three parameters
	 *
	 * @param <T1>
	 * 		Type of the first parameter
	 * @param <T2>
	 * 		Type of the second parameter
	 * @param <T3>
	 * 		Type of the third parameter
	 * @param <R>
	 * 		Type of the return value
	 */
	public interface IFunc3 <T1, T2, T3, R> {
		R apply(T1 in1, T2 in2, T3 in3);
	}

	/**
	 * Interface for a virtual method with a return value and four parameters
	 *
	 * @param <T1>
	 * 		Type of the first parameter
	 * @param <T2>
	 * 		Type of the second parameter
	 * @param <T3>
	 * 		Type of the third parameter
	 * @param <T4>
	 * 		Type of the fourth parameter
	 * @param <R>
	 * 		Type of the return value
	 */
	public interface IFunc4 <T1, T2, T3, T4, R> {
		R apply(T1 in1, T2 in2, T3 in3, T4 in4);
	}
}