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
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotController;
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
import com.kauailabs.navx.frc.AHRS;


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
  private Intake intake;
  private Joystick buttonBoard;
  //private Compressor compressor;
  private Climb climb;
  private LED led;
  private 
 // Drivetrain drive;
  
  //DrivetrainSpark driveSpark;
  DriveSubsystem driveSpark;
  private Timer time;
  private double starttime;
  private boolean seenBlue;
  private Limelight limelight;
  private AHRS gyro;
  private CANSparkMax tester;


  private CANSparkMax test1;
  private CANSparkMax test2;
  private CANSparkMax tester2;
  private CANSparkMax shooter1;
  private CANSparkMax shooter2;

  
  
  private Shooter shooter;
  // private final I2C.Port i2cPort = I2C.Port.kOnboard;
  // private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  //Encoder encoder = new Encoder(0, 1, true); // Add EncodingType.k4X
    
  double speed = 0;
  boolean goer = false;

  int casire = 1;

  CANSparkMax test;
  double power = -0.2;

  int multipleCount = 0;
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
  //  drive = new Drivetrain(1, 2, 3, 4);
  // *** GET RID OF DRIVESPARK WHEN COMMENTING IN
  //m_robotContainer = new RobotContainer();
    joystick1 = new Joystick(0);
    joystick2 = new Joystick(2);
    
    
    // tester = new CANSparkMax(11, MotorType.kBrushless);
  
   /* intake = new Intake(99, 739, 365, 214);
    shooter = new Shooter(78325, 34154, 49816);
    climb = new Climb(520, 613);
*/

    led= new LED(8, 88);
    buttonBoard = new Joystick(1);    
    limelight = new Limelight();
  //  auto = new Auto(limelight);


    //driveSpark = new DrivetrainSpark(2, 5, 8, 3, 4, 10);
    driveSpark = new DriveSubsystem();

    // test1 = new CANSparkMax(11, MotorType.kBrushless);
    // test2 = new CANSparkMax(9, MotorType.kBrushless);
     tester = new CANSparkMax(13, MotorType.kBrushless);
     tester2 = new CANSparkMax(9, MotorType.kBrushless);
     shooter1 = new CANSparkMax(15, MotorType.kBrushless);
     shooter2 = new CANSparkMax(7, MotorType.kBrushless);
   //  tester = new CANSparkMax(13, MotorType.kBrushless);

    


  //  driveMethods = new Drive(gyro, driveSpark);
   // compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
    //climb = new Climb(0);
    time = new Timer();
    tester2.follow(tester, true);
    

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
    starttime = time.getFPGATimestamp();
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
   
  public void driveUntilBlue() {
   /* Color detectedColor= colorSensor.getColor();
    double IR = colorSensor.getIR();

    if (detectedColor.blue < 0.3 && !seenBlue) {
     // drive.drive(-0.2, 0);
      SmartDashboard.putNumber("Blue", detectedColor.blue);
    } else {
      // drive.safteyDrive();
      seenBlue = true;
    }*/
  }

 
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
   
   
    //comp.start();
   // compressor.enableDigital();
   // change to two for airplane controller 

   

   // drive
  //  if(!goer){
  //    led.mode=1;
  //    goer=true;
  //  }
   
   
    // 0 = x-axis, 1 = y-axis, 3 = slider


    if (joystick2.getRawAxis(0) > 0.01 || joystick1.getRawAxis(1) > 0.01 || joystick2.getRawAxis(0) < -0.01 || joystick1.getRawAxis(1) < -0.01){
      driveSpark.arcadeDrive(-joystick1.getRawAxis(1), -joystick2.getRawAxis(0));
    } else {
      System.out.println("hello");
      driveSpark.arcadeDrive(0, 0);
    }


    
    System.out.println(time.getFPGATimestamp());
    int times = (int)time.getFPGATimestamp();
    
    // we used this to automaticaly reverse the pullies to grease them. 
    if (times % 8 == 0){
      if (multipleCount == 0) {
      power = -power;
      System.out.println("multiple count" + multipleCount);
      multipleCount += 1;
      }
    } else {
      System.out.println("else: " + multipleCount);
      multipleCount = 0;
    }

    // test1.set(-power); // 11
    // test2.set(-power); // 9
    
    
    //pls delete, just for testing

    if(buttonBoard.getRawButton(1)){
      System.out.println("yes");
      tester.set(-.5);
    }
    else if(buttonBoard.getRawButton(2)){
      tester.set(.5);
    }
    else{
      tester.set(0);
    } 
    if (buttonBoard.getRawButton(3)){
      shooter1.set(0.3);
    } 
    
    if (buttonBoard.getRawButton(4)){
      shooter2.set(0);
    }

    
    
    
   
   

 
   
  //driveSpark.arcadeDrive(.2, 0, false);

   /*----- Intake & Shoot ----- */
/*
   if (buttonBoard.getRawButton(7)) {
     intake.forwardIntake();
   } else if(buttonBoard.getRawButton(8)) {
     intake.backIntake();
   } else{
     intake.stopIntake();
   }

   if (buttonBoard.getRawButton(5)) {
    intake.forwardHopper();
  } else if(buttonBoard.getRawButton(6)) {
    intake.backHopper();
  } else{
    intake.stopHopper();
  }
  */
  

   if (buttonBoard.getRawButton(4)) {
  //   //shooter.shoot();
     led.inShot = true;
  } else{
  //  // shooter.dontShoot();
     led.inShot=false;
   }

  if(buttonBoard.getRawButtonReleased(4)){
     led.changeMode();
   }

  // // intake pistons
  // if (buttonBoard.getRawButtonPressed(3)) {
      
  //     //intake.setIntake();
  // }





  /*------- Climb ------- */
  /*
  if (buttonBoard.getRawButtonPressed(1)) {
    climb.raiseArm();
  } else if (buttonBoard.getRawButtonPressed(2)) {
    climb.lowerArm();
  }

*/


  
     
    // limelight.update();
    //post to smart dashboard periodically
    // SmartDashboard.putNumber("LimelightX", limelight.x);
    // SmartDashboard.putNumber("LimelightY", limelight.y);
    // SmartDashboard.putNumber("LimelightArea", limelight.area);
    // limelight.driverCamera();

    




   led.run(); 

  

  }


  @Override
  public void testPeriodic() {
  }

}