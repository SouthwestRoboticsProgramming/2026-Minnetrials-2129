// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Subsystems.ButterArm;
import frc.robot.Subsystems.Drivebase;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.Shooter;
public class RobotContainer {
  public RobotContainer() {

    drivebase = new Drivebase();
    driverController = new CommandXboxController(0);
    operatorController = new CommandXboxController(1);
    configureBindings();
    shooter = new Shooter();
    intake = new Intake();
    intake2 = new Intake();
    butterArm = new ButterArm();
  }  
  private final CommandXboxController driverController;
  private final CommandXboxController operatorController;
  private final Drivebase drivebase; 
  private final Shooter shooter;
  private final Intake intake;
  private final Intake intake2;
  private final ButterArm butterArm;

  private void configureBindings() {
    drivebase.setDefaultCommand(drivebase.arcadeDrive(
      () -> MathUtil.applyDeadband(-driverController.getLeftY(), 0.1),
      () -> MathUtil.applyDeadband(driverController.getRightX(), 0.1)));
     // Put the shooter flywheel in idle by default to save battery power.
    shooter.setDefaultCommand(shooter.idle());
    // Bind the flywheels to the left trigger on the operator controller.
    // Use a Trigger to convert the analog input into a digital (boolean) one.
    new Trigger(() -> driverController.getLeftTriggerAxis() > 0.5)
      .whileTrue(shooter.spinFlywheel());
    intake.setDefaultCommand(intake.idle());
    // Bind the ball intake to the right trigger on the operator controller.
    intake2.setDefaultCommand(intake2.idle());
    butterArm.setDefaultCommand(butterArm.idle());
    new Trigger(()-> driverController.getRightTriggerAxis()>0.5)
     .whileTrue(intake.run());
    new Trigger(() -> operatorController.getLeftTriggerAxis()>0.5)
     .whileTrue(intake.outake());
    new Trigger(()-> operatorController.getRightTriggerAxis()>0.5)
     .whileTrue(intake.butter()); 
     .whileTrue(butterArm.run());
  }   

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
