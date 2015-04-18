package com.samborskiy.statistic;

/**
 * Structure for holding statistics of testAccounts.
 * <p>
 * Created by warrior on 19.09.14.
 */
public class Statistics {

    private double fMeasure;
    private double precision;
    private double recall;

    /**
     * Creates instance of {@code Statistics} with init precision and recall.
     *
     * @param precision {@link #precision}
     * @param recall    {@link #recall}
     */
    public Statistics(double precision, double recall) {
        this.precision = precision;
        this.recall = recall;
        this.fMeasure = computeFDistance(precision, recall);
    }

    /**
     * Computes F-Measure value
     *
     * @param precision {@link #precision}
     * @param recall    {@link #recall}
     * @return F-Measure value
     */
    private static double computeFDistance(double precision, double recall) {
        return 2 * precision * recall / (precision + recall);
    }

    public double getFMeasure() {
        return fMeasure;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }

    /**
     * Gets {@link #Statistics} using {@code confusionMatrix}
     *
     * @param confusionMatrix confusion matrix
     * @return {@link #Statistics}
     */
    public static Statistics createStatistics(int[][] confusionMatrix) {
        checkConfusionMatrix(confusionMatrix);
        double[] answer = new double[2];
        computePrecisionAndRecall(confusionMatrix, answer);
        return new Statistics(answer[0], answer[1]);
    }

    private static void checkConfusionMatrix(int[][] confusionMatrix) {
        if (confusionMatrix == null) {
            throw new IllegalArgumentException("confusionMatrix must be not null");
        }
        if (confusionMatrix.length != confusionMatrix[0].length) {
            throw new IllegalArgumentException("confusionMatrix must be square");
        }
    }

    /**
     * @param ans answer array. After method's execution {@code ans[0]} contains precision,
     *            {@code ans[1]} contains recall
     * @see <a href="http://bazhenov.me/blog/2012/07/21/classification-performance-evaluation.html">confusionMatrix</a>
     */
    private static void computePrecisionAndRecall(int[][] confusionMatrix, double[] ans) {
        if (confusionMatrix == null) {
            throw new IllegalArgumentException("confusionMatrix must be not null");
        }
        if (confusionMatrix.length != confusionMatrix[0].length) {
            throw new IllegalArgumentException("confusionMatrix must be square");
        }
        if (ans == null || ans.length < 2) {
            throw new IllegalArgumentException("answer array length must be >= 2");
        }
        int size = confusionMatrix.length;
        int precisionCount = 0;
        int recallCount = 0;
        ans[0] = 0;
        ans[1] = 0;
        for (int i = 0; i < size; i++) {
            double sum = 0;
            for (int j = 0; j < size; j++) {
                sum += confusionMatrix[i][j];
            }
            if (sum != 0) {
                ans[0] += confusionMatrix[i][i] / sum;
                precisionCount++;
            }
            sum = 0;
            for (int j = 0; j < size; j++) {
                sum += confusionMatrix[j][i];
            }
            if (sum != 0) {
                ans[1] += confusionMatrix[i][i] / sum;
                recallCount++;
            }

        }
        ans[0] /= precisionCount;
        ans[1] /= recallCount;
    }

    @Override
    public String toString() {
        return String.format("F-measure = %f\nPrecision = %f\nRecall = %f", fMeasure, precision, recall);
    }
}
