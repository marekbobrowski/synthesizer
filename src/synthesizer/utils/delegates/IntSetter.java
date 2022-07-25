package synthesizer.utils.delegates;

/**
 * This interface works as a delegate class for a setter of int type.
 *
 * @author Marek Bobrowski
 */
public interface IntSetter {

    /**
     * This method works as a delegate for a setter of int type.
     *
     * @param value a value that will be used to call the assigned setter.
     */
    void set(int value);
}

