package de.motivational.stairs.game.general.timestep.gpio;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import de.motivational.stairs.game.general.timestep.GameTimeStep;
import org.springframework.stereotype.Component;


import java.awt.*;

/**
 * Created by Florian on 09.01.2017.
 */
@Component
public class RaspberryPIHandler {
    private static Color ledPressColor = new Color(0,255,0);
    private static boolean unsatisfiedLinkError = false;

    private static final Pin PIN_BUTTON_R1 = RaspiPin.GPIO_30; // Unten 7
    private static final Pin PIN_BUTTON_R2 = RaspiPin.GPIO_21;
    private static final Pin PIN_BUTTON_R3 = RaspiPin.GPIO_22;
    private static final Pin PIN_BUTTON_L1 = RaspiPin.GPIO_23;
    private static final Pin PIN_BUTTON_L2 = RaspiPin.GPIO_24;
    private static final Pin PIN_BUTTON_L3 = RaspiPin.GPIO_25; // Unten 2

    private static final Pin PIN_CLOCK_LEFT= RaspiPin.GPIO_00;
    private static final Pin PIN_DATA_LEFT = RaspiPin.GPIO_09;
    private static final Pin PIN_CLOCK_RIGHT = RaspiPin.GPIO_02;
    private static final Pin PIN_DATA_RIGHT = RaspiPin.GPIO_07;

    //Should be final but won't work with error handling...
    private static GpioPinDigitalInput GPIO_BUTTON_R1;
    private static GpioPinDigitalInput GPIO_BUTTON_R2;
    private static GpioPinDigitalInput GPIO_BUTTON_R3;
    private static GpioPinDigitalInput GPIO_BUTTON_L1;
    private static GpioPinDigitalInput GPIO_BUTTON_L2;
    private static GpioPinDigitalInput GPIO_BUTTON_L3;

    private static GpioPinDigitalOutput dataPinLeft;
    private static GpioPinDigitalOutput clockPinLeft;
    private static LPD6803 ledStripLeft;

    private static GpioPinDigitalOutput dataPinRight;
    private static GpioPinDigitalOutput clockPinRight;
    private static LPD6803 ledStripRight;

    private GameTimeStep gameTimeStep;

    static{
        try{
            GpioController gpio = GpioFactory.getInstance();
            GPIO_BUTTON_R1 = gpio.provisionDigitalInputPin(PIN_BUTTON_R1, PinPullResistance.PULL_UP);
            GPIO_BUTTON_R2 = gpio.provisionDigitalInputPin(PIN_BUTTON_R2, PinPullResistance.PULL_UP);
            GPIO_BUTTON_R3 = gpio.provisionDigitalInputPin(PIN_BUTTON_R3, PinPullResistance.PULL_UP);
            GPIO_BUTTON_L1 = gpio.provisionDigitalInputPin(PIN_BUTTON_L1, PinPullResistance.PULL_UP);
            GPIO_BUTTON_L2 = gpio.provisionDigitalInputPin(PIN_BUTTON_L2, PinPullResistance.PULL_UP);
            GPIO_BUTTON_L3 = gpio.provisionDigitalInputPin(PIN_BUTTON_L3, PinPullResistance.PULL_UP);

            dataPinLeft = gpio.provisionDigitalOutputPin(PIN_DATA_LEFT, "dataLeft", PinState.LOW);
            clockPinLeft = gpio.provisionDigitalOutputPin(PIN_CLOCK_LEFT, "clockLeft", PinState.LOW);
            ledStripLeft = new LPD6803(dataPinLeft, clockPinLeft, 3);

            dataPinRight= gpio.provisionDigitalOutputPin(PIN_DATA_RIGHT, "dataRight", PinState.LOW);
            clockPinRight = gpio.provisionDigitalOutputPin(PIN_CLOCK_RIGHT, "clockRight", PinState.LOW);
            ledStripRight = new LPD6803(dataPinRight, clockPinRight, 3);

        } catch (UnsatisfiedLinkError e){
            unsatisfiedLinkError = true;
            System.err.println("IllegalArgumentException: " + e.getMessage()+ ", r4j native library nicht gefunden, da Ausführung derzeit nicht auf Raspberry PI?");
        }
    }

    public RaspberryPIHandler(){

    }
    public boolean gpioError(){
        //Maybe more later, like null checks etc
        return unsatisfiedLinkError;
    }

