package frc.robot;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Climb {
    private static Solenoid climbenoid1;
    private static Solenoid climbenoid2;

    private static VictorSPX winchold1;
    private static VictorSPX winchold2;

    public Climb(int position1, int position2, int victor1, int victor2) {
        climbenoid1 = new Solenoid(PneumaticsModuleType.CTREPCM, position1);
        climbenoid2 = new Solenoid(PneumaticsModuleType.CTREPCM, position2);
        winchold1 = new VictorSPX(victor1);
        winchold2 = new VictorSPX(victor2);
        climbenoid1.set(false);
        climbenoid2.set(false);
    }

    public void winchIn(){
        winchold1.set(ControlMode.PercentOutput, -.7);
        winchold2.follow(winchold1);
    }

    public void winchOut(){
        winchold1.set(ControlMode.PercentOutput, 1);
        winchold2.follow(winchold1);
    }

    public void winch(double speed){
        winchold1.set(ControlMode.PercentOutput, speed);
        winchold2.follow(winchold1);
    }
    public void winchStop(){
        winchold1.set(ControlMode.PercentOutput, 0);
        winchold2.follow(winchold1);
    }

    public void raiseArm() {
        climbenoid1.set(true);
        climbenoid2.set(true);
    }
    
    public void lowerArm() {
        climbenoid1.set(false);
        climbenoid2.set(false);
    }
}