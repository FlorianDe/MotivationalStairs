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
        final GpioPinDigitalOutput pinLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.HIGH);

        // set shutdown state for this pin
        pinLed.setShutdownOptions(true, PinState.LOW);

        System.out.println("--> GPIO state should be: ON");

        //Thread.sleep(5000);

        // turn off gpio pin #01
        pinLed.low();
        System.out.println("--> GPIO state should be: OFF");

        //Thread.sleep(5000);

        // toggle the current state of gpio pin #01 (should turn on)
        pinLed.toggle();
        System.out.println("--> GPIO state should be: ON");

        //Thread.sleep(5000);

        // toggle the current state of gpio pin #01  (should turn off)
        pinLed.toggle();
        System.out.println("--> GPIO state should be: OFF");

        //Thread.sleep(5000);

        // turn on gpio pin #01 for 1 second and then off
        System.out.println("--> GPIO state should be: ON for only 1 second");
        pinLed.pulse(1000, true); // set second argument to 'true' use a blocking call

        System.out.println("Exiting ControlGpioExample");

        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        myButton.setShutdownOptions(true);

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
            }

        });

        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        GpioPinDigitalOutput dataPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "data", PinState.LOW);
        GpioPinDigitalOutput clockPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "clock", PinState.LOW);
        LPD6803 ledStrip = new LPD6803(dataPin, clockPin, 2);
        ledStrip.setPixelColor(0, (byte)255, (byte)0, (byte)0);
        ledStrip.setPixelColor(1, (byte)0, (byte)255, (byte)0);
        ledStrip.setPixelColor(2, (byte)0, (byte)0, (byte)255);

        byte color = 0;
        while(true) {
            ledStrip.writeLeds();
            Thread.sleep(0);
            if(ledStrip.isDone()) {
                System.out.println("set color " + color);
                color++;
                if(color > 125) {
                    color = 0;
                }
                ledStrip.setPixelColor(2, (byte)color, (byte)0, (byte)0);
                ledStrip.setPixelColor(1, (byte)0, (byte)color, (byte)0);
                ledStrip.setPixelColor(0, (byte)0, (byte)0, (byte)color);
                ledStrip.show();
            }
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpioBtn.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
}
