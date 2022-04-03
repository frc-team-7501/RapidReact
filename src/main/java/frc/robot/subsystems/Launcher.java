package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
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
    final int timeout = 0; // 30
    final double  p = 0.1,
                  i = 0.001,
                  d = 0,
                  f = 0;



    shooterF.configFactoryDefault();
    shooterF.configNeutralDeadband(0.001);
    shooterF.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, timeout);

    shooterF.configNominalOutputForward(0, timeout);
    shooterF.configNominalOutputReverse(0, timeout);
    shooterF.configPeakOutputForward(1, timeout);
    shooterF.configPeakOutputReverse(-1, timeout);

    shooterF.config_kP(0, p, timeout);
    shooterF.config_kI(0, i, timeout);
    shooterF.config_kD(0, d, timeout);
    shooterF.config_kF(0, f, timeout);



    shooterB.configFactoryDefault();
    shooterB.configNeutralDeadband(0.001);
    shooterB.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, timeout);

    shooterB.configNominalOutputForward(0, timeout);
    shooterB.configNominalOutputReverse(0, timeout);
    shooterB.configPeakOutputForward(1, timeout);
    shooterB.configPeakOutputReverse(-1, timeout);

    shooterB.config_kP(0, p, timeout);
    shooterB.config_kI(0, i, timeout);
    shooterB.config_kD(0, d, timeout);
    shooterB.config_kF(0, f, timeout);


    // orchestra.addInstrument(shooterF);
    // orchestra.addInstrument(shooterB);
    // orchestra.loadMusic("mega.chrp");
    // orchestra.loadMusic("jeopardy.chrp");
    // orchestra.loadMusic("sus.chrp");
    // orchestra.loadMusic("ttfaf.chrp");
  }

  @Override
  public void periodic() {
    // SmartDashboard...
    SmartDashboard.putNumber("shooterF v", shooterF.getSelectedSensorVelocity());
    SmartDashboard.putNumber("shooterB v", shooterB.getSelectedSensorVelocity());
  }

  public void runShooterShort() { // TODO: other distances
    // shooterF.set(TalonFXControlMode.PercentOutput, .55);
    // shooterB.set(TalonFXControlMode.PercentOutput, .55);

    // shooterF.set(TalonFXControlMode.Velocity, 1000); // TODO
    // double coef = 2000.0 * 2048.0 / 600.0;
    final double l_plus_ratio = 2500;

    shooterF.set(TalonFXControlMode.Velocity, -l_plus_ratio * (2048 / 600));
    shooterB.set(TalonFXControlMode.Velocity, l_plus_ratio * (2048 / 600));
    // shooterB.set(TalonFXControlMode.Velocity, 3400);
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
