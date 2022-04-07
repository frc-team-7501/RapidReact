package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;

public class Climber extends SubsystemBase {
  private final CANSparkMax extendMotor = new CANSparkMax(CANMapping.SPARKMAX_CLIMBER_EXTEND, MotorType.kBrushless);

  private static Climber instance;
  public static Climber getInstance() {
    if (instance == null)
      instance = new Climber();
    return instance;
  }

  private Climber() {
    // Configure extendMotor

    extendMotor.restoreFactoryDefaults();
    extendMotor.setIdleMode(IdleMode.kBrake);

    extendMotor.setSmartCurrentLimit(20); // TODO: tune this if needed
  }
  
  public void manualControl(double extendPower) {
    if (Math.abs(extendPower) < 0.01) extendPower = 0;

    extendMotor.set(extendPower);
  }
}
