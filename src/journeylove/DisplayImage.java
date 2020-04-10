/*Editor: Johnson Gao

 * Date This Project Created:May 2019
 * Description Of This Class: This is an abstraction class of the images being displayed, carrying their information.
 */
package journeylove;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This is an abstraction class of an image.
 * @author Johnson Gao
 */
public class DisplayImage
{

    /**
     * Name indicates this icon.
     */
    private String name;
    /**
     * An url that points towards this icon, can either be inside this package
     * or online. Open file in file manager will be supported soon.
     */
    private String url;
    /**
     * Where does this image store? Can be online, package or offline
     * imported(supported soon).
     * <br> <code>type = 0</code> undefined
     * <br> <code>type = 1</code> From package.
     * <br> <code>type = 2</code> From local.
     * <br> <code>type = 3</code> From online.
     */
    private int type = 0;
    /**
     * When this image displays, what do you want the displayer do with this
     * image. Default=null;
     */
    private String buff = null;
    /**
     * When the main displayer player to this image, what bgcolor you expect?.
     * null is the default.
     */
    //private Color perferredBgColor;

    /**
     * This indicates that the type of the address of this image is undefined.
     */
    static final int TYPE_UNDEFINED = 0;
    /**
     * This indicates that the type of the address of this image is from the package.
     */
    public static final int TYPE_PACKAGE = 1;
    /**
     * This indicates that the type of the address of this image is from users computer.
     */
    public static final int TYPE_LOCAL = 2;
    /**
     * This indicates that the type of the address of this image is directly from online.
     */
    public static final int TYPE_ONLINE = 3;
    /**
     * The id assigned to the image.
     */
    private int id=0;
    /**
     * The description of the image.
     */
    private String description = "" ;
    /**
     * An imagemanager.
     */
    final ImageManager IM = new ImageManager();

    /**
     * Create a default model of the image.
     */
    public DisplayImage()
    {
//        icon = null;
        name = "Undefined";
        url = "";
        type = 0;
        buff = null;
        description = "";
        //perferredBgColor = null;
    }

    

    /**
     * Given name and type to open an icon.
     *
     * @param name The name of the icon.
     * @param url Where is the icon.
     * @param type How should I access the icon.
     * @param buff Spectial buff comes with the icon.
     * @param id the identifying number of the image.
     */
    public DisplayImage(String name, String url, int type, String buff,int id)
    {
        this.name = name;
        this.url = url;
        this.type = type;
        this.buff = buff;
        this.id = id;
    }
    
    /**
     * Given a name, url and type of the image.
     * @param name Name of the image.
     * @param url where is the image.
     * @param type Type of the image.User-defined?
     * <br> <code>type = 0</code> undefined
     * <br> <code>type = 1</code> From package.
     * <br> <code>type = 2</code> From local.
     * <br> <code>type = 3</code> From online.
     * @param id the identifying number of the image
     * 
     * 
     */
    public DisplayImage (String name, String url, int type , int id)
    {
        this.name = name;
        this.url = url;
        this.type = type;
        this.id = id;
//        this.readIcon(url, type);
    }
    
    
    public DisplayImage(String name,  String url,  int type, int id, String description)
    {
        this.name = name;
        this.url = url;
        this.type = type;
        this.id = id;
        this.description = description;
    }
    
    /**
     * Only give url and type of the image.
     * @param url Where is the image.
     * @param type How to access the image. User-defined?
     * <br> <code>type = 0</code> undefined
     * <br> <code>type = 1</code> From package.
     * <br> <code>type = 2</code> From local.
     * <br> <code>type = 3</code> From Online.
     */
    public DisplayImage (String url, int type)
    {
        this.url = url;
        this.type = type;
//        this.readIcon(url, type);
    }
    
   

