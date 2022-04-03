package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;

public class Launcher extends SubsystemBase {
  private final WPI_TalonFX shooterF = new WPI_TalonFX(CANMapping.TALONFX_LAUNCHER_SHOOTER_F);
  private final WPI_TalonFX shooterB = new WPI_TalonFX(CANMapping.TALONFX_LAUNCHER_SHOOTER_B);

  private final Orchestra orchestra = new Orchestra();

  private static Launcher instance;
  public static Launcher getInstance() {
    if (instance == null)
      instance = new Launcher();
    return instance;
  }

  private Launcher() {
    shooterF.setInverted(true);
    // shooterB.follow(shooterF);
    // shooterB.setInverted(TalonFXInvertType.OpposeMaster);

    orchestra.addInstrument(shooterF);
    orchestra.addInstrument(shooterB);
    // orchestra.loadMusic("mega.chrp");
    orchestra.loadMusic("jeopardy.chrp");
  }

  @Override
  public void periodic() {
    // SmartDashboard...
    SmartDashboard.putNumber("shooterF v", shooterF.getSelectedSensorVelocity());
    SmartDashboard.putNumber("shooterB v", shooterB.getSelectedSensorVelocity());
  }

  public void runShooterShort() { // TODO: other distances
    shooterF.set(TalonFXControlMode.PercentOutput, 0.675);
    shooterB.set(TalonFXControlMode.PercentOutput, 0.675);

    // shooterF.set(TalonFXControlMode.Velocity, 1000); // TODO
    // double coef = 2000.0 * 2048.0 / 600.0;
    // shooterF.set(TalonFXControlMode.Velocity, 3400);
    // shooterB.set(TalonFXControlMode.Velocity, 3400);
  }

  public void runShooterLow() {
    shooterF.set(TalonFXControlMode.PercentOutput, 0.50);
    shooterB.set(TalonFXControlMode.PercentOutput, 0.50);
  }

  public void stopShooter() {
    shooterF.set(TalonFXControlMode.PercentOutput, 0);
    shooterB.set(TalonFXControlMode.PercentOutput, 0);
    // shooterF.set(TalonFXControlMode.Velocity, 0);
    // shooterB.set(TalonFXControlMode.Velocity, 0);
  }
  
  public void play() {
    orchestra.play();
  }
}
