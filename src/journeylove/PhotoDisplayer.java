/*Editor: Johnson Gao

 * Date This Class: June 2019
 * Description Of This Class: This is a photo displayer with multi function.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene.image.Image;
//import javafx.scene.image.WritableImage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * This frame displays our memories.
 *
 * @author Johnson Gao
 */
public class PhotoDisplayer extends JFrame implements ActionListener, ItemListener
{

    //Final:font
    /**
     * A constant decleared for the font on the top.
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
    final Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 20);
    /**
     * Holds things for the title.
     */
    private JLabel titleLabel, statusLabel;
    /**
     * This button disposes the frame.
     */
    private JButton returnButton, custmizeButtom;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    private JPanel northPanel;
    private JPanel mainControlPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel centralPanel;
    private JLabel feedbackLabel, multipleLabel;
    private JPanel southPanel;
    private JLabel imageLabel;
    private JLabel cutePicLabel;
    final ImageManager IM = new ImageManager();
    private Box progressBox;
    private JComboBox<String> displayComboBox;
    public static final String[] DISPLAY_GROUP =
    {
        "--Please select display group--",
    };
    private JScrollBar delayBar;
    private JCheckBox preferSizeCheckBox;
    private JTextField xField, yField;
    private JComboBox<String> musicComboBox;
    public static final String[] MUSIC_GROUP =
    {
        "那个男孩",
        "王伟 - 小时光.wav",

    };
    private JCheckBox playMusicCheckBox;
    private JTextField musicLocalURLfield;
    private JCheckBox saveToListBox;
    private JButton previousButton, playButton, pauseButton, stopButton, nextButton, importMusicButton;
    private JScrollPane mainPane, tableListPane;
    private JMenu personalizeMenu;
    private JRadioButton musicUrl, musicLocal;
    private ButtonGroup musicUrlOrMusicLocal;
    private JTable playListTable;
    final String[] PLAYLIST_TABLE_HEADER =
    {
        "Sequence #", "Name", "Origin", "Remarks"
    };
    private String[][] displayList = null;
    private Timer mainTimer;
    private boolean playMusic;
    /**
     * This indicates the position of displaying in the current player.
     */
    private int currentPosition;
    private String[] playList;

    private AudioInputStream myStream;
    private Clip myClip;
    /**
     * What is this frame doing. 0.Stopped<br>
     * 1.Displaying<br>
     * 2.pausing<br>
     */
    private int status = 0, maxHeight = 900, maxWidth = 900;
    private boolean musicInProgress = false;

