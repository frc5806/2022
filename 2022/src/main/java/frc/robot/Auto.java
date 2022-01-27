package frc.robot;



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

private int position;
private int startTime;
private Drivaetrain drivetrain;
private boolean hasGone;
private Timer timer;
private double time;
private SenseColor colorSense;

public class Auto{

    public Auto(Drivetrain drive){
        position=1;
        startTime=timer.getFPGATimestamp();
        drivetrain=drive;
        hasGone=false;
        time = startTime;
    }

    public void driveTime(int pos, double duration, double speed, double turn, int newPos){
        updateTime();
        if(time < duration && pos = position){
            drivetrain.drive(speed, turn);
            hasGone=true;
        }
        else if(hasGone && pos=position){
            setPos(newPos);
        }
    }

    public void AddPos(){
        position=position+1;
        startTime = timer.getFPGATimestamp()
        hasGone = false;
    }

    public void setPos(int x){
        position= x;
        startTime = timer.getFPGATimestamp()
        hasGone = false;
    }


    public void driveColor(int pos, String color, double speed, double turn, int newPos){
        if(pos=position){
            hasGone=true;
            if(!colorSense.seeingColor(color)){
                drivetrain.drive(speed, turn);
            }
            else if(hasGone){
                setPos(newPos);
            
            
            }
        }
        
    }


    public void updateTime(){
        time=timer.getFPGATimestamp()-startTime;
    }

}
