package frc.robot.utils;

import java.util.function.DoubleSupplier;

public class AngleNormalizer implements DoubleSupplier {
    private final DoubleSupplier supplier;
    private double zero;

    public AngleNormalizer(DoubleSupplier supplier) {
        this.supplier = supplier;
        zero = supplier.getAsDouble();
    }

    public final double get() {
        return (((supplier.getAsDouble() - zero) % 360) + 360) % 360;
    }

    @Override
    public double getAsDouble() {
        return get();
    }

    public void reset() {
        zero = supplier.getAsDouble();
    }
}
