package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
// ... (other imports remain the same) ...
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ButterArm extends SubsystemBase {

    // --- Constants ---
    private static final int MOTOR_ID = 7; // Example CAN ID
    private static final double GEAR_RATIO = 100.0; 
    // This value is now 0.0, which means 0 degrees is the starting position 
    // when the encoder is reset.
    private static final double STARTING_ANGLE_DEGREES = 0.0; 
    // Sets the current measured position (rotations) to the rotation equivalent of 0 degrees.
    

    // PID/Motion Magic Constants - (Keep tuning these!)
    // ... (kP, kI, kD, CRUISE_VELOCITY_ROT, etc. remain the same) ...
    private static final double kP = 0.1;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kF = 0.0; 
    private static final double CRUISE_VELOCITY_ROT = 2.0;
    private static final double ACCELERATION_ROT = 4.0; 

    private final TalonFX armMotor;
    private final MotionMagicVoltage m_motionMagic = new MotionMagicVoltage(0).withSlot(0);
    

    /**
     * Creates a new ButterArm subsystem.
     */
    public ButterArm() {
        armMotor = new TalonFX(7);
        configureMotor();
        // Sets the current measured position (rotations) to the rotation equivalent of 0 degrees.
        armMotor.setPosition(degreesToRotations(STARTING_ANGLE_DEGREES));
        // **IMPORTANT:** Do NOT zero here if you want it zeroed every match start.
        // We will call the zero method from the main Robot class instead.
    }

    // ... (configureMotor() and degreesToRotations() methods remain the same) ...

    private double degreesToRotations(double degrees) {
        return (degrees / 360.0) * GEAR_RATIO;
    }

    private void configureMotor() {
        // ... (PID configuration logic remains the same) ...
        armMotor.getConfigurator().apply(new TalonFXConfiguration());
        TalonFXConfiguration config = new TalonFXConfiguration();

        Slot0Configs slot0Config = config.Slot0;
        slot0Config.kP = kP;
        slot0Config.kI = kI;
        slot0Config.kD = kD;
        slot0Config.kS = kF; 

        config.MotionMagic.MotionMagicCruiseVelocity = CRUISE_VELOCITY_ROT;
        config.MotionMagic.MotionMagicAcceleration = ACCELERATION_ROT;

        armMotor.getConfigurator().apply(config);
    }
    
    /**
     * Resets the integrated encoder's position to the starting angle (0 degrees).
     * This MUST be called at the beginning of every match.
     */
    public void zeroArmPosition() {
        // Set the current motor position to the rotation value that corresponds to 0 degrees
        armMotor.setPosition(degreesToRotations(STARTING_ANGLE_DEGREES));
        System.out.println("ButterArm encoder zeroed. Current angle: 0.0 degrees.");
    }
    
    /**
     * Moves the arm to a specific angle using Motion Magic.
     * @param targetDegrees The desired angle in degrees.
     */
    public void setArmPosition(double targetDegrees) {
        double targetRotations = degreesToRotations(targetDegrees);
        armMotor.setControl(m_motionMagic.withPosition(targetRotations));
    }

    public boolean isAtTarget() {
        // double tolerance = degreesToRotations(2.0); // 2 degrees tolerance example
        // double error = armMotor.getPosition().getError();
        // return Math.abs(error) < tolerance;

        double tolerance = 2.0;
        var positionSignal = armMotor.getPosition();
        positionSignal.refresh();
        double rotations = positionSignal.getValueAsDouble();
        double positionDegrees = rotations * 360.0;

        return Math.abs(positionDegrees) < tolerance;
    }

    @Override
    public void periodic() {
        // ...
    }
    public Command idle() {
        return this.run(() -> {
     // Stop the flywheel to conserve battery power.
     armMotor.setPosition(0.0);
     });
    }
    public Command up() {
        return this.run(() -> {
            
            armMotor.setPosition(-5);
        });
    }
    public Command score() {
        return this.run(() -> {

            armMotor.setPosition(10);
        });
    }

}