    /**
     * This is a photo displayer with multiple function.
     */
    public PhotoDisplayer()
    {

        /**
         * JFrame.
         */
        super("My Frame");
        this.setBounds(100/*x align R*/, 100/*y align down*/, 800/*X-WIDTH*/, 700/*Y-Width*/);
        this.getContentPane().setBackground(new LovelyColors().LIGHT_HEART_BLUE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("Our memories.");
        titleLabel.setFont(TITLE_FONT);
        this.imageLabel = new JLabel(IM.openIcon("on bed.jpg"));
        this.feedbackLabel = new JLabel("Welcome to Our Journey Memories Refresher");
        feedbackLabel.setFont(MESSAGE_FONT);
        multipleLabel = new JLabel(IM.openIcon("X.png"));
        cutePicLabel = new JLabel(IM.openIcon("pkq-wel.jpg", 290, 215));
        statusLabel = new JLabel("Stopped");
        statusLabel.setFont(MESSAGE_FONT);

        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        previousButton = new JButton(IM.openIcon("prev.png"));
        previousButton.setActionCommand("<");
        previousButton.addActionListener(this);
        nextButton = new JButton(IM.openIcon("next.png"));
        nextButton.setActionCommand(">");
        nextButton.addActionListener(this);
        playButton = new JButton("Play", IM.openIcon("play.png"));
        playButton.addActionListener(this);
        pauseButton = new JButton("Pause", IM.openIcon("pause.png"));
        pauseButton.setActionCommand("Pause");
        pauseButton.addActionListener(this);
        pauseButton.setVisible(false);
        stopButton = new JButton("Stop", IM.openIcon("Stop.png"));
        stopButton.setBackground(new LovelyColors().MERRY_CRANESBILL);
        stopButton.setVisible(false);
        stopButton.addActionListener(this);
        this.importMusicButton = new JButton("import", IM.openIcon("newFile.png"));
        importMusicButton.addActionListener(this);
        importMusicButton.setEnabled(false);
        custmizeButtom = new JButton("My Playlist");
        custmizeButtom.addActionListener(this);
        //custmizeButtom.setBorder(BorderFactory.createTitledBorder("Click to check the playlist"));
        /*
         * JComboBox
         */
        displayComboBox = new JComboBox<>(DISPLAY_GROUP);
        displayComboBox.setBorder(BorderFactory.createTitledBorder(""));
        displayComboBox.setPreferredSize(new Dimension(210, 30));
        displayComboBox.setBorder(BorderFactory.createTitledBorder("Select photo display group:"));
        displayComboBox.setOpaque(false);

        musicComboBox = new JComboBox<>(MUSIC_GROUP);
        musicComboBox.setBorder(BorderFactory.createTitledBorder(""));
        musicComboBox.setOpaque(false);
        musicComboBox.setEnabled(false);

        /*
        JCheckBox
         */
        preferSizeCheckBox = new JCheckBox("Adjest Size");
        preferSizeCheckBox.setOpaque(false);
        preferSizeCheckBox.addActionListener(this);
        playMusicCheckBox = new JCheckBox("Accompany Background Music");
        playMusicCheckBox.setOpaque(false);
        playMusicCheckBox.addActionListener(this);

        /*
        JRadioButton
         */
        musicLocal = new JRadioButton("Use Existing Music");
        musicLocal.setOpaque(false);
        musicLocal.addActionListener(this);
        musicLocal.setEnabled(false);
        musicUrl = new JRadioButton("Or Read From File");
        musicUrl.setOpaque(false);
        musicUrl.addActionListener(this);
        musicUrl.setEnabled(false);
        //Group abstract
        musicUrlOrMusicLocal = new ButtonGroup();
        musicUrlOrMusicLocal.add(musicLocal);
        musicUrlOrMusicLocal.add(musicUrl);


        /*
        JTextfields
         */
        xField = new JTextField(5);
        xField.setBorder(BorderFactory.createTitledBorder("Width"));
        xField.setEnabled(false);
        yField = new JTextField(5);
        yField.setBorder(BorderFactory.createTitledBorder("Height"));
        yField.setEnabled(false);
        musicLocalURLfield = new JTextField();
        musicLocalURLfield.setBorder(BorderFactory.createTitledBorder("Enter a COMPLETE Path:"));
        musicLocalURLfield.setEnabled(false);

        /*
        JScrollBarrrrr
         */
        delayBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 5, 0, 200);
        delayBar.setBorder(BorderFactory.createTitledBorder("Delay"));
        delayBar.addAdjustmentListener(new AdjustmentListener()
        {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                mainTimer.setDelay(e.getValue() * 100);
            }
        });
        delayBar.setOpaque(false);

        /*
        Timer
         */
        mainTimer = new Timer(3500, this);
        mainTimer.setActionCommand(">");

        /*
        JScrollpane
         */
        mainPane = new JScrollPane(imageLabel);
        mainPane.getViewport().setOpaque(false);

        /* sizeBox.add(preferSizeCheckBox);
        sizeBox.add(xField);
        sizeBox.add(multipleLabel);
        sizeBox.add(yField);
        sizeBox.setBorder(BorderFactory.createTitledBorder("Set Preferred Picture Size"));
        sizeBox.setOpaque(false);
        /*musicLocalBox = Box.createHorizontalBox();
        musicLocalBox.add(this.musicLocal);
        musicLocalBox.add(musicComboBox);
        musicLocalBox.setOpaque(false);
        musicLocalBox.setBorder(BorderFactory.createTitledBorder("Music Begin From Local Music: "));
        musicLocalBox.setEnabled(false);
        musicUrlBox = Box.createHorizontalBox();
        musicUrlBox.add(this.musicUrl);
        musicUrlBox.add(importMusicButton);
        musicUrlBox.setOpaque(false);
        musicUrlBox.setBorder(BorderFactory.createTitledBorder("Or Import Music: "));*/
 /*this.startBox = new JPanel(new GridLayout(6, 0, 2, 0));
        ImageIcon bgImageIcon = IM.openIcon("secretBGimgBig.jpg");
        Image bgImage = bgImageIcon.getImage();
        startBox.add(custmizeButtom);
        startBox.add(delayBar);
        //startBox.add(sizeBox);
        startBox.add(playMusicCheckBox);
        startBox.add(musicLocalBox);
        startBox.add(musicUrlBox);
        startBox.setOpaque(false);
        System.out.println("width of startBox:" + startBox.getWidth() + " height "+ startBox.getHeight());*/
        this.progressBox = Box.createHorizontalBox();
        progressBox.add(previousButton);
        progressBox.add(playButton);
        progressBox.add(pauseButton);
        progressBox.add(stopButton);
        progressBox.add(nextButton);
        progressBox.setOpaque(false);

