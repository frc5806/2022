package frc.robot;

public class driveForTime{
double startMotion

double endMotion

boolean succesion

boolean completed

boolean wasInMotion

public driveForTime(double start, double end, boolean s){
    completed=false;
    startMotion = start
    endMotion = end;
    succesion = s;
    wasInMotion = false;

}

public void run(double speed, double turn){
    if(Robot.time < endMotion && Robot.time > startMotion && succesion){
        wasInMotion = true;
        Robot.drive.drive(speed, turn);
    }
    else if(wasInMotion){
        completed=True;
    }
}

}