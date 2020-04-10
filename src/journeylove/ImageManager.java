/*Editor: Johnson Gao

 * Date This Project Created:May 2019
 * Description Of This Class: This have a bunch of opening image and scaling image methods.
 */
package journeylove;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This have a bunch of opening image and scaling image methods.
 *
 * @author Johnson Gao
 */
public class ImageManager
{

    /**
     * Creates an object of the class.
     */
    public ImageManager()
    {

    }

    /**
     * Opens an icon within the package.
     *
     * @param addressURL The name/address within the package.
     * @return The icon in the package.
     */
    public ImageIcon openIcon(String addressURL)
    {
        java.net.URL url = getClass().getResource(addressURL);
        ImageIcon imageIcon = new ImageIcon(url);
        return imageIcon;
    }

    /**
     * Open an icon within a package with specified height and width with
     * default scale.
     *
     * @param addressURL The name of the image.
     * @param width The width of the image
     * @param height The height of the image.
     * @return The icon opened.
     */
    public ImageIcon openIcon(String addressURL, int width, int height)
    {
        return this.openIcon(addressURL, width, height, Image.SCALE_DEFAULT);
    }

    /**
     * Open an icon within the package with a defined opening scale algorism.
     * @param addressURL The address in this package.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param imageScale The scale algrotism to scale the image.
     * @return The opened image.
     */
    public ImageIcon openIcon(String addressURL, int width, int height, int imageScale)
    {
        java.net.URL url = getClass().getResource(addressURL);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(width, height, imageScale));
        return imageIcon;
    }

    /**
     * Open an icon in the package that the maximum height and width will
     * be lower than the defined number. The image height and width will be devided by 2 in both
     * side until it fits into the defined maximun width and height.
     * @param addressURL The url in the package, the address of the icon.
     * @param maxWidth The defined maximun width of the new icon.
     * @param maxHeight The defined maximun height of the new icon.
     * @return The autoscaled icon.
     */
    public ImageIcon openAutoScaledIcon(String addressURL, int maxWidth, int maxHeight)
    {
        return this.autoScaleIcon(openIcon(addressURL), 2, maxWidth, maxHeight);
    }

    /**
     * Open an icon in the package that the maximum height and width will
     * be lower than the defined number. The image height and width will be devided by the ratio in both
     * side until it fits into the defined maximun width and height.
     * @param addressURL The url in the package, the address of the icon.
     * @param ratio The ratio used to autoscale the image.
     * @param maxWidth The defined maximun width of the new icon.
     * @param maxHeight The defined maximun height of the new icon.
     * @return The autoscaled icon.
     */
    public ImageIcon openAutoScaledIcon(String addressURL, int ratio, int maxWidth, int maxHeight)
    {
        return this.autoScaleIcon(this.openIcon(addressURL), ratio, maxWidth, maxHeight);
    }

    /**
     * This will scale the image not larger than maximun but not smaller than
     * minimun, also respects the original ratio of the image.
     *
     * @param addressURL Where the image are stored in the package.
     * @param maxWidth Maximun width of the image.
     * @param maxHeight Max allowed height
     * @param minWidth Min allowed width
     * @param minHeight Min allowed height.
     * @return
     */
    public ImageIcon openAutoScaledIcon(String addressURL, int maxWidth, int maxHeight, int minWidth, int minHeight)
    {

        java.net.URL url = getClass().getResource(addressURL);
        ImageIcon originIcon = new ImageIcon(new ImageIcon(url).getImage());
        int height = originIcon.getIconHeight();
        int width = originIcon.getIconWidth();

        if (height > maxHeight || width > maxWidth || height < minHeight || width < minWidth)
        {
            //feedbackLabel.setText("Image are scaled");
            ///Deal with large image

            while (height < minHeight || width < minWidth)
            {
                height *= 2;
                width *= 2;
            }
            while (height > maxHeight || width > maxWidth)
            {
                height /= 2;
                width /= 2;
            }
            ImageIcon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(height, width, Image.SCALE_DEFAULT));
            return icon;
        } else
        {
            return originIcon;
        }
    }

    /**
     * Scale an icon with specified maximun allowed width and height and ration,
     * along with respecting the original ration of the image.
     *
     * @param originIcon The icon needs to be scaled.
     * @param ratio The ratio to scale the image.
     * @param maxWidth Max allowed width.
     * @param maxHeight Max allowed height.
     * @param hint The scale of the image.
     * @return The scaled image.
     */
    public ImageIcon autoScaleIcon(ImageIcon originIcon, double ratio, int maxWidth, int maxHeight, int hint)
    {
        int height = originIcon.getIconHeight();
        int width = originIcon.getIconWidth();
        if (height > maxHeight || width > maxWidth)
        {
            //feedbackLabel.setText("Image are scaled");
            ///Deal with large image
            while (height > maxHeight || width > maxWidth)
            {
                height *= ratio;
                width *= ratio;
            }
            ImageIcon icon = new ImageIcon(originIcon.getImage().getScaledInstance(width, height, hint));
            return icon;
        } else
        {
            return originIcon;
        }
    }

    /**
     * Automatically scale an icon that will fit into the maximun width and height
     * without hurting its original ratio.
     * @param originIcon The origin icon to be scaled.
     * @param ratio The ratio to scale the icon.
     * @param maxWidth The maximun width accepted of this icon.
     * @param maxHeight The maximun height accepted of this icon.
     * @return The scaled icon.
     */
    public ImageIcon autoScaleIcon(ImageIcon originIcon, double ratio, int maxWidth, int maxHeight)
    {
        return this.autoScaleIcon(originIcon, ratio, maxWidth, maxHeight, Image.SCALE_DEFAULT);
    }
