package frc.robot;

public final class Constants {
    public static final class CANMapping {
        // DriveTrain
        public static final int VICTORSPX_DRIVE_BL = 1;
        public static final int VICTORSPX_DRIVE_BR = 2;
        public static final int VICTORSPX_DRIVE_FL = 3;
        public static final int VICTORSPX_DRIVE_FR = 4;

        // Intake
        public static final int TALONFX_INTAKE_L = 11; // TODO
        public static final int TALONFX_INTAKE_R = 12; // TODO

        // Indexer
        public static final int SPARKMAX_INDEXER_FEED = 22; // TODO
        public static final int SPARKMAX_INDEXER_KICK = 21; // TODO

        // Launcher
        public static final int TALONFX_LAUNCHER_SHOOTER_F = 13; // TODO
        public static final int TALONFX_LAUNCHER_SHOOTER_B = 14; // TODO
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
