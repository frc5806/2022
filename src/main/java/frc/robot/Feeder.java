package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
final class Feeder{
    public CANSparkMax intakeM1;
    public Feeder(int CANID){
        intakeM1 = new CANSparkMax(CANID,MotorType.kBrushless);
    }
    public void spinUp(double speed){
        intakeM1.set(speed);
    }
    public void spinDown(){
        intakeM1.stopMotor();
    }
}