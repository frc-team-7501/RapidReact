package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeRunCommand extends CommandBase {
  private final Intake intake;
  private final double speed;
  
  public IntakeRunCommand(final Intake intake, final double speed) {
    this.intake = intake;
    this.speed = speed;
    addRequirements(intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intake.runIntake(speed);
  }

  @Override
  public void end(boolean interrupted) {
    intake.runIntake(0.0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
