package frc.robot.subsystems;

import java.util.function.Supplier;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//network imports
import frc.lib.net.NTDouble;
import frc.lib.net.NTEntry;

public class Drivebase extends SubsystemBase {
  private final NTEntry<Double> AIM_kP = new NTDouble("Drivebase/Aim_kP", 0.0111).setPersistent(); // Proportional constant for aiming

  private final TalonSRX leftMotor1;
  private final TalonSRX leftMotor2;
  private final TalonSRX rightMotor1;
  private final TalonSRX rightMotor2;

  public Drivebase() {  
    //Gives each motor a unique ID that corresponds to the ID set in Phoenix Tuner
      leftMotor1 = new TalonSRX(1);
      leftMotor2 = new TalonSRX(2);
      rightMotor1 = new TalonSRX(3);
      rightMotor2 = new TalonSRX(4);
  }

  


  // @Override
  // public void periodic() {
  //   if (getCurrentCommand() == null) {
  //     System.out.println("No current command");
  //   } else {
  //     System.out.println(getCurrentCommand().getName());
  //   } 
  // }
      
  public Command arcadeDrive(
    Supplier<Double> forwardSupplier,
    Supplier<Double> turnSupplier) {
    return this.run(() -> {
        
      // Get the latest control inputs.
      double forward = forwardSupplier.get() / 2;
      double turn = turnSupplier.get() / 2; 
      // Calculate how fast each set of wheels should turn.
        
      double leftWheels = forward + turn;
      double rightWheels = -forward + turn;

      // Desaturate wheel speeds if needed.
      double maxOutput = Math.max(Math.abs(leftWheels), Math.abs(rightWheels));
      if (maxOutput > .6) {
        // Too fast! Our motor controllers aren't capable of this speed, so we
        // need to slow it down.
        leftWheels = leftWheels / maxOutput;
        rightWheels = rightWheels / maxOutput;
      }
      // Tell the motor controllers to spin the motors!
      leftMotor1.set(ControlMode.PercentOutput, leftWheels);
      leftMotor2.set(ControlMode.PercentOutput, leftWheels);
      rightMotor1.set(ControlMode.PercentOutput, rightWheels);
      rightMotor2.set(ControlMode.PercentOutput, rightWheels);
        
      }).withName("Arcade Drive");
    }
    public Command autoAim(Supplier<Double> txSupplier, Supplier<Double> forwardSupplier) {
      return this.run(() -> {
        // Get the latest control inputs.
        double tx = txSupplier.get();
        double forward = forwardSupplier.get();
        
        // Calculate turn value using proportional control.
        double kP = AIM_kP.get();
        double turn = tx * kP;
        
        // Calculate how fast each set of wheels should turn.
        double leftWheels = forward + turn;
        double rightWheels = -forward + turn;
        
        // Desaturate wheel speeds if needed.
        double maxOutput = Math.max(Math.abs(leftWheels), Math.abs(rightWheels));
        if (maxOutput > .6) {
          // Too fast! Our motor controllers aren't capable of this speed, so we
          // need to slow it down.
          leftWheels = leftWheels / maxOutput;
          rightWheels = rightWheels / maxOutput;
        }
        
        // Tell the motor controllers to spin the motors!
        leftMotor1.set(ControlMode.PercentOutput, leftWheels);
        leftMotor2.set(ControlMode.PercentOutput, leftWheels);
        rightMotor1.set(ControlMode.PercentOutput, rightWheels);
        rightMotor2.set(ControlMode.PercentOutput, rightWheels);
      }).withName("Auto aim");
  }

  
}

  
    
    
