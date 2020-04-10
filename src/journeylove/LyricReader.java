/*Editor: Johnson Gao

 * Date This Project Created:June 24 2019
 * Description Of This Class: This class receives a .lrc file and read the file, abstract .lrc into a java readable data.
 */
package journeylove;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class receives a .lrc file and read the file, abstract .lrc into a java
 * readable data.
 *
 * @author Johnson Gao
 */
public class LyricReader
{

    private String lyricTitle = "Unknown";

    private String artist = "Unknown";

    private String editor = "Unknown";

    private String album = "Unknown";

    private File lyricFile = null;

    private int currentPosition = 0;

    private LyricLine previousLine = null, currentLine = null, nextLine = null;

    private int offset = 0;

    private ArrayList<LyricLine> lines = new ArrayList<>();

    /**
     * The main constructor of a lyric reader. Will read information given from
     * the file.
     *
     * @param lrcFile
     * @throws IOException
     */
    public LyricReader(File lrcFile) throws IOException
    {
        lyricFile = lrcFile;
        readFile();
    }

    /**
     * Read the lyric file and convert the file into informations.
     *
     * @throws IOException
     */
    public void readFile() throws IOException
    {
        FileReader reader = new FileReader(lyricFile);
        String line;
        try (BufferedReader br = new BufferedReader(reader))
        {
            while ((line = br.readLine()) != null)
            {
                if (line.startsWith("[ti"))//this is the header of the lrc, since lines of the lrc is random, we can only get from here.
                {
                    lyricTitle = line.split(":")[1].replace("]", "");
                } else if (line.startsWith("[ar"))//artist
                {
                    artist = line.split(":")[1].replace("]", "");
                } else if (line.startsWith("[by"))//editor
                {
                    editor = line.split(":")[1].replace("]", "");
                } else if (line.startsWith("[al"))//album
                {
                    album = line.split(":")[1].replace("]", "");
                } else if (line.startsWith("[offset"))//offset
                {
                    offset = Integer.parseInt(line.split(":")[1].replace("]", ""));
                } else if (line.startsWith("["))//line
                {
                    LyricLine newLine = new LyricLine(line);
                    if (!newLine.getLyric().isEmpty())
                    {
                        //newLine.setLyric(" ");
                        if(offset !=0)
                        {
                            newLine.setMicrosecondPosition(newLine.getMicrosecondPosition()-offset);
                        }
                        lines.add(newLine);
                    }
                }
            }
            currentLine = lines.get(currentPosition);
            if (hasNextLine())
            {
                nextLine = lines.get(currentPosition + 1);
            }
            previousLine = new LyricLine();
            System.out.println("Total lines: " + lines.size());

        }

    }

    /**
     * Find the line number of an given position.
     *
     * @param position The time position in microsecond.
     * @return The current lyric line.
     */
    public LyricLine tracePosition(long position)
    {

        int point = 0;
//        while (position > lines.get(point).getMicrosecondPosition() && hasNextLine())
//        {
//            if(point < lines.size() - 1)
//            {
//                point++;
//            }else
//            {
//                break;
//            }
//        }
//        
        for (int i = 0; position > lines.get(point).getMicrosecondPosition() && i < lines.size() - 1; i++)
        {
            point++;
        }
        currentPosition = point;

        if (currentPosition != 0)
        {
            previousLine = lines.get(currentPosition - 1);
        } else
        {
            previousLine = new LyricLine();
        }
        currentLine = lines.get(currentPosition);

        if (hasNextLine())
        {
            nextLine = lines.get(currentPosition + 1);//7758258
        } else
        {
            nextLine = new LyricLine();
        }

        return currentLine;
    }

    /**
     * Switch the current position into the next line.
     */
    public void nextLine()
    {

        if (hasNextLine())
        {
            if (currentPosition != 0)
            {
                previousLine = lines.get(currentPosition - 1);
            }

            currentLine = lines.get(currentPosition);

            nextLine = lines.get(currentPosition + 1);

            currentPosition++;
        } else
        {
            previousLine = lines.get(lines.size() - 2);
            currentLine = lines.get(lines.size() - 1);
            nextLine = new LyricLine();
        }

//        System.out.println("journeylove.LyricReader.nextLine()");
//        System.out.println("Current line no. " + currentPosition + " ,maximum  " + (lines.size() - 1));

    }

    /**
     * See if this player has next line.
     *
     * @return Returns <code>true</code> if according to the current position,
     * the song still have next line. <code>false</code> otherwise.
     */
    public boolean hasNextLine()
    {
        boolean flag = currentPosition < lines.size() - 1;
//        System.out.println("current position = " + currentPosition + "Maximum is " + (lines.size() - 1) + "return\n"
//                + flag);
        return flag;
    }

    /**
     * Test main method.
     *
     * @param args Lines command argument.
     */
    public static void main(String[] args)
    {
        try
        {
            LyricReader lr = new LyricReader(new File("C:\\Users\\app\\Documents\\那年新塍-方糖泡泡.lrc"));
            lr.readFile();
            System.out.println(lr);
            System.out.println(lr.getLines());
        } catch (IOException ex)
        {
            Logger.getLogger(LyricReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the previous line according to the current position.
     *
     * @return The previous line of the current position.
     */
    public LyricLine getPreviousLine()
    {
        return previousLine;
    }

    /**
     * The current line of the song playing according to current position.
     *
     * @return The line indicates the current position.
     */
    public LyricLine getCurrentLine()
    {
        return currentLine;
    }

    /**
     * Ger the next line of the current position.
     *
     * @return The next line of the song.
     */
    public LyricLine getNextLine()
    {
        return nextLine;
    }

    /**
     * Get the title part of the lyric read.
     *
     * @return the lyric title part.
     */
    public String getLyricTitle()
    {
        return lyricTitle;
    }

    public void setLyricTitle(String lyricTitle)
    {
        this.lyricTitle = lyricTitle;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getEditor()
    {
        return editor;
    }

    public void setEditor(String editor)
    {
        this.editor = editor;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public File getLyricFile()
    {
        return lyricFile;
    }

    /**
     * Reset the lyric file constructed this.
     *
     * @param lyricFile The new lyric to read.
     */
    public void setLyricFile(File lyricFile)
    {
        clear();
        this.lyricFile = lyricFile;
    }

    /**
     * Set this reader into a empty state.
     */
    public void clear()
    {
        lyricTitle = "Unknown";

        artist = "Unknown";

        editor = "Unknown";

        album = "Unknown";

        lyricFile = null;

        currentPosition = 0;

        previousLine = null;
        currentLine = null;
        nextLine = null;

        offset = 0;

        lines = new ArrayList<>();

    }

    public int getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    /**
     * Override to string method.
     *
     * @return String form of this class.
     */
    @Override
    public String toString()
    {
        return "LyricReader{" + "lyricTitle=" + lyricTitle + ", artist=" + artist + ", editor=" + editor + ", album=" + album + ", lyricFile=" + lyricFile + ", currentPosition=" + currentPosition + ", currentLine=" + currentLine + ", previousLine=" + previousLine + ", nextLine=" + nextLine + ", offset=" + offset + '}';
    }

    public ArrayList<LyricLine> getLines()
    {
        return lines;
    }

}
