package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.net.NTDouble;
import frc.lib.net.NTEntry;

public class Intake extends SubsystemBase {
    private final NTEntry<Double> INTAKE_VOLTAGE = new NTDouble("Intake/Intake Voltage", 4.0).setPersistent();
    private final NTEntry<Double> OUTAKE_VOLTAGE = new NTDouble("Intake/Outake Voltage", -8.0).setPersistent();
    private final NTEntry<Double> BUTTER_VOLTAGE = new NTDouble("Intake/Butter Voltage", 6.0).setPersistent();
    private final NTEntry<Double> OUTAKE_BOTTOM_VOLTAGE = new NTDouble("Intake/Outake Bottom Voltage", 8.0).setPersistent();
    private final NTEntry<Double> BUTTER_BOTTOM_VOLTAGE = new NTDouble("Intake/Butter Bottom Voltage", -7.0).setPersistent();
    private final NTEntry<Double> HOLD_BUTTER_PERCENT = new NTDouble("Intake/Hold Butter Percent (0-100%)", 20).setPersistent();
    private final NTEntry<Double> BUTTER_DETECT_CURRENT_THRESHOLD = new NTDouble("Intake/Butter Detect Current Threshold (Amps)", 15.0).setPersistent();
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
            intakeTop.setControl(new NeutralOut());
            intakeBottom.setControl(new NeutralOut());
           
        });
    }
    // Moves intake to run position
    public Command intake() {
        return this.run(() -> {
            intakeTop.setControl(new VoltageOut(INTAKE_VOLTAGE.get()));
            intakeBottom.setControl(new VoltageOut(INTAKE_VOLTAGE.get()));
            
        }).withName("Intaking Popcorn");
    }

    // Shoots the butter out
    public Command outakeButter() {
        return this.run(() -> {
            intakeTop.setControl(new VoltageOut(OUTAKE_VOLTAGE.get()));
            intakeBottom.setControl(new VoltageOut(OUTAKE_BOTTOM_VOLTAGE.get()));
        }).withName("Shooting out the butter");
    }
    // Spins the wheels to intake the butter
    public Command butter() {
        return this.run(() -> {
            intakeTop.setControl(new VoltageOut(BUTTER_VOLTAGE.get()));
            intakeBottom.setControl(new VoltageOut(BUTTER_BOTTOM_VOLTAGE.get()));
        }).withName("Intaking the butter");
    }

    // Holds the butter gently
    public Command butterHold() {
        return this.run(() -> {
            intakeTop.setControl(new VoltageOut(BUTTER_VOLTAGE.get() * HOLD_BUTTER_PERCENT.get() / 100));
            intakeBottom.setControl(new VoltageOut(BUTTER_BOTTOM_VOLTAGE.get() * HOLD_BUTTER_PERCENT.get() / 100));
        
        }).withName("I am Carresing the butter softly");
    }
    
    public boolean hasButter() {
        // Placeholder for sensor logic to detect butter presence
        return intakeTop.getStatorCurrent().getValueAsDouble() > BUTTER_DETECT_CURRENT_THRESHOLD.get();
    }
}
