package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Intake {
    private Solenoid pistonIntake1;
    private Solenoid pistonIntake2;

    static public CANSparkMax intake1;
    static public CANSparkMax hopper;

    public boolean intakeEnabled = false;
   
    public Intake(int CANID1,int CANID2, int position1, int position2) {
        intake1 = new CANSparkMax(CANID1, MotorType.kBrushless);
        hopper = new CANSparkMax(CANID2, MotorType.kBrushless);
        
        pistonIntake1 = new Solenoid(PneumaticsModuleType.CTREPCM, position1);
        pistonIntake2 = new Solenoid(PneumaticsModuleType.CTREPCM, position2);

       
    }

    public void forwardIntake() {
        intake1.set(.5);
    }

    public void forwardHopper() {
        hopper.set(.5);
    }

    public void stopIntake(){
        intake1.set(0);
    }

    public void stopHopper(){
        hopper.set(0);
    }

    public void backIntake(){
        intake1.set(-.5);
    }

    public void backHopper(){
        hopper.set(-.5);
    }
    public void setIntake(){
        intakeEnabled = !intakeEnabled;
        pistonIntake1.set(intakeEnabled);
        pistonIntake2.set(intakeEnabled);
    }

   
    
}
