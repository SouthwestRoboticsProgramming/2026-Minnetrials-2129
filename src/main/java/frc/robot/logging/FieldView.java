package frc.robot.logging;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.FieldObject2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class FieldView {
    private static final Field2d field = new Field2d();
    private static final Field2d left = new Field2d();
    private static final Field2d right = new Field2d();

    public static void publish() {
        SmartDashboard.putData("Field View", field);
        SmartDashboard.putData("Left", left);
        SmartDashboard.putData("Right", right);
    }
}
