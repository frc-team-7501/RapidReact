package frc.robot.utils;

import java.util.function.DoubleSupplier;

public class InputNormalizer implements DoubleSupplier {
    private final DoubleSupplier source;
    private final double deadband;
    private final double min;
    private final double max;

    public InputNormalizer(final DoubleSupplier source, final double deadband, final double min, final double max) {
        this.source = source;
        this.deadband = deadband;
        this.min = min;
        this.max = max;
    }

    public InputNormalizer(final DoubleSupplier source, final double deadband) {
        this(source, deadband, -1.0, 1.0);
    }

    public InputNormalizer(final DoubleSupplier source) {
        this(source, 0.0);
    }

    @Override
    public double getAsDouble() {
        double value = source.getAsDouble();
        return calculate(value, deadband, min, max);
    }

    public static double calculate(double in, double deadband, double min, double max) {
        double curvy = Math.abs(in) < deadband
            ? 0
            : (Math.signum(in) / (1.0 - deadband)) * (Math.abs(in) - deadband);
        double normalized = Math.max(min, Math.min(curvy * curvy * Math.signum(in), max));

        return normalized;
    }
}
