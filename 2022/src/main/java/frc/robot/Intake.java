package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Intake {
    private Solenoid pistonIntake1;
    private Solenoid pistonIntake2;

    static public CANSparkMax intake1;
    static public VictorSPX hopper;

    public boolean intakeEnabled = false;
    public boolean intakeSpeed;
   
    public Intake(int CANID1,int CANID2, int position1, int position2) {
        intake1 = new CANSparkMax(CANID1, MotorType.kBrushless);
        hopper = new VictorSPX(CANID2);
        
        pistonIntake1 = new Solenoid(0, PneumaticsModuleType.CTREPCM, position1); //5
        pistonIntake2 = new Solenoid(0, PneumaticsModuleType.CTREPCM, position2);//6

       
    }

    public void forwardIntake(double speed) {
        intake1.set(speed);
    }

    public void forwardHopper() {
        hopper.set(ControlMode.PercentOutput, 1);
    }

    public void stopIntake(){
        intake1.set(0);
    }

    public void rampUp(double seconds){
        double speed = 0;
        speed -= 0.25 * seconds;
        if (seconds >= 30){
            speed = -0.85;
        }
        intake1.set(speed);
    }

    public void stopHopper(){
        hopper.set(ControlMode.PercentOutput, 0);
    }

    public void backIntake(){
        intake1.set(.5);
    }

    public void backHopper(){
        hopper.set(ControlMode.PercentOutput, -1);
    }

    public void setIntake(){
        intakeEnabled = !intakeEnabled;
        pistonIntake1.set(intakeEnabled);
        pistonIntake2.set(intakeEnabled);
    }

   
    
}
