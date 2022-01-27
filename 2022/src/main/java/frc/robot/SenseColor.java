package frc.robot;



import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.sql.Time;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;


public class SenseColor{

    Color detectedColor;
    double IR;
    private I2C.Port i2cPort = I2C.Port.kOnboard;
    private ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    public SenseColor(){
        
    }

    public void updateValues(){
        detectedColor = colorSensor.getColor();
    }

    public boolean seeingColor(String color, double threshold){
        if(color == "blue"){
            return detectedColor.blue > threshold;
        }
        else if(color == "green"){
            return detectedColor.green > threshold;
        }
        else{
            return detectedColor.red > threshold;
        }
    }
}