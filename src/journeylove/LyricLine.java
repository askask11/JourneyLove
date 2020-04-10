/*Editor: Johnson Gao
 * Date This Project Created: June 24 2019
 * Description Of This Class: Receives a normal line of a lyric .lrc file and transfer data into position and words.
 */
package journeylove;

/**
 * Receives a normal line of a lyric .lrc file and transfer data into position
 * and words.
 *
 * @author Johnson Gao
 */
public class LyricLine
{

    /**
     * The microsecond position of this line.
     */
    private long microsecondPosition = -1;

    /**
     * The complete line without any analyzing.
     */
    private String fullLine = "";

    /**
     * The word-only part of the line.
     */
    private String lyric = "";

    private int minuteLength = -1;

    private double secondLength = -1;

    public static final int SECOND_MICROSECOND_RATIO = 1000000, MINUTE_SECONDS = 60;

    /**
     * Construct a line of lyric with specific lyric;
     *
     * @param fullLyric
     */
    public LyricLine(String fullLyric)
    {
        this.fullLine = fullLyric;
        readLine();
    }

    /**
     * Construct a new lyric linem with given minute length, second length and
     * lyric words.
     *
     * @param minuteLength The "minute" part of the position.
     * @param secondLength The "socond"part of the positon.
     * @param lyric The word part of the lyric.
     */
    public LyricLine(int minuteLength, double secondLength, String lyric)
    {
        this.minuteLength = minuteLength;
        this.secondLength = secondLength;
        this.lyric = lyric;
        microsecondPosition = (minuteLength * MINUTE_SECONDS * SECOND_MICROSECOND_RATIO) + (long) (secondLength * SECOND_MICROSECOND_RATIO);
    }

    /**
     * Construct a new empty lyric line with no position.
     */
    public LyricLine()
    {

    }

    /**
     * Read lyric time and words from a read lrc line.
     */
    public void readLine()
    {
        if (!fullLine.isEmpty() && fullLine.startsWith("["))
        {
            String time = fullLine.substring(fullLine.indexOf("[") + 1, fullLine.indexOf("]"));
            String[] timecore = time.split(":");
            lyric = fullLine.substring(fullLine.lastIndexOf("]") + 1, fullLine.length());
            minuteLength = Integer.parseInt(timecore[0]);
            secondLength = Double.parseDouble(timecore[1]);
            microsecondPosition = (minuteLength * MINUTE_SECONDS * SECOND_MICROSECOND_RATIO) + (long) (secondLength * SECOND_MICROSECOND_RATIO);
        } else
        {
            throw new IllegalArgumentException("Full lyric is empty or wrong! full lyric: " + fullLine);
        }

    }

    /**
     * Get the microsecond position of this line.
     *
     * @return The position of this line.
     */
    public long getMicrosecondPosition()
    {
        return microsecondPosition;
    }

    /**
     * Manually define the position of this line.
     *
     * @param microsecondPosition The define position of this line.
     */
    public void setMicrosecondPosition(long microsecondPosition)
    {
        this.microsecondPosition = microsecondPosition;
    }

    /**
     * Get the original text of this lyric line.
     *
     * @return The original unanalyzed line.
     */
    public String getFullLine()
    {
        return fullLine;
    }

    /**
     * Manually define the full context of this line.
     *
     * @param fullLine The manual define text of this line.
     */
    public void setFullLine(String fullLine)
    {
        this.fullLine = fullLine;
    }

    /**
     * Get only the word part of the lyric line received.
     *
     * @return The word only part of the lyric line.
     */
    public String getLyric()
    {
        return lyric;
    }

    /**
     * Set the word part of the lyricline.
     *
     * @param lyric words
     */
    public void setLyric(String lyric)
    {
        this.lyric = lyric;
    }

    /**
     * Get the current position, minute part.
     *
     * @return the minute part of current position.
     */
    public int getMinuteLength()
    {
        return minuteLength;
    }

    /**
     * Set the current position, minute part.
     *
     * @param minuteLength the minute part of current position.
     */
    public void setMinuteLength(int minuteLength)
    {
        this.minuteLength = minuteLength;
    }

    /**
     * Get the second part of the line position.
     *
     * @return The second part of the line position.
     */
    public double getSecondLength()
    {
        return secondLength;
    }

    /**
     * Set the second part of the current position.
     *
     * @param secondLength The seconds position of this line.
     */
    public void setSecondLength(double secondLength)
    {
        this.secondLength = secondLength;
    }

    @Override
    public String toString()
    {
        return "LyricLine{" + "microsecondLength=" + microsecondPosition + ", fullLyric=" + fullLine + ", lyric=" + lyric + ", minuteLength=" + minuteLength + ", secondLength=" + secondLength + '}' + "\n";
    }

    /**
     * Test main method.
     *
     * @param args Lines command argument.
     */
    public static void main(String[] args)
    {
        //test main method
        LyricLine line = new LyricLine("[00:11.48]Beckons softly with its call night day");
        System.out.println(line);
    }

}
