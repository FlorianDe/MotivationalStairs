package de.motivational.stairs.game.general.timestep.gpio;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import de.motivational.stairs.game.general.timestep.GameTimeStep;

/**
 * Created by Florian on 09.01.2017.
 */
public class RaspberryPIHandler {
    static final Pin PIN_BUTTON_R1 = RaspiPin.GPIO_30; // Unten 7
    static final Pin PIN_BUTTON_R2 = RaspiPin.GPIO_21;
    static final Pin PIN_BUTTON_R3 = RaspiPin.GPIO_22;
    static final Pin PIN_BUTTON_L1 = RaspiPin.GPIO_23;
    static final Pin PIN_BUTTON_L2 = RaspiPin.GPIO_24;
    static final Pin PIN_BUTTON_L3 = RaspiPin.GPIO_25; // Unten 2

    static final GpioPinDigitalInput GPIO_BUTTON_R1 = GpioFactory.getInstance().provisionDigitalInputPin(PIN_BUTTON_R1, PinPullResistance.PULL_UP);
    static final GpioPinDigitalInput GPIO_BUTTON_R2 = GpioFactory.getInstance().provisionDigitalInputPin(PIN_BUTTON_R2, PinPullResistance.PULL_UP);
    static final GpioPinDigitalInput GPIO_BUTTON_R3 = GpioFactory.getInstance().provisionDigitalInputPin(PIN_BUTTON_R3, PinPullResistance.PULL_UP);
    static final GpioPinDigitalInput GPIO_BUTTON_L1 = GpioFactory.getInstance().provisionDigitalInputPin(PIN_BUTTON_L1, PinPullResistance.PULL_UP);
    static final GpioPinDigitalInput GPIO_BUTTON_L2 = GpioFactory.getInstance().provisionDigitalInputPin(PIN_BUTTON_L2, PinPullResistance.PULL_UP);
    static final GpioPinDigitalInput GPIO_BUTTON_L3 = GpioFactory.getInstance().provisionDigitalInputPin(PIN_BUTTON_L3, PinPullResistance.PULL_UP);

    GameTimeStep gameTimeStep;

    public RaspberryPIHandler(GameTimeStep gameTimeStep){
        this.gameTimeStep = gameTimeStep;
        this.addInputHandlers();
    }

    void addInputHandlers(){
        GPIO_BUTTON_R1.removeAllListeners();
        GPIO_BUTTON_R2.removeAllListeners();
        GPIO_BUTTON_R3.removeAllListeners();
        GPIO_BUTTON_L1.removeAllListeners();
        GPIO_BUTTON_L2.removeAllListeners();
        GPIO_BUTTON_L3.removeAllListeners();

        GPIO_BUTTON_R1.addListener((GpioPinListenerDigital) event -> {
            if(event.getState().isLow()) {
                gameTimeStep.getGameInputListener().buttonR1Pressed();
            } else {
                gameTimeStep.getGameInputListener().buttonR1Released();
            }
        });

        GPIO_BUTTON_R2.addListener((GpioPinListenerDigital) event -> {
            if(event.getState().isLow()) {
                gameTimeStep.getGameInputListener().buttonR2Pressed();
            } else {
                gameTimeStep.getGameInputListener().buttonR2Released();
            }
        });

        GPIO_BUTTON_R3.addListener((GpioPinListenerDigital) event -> {
            if(event.getState().isLow()) {
                gameTimeStep.getGameInputListener().buttonR3Pressed();
            } else {
                gameTimeStep.getGameInputListener().buttonR3Released();
            }
        });



        GPIO_BUTTON_L1.addListener((GpioPinListenerDigital) event -> {
            if(event.getState().isLow()) {
                gameTimeStep.getGameInputListener().buttonL1Pressed();
            } else {
                gameTimeStep.getGameInputListener().buttonL1Released();
            }
        });

        GPIO_BUTTON_L2.addListener((GpioPinListenerDigital) event -> {
            if(event.getState().isLow()) {
                gameTimeStep.getGameInputListener().buttonL2Pressed();
            } else {
                gameTimeStep.getGameInputListener().buttonL2Released();
            }
        });

        GPIO_BUTTON_L3.addListener((GpioPinListenerDigital) event -> {
            if(event.getState().isLow()) {
                gameTimeStep.getGameInputListener().buttonL3Pressed();
            } else {
                gameTimeStep.getGameInputListener().buttonL3Released();
            }
        });
    }
}
