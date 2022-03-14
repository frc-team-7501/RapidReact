package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveManualCommand extends CommandBase {
  private final DriveTrain driveTrain;

  private final DoubleSupplier forwardSupplier;
  private final DoubleSupplier rotateSupplier;
  private final BooleanSupplier quickTurnSupplier;
  private final BooleanSupplier turboSupplier;
  
  public DriveManualCommand(
      final DriveTrain driveTrain,
      DoubleSupplier forwardSupplier,
      DoubleSupplier rotateSupplier,
      BooleanSupplier quickTurnSupplier,
      BooleanSupplier turboSupplier) {
    addRequirements(driveTrain);
    this.driveTrain = driveTrain;
    this.forwardSupplier = forwardSupplier;
    this.rotateSupplier = rotateSupplier;
    this.quickTurnSupplier = quickTurnSupplier;
    this.turboSupplier = turboSupplier;
  }

  @Override
  public void initialize() {
    driveTrain.stop();
  }

  @Override
  public void execute() {
    driveTrain.drive(
      forwardSupplier.getAsDouble() * (turboSupplier.getAsBoolean() ? 1.0 : 0.75),
      rotateSupplier.getAsDouble(),
      quickTurnSupplier.getAsBoolean()
    );
  }

  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
