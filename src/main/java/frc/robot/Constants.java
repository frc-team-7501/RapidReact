package frc.robot;

public final class Constants {
    public static final class CANMapping {
        public static final int VICTORSPX_DRIVE_FL = 1;
        public static final int VICTORSPX_DRIVE_BL = 2;
        public static final int VICTORSPX_DRIVE_FR = 3;
        public static final int VICTORSPX_DRIVE_BR = 4;

        public static final int PIGEON_IMU = 20;

        public static final int SPARKMAX_WINCH = 44;
        public static final int SPARKMAX_INTAKE_RUN = 46;
    }

    public static final class DIOMapping {
        public static final int ENCODER_DRIVE_L[] = { 0, 1 };
        public static final int ENCODER_DRIVE_R[] = { 2, 3 };
    }

    public static final class ControllerMapping {
        public static final int JOYSTICK = 0;
        public static final int XBOX = 1;
    }
}
