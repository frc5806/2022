package frc.robot;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain{
    CANSparkMax driveR1;
    CANSparkMax driveR2;
    CANSparkMax driveL1;
    CANSparkMax driveL2;
    CANPIDController PIDdriveR1;
    CANPIDController PIDdriveR2;
    CANPIDController PIDdriveL1;
    CANPIDController PIDdriveL2; 
    SpeedControllerGroup left;
    SpeedControllerGroup right;
    DifferentialDrive drive;
    public DriveTrain(int CANIDS1, int CANIDS2, int CANIDS3, int CANIDS4){
        driveR1 = new CANSparkMax(CANIDS1, MotorType.kBrushless);
        driveR2 = new CANSparkMax(CANIDS2, MotorType.kBrushless);
        driveL1 = new CANSparkMax(CANIDS3, MotorType.kBrushless);
        driveL2 = new CANSparkMax(CANIDS4, MotorType.kBrushless);

        PIDdriveR1 = new CANPIDController(driveR1);
        PIDdriveR2 = new CANPIDController(driveR2);
        PIDdriveL1 = new CANPIDController(driveL1);
        PIDdriveL2 = new CANPIDController(driveL2);

        PIDdriveR1.setFF(0);
        PIDdriveR1.setP(0);
        PIDdriveR1.setI(0);

        PIDdriveR2.setFF(0);
        PIDdriveR2.setP(0);
        PIDdriveR2.setI(0);

        PIDdriveL1.setFF(0);
        PIDdriveL1.setP(0);
        PIDdriveL1.setI(0);

        PIDdriveL2.setFF(0);
        PIDdriveL2.setP(0);
        PIDdriveL2.setI(0);

        right = new SpeedControllerGroup(driveR1, driveR2);
        left = new SpeedControllerGroup(driveL1, driveL2);
        drive = new DifferentialDrive(left, right);
    }
    public void drive(double speed, double turn){
        drive.curvatureDrive(speed, turn, speed<.15);
    }
}