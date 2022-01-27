/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;



import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.sql.Time;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.AnalogInput;

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
  
  private Timer time;
  private double starttime;
  private boolean seenBlue;
  private boolean closeDis;
  private double currentDistanceInches;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  Encoder encoder = new Encoder(0, 1, true, EncodingType.k4X);
  private AnalogInput ultraSonic;
	public static final double WHEEL_DIAMETER = 4;
	public static final double PULSE_PER_REVOLUTION = 360;
	public static final double ENCODER_GEAR_RATIO = 0;
	public static final double GEAR_RATIO = 8.45 / 1;
	public static final double FUDGE_FACTOR = 1.0;


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
    ultraSonic= new AnalogInput(0);
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
    closeDis = false;

    final double distancePerPulse = Math.PI * Defines.WHEEL_DIAMETER / Defines.PULSE_PER_REVOLUTION / Defines.ENCODER_GEAR_RATIO / Defines.GEAR_RATIO * Defines.FUDGE_FACTOR; 
    encoder.setDistancePerPulse(distancePerPulse);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    //switch (m_autoSelected) {
     // case kCustomAuto:
        // Put custom auto code here
    drive.safteyDrive();

    double encoderDistanceReading = encoder.getDistance();
		SmartDashboard.putNumber("encoder reading", encoderDistanceReading);
		
		drive.drive(-0.25, 0);
		if (encoderDistanceReading > 36) {
			drive.drive(0, 0);
		} 
    //driveTillWall(-0.3, 80, 110);
  }
   
  public void driveUntilBlue() {
    Color detectedColor= colorSensor.getColor();
    double IR = colorSensor.getIR();

    if (detectedColor.blue < 0.3 && !seenBlue) {
      drive.drive(-0.2, 0);
      SmartDashboard.putNumber("Blue", detectedColor.blue);
    } else {
      drive.safteyDrive();
      seenBlue = true;
    }
  }

  public void driveTillWall(double power, double distanceAway, double rampDownDistance){
    // The difference btwn distance to travel and Ramp down distance should be the power you gieve *100
    double rawValue = ultraSonic.getValue();
    double voltageScaleFactor = 5/RobotController.getVoltage5V();
    currentDistanceInches = rawValue * voltageScaleFactor * 0.0492;

    SmartDashboard.putNumber("Distance in inches: ", currentDistanceInches);
   // driveTillWall(currentDistanceInches);

      if (currentDistanceInches <= rampDownDistance && currentDistanceInches > distanceAway){
        closeDis = true;
      } else if (currentDistanceInches <= 30.0){
        drive.safteyDrive();
      }
      
      if(!closeDis){
        drive.drive(power, 0);
      } else if (closeDis){
        double pwr = (currentDistanceInches - distanceAway)/100;
        SmartDashboard.putNumber("Power" , pwr);
        drive.drive(-pwr, 0);
      } 
  }

       // break;
     // case kDefaultAuto:
     // default:
      //  break;
    

  
  
 
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

    double rawValue = ultraSonic.getValue();
    double voltageScaleFactor = 5/RobotController.getVoltage5V();
    double currentDistanceInches = rawValue * voltageScaleFactor * 0.0492;
  
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    SmartDashboard.putNumber("Distance in inches: ", currentDistanceInches);
      
  }
    
  @Override
  public void testPeriodic() {
  }

/*autonomousCEOTB = Autonomous: End of the Beginning*/
 /* public void autonomousCEOTB() {
    // Shoot ball
    // TODO: Add encoders to measure distance
    // drive.drive();
    //last part
    if (!(currentDistanceInches < 50) ) {
      drive.drive(2,0);
     } else {
       drive.safteyDrive();
     }
     // turn
     // drive
     // stop
 }*/

}