package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeArm.IntakeArmPosition;

public class ArmAutoCommand extends CommandBase {
    private final IntakeArm intakeArm;
    private final IntakeArmPosition position;
    
    public ArmAutoCommand(final IntakeArm intakeArm, final IntakeArmPosition position) {
        this.intakeArm = intakeArm;
        this.position = position;

        addRequirements(intakeArm);
    }

    @Override
    public void initialize() {
        intakeArm.enable();
        intakeArm.setWinchSetpoint(position);
    }

    @Override
    public boolean isFinished() {
        return intakeArm.getController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        intakeArm.disable();
        super.end(interrupted);
    }
}
