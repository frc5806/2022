package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision extends Thread implements RobotData {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry tl = table.getEntry("tl");

    private boolean enable = true;
    private double x;
    private double y;
    private double l;
    private double d;
    private double a;

    // read values periodically
    public void run() {
        while (enable) {
            x = tx.getDouble(0.0);
            y = ty.getDouble(0.0);
            l = tl.getDouble(0.0);
            a = ta.getDouble(0.0);
            d = a == 0 ? 0 : ((targetHeight - cameraHeight) / (Math.tan((cameraAngle + y) * (Math.PI / 180))));
            Data.x = this.x;
            Data.y = this.y;
            Data.l = this.l;
            Data.a = this.a;
            Data.d = this.d;
            Data.enableV = enable;
            updateDash();
    }
}
    public void enableC(){
        enable=!enable;
    }
    //post to smart dashboard periodically
    public void updateDash(){
    SmartDashboard.putNumber("LimelightX: ", x);
    SmartDashboard.putNumber("LimelightY: ", y);
    SmartDashboard.putNumber("LimelightArea: ", a);
    SmartDashboard.putNumber("LimelightDistance: ", d);
}
}