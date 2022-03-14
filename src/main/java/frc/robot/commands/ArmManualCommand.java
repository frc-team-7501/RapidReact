package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;

public class ArmManualCommand extends CommandBase {
  private final IntakeArm intakeArm;
  private final DoubleSupplier input;

  public ArmManualCommand(final IntakeArm intakeArm, final DoubleSupplier input) {
    addRequirements(intakeArm);
    this.intakeArm = intakeArm;
    this.input = input;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intakeArm.runMotor(input.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    intakeArm.runMotor(0.0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
