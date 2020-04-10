/*Editor: Johnson Gao

 * Date This Project Created:
 * Description Of This Class:Convert time between microseconds, seconds and minutes.
 */
package journeylove;

import java.util.Scanner;

/**
 *
 * @author Johnson Gao
 */
public class TimeConverter
{

    private long microSeconds;
    public static final double RATIO_MICROSEC_MIN = 0.000000016667;

    private int minutes=0;
    private int seconds=0;

    /**
     * Construct a new time converter.
     */
    public TimeConverter()
    {
        microSeconds = -1;//This is an undefined value.
    }

    /**
     * Input microseconds and convert.
     * @param microSeconds 
     */
    public TimeConverter(long microSeconds)
    {
        this.microSeconds = microSeconds;
        convertToMinutes();
    }

    /**
     * input minute and seconds.
     * @param min
     * @param sec 
     */
    public TimeConverter(int min, int sec)
    {
        minutes = min;
        seconds = sec;
        //convertToMicroseconds();
    }
    /**
     * Construct a time converter using seconds.
     * @param sec converted value
     */
    public TimeConverter (int sec)
    {
        seconds = sec;
    }

    /**
     * Convert to min.
     */
    public void convertToMinutes()
    {
        double minuteSeconds;
        double decimalSeconds;
        minuteSeconds = microSeconds * RATIO_MICROSEC_MIN;
        minutes = (int) minuteSeconds;
        decimalSeconds = minuteSeconds - minutes;
        decimalSeconds *= 60;
        seconds = (int) decimalSeconds;
    }

    /**
     * Convert to ms.
     */
    public void convertToMicroseconds()
    {
        double secondsDecimal, minutesDecimal;
        secondsDecimal = seconds / 60;
        minutesDecimal = secondsDecimal + minutes;
        microSeconds = (long) (minutesDecimal / RATIO_MICROSEC_MIN);
    }
    
/**
 * Get the microsecond value.
 * @return 
 */
    public long getMicroSeconds()
    {
        if(microSeconds == -1)
        {
            convertToMicroseconds();
        }
        return microSeconds;
    }
//setters and getters
    public void setMicroSeconds(long microSeconds)
    {
        this.microSeconds = microSeconds;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }

    public int getSeconds()
    {
        return seconds;
    }
    
    public int convertToSeconds()
    {
        return this.seconds + minutes*60;
    }
    
    

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }

    @Override
    public String toString()
    {
        return "TimeConverter{" + "microSeconds=" + microSeconds + ", minutes=" + minutes + ", seconds=" + seconds + '}';
    }

    /**
     * Test main method.
     * @param args Lines commage argument.
     */
    public static void main(String[] args)
    {
        TimeConverter timeConverter = new TimeConverter();
        Scanner keyboard = new Scanner(System.in);
        long microsecond;
        int minutes;
        int seconds;
        int decision;
        System.out.println("Welcome to test converter");

        System.out.println("Enter 1 --> microsecnds to mins\n"
                + "2--> mins to microseconds");
        decision = keyboard.nextInt();
        switch (decision)
        {
            case 1:
                System.out.println("Please enter microseconds");
                microsecond = keyboard.nextLong();
                timeConverter.setMicroSeconds(microsecond);
                timeConverter.convertToMinutes();
                System.out.println("It is " + timeConverter.getMinutes() + " : " + timeConverter.getSeconds());
                break;
            case 2:
                System.out.println("Please enter mins");
                minutes = keyboard.nextInt();
                timeConverter.setMinutes(minutes);
                System.out.println("Please enter seconds");
                seconds = keyboard.nextInt();
                timeConverter.setSeconds(seconds);
                timeConverter.convertToMicroseconds();
                System.out.println("It is " + timeConverter.getMicroSeconds() + " microseconds");
                break;
            default:
                System.out.println("I don't know what to do.");
                break;
        }
    }

}
