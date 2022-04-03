package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ControllerMapping;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.IndexerFeedForwardContinuousCommand;
import frc.robot.commands.IndexerFullReverseContinuousCommand;
import frc.robot.commands.IntakeRunInCommand;
import frc.robot.commands.IntakeRunOutCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.utils.ExtendedJoystick;
import frc.robot.utils.ExtendedXboxController;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

public class RobotContainer {
  private final ExtendedJoystick stick = new ExtendedJoystick(ControllerMapping.JOYSTICK);
  private final ExtendedXboxController controller = new ExtendedXboxController(ControllerMapping.XBOX);

  private final DriveTrain driveTrain = DriveTrain.getInstance();
  private final Intake intake = Intake.getInstance();
  private final Indexer indexer = Indexer.getInstance();
  private final Launcher launcher = Launcher.getInstance();

  private final DriveManualCommand driveManualCommand = new DriveManualCommand(
      driveTrain,
      () -> -stick.getY(), () -> -stick.getX(),
      stick::getTop, () -> true); // TODO: use throttle

  private final SequentialCommandGroup shootCommand = new SequentialCommandGroup(
    new ParallelDeadlineGroup(
      new WaitCommand(1.0),
      new InstantCommand(launcher::runShooterShort, launcher),
      new InstantCommand(indexer::runKickReverse, indexer)
    ),
    new ParallelDeadlineGroup(
      new WaitCommand(1.5),
      new InstantCommand(indexer::runBothForward, indexer)
    ),
    new ParallelCommandGroup(
      new InstantCommand(launcher::stopShooter, launcher),
      new InstantCommand(indexer::stopBoth, indexer)
    )
  );

  private final SequentialCommandGroup autonA = new SequentialCommandGroup(
    new SequentialCommandGroup(
      new ParallelDeadlineGroup(
        new WaitCommand(1.0),
        new InstantCommand(launcher::runShooterShort, launcher),
        new InstantCommand(indexer::runKickReverse, indexer)
      ),
      new ParallelDeadlineGroup(
        new WaitCommand(1.5),
        new InstantCommand(indexer::runBothForward, indexer)
      ),
      new ParallelCommandGroup(
        new InstantCommand(launcher::stopShooter, launcher),
        new InstantCommand(indexer::stopBoth, indexer)
      )
    )
  );

  private final SendableChooser<Command> autonChooser = new SendableChooser<>();

  public RobotContainer() {
    autonChooser.addOption("A", autonA);
    SmartDashboard.putData("auton", autonChooser);

    configureButtonBindings();

    driveTrain.setDefaultCommand(driveManualCommand);
  }

  public void teleopInit() {
    // TODO: drive brake mode disabled
  }

  private void configureButtonBindings() {
    // TODO: Check if servos are already set to out setpoint. If not, wait before starting intakerun.
    controller.b_LeftBumper()
      .whileActiveOnce(
        new SequentialCommandGroup(
          new InstantCommand(intake::pivotOut, intake),
          new ParallelCommandGroup(
            new IntakeRunInCommand(intake),
            new IndexerFeedForwardContinuousCommand(indexer)
          )
        )
      );
    controller.b_RightBumper()
      .whileActiveOnce(
        new SequentialCommandGroup(
          new InstantCommand(intake::pivotOut, intake),
          new ParallelCommandGroup(
            new IntakeRunOutCommand(intake),
            new IndexerFullReverseContinuousCommand(indexer)
          )
        )
      );
    
    controller.b_A()
      .whenPressed(new InstantCommand(intake::pivotOut, intake));
    controller.b_B()
      .whenPressed(new InstantCommand(intake::pivotIn, intake));
    
    controller.b_Start()
      .whenPressed(new InstantCommand(launcher::play, launcher));

    stick.b_Trigger()
      .whenPressed(shootCommand);
  }

  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}
