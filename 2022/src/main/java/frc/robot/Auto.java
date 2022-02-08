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


public class Auto{
    public double moveSpeed;
    public double turnSpeed;
    private int position;
    private double startTime;
    private boolean hasGone;
    private Timer timer;
    private double time;
    private SenseColor colorSense;

    

    public Auto(){
        position=1;
        startTime= timer.getFPGATimestamp();
        colorSense = new SenseColor();
        hasGone=false;
        time = 0;
    }
    public void driveTime(int pos, double duration, double speed, double turn, int newPos){
        updateTime();
        if(time < duration && pos == position){
            moveSpeed = speed;
            turnSpeed=turn;
            hasGone=true;
        }
        else if(hasGone && pos == position){
            setPos(newPos);
        }
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
    public void startTimeSet(){
        startTime=timer.getFPGATimestamp();
    }


    public void driveColor(int pos, String color, double thresh, double speed, double turn, int newPos){
        if(pos==position){
            hasGone=true;
            if(!colorSense.seeingColor(color, thresh)){
                moveSpeed = speed;
                turnSpeed = turn;
            }
            else{
                setPos(newPos);
                moveSpeed = 0;
                turnSpeed = 0;
            
            
            }
        }
        
    }

    public void updateTime(){
        time=timer.getFPGATimestamp()-startTime;
    }

    

    
}
