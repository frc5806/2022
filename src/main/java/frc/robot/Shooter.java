package frc.robot;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

final class Shooter implements PIDShooterData{
    CANSparkMax shooterM1;
    CANSparkMax shooterM2;
    CANPIDController PIDShooterM1;
    CANPIDController PIDShooterM2;
    public Shooter(int CANIDS1, int CANIDS2){
        shooterM1 = new CANSparkMax(CANIDS1,MotorType.kBrushless);
        shooterM2 = new CANSparkMax(CANIDS2,MotorType.kBrushless);
        PIDShooterM1 = new CANPIDController(shooterM1);
        PIDShooterM2 = new CANPIDController(shooterM2);

        PIDShooterM1.setFF(gFF+tIM1FF);
        PIDShooterM1.setP(gP+tIM1P);
        PIDShooterM1.setI(gI+tIM1P);

        PIDShooterM2.setFF(gFF+tIM2FF);
        PIDShooterM2.setP(gP+tIM2P);
        PIDShooterM2.setI(gI+tIM2I);
    }
    public void spinUp(double speed){
        shooterM1.set(speed);
        shooterM2.follow(shooterM1);
    }
    public void spinDown(){
        shooterM1.set(0);
        shooterM2.follow(shooterM1);
    }
}