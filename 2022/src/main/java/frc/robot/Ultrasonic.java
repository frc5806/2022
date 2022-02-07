package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotController;

import java.sql.Time;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogInput;



public class Ultrasonic {
    private AnalogInput ultraSonic;
    private double currentDistanceInches;
    private boolean closeDis;

    public Ultrasonic(){
        closeDis = false;
        ultraSonic= new AnalogInput(0);
    }

    public double getDistance(){
        double rawValue = ultraSonic.getValue();
        double voltageScaleFactor = 5/RobotController.getVoltage5V();
        currentDistanceInches = rawValue * voltageScaleFactor * 0.0492;
        return currentDistanceInches;
    }

    public void driveTillBlue(double power, double distanceAway, double rampDownDistance, Drivetrain drive){
            // The difference btwn distance to travel and Ramp down distance should be the power you gieve *100
        if (getDistance() <= rampDownDistance && getDistance() > distanceAway){
            closeDis = true;
          } else if (currentDistanceInches <= 30.0){
            drive.safteyDrive();
          }
          
          if(!closeDis){
            drive.drive(power, 0);
          } else if (closeDis){
            double pwr = (getDistance() - distanceAway)/100;
            SmartDashboard.putNumber("Power" , pwr);
            drive.drive(-pwr, 0);
          } 
    }
}
