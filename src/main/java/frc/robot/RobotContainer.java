package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ControllerMapping;
import frc.robot.commands.ClimberManualCommand;
import frc.robot.commands.DriveAutoCommand;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.IntakeArmAutoCommand;
import frc.robot.commands.IntakeInCommand;
import frc.robot.commands.IntakeOutCommand;
import frc.robot.commands.IntakeArmAutoCommand.IntakeArmPosition;
import frc.robot.commands.ArmManualCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeArm;
import frc.robot.utils.ExtendedXboxController;
import frc.robot.utils.InputNormalizer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RobotContainer {
  private final Joystick driveJoystick = new Joystick(ControllerMapping.JOYSTICK);
  private final ExtendedXboxController controller = new ExtendedXboxController(ControllerMapping.XBOX);


  private final DriveTrain driveTrain = new DriveTrain();
  private final Intake intake = new Intake();
  private final IntakeArm intakeArm = new IntakeArm();
  private final Climber climber = new Climber();


  private final DriveManualCommand driveManualCommand = new DriveManualCommand(
    driveTrain,
    () -> -driveJoystick.getY(), () -> -driveJoystick.getX(),
    driveJoystick::getTop, driveJoystick::getTrigger
  );

  private final ClimberManualCommand climberManualCommand = new ClimberManualCommand(
    climber,
    new InputNormalizer(() -> -controller.getLeftY(), 0.1, -1.0, 1.0), // arm
    new InputNormalizer(() -> -controller.getRightY(), 0.1, -1.0, 1.0) // winch
  );

  private final Command autonSimpleRight = new SequentialCommandGroup(
    new IntakeOutCommand(intake).raceWith(new WaitCommand(0.5)),
    new ParallelCommandGroup(
      new SequentialCommandGroup(
        new DriveAutoCommand(driveTrain, -4, 0),
        new DriveAutoCommand(driveTrain, 0, -80)
      ),
      new IntakeArmAutoCommand(intakeArm, IntakeArmPosition.DOWN)
    ),
    new DriveAutoCommand(driveTrain, 10, 0)
  );

  private final Command autonComplexRight = new SequentialCommandGroup(
    // new InstantCommand(intakeArm::enable, intake),
    new ParallelCommandGroup(
      new IntakeArmAutoCommand(intakeArm, IntakeArmPosition.DOWN),
      new DriveAutoCommand(driveTrain, 0, 40) // positive = clockwise
    ),
    new ParallelRaceGroup(
      new IntakeInCommand(intake),
      new DriveAutoCommand(driveTrain, 4.1, -5)
    ),
    new ParallelCommandGroup(
      new IntakeArmAutoCommand(intakeArm, IntakeArmPosition.UP),
      new DriveAutoCommand(driveTrain, 0, 140)
    ),
    new DriveAutoCommand(driveTrain, 6, 0),
    new IntakeOutCommand(intake).raceWith(new WaitCommand(0.5)),
    new ParallelCommandGroup(
      new SequentialCommandGroup(
        new DriveAutoCommand(driveTrain, -4, 0),
        new DriveAutoCommand(driveTrain, 0, -80)
      ),
      new IntakeArmAutoCommand(intakeArm, IntakeArmPosition.DOWN)
    ),
    new DriveAutoCommand(driveTrain, 10, 0)
  );

  private final SendableChooser<Command> autonChooser = new SendableChooser<>();

  private final InputNormalizer intakeArmInput = new InputNormalizer(
    () -> controller.getLeftTriggerAxis() - controller.getRightTriggerAxis(), 0.02, -0.5, 0.5);
  private final ArmManualCommand debugIntakeArmCommand = new ArmManualCommand(intakeArm, intakeArmInput);
  
  public RobotContainer() {
    autonChooser.addOption("autonSimpleRight", autonSimpleRight);
    autonChooser.addOption("autonComplexRight", autonComplexRight);
    SmartDashboard.putData("auton", autonChooser);

    configureButtonBindings();

    driveTrain.setDefaultCommand(driveManualCommand);
    intakeArm.setDefaultCommand(debugIntakeArmCommand);
    climber.setDefaultCommand(climberManualCommand);
  
    intakeArm.zeroEncoder(); // TODO: ?
  }

  public void teleopInit() {
    // TODO: drive brake mode disabled
  }

  private void configureButtonBindings() {
    controller.b_LeftBumper()
      .whileActiveOnce(new IntakeInCommand(intake));
    controller.b_RightBumper()
      .whileActiveOnce(new IntakeOutCommand(intake));
    // controller.b_A()
    //   .whenPressed(new InstantCommand(intakeArm::enable, intake));
    // controller.b_B()
    //   .whenPressed(new InstantCommand(intakeArm::disable, intake));
    controller.b_X()
      .whenPressed(new IntakeArmAutoCommand(intakeArm, IntakeArmPosition.DOWN));
    controller.b_Y()
      .whenPressed(new IntakeArmAutoCommand(intakeArm, IntakeArmPosition.UP));
    controller.b_Start()
      .whenPressed(new InstantCommand(intakeArm::zeroEncoder, intakeArm));
    // controller.b_Back()
    //   .whenPressed(autonComplexRight);
  }

  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}
