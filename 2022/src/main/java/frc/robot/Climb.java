package frc.robot;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import com.revrobotics.SparkMaxPIDController;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb {
    private static Solenoid climbenoid1;
    private static Solenoid climbenoid2;
    private static Solenoid climbenoid3;
    private static Solenoid climbenoid4;
    private boolean enabler;
    private SparkMaxPIDController m_pidController1;
    private SparkMaxPIDController m_pidController2;
    
    private RelativeEncoder m_encoder1;
    private RelativeEncoder m_encoder2;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public CANSparkMax winchold1;
    public CANSparkMax winchold2;

    private static Compressor compressor;

    public Climb(int position1, int position2, int victor1, int victor2, int position3, int position4) {
        climbenoid1 = new Solenoid(PneumaticsModuleType.CTREPCM, position1); // 7
        climbenoid2 = new Solenoid(PneumaticsModuleType.CTREPCM, position2); // 4
        winchold1 = new CANSparkMax(victor1, MotorType.kBrushless);
        winchold2 = new CANSparkMax(victor2, MotorType.kBrushless);
        enabler = false;
        climbenoid3 = new Solenoid(PneumaticsModuleType.CTREPCM, position3); // 7
        climbenoid4 = new Solenoid(PneumaticsModuleType.CTREPCM, position4); // 4

        climbenoid1.set(false);
        climbenoid2.set(false);
        climbenoid3.set(false);
         
        m_pidController1 = winchhold1.getPIDController();

    // Encoder object created to display position values
        m_encoder1 = winchhold1.getEncoder();
        
        m_pidController2 = winchhold2.getPIDController();

    // Encoder object created to display position values
        m_encoder2 = winchhold2.getEncoder();

        winchold1.setIdleMode(IdleMode.kBrake);
        winchold1.setIdleMode(IdleMode.kBrake);
        kP = 0.1; 
        kI = 1e-4;
        kD = 1; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

    // set PID coefficients
        m_pidController1.setP(kP);
        m_pidController1.setI(kI);
        m_pidController1.setD(kD);
        m_pidController1.setIZone(kIz);
        m_pidController1.setFF(kFF);
        m_pidController1.setOutputRange(kMinOutput, kMaxOutput);
        
        m_pidController2.setP(kP);
        m_pidController2.setI(kI);
        m_pidController2.setD(kD);
        m_pidController2.setIZone(kIz);
        m_pidController2.setFF(kFF);
        m_pidController2.setOutputRange(kMinOutput, kMaxOutput);
        
        

    }

    public void enableComp(){
        compressor.enableHybrid(40, 80);
    }
    
    public void winchInPID(rpm){
        m_pidController1.setReference(-rpm, CANSparkMax.ControlType.kVelocity);
        m_pidController2.setReference(rpm, CANSparkMax.ControlType.kVelocity);
    }
    
    public void winchOutPID(rpm){
        m_pidController1.setReference(rpm, CANSparkMax.ControlType.kVelocity);
        m_pidController2.setReference(-rpm, CANSparkMax.ControlType.kVelocity);
    }

    public void winchIn(){
        winchold1.set(-1);
        winchold2.set(1);
    }

    public void winchOut(){
        winchold1.set(1);
        winchold2.set(-1);
    }

    public void winch(double speed){
        winchold1.set(speed);
        winchold2.set(-speed);
    }
    public void winchStop(){
        winchold1.set(0);
        winchold2.follow(winchold1);
    }

    public void armForward() {
        climbenoid1.set(true);
        climbenoid2.set(true);
    }
    
    public void armBackward() {
        climbenoid1.set(false);
        climbenoid2.set(false);
    }

    public void clencher(){
        enabler = !enabler;
        climbenoid3.set(enabler);
        climbenoid4.set(enabler);
    }

}
