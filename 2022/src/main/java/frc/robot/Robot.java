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
import java.io.IOException;
import java.nio.file.Path;

import static edu.wpi.first.wpilibj.XboxController.Button;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.util.List;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Filesystem;

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
  private Trajectory m_trajectory;
  private final RamseteController m_ramseteController = new RamseteController();
  private Joystick joystick2;
  private int direction;
  private boolean bools2=true;
  private Intake intake;
  private Joystick buttonBoard;
  private boolean bools = false;
  private double sensitivity = 1;
  private double startTime;
  private Timer timer;
  
  private Constants constants=new Constants();
  //private Compressor compressor;
  
  private LED led;
 // Drivetrain drive;
  
  //DrivetrainSpark driveSpark;
  DriveSubsystem driveSpark;
  private Limelight limelight;
  private int position1 = 0;
  private int position2 = 0;
  private double shooterSensitivity;
 
  // private AHRS gyro;
  private Climb climb;
  private Shooter shooter;
  private int prev1 = 0;
  private int prev2=0;
  private boolean prev1B=false;
  private boolean prev2B=false;
  private double reverse = 1;

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
    String trajectoryJSON = "paths/YourPath.wpilib.json";
   
    //m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
   // m_chooser.addOption("My Auto", kCustomAuto);
  // *** GET RID OF DRIVESPARK WHEN COMMENTING IN
  //m_robotContainer = new RobotContainer();
    timer = new Timer();
    shooterSensitivity=1;
    joystick1 = new Joystick(0);
    joystick2 = new Joystick(2);
    buttonBoard = new Joystick(1);   
    climb = new Climb(7,4,11,13,5,6);
    limelight = new Limelight();
    shooter = new Shooter(7, 6, 20, limelight);    
    position1=0;
    position2=0;
    prev1B=true;
    prev2B=true;
    intake = new Intake(9, 37, 2, 3);
    led= new LED(8, 88);
    direction=1;
  //  auto = new Auto(limelight);
    driveSpark = new DriveSubsystem(); 
    try {
    Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
    m_trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    }
    catch(IOException ex){
      DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
    }
   

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
    
    timer.start();
    startTime = timer.get();
    driveSpark.resetOdometry(m_trajectory.getInitialPose());
  }

 
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
      // Update odometry.
      driveSpark.updateOdometry();

      // Update robot position on Field2d.
     
  
      if (timer.get() < m_trajectory.getTotalTimeSeconds()) {
        // Get the desired pose from the trajectory.
        var desiredPose = m_trajectory.sample(timer.get());
  
        // Get the reference chassis speeds from the Ramsete controller.
        var refChassisSpeeds = m_ramseteController.calculate(driveSpark.getPose(), desiredPose);
  
        // Set the linear and angular speeds.
        driveSpark.drive2(refChassisSpeeds.vxMetersPerSecond, refChassisSpeeds.omegaRadiansPerSecond);
      } else {
        driveSpark.drive2(0, 0);
      }
  }

 
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    System.out.println("teleop"); 
    // if(joystick2.getRawButton(6)){
    //   direction=-1*direction;
    // }

    sensitivity  = 0.5 - joystick2.getRawAxis(3)/2;
    shooterSensitivity  = 0.5 - joystick1.getRawAxis(3)/2;
    
    //comp.start();
   // compressor.enableDigital();
   // change to two for airplane controller 
   // drive
  //  if(!goer){
  //    led.mode=1;
  //    goer=true;
  //  }




    /* ------- Drive ------- */

   if (joystick2.getRawAxis(0) > 0.01 || joystick2.getRawAxis(2) > 0.01 || joystick2.getRawAxis(0) < -0.01 || joystick2.getRawAxis(2) < -0.01){
     driveSpark.drive(joystick2.getRawAxis(1) * sensitivity *reverse, joystick2.getRawAxis(2)*sensitivity*reverse);
    } else {
      System.out.println("hello");
      driveSpark.drive(0, 0);
    }

    // Reverse drive
    if (joystick2.getRawButton(8)){
      reverse = 1;
    }
    if (joystick2.getRawButton(10)){
      reverse = -1;
    }
    

  /* ------ Intake ----------- */
  if (joystick2.getRawButton(1)) {
    intake.forwardIntake(-0.65);
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
      shooter.setSpeedPID(5600*shooterSensitivity); // takes in speed
      led.inShot=true;
    }
    else if( joystick1.getRawButton(3)){
      shooter.setSpeedPID(5600*.5);
      led.inShot=true;
    }
    else{
      shooter.setSpeedPID(0.0);
      led.inShot=false;
    }
    if(joystick1.getRawButtonReleased(1) || joystick1.getRawButtonReleased(3)){
      led.changeMode();
    }

    shooter.update();
    
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

  

	if(buttonBoard.getRawButton(1)){ 
      
      position1 = position1+ 11;
      position2 = position2- 11;
       

      
      bools2=true;
      prev1B=true;
      prev2B=true;
      
      
    }
    else if (buttonBoard.getRawButton(2)) {
           
      position1 =  position1- 11;
      position2 = position2+ 11;
      
      bools2=false;
      prev1B=true;
      prev2B=true;
      
      }
  
      else{
        if (buttonBoard.getRawButton(4)) {
          prev1B=true;
          position1 = position1 +11;
        } 
        else if (buttonBoard.getRawButton(3)) {
          prev1B=true;
          position1=position1-11;
        }
        else{
          if(prev1B){
          position1=(int)climb.m_encoder1.getPosition();
          }
          prev1B=false;
        }
        if (buttonBoard.getRawButton(5)) {
          position2 = position2-11;
          prev2B=true;
        }
        else if (buttonBoard.getRawButton(6)) {
          position2=position2+11;
          prev2B=true;
          
        }
        else{
          if(prev2B){
          position2=-(int)climb.m_encoder2.getPosition();
          }
          prev2B=false;
        }

      
      
    }

 
   climb.update(position1, position2);
  
     
    
          
      




     
      
    
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
