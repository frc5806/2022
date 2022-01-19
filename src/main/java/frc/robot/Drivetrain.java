package frc.robot;
import com.revrobotics.CANPIDController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain implements PIDDrivetrainData{
    public VictorSPX driveR1;
    public VictorSPX driveR2;
    public VictorSPX driveL1;
    public VictorSPX driveL2;
    SpeedControllerGroup left;
    SpeedControllerGroup right;
    DifferentialDrive drive;
    public Drivetrain(int CANIDSR1, int CANIDSR2, int CANIDSL1, int CANIDSL2){
        driveR1 = new VictorSPX(CANIDSR1, MotorType.kBrushless);
        driveR2 = new VictorSPX(CANIDSR2, MotorType.kBrushless);
        driveL1 = new VictorSPX(CANIDSL1, MotorType.kBrushless);
        driveL2 = new VictorSPX(CANIDSL2, MotorType.kBrushless);



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