package remote_test;

import com.pi4j.wiringpi.Spi;

/**
 * Created by Florian on 27.12.2016.
 */
public class LedTest {
    public static void main(String[] args) {
        // setup SPI for communication with the led strip.
        int fd = Spi.wiringPiSPISetup(0, 24000000);
        if (fd <= -1) {
            System.out.println("SPI initialization FAILED.");
            return;
        }
        System.out.println("SPI initialization SUCCEEDED.");


        // Test proper working of a led strip.
        // The led strip in this example has 12 leds.
        final LedStrip ledStrip = new LedStrip(2, 0.5F);
        try {
            System.out.println("Start testing led strip.");

            ledStrip.testStrip();

            Thread.sleep(1000);

            //animate(ledStrip);

            Thread.sleep(4000);

            ledStrip.allOff();
            ledStrip.update();

            System.out.println("Test finished.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
