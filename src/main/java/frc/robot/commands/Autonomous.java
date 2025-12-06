package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;

public class Autonomous {
    public static Command driveForwardAutoSequence(RobotContainer robot) {
        return Commands.sequence(
            robot.drivebase.arcadeDrive(() -> 2.0, () -> 0.0).withTimeout(.5)

        );
    }
    public static Command Attempt1Sequence(RobotContainer robot) {
        return Commands.sequence(
            robot.drivebase.arcadeDrive(() -> 2.0, () -> 0.0).withTimeout(.5),
            robot.drivebase.arcadeDrive(() -> 0.0, () -> 2.0).withTimeout(.5),
            robot.shooter.spinFlywheel().withTimeout(2.0),
            robot.shooter.idle(),
            robot.drivebase.arcadeDrive(() -> -2.0, () -> 0.0).withTimeout(.5)
        );
    }
}
