/*Editor: Johnson Gao

 * Date This Class:
 * Description Of This Class: A testing class to test mymusic\
NO LONGER USED.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * This class is a model of standard JFrames.
 *
 * @author Johnson Gao
 */
public class TestVoice extends JFrame implements ActionListener
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
    /**
     * This button disposes the frame.
     */
    private JButton returnButton,playButton;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JTextField nameField;
    private AudioInputStream myStream;
    private Clip clickClip;
    public TestVoice()
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
        
        /**
         * JTextfield.
         */
        this.nameField = new JTextField(10);
        nameField.setBorder(BorderFactory.createTitledBorder("Enter name here:"));
        
        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        this.playButton = new JButton("play");
        playButton.addActionListener(this);
        /**
         * JPanel.
         */
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel,SwingConstants.CENTER);
        northPanel.setOpaque(false);
        this.southPanel = new JPanel(new FlowLayout());
        southPanel.add(returnButton);
        southPanel.add(nameField);
        southPanel.add(playButton);
        /*this.westPanel = new JPanel();
        westPanel.setOpaque(false);*/
        /*this.eastPanel = new JPanel();
        eastPanel.setOpaque(false);*/
        
        /**
         * Add components and finalize frame.
         */
        this.add(northPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
        //this.add(westPanel,BorderLayout.WEST);
        //this.add(eastPanel,BorderLayout.EAST);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        switch(command)
        {
            case "return":
                this.dispose();
                break;
            case "play":
                System.out.println(nameField.getText());
                this.clickSound(nameField.getText());
                break;
        }
        
    }
    public static void main(String[] args)
    {
       TestVoice standardFrame = new TestVoice();
    }
    public void clickSound(String fileName)
    {
        try
        {
            myStream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
            clickClip = AudioSystem.getClip();
            clickClip.open(myStream);
            clickClip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
        {
            journeylove.Warning warning = new journeylove.Warning("OHH NO ERROR PLAYING CLICK SOUND");       
        }
    }

}
