/*Editor: Johnson Gao

 * Date This Project Created:
 * Description Of This Class: Convert .mp3 music to .wav and save. Using JLayer.
 */
package journeylove;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;

/**
 * Convert .mp3 music to .wav and save.
 *
 * @author Johnson Gao
 */
public class MusicConverter
{

    // private String address;
    private Converter converter = new Converter();

    /**
     * Construct a new converter.
     */
    public MusicConverter()
    {

    }

    /**
     * Convert a online mp3 media to .wav file and save it to local directory.
     *
     * @param url The address of online file.
     * @param newName The name of the new file.
     * @param outFile Where the new file will be stored.
     * @return The new file converted.
     * @throws IOException Cannot find/ open your file.
     * @throws JavaLayerException Failed to convert your file.
     */
    public File convertAndSave(java.net.URL url, String newName, File outFile) throws IOException, JavaLayerException
    {
        //Thread thread = new Thread(()->{})
        if (!outFile.exists())
        {
            outFile.mkdirs();
        }
        int len;
        File temp = new File(outFile.getAbsolutePath() + "\\" + newName + ".wav");//File.createTempFile(newName , ".wav" , outFile);
        temp.createNewFile();
        System.out.println("A new file was created at " + temp.getAbsolutePath());
//        temp = File.createTempFile(newName, ".wav");
        System.out.println("Converting started.");
        converter.convert(url.openStream(), temp.getAbsolutePath(), null, new Decoder.Params());
        System.out.println("journeylove.MusicConverter.convertAndSave() :converted");

        return temp;
    }

    /**
     * Convert a .mp3 file into a .wav file using filepath -- file algrotim.
     *
     * @param inFile The mp3 file that needs to be converted.
     * @param name The new name of the new file.
     * @param outFile Where the new file will be stored.
     * @return The file converted.
     * @throws IOException When failed to get your file.
     * @throws JavaLayerException From converter -- When failed to convert your
     * file.
     * @deprecated this doesn't work as it outputs emptyfile.
     */
    public File convertAndSave(File inFile, String name, File outFile) throws IOException, JavaLayerException
    {
        if (!outFile.exists())
        {
            outFile.mkdirs();
            System.out.println(" The directory user chosen does not exist, a new folder was created.\n"
                    + "The new file will be hold in the directory.");

        }
        File temp = File.createTempFile(name, ".wav", outFile);

        System.out.println("A new file was created at " + temp.getAbsolutePath());

        System.out.println("Converting started.");
        converter.convert(inFile.toURI().toURL().openStream(), outFile.getAbsolutePath(), null, new Decoder.Params());

        System.out.println("Converted & Saved to your local file.");
        return temp;
    }

    /**
     * Test main method.
     *
     * @param args Lines command argument.
     */
    public static void main(String[] args)
    {
        MusicConverter converter = new MusicConverter();
        int confirmed;
        String in, name;
        InputStream stream;
        Scanner keyboard = new Scanner(System.in);
        URI uri = null;
        JFileChooser chooser = new JFileChooser();

        try
        {
            System.out.println("enter online input");
            // in = keyboard.nextLine();

            System.out.println("enter new name of the file.");
            //name = keyboard.nextLine();

            // url.getFile().getBytes()
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            confirmed = chooser.showSaveDialog(null);
            //uri = new URI(in);
            if (confirmed == JFileChooser.APPROVE_OPTION)
            {
                File nf = chooser.getSelectedFile();
                System.out.println(nf.getAbsolutePath());

                converter.convertAndSave(new File("C:\\Users\\app\\Music\\陈建骐 - 拥有.mp3".replace("\\", "/").replace("\"", "")).toURI().toURL(),
                         "拥有 - ", nf);
                // System.out.println(new File("\"C:\\Users\\app\\Music\\陈建骐 - 拥有.mp3\"").toURI().toURL());
            } else
            {
                System.out.println("User cancelled.");
            }
        } catch (IOException | JavaLayerException ex)
        {
            Logger.getLogger(MusicConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * 
     */
}