        /*
         JPanel.
         */
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel, SwingConstants.CENTER);
        northPanel.setOpaque(false);
        this.mainControlPanel = new JPanel(new FlowLayout());
        mainControlPanel.add(statusLabel);
        mainControlPanel.add(returnButton);
        mainControlPanel.add(progressBox, SwingConstants.CENTER);
        mainControlPanel.setOpaque(false);
        southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.add(mainControlPanel, BorderLayout.NORTH);
        this.westPanel = new JPanel();
        westPanel.setOpaque(false);
        this.eastPanel = new JPanel();
        eastPanel.setOpaque(false);
        //eastPanel.add(startBox);
        /*this.rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel(IM.openIcon("start.png",239,31)),BorderLayout.NORTH);
        rightPanel.add(startBox,BorderLayout.CENTER);
        rightPanel.add(cutePicLabel,BorderLayout.SOUTH);
        rightPanel.setOpaque(false);*/
        this.centralPanel = new JPanel(new BorderLayout());
        centralPanel.setOpaque(false);
        centralPanel.add(feedbackLabel, BorderLayout.NORTH);
        centralPanel.add(imageLabel, BorderLayout.CENTER, SwingConstants.CENTER);
        //       centralPanel.add(progressBox,BorderLayout.SOUTH,SwingConstants.CENTER);