//    public ImageIcon openIconFromPackagePath(String pathInPkg)
//    {
//       ClassLoader cl = this.getClass().getClassLoader();
//       return new ImageIcon(cl.getResource("journeylove/"+pathInPkg));
//    }
//    
//    public ImageIcon openScaledIconFromPackagePath(String pathInPkg,int width,int height,int hints)
//    {
//       return  new ImageIcon (openIconFromPackagePath(pathInPkg).getImage().getScaledInstance(width, height, hints));
//    }
//    public ImageIcon openScaledIconFromPackagePath(String pathInPkg,int width,int height)
//    {
//        return  new ImageIcon (openIconFromPackagePath(pathInPkg).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
//    }
//    
//    public ImageIcon openAutoScaledIconFromPackagePath(String pathInPkg,int Maxwidth,int Maxheight,int ratio)
//    {
//        return this.autoScaleIcon(openIcon(pathInPkg), ratio, Maxwidth, Maxheight);   
//    }
//    
//    public ImageIcon openAutoScaledIconFromPackagePath(String pathInPkg,int Maxwidth,int Maxheight,int ratio,int hint)
//    {
//        return this.autoScaleIcon(openIconFromPackagePath(pathInPkg), ratio, Maxwidth, Maxheight, hint);
//    }
//    

    /**
     * Open an icon directly from the url.
     *
     * @param url The url of the image.
     * @return The opened image.
     * @throws java.io.IOException When failed to open from online
     */
    public ImageIcon openOnlineIcon(java.net.URL url) throws IOException
    {

        ImageIcon img = new ImageIcon(ImageIO.read(url));
        return img;

    }

    /**
     * Open an icon from online with specified width and height.
     *
     * @param url The url of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @return The opened icon.
     * @throws IOException
     */
    public ImageIcon openOnlineIcon(java.net.URL url, int width, int height) throws IOException
    {
        return openOnlineIcon(url, width, height, Image.SCALE_DEFAULT);
    }

    /**
     * Open an online icon that will fit into the maximun height and width
     * scale the icon without hurting its original ratio.
     * @param url The url of the icon.
     * @param width The maximun width accepted.
     * @param height The maximun height accepted.
     * @param hint The hint that indicates scaling algrotism.
     * @return The scaled icon.
     * @throws IOException When failed to bulid file.
     * 
     */
    public ImageIcon openOnlineIcon(java.net.URL url, int width, int height, int hint) throws IOException
    {
        return new ImageIcon(openOnlineIcon(url).getImage().getScaledInstance(width, height, hint));
    }

    /**
     * Open an icon directly from online.
     * @param url The online address of the image.
     * @return The opened icon.
     * @throws MalformedURLException When the URL format is incorrect or mismatch.
     * @throws IOException When cannot get the file.
     */
    public ImageIcon openOnlineIcon(String url) throws MalformedURLException, IOException
    {
        java.net.URL netURL = new URL(url);
        return this.openOnlineIcon(netURL);

    }

    /**
     * Open an icon directly from online, scaled with default algrotism.
     * @param url The online address of the image.
     * @param width The designed width.
     * @param height The height of the icon.
     * @return the opened icon.
     * @throws java.net.MalformedURLException When the url format is incorrect or mismatch.
     * @throws IOException Failed file.
     */
    public ImageIcon openOnlineIcon(String url, int width, int height) throws java.net.MalformedURLException, IOException
    {
        return openOnlineIcon(url, width, height, Image.SCALE_DEFAULT);
    }

    /**
     * Open an icon directly from online.
     * @param url The address of the image.
     * @param width The width of the icon.
     * @param height The height of the icon.
     * @param hint The constant indicates the algrotism to scale the imageicon.
     * @return the opened icon.
     * @throws MalformedURLException
     * @throws IOException 
     */
    public ImageIcon openOnlineIcon(String url, int width, int height, int hint) throws MalformedURLException, IOException
    {
       return new ImageIcon(openOnlineIcon(url).getImage().getScaledInstance(width, height, hint));
    }

    /**
     * Open an icon directly from online and scale to fit in the maximun size.
     * It will not hurt the original ratio. Use defined algrotism.
     * @param url The url where the image are stored.
     * @param maxWidth The maximun width accepted.
     * @param maxHeight The maximun heoght accepted.
     * @param hints The hint(algrorism) used to scale the icon.
     * @return The auto scaled icon.
     * @throws MalformedURLException when the url format is incorrect or mismatch.
     * @throws IOException Failed to open file.
     */
    public ImageIcon openAutoScaledOnlineIcon(String url, int maxWidth, int maxHeight, int hints) throws MalformedURLException, IOException
    {
        return autoScaleIcon(openOnlineIcon(url), 2, maxWidth, maxHeight, hints);
    }

    /**
     * Auto scale the icon that the two sides will not greater than 
     * defined maximun width and height.
     * This method will use default algrotism.
     * @param url The url of the image.
     * @param maxWidth The maximun width of the image.
     * @param maxHight The maximun hight of the image.
     * @return The auto scaled icon.
     * @throws MalformedURLException
     * @throws IOException 
     */
    public ImageIcon openAutoScaledOnlineIcon(String url, int maxWidth, int maxHight) throws MalformedURLException, IOException
    {
        return openAutoScaledOnlineIcon(url, maxWidth, maxHight, Image.SCALE_DEFAULT);
    }

    /**
     * Open buffered image from file system.
     * @param filePath The path where the file stored at.
     * @return The opemed buffered image.
     * @throws IOException Failed to open the file.
     */
    public BufferedImage openLocalBufferedImage(String filePath) throws IOException
    {
        return ImageIO.read(new File(filePath));
    }

    /**
     * Test main method.
     *
     * @param args Argument.
     */
    public static void main(String[] args)
    {
        MemoriesRefresher memoriesRefresher = new MemoriesRefresher();
    }

}
