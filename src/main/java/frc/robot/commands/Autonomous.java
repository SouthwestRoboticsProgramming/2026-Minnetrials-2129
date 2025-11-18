package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.RobotContainer;

public final class Autonomous {
    public static Command setIntake(RobotContainer robot, boolean state) {
        return Commands.runOnce(() -> robot.intake.setIntake(state), robot.intake);
    }
    public static Command setButterArm(RobotContainer robot, boolean state) {
        return Commands.runOnce(() -> robot.butterArm.setButterArm(state), robot.butterArm);
    }
    public static Command setShooter(RobotContainer robot, boolean state) {
        return Commands.runOnce(() -> robot.shooter.setShooter(state), robot.shooter);
    }
    public static Command waitSeconds(double seconds) {
        return Commands.waitSeconds(seconds);
    }
    public static Command stopAll(RobotContainer robot) {
        return Commands.parallel(
            setIntake(robot, false),
            setButterArm(robot, false),
            setShooter(robot, false)
            
        );
    }
    
   
    public static Command createAutoSequence(RobotContainer robot) {
        return Commands.sequence();
    }    
}                
