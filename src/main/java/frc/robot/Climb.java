package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
final class Climb{
    private Solenoid arm;
    private VictorSPX winch1;
    private VictorSPX winch2;
    public Climb(int port){
        arm = new Solenoid(port);
        winch1 = new VictorSPX(12);
        winch2 = new VictorSPX(13);
    }
    public void arm(boolean state){
        arm.set(state);
    }
    public void raiseArm(){
        arm.set(true);
        Data.armIsRaised=true;
        //Data.climbing = true;
    }
    public void lowerArm(){
        arm.set(false);
        Data.armIsRaised=false;
    }
    public void changeArm(){
        if(Data.armIsRaised){
            arm.set(false);
        }
        else{
            arm.set(true);
        }
        //Data.climbing = !Data.climbing;
        Data.armIsRaised=!Data.armIsRaised;
    }
    public void winchIn(){
        winch1.set(ControlMode.PercentOutput, -.7);
        winch2.follow(winch1);
    }
    public void winchOut(){
        winch1.set(ControlMode.PercentOutput, 1);
        winch2.follow(winch1);
    }
    public void winch(double speed){
        winch1.set(ControlMode.PercentOutput, speed);
        winch2.follow(winch1);
    }
    public void winchStop(){
        winch1.set(ControlMode.PercentOutput, 0);
        winch2.follow(winch1);
    }
}