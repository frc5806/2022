package frc.robot;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;

import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb {
    private static Solenoid climbenoid1;
    private static Solenoid climbenoid2;

    private static CANSparkMax winchold1;
    private static CANSparkMax winchold2;

    private static Compressor compressor;

    public Climb(int position1, int position2, int victor1, int victor2) {
        climbenoid1 = new Solenoid(PneumaticsModuleType.CTREPCM, position1); // 7
        climbenoid2 = new Solenoid(PneumaticsModuleType.CTREPCM, position2); // 4
        winchold1 = new CANSparkMax(victor1, MotorType.kBrushless);
        winchold2 = new CANSparkMax(victor2, MotorType.kBrushless);
        climbenoid1.set(false);
        climbenoid2.set(false);
        
        

    }

    public void enableComp(){
        compressor.enableHybrid(40, 80);
    }

    public void winchIn(){
        winchold1.set(-1);
        winchold2.set(1);
    }

    public void winchOut(){
        winchold1.set(1);
        winchold2.set(-1);
    }

    public void winch(double speed){
        winchold1.set(speed);
        winchold2.set(-speed);
    }
    public void winchStop(){
        winchold1.set(0);
        winchold2.follow(winchold1);
    }

    public void armForward() {
        climbenoid1.set(true);
        climbenoid2.set(true);
    }
    
    public void armBackward() {
        climbenoid1.set(false);
        climbenoid2.set(false);
    }
}