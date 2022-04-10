package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveConstantCommand extends CommandBase {
  private final DriveTrain driveTrain;
  private final double forwardSpeed;
  private final double rotateSpeed;
  private final boolean quickTurn;
  
  public DriveConstantCommand(final DriveTrain driveTrain, final double forwardSpeed, final double rotateSpeed, final boolean quickTurn) {
    this.driveTrain = driveTrain;
    this.forwardSpeed = forwardSpeed;
    this.rotateSpeed = rotateSpeed;
    this.quickTurn = quickTurn;
    addRequirements(driveTrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    driveTrain.drive(-forwardSpeed, rotateSpeed, quickTurn);
  }

  @Override
  public void end(boolean interrupted) {
    driveTrain.drive(0, 0, false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
