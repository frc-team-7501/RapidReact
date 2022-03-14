package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakePosition;

public class ArmAutoCommand extends CommandBase {
    private final Intake intake;
    private final IntakePosition position;
    
    public ArmAutoCommand(final Intake intake, final IntakePosition position) {
        this.intake = intake;
        this.position = position;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.enable();
        intake.setWinchSetpoint(position);
    }

    @Override
    public boolean isFinished() {
        return intake.getController().atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        intake.disable();
        super.end(interrupted);
    }
}
