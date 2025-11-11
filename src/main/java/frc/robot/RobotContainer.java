// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.net.NTDouble;
import frc.lib.net.NTEntry;
import frc.robot.subsystems.ButterArm;
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
public class RobotContainer {

  private final NTEntry<Double> TRIGGER_THRESHOLD = new NTDouble("Trigger Threshold", 0.5).setPersistent();

  private final CommandXboxController driverController;
  private final CommandXboxController operatorController;
  private final Drivebase drivebase; 
  private final Shooter shooter;
  private final Intake intake;
  private final ButterArm butterArm;

  public RobotContainer() {
    drivebase = new Drivebase();
    driverController = new CommandXboxController(0);
    operatorController = new CommandXboxController(1);
    configureBindings();
    shooter = new Shooter();
    intake = new Intake();
    butterArm = new ButterArm();
  }  

  private void configureBindings() {
    drivebase.setDefaultCommand(drivebase.arcadeDrive(
      () -> MathUtil.applyDeadband(-driverController.getLeftY(), 0.1),
      () -> MathUtil.applyDeadband(driverController.getRightX(), 0.1)));
     // Put the shooter flywheel in idle by default to save battery power.
    shooter.setDefaultCommand(shooter.idle());
    intake.setDefaultCommand(intake.idle());
    butterArm.setDefaultCommand(butterArm.idle());
    
    new Trigger(() -> driverController.getLeftTriggerAxis() > TRIGGER_THRESHOLD.get()).whileTrue(shooter.spinFlywheel());

    // Bind the ball intake to the right trigger on the operator controller.
    
    
    new Trigger(()-> driverController.getRightTriggerAxis() > TRIGGER_THRESHOLD.get()).whileTrue(intake.intake());


    /*
    Left trigger - outtake butter
    Right trigger - intake butter
    
    */

    // Ball outake

    new Trigger(()-> operatorController.getLeftTriggerAxis() > TRIGGER_THRESHOLD.get()).whileTrue(intake.outake());
    operatorController.y().onTrue(butterArm.up());
    // Butter intake
    new Trigger(()-> operatorController.getRightTriggerAxis() > TRIGGER_THRESHOLD.get()).whileTrue(intake.butter());
   
     
  }   

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
