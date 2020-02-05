package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

final class Turret{
    TalonSRX turretM;
    public Turret(int CANIDT){
        turretM = new TalonSRX(CANIDT);
    }
}