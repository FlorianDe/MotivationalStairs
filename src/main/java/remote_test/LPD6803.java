package remote_test;


import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * Created by Florian on 27.12.2016.
 */
public class LPD6803 {
    private final GpioPinDigitalOutput dataPin;
    private final GpioPinDigitalOutput clockPin;
    private final int[] pixels;

    // state
    private SendMode mode;
    private short swapAsap = 0;
    private byte lastData = 0;
    private byte blankCounter = 0;
    private int ledIndex = 0;
    private byte bitCount = 0;

    private enum SendMode {
        START,
        HEADER,
        DATA,
        DONE
    }

    public LPD6803(GpioPinDigitalOutput dataPin, GpioPinDigitalOutput clockPin, int count) {
        this.dataPin = dataPin;
        this.clockPin = clockPin;
        this.pixels = new int[count];

        for(int i = 0; i < count; i++) {
            this.setPixelColor(i, (byte)0, (byte)0, (byte)0);
        }

        this.mode = SendMode.START;
    }

    public void show() {
        this.mode = SendMode.START;
        while(this.mode != SendMode.DONE) {
            this.writeLeds();
        }
    }
    public void writeLeds() {
        switch (this.mode) {
            case DONE:
                //this.mode = SendMode.START;
                if (swapAsap>0) {
                    if(blankCounter == 0)    //AS SOON AS CURRENT pwm IS DONE. BlankCounter
                    {
                        bitCount = 0;
                        ledIndex = swapAsap;  //set current led
                        mode = SendMode.HEADER;
                        swapAsap = 0;
                    }
                }
                break;
            case DATA:
                if (((1 << (15-bitCount)) & pixels[ledIndex]) != 0) {
                    if (lastData == 0) {     // digitalwrites take a long time, avoid if possible
                        // If not the first bit then output the next bits
                        // (Starting with MSB bit 15 down.)
                        dataPin.high();
                        lastData = 1;
                    }
                } else {
                    if (lastData != 0) {       // digitalwrites take a long time, avoid if possible
                        dataPin.low();
                        lastData = 0;
                    }
                }
                bitCount++;

                if(bitCount == 16)    //Last bit?
                {
                    ledIndex++;        //Move to next LED
                    if (ledIndex < this.pixels.length) //Still more leds to go or are we done?
                    {
                        bitCount=0;      //Start from the fist bit of the next LED
                    } else {
                        // no longer sending data, set the data pin low
                        dataPin.low();
                        lastData = 0; // this is a lite optimization
                        mode = SendMode.DONE;  //No more LEDs to go, we are done!
                    }
                }
                break;
            case HEADER:
                if (bitCount < 32) {
                    dataPin.low();
                    lastData = 0;
                    bitCount++;
                    if (bitCount==32) {
                        mode = SendMode.DATA;      //If this was the last bit of header then move on to data.
                        ledIndex = 0;
                        bitCount = 0;
                    }
                }
                break;
            case START:
                if (blankCounter == 0)    //AS SOON AS CURRENT pwm IS DONE. BlankCounter
                {
                    bitCount = 0;
                    ledIndex = 0;
                    mode = SendMode.HEADER;
                }
                break;
        }

        this.clockPin.high();
        this.clockPin.low();

        this.blankCounter++;
    }

    public void setPixelColor(int position, byte b, byte g, byte r) {
        this.setPixelColor(position, b, g, r, true);
    }

    public void setPixelColor(int position, byte b, byte g, byte r, boolean direct) {
        int data;

        if( position >= this.pixels.length)
            return;

        r = (byte)Math.floor((float)r / 2);
        g = (byte)Math.floor((float)g / 2);
        b = (byte)Math.floor((float)b / 2);

        data = g & 0x1F;
        data <<= 5;
        data |= b & 0x1F;
        data <<= 5;
        data |= r & 0x1F;
        data |= 0x8000;

        this.pixels[position] = data;

        if(direct) {
            this.show();
        }
    }
}
