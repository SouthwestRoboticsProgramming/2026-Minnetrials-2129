package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.google.flatbuffers.Constants;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ButterArm extends SubsystemBase {
    private final TalonFX butterArm;
    public ButterArm() {
        butterArm = new TalonFX(7);
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.Slot0.kP = Constants.kbutterArmKP.get();
        config.Slot0.kD = Constants.kbutterArmKD.get();
         // These are determined by the gears in the gearbox and the sprocket on the chain
        config.Feedback.SensorToMechanismRatio = (8.0 / 72.0);
        
        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        butterArm.getConfigurator().apply(config);

        

        // Tell motor controller where the arm is initially
        butterArm.setPosition(0.0);

      
        
    }
    public Command idle() {
        return this.run(() -> {
            butterArm.setPosition(0.0);
           
        });
    }
    public Command run() {
        return this.run(() -> {
            butterArm.setPosition(50.0);
           
        });
    }
  
    
    
    
}
