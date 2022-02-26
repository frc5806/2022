package frc.robot;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Climb {
    private Solenoid climbenoid1;
    private Solenoid climbenoid2;

    public Climb(int position1, int position2) {
        climbenoid1 = new Solenoid(PneumaticsModuleType.CTREPCM, position1);
        climbenoid2 = new Solenoid(PneumaticsModuleType.CTREPCM, position2);
        climbenoid1.set(false);
        climbenoid2.set(false);
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