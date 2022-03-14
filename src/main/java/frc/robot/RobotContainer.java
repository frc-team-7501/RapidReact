package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ControllerMapping;
import frc.robot.commands.ArmManualCommand;
import frc.robot.commands.DriveAutoCommand;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.ArmAutoCommand;
import frc.robot.commands.IntakeRunCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakePosition;
import frc.robot.utils.InputNormalizer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final Joystick driveJoystick = new Joystick(ControllerMapping.JOYSTICK);
  private final XboxController controller = new XboxController(ControllerMapping.XBOX);

  private final DriveTrain driveTrain = new DriveTrain();
  private final Intake intake = new Intake();

  private final DriveManualCommand driveManualCommand = new DriveManualCommand(
    driveTrain,
    () -> -driveJoystick.getY(), () -> -driveJoystick.getX(),
    driveJoystick::getTop, driveJoystick::getTrigger
  );

  private final Command autonSimpleRight = new SequentialCommandGroup(
    new IntakeRunCommand(intake, 1.0).raceWith(new WaitCommand(0.5)),
    new ParallelCommandGroup(
      new SequentialCommandGroup(
        new DriveAutoCommand(driveTrain, -4, 0),
        new DriveAutoCommand(driveTrain, 0, -80)
      ),
      new ArmAutoCommand(intake, IntakePosition.DOWN)
    ),
    new DriveAutoCommand(driveTrain, 10, 0)
  );

  private final Command autonComplexRight = new SequentialCommandGroup(
    new InstantCommand(intake::enable, intake),
    new ParallelCommandGroup(
      new ArmAutoCommand(intake, IntakePosition.DOWN),
      new DriveAutoCommand(driveTrain, 0, 40) // positive = clockwise
    ),
    new ParallelRaceGroup(
      new IntakeRunCommand(intake, -0.5),
      new DriveAutoCommand(driveTrain, 4, -5)
    ),
    new ParallelCommandGroup(
      new ArmAutoCommand(intake, IntakePosition.UP),
      new DriveAutoCommand(driveTrain, 0, 140)
    ),
    new DriveAutoCommand(driveTrain, 6, 0),
    new IntakeRunCommand(intake, 1.0).raceWith(new WaitCommand(0.5)),
    new ParallelCommandGroup(
      new SequentialCommandGroup(
        new DriveAutoCommand(driveTrain, -4, 0),
        new DriveAutoCommand(driveTrain, 0, -80)
      ),
      new ArmAutoCommand(intake, IntakePosition.DOWN)
    ),
    new DriveAutoCommand(driveTrain, 10, 0)
  );

  private final SendableChooser<Command> autonChooser = new SendableChooser<>();

  private final InputNormalizer controllerLeftY = new InputNormalizer(controller::getLeftY, 0.02, -0.5, 0.5);
  private final ArmManualCommand debugIntakeArmCommand = new ArmManualCommand(intake, controllerLeftY);

  private final JoystickButton controllerButtonLB = new JoystickButton(controller, Button.kLeftBumper.value);
  private final JoystickButton controllerButtonRB = new JoystickButton(controller, Button.kRightBumper.value);

  private final JoystickButton controllerButtonA = new JoystickButton(controller, Button.kA.value);
  private final JoystickButton controllerButtonB = new JoystickButton(controller, Button.kB.value);
  private final JoystickButton controllerButtonX = new JoystickButton(controller, Button.kX.value);
  private final JoystickButton controllerButtonY = new JoystickButton(controller, Button.kY.value);
  private final JoystickButton controllerButtonStart = new JoystickButton(controller, Button.kStart.value);
  private final JoystickButton controllerButtonBack = new JoystickButton(controller, Button.kBack.value);
  
  public RobotContainer() {
    autonChooser.addOption("autonSimpleRight", autonSimpleRight);
    autonChooser.addOption("autonComplexRight", autonComplexRight);
    SmartDashboard.putData("auton", autonChooser);

    configureButtonBindings();

    driveTrain.setDefaultCommand(driveManualCommand);
    intake.setDefaultCommand(debugIntakeArmCommand);
  
    intake.setWinchEncoderZero();
  }

  public void teleopInit() {
    // TODO: drive brake mode disabled
  }

  private void configureButtonBindings() {
    controllerButtonLB // fire
      .whileActiveOnce(new IntakeRunCommand(intake, 0.4));
    controllerButtonRB // intake
      .whileActiveOnce(new IntakeRunCommand(intake, -0.5));
    controllerButtonA.whenPressed(
      new InstantCommand(intake::enable, intake)
    );
    controllerButtonB.whenPressed(
      new InstantCommand(intake::disable, intake)
    );
    controllerButtonX.whenPressed(
      new ArmAutoCommand(intake, IntakePosition.DOWN)
    );
    controllerButtonY.whenPressed(
      new ArmAutoCommand(intake, IntakePosition.UP)
    );
    controllerButtonStart.whenPressed(
      new InstantCommand(intake::setWinchEncoderZero)
    );
    controllerButtonBack.whenPressed(
      autonComplexRight
    );

  }

  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}
