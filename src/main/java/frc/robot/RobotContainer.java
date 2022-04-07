package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ControllerMapping;
import frc.robot.Constants.Setpoints;
import frc.robot.commands.ClimberManualCommand;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.IndexerFeedForwardContinuousCommand;
import frc.robot.commands.IndexerFullReverseContinuousCommand;
import frc.robot.commands.IntakePositionCommand;
import frc.robot.commands.IntakeRunInCommand;
import frc.robot.commands.IntakeRunOutCommand;
import frc.robot.commands.LauncherVelocityContinuousCommand;
import frc.robot.commands.IntakePositionCommand.IntakePosition;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.utils.ExtendedJoystick;
import frc.robot.utils.ExtendedXboxController;
import frc.robot.utils.InputNormalizer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;

public class RobotContainer {
  private final ExtendedJoystick stick = new ExtendedJoystick(ControllerMapping.JOYSTICK);
  private final ExtendedXboxController controller = new ExtendedXboxController(ControllerMapping.XBOX);

  private final Climber climber = Climber.getInstance();
  private final DriveTrain driveTrain = DriveTrain.getInstance();
  private final Intake intake = Intake.getInstance();
  private final Indexer indexer = Indexer.getInstance();
  private final Launcher launcher = Launcher.getInstance();

  private final DriveManualCommand driveManualCommand = new DriveManualCommand(
    driveTrain,
    () -> stick.getY() * (stick.getThrottle() * 0.5 + 0.5), () -> -stick.getX(),
    () -> stick.getTop()
  );

  private final ClimberManualCommand climberManualCommand = new ClimberManualCommand(climber, () -> InputNormalizer.calculate(controller.getLeftY(), 0.05, -1, 1));

  private final SequentialCommandGroup autonA = new SequentialCommandGroup(
    new WaitCommand(1.0),
    new WaitCommand(1.0).deadlineWith(new IndexerFeedForwardContinuousCommand(indexer, true))
    // TODO: drive backwards
  );

  private final SendableChooser<Command> autonChooser = new SendableChooser<>();

  public RobotContainer() {
    autonChooser.addOption("A", autonA);
    SmartDashboard.putData("auton", autonChooser);

    configureButtonBindings();

    driveTrain.setDefaultCommand(driveManualCommand);
    launcher.setDefaultCommand(new LauncherVelocityContinuousCommand(launcher, Setpoints.LAUNCHER_SHOOT_CLOSE_RPM));
    climber.setDefaultCommand(climberManualCommand);

    ShuffleboardTab subsysTab = Shuffleboard.getTab("Subsystems");
    subsysTab.add("Climber", climber);
    subsysTab.add("DriveTrain", driveTrain);
    subsysTab.add("Intake", intake);
    subsysTab.add("Indexer", indexer);
    subsysTab.add("Launcher", launcher);
  }

  // Lifecycle hooks

  public void autonomousInit() {
    driveTrain.setBrakeMode(true);
  }

  public void teleopInit() {
    driveTrain.setBrakeMode(false);
  }

  private void configureButtonBindings() {
    controller.b_LeftBumper()
      .whileActiveOnce(
        new SequentialCommandGroup(
          new IntakePositionCommand(intake, IntakePosition.OUT),
          new ParallelCommandGroup(
            new IntakeRunInCommand(intake),
            new IndexerFeedForwardContinuousCommand(indexer, false)
          )
        )
      );
    controller.b_RightBumper()
      .whileActiveOnce(
        new SequentialCommandGroup(
          new IntakePositionCommand(intake, IntakePosition.OUT),
          new ParallelCommandGroup(
            new IntakeRunOutCommand(intake),
            new IndexerFullReverseContinuousCommand(indexer)
          )
        )
      );
    
    controller.b_A()
      .whenPressed(new IntakePositionCommand(intake, IntakePosition.OUT));
    controller.b_B()
      .whenPressed(new IntakePositionCommand(intake, IntakePosition.IN));

    controller.b_Start()
      .whenPressed(new InstantCommand(launcher::play, launcher));

    stick.b_Trigger()
      .whenPressed(new WaitCommand(1.0).deadlineWith(new IndexerFeedForwardContinuousCommand(indexer, true)));
  }

  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}
