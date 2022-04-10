package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;

public class Launcher extends SubsystemBase {
  private final WPI_TalonFX shooterF = new WPI_TalonFX(CANMapping.TALONFX_LAUNCHER_SHOOTER_F);
  private final WPI_TalonFX shooterB = new WPI_TalonFX(CANMapping.TALONFX_LAUNCHER_SHOOTER_B);

  private double targetVelocity = 0;

  private final Orchestra orchestra = new Orchestra();
  private String song = null;

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


    orchestra.addInstrument(shooterF);
    orchestra.addInstrument(shooterB);
    // orchestra.loadMusic("mega.chrp");
    // orchestra.loadMusic("jeopardy.chrp");
    // orchestra.loadMusic("sus.chrp");
    // orchestra.loadMusic("ttfaf.chrp");
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("F velocity", shooterF::getSelectedSensorVelocity, x -> {});
    builder.addDoubleProperty("B velocity", shooterB::getSelectedSensorVelocity, x -> {});
    builder.addDoubleProperty("F amps", shooterF::getStatorCurrent, x -> {});
    builder.addDoubleProperty("B amps", shooterB::getStatorCurrent, x -> {});
    builder.addBooleanProperty("atVelocity", this::atVelocity, x -> {});
  }

  public void runShooter(double rpm) { // TODO: other distances
    // v(x) = x rev/min * 1/600 min/decisec * 2048 cycle/rev = 256*x/75 cycle/decisec
    double v = 256 * rpm / 75;
    // shooterF setpoint must be inverted due to mirroring
    shooterF.set(TalonFXControlMode.Velocity, -v);
    shooterB.set(TalonFXControlMode.Velocity, v);
    targetVelocity = v;
  }

  public boolean atVelocity() {
    double vf = shooterF.getSelectedSensorVelocity();
    double vb = shooterB.getSelectedSensorVelocity();
    
    return Math.abs(vf + targetVelocity) <= 50 && Math.abs(vb - targetVelocity) <= 50;
  }

  public void stopShooter() {
    shooterF.set(TalonFXControlMode.PercentOutput, 0);
    shooterB.set(TalonFXControlMode.PercentOutput, 0);
    targetVelocity = 0;
  }

  public String getSong()  {
    return song;
  }

  public void setSong(String song) {
    this.song = song;
    orchestra.stop();
    orchestra.loadMusic(song);
  }
  
  public void play() {
    orchestra.play();
  }
}
