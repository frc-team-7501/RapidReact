package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class ArmManualCommand extends CommandBase {
  private final Intake intake;
  private final DoubleSupplier input;

  public ArmManualCommand(final Intake intake, final DoubleSupplier input) {
    addRequirements(intake);
    this.intake = intake;
    this.input = input;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intake.runWinch(input.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    intake.runWinch(0.0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
