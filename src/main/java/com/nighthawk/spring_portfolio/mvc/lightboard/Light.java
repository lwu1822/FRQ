package com.nighthawk.spring_portfolio.mvc.lightboard;

import java.util.HashMap;
import java.util.Map;

import java.util.Random; 
import java.util.Scanner; 

import lombok.Data;
@Data  // Annotations to simplify writing code (ie constructors, setters)
public class Light {
    boolean on;
    short red;
    short green;
    short blue;
    short effect;

    Random random = new Random(); 
    Scanner sc = new Scanner(System.in); 

    /*  ANSI effects
        n	Name	Note
        0	Reset or normal	All attributes off
        1	Bold or increased intensity	As with faint, the color change is a PC (SCO / CGA) invention.[38][better source needed]
        2	Faint, decreased intensity, or dim	May be implemented as a light font weight like bold.[39]
        3	Italic	Not widely supported. Sometimes treated as inverse or blink.[38]
        4	Underline	Style extensions exist for Kitty, VTE, mintty and iTerm2.[40][41]
        5	Slow blink	Sets blinking to less than 150 times per minute
        6	Rapid blink	MS-DOS ANSI.SYS, 150+ per minute; not widely supported
        7	Reverse video or invert	Swap foreground and background colors; inconsistent emulation[42]
        8	Conceal or hide	Not widely supported.
        9	Crossed-out, or strike	Characters legible but marked as if for deletion. Not supported in Terminal.app
     */
    private final Map<Short, String> EFFECT = new HashMap<>();
    {
        // Map<"separator", not_used>
        EFFECT.put((short) 0, "Normal");
        EFFECT.put((short) 1, "Bold");
        EFFECT.put((short) 2, "Faint");
        EFFECT.put((short) 3, "Italic");
        EFFECT.put((short) 4, "Underline");
        EFFECT.put((short) 5, "Slow Blink");
        EFFECT.put((short) 6, "Fast Blink");
        EFFECT.put((short) 7, "Reverse");
        EFFECT.put((short) 8, "Conceal");
        EFFECT.put((short) 9, "Crossed_out");
    }

    /* Assign random colors and effects */
    public Light() {
        int maxColor = 255;
        int effect = 9;
        /* 
        System.out.println("Red: ");
        this.red = sc.nextShort(); 
        System.out.println("Green: ");
        this.green = sc.nextShort(); 
        System.out.println("Blue: ");
        this.blue = sc.nextShort(); 
        System.out.println("On/off: ");
        this.on = sc.nextBoolean(); 
        */
        this.effect = (short) (Math.random()*(effect+1));
        
        this.red = (short) (Math.random()*(maxColor+1));
        this.green = (short) (Math.random()*(maxColor+1));
        this.blue = (short) (Math.random()*(maxColor+1));
        this.effect = (short) (Math.random()*(effect+1));
        this.on = random.nextBoolean(); 
        
    }

    public Light(short red, short green, short blue, boolean on) {
        int maxColor = 255;
        int effect = 9;
        this.red = red; 
        this.green = green; 
        this.blue = blue; 
        this.on = on; 

        this.effect = (short) (Math.random()*(effect+1));
        /* 
        this.red = (short) (Math.random()*(maxColor+1));
        this.green = (short) (Math.random()*(maxColor+1));
        this.blue = (short) (Math.random()*(maxColor+1));
        this.effect = (short) (Math.random()*(effect+1));
        this.on = random.nextBoolean(); 
        */
    }


    public String getEffectTitle() {
        return EFFECT.get(this.effect);
    }

    public String getRGB() {
        return ( "#" +
        // IMPORTANT: %02X = convert decimal to hex, see:
        // https://mkyong.com/java/java-how-to-convert-bytes-to-hex/
         String.format("%02X", this.red) +
         String.format("%02X", this.green) + 
         String.format("%02X", this.blue) 
         );
    }

    /* toString output as key/values */
    public String toString() {
        return( "{" + 
            "\"red\": " + red + "," +
            "\"green\": " +  green + "," + 
            "\"blue\": " + blue + "," +
            "\"effect\": " + "\"" + EFFECT.get(effect) + "\"" + "," +
            "\"on\": " + on  +
            "}" );
    }

    public boolean getOn() {
        return this.on; 
    }

    static public void main(String[] args) {
        // create and display LightBoard
        Light light = new Light();
        System.out.println(light);  // use toString() method
    }
}
