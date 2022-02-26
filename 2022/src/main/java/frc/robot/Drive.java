package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*; 
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drive {
    int P, I, D = 1;
    public int integral, previous_error, setpoint = 0;
    public double error;
    public AHRS gyro;
    
    double rcw; // turn
    public static DrivetrainSpark drive;


    private static double wheelDiameter;
    private static double wheelCircum;

    public static RelativeEncoder encoderR1;



    public Drive(AHRS gyro, DrivetrainSpark drive) {
        this.gyro = gyro;
        this.drive = drive;
        wheelCircum = Math.PI * wheelDiameter;
        encoderR1 = drive.driveR1.getAlternateEncoder(4260);

    }

    public void setSetpoint(int setpoint) {
        this.setpoint = setpoint;
    }

    public void PID() {
        error = setpoint - gyro.getAngle(); // Error = Target - Actual
        this.integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        double derivative = (error - this.previous_error) / .02;
        this.rcw = P*error + I*this.integral + D*derivative;
    }

    public void execute() {
        PID();
        drive.arcadeDrive(0, rcw, false);
    }

    public void driveStraight(double power){
        error = -gyro.getAngle();
        double turn_power = P * error;
        drive.arcadeDrive(power, turn_power, false);
    }

    public boolean turn(int angle){
        setSetpoint(angle);
        execute();
        return Math.abs(error) < 2;
    }

    public void resetEncoders(){
        encoderR1.setPosition(0);
    }

    public void resetGyro() {
        gyro.setAngleAdjustment(gyro.getAngle());
    }

    public void driveDistance(double distance, double pvalue){
        double distanceLeft = distance - encoderR1.getPosition()/encoderR1.getCountsPerRevolution()*wheelCircum; // Error = Target - Actual
        
        this.integral += (distanceLeft*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        double derivative = (distanceLeft - this.previous_error) / .02;
        this.rcw = pvalue*distanceLeft + I*this.integral + D*derivative;

        drive.arcadeDrive(rcw, 0, false);
    }

    public boolean driveDSimple(double distance){
        boolean inAction=true;
        double distanceLeft = distance - encoderR1.getPosition()/encoderR1.getCountsPerRevolution()*wheelCircum; // Error = Target - Actual
        
        if (distanceLeft > 7) {
            drive.arcadeDrive(.5, 0, false);
            inAction=true;
        }
        else if (distanceLeft > 0){
            drive.arcadeDrive(.1, 0, false);
            inAction=true;
        }
        else{
            inAction=false;
            drive.arcadeDrive(0, 0, false);
        }
        

        return inAction;

    } // end of driveSimple

    public boolean gyroTurn(double degrees) {

        // drive.PID();

        boolean inAction=true;
        double distanceLeft = degrees - gyro.getAngle(); // Error = Target - Actual
        
        if (distanceLeft > 7) {
            drive.arcadeDrive(0, 0.5, false);
            inAction=true;
        }
        else if (distanceLeft > 0){
            drive.arcadeDrive(0, 0.1, false);
            inAction=true;
        }
        else{
            inAction=false;
            drive.arcadeDrive(0, 0, false);
        }
        

        return inAction;

    }

    
    

}
