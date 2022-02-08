package frc.robot;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Climb {
    private Solenoid climbenoid;

    public Climb(int position) {
        climbenoid = new Solenoid(PneumaticsModuleType.CTREPCM,position);
    }
    public void raiseArm() {climbenoid.set(true);}
    public void lowerArm() {climbenoid.set(false);}
}