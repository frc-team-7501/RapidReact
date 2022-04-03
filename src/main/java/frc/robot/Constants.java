package frc.robot;

public final class Constants {
    public static final class CANMapping {
        // DriveTrain
        public static final int VICTORSPX_DRIVE_BL = 1;
        public static final int VICTORSPX_DRIVE_BR = 2;
        public static final int VICTORSPX_DRIVE_FL = 3;
        public static final int VICTORSPX_DRIVE_FR = 4;

        // Intake
        public static final int TALONFX_INTAKE_L = 11;
        public static final int TALONFX_INTAKE_R = 12;

        // Indexer
        public static final int SPARKMAX_INDEXER_FEED = 22;
        public static final int SPARKMAX_INDEXER_KICK = 21;

        // Launcher
        public static final int TALONFX_LAUNCHER_SHOOTER_F = 13;
        public static final int TALONFX_LAUNCHER_SHOOTER_B = 14;
    }

    public static final class PWMMapping {
        // Intake
        public static final int SERVO_INTAKE_PIVOT_L = 0;
        public static final int SERVO_INTAKE_PIVOT_R = 1;
    }

    public static final class ControllerMapping {
        public static final int JOYSTICK = 0;
        public static final int XBOX = 1;
    }
}
