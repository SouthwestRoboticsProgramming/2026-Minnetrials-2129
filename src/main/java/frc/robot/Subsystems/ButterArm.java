package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ButterArm extends SubsystemBase {

    // --- Arm Angle Constants (Degrees) ---
    private static final double STARTING_ANGLE_DEGREES = 0.0; // The 'Idle' position (always 0 degrees)
    private static final double UP_ANGLE_DEGREES = 18;     // Angle to pick up a game piece (TUNE THIS)
    private static final double SCORE_ANGLE_DEGREES = 28;  // Angle for scoring (TUNE THIS)

    // --- Motor/Mechanism Constants ---
    private static final double GEAR_RATIO_NUMERATOR = 18;
    private static final double GEAR_RATIO_DENOMINATOR = 1;
    private static final double GEAR_RATIO = GEAR_RATIO_NUMERATOR / GEAR_RATIO_DENOMINATOR; 
    
    // --- PID/Motion Magic Constants (Tune these!) ---
    private static final double kP = 5; 
    private static final double kI = 0.0;
    private static final double kD = 0.05;
    private static final double GRAVITY_FEEDFORWARD_VOLTAGE_AT_MAX = 0.5; // TUNE THIS
    private static final double CRUISE_VELOCITY_ROT = 2.0; 
    private static final double ACCELERATION_ROT = 4.0;    

    private final TalonFX armMotor;
    private final MotionMagicVoltage m_motionMagic = new MotionMagicVoltage(0).withSlot(0);
    
    /**
     * Creates a new ButterArm subsystem with a TalonFX on ID 7.
     */
    public ButterArm() {
        armMotor = new TalonFX(7); // Motor ID 7 on CAN bus
        configureMotor();
        
        // Zeros the encoder at the physical start position (0 degrees)
        armMotor.setPosition(0);
        
        // Call idleCommand() as the default command so the arm always tries to hold 0 degrees.
        setDefaultCommand(idle());
    }

// ---

    // ⚙️ Configuration and Conversion

    /**
     * Converts an angle in degrees to the equivalent motor rotations for Motion Magic.
     */
    

    /**
     * Configures the TalonFX for Motion Magic control.
     */
    private void configureMotor() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        Slot0Configs slot0Config = config.Slot0;
        slot0Config.kP = kP;
        slot0Config.kI = kI;
        slot0Config.kD = kD;
        slot0Config.kS = 0.0; // Set dynamically for gravity

        config.MotionMagic.MotionMagicCruiseVelocity = CRUISE_VELOCITY_ROT;
        config.MotionMagic.MotionMagicAcceleration = ACCELERATION_ROT;
        
        // Corrected way to set inversion in Phoenix 6
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive; // Adjust to Clockwise_Positive if needed
        
        armMotor.getConfigurator().apply(config);
    }
    
    /**
     * Resets the integrated encoder's position to the starting angle (0 degrees).
     */
    public void zeroArmPosition() {
        armMotor.setPosition(0);
        System.out.println("ButterArm encoder zeroed. Current angle: 0.0 degrees.");
    }

// ---

    // 🎬 Arm Commands

    /**
     * Calculates the voltage required to counteract gravity at the arm's current position.
     */
    private double calculateGravityFeedforward() {
        double currentRotations = armMotor.getPosition().getValueAsDouble();
        // Convert motor rotations back to arm degrees:
        double currentDegrees = currentRotations * GEAR_RATIO * 360.0;
        
        double angleInRadians = Math.toRadians(currentDegrees);
        
        // Gravity term: F_max * cos(angle). 
        // Assuming 0 degrees is horizontal (max gravity effect).
        double gravityVoltage = GRAVITY_FEEDFORWARD_VOLTAGE_AT_MAX * Math.cos(angleInRadians);
        
        return gravityVoltage;
    }

    /**
     * Command to hold the arm at the starting position (0 degrees).
     */
    public Command idle() {
        return goToPositionCommand(STARTING_ANGLE_DEGREES)
            .withName("Idle_0Deg");
    }
    
    /**
     * Command to move the arm to the 'up' position.
     */
    public Command up() {
        return goToPositionCommand(UP_ANGLE_DEGREES)
            .withName("Up_" + UP_ANGLE_DEGREES + "Deg");
    }
    
    /**
     * Command to move the arm to the 'score' position.
     */
    public Command score() {
        return goToPositionCommand(SCORE_ANGLE_DEGREES)
            .withName("Score_" + SCORE_ANGLE_DEGREES + "Deg");
    }
    
    /**
     * Generic command to move the arm to a target angle using Motion Magic 
     * and applying dynamic gravity feedforward.
     */
    private Command goToPositionCommand(double targetDegrees) {
        return this.run(() -> {
            double targetRotations = (targetDegrees);
            double kG = calculateGravityFeedforward();
            
            // Set the target position AND the feedforward voltage (kG)
            armMotor.setControl(
                m_motionMagic.withPosition(targetRotations)
                             .withFeedForward(kG)
            );
        });
    }

    @Override
    public void periodic() {
        // Nothing needed here.
    }
}
