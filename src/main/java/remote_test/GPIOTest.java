package remote_test;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Created by Florian on 27.12.2016.
 */
public class GPIOTest {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("<--Pi4J--> GPIO Control Example ... started.");
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        //final GpioPinDigitalOutput pinLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.HIGH);

        // set shutdown state for this pin
        //pinLed.setShutdownOptions(true, PinState.LOW);

        System.out.println("--> GPIO state should be: ON");

        //Thread.sleep(5000);

        // turn off gpio pin #01
        //pinLed.low();
        System.out.println("--> GPIO state should be: OFF");

        //Thread.sleep(5000);

        // toggle the current state of gpio pin #01 (should turn on)
        //pinLed.toggle();
        System.out.println("--> GPIO state should be: ON");

        //Thread.sleep(5000);

        // toggle the current state of gpio pin #01  (should turn off)
        //pinLed.toggle();
        System.out.println("--> GPIO state should be: OFF");

        //Thread.sleep(5000);

        // turn on gpio pin #01 for 1 second and then off
        System.out.println("--> GPIO state should be: ON for only 1 second");
        //pinLed.pulse(1000, true); // set second argument to 'true' use a blocking call

        System.out.println("Exiting ControlGpioExample");

        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_30, PinPullResistance.PULL_UP);

        // set shutdown state for this input pin

        myButton.setShutdownOptions(true);

        GpioPinDigitalOutput dataPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "data", PinState.LOW);
        GpioPinDigitalOutput clockPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "clock", PinState.LOW);
        LPD6803 ledStrip = new LPD6803(dataPin, clockPin, 2);
        ledStrip.setPixelColor(0, (byte)127, (byte)0, (byte)0);
        //myButton.setDebounce(120);
        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());

                if(event.getState().isLow()) {
                    byte r = (byte)(((Math.random() + 0) / 1) * 255);
                    byte g = (byte)(((Math.random() + 0) / 1) * 255);
                    byte b = (byte)(((Math.random() + 0) / 1) * 255);
                    r = (byte)0;
                    g = (byte)0;
                    b = (byte)126;
                    ledStrip.setPixelColor(0, r, g, b);
                    System.out.printf("%d, %d, %d\n", r, g, b);
                } else {
                    ledStrip.setPixelColor(0, (byte)0, (byte)0, (byte)0);
                }
            }

        });

        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)

        //ledStrip.setPixelColor(0, (byte)255, (byte)0, (byte)0);
        //ledStrip.setPixelColor(1, (byte)0, (byte)0, (byte)0);

        byte R = 0;
        byte G = 0;
        double num = 0;
        while(true) {
            //
            Thread.sleep(1000);
            //ledStrip.setPixelColor(0, (byte)255, (byte)0, (byte)0);
            /*
            ledStrip.writeLeds();
            testWait();
            if(ledStrip.isDone()) {
                num += 0.074;
                R = (byte)(((Math.random() + 0) / 1) * 255);
                G = (byte)(((Math.random() + 0) / 1) * 255);

                ledStrip.setPixelColor(1, (byte)(((Math.random() + 0) / 1) * 255), (byte)(((Math.random() + 0) / 1) * 255), (byte)(((Math.random() + 0) / 1) * 255));
                ledStrip.show();
            }
            */
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpioBtn.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }

    public static void testWait(){
        final long INTERVAL = 1800000;
        long start = System.nanoTime();
        long end=0;
        do{
            end = System.nanoTime();
        }while(start + INTERVAL >= end);
    }
}
