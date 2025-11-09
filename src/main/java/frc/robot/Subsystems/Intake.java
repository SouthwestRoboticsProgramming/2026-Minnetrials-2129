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
    private final TalonFX intake;
    private final TalonFX intake2;
    private final TalonFX intake3;
    public Intake() {
        intake = new TalonFX(5);
        intake2 = new TalonFX(6);
        intake3 = new TalonFX(7);
        TalonFXConfiguration intakeConfig = new TalonFXConfiguration();
        intakeConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        intake.getConfigurator().apply(intakeConfig);
        intake2.setInverted(true);
        TalonFXConfiguration intake2Config = new TalonFXConfiguration();
        intake2Config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        intake2.setControl(new Follower(5,true));
        TalonFXConfiguration intake3Config = new TalonFXConfiguration();
        intake3Config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        intake3.getConfigurator().apply(intake3Config);
        
    } 
    public Command idle() {
        return this.run(() -> {
            intake.setControl(new NeutralOut());
            intake2.setControl(new NeutralOut());
            intake3.setControl(new NeutralOut());
        });
    }
    public Command run() {
        return this.run(() -> {
            intake.setControl(new VoltageOut(4.0));
            intake2.setControl(new VoltageOut(4.0));
            intake3.setControl(new NeutralOut());
        });
    }
    public Command outake() {
        return this.outake(() -> {
            intake.setControl(new VoltageOut(-4.0));
            intake2.setControl(new VoltageOut(4.0));
            intake3.setControl(new VoltageOut(3.0));

        });
    }
    public Command butter() {
        return this.butter(() -> {
            intake.setControl(new VoltageOut(4.0));
            intake2.setControl(new VoltageOut(-4.0));
        intake3.setControl(new NeutralOut());
        });
    }
}
