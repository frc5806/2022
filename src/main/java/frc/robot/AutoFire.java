package frc.robot;

public class AutoFire extends Thread implements RobotData, AutoFireData{
    private boolean enable = true;
    private Turret turret;
    private Drivetrain drive;
    private boolean onRight = true;
    public AutoFire(Turret turret){
        this.turret = turret;
    }
    public void run(){
        while(enable){
            Data.AFenabled = enable;
            track();
            System.out.println(Data.d);
        }
    }
    public void enableC(){
        enable=!enable;
    }
    public void track(){
        double tValue = (maxTRSpeed*Math.sqrt(Math.abs(Data.x)-dToBound()))/(Math.sqrt(visionBound-dToBound()))+(onRight?toSpeed():-1*toSpeed())+(-1*Data.roboTVel);
        tValue = tValue>1?1:tValue;
        if(Data.x<0)
            turret.turn(-1*tValue);
        else
            turret.turn(tValue);
    }
}