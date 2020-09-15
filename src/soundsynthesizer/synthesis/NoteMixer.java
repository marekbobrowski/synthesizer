package soundsynthesizer.synthesis;

/**
 * @author Marek Bobrowski
 */
public class NoteMixer {

    public NoteMixer() {
    }

    public double[][] mixAndNormalizeAllNotes(double[][][] arraysOfSamples) {
        int numberOfSamples = Converter.BUFFER_SIZE;
        double[][] mixedSamples = new double[2][numberOfSamples];

        for (int i = 0; i < numberOfSamples; i++) {
            double sumLeft = 0;
            double sumRight = 0;

            for (int j = 0; j < arraysOfSamples.length; j++) {
                if(i < arraysOfSamples[j][0].length) {
                    sumLeft += arraysOfSamples[j][0][i];
                    sumRight += arraysOfSamples[j][1][i];
                }
            }

            mixedSamples[0][i] = sumLeft / 16;
            mixedSamples[1][i] = sumRight / 16;
        }
        return mixedSamples;
    }

}

