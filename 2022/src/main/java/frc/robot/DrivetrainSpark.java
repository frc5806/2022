package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DrivetrainSpark {

    public DifferentialDrive drive;
    
    public CANSparkMax driveR1;
    public CANSparkMax driveR2;
    public CANSparkMax driveR3;
    public CANSparkMax driveL1;
    public CANSparkMax driveL2;
    public CANSparkMax driveL3;

    public MotorControllerGroup left;
    public MotorControllerGroup right;
    
    public DrivetrainSpark(int CANIDSR1, int CANIDSR2, int CANIDSR3, int CANIDSL1, int CANIDSL2, int CANIDSL3) {
        driveR1 = new CANSparkMax(CANIDSR1, MotorType.kBrushless);
        driveR2 = new CANSparkMax(CANIDSR2, MotorType.kBrushless);
        driveR3 = new CANSparkMax(CANIDSR3, MotorType.kBrushless);
        driveL1 = new CANSparkMax(CANIDSL1, MotorType.kBrushless);
        driveL2 = new CANSparkMax(CANIDSL2, MotorType.kBrushless);
        driveL3 = new CANSparkMax(CANIDSL3, MotorType.kBrushless);
        driveR1.setInverted(true);
        driveR2.setInverted(true);
        driveR3.setInverted(true);
        
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



}
