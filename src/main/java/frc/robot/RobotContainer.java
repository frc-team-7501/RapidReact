package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ControllerMapping;
import frc.robot.commands.DriveManualCommand;
import frc.robot.subsystems.DriveTrain;
// import frc.robot.utils.ExtendedXboxController;

public class RobotContainer {
  private final Joystick driveJoystick = new Joystick(ControllerMapping.JOYSTICK);
  // private final ExtendedXboxController controller = new ExtendedXboxController(ControllerMapping.XBOX);


  private final DriveTrain driveTrain = new DriveTrain();


  private final DriveManualCommand driveManualCommand = new DriveManualCommand(
    driveTrain,
    () -> -driveJoystick.getY(), () -> -driveJoystick.getX(),
    driveJoystick::getTop, driveJoystick::getTrigger
  );

  private final SendableChooser<Command> autonChooser = new SendableChooser<>();
  
  public RobotContainer() {
    SmartDashboard.putData("auton", autonChooser);

    configureButtonBindings();

    driveTrain.setDefaultCommand(driveManualCommand);
  }

  public void teleopInit() {
    // TODO: drive brake mode disabled
  }

  private void configureButtonBindings() {
    // controller.b_Back()
    //   .whenPressed(autonComplexRight);
  }

  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}
