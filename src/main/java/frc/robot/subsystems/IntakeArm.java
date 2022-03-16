package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;
import frc.robot.utils.InputNormalizer;

public class IntakeArm extends SubsystemBase {
  private final CANSparkMax winchMotor = new CANSparkMax(CANMapping.SPARKMAX_INTAKEARM_WINCH, MotorType.kBrushless);
  
  public IntakeArm() {
    super();
    winchMotor.setIdleMode(IdleMode.kBrake);
  }
  
  public double getEncoderPosition() {
    return winchMotor.getEncoder().getPosition();
  }

  @Override
  public void periodic() {
    super.periodic();
    SmartDashboard.putNumber("IntakeArm pos", getEncoderPosition());
  }

  public void runMotor(double speed) {
    double normal = InputNormalizer.calculate(speed, 0, -1.0, 1.0);
    winchMotor.set(normal);
  }

  public void zeroEncoder() {
    winchMotor.getEncoder().setPosition(0.0);
  }
}
