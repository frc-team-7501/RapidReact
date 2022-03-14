package frc.robot;

public final class Constants {
    public static final class CANMapping {
        // DriveTrain
        public static final int VICTORSPX_DRIVE_FL = 1;
        public static final int VICTORSPX_DRIVE_BL = 2;
        public static final int VICTORSPX_DRIVE_FR = 3;
        public static final int VICTORSPX_DRIVE_BR = 4;
        public static final int PIGEON_IMU = 20;

        // IntakeArm
        public static final int SPARKMAX_INTAKEARM_WINCH = 44;
        
        // Intake
        public static final int SPARKMAX_INTAKE_RUN = 46;

        // Climber
        public static final int SPARKMAX_CLIMBER_ARM_L = 0; // TODO
        public static final int SPARKMAX_CLIMBER_ARM_R = 0;
        public static final int SPARKMAX_CLIMBER_WINCH_L = 0;
        public static final int SPARKMAX_CLIMBER_WINCH_R = 0;
    }

    public static final class DIOMapping {
        public static final int ENCODER_DRIVE_L[] = { 0, 1 };
        public static final int ENCODER_DRIVE_R[] = { 2, 3 };
    }

    public static final class ControllerMapping {
        public static final int JOYSTICK = 0;
        public static final int XBOX = 1;
    }

    public static final class Tunable {
        // Intake
        public static final double INTAKE_RUN_IN    = -0.4;
        public static final double INTAKE_RUN_OUT   = 1.0;
    }
}
