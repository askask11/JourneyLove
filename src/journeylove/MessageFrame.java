/*Editor: Johnson Gao

 * Date This Class: May 2019
 * Description Of This Class:A free frame
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class is a flaxiable frame of Journey Love.
 *
 * @author Johnson Gao
 */
public class MessageFrame extends JFrame implements ActionListener
{

    //Final:font
    /**
     * A constant decleared for the font on the top. 
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
   
    /**
     * Holds things for the title.
     */
    private JLabel titleLabel;
    private JLabel centralLabel;
    
    /**
     * This button disposes the frame.
     */
    private JButton returnButton;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel centralPanel;
    
    public MessageFrame()
    {
        /**
         * Constructing sequence: JFrame JLabel JButtom JPanel ADDING ?PACK?
         * Rule: When constructing, call"this",otherwise, no "this"unless necessary. 
         */

        /**
         * JFrame.
         */
        super("My Frame");
        this.setBounds(100/*x align R*/, 100/*y align down*/, 500/*X-WIDTH*/, 500/*Y-Width*/);
        this.getContentPane().setBackground(Color.PINK);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("My Frame");
        titleLabel.setFont(TITLE_FONT);
        this.centralLabel = new JLabel();
        
        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        /**
         * JPanel.
         */
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel,SwingConstants.CENTER);
        northPanel.setOpaque(false);
        this.southPanel = new JPanel(new FlowLayout());
        southPanel.add(returnButton);
        centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(centralLabel,BorderLayout.CENTER);
        centralPanel.setOpaque(false);
        /*this.westPanel = new JPanel();
        westPanel.setOpaque(false);
        this.eastPanel = new JPanel();
        eastPanel.setOpaque(false);*/
        
        /**
         * Add components and finalize frame.
         */
        this.add(northPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(centralPanel,BorderLayout.CENTER);
        //this.add(westPanel,BorderLayout.WEST);
        //this.add(eastPanel,BorderLayout.EAST);
        this.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.dispose();
    }
    public static void main(String[] args)
    {
       MessageFrame standardFrame = new MessageFrame();
       standardFrame.getCentralLabel().setText("central text");
       standardFrame.getTitleLabel().setText("Title text");
    }
    /**
     * This will give the title of this frame.
     * @return The title label of the frame.
     */
    public JLabel getTitleLabel()
    {
        return this.titleLabel;
    }
    /**
     * Return the central label of the frame.
     * @return The central label of the frame.
     */
    public JLabel getCentralLabel ()
    {
        return this.centralLabel;
    }

    public JPanel getCentralPanel()
    {
        return centralPanel;
    }

    /**
     * Places 3 imageicons horizontally.
     * @param l Left icon
     * @param c Central Icon
     * @param r Right Icon
     */
    public void spi(ImageIcon l, ImageIcon c, ImageIcon r)
    {
               this.getCentralLabel().setIcon(c);
                JLabel shLabel = new JLabel(l);
                JLabel jenLabel = new JLabel(r);
                getCentralPanel().add(shLabel,BorderLayout.WEST);
                getCentralPanel().add(jenLabel,BorderLayout.EAST);
                pack();
                getTitleLabel().setText("A LETTER TO EXELLENCY");
//                try
//                {
//                    Thread.currentThread().sleep(5000);//Give user a time for responding, avoid from rushing messages out.
//                } catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
                
    }
}
