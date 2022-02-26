/*---- Gyro class ----*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

public class NavX {
    AHRS ahrs = new AHRS(SPI.Port.kMXP); 
    final static double kCollisionThreshold_DeltaG = 0.5f;
    double last_world_linear_accel_x;
    double last_world_linear_accel_y;

    public boolean hasCollided() {
        boolean collisionDetected = false;
          
        double curr_world_linear_accel_x = ahrs.getWorldLinearAccelX();
        double currentJerkX = curr_world_linear_accel_x - last_world_linear_accel_x;
        last_world_linear_accel_x = curr_world_linear_accel_x;
        double curr_world_linear_accel_y = ahrs.getWorldLinearAccelY();
        double currentJerkY = curr_world_linear_accel_y - last_world_linear_accel_y;
        last_world_linear_accel_y = curr_world_linear_accel_y;
          
        if ( ( Math.abs(currentJerkX) > kCollisionThreshold_DeltaG ) || ( Math.abs(currentJerkY) > kCollisionThreshold_DeltaG) ) {
            collisionDetected = true;
        }
        return collisionDetected;
    }
}
