package frc.robot;
public class AutoFire extends Thread implements RobotData{
    private boolean enable = true;
    public void run(){
        while(!enable){
            Data.enableAF = enable;

        }
    }
    public void enableC(){
        enable=!enable;
    }
}