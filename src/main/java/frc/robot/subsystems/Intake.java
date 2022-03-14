package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.CANMapping;
import frc.robot.utils.InputNormalizer;

public class Intake extends PIDSubsystem {
  public static enum IntakePosition {
    DOWN,
    IDLE,
    UP,
  }
  
  private final CANSparkMax intakeMotor = new CANSparkMax(CANMapping.SPARKMAX_INTAKE_RUN, MotorType.kBrushless);
  private final CANSparkMax winchMotor = new CANSparkMax(CANMapping.SPARKMAX_WINCH, MotorType.kBrushless);
  
  public Intake() {
    super(new PIDController(0.1, 0.01, 0.01));

    getController().setTolerance(2.0);

    winchMotor.setIdleMode(IdleMode.kBrake);
  }

  public void runIntake(double speed) {
    intakeMotor.set(speed);
  }

  public void runWinch(double speed) {
    winchMotor.set(speed);
  }

  public void setWinchSetpoint(final IntakePosition pos) {
    double setpoint;
    switch (pos) {
      case DOWN:
        setpoint = 95.0;
        break;
      case IDLE:
        setpoint = 6.0;
        break;
      case UP:
        setpoint = 0;
        break;
      default:
        setpoint = 0;
        break;
    }
    // enable();
    setSetpoint(setpoint);
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    SmartDashboard.putNumber("PID output", output);
    double normal = InputNormalizer.calculate(output, 0, -1.0, 1.0);
    SmartDashboard.putNumber("PID out normal", normal);
    SmartDashboard.putNumber("PID setpoint", setpoint);
    runWinch(normal);
  }

  @Override
  protected double getMeasurement() {
    double measure = winchMotor.getEncoder().getPosition();
    SmartDashboard.putNumber("PID measure", measure);
    return measure;
  }
  
  public void setWinchEncoderZero() {
    winchMotor.getEncoder().setPosition(0.0);
  }
}
