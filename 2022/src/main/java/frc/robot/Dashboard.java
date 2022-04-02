package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
    
    // Initialize value on SmartDashboard for user input, but leave old value if already present.
    public static double createSmartDashBoardNumber(String key, double defaultValue) {

        // see if there's already a value
        double value = SmartDashboard.getNumber(key, defaultValue);

        SmartDashboard.putNumber(key, value);

        return value;
    } // end of createSmartDashBoardNumber


    public static void createSpeedSlot(String key, Shooter shooter) {

        SmartDashboard.putNumber(key, shooter.getSpeed());
    } // end of createSpeedlot

    public static double getDashSpeed(Shooter shooter) {
        return SmartDashboard.getNumber("Speed", shooter.getSpeed()); // second param is default valueu if nothing is found
    }






}
