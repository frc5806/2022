package frc.robot;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain implements PIDDrivetrainData{
    public CANSparkMax driveR1;
    public CANSparkMax driveR2;
    public CANSparkMax driveL1;
    public CANSparkMax driveL2;
    CANPIDController PIDdriveR1;
    CANPIDController PIDdriveR2;
    CANPIDController PIDdriveL1;
    CANPIDController PIDdriveL2; 
    SpeedControllerGroup left;
    SpeedControllerGroup right;
    DifferentialDrive drive;
    public Drivetrain(int CANIDSR1, int CANIDSR2, int CANIDSL1, int CANIDSL2){
        driveR1 = new CANSparkMax(CANIDSR1, MotorType.kBrushless);
        driveR2 = new CANSparkMax(CANIDSR2, MotorType.kBrushless);
        driveL1 = new CANSparkMax(CANIDSL1, MotorType.kBrushless);
        driveL2 = new CANSparkMax(CANIDSL2, MotorType.kBrushless);

        driveR1.getEncoder().setVelocityConversionFactor(0.00797964534);
        driveR2.getEncoder().setVelocityConversionFactor(0.00797964534);
        driveL1.getEncoder().setVelocityConversionFactor(0.00797964534);
        driveL2.getEncoder().setVelocityConversionFactor(0.00797964534);

        driveR1.getEncoder().setPositionConversionFactor(1.5707963267916662531);
        driveR2.getEncoder().setPositionConversionFactor(1.5707963267916662531);
        driveL1.getEncoder().setPositionConversionFactor(1.5707963267916662531);
        driveL2.getEncoder().setPositionConversionFactor(1.5707963267916662531);

        driveR1.getEncoder().setPosition(0);
        driveR2.getEncoder().setPosition(0);
        driveL1.getEncoder().setPosition(0);
        driveL2.getEncoder().setPosition(0);

        PIDdriveR1 = new CANPIDController(driveR1);
        PIDdriveR2 = new CANPIDController(driveR2);
        PIDdriveL1 = new CANPIDController(driveL1);
        PIDdriveL2 = new CANPIDController(driveL2);

        PIDdriveR1.setFF(gFF+tIM1FF);
        PIDdriveR1.setP(gP+tIM1P);
        PIDdriveR1.setI(gI+tIM1I);

        PIDdriveR2.setFF(gFF+tIM2FF);
        PIDdriveR2.setP(gP+tIM2P);
        PIDdriveR2.setI(gI+tIM2I);

        PIDdriveL1.setFF(gFF+tIM3FF);
        PIDdriveL1.setP(gFF+tIM3P);
        PIDdriveL1.setI(gFF+tIM3I);

        PIDdriveL2.setFF(gFF+tIM4FF);
        PIDdriveL2.setP(gFF+tIM4P);
        PIDdriveL2.setI(gFF+tIM4I);

        right = new SpeedControllerGroup(driveR1, driveR2);
        left = new SpeedControllerGroup(driveL1, driveL2);
        drive = new DifferentialDrive(left, right);
    }
    public void drive(double speed, double turn){
        drive.curvatureDrive(-speed, turn, speed<.15||turn<.4);
    }
    public void tankDrive(double speedR, double speedL){
        drive.tankDrive(speedL, speedR);
    }
    public void arcadeDrive(double speed, double turn){
        drive.arcadeDrive(speed, turn);
    }
    public void safteyDrive(){
        drive.arcadeDrive(0, 0);
    }
    public CANSparkMax[] getMotors(){
        return new CANSparkMax[]{driveR1, driveR2, driveL1, driveL2};
    }
}