package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;
import frc.robot.Constants.PWMMapping;

public class Intake extends SubsystemBase {
  // falcon left, right (intake)
  // servo left, right (pivot)

  private final WPI_TalonFX intakeL = new WPI_TalonFX(CANMapping.TALONFX_INTAKE_L);
  private final WPI_TalonFX intakeR = new WPI_TalonFX(CANMapping.TALONFX_INTAKE_R);

  private final Servo pivotL = new Servo(PWMMapping.SERVO_INTAKE_PIVOT_L);
  private final Servo pivotR = new Servo(PWMMapping.SERVO_INTAKE_PIVOT_R);
  
  private static Intake instance;
  public static Intake getInstance() {
    if (instance == null)
      instance = new Intake();
    return instance;
  }

  private Intake() {
    intakeR.follow(intakeL);
    intakeR.setInverted(InvertType.OpposeMaster);
  }

  @Override
  public void periodic() {
    // SmartDashboard...
  }

  private void setPivotLAngle(double angle) {
    pivotL.set(1 - (angle / 270.0));
  }

  private void setPivotRAngle(double angle) {
    pivotR.set(angle / 270.0);
  }

  private void setPivot(double angle) {
    setPivotLAngle(angle);
    setPivotRAngle(angle);
  }

  public void pivotIn() {
    setPivot(180);
  }

  public void pivotOut() {
    setPivot(0);
  }

  public void runIn() {
    // intakeL.set(TalonFXControlMode.Velocity, 1000);
    intakeL.set(TalonFXControlMode.PercentOutput, 0.4);
  }

  public void runOut() {
    intakeL.set(TalonFXControlMode.PercentOutput, -0.6);
  }

  public void stop() {
    intakeL.set(TalonFXControlMode.PercentOutput, 0);
    // intakeR.set(TalonFXControlMode.PercentOutput, 0);
  }
}
