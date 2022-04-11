package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANPIDController;



public class Shooter {
    private final Limelight limelight;
    private final Constants constants;
    static public CANSparkMax shooter1;
    static public CANSparkMax shooter2;
    private final SparkMaxPIDController pid1;
    private final SparkMaxPIDController pid2;

    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public double kP2, kI2, kD2, kIz2, kFF2, kMaxOutput2, kMinOutput2;

    static public VictorSPX shooter3;

    static public double speed;
    private double targetRPM;

    public Shooter(int CANID1,int CANID2,int CANID3, Limelight limelight) {
        this.limelight = limelight;
        shooter1 = new CANSparkMax(CANID1, MotorType.kBrushless);
        shooter2 = new CANSparkMax(CANID2, MotorType.kBrushless);
        shooter3 = new VictorSPX(CANID3);

        pid1 = shooter1.getPIDController();
        pid2 = shooter2.getPIDController();
        
        constants = new Constants();
        speed = 0;

        kP = 0.5; 
        kI = 1e-4;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

  
        pid2.setP(kP);
        pid2.setI(kI);
        pid2.setD(kD);
        pid2.setIZone(kIz);
        pid2.setFF(kFF);
        pid2.setOutputRange(kMinOutput, kMaxOutput);

        kP2 = 0.5; 
        kI2 = 1e-4;
        kD2 = 0; 
        kIz2 = 0; 
        kFF2 = 0; 
        kMaxOutput2 = 1; 
        kMinOutput2 = -1;

    // set PID coefficients
        pid1.setP(kP);
        pid1.setI(kI);
        pid1.setD(kD);
        pid1.setIZone(kIz);
        pid1.setFF(kFF);
        pid1.setOutputRange(kMinOutput, kMaxOutput);
        
    }

    public void shoot(double setSpeed) {
        speed = setSpeed;

        shooter1.set(speed);
        shooter2.set(speed);

        shooter3.set(ControlMode.PercentOutput, speed);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double newSpeed) {
        if (newSpeed >= 0 && newSpeed <= 1) {
            speed = newSpeed;
        } else {
            System.out.println("~~~ENTER A SPEED BETWEEN 0 AND 1!~~~");
        }
    }

    public void setSpeedPID(double vel){
        pid1.setReference(vel, CANSparkMax.ControlType.kVelocity);
        pid2.setReference(vel, CANSparkMax.ControlType.kPVelocity);
    }

    public void dontShoot() {
        shooter1.set(0);
        shooter2.set(0);
        shooter3.set(ControlMode.PercentOutput, 0);
    }


    // change to set reference kSmartVelocity
    public void shootLL(){
        double distance = limelight.getDistanceFromTarget() + 0.5;
        //                                                     ^
        // may have to alter based on tested + position of limelight
        if(distance <= 30)
            targetRPM = (distance * 34.1) + 2505;
        else
        // set constant speed if more than 30 inches
            targetRPM = 3750;

        shoot(targetRPM/Constants.maxRPM);
    }

}
