package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;

public class Autonomous {
    public static Command driveForwardAutoSequence(RobotContainer robot) {
        return Commands.sequence(
            robot.drivebase.arcadeDrive(() -> 0.5, () -> 0.0).withTimeout(.5)
        );
    }
}
