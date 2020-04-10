/*Editor: Johnson Gao

 * Date This Project Created: May 2019
 * Description Of This Class: This class is an abstraction class of musics played in the player.
 */
package journeylove;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author Johnson Gao
 */
public class MyMusic
{

    // private AudioInputStream stream = null;
    private String name;
    private String url;
    /**
     * where is the url indicates?.
     * <br> <code>type = 0 </code> Undefined
     * <br> <code>type = 1</code> From Package
     * <br> <code>type = 2</code> From file path
     * <br> <code>typr = 3</code> From online.
     */
    private int type = 0;

    private int id = 0;

    private String time = "";

    public static final int TYPE_UNDEFINED = 0;

    public static final int TYPE_PACKAGE = 1;

    public static final int TYPE_LOCAL = 2;

    public static final int TYPE_ONLINE = 3;

    private String lyricAddress = "";

    public MyMusic()
    {
        name = "Test Music";
        url = "";
        type = 0;

    }

    /**
     * Create an obj only defined the url and the type of the music.
     *
     * @param url The address of the music.
     * @param type where is the url indicates?.
     * <br> <code>type = 0 </code> Undefined
     * <br> <code>type = 1</code> From Package
     * <br> <code>type = 2</code> From file path
     * <br> <code>typr = 3</code> From online inputStream.
     */
    public MyMusic(String url, int type)
    {
        this.url = url;
        this.type = type;
    }

    public MyMusic(String name, String url, int type)
    {
        this.name = name;
        this.url = url;
        this.type = type;

    }

    public MyMusic(String name)
    {
        this.name = name;
        // this.stream = stream;

    }

