/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.sql.Time;

import edu.wpi.first.wpilibj.SPI;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import edu.wpi.first.wpilibj.Encoder;

import com.kauailabs.navx.frc.*;
// import com.kauailabs.navx.frc.AHRS;


import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.RelativeEncoder;


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
  private Joystick joystick1;
  private Joystick joystick2;
  private bools2=true;
  private Intake intake;
  private Joystick buttonBoard;
  private boolean bools = false;
  private double sensitivity = 1;
  //private Compressor compressor;
  
  private LED led;
 // Drivetrain drive;
  
  //DrivetrainSpark driveSpark;
  DriveSubsystem driveSpark;
  private Limelight limelight;
  private int position1 = 0;
  private int position2 = 0;
  private int position1Double = 0;
  private int position2Double = 0;
  // private AHRS gyro;
  private Climb climb;
  private VictorSPX tester33;
  private Shooter shooter;
  // private final I2C.Port i2cPort = I2C.Port.kOnboard;
  // private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  //Encoder encoder = new Encoder(0, 1, true); // Add EncodingType.k4X
  boolean goer = false;
  

/*
	public static final double WHEEL_DIAMETER = 4;
	public static final double PULSE_PER_REVOLUTION = 360;
	public static final double ENCODER_GEAR_RATIO = 0;
	public static final double GEAR_RATIO = 8.45 / 1;
	public static final double FUDGE_FACTOR = 1.0;
*/

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  



  @Override
  public void robotInit() {
    //m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
   // m_chooser.addOption("My Auto", kCustomAuto);
  // *** GET RID OF DRIVESPARK WHEN COMMENTING IN
  //m_robotContainer = new RobotContainer();
    joystick1 = new Joystick(0);
    buttonBoard = new Joystick(1);    
    joystick2 = new Joystick(2);
    climb = new Climb(7,4,11,13,5,6);
    shooter = new Shooter(7, 6, 20);    
    
    intake = new Intake(9, 37, 2, 3);
    led= new LED(8, 88);
    limelight = new Limelight();
  //  auto = new Auto(limelight);
    driveSpark = new DriveSubsystem();    


    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    
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
     // Fill the buffer with a rainbow
     
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
  //  auto.startTimeSet();

  }

 
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    //switch (m_autoSelected) {
     // case kCustomAuto:
        // Put custom auto code here
   /* auto.driveColor(1,"blue", .3, .2, 0, 2);
    auto.driveTime(2,2, 0, 0, 3 );
    auto.driveTime(3,2,0,.3, 4);
    auto.driveTime(4, 100, 0, 0, 4);*/

    // each case refers to each starting position
  /*  if (casire == 1) {  

      auto.driveDistance(1, 72, 2);
      auto.driveTurn(2,90,3);
      auto.driveDistance(3,22*12,4);
      auto.driveTime(4, 1000, 0, 0, 4);

    } else if (casire == 2) {  

      auto.driveTurn(1, 210, 2);
      auto.driveDistance(3,19*12,4);
      auto.driveTime(4, 1000, 0, 0, 4);

    } else if (casire == 3) {  

      auto.driveTurn(1, 203, 2);
      auto.driveDistance(3,20*12,4);
      auto.driveTime(4, 1000, 0, 0, 4);

    } else {  

      auto.driveDistance(1, 96, 2);
      auto.driveTurn(2,270,3);
      auto.driveDistance(3,22*12,4);
      auto.driveTime(4, 1000, 0, 0, 4);

    }*/

    //double encoderDistanceReading = encoder.getDistance();
		//SmartDashboard.putNumber("encoder reading", encoderDistanceReading);
		
	//	drive.drive(-0.25, 0);
	/*	if (encoderDistanceReading > 36) {
			drive.drive(0, 0);
		} 
    //driveTillWall(-0.3, 80, 110);
    */
  }

 
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    System.out.println("teleop"); 

    sensitivity  = .5 + joystick2.getRawAxis(4)/2;
    
    //comp.start();
   // compressor.enableDigital();
   // change to two for airplane controller 
   // drive
  //  if(!goer){
  //    led.mode=1;
  //    goer=true;
  //  }




    /* ------- Drive ------- */

   // if (joystick2.getRawAxis(0) > 0.01 || joystick2.getRawAxis(2) > 0.01 || joystick2.getRawAxis(0) < -0.01 || joystick2.getRawAxis(2) < -0.01){
   //   driveSpark.arcadeDrive(joystick2.getRawAxis(1) * sensitivity, joystick2.getRawAxis(2)*sensitivity);
   //  } else {
    //   System.out.println("hello");
    //   driveSpark.arcadeDrive(0, 0);
    // }
    

  /* ------ Intake ----------- */
  if (joystick2.getRawButton(0)) {
    intake.forwardIntake();
   } else if (joystick2.getRawButton(3)) {
    intake.backIntake();
   } else  {
     intake.stopIntake();
 }

 if (joystick2.getRawButtonPressed(2)){
   intake.setIntake();
 }

    Dashboard.createSmartDashBoardNumber("Speed", 0);
    Dashboard.createSpeedSlot("Speed", shooter); 
    shooter.setSpeed(Dashboard.getDashSpeed(shooter));







  //  /* ------ Hopper -------- */ No more hopper :(
  //  if (buttonBoard.getRawButton(5)) {
  //   intake.forwardHopper();
  // } else if(buttonBoard.getRawButton(6)) {
  //   intake.backHopper();
  // } else{
  //   intake.stopHopper();
  // }


    /* ------------ Shooter --------- */
    if (joystick1.getRawButton(1)){
      shooter.shoot(1); // takes in speed
      led.inShot=true;
    }
    else if( joystick1.getRawButton(3)){
      shooter.shoot(.5);
      led.inShot=true;
    }
    else{
      shooter.dontShoot();
      led.inShot=false;
    }
    if(joystick1.getRawButtonReleased(1) || joystick1.getRawButtonReleased(3)){
      led.changeMode();
    }
    
    System.out.println(shooter.shooter1.isFollower());

    // if (joystick2.)


    /*------- Climb ------- */
  
    // winch
      /*if (buttonBoard.getRawButton(1)) {
        climb.winchInPID(5000);
      }
      else if (buttonBoard.getRawButton(2)) {
        climb.winchOutPID(5000);
      }

      else{
        if (buttonBoard.getRawButton(4)) {
          climb.m_pidController1.setReference(-5000, CANSparkMax.ControlType.kVelocity);
        }
        else if (buttonBoard.getRawButton(3)) {
          climb.m_pidController1.setReference(5000, CANSparkMax.ControlType.kVelocity);
        }
        else{
          climb.m_pidController1.setReference(climb.m_encoder1.getPosition(), CANSparkMax.ControlType.kPosition);
        }
        if (buttonBoard.getRawButton(5)) {
          climb.m_pidController2.setReference(5000, CANSparkMax.ControlType.kVelocity);
        }
        else if (buttonBoard.getRawButton(6)) {
          climb.m_pidController2.setReference(-5000, CANSparkMax.ControlType.kVelocity);
        }
        else{
          climb.m_pidController2.setReference(climb.m_encoder2.getPosition(), CANSparkMax.ControlType.kPosition);
        }
	}*/
	if(buttonBoard.getRawButton(1){ 
      if(bools2){
      position1Double = position1Double+ .042;
      position2Double = position2Double+ .042;
      }
      else{
        position1Double=climb.m_encoder1.getPosition()+.042;
        position2Double=climb.m_encoder2.getPosition()+.042;
      }
      bools2=true;
      
      
    }
    else if (buttonBoard.getRawButton(2)) {
            if(!bools2){
      position1Double = position1Double- .042;
      position2Double = position2Double- .042;
      }
      else{
        position1Double=climb.m_encoder1.getPosition()-.042;
        position2Double=climb.m_encoder2.getPosition()-.042;
      }
      bools2=false;
      
      }
  
      else{
        if (buttonBoard.getRawButton(4)) {
          position1Double = climb.m_encoder1.getPosition()+.042;
        }
        else if (buttonBoard.getRawButton(3)) {
          position1Double=climb.m_encoder1.getPosition()-.042;
        }
        else{
          position1Double=climb.m_encoder1.getPosition()
        if (buttonBoard.getRawButton(4)) {
          position2Double = climb.m_encoder2.getPosition()+.042;
        }
        else if (buttonBoard.getRawButton(3)) {
          position2Double=climb.m_encoder12getPosition()-.042;
        }
        else{
          position2Double=climb.m_encoder2.getPosition()
        }
      
      
    }
        climb.winchPID((int)position1Double, (int)position2Double);
    
          
      




     
      
    
      // forward and backwards arm
      if(buttonBoard.getRawButtonPressed(7)){
        bools=!bools;
        if(bools){
          climb.armForward();
        }
        else{
          climb.armBackward();
        }
      }
    
      // clench hand
      if(buttonBoard.getRawButtonPressed(8)){
        climb.clencher();
      }


  
 
      
   led.run(); 
  } // end of teleop


  @Override
  public void testPeriodic() {
  }

}
