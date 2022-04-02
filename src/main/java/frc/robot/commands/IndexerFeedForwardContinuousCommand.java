package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class IndexerFeedForwardContinuousCommand extends CommandBase {
  private final Indexer indexer;
  
  public IndexerFeedForwardContinuousCommand(final Indexer indexer) {
    this.indexer = indexer;
    addRequirements(indexer);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    indexer.runFeedForward();
  }

  @Override
  public void end(boolean interrupted) {
    indexer.stopFeed();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
