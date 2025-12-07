package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.net.NTDouble;
import frc.lib.net.NTEntry;

public class Intake extends SubsystemBase {
    
    private final NTEntry<Double> BUTTER_DETECT_CURRENT_THRESHOLD = new NTDouble("Intake/Butter Detect Current Threshold (Amps)", 30.0).setPersistent();
    // Defines motors
    private final TalonFX intake;
  
    public Intake() {
        intake = new TalonFX(5);
        
        
        //top motor configuration
        TalonFXConfiguration intakeConfig = new TalonFXConfiguration();
        intakeConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        intake.getConfigurator().apply(intakeConfig);
        // Bottom motor is inverted + configurated to follow top motor
        intakeConfig.MotorOutput.Inverted = com.ctre.phoenix6.signals.InvertedValue.Clockwise_Positive;
        
      
        
    } 
    // @Override
    // public void periodic() {
    //   if (getCurrentCommand() == null) {
    //     System.out.println("No current command");
    //   } else {
    //     System.out.println(getCurrentCommand().getName());
    //   } 
    // }
    // Sets intake to idle position
    public Command idle() {
        return this.run(() -> {
            intake.setControl(new NeutralOut());
            
           
        });
    }
    // Moves intake to run position
    public Command intake() {
        return this.run(() -> {
            intake.setControl(new VoltageOut(-4.5));
        
            
        }).withName("Intaking Popcorn");
    }

    // Shoots the butter out
    public Command outakeButter() {
        return this.run(() -> {
            intake.setControl(new VoltageOut(6));
            
        }).withName("Shooting out the butter");
    }
    // Spins the wheels to intake the butter
    public Command butter() {
        return this.run(() -> {
            intake.setControl(new VoltageOut(-3));
            
        }).withName("Intaking the butter");
    }

    // Holds the butter gently
    public Command butterHold() {
        return this.run(() -> {
            intake.setControl(new VoltageOut(-6 * 20 / 100));
        
        }).withName("I am Carresing the butter softly");
    }
    
    public boolean hasButter() {
        // Placeholder for sensor logic to detect butter presence
        return intake.getStatorCurrent().getValueAsDouble() > BUTTER_DETECT_CURRENT_THRESHOLD.get();
    }

    
    

}