//        centralPanel.add(rightPanel,BorderLayout.EAST);

        /**
         * Add components and finalize frame.
         */
        this.add(northPanel, BorderLayout.NORTH);
        this.add(mainControlPanel, BorderLayout.SOUTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(centralPanel, BorderLayout.CENTER, SwingConstants.CENTER);
        this.setVisible(true);
        this.validate();
        this.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        switch (command)
        {
            case "return":
                this.dispose();
                break;
            case "Play":
                this.play();
                break;
            case "Pause":
                this.pause();
                break;
            case "Stop":
                this.stop();
                break;
            case "Adjest Size":
                boolean flag = preferSizeCheckBox.isSelected();
                xField.setEnabled(flag);
                yField.setEnabled(flag);
                multipleLabel.setEnabled(flag);
                break;
            case "Accompany Background Music":
                boolean musicFlag = playMusicCheckBox.isSelected();
                musicLocal.setEnabled(musicFlag);
                musicUrl.setEnabled(musicFlag);
//                musicLocalBox.setEnabled(musicFlag);
//                musicUrlBox.setEnabled(musicFlag);
                musicComboBox.setEnabled(musicFlag);
                importMusicButton.setEnabled(musicFlag);
                break;
            case ">":
            {
                try
                {
                    next();
                } catch (MalformedURLException ex)
                {

                    Logger.getLogger(PhotoDisplayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex)
                {
                    Logger.getLogger(PhotoDisplayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "<":
            {
                try
                {
                    pervious();
                } catch (MalformedURLException ex)
                {
                    Logger.getLogger(PhotoDisplayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex)
                {
                    Logger.getLogger(PhotoDisplayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "import":
                feedbackLabel.setText("Not supported yet");
                break;
            case "Use Existing Music":
                openMusic(musicComboBox.getItemAt(musicComboBox.getSelectedIndex()));
                break;
            case "My Playlist":
                System.out.println("Not supported yet");
                break;
            default:
                System.out.println("Uncought action command received: " + command);
                break;
        }

    }

    /**
     * Start playing images.
     */
    public void play()
    {
        status = 1;
//        rightPanel.setVisible(false);
        playButton.setVisible(false);
        pauseButton.setVisible(true);
        stopButton.setVisible(true);
        mainTimer.start();
        imageLabel.setIcon(IM.openAutoScaledIcon("Ourphotos/showHeartToWZN.jpg", maxWidth, maxHeight));
        statusLabel.setText("Playing");
        if (this.playMusicCheckBox.isSelected() && musicInProgress == false)
        {
            playMusic();
            musicInProgress = true;
        }
    }

    /**
     * Pause playing images and music.
     */
    public void pause()
    {
        status = 2;
        playButton.setVisible(true);
        pauseButton.setVisible(false);
        stopButton.setVisible(true);
//        rightPanel.setVisible(true);
        mainTimer.stop();
        statusLabel.setText("Paused");
        musicInProgress = false;
        if (myClip.isOpen())
        {
            try
            {
                myClip.stop();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * Stop playing images and stop music.
     */
    public void stop()
    {
        status = 0;
        playButton.setVisible(true);
        pauseButton.setVisible(false);
        stopButton.setVisible(false);
//        rightPanel.setVisible(true);
        mainTimer.stop();
        stopMusic();
        musicInProgress = false;
        currentPosition = 0;

        imageLabel.setIcon(null);
        statusLabel.setText("Stopped");
        if (myClip.isOpen())
        {
            stopMusic();
        }
    }

    /**
     * Go to next image.
     * @throws MalformedURLException
     * @throws IOException 
     */
    public void next() throws MalformedURLException, IOException
    {
        currentPosition++;
        ImageIcon nextOrigin;
        ImageIcon nextDisplay;
        if (currentPosition >= ImageURLList.TEXT_QZONE1.length)
        {
            currentPosition = 0;
        }
        nextOrigin = IM.openOnlineIcon(ImageURLList.TEXT_QZONE1[currentPosition]);
        nextDisplay = IM.autoScaleIcon(nextOrigin, 2, maxWidth, maxHeight, Image.SCALE_DEFAULT);
        this.imageLabel.setIcon(nextDisplay);
        feedbackLabel.setText("Original Image Size: " + nextOrigin.getIconWidth() + " X " + nextOrigin.getIconHeight() + " scaled to " + nextDisplay.getIconWidth() + " X " + nextDisplay.getIconHeight());

    }

    /**
     * Go to previous image in the list.
     * @throws MalformedURLException
     * @throws IOException 
     */
    public void pervious() throws MalformedURLException, IOException
    {
        currentPosition--;
        ImageIcon nextOrigin;
        ImageIcon nextDisplay;
        if (currentPosition >= ImageURLList.TEXT_QZONE1.length)
        {
            currentPosition = ImageURLList.TEXT_QZONE1.length;
        }
        nextOrigin = IM.openOnlineIcon(ImageURLList.TEXT_QZONE1[currentPosition]);
        nextDisplay = IM.autoScaleIcon(nextOrigin, 2, maxWidth, maxHeight, Image.SCALE_DEFAULT);
        this.imageLabel.setIcon(nextDisplay);
        feedbackLabel.setText("Original Image Size: " + nextOrigin.getIconWidth() + " X " + nextOrigin.getIconHeight() + " scaled to " + nextDisplay.getIconWidth() + " X " + nextDisplay.getIconHeight());
    }

    
//    public void envalidateSettings()
//    {
//        playMusic = playMusicCheckBox.isSelected();
//
//        switch (displayComboBox.getSelectedIndex())
//        {
//            case 0:
//        }
//
//    }

    /**
     * Open the music in this frame.
     * @param fileName Name of the file.
     */
    public void openMusic(String fileName)
    {
        try
        {
            myStream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
            myClip = AudioSystem.getClip();
            myClip.open(myStream);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
        {
            Warning warning = new Warning("Error playing your music!(@open method)");
            // warning.setExceptionString(e.toString());
            e.printStackTrace();
        }

    }

    /**
     * Start music.
     */
    public void playMusic()
    {
        this.myClip.start();
    }

    /**
     * pause music.
     */
    public void pauseMusic()
    {
        myClip.stop();
    }

    /**
     * Stop music.
     */
    public void stopMusic()
    {
        myClip.close();
    }

    /**
     * Test main method.
     * @param args Lines command argument.
     */
    public static void main(String[] args)
    {
        PhotoDisplayer standardFrame = new PhotoDisplayer();
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        Object item = e.getItem();
        if (item.equals(musicComboBox))
        {
            openMusic(musicComboBox.getItemAt(musicComboBox.getSelectedIndex()));
        }
    }

}
