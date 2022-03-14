
package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.utils.InputNormalizer;

public class DriveAutoCommand extends CommandBase {
  private final DriveTrain driveTrain;

  private final PIDController pidF = new PIDController(0.5, 0.02, 0.01);
  private final PIDController pidR = new PIDController(0.045, 0.3, 0.02);
  
  private final double setF, setR;

  private boolean closedLoopReady = false;

  public DriveAutoCommand(final DriveTrain driveTrain, final double setF, final double setR) {
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);

    pidF.setTolerance(0.08);
    pidR.setTolerance(0.08);

    pidR.enableContinuousInput(0.0, 360.0);
    
    this.setF = setF;
    this.setR = setR;
  }

  @Override
  public void execute() {
    boolean quickTurn = pidF.atSetpoint();

    double measureF = (driveTrain.getDistanceL() + driveTrain.getDistanceR()) / 2;
    double normalF = closedLoopReady && pidF.atSetpoint()
      ? 0.0
      : InputNormalizer.calculate(pidF.calculate(measureF, setF), 0.03, -0.5, 0.5);
      // : InputNormalizer.calculate(pidF.calculate(measureF, setF), 0.03, -1.0, 1.0);

    double measureR = driveTrain.getRotation();
    double normalR = closedLoopReady && pidR.atSetpoint()
      ? 0.0
      : InputNormalizer.calculate(pidR.calculate(measureR, setR), 0.01, -0.5, 0.5);
    
    driveTrain.drive(normalF, -normalR, quickTurn);

    closedLoopReady = true;

    System.err.println("execute " + normalF + ", " + -normalR + ", " + quickTurn);
  }

  @Override
  public void initialize() {
    super.initialize();
    
    driveTrain.zeroL();
    driveTrain.zeroR();
    driveTrain.zeroRotation();

    pidF.reset();
    pidR.reset();
  }

  @Override
  public boolean isFinished() {
    boolean res = closedLoopReady && pidF.atSetpoint() && pidR.atSetpoint();
    System.err.println("isFinished: " + res);
    return res;
  }

  @Override
  public void end(boolean interrupted) {
    driveTrain.drive(0, 0, false);
    closedLoopReady = false;
  }
}
