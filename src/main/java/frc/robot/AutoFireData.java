package frc.robot;
interface AutoFireData extends RobotData{
    final double maxTRSpeed = 1;
    final int visionBound = 320;
    default double dToBound() {
        return 0;
    }
    default double toSpeed(){
        return (Math.atan(Math.toRadians((Data.d+radToCam)/Data.roboVel))/360)*(2*Math.PI*radToCam);
    }
}