package frc.robot;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.sql.Time;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.AnalogInput;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import com.kauailabs.navx.frc.AHRS;


public class Auto{
    public double moveSpeed;
    public double turnSpeed;

    private int position;

    private boolean hasGone;

    private double startTime;
    private Timer timer;
    private double time;

    private SenseColor colorSense;
    private Limelight limelight;

    Drive drive;

    AHRS gyro;
    double error;
    int setpoint, integral, previous_error = 0;


    

    public Auto(Limelight limelight, Drive drive, AHRS gyro, double error){
        position = 1;
        startTime = timer.getFPGATimestamp();
        colorSense = new SenseColor();
        hasGone=false;
        time = 0;
        this.limelight = limelight;
        this.drive = drive;

        this.gyro = gyro;
        this.error = error;
    }

    public void updateTime(){
        time=timer.getFPGATimestamp()-startTime;
    } 

    public void startTimeSet(){
        startTime=timer.getFPGATimestamp();
    }

    public void AddPos(){
        position=position+1;
        startTime = timer.getFPGATimestamp();
        hasGone = false;
    }

    public void setPos(int x){
        position= x;
        startTime = timer.getFPGATimestamp();
        hasGone = false;
    }

/*------------------------------------------------------------------------------------------*/

    public void driveTime(int pos, double duration, double speed, double turn, int newPos){
        updateTime();

        if (time < duration && pos == position) {
            drive.drive.arcadeDrive(speed, turn, false);
            hasGone = true;
        }
        else if (hasGone && pos == position) {
            setPos(newPos);
        }
    } // end of driveTime

    public void driveDistance(int pos, double distance, int newPos) {
        if(pos==position){
            if(!hasGone){
                drive.resetEncoders();
            }
            hasGone=true;
            boolean going = drive.driveDSimple(distance);
            if(!going){
                setPos(newPos);
                drive.drive.arcadeDrive(0, 0, false);
            }
            
        }
    } // end of driveDistance

    public void driveTurn(int pos, int distance, int newPos) {
        
        if(pos==position){
            if(!hasGone){
                drive.resetGyro();
            }
            hasGone=true;
            boolean going = drive.gyroTurn(distance);
            if(!going){
                setPos(newPos);
                drive.drive.arcadeDrive(0, 0, false);
            }
            
        }
    } // end of driveDistance


    public void driveColor(int pos, String color, double thresh, double speed, double turn, int newPos) {
        if (pos == position) {
            hasGone=true;
            if (!colorSense.seeingColor(color, thresh)) {
                drive.drive.arcadeDrive(speed, turn, false);
            } else {
                setPos(newPos);
                drive.drive.arcadeDrive(0, 0, false);
            
            
            }
        }
        
    } // end of driveColor

    public void turnLime(int pos, double target, int newPos) {
        if (position == pos){
            if (limelight.x - target > 2){
                drive.drive.arcadeDrive(0, .2, false);
            } 
            else if (limelight.x - target < -2){
                moveSpeed = 0;
                turnSpeed = -.2;
            } 
            else {
                setPos(newPos);
                drive.drive.arcadeDrive(0, 0, false);
            }
        }
    } // end of turnLime


    
} // end of class
