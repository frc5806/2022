//package edu.wpi.first.wpilibj.examples.ramsetecommand;
package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
public class DriveSubsystem extends SubsystemBase {
    Constants driveConstants = new Constants();
    // The motors on the left side of the drive.
    private final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(1, 3);
    private final PIDController m_leftPIDController = new PIDController(1, 0, 0);
    private final PIDController m_rightPIDController = new PIDController(1, 0, 0);

    private CANSparkMax driveL1 = new CANSparkMax(driveConstants.kLeftMotor1Port, MotorType.kBrushless); // 3
    private CANSparkMax driveL2 = new CANSparkMax(driveConstants.kLeftMotor2Port, MotorType.kBrushless); // 3
    private CANSparkMax driveL3 = new CANSparkMax(driveConstants.kLeftMotor3Port, MotorType.kBrushless); // 3

    // The motors on the Right side of the drive.
    private CANSparkMax driveR1 = new CANSparkMax(driveConstants.kRightMotor1Port, MotorType.kBrushless); // 3
    private CANSparkMax driveR2 = new CANSparkMax(driveConstants.kRightMotor2Port, MotorType.kBrushless); // 3
    private CANSparkMax driveR3 = new CANSparkMax(driveConstants.kRightMotor3Port, MotorType.kBrushless); // 3

  // add drive constants file and make instance of it
  // Group the left motors.
  private final MotorControllerGroup m_leftMotors =
      new MotorControllerGroup(
          driveL1,
          driveL2,
          driveL3);

  // Group the right motors.
  private final MotorControllerGroup m_rightMotors =
      new MotorControllerGroup(
          driveR1,
          driveR2,
          driveR3);

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  private final DifferentialDriveKinematics m_kinematics =
  new DifferentialDriveKinematics(driveConstants.kTrackwidthMeters);
  // The left-side drive encoder
  private final RelativeEncoder m_leftEncoder = driveL1.getAlternateEncoder(4260);

  // The right-side drive encoder
  private final RelativeEncoder m_rightEncoder = driveR1.getAlternateEncoder(4260);

  // The gyro sensor
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotors.setInverted(true);
    driveL1.setSmartCurrentLimit(20);
    driveL2.setSmartCurrentLimit(20);
    driveL3.setSmartCurrentLimit(20);
    driveR1.setSmartCurrentLimit(20);
    driveR2.setSmartCurrentLimit(20);
    driveR3.setSmartCurrentLimit(20);

    // Sets the distance per pulse for the encoders
    //m_leftEncoder.setDistancePerPulse(driveConstants.kEncoderDistancePerPulse);
    //m_rightEncoder.setDistancePerPulse(driveConstants.kEncoderDistancePerPulse);

    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        m_gyro.getRotation2d(), m_leftEncoder.getPosition()*driveConstants.kWheelDiameterMeters, m_rightEncoder.getPosition()*driveConstants.kWheelDiameterMeters);
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getVelocity(), m_rightEncoder.getVelocity());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }
  public void drive(double fwd, double rot){
    m_drive.curvatureDrive(fwd, rot, true);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_leftMotors.setVoltage(leftVolts);
    m_rightMotors.setVoltage(rightVolts);
    m_drive.feed();
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getPosition()*driveConstants.kWheelDiameterMeters + m_rightEncoder.getPosition()*driveConstants.kWheelDiameterMeters) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public RelativeEncoder getLeftEncoder() {
    return m_leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public RelativeEncoder getRightEncoder() {
    return m_rightEncoder;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }
  public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
    final double leftFeedforward = m_feedforward.calculate(speeds.leftMetersPerSecond);
    final double rightFeedforward = m_feedforward.calculate(speeds.rightMetersPerSecond);

    final double leftOutput =
        m_leftPIDController.calculate(m_leftEncoder.getVelocity()/60*driveConstants.kWheelDiameterMeters, speeds.leftMetersPerSecond);
    final double rightOutput =
        m_rightPIDController.calculate(m_rightEncoder.getVelocity()/60*driveConstants.kWheelDiameterMeters, speeds.rightMetersPerSecond);
    m_leftMotors.setVoltage(leftOutput + leftFeedforward);
    m_rightMotors.setVoltage(rightOutput + rightFeedforward);
  }

  public void drive2(double xSpeed, double rot) {
    var wheelSpeeds = m_kinematics.toWheelSpeeds(new ChassisSpeeds(xSpeed, 0.0, rot));
    setSpeeds(wheelSpeeds);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  public void updateOdometry() {
    m_odometry.update(
        m_gyro.getRotation2d(), m_leftEncoder.getPosition()*driveConstants.kWheelDiameterMeters, m_rightEncoder.getPosition()*driveConstants.kWheelDiameterMeters);
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }
}