    public void resetLedStrips(){
        resetLeftLedStrip();
        resetRightLedStrip();
    }
    public void resetLeftLedStrip(){
        resetLedStripHelper(ledStripLeft);
    }
    public void resetRightLedStrip(){
        resetLedStripHelper(ledStripRight);
    }
    private void resetLedStripHelper(LPD6803 ledStrip){
        if(!gpioError()) {
            byte O = 0;
            ledStrip.setPixelColor(0, O, O, O, false);
            ledStrip.setPixelColor(1, O, O, O, false);
            ledStrip.setPixelColor(2, O, O, O, true);
        }
    }

    public void setAllColorLedStripLeft(Color c){
        setColorLedStripHelper(ledStripLeft, c);
    }

    public void setAllColorLedStripRight(Color c){
        setColorLedStripHelper(ledStripRight, c);
    }

    private void setColorLedStripHelper(LPD6803 ledStrip, Color c){
        if(!gpioError()) {
            byte r = (byte) (c.getRed() / 4);
            byte g = (byte) (c.getGreen() / 4);
            byte b = (byte) (c.getBlue() / 4);

            ledStrip.setPixelColor(0, r, g, b, false);
            ledStrip.setPixelColor(1, r, g, b, false);
            ledStrip.setPixelColor(2, r, g, b, true);
        }
    }

    private void resetSingleLed(int pos, LPD6803 ledStrip){
        if(!gpioError()) {
            byte O = (byte)0;
            ledStrip.setPixelColor(pos, O, O, O, true);
        }
    }

    private void setSingleLed(int pos, LPD6803 ledStrip, Color c){
        if(!gpioError()) {
            byte r = (byte) (c.getRed() / 4);
            byte g = (byte) (c.getGreen() / 4);
            byte b = (byte) (c.getBlue() / 4);
            ledStrip.setPixelColor(pos, r, g, b, true);
        }
    }

    public void setInputHandlers(GameTimeStep gameTimeStep){
        this.gameTimeStep = gameTimeStep;

        if(!gpioError()) {
            resetLedStrips();

            GPIO_BUTTON_R1.removeAllListeners();
            GPIO_BUTTON_R2.removeAllListeners();
            GPIO_BUTTON_R3.removeAllListeners();
            GPIO_BUTTON_L1.removeAllListeners();
            GPIO_BUTTON_L2.removeAllListeners();
            GPIO_BUTTON_L3.removeAllListeners();

            GPIO_BUTTON_R1.addListener((GpioPinListenerDigital) event -> {
                if (event.getState().isLow()) {
                    gameTimeStep.getGameInputListener().buttonR1Pressed();
                    setSingleLed(0, ledStripRight, ledPressColor);
                } else {
                    gameTimeStep.getGameInputListener().buttonR1Released();
                    resetSingleLed(0, ledStripRight);
                }
            });

            GPIO_BUTTON_R2.addListener((GpioPinListenerDigital) event -> {
                if (event.getState().isLow()) {
                    gameTimeStep.getGameInputListener().buttonR2Pressed();
                    setSingleLed(1, ledStripRight, ledPressColor);
                } else {
                    gameTimeStep.getGameInputListener().buttonR2Released();
                    resetSingleLed(1, ledStripRight);
                }
            });

            GPIO_BUTTON_R3.addListener((GpioPinListenerDigital) event -> {
                if (event.getState().isLow()) {
                    gameTimeStep.getGameInputListener().buttonR3Pressed();
                    setSingleLed(2, ledStripRight, ledPressColor);
                } else {
                    gameTimeStep.getGameInputListener().buttonR3Released();
                    resetSingleLed(2, ledStripRight);
                }
            });


            GPIO_BUTTON_L1.addListener((GpioPinListenerDigital) event -> {
                if (event.getState().isLow()) {
                    gameTimeStep.getGameInputListener().buttonL1Pressed();
                    setSingleLed(0, ledStripLeft, ledPressColor);
                } else {
                    gameTimeStep.getGameInputListener().buttonL1Released();
                    resetSingleLed(0, ledStripLeft);
                }
            });

            GPIO_BUTTON_L2.addListener((GpioPinListenerDigital) event -> {
                if (event.getState().isLow()) {
                    gameTimeStep.getGameInputListener().buttonL2Pressed();
                    setSingleLed(1, ledStripLeft, ledPressColor);
                } else {
                    gameTimeStep.getGameInputListener().buttonL2Released();
                    resetSingleLed(1, ledStripLeft);
                }
            });

            GPIO_BUTTON_L3.addListener((GpioPinListenerDigital) event -> {
                if (event.getState().isLow()) {
                    gameTimeStep.getGameInputListener().buttonL3Pressed();
                    setSingleLed(2, ledStripLeft, ledPressColor);
                } else {
                    gameTimeStep.getGameInputListener().buttonL3Released();
                    resetSingleLed(2, ledStripLeft);
                }
            });
        }
    }
}
