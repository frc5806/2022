/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  Drivetrain drive;
  private Joystick joystick;
  private Joystick buttonBoard;
  private AutoDrivetrain a;
  private Intake in;
  private Hopper hop;
  private Climb climb;
  private Turret turret;
  private Compressor comp;
  private Feeder feed;
  private Index holder;
  private boolean reverse=false;
  private Shooter shoot;
  private boolean armPressed = false;
  private boolean climbSafety = false;
  private boolean climbUpper = false;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    drive = new Drivetrain(4, 3, 10, 9);
    comp = new Compressor();
    comp.start();
    joystick = new Joystick(0);
    turret = new Turret(1);
    //AutoFire autofire = new AutoFire(turret);
    //autofire.start();
    buttonBoard = new Joystick(0);
    a = new AutoDrivetrain(drive);
    in = new Intake(7);
    hop = new Hopper(8, 6);
    climb = new Climb(0);
    feed = new Feeder(2);
    holder = new Index(hop, feed);
    climbSafety = false;
    //add CAN ids
    shoot = new Shooter(0,0);
    a.start();

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
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        break;
    }
  }
 
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //comp.start();
    if(joystick.getRawButtonPressed(1)){
      if(comp.enabled())
          comp.stop();
        else{
          comp.start();
        }
    }
    if(joystick.getPOV(0)==90){
      
      System.out.println("90");
    }
    if(joystick.getPOV(0)==90 && climbSafety){
      climb.winchIn();
      System.out.println("90");
    }
    else if(joystick.getPOV(0)==270 && climbSafety ){
      climb.winchOut();
    }
    else if(joystick.getPOV(0)==0 && climbSafety){
      if(!armPressed){
        climb.raiseArm();
        System.out.println("example");
        climbUpper = !climbUpper;
      }
      armPressed=true;
      climb.winchStop();
    }
    else if(joystick.getPOV(0)==180 && climbSafety){
      if(!armPressed){
        climb.lowerArm();
      }
      armPressed=true;
      climb.winchStop();
    }
    else if(joystick.getRawButtonPressed(8)){
      climbSafety = !climbSafety;
      climb.winchStop();
    }
    else{
      armPressed=false;
      climb.winchStop();
    }
    if(joystick.getRawAxis(1) > .01 || joystick.getRawAxis(4) > .01 || joystick.getRawAxis(1) < -.01 || joystick.getRawAxis(4) < -.01){
      drive.drive(joystick.getRawAxis(1) , joystick.getRawAxis(4));
      climb.winchStop();
    }
    else{
      drive.safteyDrive();
    }
    
    if(joystick.getRawAxis(3)>.05)
      in.spin(reverse?-joystick.getRawAxis(3):joystick.getRawAxis(3));
    else
      in.spinDown();
    if (joystick.getRawButtonPressed(7)) {
      reverse =  !reverse;
    }
    if(joystick.getRawButton(6))
      turret.turn(-1);
    else if(joystick.getRawButton(5))
      turret.turn(1);
    else
    turret.stop();
    if(joystick.getRawAxis(2)>.05)
      feed.spinUp(reverse?-joystick.getRawAxis(2):joystick.getRawAxis(2));
    else
      feed.spinDown();

    SmartDashboard.putBoolean("Climb is ready: ", climbSafety);
    SmartDashboard.putBoolean("Climb is ok: ", climbUpper);
    SmartDashboard.putBoolean("Intake and feeder is reversed: ", reverse);
    
  }
    /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
/* 

//drive.drive(joystick.getRawAxis(1), joystick2.getRawAxis(0));
    //drive.drive(joystickR.getRawAxis(1), joystickL.getRawAxis(0));
    if(joystick.getRawAxis(3) > .1)
      holder.toShoot();
    else
      holder.stop();

      if(joystick.getRawButtonPressed(5))
      climb.raiseArm();
    if(joystick.getRawButtonPressed(4))
      climb.lowerArm();
    
    if(joystick.getPOV(pov)&&Data.climbing){
      climb.winch(joystick.getRawAxis(2));
      drive.safteyDrive();
    }
    else{
      drive.drive(joystickL.getRawAxis(1), joystickR.getRawAxis(0));
      climb.winchStop();
    }

    if(joystickR.getRawButton(2)&&!Data.armIsRaised&&!Data.climbing){
      turret.turn(joystickR.getRawAxis(2));
    }
    else{
      turret.stop();
    }
    
    if(joystickR.getRawButton(1))
      in.spin(1);
    else{
      in.spinDown();
      if(joystickR.getRawButtonPressed(4))
        if(comp.enabled())
          comp.stop();
        else
          comp.start()
        
    
    if(buttonBoard.getRawButton(1))
      climb.winchOut();
    else if(buttonBoard.getRawButton(2))
      climb.winchIn();
    else
      climb.winchStop();
      SmartDashboard.putBoolean("Arm is raised: ", Data.armIsRaised);
      SmartDashboard.putBoolean("Climbing: ", Data.climbing);

*/
