package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;

public class Autonomous {
    /**
     * Creates an autonomous command sequence: Drive Forward, Turn, then Shoot.
     *
     * @param robot The RobotContainer holding all subsystems and commands.
     * @return The complete autonomous command sequence.
     */
    public static Command driveForwardTurnShootAuto(RobotContainer robot) {
        // --- 1. Drive Forward Command ---
        // Drives forward at a speed of -2.0 for 0.5 seconds.
        // Assumes negative speed drives the robot forward based on your original snippet.
        Command driveForward = robot.drivebase
            // Instant command to set drive power (assuming it doesn't end on its own)
            // Use withTimeout to make it stop after a duration.
            .arcadeDrive(() -> -4.0, () -> 0.0)
            .withTimeout(0.5);

        // --- 2. Turn Command ---
        // Turns in place (e.g., to the right) at a rotation speed of -1.0 for 0.5 seconds.
        Command turn = robot.drivebase
            .arcadeDrive(() -> 0.0, () -> 0.0)
            .withTimeout(0.5);

        // --- 3. Stop Command (Crucial after movement) ---
        // A simple command to ensure the drivebase stops moving before the next action.
        Command stopDrive = robot.drivebase
            .arcadeDrive(() -> 0.0, () -> 0.0)
            .withTimeout(0.01); // Just an instant command to set power to 0

        // --- 4. Shoot Command ---
        // Assumes you have a Shooter subsystem and a command to fire it, e.g., 'ShootCommand'.
        // NOTE: You must implement your actual shoot command.
        Command outake = Commands.print("Starting Shooter sequence...")
            .andThen(robot.shooter.spinFlywheel()) // Placeholder for your actual ShootCommand
            .withTimeout(2.0); // Allow 2 seconds for the shooting process

        // --- Sequence Assembly ---
        // Chaining the commands in order: Drive -> Stop -> Turn -> Stop -> Shoot
        return Commands.sequence(
            driveForward,
            stopDrive,
            turn,
            stopDrive, // Stop again after the turn
            outake
        );
    }
}
