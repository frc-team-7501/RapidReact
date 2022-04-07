package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberManualCommand extends CommandBase {
  private final Climber climber;
  private final DoubleSupplier supplier;
  
  public ClimberManualCommand(final Climber climber, final DoubleSupplier supplier) {
    this.climber = climber;
    this.supplier = supplier;
    addRequirements(climber);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double input = supplier.getAsDouble();
    climber.manualControl(input);
  }

  @Override
  public void end(boolean interrupted) {
    climber.manualControl(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