    /**
     * Access the image through the type given.
     * @param url The url targets the image.
     * @param type The type of the image.
     * @return The image received.
     * <br> <code>type = 0</code> undefined
     * <br> <code>type = 1</code> From package.
     * <br> <code>type = 2</code> From local.
     * <br> <code>type = 3</code> From online.
     * @throws java.net.MalformedURLException
     */
    public ImageIcon readIcon(String url, int type) throws MalformedURLException, IOException 
    {
        ImageIcon icon = null;
        switch (type)
        {
            case 0:
                //icon = this.icon;
                System.err.println("journeylove.DisplayImage.readIcon()");
                System.err.println("Icon undefined (0)");
                break;
            case 1:
                icon = IM.openIcon(url);
                break;
            case 2:
                icon = new ImageIcon(ImageIO.read(new File(url)));
                break;
            case 3:
                icon = IM.openOnlineIcon(url);
                break;
            default:
                System.out.println("journeylove.DisplayImage.readIcon()");
                System.err.println("Unknown Type caught: " + type);
                break;
        }
        //this.icon = icon;
        return icon;
        
    }
    
    /**
     * Read icon from the type and url given.
     * @return The icon read.
     * @throws MalformedURLException When entered an non-url form online address
     * @throws IOException When failed to open file
     */
    public ImageIcon readIcon() throws MalformedURLException, IOException
    {
        return this.readIcon(url, type);
    }

    /**
     * The name of the image.
     * @return name of the image
     */
    public String getName()
    {
        if(name == null)
        {
            name = "";
        }
        return name;
    }

    /**
     * Give a name to the image.
     * @param name Name of the image.
     */
    public void setName(String name)
    {
        this.name = name;
    }
/**
 * Get the address of the image.
 * @return the address of the image.
 */
    public String getUrlAsString()
    {
        return url;
    }

    /**
     * Set the address of the image.
     * @param url Where the image stored.
     */
    public void setUrlString(String url)
    {
        this.url = url;
    }
    
/**
 * Returns the type in integer.
 * @return The type of address in integer.
 */
    public int getType()
    {
        return type;
    }
    /**
     * Purse the type of integer to a user-readeable string.
     * @return The string of the type of the address.
     */
    public String getTypeAsString()
    {
        String string ="";
        switch (type)
        {
            case 1:
                string = "Package";
                break;
            case 2:
                string = "Local";
                break;
            case 3:
                string = "Online";
                break;
            default:
                string = "Undefined";
                break;
        }
        return string;
    }

    /**
     * Set the type of the image.
     * @param type Type of the image.
     */
    public void setType(int type)
    {
        this.type = type;
    }

    public String getBuff()
    {
        return buff;
    }

    public void setBuff(String buff)
    {
        this.buff = buff;
    }

    /**
     * Get the id of the image, every image's id is unique.
     * @return The id of the image.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set the id of the image.
     * @param id Identical number of the image.
     */
    public void setId(int id)
    {
        this.id = id;
    }

//    public Color getPerferredBgColor()
//    {
//        return perferredBgColor;
//    }
//
//    public void setPerferredBgColor(Color perferredBgColor)
//    {
//        this.perferredBgColor = perferredBgColor;
//    }

    /**
     * A better name for"read icon"
     * @return The icon.
     * @throws MalformedURLException
     * @throws IOException 
     */
    public ImageIcon getIcon() throws MalformedURLException, IOException
    {
        return readIcon();
    }

    public String getDescription()
    {
        if(description == null)
        {
            description = "";
        }
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns main variable of this object.
     * @return The main object of the obj.
     */
    @Override
    public String toString()
    {
        return "DisplayImage{" + "name=" + name + ", url=" + url + ", type=" + type + ", buff=" + buff  + ", ID=" + id + ", Description= "+ description + '}';
    }
    /**
     * 
     * Test main method.
     * @param args Argument.
     */
    public static void main(String[] args)
    {
        MemoriesRefresher memoriesRefresher = new MemoriesRefresher();
    }

    
}
