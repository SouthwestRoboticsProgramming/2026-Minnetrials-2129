package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    // Defines motors
    private final TalonFX intakeTop;
    private final TalonFX intakeBottom;
  
    public Intake() {
        intakeTop = new TalonFX(5);
        intakeBottom = new TalonFX(6);
        
        //top motor configuration
        TalonFXConfiguration intakeConfig = new TalonFXConfiguration();
        intakeConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        intakeTop.getConfigurator().apply(intakeConfig);
        // Bottom motor is inverted + configurated to follow top motor
        intakeConfig.MotorOutput.Inverted = com.ctre.phoenix6.signals.InvertedValue.Clockwise_Positive;
        intakeBottom.setControl(new Follower(5,true));
        intakeBottom.getConfigurator().apply(intakeConfig); 
      
        
    } 
    // Sets intake to idle position
    public Command idle() {
        return this.run(() -> {
            intakeTop.setControl(new NeutralOut());
            intakeBottom.setControl(new NeutralOut());
           
        });
    }
    // Moves intake to run position
    public Command intake() {
        return this.run(() -> {
            intakeTop.setControl(new VoltageOut(4.0));
            intakeBottom.setControl(new VoltageOut(4.0));
            
        });
    }
    // Moves intake to outake position
    public Command outake() {
        return this.run(() -> {
            intakeTop.setControl(new VoltageOut(-8.0));
            intakeBottom.setControl(new VoltageOut(8.0));
           

        });
    }
    // Moves intake to butter position
    public Command butter() {
        return this.run(() -> {
            intakeTop.setControl(new VoltageOut(4.0));
            intakeBottom.setControl(new VoltageOut(-4.0));
        
        });
    }
}