    /**
     * Create an background music obj with specified name, url and type.
     *
     * @param name The name of the music.
     * @param url The path of the music.
     * @param type where is the url indicates?. CANNNOT BE 0
     * <br> <code>type = 1</code> From Package
     * <br> <code>type = 2</code> From file path
     * <br> <code>typr = 3</code> From online inputStream.
     * @param id An 4 digit ID assigned to the music.
     */
    public MyMusic(String name, String url, int type, int id)
    {
        //this.stream = stream;

        this.name = name;
        this.id = id;
        switch (type)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                this.type = type;
                break;
            default:
                System.out.println("journeylove.MyMusic.<init>()");
                throw new IllegalArgumentException("Error ! unknown type!");
        }
        this.url = url;
    }

    /**
     * Creates an object of music being displayed.
     *
     * @param name Name given to the music
     * @param url The address of the music
     * @param type the type of the address.
     * @param id Assign an unique id to the music
     * @param time The total length of the music in min:sec
     */
    public MyMusic(String name, String url, int type, int id, String time)
    {
        this(name, url, type, id, time, "");
    }

    /**
     * Creates an object of music being displayed.
     *
     * @param name Name given to the music
     * @param url The address of the music
     * @param type the type of the address.
     * @param id Assign an unique id to the music
     * @param time The total length of the music in min:sec
     * @param lyricAddress address of lyric
     */
    public MyMusic(String name, String url, int type, int id, String time, String lyricAddress)
    {
        this.name = name;
        this.id = id;
        switch (type)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                this.type = type;
                break;
            default:
                System.out.println("journeylove.MyMusic.<init>()");
                throw new IllegalArgumentException("Error ! unknown type!");
        }
        this.url = url;
        this.time = time;
        this.lyricAddress = lyricAddress;
    }

    /**
     * Get the length of the music addressed.
     *
     * @return The time length of the music in form hrs:min.
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javazoom.jl.decoder.JavaLayerException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public String getTime() throws UnsupportedAudioFileException, JavaLayerException, JavaLayerException, LineUnavailableException, IOException
    {
        if (time.isEmpty())
        {
            System.out.println("journeylove.MyMusic.getTime(): Oh shoot I don't have a time, now i'm going to calculate time!");
            calculateTimeLength();
        }
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getType()
    {
        return type;
    }

    /**
     * Returns the translated type as string.
     *
     * @return the actually type of the address.
     */
    public String getTypeString()
    {
        String type;
        switch (this.getType())
        {
            case 0:
                type = "Undefined";
                break;
            case 1:
                type = "Package";
                break;
            case 2:
                type = "Local File";
                break;
            case 3:
                type = "Online";
                break;
            default:
                type = "???";
                break;
        }
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getLyricAddress()
    {
        return lyricAddress;
    }

    public void setLyricAddress(String lyricAddress)
    {
        this.lyricAddress = lyricAddress;
    }

    /**
     * Invoke if you are sure that the class already come with the stream.
     *
     * @return The input stream of the music.
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws javazoom.jl.decoder.JavaLayerException
     */
    public AudioInputStream getAudioInputStream() throws UnsupportedAudioFileException, IOException, JavaLayerException
    {
        AudioInputStream myStream = null;

        System.out.println("The music " + name + " is trying to openStream!");
        //Add protection to this class.
        switch (type)
        {
            case 1:
                myStream = getSourceInputStream(url);
                break;
            case 2:
                myStream = getLocalInputStream(url);
                break;
            case 3:
                myStream = this.getOnlineInputStream(url);
                break;
            case 0:
                System.err.println("Warning:  TYPE UNDEFIEND");
                throw new NullPointerException("Type cannot be 0");
            default:
                System.out.println("ERROR @ journeylove.BackgroundMusic.<init>()");
                throw new IllegalArgumentException("Unknown type for input type: " + type);

        }

        return myStream;
    }

    /**
     * Calculate the timelength of the music.
     *
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javazoom.jl.decoder.JavaLayerException
     * @throws javax.sound.sampled.LineUnavailableException
     * @throws java.io.IOException
     */
    public void calculateTimeLength() throws UnsupportedAudioFileException, JavaLayerException, LineUnavailableException, IOException
    {
        long microseconds;
        try (Clip myClip = AudioSystem.getClip())
        {
            myClip.open(this.getAudioInputStream());
            microseconds = myClip.getMicrosecondLength();
            TimeConverter tc = new TimeConverter(microseconds);
            time = tc.getMinutes() + " : " + tc.getSeconds();
        }
    }

    /**
     * Verify if the music does exists / can be opened.
     *
     * @return True if the music is s true music, false otherwise.
     */
    public boolean isLegal()
    {
        boolean flag;
        try
        {
            calculateTimeLength();
            flag = true;
        } catch (IOException | UnsupportedAudioFileException | NullPointerException | JavaLayerException | LineUnavailableException e)
        {
            flag = false;
            // e.printStackTrace();
        }
        //Logger.getLogger(MyMusic.class.getName()).log(Level.SEVERE, null, ex);
        return flag;
    }

    /**
     * Produce the input stream of the music, can use from online
     *
     * @param url input the address of the music
     * @return the input stream of the music.
     * @throws javax.sound.sampled.UnsupportedAudioFileException When open an
     * illegal file.
     * @throws java.io.IOException When failed to open the file.
     */
    private AudioInputStream getOnlineInputStream(String url) throws UnsupportedAudioFileException, IOException, JavaLayerException
    {

        switch (type)
        {
            case 3:

                java.net.URL myUrl = new URL(url);
                if (!url.endsWith(".wav"))
                {
                    return AudioSystem.getAudioInputStream(toWavFile(myUrl.openStream()));
                } else
                {
                    return AudioSystem.getAudioInputStream(myUrl);
                }
            default:
                System.out.println("journeylove.BackgroundMusic.getOnlineInputStream()");
                throw new IllegalArgumentException("The type in this class is not online type, please check type: " + type);
        }

    }

    /**
     * Get the input stream of the music.
     *
     * @param url
     * @param type
     * @return the input stream of the music, can be directly opened by the
     * clip.
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     */
    private AudioInputStream getLocalInputStream(String path) throws UnsupportedAudioFileException, IOException, JavaLayerException
    {

        switch (type)
        {
            case 2:
                AudioInputStream stream;

                String trimmedPath = path.trim().replace("\\", "/").replace("\"", "");
                //String codedString = new String(trimmedPath.getBytes(),"utf-8");
                File anAudioFile = new File(trimmedPath).getAbsoluteFile();
                //System.out.println("File at " + path + " Actually opeing :" + trimmedPath);
                if (!trimmedPath.endsWith(".wav"))
                {
                    return AudioSystem.getAudioInputStream(toWavFile(anAudioFile));
                } else
                {
                    return AudioSystem.getAudioInputStream(anAudioFile);
                }

            default:
                System.out.println("journeylove.BackgroundMusic.getLocalInputStream()");
                System.err.println("For input type: " + type);
                throw new IllegalArgumentException("This is not an local type, please double check. For " + type);
        }

    }

    private AudioInputStream getSourceInputStream(String pathName) throws UnsupportedAudioFileException, IOException
    {
        return AudioSystem.getAudioInputStream(getClass().getResource(pathName));
    }

    /**
     * This method receives a non-wav audio file and convert it to an .wav file.
     *
     * @param anAudioFile The audio file that needs to be converted.
     * @return The .wav file.
     * @throws IOException When failed to open the original file.
     * @throws JavaLayerException When failed to convert.
     */
    public File toWavFile(File anAudioFile) throws IOException, JavaLayerException, IllegalArgumentException
    {
        File temp = null;
        temp = File.createTempFile(" a file", ".wav");
        temp.deleteOnExit();
        Converter converter = new Converter();
        converter.convert(anAudioFile.getAbsolutePath(), temp.getAbsolutePath());
        return temp;
    }

    /**
     * This method converts an music from inputstream to .wav form.
     *
     * @param stream The inputstream of the music.
     * @return The converted music.
     * @throws IOException
     * @throws JavaLayerException
     */
    public File toWavFile(InputStream stream) throws IOException, JavaLayerException
    {
        File temp = null;
        temp = File.createTempFile(" new", ".wav");
        temp.deleteOnExit();
        Converter converter = new Converter();
        converter.convert(stream, temp.getAbsolutePath(), null, new Decoder.Params());
        return temp;
    }

    @Override
    public String toString()
    {
        return "MyMusic{" + "name=" + name + ", url=" + url + ", type=" + type + ", id=" + id + ", time = " + time + ",lyric Address : " + lyricAddress + " }\n";
    }

    /**
     * Test main method.
     *
     * @param args
     */

    public static void main(String[] args)
    {
        Clip myClip;

        String name, path;
        int id, type;
        MyMusic myMusic = new MyMusic();
        Scanner kerboard = new Scanner(System.in);
        System.out.println("Welcome to test music!");
        try
        {
            System.out.println("Please enter name of music:");
            name = kerboard.nextLine();
            myMusic.setName(name);
            System.out.println("Please enter type of music Reference: \n"
                    + " Type = 1: <br> <code>type = 1</code> From Package\n"
                    + " type = 2 From file path\n"
                    + " type = 3 From online.\n"
                    + "Please make your type");
            System.err.println("ONLY SUPPORT .wav file!");
            type = kerboard.nextInt();
            //type = kerboard.nextInt();
            myMusic.setType(type);
            System.out.println("Please enter path here:");
            path = kerboard.nextLine();
            path = kerboard.nextLine();
            myMusic.setUrl(path);
            System.out.println("Please assign an id to it");
            id = kerboard.nextInt();
            myMusic.setId(id);
            System.out.println(myMusic);

            myClip = AudioSystem.getClip();
            System.out.println("Please wait, music will start in a second!");
            myClip.open(myMusic.getAudioInputStream());

            myClip.start();
            System.out.println("Press any key to stop.");
            kerboard.next();
            kerboard.next();

        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }

}
