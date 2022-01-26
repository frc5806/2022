/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //private static final String kDefaultAuto = "Default";
  //private static final String kCustomAuto = "My Auto";
  //private String m_autoSelected;
  //private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Joystick joystick;
  private Joystick buttonBoard;
  
  Drivetrain drive;
  
  public Timer time;
  private double starttime;
  private boolean seenBlue;


  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
   // m_chooser.addOption("My Auto", kCustomAuto);
    drive = new Drivetrain(1, 2, 3, 4);
    joystick = new Joystick(0);
    
    
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
   // m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    //System.out.println("Auto selected: " + m_autoSelected);
    starttime = time.getFPGATimestamp();
    seenBlue = false;
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    //switch (m_autoSelected) {
     // case kCustomAuto:
        // Put custom auto code here

    Color detectedColor = colorSensor.getColor();
    double IR = colorSensor.getIR();

    if (detectedColor.blue < 0.3 && !seenBlue ){
      drive.drive(-0.2, 0);
      SmartDashboard.putNumber("Blue", detectedColor.blue);
      
    } else {
      drive.safteyDrive();
      seenBlue = true;
      /// TODO Update starttime
     double timer =  time.getFPGATimestamp();
      if (timer - starttime < 4){
            drive.arcadeDrive(0, .2);
      } else{
            drive.safteyDrive();
      }
    }
    

   

       // break;
     // case kDefaultAuto:
     // default:
      //  break;
    }

  
  
 
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //comp.start();

    if(joystick.getRawAxis(1) > .01 || joystick.getRawAxis(4) > .01 || joystick.getRawAxis(1) < -.01 || joystick.getRawAxis(4) < -.01){
      drive.drive(joystick.getRawAxis(1) , joystick.getRawAxis(4));
    }
    else{
      drive.safteyDrive();
    }

    // Color sensor 
    Color detectedColor = colorSensor.getColor();
    double IR = colorSensor.getIR();
  
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
      
  }
    
  @Override
  public void testPeriodic() {
  }
}
