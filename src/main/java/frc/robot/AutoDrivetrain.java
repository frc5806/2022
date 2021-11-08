package frc.robot;

import java.util.concurrent.BlockingQueue;

import com.revrobotics.CANEncoder;

final class AutoDrivetrain extends Thread implements RobotData {

    private Drivetrain drive;

    private BlockingQueue<Double> S;
    private BlockingQueue<Double> T;

    private BlockingQueue<String> Type;

    private double tickPerM;
    private double pR;
    private double pL;
    private double speed;
    private double to;

    private boolean enabled;
    private boolean isMoving;
    private boolean isTurning;
    private boolean running;

    private CANEncoder eR1;
    private CANEncoder eL1;

    public AutoDrivetrain(Drivetrain drive) {
        this.drive = drive;
        eR1 = drive.driveR1.getEncoder();
        eL1 = drive.driveL1.getEncoder();

        tickPerM = eR1.getCountsPerRevolution() / wheelACircum;

        pR = 0;
        pL = 0;

        enabled = true;
        isMoving = false;
        isTurning = false;
        running = false;
    }

    public void run() {
        while (enabled) {
                Data.ADEnabled=true;
                if (!running && (isMoving || isTurning))
                    setEP();
                if (isMoving) {
                    isTurning = false;
                    move();
                } else if (isTurning) {
                    isMoving = false;
                    turn();
                } else {
                    try {
                        switch (Type.take()) {
                        case "move":
                            speed = S.take();
                            to = T.take();
                            isMoving = true;
                            continue;
                        case "turn":
                            speed = S.take();
                            to = T.take();
                            isTurning = true;
                            continue;
                        default:
                            System.out.println("ERROR IN CODE AUTODRIVETRAIN");
                            break;
                        }
                    } catch (InterruptedException e) {
                        System.out.println("ERROR IN CODE AUTODRIVETRAIN");
                    }
            }
        }
        Data.ADEnabled = false;
    }
    public void enableC(){
        enabled=!enabled;
        if(!enabled){
            drive.drive(0, 0);
            Data.ADEnabled = false;
        }
        else
            Data.ADEnabled = true;
    }
    private void setEP(){
        pR = eR1.getPosition();
        pL = eL1.getPosition();
    }
    public void updateMove(double speed, double dist){
        if(S.isEmpty())
            S.add(0d);
        if(T.isEmpty())
            T.add(0d);
        S.add(speed);
        T.add(dist);
        Type.add("move");
    }
    public void updateTurn(double speed, double turn){
        if(S.isEmpty())
            S.add(0d);
        if(T.isEmpty())
            T.add(0d);
        S.add(speed);
        T.add(turn);
        Type.add("turn");
    }
    public void stopDrive(){
        drive.drive(0, 0);
    }
    public void clear(){
        S.clear();
        T.clear();
        drive.drive(0, 0);
    }
    private void move(){
        drive.arcadeDrive(-speed, 0);
        Data.moving = true;
        if((eR1.getPosition()-pR)*tickPerM>to||(eL1.getPosition()-pL)*tickPerM>to){
            isMoving = false;
            Data.moving = false;
        }
    }
    private void turn(){
        drive.arcadeDrive(0, speed);
        Data.turning = true;
        if(Math.abs((eR1.getPosition()-pR)+(eL1.getPosition()-pL))/2<=((to/90)*tickPer90)){
            isTurning = false;
            Data.turning = false;
        }
    }
}