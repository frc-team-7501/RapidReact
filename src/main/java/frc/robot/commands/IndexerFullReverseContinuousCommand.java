package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class IndexerFullReverseContinuousCommand extends CommandBase {
  private final Indexer indexer;
  
  public IndexerFullReverseContinuousCommand(final Indexer indexer) {
    this.indexer = indexer;
    addRequirements(indexer);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    indexer.runBothReverse();
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
