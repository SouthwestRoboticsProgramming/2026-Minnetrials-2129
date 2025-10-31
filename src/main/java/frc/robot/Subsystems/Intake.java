package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private final TalonFX intake;
    public Intake() {
        intake = new TalonFX(5);
        TalonFXConfiguration intakeConfig = new TalonFXConfiguration();
        intakeConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        intake.getConfigurator().apply(intakeConfig);
    }
    public Command idle() {
        return this.run(() -> {
            Intake.setControl(new NeutralOut());
        });
    }
    public Command run() {
        return this.run(() -> {
            Intake.setControl(new VoltageOut(2.0));
        });
    }

}
