/*Editor: Johnson Gao

 * Date This Project Created:June 2019
 * Description Of This Class: This bulids a JFrame that enables user to edit a component.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *This bulids a JFrame that enables user to edit a component.
 * @author Johnson Gao
 */
public class ComponentEditor extends JFrame
{

    public static final long serialVersionUID = 1L;
final Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 20);
    private JPanel westPanel, northPanel;
    private JLabel fontLabel,colorLabel,titleLabel,fontSizeLabel,foreGround;
    private JComboBox<String> fontComboBox;
    private Component component;
    private JButton chooseBackgroundColorButton,foregroundColorButton;
    private JTextField fontSizeField;
    /**
     * Main constructor of the editor.
     * @param component The component to be edited.
     */
    public ComponentEditor(Component component)
    {
        super(component.getName() + " Setting");
        setBounds(300, 300, 600, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(LovelyColors.GLASS_GALL);
        setLayout(new BorderLayout());
        this.component = component;
        
        //jlabel
        fontLabel = new JLabel("Font Family");
        fontSizeLabel = new JLabel("Font Size");
        colorLabel = new JLabel("Background");
        foreGround = new JLabel("Foreground");
        titleLabel = new JLabel(this.getTitle());
        titleLabel.setFont(MESSAGE_FONT);
        
        //jbutton
        
        chooseBackgroundColorButton = new JButton("Edit");
        chooseBackgroundColorButton.addActionListener((ActionEvent e) ->
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            Color color = JColorChooser.showDialog(null, this.getTitle() , component.getBackground());
            if(color != null)
            {
                component.setBackground(color);
            }
        });
        foregroundColorButton = new JButton("Fore Ground");
        foregroundColorButton.addActionListener((ActionEvent e) ->
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            Color color = JColorChooser.showDialog(null, "Choose foreground", component.getForeground());
            if(color != null)
            {
                component.setForeground(color);
            }
        });
        
        //JTextfield
        fontSizeField = new JTextField(Float.toString(component.getFont().getSize2D()));
        fontSizeField.setToolTipText("Enter a number, press \"Enter\" to edit!");
        fontSizeField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                clickSound(SoundOracle.PHONE_TYPING_SOUND);
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_ENTER)
                {
                    try
                    {
                        float size = Float.parseFloat(fontSizeField.getText());
                        if(size > 0)
                        {
                            component.setFont(component.getFont().deriveFont((size)));
                        }else
                        {
                            Warning warning = new Warning("Please enter a positive number!");
                            warning.pack();
                        }
                    } catch (Exception ex)
                    {
                        
                        Warning warning = Warning.createWarningDialog(ex);//new Warning("Error setting your font size, please re-enter a positive number!");
                        warning.getDetailArea().append("Error setting your font size, please re-enter a positive number!");
                    }
                }
            }
            
});
        //jcombobox
       
        fontComboBox = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontComboBox.addItemListener((ItemEvent e) ->
        {
            component.setFont(new Font(fontComboBox.getItemAt(fontComboBox.getSelectedIndex()), component.getFont().getStyle(), component.getFont().getSize()));
            clickSound(SoundOracle.WATER_PRESS_4);
        });
        
        //jpanal
        
        westPanel = new JPanel(new GridLayout(4, 2));
        westPanel.add(fontLabel);
        westPanel.add(fontComboBox);
        westPanel.add(fontSizeLabel);
        westPanel.add(fontSizeField);
        westPanel.add(colorLabel);
        westPanel.add(chooseBackgroundColorButton);
        westPanel.add(foreGround);
        westPanel.add(foregroundColorButton);
        
        westPanel.setOpaque(false);
        northPanel = new JPanel();
        northPanel.add(titleLabel,SwingConstants.CENTER);
        northPanel.setOpaque(false);
        add(westPanel,BorderLayout.WEST);
        add(northPanel,BorderLayout.NORTH);
        pack();
        setVisible(true);
        validate();
    }
    /**
     * Remove the option for select background from setting and repaint.
     */
    public void removeBackgroundOption()
    {
        westPanel.remove(colorLabel);
        westPanel.remove(chooseBackgroundColorButton);
        westPanel.repaint();
        repaint();
    }
    /**
     * Remove the option for select foreground from setting and repaint.
     */
    public void removeForegroundOption()
            
    {
        westPanel.remove(foreGround);
        westPanel.remove(foregroundColorButton);
        westPanel.repaint();
        repaint();
    }

    /**
     * Get the westpanel.
     * @return westpanel
     */
    public JPanel getWestPanel()
    {
        return westPanel;
    }
    
    /**
     * Test main method.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JFrame tf = new JFrame("Test Area");
        JTextArea testArea = new JTextArea("Jenny I really do love you so!\n 我爱你小宝贝@!!!! \n mua!",5,5);
        testArea.setName("Test Area");
        tf.add(testArea);
        tf.pack();
        tf.setVisible(true);
       // MemoriesRefresher mr = new MemoriesRefresher();
        ComponentEditor ce = new ComponentEditor(testArea);
        // TODO code application logic here
    }
    /**
     * Produce a small sound.
     * @param soundName The name of the sound.
     * @see SoundOracle
     */
    public void clickSound(String soundName)
    {
        try
        {
            Clip effectClip = AudioSystem.getClip();
            effectClip.open(AudioSystem.getAudioInputStream(getClass().getResource(soundName)));
            effectClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Warning warning = new Warning("Cannot open sound  " , ex.toString(),ex,false);
        }
    }
}
