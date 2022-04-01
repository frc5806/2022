package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Shooter {
    static public CANSparkMax shooter1;
    static public CANSparkMax shooter2;
    static public VictorSPX shooter3;

    public Shooter(int CANID1,int CANID2,int CANID3) {
        shooter1 = new CANSparkMax(CANID1, MotorType.kBrushless);
        shooter2 = new CANSparkMax(CANID2, MotorType.kBrushless);
        shooter3 = new VictorSPX(CANID3);
        
        
        
    }

    public void shoot() {
        shooter1.set(.5);
        shooter2.set(.5);

        shooter3.set(ControlMode.PercentOutput, .5);
    }

    public void dontShoot() {
        shooter1.set(0);
        shooter2.set(0);
        shooter3.set(ControlMode.PercentOutput, 0);
    }

}
