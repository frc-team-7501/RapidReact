package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;

public class Indexer extends SubsystemBase {
  private final CANSparkMax feedMotor = new CANSparkMax(CANMapping.SPARKMAX_INDEXER_FEED, MotorType.kBrushless);
  private final CANSparkMax kickMotor = new CANSparkMax(CANMapping.SPARKMAX_INDEXER_KICK, MotorType.kBrushless);

  // Limit switches...
  
  private static Indexer instance;
  public static Indexer getInstance() {
    if (instance == null)
      instance = new Indexer();
    return instance;
  }

  private Indexer() {
    feedMotor.setInverted(true);
    kickMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // SmartDashboard...
  }

  public void runFeedForward() {
    feedMotor.set(0.5);
  }

  public void runFeedReverse() {
    feedMotor.set(-0.5);
  }

  public void stopFeed() {
    feedMotor.set(0);
  }

  public void runKick() {
    kickMotor.set(0.5);
  }

  public void stopKick() {
    kickMotor.set(0);
  }

  public void runBoth() {
    runFeedForward();
    runKick();
  }

  public void stopBoth() {
    feedMotor.set(0);
    kickMotor.set(0);
  }
}
