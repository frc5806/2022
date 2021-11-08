package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

final class Turret implements PIDTurretData{
    TalonSRX turretM;
    private double x;
    private double y;
    private double rA;
    private double targetX;
    private double targetY;
    private Timer time;
    public Turret(int CANIDT){
        turretM = new TalonSRX(CANIDT);

        x = 0;
        y = 0;
        rA = 0;
        
        targetX = 0;
        targetY = 0;

        turretM.configFactoryDefault();
        turretM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        turretM.setSelectedSensorPosition(0);

        turretM.setNeutralMode(NeutralMode.Brake);

        turretM.setSensorPhase(true);

        turretM.configPeakOutputForward(1);
        turretM.configPeakOutputReverse(-1);

        turretM.config_kF(0, FF);
        turretM.config_kP(0, P);
        turretM.config_kI(0, I);
        turretM.config_kD(0, I);
    }
    public void turn(double speed){
        turretM.set(ControlMode.PercentOutput, speed);
        Data.onRight = turretM.getSelectedSensorPosition()>=0;
    }
    public void turnRPM(double velocity){
        turretM.set(ControlMode.Velocity, (velocity * 4096 / 600));
        //System.out.println((velocity * 4096 / 600));
        Data.onRight = turretM.getSelectedSensorPosition()>=0;
    }
    public void stop(){
        turretM.set(ControlMode.PercentOutput, 0);
    }
    public void isolateMotionA(CANSparkMax[] motors){
        DifferentialDriveKinematics kine = new DifferentialDriveKinematics(RobotData.chassisWidthMeters);

        double speedR = Math.max(motors[0].getEncoder().getVelocity(), motors[1].getEncoder().getVelocity());
        double speedL = Math.max(motors[2].getEncoder().getVelocity(), motors[3].getEncoder().getVelocity());

        ChassisSpeeds robotSpeeds = kine.toChassisSpeeds(new DifferentialDriveWheelSpeeds(speedL, speedR));

        double angularV = robotSpeeds.omegaRadiansPerSecond;
        double angleCompRotM = -1*((angularV/(2*Math.PI))*60);

        System.out.println(angleCompRotM);

        turnRPM(angleCompRotM);

    }
    public void isolateMotionP(CANSparkMax[] motors){
        DifferentialDriveKinematics kine = new DifferentialDriveKinematics(RobotData.chassisWidthMeters);

        double speedR = Math.max(motors[0].getEncoder().getVelocity(), motors[1].getEncoder().getVelocity());
        double speedL = Math.max(motors[2].getEncoder().getVelocity(), motors[3].getEncoder().getVelocity());

        ChassisSpeeds robotSpeeds = kine.toChassisSpeeds(new DifferentialDriveWheelSpeeds(speedL, speedR));

        double linearV = robotSpeeds.vxMetersPerSecond;
        double angularV = robotSpeeds.omegaRadiansPerSecond;
        
        double angleCompRotM = -1*((angularV/(2*Math.PI))*60);
        
        rA += angularV * time.get();
        x += Math.cos(rA)*linearV*time.get();
        y += Math.sin(rA)*linearV*time.get();

        double linearCompRotM = (((Math.atan((targetX - (x + Math.cos(rA)*linearV*RobotData.tDiff))/(targetY - (y + Math.sin(rA)*linearV*RobotData.tDiff))) - Math.atan((targetX - x)/(targetY - y)))/RobotData.tDiff)/(2*Math.PI))*60;
        
        turnRPM(linearCompRotM+angleCompRotM);
        time.reset();
    }
    public void startIsolateP(){
        time.start();
    }
    public void endIsolateP(){
        time.reset();
        time.stop();
    }
    public void resetRPosition(){
        x = 0;
        y = 0;
        rA = 0;
    }
    public void resetTPosition(){
        turretM.setSelectedSensorPosition(0);
    }
    public double[] getRPosition(){
        return new double[]{x,y,rA};
    }
    public double getTAngle(){
        return ((2*Math.PI)/4096);
    }
    public void selectSTarget(double x, double y){
        targetX = x;
        targetY = y;
    }
    public void selectRTarget(double distM){
        targetX = distM * Math.cos(((2*Math.PI)/4096)) + x;
        targetY = distM * Math.sin(((2*Math.PI)/4096)) + y;
    }
    public void rotateToPos(double pos){
        turretM.set(ControlMode.MotionMagic, pos);
    }
}