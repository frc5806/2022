package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
final class Intake{
    private CANSparkMax intakeM;
    public Intake(int CANIDS){
        intakeM = new CANSparkMax(CANIDS,MotorType.kBrushless);
    }
    public void spin(double speed){
        intakeM.set(-speed);
    }
    public void spinUp(){
        intakeM.set(-1);
    }
    public void spinDown(){
        intakeM.stopMotor();
    }
}