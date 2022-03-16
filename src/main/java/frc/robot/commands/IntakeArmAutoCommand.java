package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.IntakeArm;

public class IntakeArmAutoCommand extends PIDCommand {
  public IntakeArmAutoCommand(final IntakeArm intakeArm, final IntakeArmPosition position) {
    super(
        new PIDController(0.1, 0.01, 0.01),
        // This should return the measurement
        () -> intakeArm.getEncoderPosition(),
        // This should return the setpoint (can also be a constant)
        () -> calculateSetpoint(position),
        output -> {
          intakeArm.runMotor(output);
        });
    addRequirements(intakeArm);
    getController().setTolerance(2.0);
  }

  public static enum IntakeArmPosition {
    DOWN,
    IDLE,
    UP,
  }

  public static final double calculateSetpoint(final IntakeArmPosition pos) {
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
    return setpoint;
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
