package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Shooter {
    static public CANSparkMax shooter1;
    static public CANSparkMax shooter2;
    static public CANSparkMax shooter3;

    public Shooter(int CANID1,int CANID2,int CANID3) {
        shooter1 = new CANSparkMax(CANID1, MotorType.kBrushless);
        shooter2 = new CANSparkMax(CANID2, MotorType.kBrushless);
        shooter3 = new CANSparkMax(CANID3, MotorType.kBrushless);
        shooter2.follow(shooter1);
        shooter3.follow(shooter1);
    }

    public void shoot() {
        shooter1.set(1);
    }

    public void dontShoot() {
        shooter1.set(0);
    }

}
