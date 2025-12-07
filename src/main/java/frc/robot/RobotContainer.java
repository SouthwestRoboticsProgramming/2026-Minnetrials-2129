// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
// Import statements{
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//Command-based library imports
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
// Network table imports
import frc.lib.LimelightHelpers;
import frc.lib.net.NTDouble;
import frc.lib.net.NTEntry;
import frc.robot.commands.Autonomous;
import frc.robot.logging.FieldView;
// Subsystem imports
import frc.robot.subsystems.Drivebase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ButterArm;
 
// This class is where the bulk of the robot should be declared. 
public class RobotContainer {
  // Network table entry for trigger threshold
  private final NTEntry<Double> TRIGGER_THRESHOLD = new NTDouble("Trigger Threshold", 0.5).setPersistent();
  // Define controllers and subsystems
  private final CommandXboxController driverController;
  private final CommandXboxController operatorController;
  public final Drivebase drivebase; 
  public final Shooter shooter;
  public final Intake intake;
  public final ButterArm armMotor;

  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  
  // Constructor
  public RobotContainer() {
    drivebase = new Drivebase();
    driverController = new CommandXboxController(0);
    operatorController = new CommandXboxController(1);
    shooter = new Shooter();
    intake = new Intake();
    armMotor = new frc.robot.subsystems.ButterArm();
    configureBindings();
    FieldView.publish();
    
    autoChooser.setDefaultOption("Drive forward", Autonomous.driveForwardAutoSequence(this));
    
    SmartDashboard.putData("Auto chooser", autoChooser);
    
    DriverStation.silenceJoystickConnectionWarning(true);
  }


  // Configure button bindings
  private void configureBindings() {
    // default commands
    drivebase.setDefaultCommand(drivebase.arcadeDrive(
      () -> MathUtil.applyDeadband(driverController.getLeftY(), 0.1),
      () -> MathUtil.applyDeadband(driverController.getRightX(), 0.1)));
    
    shooter.setDefaultCommand(shooter.idle());
    intake.setDefaultCommand(intake.idle());
    armMotor.setDefaultCommand(armMotor.setAngleCommand(0));

    // Popcorn outake
    new Trigger(() -> driverController.getLeftTriggerAxis() > TRIGGER_THRESHOLD.get()).whileTrue(shooter.spinFlywheel());
    // Auto aim
    driverController.x().whileTrue(drivebase.autoAim(() -> LimelightHelpers.getTX("limelight"), () -> MathUtil.applyDeadband(driverController.getRightX(), 0.1))); 
    new Trigger(()-> driverController.getRightTriggerAxis() > TRIGGER_THRESHOLD.get()).whileTrue(intake.intake());
    
    

    /*
    Left trigger - outtake butter
    Right trigger - intake butter
    */

    // Butter outake
    new Trigger(()-> operatorController.getLeftTriggerAxis() > TRIGGER_THRESHOLD.get()).whileTrue(intake.outakeButter());
        
    //butter arm up
    
    
    // Butter intake
    new Trigger(()-> operatorController.getRightTriggerAxis() > TRIGGER_THRESHOLD.get())
        // .whileTrue(intake.butter()
        // .until(() -> intake.hasButter()))
        // .andThen(intake.butterHold()
        // .alongWith(armMotor.score()));
        .onTrue(
        intake.butter()
          .alongWith(armMotor.setAngleCommand(300))
            .until(intake::hasButter) // This runs until the condition is true
            .andThen(intake.butterHold() // This runs immediately after butter() finishes
                .alongWith(armMotor.setAngleCommand(45)) // This runs in parallel with butterHold()
            )
        );
  }  
  

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
