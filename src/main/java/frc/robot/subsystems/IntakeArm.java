package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.CANMapping;
import frc.robot.utils.InputNormalizer;

public class IntakeArm extends PIDSubsystem {
  private final CANSparkMax winchMotor = new CANSparkMax(CANMapping.SPARKMAX_WINCH, MotorType.kBrushless);
  
  public IntakeArm() {
    super(new PIDController(0.1, 0.01, 0.01));
    getController().setTolerance(2.0);
    winchMotor.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    runMotor(output);
  }

  @Override
  public double getMeasurement() {
    double position = winchMotor.getEncoder().getPosition();
    SmartDashboard.putNumber("IntakeArm pos", position);
    return position;
  }

  public void runMotor(double speed) {
    double normal = InputNormalizer.calculate(speed, 0, -1.0, 1.0);
    winchMotor.set(normal);
  }

  public void zeroEncoder() {
    winchMotor.getEncoder().setPosition(0.0);
  }

  public static enum IntakeArmPosition {
    DOWN,
    IDLE,
    UP,
  }

  public void setWinchSetpoint(final IntakeArmPosition pos) {
    // TODO: move this to a command
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
    setSetpoint(setpoint);
  }
}
