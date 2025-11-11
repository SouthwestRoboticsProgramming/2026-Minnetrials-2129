package frc.robot.subsystems;

//import com.ctre.phoenix6.configs.Slot0Configs; need to add
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
//import com.ctre.phoenix6.signals.GravityTypeValue; need to add
import com.ctre.phoenix6.signals.InvertedValue;
//import com.google.flatbuffers.Constants; need to add

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class ButterArm extends SubsystemBase {
    // Defines motors
    private final TalonFX butterArm;
    private final PositionVoltage control;
    // Sets up motor configuration
    public ButterArm() {
        butterArm = new TalonFX(7);
        TalonFXConfiguration config = new TalonFXConfiguration();
        // Set neutral mode to brake
        //config.Slot0.kP = Constants.kbutterArmkP.get(); need to add
        //config.Slot0.kD = Constants.kbutterArmkD.get(); need to add
        config.Slot0.kP = 0.0;
        config.Slot0.kD = 0.0;
        
        control = new PositionVoltage(0);

        // These are determined by the gears in the gearbox and the sprocket on the chain
        config.Feedback.SensorToMechanismRatio = (72.0 / 8.0);
        
        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        butterArm.getConfigurator().apply(config);

        // Tell motor controller where the arm is initially
        butterArm.setPosition(0.0);
    }
    

    public Command idle() {
        return this.run(() -> {
            butterArm.setControl(control.withPosition(0));
           
        });
    }
    
    // Moves butter arm to butter position
    public Command up() {
        return this.run(() -> {
            butterArm.setControl(control.withPosition(30));
        });
    }
  
    
    
    
}
