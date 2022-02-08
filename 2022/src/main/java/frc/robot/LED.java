package frc.robot;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;


public class LED {
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    private double startTime;
    private Timer timer; 
    private double time;
    private int length;

    public LED(int port, int length){
        m_led = new AddressableLED(port);
        m_ledBuffer = new AddressableLEDBuffer(length);
        m_led.setLength(m_ledBuffer.getLength());
        this.length = length;

    // Set the data
        m_led.setData(m_ledBuffer);
        m_led.start();
        // Timer
        startTimeSet();  
        // Set all LED's to white
        for (int i = 0; i < m_ledBuffer.getLength(); i++){
            m_ledBuffer.setRGB(i, 255, 255,255);
        }
    }

    private void rainbow() {
        // For every pixel
    
        int m_rainbowFirstPixelHue = 150;
    
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
          // Set the value
          m_ledBuffer.setHSV(i, hue, 255, 120);
        }
        // Increase by to make the rainbow "move"
        m_rainbowFirstPixelHue += 3;
        // Check bounds
        m_rainbowFirstPixelHue %= 180;
      }

    public void startTimeSet(){
        startTime = timer.getFPGATimestamp();
    }
    
    public void updateTime(){
        time = timer.getFPGATimestamp()-startTime;
    }

    
    
    private void maroon(){
        updateTime();

        for (int i = 0; i < length/2; i++){
            if (i < time*20){
                m_ledBuffer.setRGB(i, 135, 2, 2);
                m_ledBuffer.setRGB(length/2 - i, 135, 2, 2);
            } else {
              //  m_ledBuffer.setRGB(i, 255, 255, 255);
               // m_ledBuffer.setRGB(length/2 - 1 - i, 255, 255, 255);
            }
            
            
        }
        if(time>2){
            startTimeSet();
            for(int j = 0; j <length; j++){
                m_ledBuffer.setRGB(j, 255, 255, 255);
                
            }
        }
    }
    
    public void run(){
        maroon();
     // Set the LEDs
        m_led.setData(m_ledBuffer);
    }
    
}