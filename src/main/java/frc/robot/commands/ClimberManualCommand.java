package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberManualCommand extends CommandBase {
  private final Climber climber;
  private final DoubleSupplier armSupplier;
  private final DoubleSupplier winchSupplier;

  public ClimberManualCommand(final Climber climber, final DoubleSupplier armSupplier, final DoubleSupplier winchSupplier) {
    this.climber = climber;
    this.armSupplier = armSupplier;
    this.winchSupplier = winchSupplier;

    addRequirements(climber);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double arm = armSupplier.getAsDouble();
    double winch = winchSupplier.getAsDouble();
    climber.setArmL(-arm); // TODO: is this inverted?
    climber.setArmR(arm);
    climber.setWinchL(-winch);
    climber.setWinchR(winch);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
