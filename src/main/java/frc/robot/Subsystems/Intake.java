package frc.robot.Subsystems;

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
    private final TalonFX intake;
    private final TalonFX intake2;
  
    public Intake() {
        intake = new TalonFX(5);
        intake2 = new TalonFX(6);
        
        //intake motor configuration
        TalonFXConfiguration intakeConfig = new TalonFXConfiguration();
        intakeConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        intake.getConfigurator().apply(intakeConfig);
        //intake2 motor configuration
        intake2.setInverted(true);
        TalonFXConfiguration intake2Config = new TalonFXConfiguration();
        intake2Config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        intake2.setControl(new Follower(5,true));
        intake2.getConfigurator().apply(intake2Config);
      
        
    } 
    public Command idle() {
        return this.run(() -> {
            intake.setControl(new NeutralOut());
            intake2.setControl(new NeutralOut());
           
        });
    }
    public Command run() {
        return this.run(() -> {
            intake.setControl(new VoltageOut(4.0));
            intake2.setControl(new VoltageOut(4.0));
            
        });
    }
    public Command outake() {
        return this.run(() -> {
            intake.setControl(new VoltageOut(-8.0));
            intake2.setControl(new VoltageOut(8.0));
           

        });
    }
    public Command butter() {
        return this.run(() -> {
            intake.setControl(new VoltageOut(4.0));
            intake2.setControl(new VoltageOut(-4.0));
        
        });
    }
}
