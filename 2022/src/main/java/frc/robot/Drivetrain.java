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

public class Drivetrain{
    
   
    public WPI_VictorSPX driveR1;
    public WPI_VictorSPX driveR2;
    public WPI_VictorSPX driveL1;
    public WPI_VictorSPX driveL2;
 //   SpeedControllerGroup left;
  //  SpeedControllerGroup right;
    public MotorControllerGroup left;
    public MotorControllerGroup right;
    DifferentialDrive drive;
    public Drivetrain(int CANIDSR1, int CANIDSR2, int CANIDSL1, int CANIDSL2){
       
        driveR1 = new WPI_VictorSPX(CANIDSR1);
        driveR2 = new WPI_VictorSPX(CANIDSR2);
        driveL1 = new WPI_VictorSPX(CANIDSL1);
        driveL2 = new WPI_VictorSPX(CANIDSL2);
      
        driveR1.setInverted(true);
        driveR2.setInverted(true);
    
        right = new MotorControllerGroup(driveR1, driveR2);
        left = new MotorControllerGroup(driveL1, driveL2);
        drive = new DifferentialDrive(left, right);
    }
    public void drive(double speed, double turn){
        drive.curvatureDrive(-speed, turn, speed<.15||turn<.4);
    }

    public void drive2(double speed, double turn){
        // Right
        driveR2.follow(driveR1);
        driveR1.set(ControlMode.PercentOutput, speed+turn);
        driveR2.setInverted(InvertType.FollowMaster);
        
        // Left
        driveL2.follow(driveL1);
        driveL1.set(ControlMode.PercentOutput, speed-turn);
        driveL2.setInverted(InvertType.FollowMaster);
    }


    public void tankDrive(double speedR, double speedL){
        drive.tankDrive(speedL, speedR);
    }
    public void arcadeDrive(double speed, double turn){
        drive.arcadeDrive(speed, turn);
    }
    public void safteyDrive(){
        drive.arcadeDrive(0, 0);
    }
  
}