package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class IndexerFeedForwardContinuousCommand extends CommandBase {
  private final Indexer indexer;
  private final boolean runKick;
  
  public IndexerFeedForwardContinuousCommand(final Indexer indexer, final boolean runKick) {
    this.indexer = indexer;
    this.runKick = runKick;
    addRequirements(indexer);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (runKick)
      indexer.runBothForward();
    else
      indexer.runFeedForward();
  }

  @Override
  public void end(boolean interrupted) {
    indexer.stopBoth();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
