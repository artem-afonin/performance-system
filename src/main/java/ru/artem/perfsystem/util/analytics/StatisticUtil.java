package ru.artem.perfsystem.util.analytics;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class StatisticUtil {

    public static double sum(double[] values) {
        validate(values);
        double sum = 0;
        for (double value : values)
            sum += value;
        return sum;
    }

    public static double min(double[] values) {
        validate(values);
        double min = Double.MAX_VALUE;
        for (double value: values)
            if (value < min)
                min = value;
        return min;
    }

    public static double max(double[] values) {
        validate(values);
        double max = Double.MIN_VALUE;
        for (double value: values)
            if (value > max)
                max = value;
        return max;
    }

    public static double mean(double[] values) {
        validate(values);
        return sum(values) / values.length;
    }

    public static double geomean(double[] values) {
        validate(values);
        if (ArrayUtils.contains(values, 0.0))
            throw new IllegalArgumentException("Array contains zero. Geomean is not applicable");

        double sum = 0;
        for (double value : values)
            sum += Math.log(value);
        return Math.exp(sum / values.length);
    }

    public static double deviation(double[] values) {
        validate(values);
        double min = min(values);
        double max = max(values);
        return (max - min) / max * 100;
    }

    public static double variation(double[] values) {
        validate(values);
        double mean = mean(values);
        double variation = 0;
        for (double value : values)
            variation += Math.pow(value - mean, 2);
        return variation / values.length;
    }

    public static double stddev(double[] values) {
        validate(values);
        return Math.sqrt(variation(values));
    }

    public static double percentile(double[] values, double percentile) {
        validate(values);
        if (0.0 > percentile || percentile > 1.0)
            throw new IllegalArgumentException("Percentile should be in range [0.0 : 1.0]");

        double[] clonedValues = new double[values.length];
        System.arraycopy(values, 0, clonedValues, 0, values.length);
        Arrays.sort(clonedValues);
        return clonedValues[(int)(percentile * clonedValues.length)];
    }

    private static void validate(double[] values) {
        if (values == null)
            throw new NullPointerException("Array is null");
        if (values.length == 0)
            throw new IllegalArgumentException("Array is empty");
    }

}
