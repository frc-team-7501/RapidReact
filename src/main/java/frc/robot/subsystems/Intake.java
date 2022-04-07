package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANMapping;
import frc.robot.Constants.DIOMapping;

public class Intake extends SubsystemBase {
  private final WPI_TalonFX   intakeMotorL  = new WPI_TalonFX(CANMapping.TALONFX_INTAKE_L);
  private final WPI_TalonFX   intakeMotorR  = new WPI_TalonFX(CANMapping.TALONFX_INTAKE_R);
  private final WPI_TalonSRX  agitatorMotor = new WPI_TalonSRX(CANMapping.TALONSRX_INTAKE_AGITATOR);
  private final WPI_TalonSRX  pivotMotorL   = new WPI_TalonSRX(CANMapping.TALONSRX_INTAKE_PIVOT_L);
  private final WPI_TalonSRX  pivotMotorR   = new WPI_TalonSRX(CANMapping.TALONSRX_INTAKE_PIVOT_R);
  private final Encoder       pivotEncoderL = new Encoder(DIOMapping.ENCODER_INTAKE_PIVOT_L[0], DIOMapping.ENCODER_INTAKE_PIVOT_L[1]);
  private final Encoder       pivotEncoderR = new Encoder(DIOMapping.ENCODER_INTAKE_PIVOT_R[0], DIOMapping.ENCODER_INTAKE_PIVOT_R[1]);

  // private final PIDController pidL = new PIDController(0.1, 0, 0.01);
  // private final PIDController pidR = new PIDController(0.1, 0, 0.01);

  private static final double SETPOINT_OUT_L = -660; // TODO
  private static final double SETPOINT_OUT_R = 630; // TODO
  private static final double SETPOINT_IN_L  = 0;
  private static final double SETPOINT_IN_R  = 0;

  double setpointL = 0;
  double setpointR = 0;

  private static Intake instance;
  public static Intake getInstance() {
    if (instance == null)
      instance = new Intake();
    return instance;
  }

  private Intake() {
    intakeMotorR.follow(intakeMotorL);
    intakeMotorR.setInverted(InvertType.OpposeMaster);

    // Configure pivotL

    pivotMotorL.configFactoryDefault(0);
    pivotMotorL.setNeutralMode(NeutralMode.Brake);

    // Configure pivotR

    pivotMotorR.configFactoryDefault(0);
    pivotMotorR.setNeutralMode(NeutralMode.Brake);

    // Configure pidL

    // pidL.setTolerance(0.1);

    // Configure pidR

    // pidR.setTolerance(0.1);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("pivot L", pivotEncoderL::getDistance, x -> {});
    builder.addDoubleProperty("pivot R", pivotEncoderR::getDistance, x -> {});
    builder.addDoubleProperty("setpoint L", () -> this.setpointL, x -> {});
    builder.addDoubleProperty("setpoint R", () -> this.setpointR, x -> {});
    builder.addBooleanProperty("isPivotIn", this::isPivotIn, x -> {});
    builder.addBooleanProperty("isPivotOut", this::isPivotOut, x -> {});
  }

  @Override
  public void periodic() {    
    double posL = pivotEncoderL.getDistance();
    double posR = pivotEncoderR.getDistance();

    double diffL = posL - setpointL;
    if (Math.abs(diffL) > 20) {
      pivotMotorL.set(Math.signum(diffL) * 1.0);
    } else {
      pivotMotorL.set(0);
    }

    double diffR = posR - setpointR;
    if (Math.abs(diffR) > 20) {
      pivotMotorR.set(Math.signum(diffR) * 1.0);
    } else {
      pivotMotorR.set(0);
    }

    // double outL = pidL.atSetpoint() ? 0 : pidL.calculate(posL);
    // double outR = pidR.atSetpoint() ? 0 : pidR.calculate(posR);

    // pivotMotorL.set(outL);
    // pivotMotorR.set(outR);
  }

  public void pivotIn() {
    // pidL.setSetpoint(SETPOINT_IN_L);
    // pidR.setSetpoint(SETPOINT_IN_R);
    setpointL = SETPOINT_IN_L;
    setpointR = SETPOINT_IN_R;
  }

  public boolean isPivotIn() {
    return  Math.abs(pivotEncoderL.getDistance() - SETPOINT_IN_L) < 20
      &&    Math.abs(pivotEncoderR.getDistance() - SETPOINT_IN_R) < 20;
  }

  public void pivotOut() {
    // pidL.setSetpoint(SETPOINT_OUT_L);
    // pidR.setSetpoint(SETPOINT_OUT_R);
    setpointL = SETPOINT_OUT_L;
    setpointR = SETPOINT_OUT_R;
  }

  public boolean isPivotOut() {
    return  Math.abs(pivotEncoderL.getDistance() - SETPOINT_OUT_L) < 20
      &&    Math.abs(pivotEncoderR.getDistance() - SETPOINT_OUT_R) < 20;
  }

  public void intake() {
    intakeMotorL.set(TalonFXControlMode.PercentOutput, 0.4);
    agitatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
  }

  public void eject() {
    intakeMotorL.set(TalonFXControlMode.PercentOutput, -0.6);
    agitatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
  }

  public void stopIntake() {
    intakeMotorL.set(TalonFXControlMode.PercentOutput, 0);
    agitatorMotor.set(TalonSRXControlMode.PercentOutput, 0);
  }

  public void resetEncoders() {
    pivotEncoderL.reset();
    pivotEncoderR.reset();
  }
}
