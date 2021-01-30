/*Editor: Johnson Gao

 * Date This Project Created:
 * Description Of This Class:
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author app
 */
public class WaitDialog extends JFrame
{

    private JLabel loadingLabel;
    final Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 18);
    public final ImageManager IMANAGER = new ImageManager();

    public WaitDialog(String waitName) 
    {
        super("Please wait!");
        initComponents(waitName);
    }
    
    public WaitDialog(String waitName, ImageIcon titleIcon)
    {
        super("Please wait..");
        initComponents(waitName, titleIcon);
    }
    
    public void initComponents(String name, ImageIcon icon)
    {
        getContentPane().setBackground(Color.pink);
        setBounds(444, 333, 520, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        //JLabel
        loadingLabel = new JLabel("<html><center> "+ name +" is loading, please"
                + " wait...</center></html>", icon,
                SwingConstants.CENTER);;
        loadingLabel.setFont(MESSAGE_FONT);
        //Add
        add(loadingLabel, BorderLayout.NORTH, SwingConstants.CENTER);
        add(new JLabel(IMANAGER.openIcon("hug.png"), SwingConstants.CENTER), BorderLayout.CENTER, SwingConstants.CENTER);
        //Finalize
        setVisible(true);
        requestFocus();
    }
    
    public void initComponents(String name)
    {
        getContentPane().setBackground(Color.pink);
        setBounds(444, 333, 520, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        //JLabel
        loadingLabel = new JLabel("<html><center> "+ name +" is loading, please"
                + " wait...</center></html>",
                SwingConstants.CENTER);
        loadingLabel.setFont(MESSAGE_FONT);
        //Add
        add(loadingLabel, BorderLayout.NORTH, SwingConstants.CENTER);
        add(new JLabel(IMANAGER.openIcon("hug.png"), SwingConstants.CENTER), BorderLayout.CENTER, SwingConstants.CENTER);
        //Finalize
        setVisible(true);
        requestFocus();
    }
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new WaitDialog("Music Player");
    }
    
}
