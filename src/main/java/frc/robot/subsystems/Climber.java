package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;

public class Climber extends SubsystemBase {
  private final CANSparkMax armL = new CANSparkMax(CANMapping.SPARKMAX_CLIMBER_ARM_L, MotorType.kBrushless);
  private final CANSparkMax armR = new CANSparkMax(CANMapping.SPARKMAX_CLIMBER_ARM_R, MotorType.kBrushless);
  
  private final CANSparkMax winchL = new CANSparkMax(CANMapping.SPARKMAX_CLIMBER_WINCH_L, MotorType.kBrushless);
  private final CANSparkMax winchR = new CANSparkMax(CANMapping.SPARKMAX_CLIMBER_WINCH_R, MotorType.kBrushless);
  
  public Climber() {
  }

  public double getArmLPosition() {
    return armL.getEncoder().getPosition();
  }

  public double getArmRPosition() {
    return armR.getEncoder().getPosition();
  }

  public double getWinchLPosition() {
    return winchL.getEncoder().getPosition();
  }

  public double getWinchRPosition() {
    return winchR.getEncoder().getPosition();
  }

  public void setArmL(double speed) {
    armL.set(speed);
  }

  public void setArmR(double speed) {
    armR.set(speed);
  }

  public void setWinchL(double speed) {
    winchL.set(speed);
  }

  public void setWinchR(double speed) {
    winchR.set(speed);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("armL", getArmLPosition());
    SmartDashboard.putNumber("armR", getArmRPosition());
    SmartDashboard.putNumber("winchL", getWinchLPosition());
    SmartDashboard.putNumber("winchR", getWinchRPosition());
  }
}
