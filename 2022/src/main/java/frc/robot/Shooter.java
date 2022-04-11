package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Shooter {
    private final Limelight limelight;
    private final Constants constants;
    static public CANSparkMax shooter1;
    static public CANSparkMax shooter2;
    static public VictorSPX shooter3;

    static public double speed;
    private double targetRPM;

    public Shooter(int CANID1,int CANID2,int CANID3, Limelight limelight) {
        this.limelight = limelight;
        shooter1 = new CANSparkMax(CANID1, MotorType.kBrushless);
        shooter2 = new CANSparkMax(CANID2, MotorType.kBrushless);
        shooter3 = new VictorSPX(CANID3);
        constants = new Constants();
        speed = 0;
        
        
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

    public void dontShoot() {
        shooter1.set(0);
        shooter2.set(0);
        shooter3.set(ControlMode.PercentOutput, 0);
    }


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
