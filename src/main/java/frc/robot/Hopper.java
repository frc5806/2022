package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
final class Hopper{
    CANSparkMax intakeM1;
    CANSparkMax intakeM2;
    public Hopper(int CANIDS1,int CANIDS2){
        intakeM1 = new CANSparkMax(CANIDS1,MotorType.kBrushless);
        intakeM2 = new CANSparkMax(CANIDS2,MotorType.kBrushless);
        intakeM1.setSmartCurrentLimit(40);
        intakeM2.setSmartCurrentLimit(40);
    }
    public void spinUp(double speed){
        intakeM1.set(speed);
        intakeM2.set(-1*speed);
    }
    public void spinDown(){
        intakeM1.stopMotor();
        intakeM2.stopMotor();
    }
}