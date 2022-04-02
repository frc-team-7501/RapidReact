package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;

public class DriveTrain extends SubsystemBase {
  private final WPI_VictorSPX motorFL = new WPI_VictorSPX(CANMapping.VICTORSPX_DRIVE_FL);
  private final WPI_VictorSPX motorFR = new WPI_VictorSPX(CANMapping.VICTORSPX_DRIVE_FR);
  private final WPI_VictorSPX motorBL = new WPI_VictorSPX(CANMapping.VICTORSPX_DRIVE_BL);
  private final WPI_VictorSPX motorBR = new WPI_VictorSPX(CANMapping.VICTORSPX_DRIVE_BR);
  
  private final MotorControllerGroup groupL = new MotorControllerGroup(motorFL, motorBL);
  private final MotorControllerGroup groupR = new MotorControllerGroup(motorFR, motorBR);

  private final DifferentialDrive differentialDrive = new DifferentialDrive(groupL, groupR);

  private static DriveTrain instance;
  public static DriveTrain getInstance() {
    if (instance == null)
      instance = new DriveTrain();
    return instance;
  }

  private DriveTrain() {
    // motorBL.setNeutralMode(NeutralMode.Brake);
    // motorBR.setNeutralMode(NeutralMode.Brake);
    // motorFL.setNeutralMode(NeutralMode.Brake);
    // motorFR.setNeutralMode(NeutralMode.Brake);
    
    groupL.setInverted(true);
  }

  public void drive(double forwards, double rotate, boolean quickTurn) {
    differentialDrive.curvatureDrive(-forwards, rotate, quickTurn);
  }

  public void stop() {
    // differentialDrive.stopMotor();
    drive(0, 0, false);
  }
}
