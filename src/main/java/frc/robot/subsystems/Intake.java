package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;
import frc.robot.Constants.Tunable;

public class Intake extends SubsystemBase {
  private final CANSparkMax intakeMotor = new CANSparkMax(CANMapping.SPARKMAX_INTAKE_RUN, MotorType.kBrushless);
  
  public Intake() {
    super();
  }

  public void in() {
    intakeMotor.set(Tunable.INTAKE_RUN_IN);
  }

  public void out() {
    intakeMotor.set(Tunable.INTAKE_RUN_OUT);
  }

  public void stop() {
    intakeMotor.set(0);
  }
}
