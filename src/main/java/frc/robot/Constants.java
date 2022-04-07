package frc.robot;

public final class Constants {
    public static final class CANMapping {
        // Climber
        public static final int SPARKMAX_CLIMBER_EXTEND = 42;

        // DriveTrain
        public static final int VICTORSPX_DRIVE_BL = 1;
        public static final int VICTORSPX_DRIVE_BR = 2;
        public static final int VICTORSPX_DRIVE_FL = 3;
        public static final int VICTORSPX_DRIVE_FR = 4;

        // Intake
        public static final int TALONFX_INTAKE_L = 11;
        public static final int TALONFX_INTAKE_R = 12;
        public static final int TALONSRX_INTAKE_AGITATOR = 31;
        public static final int TALONSRX_INTAKE_PIVOT_L = 33;
        public static final int TALONSRX_INTAKE_PIVOT_R = 32;

        // Indexer
        public static final int SPARKMAX_INDEXER_FEED = 22;
        public static final int SPARKMAX_INDEXER_KICK = 21;

        // Launcher
        public static final int TALONFX_LAUNCHER_SHOOTER_F = 13;
        public static final int TALONFX_LAUNCHER_SHOOTER_B = 14;
    }

    public static final class DIOMapping {
        // Intake
        public static final int[] ENCODER_INTAKE_PIVOT_L = { 0, 1 };
        public static final int[] ENCODER_INTAKE_PIVOT_R = { 2, 3 };
    }

    public static final class ControllerMapping {
        public static final int JOYSTICK = 0;
        public static final int XBOX = 1;
    }

    public static final class Setpoints {
        // Launcher
        public static final double LAUNCHER_SHOOT_CLOSE_RPM = 2200;
    }
}
