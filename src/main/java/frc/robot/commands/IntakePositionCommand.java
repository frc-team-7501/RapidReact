package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakePositionCommand extends CommandBase {
  public static enum IntakePosition {
    IN,
    OUT,
  }

  private final Intake intake;
  private final IntakePosition pos;

  public IntakePositionCommand(final Intake intake, final IntakePosition pos) {
    this.intake = intake;
    this.pos = pos;
  }

  @Override
  public void initialize() {
    switch (pos) {
      case IN:
        intake.pivotIn();
        break;
      case OUT:
        intake.pivotOut();
        break;
    }
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    switch (pos) {
      case IN:
        return intake.isPivotIn();
      case OUT:
        return intake.isPivotOut();
      default:
        return true;
    }
  }
}
