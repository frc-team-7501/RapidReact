package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Launcher;

public class LauncherVelocityContinuousCommand extends CommandBase {
  private final Launcher launcher;
  private final double rpm;
  
  public LauncherVelocityContinuousCommand(final Launcher launcher, final double rpm) {
    this.launcher = launcher;
    this.rpm = rpm;
    addRequirements(launcher);
  }

  @Override
  public void initialize() {
    if (rpm == 0)
      launcher.stopShooter();
    else
      launcher.runShooter(rpm);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    launcher.stopShooter();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
