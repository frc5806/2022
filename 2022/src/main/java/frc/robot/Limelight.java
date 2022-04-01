package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    public double x;
    public double y;
    public double area;


    /*--------------------- Calculating distance-------------------------- */
    double targetOffsetAngle_Vertical = ty.getDouble(0.0); // vertical offset
    double targetOffsetAngle_Horizontal = tx.getDouble(0.0); // horzontal offset

    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = 25.0; // edit later

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeightInches = 20.0; // l

    // distance from the target to the floor
    double goalHeightInches = 41.0; // obtained from manual

    double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    //calculate distance
    double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches)/Math.tan(angleToGoalRadians);

    double Kp = -0.1;
    double steering_adjust;

    
    /*---- Aiming---- */

    public void steerSideways() {
    
    
       // while (targetOffsetAngle_Horizontal 
        double heading_error = tx.getDouble(0.0);
        steering_adjust = Kp * tx.getDouble(0.0);
        
        


        




        
    }





    /*---- Functions ----- */
    public void update() {
        //read values periodically
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        area = ta.getDouble(0.0);

    }

    public void driverCamera() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    public void retroCamera() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    }


} // end of class
