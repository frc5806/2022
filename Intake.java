package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
final class Intake{
    CANSparkMax intakeM;
    public Intake(int CANIDS){
        intakeM = new CANSparkMax(CANIDS,MotorType.kBrushless);
    }
    public void spinUp(double speed){
        intakeM.set(speed);
    }
    public void spinDown(){
        intakeM.set(0);
    }
}