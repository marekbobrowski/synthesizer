package synthesizer.utils.delegates;

/**
 * This interface works as a delegate class for a setter of double type.
 *
 * @author Marek Bobrowski
 */
public interface DoubleSetter {

    /**
     * This method works as a delegate for a setter of double type.
     *
     * @param value a value that will be used to call the assigned setter.
     */
    void set(double value);
}
