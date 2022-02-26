package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DrivetrainSpark {

    static public DifferentialDrive drive;
    
    static public CANSparkMax driveR1;
    static public CANSparkMax driveR2;
    static public CANSparkMax driveR3;
    static public CANSparkMax driveL1;
    static public CANSparkMax driveL2;
    static public CANSparkMax driveL3;

    static public MotorControllerGroup left;
    static public MotorControllerGroup right;
    
    public DrivetrainSpark(int CANIDSR1, int CANIDSR2, int CANIDSR3, int CANIDSL1, int CANIDSL2, int CANIDSL3) {
        driveR1 = new CANSparkMax(CANIDSR1, MotorType.kBrushless); // 3
        driveR2 = new CANSparkMax(CANIDSR2, MotorType.kBrushless); // 5
        driveR3 = new CANSparkMax(CANIDSR3, MotorType.kBrushless); // 9
        driveL1 = new CANSparkMax(CANIDSL1, MotorType.kBrushless); // 2
        driveL2 = new CANSparkMax(CANIDSL2, MotorType.kBrushless); // 4
        driveL3 = new CANSparkMax(CANIDSL3, MotorType.kBrushless); // 10
       
       
        driveR1.setInverted(false);
        driveR2.setInverted(false);
        driveR3.setInverted(false);
        driveL1.setInverted(true);
        driveL2.setInverted(true);
        driveL3.setInverted(true);
        
        right = new MotorControllerGroup(driveR1, driveR2, driveR3);
        left = new MotorControllerGroup(driveL1, driveL2, driveL3);
        drive = new DifferentialDrive(left, right);

    }

    public void arcadeDrive(double speed, double turn, boolean squareInputs){
        drive.arcadeDrive(speed, turn, squareInputs);
    }
    public void drive(double speed, double turn, boolean inPlace){
        drive.curvatureDrive(speed, turn, inPlace);
    }
    public void safteyDrive(){
        arcadeDrive(0, 0, true);
    }

  /*  public void testDrive( double speed){
        driveL1.set(speed);
        driveL2.set(speed);
        driveL3.set(speed);

        driveR1.set(speed);
       driveR2.set(speed);
        driveR3.set(speed);
    } */

    public void driveDistance(){
        
    }



}
