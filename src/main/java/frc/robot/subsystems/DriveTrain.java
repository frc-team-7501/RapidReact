package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;
import frc.robot.Constants.DIOMapping;
import frc.robot.utils.AngleNormalizer;

public class DriveTrain extends SubsystemBase {
  private final WPI_VictorSPX motorFL = new WPI_VictorSPX(CANMapping.VICTORSPX_DRIVE_FL);
  private final WPI_VictorSPX motorFR = new WPI_VictorSPX(CANMapping.VICTORSPX_DRIVE_FR);
  private final WPI_VictorSPX motorBL = new WPI_VictorSPX(CANMapping.VICTORSPX_DRIVE_BL);
  private final WPI_VictorSPX motorBR = new WPI_VictorSPX(CANMapping.VICTORSPX_DRIVE_BR);
  
  private final MotorControllerGroup groupL = new MotorControllerGroup(motorFL, motorBL);
  private final MotorControllerGroup groupR = new MotorControllerGroup(motorFR, motorBR);

  private final DifferentialDrive differentialDrive = new DifferentialDrive(groupL, groupR);

  private final Encoder encoderL = new Encoder(DIOMapping.ENCODER_DRIVE_L[0], DIOMapping.ENCODER_DRIVE_L[1]);
  private final Encoder encoderR = new Encoder(DIOMapping.ENCODER_DRIVE_R[0], DIOMapping.ENCODER_DRIVE_R[1]);

  private final PigeonIMU pigeon = new PigeonIMU(CANMapping.PIGEON_IMU);
  private final AngleNormalizer headingNormal = new AngleNormalizer(pigeon::getFusedHeading);


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    // motorBL.setNeutralMode(NeutralMode.Brake);
    // motorBR.setNeutralMode(NeutralMode.Brake);
    // motorFL.setNeutralMode(NeutralMode.Brake);
    // motorFR.setNeutralMode(NeutralMode.Brake);
    
    groupL.setInverted(true);

    encoderL.setDistancePerPulse(1.0 / 225.0);
    encoderR.setDistancePerPulse(-1.0 / 225.0);
  }

  public void drive(double forwards, double rotate, boolean quickTurn) {
    differentialDrive.curvatureDrive(-forwards, rotate, quickTurn);
  }

  public void stop() {
    // differentialDrive.stopMotor();
    drive(0, 0, false);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("drive encoder L", getDistanceL());
    SmartDashboard.putNumber("drive encoder R", getDistanceR());

    SmartDashboard.putNumber("heading raw", pigeon.getFusedHeading());
    SmartDashboard.putNumber("heading normal", headingNormal.getAsDouble());

  }

  public double getRotation() {
    return headingNormal.getAsDouble();
  }
  
  public double getDistanceL() {
    return encoderL.getDistance();
  }

  public double getDistanceR() {
    return encoderR.getDistance();
  }

  public void zeroRotation() {
    headingNormal.reset();
  }

  public void zeroL() {
    encoderL.reset();
  }
  
  public void zeroR() {
    encoderR.reset();
  }
}
