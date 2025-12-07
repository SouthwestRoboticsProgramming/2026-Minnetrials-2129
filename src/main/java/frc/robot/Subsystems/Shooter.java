package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase {
  
  private final TalonFX flywheelMotor;
  

  public Shooter() {
    flywheelMotor = new TalonFX(6);
    TalonFXConfiguration flywheelConfig = new TalonFXConfiguration();
    flywheelConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
    flywheelMotor.getConfigurator().apply(flywheelConfig);
    flywheelMotor.setInverted(true);
  }
  // @Override
  // public void periodic() {
  //   if (getCurrentCommand() == null) {
  //     System.out.println("No current command");
  //   } else {
  //     System.out.println(getCurrentCommand().getName());
  //   } 
  // }
  public Command idle() {
        return this.run(() -> {
     // Stop the flywheel to conserve battery power.
     flywheelMotor.setControl(new NeutralOut());
     });
    }
    public Command spinFlywheel() {
        return this.run(() -> {
          // Run the flywheel at the shooting speed.
<<<<<<< Updated upstream
          flywheelMotor.setControl(new VoltageOut(9.5));
=======
          flywheelMotor.setControl(new VoltageOut(9));
>>>>>>> Stashed changes
        }).withName("Shooting them Balls!");
      }
}
